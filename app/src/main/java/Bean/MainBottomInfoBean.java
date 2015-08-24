package Bean;

import android.view.View;

/**
 * Created by zoulx on 2015/8/21.
 */
public class MainBottomInfoBean{

    private int songId;
    private String title;
    private String singer;
    private String singerIcon;

    public MainBottomInfoBean(String title,String singer,String singerIcon) {
        this.title=title;
        this.singer=singer;
        this.singerIcon=singerIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSingerIcon() {
        return singerIcon;
    }

    public void setSingerIcon(String singerIcon) {
        this.singerIcon = singerIcon;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
