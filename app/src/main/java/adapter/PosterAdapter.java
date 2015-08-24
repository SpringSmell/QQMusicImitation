package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import Bean.PosterBean;
import activities.PosterFragment;

/**
 * Created by zoulx on 2015/8/22.
 */
public class PosterAdapter extends FragmentPagerAdapter {

    private ArrayList<PosterBean> posters;

    public PosterAdapter(FragmentManager fm,ArrayList<PosterBean> posters) {
        super(fm);
        this.posters=posters;
    }

    @Override
    public Fragment getItem(int position) {
        PosterBean bean=posters.get(position%posters.size());
        return PosterFragment.newInstace(bean);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
