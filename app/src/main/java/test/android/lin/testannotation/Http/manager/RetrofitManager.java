package test.android.lin.testannotation.Http.manager;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.android.lin.testannotation.Http.Api;
import test.android.lin.testannotation.Http.HostType;
import test.android.lin.testannotation.Http.NewsService;
import test.android.lin.testannotation.app.App;
import test.android.lin.testannotation.bean.NeteastNewsDetail;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.bean.NeteastVideoSummary;
import test.android.lin.testannotation.bean.SinaPhotoDetail;
import test.android.lin.testannotation.bean.SinaPhotoList;
import test.android.lin.testannotation.util.NetUtil;

/**
 * Created by lxb on 16/5/31.
 */
public class RetrofitManager {
    private static final long CACHE_STATE_SEC = 60 * 60 * 24 * 2;//缓存有效期为两天
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-state=" + CACHE_STATE_SEC;
    //为only-if-cached则只查询缓存而不会请求服务器，max-state用于配合设置缓存失效的时间。
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";//在max-age秒内不会发出对应的服务器请求，直接使用缓存返回。

    private static volatile OkHttpClient sOkHttpClient;
    private NewsService mNewsService;

    //管理不用HostType的单例
    private static SparseArray<RetrofitManager> sInstanceManager = new SparseArray<>(HostType.TYPE_COUNT);
    private Interceptor mRewriteCacheControlInerceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!NetUtil.isConnected(App.getContext())) {
                //没联网//没联网直接从缓存中获取数据
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Log.e("tag", "no network");
            }
            Response response = chain.proceed(request);//

            if (NetUtil.isConnected(App.getContext())) {
                String cacheControl = request.cacheControl().toString();//有网络的时候从@Header中获取
                return response.newBuilder().header("Cache-control", cacheControl).removeHeader("Pragma").build();
            } else
                return response.newBuilder().header("Cache-control", "public,only-if-cached," + CACHE_STATE_SEC).removeHeader("Pragma").build();
        }
    };
    //打印返回的json数据拦截器
    private Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            final Response response = chain.proceed(request);

            final ResponseBody responseBody = response.body();//body
            final long contentLength = responseBody.contentLength();//contentLength;

            BufferedSource source = responseBody.source();

            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            MediaType mediaType = responseBody.contentType();
            if (mediaType != null) {
                try {
                    charset = mediaType.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }
            KLog.json(buffer.clone().readString(charset));
            return response;
        }
    };

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager instance = sInstanceManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            sInstanceManager.put(hostType, instance);
            return instance;
        } else return instance;
    }

    private RetrofitManager(@HostType.HostTypeCheck int hostType) {
        KLog.e("manager" + Api.getHost(hostType));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.getHost(hostType))
                .client(getsOkHttpClient()).addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        mNewsService = retrofit.create(NewsService.class);
    }

    private OkHttpClient getsOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (sOkHttpClient == null) {
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                    sOkHttpClient = new OkHttpClient.Builder().cache(cache).addNetworkInterceptor(mRewriteCacheControlInerceptor)
                            .addInterceptor(mLoggingInterceptor).retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS).build();
                }
            }
        }
        return sOkHttpClient;
    }

    @NonNull
    private String getCacheControl() {
        return NetUtil.isConnected(App.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    public Observable<Map<String, List<NeteastNewsSummary>>> getNewsListObservable(String type, String id, int startPage) {
        KLog.e("接收网络数据成功MyNews");
        return mNewsService.getNewsList(getCacheControl(), type, id, startPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    public Observable<Map<String, NeteastNewsDetail>> getNewsDetailObservable(String postid) {
        return mNewsService.getNewsDetail(getCacheControl(), postid).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }

    public Observable<SinaPhotoList> getSinaPhotoListObservable(String photoTypeId, int page) {
        return mNewsService.getSinaPhotoList(getCacheControl(), photoTypeId, "4ad30dabe134695c3b7c3a65977d7e72", "b207", "6042095012", "12050_0001",
                "12050_0001", "867064013906290", "802909da86d9f5fc", page)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<SinaPhotoDetail> getSinaPhotoDetailObservable(String id) {
        return mNewsService.getSinaPhotoDetail(getCacheControl(), Api.SINA_PHOTO_DETAIL_ID, "b207",
                "6042095012", "12050_0001", "12050_0001", "867064013906290", "802909da86d9f5fc", id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    public Observable<Map<String, List<NeteastVideoSummary>>> getVideoListObservable(String id, int startPage) {
        return mNewsService.getVideoList(getCacheControl(), id, startPage).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

//    public Observable<Weath>
}
