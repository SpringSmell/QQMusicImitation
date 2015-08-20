package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import adapter.WidgetAdapter;
import widgets.TitleIndicator;
import widgets.ViewPagerCompat;

public class MainTwoFragment extends Fragment {

    private View mView;
    private ViewPagerCompat mViewPagerCompat;
    private TitleIndicator mTitleIndicator;
    private WidgetAdapter mWidgetAdapter;
    /**当前显示的页面*/
    private int mCurrentTab;
    protected Context mContext;

    public MainTwoFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.finally_fragment, null);
//        init();
        return mView;
    }

    private void init() {
        mViewPagerCompat = (ViewPagerCompat) mView.findViewById(R.id.musics_pavilion_pager);
        mTitleIndicator = (TitleIndicator) mView.findViewById(R.id.musics_pavilion_pager_indicator);

    }
}
