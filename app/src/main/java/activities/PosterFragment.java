package activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activities.zoulx.homeactivity.R;

import Bean.PosterBean;

/**
 * Created by zoulx on 2015/8/23.
 */
public class PosterFragment extends Fragment implements View.OnClickListener{

    private Context mContext;
    private ImageView cover;
    private PosterBean mPosterBean;
    public PosterFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        cover=new ImageView(mContext);
        cover.setLayoutParams(params);
        cover.setScaleType(ImageView.ScaleType.FIT_XY);
        cover.setImageDrawable(getResources().getDrawable(R.drawable.banner_default_one));
        //where download image
        cover.setOnClickListener(this);
        return cover;
    }

    @Override
    public void onClick(View v) {
        if(v==cover){
            showToast("single cover");
        }
    }

    public static class PosterHolder{
        public static PosterFragment INSTACE=new PosterFragment();
    }

    public static PosterFragment newInstace(PosterBean mPosterBean){
        PosterFragment fragment=new PosterFragment();
        fragment.mPosterBean=mPosterBean;
        return fragment;
    }

    private void showToast(String content){
        Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
    }

}
