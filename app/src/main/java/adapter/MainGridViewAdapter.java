package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activities.zoulx.homeactivity.R;

import java.util.ArrayList;
import java.util.List;

import Bean.CenterRecommendBean;
import tools.DIYUtile;

/**
 * Created by zoulx on 2015/8/29.
 */
public class MainGridViewAdapter extends BaseAdapter {

    private ArrayList<CenterRecommendBean> centerRecommendBeanList;
    private Context mContext;

    public MainGridViewAdapter( Context context,ArrayList<CenterRecommendBean> centerRecommendBeanList) {
        this.centerRecommendBeanList = centerRecommendBeanList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return centerRecommendBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return centerRecommendBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_gridview, null);
            holder.init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CenterRecommendBean bean = centerRecommendBeanList.get(position);
        if (DIYUtile.isImgUrlValid(bean.getCoverUrl())){
            //在这里下载cover
        }else{

        }

        holder.tag.setVisibility(View.GONE);
        holder.count.setVisibility(View.GONE);
        holder.songIcon.setVisibility(View.GONE);
        holder.rating.setVisibility(View.GONE);

        if(bean.getCount()!=0) {
            holder.count.setVisibility(View.VISIBLE);
            if(bean.getCount()<10000){
                holder.count.setText(bean.getCount()+"");
            }else{
                holder.count.setText((bean.getCount()/10000)+"万");
            }
            if(DIYUtile.isImgUrlValid(bean.getCountIconUrl())){
                //在这里下载听众数量图标
            }else{

            }
        }

        if(null!=bean.getTag()&&!"".equals(bean.getTag())){
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(bean.getTag());
            if (DIYUtile.isImgUrlValid(bean.getTagIconUrl())) {
                //在这里下载图标
            } else {
//                holder.tag.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }

        if(null!=bean.getSongIcon()&&!"".equals(bean.getSongIcon())){
            holder.songIcon.setVisibility(View.VISIBLE);
            if(DIYUtile.isImgUrlValid(bean.getSongIcon())){
                //在这里下载播放图标
            }else {

            }

        }

        //有评分
        if(null!=bean.getRating()&&!"".equals(bean.getRating())){
            holder.rating.setVisibility(View.VISIBLE);
            holder.rating.setText(bean.getRating());
        }

        holder.title.setText(bean.getTitle());

        if(null!=bean.getCoverUrl()&&DIYUtile.isImgUrlValid(bean.getCoverUrl())){
            //在这里下载封面
        }else{

        }
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView tag;
        private TextView count;
        private ImageView songIcon;
        private ImageView cover;
        private TextView rating;

        private void init(View v) {
            tag = (TextView) v.findViewById(R.id.item_main_grid_view_tag);
            title = (TextView) v.findViewById(R.id.item_main_grid_view_title);
            count = (TextView) v.findViewById(R.id.item_main_grid_view_count);
            songIcon = (ImageView) v.findViewById(R.id.item_main_grid_view_is_song);
            cover = (ImageView) v.findViewById(R.id.item_main_grid_view_cover);
            rating= (TextView) v.findViewById(R.id.item_main_grid_view_Rating);
        }
    }
}
