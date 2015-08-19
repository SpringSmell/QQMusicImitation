package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.zoulx.homeactivity.R;

public class FragmentTwo extends Fragment {
    protected View mMainView;
    protected Context mContext;
    public FragmentTwo() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        mMainView = inflater.inflate(R.layout.middle_fragment, container, false);
        return mMainView;
    }
}
