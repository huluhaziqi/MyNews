package test.android.lin.testannotation.module.news.model;

import rx.Subscription;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/25.
 */
public interface INewsChannelInteractor<T> {

    Subscription channelDbOperator(RequestCallBack<T> callBack, String channelName, Boolean selectState);

    Subscription channelDbSwap(RequestCallBack callBack, int fromPos, int toPos);


}
