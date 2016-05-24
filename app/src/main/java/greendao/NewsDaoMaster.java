package greendao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * Created by linxiaobin on 2016/5/23.
 */
public class NewsDaoMaster extends AbstractDaoMaster {
    private static final int SCHEMA_VERSION = 1;

    public static void createAllTable(SQLiteDatabase db, boolean ifNotExits) {
        NewsChannelTableDao.createTable(db, ifNotExits);
    }

    public static void dropAllTable(SQLiteDatabase db, boolean ifExits) {
        NewsChannelTableDao.dropTable(db, ifExits);
    }


    @Override
    protected void registerDaoClass(Class<? extends AbstractDao<?, ?>> daoClass) {
        super.registerDaoClass(daoClass);
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {//继承SQLiteOpenHelper

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, 1);
        }

        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createAllTable(db, false);
        }
    }

    public static class DevHelper extends OpenHelper {//新建和重写onUpgrage()

        public DevHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        public DevHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            dropAllTable(db, true);
            onCreate(db);
        }
    }

    @Override
    public int getSchemaVersion() {
        return super.getSchemaVersion();
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return super.getDatabase();
    }

    public NewsDaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(NewsChannelTableDao.class);
    }

    @Override
    public NewsDaoSession newSession() {
        return new NewsDaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    @Override
    public NewsDaoSession newSession(IdentityScopeType type) {
        return new NewsDaoSession(db, type, daoConfigMap);
        ;
    }
}
