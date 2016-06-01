package test.android.lin.testannotation.module.news.model;

import android.util.Log;

import java.util.List;

import rx.Subscription;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by lxb on 16/5/31.
 */
public class INewsListInteractorImpl implements INewsListInteractor<List<NeteastNewsSummary>> {
    @Override
    public Subscription requestNewsList(RequestCallBack<List<NeteastNewsSummary>> callBack, String type, String id, int startPage) {
        Log.e("新闻列表", "type " + type + "; id " + id);

        return null;
    }
}
