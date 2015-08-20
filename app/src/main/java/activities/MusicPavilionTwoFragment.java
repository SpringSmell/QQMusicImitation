package activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.zoulx.homeactivity.R;

/**
 * Created by zoulx on 2015/8/20.
 */
public class MusicPavilionTwoFragment extends Fragment {

    public MusicPavilionTwoFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_pavilion_two_fragment, null);
        return view;
    }
}
