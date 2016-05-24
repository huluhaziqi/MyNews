package test.android.lin.testannotation.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import greendao.NewsChannelTableDao;
import greendao.NewsDaoMaster;
import greendao.NewsDaoSession;
import test.android.lin.testannotation.common.Constant;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class App extends Application {

    private static Context applicationContext;
    private NewsChannelTableDao newsChannelTableDao;
    private NewsDaoSession newsDaoSession;

    public NewsChannelTableDao getNewsChannelTableDao() {
        return newsChannelTableDao;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    private void setupDatabase() {
        NewsDaoMaster.DevHelper helper = new NewsDaoMaster.DevHelper(this, Constant.BD_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();

        NewsDaoMaster master = new NewsDaoMaster(database);
        newsDaoSession = master.newSession();

    }


    public static Context getContext() {
        return applicationContext;
    }

    public NewsDaoSession getNewsDaoSession() {
        return newsDaoSession;
    }
}
