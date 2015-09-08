package Bean;

import android.widget.ImageView;

/**
 * Created by zoulx on 2015/8/24.
 */
public class ItemListBean {

    private int id=0;
    private String title="";
    private String describe="";
    private ImageView cover=null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public ImageView getCover() {
        return cover;
    }

    public void setCover(ImageView cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
