package test.android.lin.testannotation.module.news.presenter;

import android.widget.TableRow;

import com.socks.library.KLog;

import java.util.List;

import test.android.lin.testannotation.base.BasePresenterImpl;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.common.DataLoadType;
import test.android.lin.testannotation.module.news.model.INewsListInteractor;
import test.android.lin.testannotation.module.news.model.INewsListInteractorImpl;
import test.android.lin.testannotation.module.news.presenter.INewsListPresenter;
import test.android.lin.testannotation.module.news.view.INewsListView;

/**
 * Created by lxb on 16/5/31.
 */
public class INewsListPresenterImpl
        extends BasePresenterImpl<INewsListView, List<NeteastNewsSummary>>
        implements INewsListPresenter {


    private INewsListInteractor<List<NeteastNewsSummary>> mNewsListInteractor;
    private String mNewsType;
    private String mNewsId;
    private int mStartPage;
    private boolean mIsRefresh = true;
    private boolean mHashInit;

    public INewsListPresenterImpl(INewsListView view, String mNewsType, String mNewsId) {
        super(view);
        this.mNewsListInteractor = new INewsListInteractorImpl();
        this.mNewsType = mNewsType;
        this.mNewsId = mNewsId;
        subscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    public INewsListPresenterImpl(INewsListView view) {
        super(view);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void requestSuccess(List<NeteastNewsSummary> data) {
        KLog.e("请求成功");
        mHashInit = true;
        if (data != null) {
            mStartPage += 20;
        }
        mView.updateNewList(data, mIsRefresh ? DataLoadType.TYPE_REFRESH_SUCCESS : DataLoadType.TYPE_LOAD_MORE_SUCCESS);
    }

    @Override
    public void requestCompleted() {
        super.requestCompleted();
    }

    @Override
    public void beforeRequest() {
        if (!mHashInit) {
            mView.showProgress();
        }
    }

    @Override
    public void requsetError(String data) {
        super.requsetError(data);
        mView.updateNewList(null, mIsRefresh ? DataLoadType.TYPE_REFRESH_FAIL : DataLoadType.TYPE_LOAD_MORE_FAIL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void refreshData() {
        mStartPage = 0;
        mIsRefresh = true;
        subscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void loadMoreData() {
        mIsRefresh = false;
        subscription = mNewsListInteractor.requestNewsList(this, mNewsType, mNewsId, mStartPage);
    }
}
