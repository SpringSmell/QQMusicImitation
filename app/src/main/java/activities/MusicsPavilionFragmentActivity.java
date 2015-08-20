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

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.TabInfo;
import adapter.WidgetAdapter;
import widgets.TitleIndicator;

public abstract class MusicsPavilionFragmentActivity extends FragmentActivity implements OnPageChangeListener {
    private static final String TAG = "DxFragmentActivity";
    public static final String EXTRA_TAB = "tab";
    public static final String EXTRA_QUIT = "extra.quit";
    protected int mCurrentTab = 0;
    protected int mLastTab = -1;
    //存放选项卡信息的列表
    private ArrayList<TabInfo> mTabs = new ArrayList<>();
    //viewpager adapter
    protected WidgetAdapter mHomePagerAdapter = null;
    //viewpager
    protected ViewPager mPager;
    //选项卡控件
    protected TitleIndicator mIndicator;

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

        mPager.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
    }

    /**
     * 添加一个选项卡
     * @param tab
     */
    public void addTabInfo(TabInfo tab) {
        mTabs.add(tab);
        mHomePagerAdapter.notifyDataSetChanged();
    }

    /**
     * 从列表添加选项卡
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfo> tabs) {
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

    protected TabInfo getFragmentById(int tabId) {
        if (mTabs == null) return null;
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfo tab = mTabs.get(index);
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
        return R.layout.musics_pavilion;
    }

    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyTabs(List<TabInfo> tabs);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // for fix a known issue in support library
        // https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
