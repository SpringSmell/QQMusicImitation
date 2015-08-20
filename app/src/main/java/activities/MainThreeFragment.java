package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainThreeFragment extends Fragment {

    private View mMainView;
    private static ArrayList<Map<String, Object>> mlistItems;
    private Context mContext;

    static {
        mlistItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "name#" + i);
            map.put("sex", i % 2 == 0 ? "male" : "female");
            mlistItems.add(map);
        }
    }
    public MainThreeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        mMainView = inflater.inflate(R.layout.finally_fragment, container, false);
        return mMainView;
    }

}
