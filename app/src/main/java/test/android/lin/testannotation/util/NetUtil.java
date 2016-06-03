package test.android.lin.testannotation.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by linxiaobin on 2016/6/1.
 */
public class NetUtil {
    private NetUtil() {
        throw new UnsupportedOperationException("CANNOT BE INSTANTIATED");
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return false;
        return connectivityManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName name = new ComponentName("com.android.setting", "com.android.setting.WirelessSetting");
        intent.setComponent(name);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent,0);
    }
}
