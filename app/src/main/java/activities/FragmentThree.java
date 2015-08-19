package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentThree extends Fragment {

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
    public FragmentThree() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        mMainView = inflater.inflate(R.layout.fragment_finally, container, false);
        ListView listView = (ListView) mMainView.findViewById(R.id.list);
        SimpleAdapter adapter = new SimpleAdapter(mContext, mlistItems,
                R.layout.listview_item, new String[] {
                "name", "sex"
        }, new int[] {
                R.id.name, R.id.download
        });
        listView.setAdapter(adapter);
        return mMainView;
    }

}
