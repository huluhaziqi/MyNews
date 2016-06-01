package test.android.lin.testannotation.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lxb on 16/5/31.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NeteastNewsDetail {
    @JsonProperty("body")
    public String body;
    @JsonProperty("replyCount")
    public String replyCount;
    @JsonProperty("digest")
    public String digest;
    @JsonProperty("dkeys")
    public String dkeys;
    @JsonProperty("ec")
    public String ec;
    @JsonProperty("docid")
    public String docid;

    /**
     * alias
     * tname
     * ename
     * tid
     */
    @JsonProperty("sourceinfo")
    public SourceinfoEntity sourceinfo;
    @JsonProperty("picnews")
    public boolean picnews;
    @JsonProperty("title")
    public String title;
    @JsonProperty("tid")
    public String tid;
    @JsonProperty("template")
    public String template;
    @JsonProperty("threadVote")
    public int threadVote;
    @JsonProperty("threadAgainst")
    public int threadAgainst;
    @JsonProperty("replyBoard")
    public String replyBoard;
    @JsonProperty("source")
    public String source;
    @JsonProperty("voicecomment")
    public String voicecomment;
    @JsonProperty("hasNext")
    public boolean hasNext;
    @JsonProperty("ptime")
    public String ptime;
    @JsonProperty("users")
    public List<?> users;
    @JsonProperty("ydbaike")
    public List<?> ydbaike;
    @JsonProperty("link")
    public List<?> link;
    @JsonProperty("votes")
    public List<?> votes;

    @JsonProperty("img")
    public List<ImgEntity> img;

    @JsonProperty("topiclist_news")
    public List<TopiclistNewsEntity> topiclistNews;

    @JsonProperty("topiclist")
    public List<TopiclistEntity> topiclist;

    @JsonProperty("keyword_search")
    public List<KeywordSearchEntity> keywordSearch;

    @JsonProperty("video")
    public List<VideoEntity> video;

    @JsonProperty("boboList")
    public List<?> boboList;

    @JsonProperty("relative_sys")
    public List<RelativeSysEntity> relativeSys;
    @JsonProperty("apps")
    public List<?> apps;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SourceinfoEntity {
        @JsonProperty("alias")
        public String alias;
        @JsonProperty("tname")
        public String tname;
        @JsonProperty("ename")
        public String ename;
        @JsonProperty("tid")
        public String tid;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImgEntity {
        @JsonProperty("ref")
        public String ref;
        @JsonProperty("pixel")
        public String pixel;
        @JsonProperty("alt")
        public String alt;
        @JsonProperty("src")
        public String src;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TopiclistNewsEntity {
        @JsonProperty("hasCover")
        public boolean hasCover;
        @JsonProperty("subnum")
        public String subnum;
        @JsonProperty("alias")
        public String alias;
        @JsonProperty("tname")
        public String tname;
        @JsonProperty("ename")
        public String ename;
        @JsonProperty("tid")
        public String tid;
        @JsonProperty("cid")
        public String cid;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TopiclistEntity {
        @JsonProperty("hasCover")
        public boolean hasCover;
        @JsonProperty("subnum")
        public String subnum;
        @JsonProperty("alias")
        public String alias;
        @JsonProperty("tname")
        public String tname;
        @JsonProperty("ename")
        public String ename;
        @JsonProperty("tid")
        public String tid;
        @JsonProperty("cid")
        public String cid;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KeywordSearchEntity {
        @JsonProperty("word")
        public String word;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VideoEntity {
        @JsonProperty("commentid")
        public String commentid;
        @JsonProperty("ref")
        public String ref;
        @JsonProperty("topicid")
        public String topicid;
        @JsonProperty("cover")
        public String cover;
        @JsonProperty("alt")
        public String alt;
        @JsonProperty("url_mp4")
        public String urlMp4;
        @JsonProperty("broadcast")
        public String broadcast;
        @JsonProperty("videosource")
        public String videosource;
        @JsonProperty("commentboard")
        public String commentboard;
        @JsonProperty("appurl")
        public String appurl;
        @JsonProperty("url_m3u8")
        public String urlM3u8;
        @JsonProperty("size")
        public String size;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RelativeSysEntity {
        @JsonProperty("id")
        public String id;
        @JsonProperty("title")
        public String title;
        @JsonProperty("source")
        public String source;
        @JsonProperty("imgsrc")
        public String imgsrc;
        @JsonProperty("type")
        public String type;
        @JsonProperty("ptime")
        public String ptime;
        @JsonProperty("href")
        public String href;
    }
}
