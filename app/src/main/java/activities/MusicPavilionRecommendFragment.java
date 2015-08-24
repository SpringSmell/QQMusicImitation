package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;

import Bean.PosterBean;
import adapter.PosterAdapter;
import manage.Constant;

/**
 * Created by zoulx on 2015/8/20.
 */
public class MusicPavilionRecommendFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private static final String TAG="MusicPavilionRecommendFragment";
    private Context mContext;
    private View mView;
    private ViewPager mViewPager;
    private RelativeLayout centerGroup;
    private PosterAdapter mPosterAdapter;
    private ArrayList<PosterBean> banners;
    //广告banners的点
    private LinearLayout posterLayoutDots;
    private ArrayList<View> dots;
    /**上一次banner位置*/
    private int lastPostion=0;
    /**当前滑动方向*/
    private static int changeSongFlag= Constant.INVALID;

    public MusicPavilionRecommendFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        mView = inflater.inflate(R.layout.recommend_fragment, null);
        init();
        return mView;
    }

    private void init() {
        mContext=getActivity();
        posterLayoutDots= (LinearLayout) mView.findViewById(R.id.poster_dots);
        dots=new ArrayList<>();

        centerGroup= (RelativeLayout) mView.findViewById(R.id.center_group);
        centerGroup.setOnClickListener(this);
        initPosterWidget();
        mViewPager= (ViewPager) mView.findViewById(R.id.poster_viewpager);
        mPosterAdapter=new PosterAdapter(getChildFragmentManager(),banners);
        mViewPager.setAdapter(mPosterAdapter);
        mViewPager.setCurrentItem(banners.size()*30-banners.size());
        mViewPager.setOnPageChangeListener(this);
    }

    private void initPosterWidget() {
        banners=new ArrayList<>();
        PosterBean bean=new PosterBean();
        bean.setCoverUrl("");
        bean.setContent("Poster");
        bean.setType("1");
        banners.add(bean);
        banners.add(bean);
        banners.add(bean);
        int resId = R.drawable.dot_normal;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(15, 15);
        params.setMargins(5, 0, 0, 10);
        for(int i=0;i<banners.size();i++){
            View dot=new View(mContext);
            dot.setBackgroundResource(resId);
            dot.setLayoutParams(params);
            posterLayoutDots.addView(dot);
            dots.add(i,dot);
        }
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_focused);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_normal);
        lastPostion=position%banners.size();
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_focused);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.center_group:
                showToast("center_group");
                break;
        }
    }
}
