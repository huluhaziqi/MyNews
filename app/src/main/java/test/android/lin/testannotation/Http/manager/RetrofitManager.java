package test.android.lin.testannotation.Http.manager;

import okhttp3.OkHttpClient;

/**
 * Created by lxb on 16/5/31.
 */
public class RetrofitManager {
    private static final long CACHE_STATE_SEC = 60 * 60 * 24 * 2;//缓存有效期为两天
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-state=" + CACHE_STATE_SEC;
    //为only-if-cached则只查询缓存而不会请求服务器，max-state用于配合设置缓存失效的时间。
    private static final String CACHE_CONTROL_NETWORK = "max-age=0";//在max-age秒内不会发出对应的服务器请求，直接使用缓存返回。

    private static volatile OkHttpClient sOkHttpClient;
}
