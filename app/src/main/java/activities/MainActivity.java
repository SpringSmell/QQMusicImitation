package activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import com.activities.zoulx.homeactivity.R;

import java.util.List;

import Bean.MainBottomInfoBean;
import Bean.TabInfoBean;
import manage.Constant;

public class MainActivity extends IndicatorFragmentActivity {

    private static final String TAG = "MainActivity";
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int supplyTabs(List<TabInfoBean> tabs) {
        Log.i(TAG, "supplyTabs ");
        tabs.add(new TabInfoBean(FRAGMENT_ONE, getString(R.string.fragment_one),
                MainOneFragment.class));
        tabs.add(new TabInfoBean(FRAGMENT_TWO, getString(R.string.fragment_two),
                MainTwoFragment.class));
        tabs.add(new TabInfoBean(FRAGMENT_THREE, getString(R.string.fragment_three),
                MainThreeFragment.class));
        return FRAGMENT_TWO;
    }

    @Override
    protected int supplyBottomPager(List<MainBottomInfoBean> bottomBeans) {
        bottomBeans.add(new MainBottomInfoBean("第一首", "chris", "www.baidu.com"));
        bottomBeans.add(new MainBottomInfoBean("第二首", "chris", "www.baidu.com"));
        bottomBeans.add(new MainBottomInfoBean("第三首", "chris", "www.baidu.com"));
        return bottomBeans.size() * 100 - bottomBeans.size();
    }

    private void init() {
        mMainBottomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 页面在滚动的时候一直调动
             * position :当前页面，及你点击滑动的页面
             * positionOffset:当前页面偏移的百分比
             * positionOffsetPixels:当前页面偏移的像素位置
             *
             * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**切换完成*/
            @Override
            public void onPageSelected(int position) {

                showToast("播放第："+(position%mTabs.size()+1)+"首");
            }

            /*
             * 此方法是在状态改变的时候调用，其中state这个参数有三种状态（0，1，2）。
             * state==1的时辰默示正在滑动，state==2的时辰默示滑动完毕了，state==0的时辰默示什么都没做。
             * 当页面开始滑动的时候，三种状态的变化顺序为（1，2，0）
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
