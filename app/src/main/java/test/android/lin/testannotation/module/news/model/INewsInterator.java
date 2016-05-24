package test.android.lin.testannotation.module.news.model;

import rx.Subscription;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public interface INewsInterator<T> {
    Subscription operatorChannelDb(RequestCallBack<T> callBack);
}
