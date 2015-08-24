package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import Bean.MainBottomInfoBean;

/**
 * Created by zoulx on 2015/8/23.
 */
public class MainBottomFragment extends Fragment implements View.OnClickListener{

    private MainBottomInfoBean bean;
    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        mView=inflater.inflate(R.layout.item_main_bottom, null);
        ImageView cover= (ImageView) mView.findViewById(R.id.bottom_cover);
        //where download image
        TextView songName= (TextView) mView.findViewById(R.id.bottom_song_name);
        TextView singer= (TextView) mView.findViewById(R.id.bottom_singer);
        singer.setText(bean.getSinger());
        songName.setText(bean.getTitle());
        mView.setOnClickListener(this);
        return mView;
    }
    public static MainBottomFragment newStance(MainBottomInfoBean bean){
        MainBottomFragment fragment=new MainBottomFragment();
        fragment.bean=bean;
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if(v==mView){
            Toast.makeText(mContext,"now current song",Toast.LENGTH_SHORT).show();
        }
    }
}
