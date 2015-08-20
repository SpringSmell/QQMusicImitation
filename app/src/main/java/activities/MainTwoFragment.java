package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.TabInfo;
import adapter.WidgetAdapter;
import manage.Constant;
import widgets.TitleIndicator;
import widgets.ViewPagerCompat;

public class MainTwoFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View mView;
    private ViewPagerCompat mViewPagerCompat;
    private TitleIndicator mTitleIndicator;
    private WidgetAdapter mWidgetAdapter;
    private ArrayList<TabInfo> mTabs;
    /**当前显示的页面*/
    private int mCurrentTab;
    protected Context mContext;

    public MainTwoFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.musics_pavilion, null);
//        init();
        return mView;
    }

    private void init() {
        mTabs= new ArrayList<>();
        mWidgetAdapter=new WidgetAdapter(getActivity(),getChildFragmentManager(),mTabs);
        mViewPagerCompat = (ViewPagerCompat) mView.findViewById(R.id.musics_pavilion_pager);
        mTitleIndicator = (TitleIndicator) mView.findViewById(R.id.musics_pavilion_pager_indicator);
        mCurrentTab=0;
        Intent data=getActivity().getIntent();
        if(null!=data){
            mCurrentTab=data.getIntExtra(Constant.EXTRA_TAB,0);
        }
        mViewPagerCompat.setAdapter(mWidgetAdapter);
        mViewPagerCompat.setOnPageChangeListener(this);
        mViewPagerCompat.setOffscreenPageLimit(mTabs.size());
        mTitleIndicator.init(mCurrentTab,mTabs,mViewPagerCompat);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
