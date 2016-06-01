package test.android.lin.testannotation.util;

import android.content.Context;

/**
 * Created by lxb on 16/5/26.
 */
public class MeasureUtil {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
