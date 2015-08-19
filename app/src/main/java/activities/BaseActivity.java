package activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import manage.Constant;

/**
 * Created by zoulx on 2015/8/19.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(Constant.FINISH);
    registerReceiver(mFinishReceiver,mIntentFilter);
    }

    private BroadcastReceiver mFinishReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String finish=intent.getStringExtra(Constant.FINISH);
            if (Constant.FINISH.equalsIgnoreCase(finish)){
                finish();
            }
        }
    };
}
