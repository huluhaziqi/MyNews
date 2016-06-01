package test.android.lin.testannotation.module.news.presenter;

import test.android.lin.testannotation.base.BasePresenter;

/**
 * Created by lxb on 16/5/30.
 */
public interface INewsListPresenter extends BasePresenter {
    void refreshData();

    void loadMoreData();
}
