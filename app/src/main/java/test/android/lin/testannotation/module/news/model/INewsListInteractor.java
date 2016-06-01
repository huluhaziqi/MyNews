package test.android.lin.testannotation.module.news.model;

import rx.Subscription;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/31.
 */
public interface INewsListInteractor<T> {
    Subscription requestNewsList(RequestCallBack<T> callBack, String type, String id, int startPage);
}
