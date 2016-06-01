package test.android.lin.testannotation.module.news.presenter;

import java.util.List;

import test.android.lin.testannotation.base.BasePresenterImpl;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.module.news.presenter.INewsListPresenter;
import test.android.lin.testannotation.module.news.view.INewsListView;

/**
 * Created by lxb on 16/5/31.
 */
public class INewsListPresenterImpl
        extends BasePresenterImpl<INewsListView,List<NeteastNewsSummary>>
    implements INewsListPresenter{
    public INewsListPresenterImpl(INewsListView view) {
        super(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void requestSuccess(List<NeteastNewsSummary> data) {
        super.requestSuccess(data);
    }

    @Override
    public void requestCompleted() {
        super.requestCompleted();
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void requsetError(String data) {
        super.requsetError(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void loadMoreData() {

    }
}
