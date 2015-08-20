package activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.List;

import Bean.TabInfo;

/**
 * Created by zoulx on 2015/8/20.
 */
public class MusicPavilionFragment extends MusicsPavilionFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(1, "111", MusicPavilionOneFragment.class));
        tabs.add(new TabInfo(2, "222", MusicPavilionTwoFragment.class));
        tabs.add(new TabInfo(2, "222", MusicPavilionTwoFragment.class));
        return 1;
    }
}
