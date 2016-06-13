package test.android.lin.testannotation.module.news.model;

import android.util.Log;

import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import test.android.lin.testannotation.Http.Api;
import test.android.lin.testannotation.Http.HostType;
import test.android.lin.testannotation.Http.manager.RetrofitManager;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/31.
 */
public class INewsListInteractorImpl implements INewsListInteractor<List<NeteastNewsSummary>> {
    @Override
    public Subscription requestNewsList(final RequestCallBack<List<NeteastNewsSummary>> callBack, final String type, final String id, int startPage) {
        Log.e("新闻列表", "type " + type + "; id " + id);
        Log.e("新闻列表", "type " + type + "; id " + id);


        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO)
                .getNewsListObservable(type, id, startPage).doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        KLog.e("处理");
                        callBack.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        KLog.e("错误时处理" + throwable + "--- " + throwable.getLocalizedMessage());
                    }
                }).flatMap(new Func1<Map<String, List<NeteastNewsSummary>>, Observable<NeteastNewsSummary>>() {
                    @Override
                    public Observable<NeteastNewsSummary> call(Map<String, List<NeteastNewsSummary>> stringListMap) {
                        KLog.e("id" + id);
                        KLog.e(stringListMap.toString());
                        if (id.equals(Api.HOUSE_ID))
                            return Observable.from(stringListMap.get("北京"));

                            return Observable.from(stringListMap.get(id));
                    }
                }).toSortedList(new Func2<NeteastNewsSummary, NeteastNewsSummary, Integer>() {
                    @Override
                    public Integer call(NeteastNewsSummary neteastNewsSummary, NeteastNewsSummary neteastNewsSummary2) {
                        KLog.e("排序成功");
                        return neteastNewsSummary2.ptime.compareTo(neteastNewsSummary.ptime);
                    }
                }).subscribe(new Subscriber<List<NeteastNewsSummary>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("tag","onCompleted success");
                        callBack.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.requsetError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<NeteastNewsSummary> neteastNewsSummaries) {
                        KLog.e("onNext success");
                        callBack.requestSuccess(neteastNewsSummaries);
                    }
                })


                ;


    }
}
