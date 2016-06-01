package test.android.lin.testannotation.module.news.presenter;

import android.util.Log;

import java.util.List;
import java.util.Map;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BasePresenterImpl;
import test.android.lin.testannotation.module.news.model.INewsChannelInteractor;
import test.android.lin.testannotation.module.news.model.INewsChannelInteractorImpl;
import test.android.lin.testannotation.module.news.view.INewsChannelView;
import test.android.lin.testannotation.util.RxBus;

/**
 * Created by lxb on 16/5/25.
 */
public class INewsChannelPresenterImpl extends BasePresenterImpl<INewsChannelView, Map<Boolean, List<NewsChannelTable>>>
        implements INewsChannelPresenter {
    private INewsChannelInteractor<Map<Boolean, List<NewsChannelTable>>> mNewsChannelIterator;

    private boolean mChannelChange;


    public INewsChannelPresenterImpl(INewsChannelView view) {
        super(view);
        mNewsChannelIterator = new INewsChannelInteractorImpl();
        subscription = mNewsChannelIterator.channelDbOperator(this, "", null);
    }

    @Override
    public void onItemAddOrRemove(String channelName, boolean selectState) {
        mChannelChange = true;
        subscription = mNewsChannelIterator.channelDbOperator(this, channelName, selectState);
    }

    @Override
    public void onItemSwap(int fromPos, int toPos) {
        mChannelChange = true;
        subscription = mNewsChannelIterator.channelDbSwap(this, fromPos, toPos);
    }

    @Override
    public void onDestroy() {
        //ADD-TODO
        Log.e("Tag", "onDestroy");
        RxBus.get().post("channelChange", mChannelChange);//发布channelChange
        super.onDestroy();
    }

    @Override
    public void requestSuccess(Map<Boolean, List<NewsChannelTable>> data) {
        mView.initTwoRecyclerView(data.get(true), data.get(false));
    }
}
