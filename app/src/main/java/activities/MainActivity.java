package activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.MainBottomInfoBean;
import Bean.QueueListViewBean;
import Bean.SongInfoBean;
import Bean.TabInfoBean;
import Interfaces.OnSearchSongListener;
import adapter.QueueListViewAdapter;
import frame.SongHandleThread;
import manage.Constant;
import services.MusicService;
import tools.DIYUtile;
import widgets.SwipeDismissListView;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

/*
*
* @author chris young
* @date 2015...
* */

public class MainActivity extends IndicatorFragmentActivity implements View.OnClickListener, DialogInterface.OnDismissListener {


    //constant
    private static final String TAG = "MainActivity";
    private final Context mContext = MainActivity.this;
    //
    private View menuView;
    private ImageView changePlayMode;//切换播放模式

    private LinearLayout llMainBottom;
    private TextView queueTitle;
    private Dialog mDialog;

    //滑动列表
    private SwipeDismissListView menuListView;
    private QueueListViewAdapter mQueueListViewAdapter;

    //数据源
    private ArrayList<QueueListViewBean> queueBeansList;
    private ArrayList<SongInfoBean> songInfoBeanList;
    /*当前播放音乐的位置*/
    private int nowCurPosition = 0;

    private final int offs = 10;//bottom viewPager偏移量
    private final int changeViewPager=1;
    private Boolean isChange = false;//是否切换viewPager

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
        System.out.print(TAG + ":supplyBottomPager");
//        bottomBeans.add(new MainBottomInfoBean("第一首", "chris", "www.baidu.com"));
//        bottomBeans.add(new MainBottomInfoBean("第二首", "chris", "www.baidu.com"));
//        bottomBeans.add(new MainBottomInfoBean("第三首", "chris", "www.baidu.com"));
//        return bottomBeans.size() * 100 - bottomBeans.size();
        return 2000;
    }

    private void init() {
        System.out.print(TAG + ":init");

        queueBeansList = new ArrayList<>();
        songInfoBeanList = new ArrayList<>();

        menuView = LayoutInflater.from(mContext).inflate(R.layout.dialog_queue, null);
        llMainBottom = (LinearLayout) findViewById(R.id.main_bottom);

        mQueueListViewAdapter = new QueueListViewAdapter(mContext, queueBeansList);

        queueTitle = (TextView) menuView.findViewById(R.id.queue_title);
        menuListView = (SwipeDismissListView) menuView.findViewById(R.id.queue_list_view);
        menuListView.setAdapter(mQueueListViewAdapter);

        initListener();
        getSongList();
    }

    private void initListener() {
        System.out.print(TAG + ":initListener");
        ((ImageView) llMainBottom.findViewById(R.id.main_bottom_queue)).setOnClickListener(this);
        ((TextView) menuView.findViewById(R.id.queue_close)).setOnClickListener(this);
        ((ImageView) llMainBottom.findViewById(R.id.main_bottom_pause_play)).setOnClickListener(this);
        menuListView.setOnDismissCallback(new SwipeDismissListView.OnDismissCallback() {
            @Override
            public void onDismiss(int dismissPosition) {
                queueBeansList.remove(dismissPosition);
                songInfoBeanList.remove(dismissPosition);
                mQueueListViewAdapter.setQueueBeans(queueBeansList);
                MusicService.songInfoBeanList = songInfoBeanList;
                mQueueListViewAdapter.notifyDataSetChanged();
            }
        });
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                queueBeansList.get(nowCurPosition).setIsPlay(false);
                queueBeansList.get(position).setIsPlay(true);
                nowCurPosition = position;
                mQueueListViewAdapter.setQueueBeans(queueBeansList);
                mQueueListViewAdapter.notifyDataSetChanged();
                musicMange(position, Constant.PLAY_MSG_PLAY);
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
                if (isChange) {
                    System.out.print(TAG + ":onPageSelected position:" + position);
                    Log.i(TAG, "" + position + "_播放第：" + (position % bottomBeanList.size() + 1) + "首");
                    int location = position % bottomBeanList.size();
                    queueBeansList.get(nowCurPosition).setIsPlay(false);
                    queueBeansList.get(location).setIsPlay(true);
                    nowCurPosition = location;
                    mQueueListViewAdapter.setQueueBeans(queueBeansList);
                    mQueueListViewAdapter.notifyDataSetChanged();
                    musicMange(location, Constant.PLAY_MSG_PLAY);
                }
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

    /*控制播放器*/
    private void musicMange(int position, int type) {
        Intent intent = new Intent();
        intent.putExtra(Constant.PLAY_MSG, type);
        intent.putExtra(Constant.PLAY_POSITION, position);
        intent.setClass(mContext, MusicService.class);
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_queue:
                showQueueAlertDialog();
                menuListView.setSelection(nowCurPosition - 2);
                break;
            case R.id.queue_close:
                mDialog.dismiss();
                break;
            case R.id.main_bottom_pause_play:
                musicMange(nowCurPosition, Constant.PLAY_MSG_PAUSE);
                break;
        }
    }

    /*
    * 显示歌曲队列
    * */
    private void showQueueAlertDialog() {
        if (mDialog == null) {
            mDialog = new Dialog(mContext, R.style.transparentDialogStyle);
            mDialog.show();
            mDialog.setContentView(menuView);
            Window mWindow = mDialog.getWindow();
            mWindow.setGravity(Gravity.TOP);

            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.x = 0;
            params.y = (int) (DIYUtile.getScreenHeight(mContext) / 1.7);
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = (int) (DIYUtile.getScreenHeight(mContext) / 1.7);
            params.horizontalMargin = 0;
            params.verticalMargin = 0;
            mWindow.setAttributes(params);
            mDialog.setOnDismissListener(this);
            mDialog.setCanceledOnTouchOutside(true);
        } else {
            mDialog.show();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
          sendMessage(changeViewPager,null);
    }


    private void getSongList() {
        SongHandleThread sht = new SongHandleThread();
        sht.SearchSongThread(getApplicationContext(), new OnSearchSongListener() {
            @Override
            public void onSearchSongComplete(ArrayList<SongInfoBean> songList, Cursor cursor) {
                setQueueData(songList);
                songInfoBeanList = songList;
                MusicService.songInfoBeanList = songInfoBeanList;
            }
        });
    }

    /*设置队列和viewPager数据*/
    private void setQueueData(ArrayList<SongInfoBean> songList) {
        queueTitle.setText("播放队列(" + songList.size() + ")");
        for (int i = 0; i < songList.size(); i++) {
            QueueListViewBean bean = new QueueListViewBean();
            bean.setSongName(songList.get(i).getSongName());
            bean.setSinger("- " + songList.get(i).getSinger());
            bean.setIsPlay(false);
            if (songList.get(i).getSongSize() > 10 * 1024 * 1024) {//大于10M
                bean.setType(Constant.SQ);
            } else if (songList.get(i).getSongSize() > 6 * 1024 * 1024) {//小于10M,大于6M
                bean.setType(Constant.HQ);
            }
            queueBeansList.add(bean);
            MainBottomInfoBean bottomBean = new MainBottomInfoBean(bean.getSongName(), bean.getSinger(), bean.getCover());
            bottomBeanList.add(bottomBean);
        }
        mQueueListViewAdapter.setQueueBeans(queueBeansList);
        mMainBottomAdapter.setBottomInfoBean(bottomBeanList);
        mMainBottomAdapter.notifyDataSetChanged();
        mMainBottomViewPager.setCurrentItem(bottomBeanList.size() * offs - bottomBeanList.size());
        mQueueListViewAdapter.notifyDataSetChanged();
        isChange=true;
    }

    public void sendMessage(int what, Object obj) {
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.SEARCH_SONG:
                    songInfoBeanList = (ArrayList<SongInfoBean>) msg.obj;
                    break;
                case changeViewPager:
                    int location = bottomBeanList.size() * offs - bottomBeanList.size() + nowCurPosition;
                    mMainBottomViewPager.setCurrentItem(location);
                    break;
            }

        }
    };

    public ArrayList<SongInfoBean> getSongInfoBeanList() {
        return songInfoBeanList;
    }
}
