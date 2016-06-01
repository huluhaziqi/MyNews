package test.android.lin.testannotation.module.news.presenter;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BasePresenterImpl;
import test.android.lin.testannotation.base.BaseView;
import test.android.lin.testannotation.base.INewsPresenter;
import test.android.lin.testannotation.module.news.model.INewsInteractorImpl;
import test.android.lin.testannotation.module.news.model.INewsInterator;
import test.android.lin.testannotation.module.news.view.INewsView;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class INewsPresenterImpl extends BasePresenterImpl<INewsView, List<NewsChannelTable>>
        implements INewsPresenter {
    private INewsInterator<List<NewsChannelTable>> mNewsInteractor;

    public INewsPresenterImpl(INewsView view) {
        super(view);
        mNewsInteractor = new INewsInteractorImpl();
        subscription = mNewsInteractor.operatorChannelDb(this);
        mView.initRxBusEvent();
    }

    @Override
    public void operatorChannelDb() {
        subscription = mNewsInteractor.operatorChannelDb(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void requestSuccess(List<NewsChannelTable> data) {
        mView.initViewPager(data);
    }

}
