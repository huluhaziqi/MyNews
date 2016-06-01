package test.android.lin.testannotation.Http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lxb on 16/5/31.
 */
public class HostType {
    public static final int TYPE_COUNT = 3;
    public static final int NETEASE_NEWS_VIDEO = 1;

    public static final int SINA_NEWS_PHOTO = 2;

    public static final int WEATHER_INFO = 3;

    @IntDef({NETEASE_NEWS_VIDEO, SINA_NEWS_PHOTO, WEATHER_INFO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeCheck {

    }
}
