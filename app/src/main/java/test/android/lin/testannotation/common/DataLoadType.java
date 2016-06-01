package test.android.lin.testannotation.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lxb on 16/5/30.
 */
public class DataLoadType {


    public static final int TYPE_REFRESH_SUCCESS = 1;

    public static final int TYPE_REFRESH_FAIL = 2;
    public static final int TYPE_LOAD_MORE_SUCCESS = 3;
    public static final int TYPE_LOAD_MORE_FAIL = 4;

    @IntDef({TYPE_REFRESH_SUCCESS, TYPE_REFRESH_FAIL, TYPE_LOAD_MORE_SUCCESS, TYPE_LOAD_MORE_FAIL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DataLoadTypeChecker {

    }
}
