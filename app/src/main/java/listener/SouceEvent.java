package listener;

import Interfaces.OnSearchSongListener;

/**
 * Created by zoulx on 2015/9/8.
 * 监听管理
 */
public class SouceEvent {

    public SouceEvent(){

    }

    public void notifyEventChange(OnSearchSongListener mOnSearchSongListener){
        if(null!=mOnSearchSongListener){
//            mOnSearchSongListener.onSearchSongComplete(new SongEvent(this));
        }
    }
}
