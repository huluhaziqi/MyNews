package test.android.lin.testannotation.module.news.model;

import java.util.List;

import greendao.NewsChannelTable;
import rx.Subscription;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class INewsInteractorImpl implements  INewsInterator<List<NewsChannelTable>> {
    @Override
    public Subscription operatorChannelDb(RequestCallBack<List<NewsChannelTable>> callBack) {
        return null;
    }
}
