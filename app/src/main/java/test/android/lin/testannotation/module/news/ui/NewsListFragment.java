package test.android.lin.testannotation.module.news.ui;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.base.BaseFragment;
import test.android.lin.testannotation.base.BaseRecyclerAdapter;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.common.DataLoadType;
import test.android.lin.testannotation.module.news.presenter.INewsListPresenter;
import test.android.lin.testannotation.module.news.view.INewsListView;
import test.android.lin.testannotation.widget.AutoLoadMoreRecyclerView;
import test.android.lin.testannotation.widget.refresh.RefreshLayout;

/**
 * Created by lxb on 16/5/30.
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list, handleRefreshLayout = true)
public class NewsListFragment extends BaseFragment<INewsListPresenter> implements INewsListView {
    protected static final String NEWS_ID = "news_id";
    protected static final String NEWS_TYPE = "news_type";
    protected static final String POSITION = "position";
    protected String mNewsId;
    protected String mNewsType;

    private BaseRecyclerAdapter<NeteastNewsSummary> mAdapter;
    private AutoLoadMoreRecyclerView mRecyclerView;
    private RefreshLayout mRefreshLayout;
    private View mLoadingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(NEWS_ID);
            mNewsType = getArguments().getString(NEWS_TYPE);
            mPosition = getArguments().getInt(POSITION);
        }
    }

    public static NewsListFragment newInstance(String newsId, String newsType, int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, newsId);
        bundle.putString(NEWS_TYPE, newsType);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }//新建实例

    @Override
    protected void initView(View fragmentRootView) {
        mRecyclerView = (AutoLoadMoreRecyclerView) fragmentRootView.findViewById(R.id.recycler_view);
        mRefreshLayout = (RefreshLayout)fragmentRootView.findViewById(R.id.refresh_layout);
        mPresenter = new INE
    }

    @Override
    public void updateNewList(List<NeteastNewsSummary> data, @DataLoadType.DataLoadTypeChecker int type) {

    }
}
