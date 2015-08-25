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

import Bean.ItemListBean;

/**
 * Created by zoulx on 2015/8/24.
 */
public class ItemListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ItemListBean> itemBeans;

    public ItemListAdapter(Context context,ArrayList<ItemListBean> itemBeans){
        this.mContext=context;
        this.itemBeans=itemBeans;

    }
    @Override
    public int getCount() {
        return itemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return itemBeans.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null==convertView){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_recommend,null);
            holder.init(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        ItemListBean bean=itemBeans.get(position);
        if(null!=bean.getCover()){
            holder.cover=bean.getCover();
        }
        holder.title.setText(bean.getTitle());
        holder.describe.setText(bean.getDescribe());
        return convertView;
    }


    public class ViewHolder{
        private ImageView cover;
        private TextView title;
        private TextView describe;
        public void init(View v){
            cover= (ImageView) v.findViewById(R.id.item_cover);
            title= (TextView) v.findViewById(R.id.item_title);
            describe= (TextView) v.findViewById(R.id.item_describe);
        }
    }
}
