package activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.MainBottomInfoBean;
import Bean.QueueListViewBean;
import Bean.TabInfoBean;
import adapter.QueueListViewAdapter;
import tools.DIYUtile;
import widgets.SwipeDismissListView;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/*
* UI框架实现
* @author chris young
* @date 2015...
* */

public class MainActivity extends IndicatorFragmentActivity implements View.OnClickListener,DialogInterface.OnDismissListener{


    //constant
    private static final String TAG = "MainActivity";
    private final Context mContext=MainActivity.this;
    //
    private View menuView;
    private LinearLayout llMainBottom;
    private Dialog mDialog;

    private SwipeDismissListView menuListView;
    private QueueListViewAdapter mQueueListViewAdapter;

    //数据源
    private ArrayList<QueueListViewBean> queueBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int supplyTabs(List<TabInfoBean> tabs) {
        Log.i(TAG, "supplyTabs ");
        System.out.print(TAG + ":supplyTabs");
        tabs.add(new TabInfoBean(0, getString(R.string.fragment_one),
                MainOneFragment.class));
        tabs.add(new TabInfoBean(1, getString(R.string.fragment_two),
                MainTwoFragment.class));
        tabs.add(new TabInfoBean(2, getString(R.string.fragment_three),
                MainThreeFragment.class));
        return 1;
    }

    @Override
    protected int supplyBottomPager(List<MainBottomInfoBean> bottomBeans) {
        System.out.print(TAG+":supplyBottomPager");
        bottomBeans.add(new MainBottomInfoBean("第一首", "chris", "www.baidu.com"));
        bottomBeans.add(new MainBottomInfoBean("第二首", "chris", "www.baidu.com"));
        bottomBeans.add(new MainBottomInfoBean("第三首", "chris", "www.baidu.com"));
        return bottomBeans.size() * 100 - bottomBeans.size();
    }

    private void init() {
        System.out.print(TAG + ":init");

        queueBeans=new ArrayList<>();
        QueueListViewBean bean=new QueueListViewBean();
        bean.setSongName("爱情三十六计（Live）");
        bean.setSinger("- 铁扇奥特曼");
        bean.setIsPlay(false);
        bean.setType("nq");
        queueBeans.add(bean);
        queueBeans.add(bean);
        queueBeans.add(bean);
        queueBeans.add(bean);
        queueBeans.add(bean);
        bean=new QueueListViewBean();
        bean.setSongName("那些年");
        bean.setSinger("- 追着光影奔跑的罗拉");
        bean.setIsPlay(true);
        bean.setType("nq");
        queueBeans.add(bean);

        menuView = LayoutInflater.from(mContext).inflate(R.layout.dialog_queue,null);
        llMainBottom= (LinearLayout) findViewById(R.id.main_bottom);

        mQueueListViewAdapter=new QueueListViewAdapter(mContext,queueBeans);

        menuListView= (SwipeDismissListView) menuView.findViewById(R.id.queue_list_view);
        menuListView.setAdapter(mQueueListViewAdapter);

        initListener();
    }

    private void initListener(){
        System.out.print(TAG + ":initListener");
        ((ImageView)llMainBottom.findViewById(R.id.main_bottom_queue)).setOnClickListener(this);
        ((TextView) menuView.findViewById(R.id.queue_close)).setOnClickListener(this);
        menuListView.setOnDismissCallback(new SwipeDismissListView.OnDismissCallback() {
            @Override
            public void onDismiss(int dismissPosition) {
                mQueueListViewAdapter.dismiss(dismissPosition);
                mQueueListViewAdapter.notifyDataSetChanged();
            }
        });
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DIYUtile.showToast(mContext,""+position);
            }
        });
        mMainBottomViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
                System.out.print(TAG + ":onPageSelected position:" + position);
                showToast("播放第：" + (position % mTabs.size() + 1) + "首");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_bottom_queue:
                showQueueAlertDialog();
                break;
            case R.id.queue_close:
                mDialog.dismiss();
                break;
        }
    }

    private void showQueueAlertDialog(){
        if(mDialog==null){
            mDialog=new Dialog(mContext,R.style.transparentDialogStyle);
            mDialog.show();
            mDialog.setContentView(menuView);
            Window mWindow=mDialog.getWindow();
            mWindow.setGravity(Gravity.TOP);

            WindowManager.LayoutParams params=mWindow.getAttributes();
            params.x= 0;
            params.y= (int) (DIYUtile.getScreenHeight(mContext)/1.7);
            params.width=WindowManager.LayoutParams.MATCH_PARENT;
            params.height= (int) (DIYUtile.getScreenHeight(mContext)/1.7);
            params.horizontalMargin=0;
            params.verticalMargin=0;
            mWindow.setAttributes(params);
            mDialog.setOnDismissListener(this);
            mDialog.setCanceledOnTouchOutside(true);
        }else{
            mDialog.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
