package Interfaces;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.EventListener;

import Bean.SongInfoBean;

/**
 * Created by zoulx on 2015/9/8.
 */
public interface OnSearchSongListener extends EventListener {

    /*
    * 搜索本地歌曲
    * @params songList 歌曲列表
    * @params cursor 歌曲信息类
    * */
    void onSearchSongComplete(ArrayList<SongInfoBean> songList,Cursor cursor);

}
