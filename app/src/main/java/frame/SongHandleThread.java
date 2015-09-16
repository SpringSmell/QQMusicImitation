package frame;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

import Bean.SongInfoBean;
import Interfaces.OnSearchSongListener;

/**
 * Created by zoulx on 2015/9/8.
 */
public class SongHandleThread {

    public String TAG = "SongHandleThread";

    /*
    * 搜索歌曲
    * */
    public void SearchSongThread(final Context context, final OnSearchSongListener mOnSongEventListener) {
        new Thread() {
            @Override
            public void run() {
                Cursor cursor = null;
                ArrayList<SongInfoBean> songInfoBeanList=new ArrayList<>();
                try {
                    cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

                    System.out.print(TAG + ":SearchSongThread cursor.count:" + cursor.getCount());
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));
                        long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
//                        if (isMusic == 0) {//不是音乐
//                            continue;
//                        }
                        if(!(size>=2*1024*1024)){//大于2M的才加入队列
                            continue;
                        }
                        SongInfoBean bean = new SongInfoBean();
                        bean.setSongID(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                        bean.setSongName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                        bean.setSinger(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                        bean.setSongURL(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                        bean.setSongTime(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                        bean.setSongSize(size);
                        songInfoBeanList.add(bean);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                mOnSongEventListener.onSearchSongComplete(songInfoBeanList,cursor);
                cursor.close();
                super.run();
            }
        }.start();
    }
}
