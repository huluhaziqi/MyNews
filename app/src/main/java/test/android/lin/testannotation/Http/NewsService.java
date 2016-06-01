package test.android.lin.testannotation.Http;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import test.android.lin.testannotation.bean.NeteastNewsDetail;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.bean.SinaPhotoList;

/**
 * Created by lxb on 16/5/31.
 */
public interface NewsService {
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NeteastNewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") String startPage;
    );

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NeteastNewsDetail>> getNewsDetail(@Header("Cache-control") String cacheControl,
                                                             @Path("postId") String postId);

    @GET("list.json")
    Observable<SinaPhotoList> getSinaPhotoList(
        @Header("Cache-Control") String cacheControl,
        @Query("channel") String photoTypeId,
        @Query("adid") String adid,
        @Query("wm") String wm,
        @Query("from") String from,
        @Query("chwm") String chwm,
        @Query("oldchwm") String oldchwm,
        @Query("imei") String imei, @Query("uid") String uid, @Query("p") int page);
}
