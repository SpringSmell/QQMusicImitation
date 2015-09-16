package Interfaces;

import java.util.EventListener;

/**
 * Created by zoulx on 2015/9/9.
 */
public interface MusicFunction extends EventListener {

    /*
    * 开始播放
    * @prams position 当前歌曲已播放的时间(msec)
    * */
    void onPlay(int position);
    /*
    * 暂停播放
    * */
    void onPause();
    /*
    * 停止播放
    * */
    void onStop();
    /*
    * 切换上一首
    * */
    void onNextPlay();
    /*
    * 切换上一首
    * */
    void onLastPlay();
    /*
    * 单曲
    * */
    void onSingPlay();
    /*
    * 单曲循环
    * */
    void onSingLoopPlay();
    /*
    * 列表
    * */
    void onListPlay();
    /*
    * 列表循环
    * */
    void onListLoopPlay();
    /*
    * 随机播放
    * */
    void onRandomPlay();

}
