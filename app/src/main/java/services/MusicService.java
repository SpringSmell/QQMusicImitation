package services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Bean.SongInfoBean;
import Interfaces.MusicFunction;
import activities.MainActivity;
import manage.Constant;

/**
 * Created by zoulx on 2015/9/9.
 */
public class MusicService extends Service implements MusicFunction, MediaPlayer.OnCompletionListener {
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private String songPath = "";//歌曲路径
    private boolean isPause = false;
    private int msec;//当前已播放的时间
    private int curPosition = 0;//播放歌曲的下标
    public static int playType;//播放方式，随机，顺序，单曲。。。
    public static ArrayList<SongInfoBean> songInfoBeanList = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == mMediaPlayer) {
            mMediaPlayer = new MediaPlayer();
        }
        int position = intent.getIntExtra(Constant.PLAY_POSITION, 0);
        songPath = songInfoBeanList.get(position).getSongURL();
        if (curPosition != position) {
            curPosition = position;
            if (mMediaPlayer.isPlaying()) {
                onStop();
            }
        }
        int msg = intent.getIntExtra(Constant.PLAY_MSG, 0);
        if (msg == Constant.PLAY_MSG_PLAY) {
            onPlay(curPosition);
        } else if (msg == Constant.PLAY_MSG_PAUSE) {
            onPause();
        } else if (msg == Constant.PLAY_MSG_STOP) {
            onStop();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onPlay(int position) {
        if (null == mMediaPlayer) {
            mMediaPlayer = new MediaPlayer();
        }
        if (mMediaPlayer.isPlaying()) {
            return;
        }
        mMediaPlayer.reset();//复位
        try {
            mMediaPlayer.setDataSource(songPath);//设置播放路径
            mMediaPlayer.prepareAsync();//缓冲
            mMediaPlayer.setOnPreparedListener(new mOnPreparedListener(mMediaPlayer, position));
            mMediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }else{
            onPlay(curPosition);
            isPause=false;
        }
    }

    @Override
    public void onStop() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSingPlay() {
        onStop();
    }

    @Override
    public void onSingLoopPlay() {
        onPlay(curPosition);
    }

    @Override
    public void onListPlay() {
        onNextPlay();
    }

    @Override
    public void onListLoopPlay() {
        if (curPosition == 0) {
            curPosition++;
        } else if (curPosition == songInfoBeanList.size() - 1) {
            curPosition = 0;
        }
        onPlay(curPosition);
    }

    @Override
    public void onRandomPlay() {
        Random ra = new Random();
        curPosition = ra.nextInt(songInfoBeanList.size());
        songPath = songInfoBeanList.get(curPosition).getSongURL();
        onPlay(curPosition);
    }

    @Override
    public void onNextPlay() {
        curPosition = curPosition == songInfoBeanList.size() - 1 ? 0 : curPosition + 1;
        songPath = songInfoBeanList.get(curPosition).getSongURL();
        onPlay(curPosition);
    }

    @Override
    public void onLastPlay() {
        curPosition = curPosition == 0 ? songInfoBeanList.size() - 1 : curPosition--;
        songPath = songInfoBeanList.get(curPosition).getSongURL();
        onPlay(curPosition);
    }

    @Override
    public void onDestroy() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (playType) {
            case Constant.PLAY_TYPE_LIST://列表
                onListPlay();
                break;
            case Constant.PLAY_TYPE_LIST_LOOP://列表循环
                onListLoopPlay();
                break;
            case Constant.PLAY_TYPE_SINGLE://单曲
                onSingPlay();
                break;
            case Constant.PLAY_TYPE_SINGLE_LOOP://单曲循环
                onSingLoopPlay();
                break;
            case Constant.PLAY_TYPE_RANDOM://随机
                onRandomPlay();
                break;
            default:
                onNextPlay();
                break;
        }
    }

    /*
        * 音乐准备好的时候播放
        *
        * */
    private final class mOnPreparedListener implements MediaPlayer.OnPreparedListener {

        private MediaPlayer mMediaPlayer;
        private int msec = 0;

        public mOnPreparedListener(MediaPlayer mMediaPlayer, int msec) {
            this.mMediaPlayer = mMediaPlayer;
            this.msec = msec;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
            if (msec > 0) {
                mMediaPlayer.seekTo(msec);
            }
        }
    }
}
