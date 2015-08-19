package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import Bean.TabInfo;

/**
 * Created by zoulx on 2015/8/18.
 * 最外层viewPager的适配器
 */
public class WidgetAdapter extends FragmentPagerAdapter {
    ArrayList<TabInfo> tabs = null;
    Context context = null;

    public WidgetAdapter(Context context, FragmentManager fm, ArrayList<TabInfo> tabs) {
        super(fm);
        this.tabs = tabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        if (tabs != null && pos < tabs.size()) {
            TabInfo tab = tabs.get(pos);
            if (tab == null)
                return null;
            fragment = tab.createFragment();
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        if (tabs != null && tabs.size() > 0)
            return tabs.size();
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TabInfo tab = tabs.get(position);
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        tab.fragment = fragment;
        return fragment;
    }
}
