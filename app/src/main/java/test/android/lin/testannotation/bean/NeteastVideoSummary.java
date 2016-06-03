package test.android.lin.testannotation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;

/**
 * Created by linxiaobin on 2016/6/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NeteastVideoSummary {
    public int picWidth = -1;
    public int picHeight = -1;
    @JsonProperty("replyCount")
    public int replyCount;
    @JsonProperty("videosource")
    public String videosource;
    @JsonProperty("mp4Hd_url")
    public Object mp4HdUrl;
    @JsonProperty("cover")
    public String cover;
    @JsonProperty("title")
    public String title;
    @JsonProperty("playCount")
    public int playCount;
    @JsonProperty("replyBoard")
    public String replyBoard;
    @JsonProperty("sectiontitle")
    public String sectiontitle;
    @JsonProperty("description")
    public String description;
    @JsonProperty("replyid")
    public String replyid;
    @JsonProperty("mp4_url")
    public String mp4Url;
    @JsonProperty("length")
    public int length;
    @JsonProperty("playersize")
    public int playersize;
    @JsonProperty("m3u8Hd_url")
    public Object m3u8HdUrl;
    @JsonProperty("vid")
    public String vid;
    @JsonProperty("m3u8_url")
    public String m3u8Url;
    @JsonProperty("ptime")
    public String ptime;
}
