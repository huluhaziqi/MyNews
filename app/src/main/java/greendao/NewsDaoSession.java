package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by linxiaobin on 2016/5/23.
 */
public class NewsDaoSession extends AbstractDaoSession {
    private NewsChannelTableDao newsChannelTableDao;
    private DaoConfig daoConfig;


    public NewsDaoSession(SQLiteDatabase db, IdentityScopeType type,
                          Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);
        daoConfig = daoConfigMap.get(NewsChannelTableDao.class).clone();
        daoConfig.initIdentityScope(type);
        newsChannelTableDao = new NewsChannelTableDao(daoConfig, this);

        registerDao(NewsChannelTable.class, newsChannelTableDao);//绑定类型和dao
    }

    public NewsChannelTableDao getNewsChannelTableDao() {
        return newsChannelTableDao;
    }
}
