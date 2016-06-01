package test.android.lin.testannotation.module.news.model;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.dao.query.Query;
import greendao.NewsChannelTable;
import greendao.NewsChannelTableDao;
import rx.Subscription;
import test.android.lin.testannotation.Http.Api;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.app.App;
import test.android.lin.testannotation.callback.RequestCallBack;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import test.android.lin.testannotation.common.Constant;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class INewsInteractorImpl implements INewsInterator<List<NewsChannelTable>> {
    @Override
    public Subscription operatorChannelDb(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return (Subscription) Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                final NewsChannelTableDao newsChannelTableDao = ((App) App.getContext()).getNewsDaoSession().getNewsChannelTableDao();
                Log.e("Tag","初始化数据库了吗"+ App.getContext().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE).getBoolean("initDb", false));
                if (!App.getContext().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE).getBoolean("initDb", false)) {

                List<String> channelName = Arrays.asList(App.getContext().getResources().getStringArray(R.array.news_channel));
                List<String> channelId = Arrays.asList(App.getContext().getResources().getStringArray(R.array.news_channel_id));
                Log.e("Tag", channelId.toString());
                Log.e("Tag", channelName.toString());
                for (int i = 0; i < channelName.size(); i++) {
                    System.out.println(i);
                    Log.e("Tag", channelId.get(i));
                    NewsChannelTable table = new NewsChannelTable(channelName.get(i), channelId.get(i), Api.getType(channelId.get(i)), i <= 2, i, i <= 2);
                    newsChannelTableDao.insert(table);
                    Log.e("Tag ", "i" + i + table.getNewsChannelType());
                }
                }
                App.getContext().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE).edit().putBoolean("initDb", true).apply();
                Log.e("Tag", "数据库初始化完毕");

                final Query<NewsChannelTable> build = newsChannelTableDao.queryBuilder()
                        .where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                        .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build();
                Log.e("Tag", build.list().toString());
                subscriber.onNext(build.list());
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        callBack.beforeRequest();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsChannelTable>>() {
                    @Override
                    public void onCompleted() {
                        callBack.requestCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.requsetError(e.getLocalizedMessage() + "\n" + e);
                    }

                    @Override
                    public void onNext(List<NewsChannelTable> newsChannelTables) {
                        callBack.requestSuccess(newsChannelTables);
                        Log.e("Tag", "数据接收完毕");
                    }

                })


                ;
    }
}
