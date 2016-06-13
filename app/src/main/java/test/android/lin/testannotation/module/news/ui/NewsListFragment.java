package test.android.lin.testannotation.module.news.ui;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.socks.library.KLog;

import java.util.List;

import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.base.BaseFragment;
import test.android.lin.testannotation.base.BaseRecyclerAdapter;
import test.android.lin.testannotation.base.BaseRecyclerViewHolder;
import test.android.lin.testannotation.base.BaseSpacesItemDecoration;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.common.DataLoadType;
import test.android.lin.testannotation.module.news.presenter.INewsListPresenter;
import test.android.lin.testannotation.module.news.presenter.INewsListPresenterImpl;
import test.android.lin.testannotation.module.news.view.INewsListView;
import test.android.lin.testannotation.util.MeasureUtil;
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
        mRefreshLayout = (RefreshLayout) fragmentRootView.findViewById(R.id.refresh_layout);
        mPresenter = new INewsListPresenterImpl(this, mNewsType, mNewsId);
    }

    @Override
    public void updateNewList(List<NeteastNewsSummary> data, @DataLoadType.DataLoadTypeChecker int type) {
        Log.e("type", String.valueOf(type));
        switch (type) {
            case DataLoadType.TYPE_REFRESH_SUCCESS:
                mRefreshLayout.refreshFinish();
                KLog.e("mAdapter", mAdapter);
                if (mAdapter == null) {
                    initNewsList(data);
                }
                else {
                    mAdapter.setData(data);
                }
                if (mRecyclerView.isAllLoaded())
                    mRecyclerView.notifyAllLoaded();
                break;
        }
    }

    private void initNewsList(List<NeteastNewsSummary> data) {
        mAdapter = new BaseRecyclerAdapter<NeteastNewsSummary>(data, getActivity()) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.item_news_summary;
            }

            @Override
            public void bindData(BaseRecyclerViewHolder holder, int position, NeteastNewsSummary item) {
                Glide.with(getActivity()).load(item.imgsrc).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_loading).error(R.drawable.ic_fail)
                        .into(holder.getImageView(R.id.iv_news_summary_photo));
                KLog.e("title", item.title);
                KLog.e("digest", item.digest);
                KLog.e("ptime", item.ptime);
                holder.getTextView(R.id.tv_news_summary_title).setText(item.title);
                holder.getTextView(R.id.tv_news_summary_digest).setText(item.digest);
                holder.getTextView(R.id.tv_news_summary_ptime).setText(item.ptime);
            }


        };
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAutoLayoutManager(linearLayoutManager).setAutoHasFixedSize(true)
                .addAutoItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(getActivity(), 4)))
                .setAutoItemAnimator(new DefaultItemAnimator()).setAutoAdapter(mAdapter);

    }
}
