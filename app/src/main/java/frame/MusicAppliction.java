package frame;

import android.app.Application;
import android.content.Context;

/**
 * Created by zoulx on 2015/8/19.
 */
public class MusicAppliction extends Application {

    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext=getApplicationContext();
    }

    public static Context getInstance(){
            return mAppContext;
    }
}
