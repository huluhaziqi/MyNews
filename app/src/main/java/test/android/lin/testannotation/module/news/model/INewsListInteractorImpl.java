package test.android.lin.testannotation.module.news.model;

import android.util.Log;

import com.socks.library.KLog;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import test.android.lin.testannotation.Http.HostType;
import test.android.lin.testannotation.Http.manager.RetrofitManager;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/31.
 */
public class INewsListInteractorImpl implements INewsListInteractor<List<NeteastNewsSummary>> {
    @Override
    public Subscription requestNewsList(final RequestCallBack<List<NeteastNewsSummary>> callBack, String type, String id, int startPage) {
        Log.e("新闻列表", "type " + type + "; id " + id);

        return (Subscription) RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .getNewsListObservable(type, id, startPage).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                         KLog.e("错误时处理" + throwable + "--- " + throwable.getLocalizedMessage());
                    }
                })
                ;


    }
}
