package test.android.lin.testannotation.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by lxb on 16/6/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SinaPhotoList {
    @JsonProperty("status")
    public int status;
    @JsonProperty("data")
    public DataEntity data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataEntity {
        @JsonProperty("is_intro")
        public String isIntro;
        @JsonProperty("list")
        public List<PhotoListEntity> list;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PhotoListEntity {
            public int picWidth = -1;
            public int picHeight = -1;

            @JsonProperty("id")
            public String id;
            @JsonProperty("title")
            public String title;
            @JsonProperty("long_title")
            public String longTitle;
            @JsonProperty("source")
            public String source;
            @JsonProperty("link")
            public String link;
            @JsonProperty("pic")
            public String pic;
            @JsonProperty("kpic")
            public String kpic;
            @JsonProperty("bpic")
            public String bpic;
            @JsonProperty("intro")
            public String intro;
            @JsonProperty("pubDate")
            public int pubDate;
            @JsonProperty("articlePubDate")
            public int articlePubDate;
            @JsonProperty("comments")
            public String comments;

            @JsonProperty("pics")
            public PicsEntity pics;
            @JsonProperty("feedShowStyle")
            public String feedShowStyle;
            @JsonProperty("category")
            public String category;
            @JsonProperty("comment")
            public int comment;


            @JsonProperty("comment_count_info")
            public CommentCountInfoEntity commentCountInfo;

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class PicsEntity {
                @JsonProperty("total")
                public int total;
                @JsonProperty("picTemplate")
                public int picTemplate;

                @JsonProperty("list")
                public List<ListEntity> list;

                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class ListEntity {
                    @JsonProperty("pic")
                    public String pic;
                    @JsonProperty("alt")
                    public String alt;
                    @JsonProperty("kpic")
                    public String kpic;
                }

            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class CommentCountInfoEntity {
                @JsonProperty("greply")
                public int greply;
                @JsonProperty("total")
                public int total;
                @JsonProperty("show")
                public int show;
                @JsonProperty("comment_status")
                public int commentStatus;
                @JsonProperty("praise")
                public int praise;
                @JsonProperty("dispraise")
                public int dispraise;

            }
        }
    }
}
