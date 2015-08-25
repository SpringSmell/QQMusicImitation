package activities;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;

import Bean.ItemListBean;
import Bean.PosterBean;
import adapter.ItemListAdapter;
import adapter.PosterAdapter;
import manage.Constant;
import tools.DIYUtile;

/**
 * Created by zoulx on 2015/8/20.
 */
public class MusicPavilionRecommendFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "MusicPavilionRecommendFragment";
    private Context mContext;
    //widget
    private View mView;
    private ViewPager mViewPager;
    private RelativeLayout centerGroup;
    //为你推荐
    private ListView forYouRecommendListView;
    private ItemListAdapter forYouRecommendAdapter;
    private ArrayList<ItemListBean> forYouRecommendBeans;
    //专栏
    private ListView speciallyListView;
    private ItemListAdapter speciallyAdapter;
    private ArrayList<ItemListBean> speciallyBeans;
    //巅峰、音乐人
    private LinearLayout peakedness;
    private LinearLayout musicsPerson;
    //

    private int listItemResId;
    //
    private PosterAdapter mPosterAdapter;
    private ArrayList<PosterBean> banners;
    //广告banners的点
    private LinearLayout posterLayoutDots;
    private ArrayList<View> dots;
    public static final int CHANGE_POSTER = 0;
    /**
     * 上一次banner位置
     */
    private int lastPostion = 0;
    private long changePosterTime = 4000;
    private ChangePosterThread mChangePosterThread;
    /**
     * 当前滑动方向
     */
    private static int changeSongFlag = Constant.INVALID;

    /**
     * 开启所有线程
     */
    private Boolean startAllThread = true;
    private Boolean pausePosterThread = true;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHANGE_POSTER:
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    break;
            }

        }
    };


    public MusicPavilionRecommendFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.recommend_fragment, null);
        init();
        return mView;
    }

    private void init() {
        mContext = getActivity();
        listItemResId = R.layout.item_recommend;

        //
        peakedness= (LinearLayout) mView.findViewById(R.id.recommend_peakedness);
        musicsPerson= (LinearLayout) mView.findViewById(R.id.recommend_music_person);
        peakedness.setOnClickListener(this);
        musicsPerson.setOnClickListener(this);
        //
        posterLayoutDots = (LinearLayout) mView.findViewById(R.id.poster_dots);

        dots = new ArrayList<>();
        //为你推荐---------------
        forYouRecommendListView = (ListView) mView.findViewById(R.id.for_you_recommend);
        forYouRecommendBeans = new ArrayList<>();
        ItemListBean bean = new ItemListBean();
        bean.setId(20150824);
        bean.setTitle("治愈系钢琴曲");
        bean.setDescribe("编辑推荐");
        ImageView cover = new ImageView(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        cover.setBackgroundResource(R.drawable.for_yout_recommend);
        cover.setScaleType(ImageView.ScaleType.FIT_XY);
        cover.setLayoutParams(params);
        bean.setCover(cover);
        forYouRecommendBeans.add(bean);
        forYouRecommendBeans.add(bean);
        forYouRecommendAdapter = new ItemListAdapter(mContext, forYouRecommendBeans);
        forYouRecommendListView.setAdapter(forYouRecommendAdapter);
        DIYUtile.setListViewHeightBasedOnChildren(forYouRecommendListView);
        //专栏--------------
        speciallyListView = (ListView) mView.findViewById(R.id.specially_group);
        speciallyBeans = new ArrayList<>();
        speciallyBeans.add(bean);
        speciallyBeans.add(bean);
        speciallyBeans.add(bean);
        speciallyBeans.add(bean);
        speciallyBeans.add(bean);
        speciallyBeans.add(bean);
        speciallyAdapter = new ItemListAdapter(mContext, speciallyBeans);
        speciallyListView.setAdapter(speciallyAdapter);
        DIYUtile.setListViewHeightBasedOnChildren(speciallyListView);
        //
        centerGroup = (RelativeLayout) mView.findViewById(R.id.center_group);
        centerGroup.setOnClickListener(this);
        initPosterWidget();
        mViewPager = (ViewPager) mView.findViewById(R.id.poster_viewpager);
        mPosterAdapter = new PosterAdapter(getChildFragmentManager(), banners);
        mViewPager.setAdapter(mPosterAdapter);
        mViewPager.setCurrentItem(banners.size() * 30 - banners.size());
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              switch (event.getAction()) {
                                                  case MotionEvent.ACTION_DOWN:
                                                  case MotionEvent.ACTION_MOVE:
                                                      pausePosterThread = false;
                                                      break;
                                                  case MotionEvent.ACTION_UP:
                                                      pausePosterThread = true;
                                                      break;
                                              }
                                              return false;
                                          }
                                      }
        );

    }

    private void initPosterWidget() {
        banners = new ArrayList<>();
        PosterBean bean = new PosterBean();
        bean.setCoverUrl("");
        bean.setContent("Poster");
        bean.setType("1");
        banners.add(bean);
        banners.add(bean);
        banners.add(bean);
        int resId = R.drawable.dot_normal;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.setMargins(5, 0, 0, 10);
        for (int i = 0; i < banners.size(); i++) {
            View dot = new View(mContext);
            dot.setBackgroundResource(resId);
            dot.setLayoutParams(params);
            posterLayoutDots.addView(dot);
            dots.add(i, dot);
        }
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_focused);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_normal);
        lastPostion = position % banners.size();
        dots.get(lastPostion).setBackgroundResource(R.drawable.dot_focused);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showToast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_group:
                showToast("center_group");
                break;
            case R.id.recommend_peakedness:
                showToast("巅峰榜");
                break;
            case R.id.recommend_music_person:
                showToast("音乐人");
                break;
        }
    }

    /**
     * @param state 向handler发送消息
     */
    public void sendMessage(int state) {
        Message msg = new Message();
        msg.what = state;
        handler.sendMessage(msg);
    }

    @Override
    public void onStart() {
        startAllThread = true;
        if (null == mChangePosterThread || mChangePosterThread.isAlive()) {
            mChangePosterThread = new ChangePosterThread();
            mChangePosterThread.start();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        startAllThread = false;
        super.onStop();
    }

    public class ChangePosterThread extends Thread {

        @Override
        public void run() {
            while (startAllThread) {
                try {
                    Thread.sleep(changePosterTime);
                    if (pausePosterThread) {
                        Message msg = new Message();
                        msg.what = CHANGE_POSTER;
                        handler.sendMessage(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
            super.run();
        }
    }
}
