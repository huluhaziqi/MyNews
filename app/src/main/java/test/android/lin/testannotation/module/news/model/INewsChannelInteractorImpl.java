package test.android.lin.testannotation.module.news.model;


import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import greendao.NewsChannelTable;
import greendao.NewsChannelTableDao;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import test.android.lin.testannotation.app.App;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/25.
 */
public class INewsChannelInteractorImpl
        implements INewsChannelInteractor<Map<Boolean, List<NewsChannelTable>>> {
    @Override
    public Subscription channelDbSwap(RequestCallBack callBack, final int fromPos, final int toPos) {
        return (Subscription) Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                final NewsChannelTableDao dao = ((App) App.getContext()).getNewsDaoSession().getNewsChannelTableDao();

                //交换前的位置对应的频道
                final NewsChannelTable fromChannel = dao.queryBuilder()
                        .where(NewsChannelTableDao.Properties.NewsChannelIndex.eq(fromPos)).unique();
                final int fromPosition = fromChannel.getNewsChannelIndex();
                //交换对应的频道

                final NewsChannelTable toChannel = dao.queryBuilder()
                        .where(NewsChannelTableDao.Properties.NewsChannelIndex.eq(toPos)).unique();
                final int toPosition = toChannel.getNewsChannelIndex();

                if (Math.abs(fromPosition - toPosition) == 1) {
                    fromChannel.setNewsChannelIndex(toPosition);
                    toChannel.setNewsChannelIndex(fromPosition);
                    dao.update(fromChannel);
                    dao.update(toChannel);
                } else {
                    if (fromPosition - toPosition < 0) {
                        final List<NewsChannelTable> betweenFromTo = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelIndex
                                        .between(fromPosition + 1, toPosition)).build().list();

                        for (NewsChannelTable s : betweenFromTo) {
                            s.setNewsChannelIndex(s.getNewsChannelIndex() - 1);
                            dao.update(s);
                        }

                        fromChannel.setNewsChannelIndex(toPosition);
                        dao.update(fromChannel);

                    } else if (fromPosition - toPosition > 0) {
                        final List<NewsChannelTable> betweenToFrom
                                = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelIndex
                                        .between(toPosition, fromPosition - 1)).build().list();
                        for (NewsChannelTable s : betweenToFrom) {
                            s.setNewsChannelIndex(s.getNewsChannelIndex() + 1);
                        }
                        fromChannel.setNewsChannelIndex(toPosition);
                        dao.update(fromChannel);
                    }
                }
                subscriber.onCompleted();


            }

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    @Override
    public Subscription channelDbOperator(final RequestCallBack<Map<Boolean, List<NewsChannelTable>>> callBack, final String channelName, final Boolean selectState) {
        return (Subscription) Observable.create(new Observable.OnSubscribe<Map<Boolean, List<NewsChannelTable>>>() {
            @Override
            public void call(Subscriber<? super Map<Boolean, List<NewsChannelTable>>> subscriber) {
                final NewsChannelTableDao dao = ((App) App.getContext()).getNewsDaoSession().getNewsChannelTableDao();
                if (selectState == null) {
                    Log.e("tag", "初始化选中的频道");
                    HashMap<Boolean, List<NewsChannelTable>> map = new HashMap<Boolean, List<NewsChannelTable>>();
                    map.put(true, dao.queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                            .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build().list());
                    map.put(false, dao.queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(false))
                            .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build().list());
                    List<NewsChannelTable> test = dao.queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                            .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build().list();
                    Log.e("tag", test.toString());
                    Log.e("Tag", map.toString());
                    subscriber.onNext(map);
                } else {
                    if (selectState) {
                        Log.e("tag", "做增操作");

                        final NewsChannelTable table = dao.queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelName.eq(channelName)).unique();

                        //原来的位置

                        final int originPos = table.getNewsChannelIndex();

                        final long toPos = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true)).buildCount().count();

                        final List<NewsChannelTable> smallChannelTable = dao.queryBuilder()//找到比他小的没有被选中的，插入到这里
                                .where(NewsChannelTableDao.Properties.NewsChannelIndex.lt(originPos)
                                        , NewsChannelTableDao.Properties.NewsChannelSelect.eq(false)).build().list();

                        for (NewsChannelTable s : smallChannelTable) {
                            s.setNewsChannelIndex(s.getNewsChannelIndex() + 1);
                            dao.update(s);//更新被选中的之前所有没被选中的index(增加1)
                        }

                        table.setNewsChannelSelect(true);
                        table.setNewsChannelIndex((int) toPos);//安排到所有选中的最后去
                        dao.update(table);
                    } else {
                        Log.e("tag", "做减操作");

                        final NewsChannelTable table = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelName.eq(channelName)).unique();

                        final int originPos = table.getNewsChannelIndex();

                        final long toPos = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(false)).buildCount().count();//放在所有没被选中的最后面
                        final List<NewsChannelTable> bigChannelTable = dao.queryBuilder()
                                .where(NewsChannelTableDao.Properties.NewsChannelIndex.gt(originPos)
                                        , NewsChannelTableDao.Properties.NewsChannelSelect.eq(true)).build().list();

                        for (NewsChannelTable s : bigChannelTable) {
                            s.setNewsChannelIndex(s.getNewsChannelIndex() + 1);
                            dao.update(s);
                        }
                        table.setNewsChannelSelect(false);
                        table.setNewsChannelIndex((int) toPos);
                        dao.update(table);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (selectState == null)
                    callBack.beforeRequest();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Map<Boolean, List<NewsChannelTable>>>() {
            @Override
            public void onCompleted() {
                callBack.requestCompleted();
            }

            @Override
            public void onError(Throwable e) {
                callBack.requsetError(e.getLocalizedMessage() + "\n" + e);
            }

            @Override
            public void onNext(Map<Boolean, List<NewsChannelTable>> booleanListMap) {
                callBack.requestSuccess(booleanListMap);

                Log.e("Tag", "接收频道的数据");
            }
        });
    }
}
