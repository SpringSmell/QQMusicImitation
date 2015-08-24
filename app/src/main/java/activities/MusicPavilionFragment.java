package activities;

import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.List;

import Bean.TabInfoBean;

/**
 * Created by zoulx on 2015/8/20.
 */
public class MusicPavilionFragment extends MusicsPavilionFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int supplyTabs(List<TabInfoBean> tabs) {
        tabs.add(new TabInfoBean(1, "111", MusicPavilionRecommendFragment.class));
        tabs.add(new TabInfoBean(2, "222", MusicPavilionTwoFragment.class));
        tabs.add(new TabInfoBean(2, "222", MusicPavilionTwoFragment.class));
        return 1;
    }
}
