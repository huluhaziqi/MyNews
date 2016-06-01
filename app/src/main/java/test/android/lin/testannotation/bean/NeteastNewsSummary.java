package test.android.lin.testannotation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lxb on 16/5/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知的
public class NeteastNewsSummary {
    @JsonProperty("postid")
    public String postid;
    @JsonProperty("hasCover")
    public boolean hasCover;
    @JsonProperty("hasHead")
    public int hasHead;
    @JsonProperty("replyCount")
    public int replyCount;
    @JsonProperty("hasImg")
    public int hasImg;
    @JsonProperty("digest")
    public String digest;
    @JsonProperty("hasIcon")
    public boolean hasIcon;
    @JsonProperty("docid")
    public String docid;
    @JsonProperty("title")
    public String title;
    @JsonProperty("order")
    public int order;
    @JsonProperty("priority")
    public int priority;
    @JsonProperty("Imodify")
    public String Imodify;

    @JsonProperty("boardid")
    public String boardid;
    @JsonProperty("photosetID")
    public int photosetID;
    @JsonProperty("template")
    public int template;
    @JsonProperty("votecount")
    public int votecount;
    @JsonProperty("skipID")
    public String skipID;
    @JsonProperty("alias")
    public String alias;
    @JsonProperty("skipType")
    public String skipType;
    @JsonProperty("cid")
    public String cid;
    @JsonProperty("hasAD")
    public int hasAD;
    @JsonProperty("imgsrc")
    public String imgsrc;
    @JsonProperty("tname")
    public String tname;
    @JsonProperty("ename")
    public String ename;
    @JsonProperty("ptime")
    public String ptime;


    @JsonProperty("ads")
    public List<AdsEntity> ads;
    @JsonProperty("imgextra")
    public List<ImagextraEntity> imgextra;

    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class AdsEntity {
        @JsonProperty("title")
        public String title;
        @JsonProperty("tag")
        public String tag;
        @JsonProperty("imgsrc")
        public String imgsrc;

        @JsonProperty("subtitle")
        public String subtitle;

        @JsonProperty("url")
        public String url;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImagextraEntity {
        @JsonProperty("imgsrc")
        public String imgsrc;
    }

}
