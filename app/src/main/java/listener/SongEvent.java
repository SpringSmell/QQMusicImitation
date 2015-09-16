package listener;

import java.util.EventObject;

/**
 * Created by zoulx on 2015/9/8.
 */
public class SongEvent extends EventObject {

    private Object obj;

    public SongEvent(Object source){
        super(source);
        this.obj=source;
    }


}
