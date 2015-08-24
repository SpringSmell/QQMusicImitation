package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;

import Bean.TabInfoBean;
import adapter.WidgetAdapter;
import manage.Constant;
import widgets.MusicPavilionIndicator;

public class MainTwoFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View mView;
    private ViewPager mViewPager;
    private MusicPavilionIndicator mMusicPavilionIndicator;
    private WidgetAdapter mWidgetAdapter;
    private ArrayList<TabInfoBean> mTabs;
    /**
     * 当前显示的页面
     */
    private int mCurrentTab = 0;
    private int mLastTab = -1;
    protected Context mContext;

    public MainTwoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.musics_pavilion, null);
        init();
        return mView;
    }

    private void init() {

        mCurrentTab = supplyTabs();
        mWidgetAdapter = new WidgetAdapter(getActivity(), getChildFragmentManager(), mTabs);
        mViewPager = (ViewPager) mView.findViewById(R.id.musics_pavilion_pager);
        mMusicPavilionIndicator = (MusicPavilionIndicator) mView.findViewById(R.id.musics_pavilion_pager_indicator);

        Intent data = getActivity().getIntent();
        if (null != data) {
            mCurrentTab = data.getIntExtra(Constant.EXTRA_TAB, 0);
        }
        mViewPager.setAdapter(mWidgetAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mTabs.size());
        mMusicPavilionIndicator.init(mCurrentTab, mTabs, mViewPager);
        mViewPager.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
    }

    /**
     * 添加一个标签
     */
    public void addTabInfo(TabInfoBean tab) {
        mTabs.add(tab);
        mWidgetAdapter.notifyDataSetChanged();
    }

    /**
     * 从列表添加选项卡
     *
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfoBean> tabs) {
        mTabs.addAll(tabs);
        mWidgetAdapter.notifyDataSetChanged();
    }


    private int supplyTabs() {
        mTabs = new ArrayList<>();
        mTabs.add(new TabInfoBean(1, getString(R.string.recommend), MusicPavilionRecommendFragment.class));
        mTabs.add(new TabInfoBean(2, getString(R.string.products), MusicPavilionTwoFragment.class));
        mTabs.add(new TabInfoBean(3, getString(R.string.song_menu), MusicPavilionThreeFragment.class));
        mTabs.add(new TabInfoBean(4, getString(R.string.station), MusicPavilionThreeFragment.class));
        mTabs.add(new TabInfoBean(5, getString(R.string.MV), MusicPavilionThreeFragment.class));
        return 0;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mMusicPavilionIndicator.onScrolled((mViewPager.getWidth() + mViewPager.getPageMargin()) * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mMusicPavilionIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mLastTab = mCurrentTab;
        }
    }

    private TabInfoBean getFragmentID(int tabID) {
        if (mTabs == null) {
            return null;
        }
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfoBean tabInfoBean = mTabs.get(index);
            if (tabID == tabInfoBean.getId()) {
                return tabInfoBean;
            }
        }
        return null;
    }

    /**跳转到指定ID页面*/
    private void navigate(int tabID) {
        if (mTabs == null) {
            for (int index = 0, count = mTabs.size();index < count; index++) {
                if(mTabs.get(index).getId()==tabID){
                    mViewPager.setCurrentItem(tabID);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
