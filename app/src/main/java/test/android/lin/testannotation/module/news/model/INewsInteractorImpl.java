package test.android.lin.testannotation.module.news.model;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
                if (App.getContext().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE).getBoolean("initDb", false)) {
                    final NewsChannelTableDao newsChannelTableDao = ((App) App.getContext()).getNewsDaoSession().getNewsChannelTableDao();

                    List<String> channelName = Arrays.asList(App.getContext().getResources().getStringArray(R.array.news_channel));
                    List<String> channelId = Arrays.asList(App.getContext().getResources().getStringArray(R.array.news_channel_id));

                    for (int i = 0; i < channelName.size(); i++) {
                        NewsChannelTable table = new NewsChannelTable(channelName.get(i), channelId.get(i), Api.getType(channelId.get(i)), i <= 2, i, i <= 2);
                        newsChannelTableDao.insert(table);
                    }
                }
                App.getContext().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE).edit().putBoolean("initDb", true);
                Log.e("Tag", "数据库初始化完毕");

            }
        });
    }
}
