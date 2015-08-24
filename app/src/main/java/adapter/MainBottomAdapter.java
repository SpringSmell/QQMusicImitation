package adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import Bean.MainBottomInfoBean;
import activities.MainBottomFragment;

/**
 * Created by zoulx on 2015/8/21.
 */
public class MainBottomAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<MainBottomInfoBean> bottomInfoBeans;

    public MainBottomAdapter(FragmentManager fm,ArrayList<MainBottomInfoBean> bottomInfoBean) {
        super(fm);
        this.bottomInfoBeans=bottomInfoBean;
    }


    @Override
    public int getCount() {
        return 5000;
    }

    public void setBottomInfoBean(ArrayList<MainBottomInfoBean> bottomInfoBean){
        this.bottomInfoBeans=bottomInfoBean;
    }

    @Override
    public Fragment getItem(int position) {
        MainBottomInfoBean bean=bottomInfoBeans.get(position%bottomInfoBeans.size());
        return MainBottomFragment.newStance(bean);
    }
}
