package Bean;

/**
 * Created by zoulx on 2015/9/7.
 */
public class QueueListViewBean {

    private String songName="";
    private String singer="";
    private Long songId=0l;
    private String type="";
    private Boolean isPlay=false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Boolean getIsPlay() {
        return isPlay;
    }

    public void setIsPlay(Boolean isPlay) {
        this.isPlay = isPlay;
    }
}
