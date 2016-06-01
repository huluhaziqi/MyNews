package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

/**
 * Created by linxiaobin on 2016/5/23.
 */
public class NewsChannelTableDao extends AbstractDao<NewsChannelTable, String> {
    public static final String TABLENAME = "NEWS_CHANNEL_TABLE";//一定要配置

    public NewsChannelTableDao(DaoConfig config) {
        super(config);
    }

    public NewsChannelTableDao(DaoConfig config, NewsDaoSession daoSession) {
        super(config, daoSession);
    }

    public static class Properties {
        public static final Property NewsChannelName = new Property(0, String.class, "newsChannelName", true, "NEWS_CHANNEL_NAME");
        public static final Property NewsChannelId = new Property(1, String.class, "newsChannelId", false, "NEWS_CHANNEL_ID");
        public static final Property NewsChannelType = new Property(2, String.class, "newsChannelType", false, "NEWS_CHANNEL_TYPE");
        public static final Property NewsChannelSelect = new Property(3, boolean.class, "newsChannelSelect", false, "NEWS_CHANNEL_SELECT");
        public static final Property NewsChannelIndex = new Property(4, int.class, "newsChannelIndex", false, "NEWS_CHANNEL_INDEX");
        public static final Property NewsChannelFixed = new Property(5, Boolean.class, "newsChannelFixed", false, "NEWS_CHANNEL_FIXED");
    }

    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS" : "";
        db.execSQL("CREATE TABLE" + constraint + "\"NEWS_CHANNEL_TABLE\""
                + "(\"NEWS_CHANNEL_NAME\"  TEXT PRIMARY KEY NOT NULL,"
                + "\"NEWS_CHANNEL_ID\" TEXT NOT NULL,"
                + "\"NEWS_CHANNEL_TYPE\" TEXT NOT NULL,"
                + "\"NEWS_CHANNEL_SELECT\" INTEGER NOT NULL,"
                + "\"NEWS_CHANNEL_INDEX\" INTEGER NOT NULL,"
                + "\"NEWS_CHANNEL_FIXED\" INTEGER" +
                ");");
        db.execSQL("CREATE INDEX " + constraint + "IDX_NEWS_CHANNEL_TABLE_NEWS_CHANNEL_NAME ON NEWS_CHANNEL_TABLE" +
                " (\"NEWS_CHANNEL_NAME\");");
    }

    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String constraint = ifExists ? "IF EXISTS" : "";
        db.execSQL("DROP TABLE" + constraint + "\"NEWS_CHANNEL_TABLE\"");
    }

    @Override
    protected NewsChannelTable readEntity(Cursor cursor, int offset) {
//        NewsChannelTable newsChannelTable = new NewsChannelTable();
//        newsChannelTable.setNewsChannelName(cursor.getString(offset + 0));
//        newsChannelTable.setNewsChannelId(cursor.getString(offset + 1));
//        newsChannelTable.setNewsChannelType(cursor.getString(offset + 2));
//        newsChannelTable.setNewsChannelSelect(cursor.getShort(offset + 3) != 0);
//        newsChannelTable.setNewsChannelIndex(cursor.getShort(offset + 4));
//        newsChannelTable.setNewsChannelFixed(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);

        NewsChannelTable newsChannelTable1 = new NewsChannelTable(cursor.getString(offset + 0),
                cursor.getString(offset + 1),
                cursor.getString(offset + 2),
                cursor.getShort(offset + 3) != 0,
                cursor.getShort(offset + 4),
                cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
        return newsChannelTable1;
    }

    @Override
    protected String readKey(Cursor cursor, int offset) {
        return cursor.getString(offset + 0);
    }

    @Override
    protected void readEntity(Cursor cursor, NewsChannelTable entity, int offset) {
        entity.setNewsChannelName(cursor.getString(offset + 0));
        entity.setNewsChannelId(cursor.getString(offset + 1));
        entity.setNewsChannelType(cursor.getString(offset + 2));
        entity.setNewsChannelSelect(cursor.getShort(offset + 3) != 0);
        entity.setNewsChannelIndex(cursor.getShort(offset + 4));
        entity.setNewsChannelFixed(cursor.isNull(offset + 5) ? null : cursor.getShort(offset + 5) != 0);
    }

    @Override
    protected void bindValues(SQLiteStatement stmt, NewsChannelTable entity) {
        stmt.clearBindings();//先清除
        stmt.bindString(1, entity.getNewsChannelName());
        stmt.bindString(2, entity.getNewsChannelId());
        stmt.bindString(3, entity.getNewsChannelType());
        stmt.bindLong(4, entity.getNewsChannelSelect() ? 1L : 0L);
        stmt.bindLong(5, entity.getNewsChannelIndex());
        if (entity.getNewsChannelFixed() != null)
            stmt.bindLong(6, entity.getNewsChannelFixed() ? 1 : 0);
    }

    @Override
    protected String updateKeyAfterInsert(NewsChannelTable entity, long rowId) {
        return entity.getNewsChannelName();
    }

    @Override
    protected String getKey(NewsChannelTable entity) {
        if (entity != null)
            return entity.getNewsChannelName();
        else return null;
    }

    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
}
