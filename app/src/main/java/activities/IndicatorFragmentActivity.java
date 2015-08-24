/*
 * @author http://blog.csdn.net/singwhatiwanna
 */
package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.MainBottomInfoBean;
import Bean.TabInfoBean;
import adapter.MainBottomAdapter;
import adapter.WidgetAdapter;
import widgets.TitleIndicator;

public abstract class IndicatorFragmentActivity extends FragmentActivity implements OnPageChangeListener {
    private static final String TAG = "DxFragmentActivity";
    public static final String EXTRA_TAB = "tab";
    public static final String EXTRA_QUIT = "extra.quit";
    protected int mCurrentBottomPager=0;
    protected int mCurrentTab = 0;
    protected int mLastTab = -1;
    //存放选项卡信息的列表
    protected ArrayList<TabInfoBean> mTabs = new ArrayList<>();
    private ArrayList<MainBottomInfoBean> bottomBeans=new ArrayList<>();
    //viewpager adapter
    protected WidgetAdapter mHomePagerAdapter = null;
    //viewpager
    protected ViewPager mPager;
    //选项卡控件
    protected TitleIndicator mIndicator;
    /**主布局底部Bean*/
    protected MainBottomInfoBean mMainBottomInfoBean;
    /**主布局底部adapter*/
    protected MainBottomAdapter mMainBottomAdapter;
    /**主布局底部viewPager*/
    protected ViewPager mMainBottomViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getMainViewResId());
        initViews();

        //设置viewpager内部页面之间的间距
//        mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
        //设置viewpager内部页面间距的drawable
//        mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
    }

    @Override
    protected void onDestroy() {
        mTabs.clear();
        mTabs = null;
        mHomePagerAdapter.notifyDataSetChanged();
        mHomePagerAdapter = null;
        mPager.setAdapter(null);
        mPager = null;
        mIndicator = null;
        super.onDestroy();
    }

    private final void initViews() {
        // 这里初始化界面

        mCurrentTab = supplyTabs(mTabs);
        mCurrentBottomPager=supplyBottomPager(bottomBeans);
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
        }
        Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
        mHomePagerAdapter = new WidgetAdapter(this, getSupportFragmentManager(), mTabs);

        mPager = (ViewPager) findViewById(R.id.main_pager);
        mPager.setAdapter(mHomePagerAdapter);
        mPager.setOnPageChangeListener(this);
        mPager.setOffscreenPageLimit(mTabs.size());

        mIndicator = (TitleIndicator) findViewById(R.id.main_pager_indicator);
        mIndicator.init(mCurrentTab, mTabs, mPager);

        mMainBottomViewPager= (ViewPager) findViewById(R.id.main_bottom_viewpager);
        mMainBottomAdapter=new MainBottomAdapter(getSupportFragmentManager(),bottomBeans);
        mMainBottomViewPager.setAdapter(mMainBottomAdapter);
        mMainBottomViewPager.setCurrentItem(mCurrentBottomPager);
        mPager.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
    }

    /**
     * 添加一个选项卡
     * @param tab
     */
    public void addTabInfo(TabInfoBean tab) {
        mTabs.add(tab);
        mHomePagerAdapter.notifyDataSetChanged();
    }

    /**
     * 从列表添加选项卡
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfoBean> tabs) {
        mTabs.addAll(tabs);
        mHomePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin()) * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mLastTab = mCurrentTab;
        }
    }

    protected TabInfoBean getFragmentById(int tabId) {
        if (mTabs == null) return null;
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfoBean tab = mTabs.get(index);
            if (tab.getId() == tabId) {
                return tab;
            }
        }
        return null;
    }
    /**
     * 跳转到任意选项卡
     * @param tabId 选项卡下标
     */
    public void navigate(int tabId) {
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            if (mTabs.get(index).getId() == tabId) {
                mPager.setCurrentItem(index);
            }
        }
    }

    @Override
    public void onBackPressed() {
            finish();
    }

    /**
     * 返回layout id
     * @return layout id
     */
    protected int getMainViewResId() {
        return R.layout.main_tab_activity;
    }

    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyTabs(List<TabInfoBean> tabs);

    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyBottomPager(List<MainBottomInfoBean> bottomBeans);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // for fix a known issue in support library
        // https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    protected void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
