package test.android.lin.testannotation.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by linxiaobin on 2016/6/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SinaPhotoDetail implements Serializable {
    @JsonProperty("status")
    public int status;


    @JsonProperty("data")
    public SinaPhotoDetailDataEntity data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SinaPhotoDetailDataEntity implements Serializable {
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
        @JsonProperty("comments")
        public String comments;
        @JsonProperty("need_match_pic")
        public boolean needMatchPic;
        @JsonProperty("pubDate")
        public int pubDate;
        @JsonProperty("lead")
        public String lead;
        @JsonProperty("content")
        public String content;
        @JsonProperty("keys")
        public List<?> keys;
        @JsonProperty("videos")
        public List<?> videos;


        @JsonProperty("pics")
        public List<SinaPhotoDetailPicsEntity> pics;
        @JsonProperty("recommends")
        public List<?> recommends;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SinaPhotoDetailPicsEntity implements Serializable {
        @JsonProperty("pic")
        public String pic;
        @JsonProperty("alt")
        public String alt;
        @JsonProperty("kpic")
        public String kpic;
        @JsonProperty("size")
        public String size;
    }

}
