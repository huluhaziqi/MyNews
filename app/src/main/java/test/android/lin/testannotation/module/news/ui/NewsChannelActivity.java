package test.android.lin.testannotation.module.news.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.base.BaseActivity;
import test.android.lin.testannotation.base.BaseSpacesItemDecoration;
import test.android.lin.testannotation.module.news.presenter.INewsChannelPresenter;
import test.android.lin.testannotation.module.news.presenter.INewsChannelPresenterImpl;
import test.android.lin.testannotation.module.news.view.INewsChannelView;
import test.android.lin.testannotation.util.MeasureUtil;

/**
 * Created by lxb on 16/5/25.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_news_channel,
        toolBarTitle = R.string.news_channel_manager
)

public class NewsChannelActivity extends BaseActivity<INewsChannelPresenter> implements INewsChannelView {
    @Override
    protected void initView() {
        mPresenter = new INewsChannelPresenterImpl(this);
    }

    @Override
    public void initTwoRecyclerView(List<NewsChannelTable> selectChannels, List<NewsChannelTable> unSelectedChannels) {
        final RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        recyclerView1.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this, 8)));

        //设置动画
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.getItemAnimator().setAddDuration(250);
        recyclerView1.getItemAnimator().setMoveDuration(250);
        recyclerView1.getItemAnimator().setRemoveDuration(250);
        recyclerView1.getItemAnimator().setChangeDuration(250);

    }
}
