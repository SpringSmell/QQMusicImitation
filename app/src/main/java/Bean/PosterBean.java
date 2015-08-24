package Bean;

/**
 * Created by zoulx on 2015/8/23.
 */
public class PosterBean {

    private String coverUrl;//封面图地址
    private String type;//类型
    private String content;//内容，根据类型判断


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
