package Bean;

/**
 * Created by zoulx on 2015/8/29.
 */
public class CenterRecommendBean {

    private String title="";//标题
    private String tag="";//所属标签
    private String coverUrl="";//封面
    private String tagIconUrl ="";//标签图标
    private long count=0;//听众数量
    private String countIconUrl;//听众数量图标
    private String rating;//是否显示评分
    private String songIcon;//是否显示播放图标

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTagIconUrl() {
        return tagIconUrl;
    }

    public void setTagIconUrl(String tagIconUrl) {
        this.tagIconUrl = tagIconUrl;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getCountIconUrl() {
        return countIconUrl;
    }

    public void setCountIconUrl(String countIconUrl) {
        this.countIconUrl = countIconUrl;
    }

    public String getSongIcon() {
        return songIcon;
    }

    public void setSongIcon(String songIcon) {
        this.songIcon = songIcon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
