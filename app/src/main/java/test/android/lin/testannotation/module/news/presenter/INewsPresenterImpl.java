package test.android.lin.testannotation.module.news.presenter;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BasePresenterImpl;
import test.android.lin.testannotation.base.BaseView;
import test.android.lin.testannotation.base.INewsPresenter;
import test.android.lin.testannotation.module.news.view.INewsView;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class INewsPresenterImpl extends BasePresenterImpl<INewsView, List<NewsChannelTable>> implements INewsPresenter {
    public INewsPresenterImpl(INewsView view) {
        super(view);
    }

    @Override
    public void operatorChannelDb() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
