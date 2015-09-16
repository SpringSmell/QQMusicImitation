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

import Bean.QueueListViewBean;
import manage.Constant;
import tools.DIYUtile;

/**
 * Created by zoulx on 2015/9/7.
 */
public class QueueListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<QueueListViewBean> queueBeans;

    public QueueListViewAdapter(Context context,ArrayList<QueueListViewBean> queueBeans){
        this.mContext=context;
        this.queueBeans=queueBeans;
    }

    @Override
    public int getCount() {
        return queueBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return queueBeans.get(position).getSongId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        QueueListViewBean bean=queueBeans.get(position);
        int greenTheme=mContext.getResources().getColor(R.color.green_theme);
        int white=mContext.getResources().getColor(R.color.white);
        int gray=mContext.getResources().getColor(R.color.tab_indicator_text_unselected);

        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            //提示，如果root传null，listView的item只有一层layout时会无法显示高度
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_queue_list_view,null);
            holder.init(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        if(bean.getIsPlay()){//正在播放
            holder.isPlay.setVisibility(View.VISIBLE);
            holder.songName.setTextColor(greenTheme);
            holder.singer.setTextColor(greenTheme);
        }else{
            holder.isPlay.setVisibility(View.GONE);
            holder.songName.setTextColor(white);
            holder.singer.setTextColor(gray);
        }

        if(bean.getType().equalsIgnoreCase(Constant.SQ)){
            holder.songName.setCompoundDrawablesWithIntrinsicBounds(null,null,mContext.getResources().getDrawable(R.drawable.sq),null);
        }else if(bean.getType().equalsIgnoreCase(Constant.HQ)){
            holder.songName.setCompoundDrawablesWithIntrinsicBounds(null,null,mContext.getResources().getDrawable(R.drawable.hq),null);
        }else{
            holder.songName.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }

        holder.songName.setText(bean.getSongName());
        holder.singer.setText(bean.getSinger());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queueBeans.remove(position);
                notifyDataSetChanged();
            }
        });
        convertView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.selector_item_queue_dialog));
        return convertView;
    }

    public void setQueueBeans(ArrayList<QueueListViewBean> queueBeans){
        this.queueBeans=queueBeans;
    }

    public void dismiss(int dismissPosition){
        queueBeans.remove(dismissPosition);
    }

    class ViewHolder {
        private TextView songName;
        private TextView singer;
        private ImageView isPlay;
        private TextView delete;
        void init(View v){
            songName= (TextView) v.findViewById(R.id.queue_song_name);
            singer= (TextView) v.findViewById(R.id.queue_singer);
            isPlay= (ImageView) v.findViewById(R.id.queue_is_play);
            delete= (TextView) v.findViewById(R.id.queue_del_single_song);
        }
    }
}
