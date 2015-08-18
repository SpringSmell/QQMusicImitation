package activities;

import java.util.List;

import android.os.Bundle;

import com.activities.zoulx.homeactivity.R;

public class MainActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_one),
                FragmentOne.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_two),
                FragmentTwo.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_three),
                FragmentThree.class));

        return FRAGMENT_TWO;
    }

}
