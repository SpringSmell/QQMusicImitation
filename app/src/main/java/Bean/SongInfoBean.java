package Bean;

/**
 * Created by zoulx on 2015/9/8.
 */
public class SongInfoBean {

    private String songName="";
    private String singer="";
    private Long songID=0l;
    private Long songTime=0l;
    private Long songSize=0l;
    private String songURL="";


    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }


    public Long getSongID() {
        return songID;
    }

    public void setSongID(Long songID) {
        this.songID = songID;
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

    public Long getSongSize() {
        return songSize;
    }

    public void setSongSize(Long songSize) {
        this.songSize = songSize;
    }

    public Long getSongTime() {
        return songTime;
    }

    public void setSongTime(Long songTime) {
        this.songTime = songTime;
    }
}
