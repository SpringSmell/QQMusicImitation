package Interfaces;

import java.util.EventListener;

/**
 * Created by zoulx on 2015/9/9.
 */
public interface OnPlayCompletionListener extends EventListener {

    /*播放完成*/
    void onPlayCompletion(int position);
}
