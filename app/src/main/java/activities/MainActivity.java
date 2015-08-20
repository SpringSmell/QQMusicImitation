package activities;

import android.os.Bundle;
import android.util.Log;

import com.activities.zoulx.homeactivity.R;

import java.util.List;

import Bean.TabInfo;

public class MainActivity extends IndicatorFragmentActivity {

    private static final String TAG="MainActivity";
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate1 ");
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate2 ");
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        Log.i(TAG, "supplyTabs ");
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_one),
                MainOneFragment.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_two),
                MainTwoFragment.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_three),
                MainThreeFragment.class));
        return FRAGMENT_TWO;
    }

}
