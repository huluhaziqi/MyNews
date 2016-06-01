package test.android.lin.testannotation.module.news.ui;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.base.BaseActivity;
import test.android.lin.testannotation.base.BaseSpacesItemDecoration;
import test.android.lin.testannotation.callback.OnItemClickAdapter;
import test.android.lin.testannotation.callback.SimpleItemTouchHelperCallback;
import test.android.lin.testannotation.module.news.presenter.INewsChannelPresenter;
import test.android.lin.testannotation.module.news.presenter.INewsChannelPresenterImpl;
import test.android.lin.testannotation.module.news.ui.adapter.NewsChannelAdapter;
import test.android.lin.testannotation.module.news.view.INewsChannelView;
import test.android.lin.testannotation.util.MeasureUtil;

/**
 * Created by lxb on 16/5/25.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_news_channel,
        menuId = R.menu.menu_news,
        enableSlider = true,
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
//        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this, 8)));


        //设置动画
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.getItemAnimator().setAddDuration(250);
        recyclerView1.getItemAnimator().setMoveDuration(250);
        recyclerView1.getItemAnimator().setRemoveDuration(250);
        recyclerView1.getItemAnimator().setChangeDuration(250);

        for (int i = 0; i < selectChannels.size(); i++) {
            Log.e("tag", selectChannels.get(i).toString());
        }
        final NewsChannelAdapter mRecyclerAdapter1 = new NewsChannelAdapter(this, selectChannels, false);

        recyclerView1.setAdapter(mRecyclerAdapter1);
        //设置adapter

        SimpleItemTouchHelperCallback callback1 = new SimpleItemTouchHelperCallback(mRecyclerAdapter1);
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(callback1);
        //SimpleItemTouchHelperCallback继承自 ItemTouchHelper.callback；
        //将ItemTouchHelper绑定到RecyclerView上面
        itemTouchHelper1.attachToRecyclerView(recyclerView1);
        mRecyclerAdapter1.setItemTouchHelper(callback1);//设置Touch callback

        mRecyclerAdapter1.setOnItemMoveListener(new NewsChannelAdapter.OnItemMoveListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                //拖拽交换的时候通知代理更新数据库
                mPresenter.onItemSwap(fromPosition, toPosition);
            }
        });//设置相应的move监听

        final RecyclerView mRecyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        //设置分割线
        mRecyclerView2.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(this, 8)));
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView2.getItemAnimator().setMoveDuration(250);
        mRecyclerView2.getItemAnimator().setChangeDuration(250);
        mRecyclerView2.getItemAnimator().setRemoveDuration(250);
        mRecyclerView2.getItemAnimator().setAddDuration(250);
        //设置动画
        final NewsChannelAdapter adapter2 = new NewsChannelAdapter(this, unSelectedChannels, false);
        mRecyclerView2.setAdapter(adapter2);

        //设置监听，进行相应的增删操作

        mRecyclerAdapter1.setOnItemClickListener(new OnItemClickAdapter() {
            @Override
            public void onItemLongClick(View view, int position) {
                super.onItemLongClick(view, position);
            }

            @Override
            public void onItemClick(View view, int position) {
                if (!mRecyclerAdapter1.getData().get(position).getNewsChannelFixed()) {
                    //不是固定的就删除
                    mPresenter.onItemAddOrRemove(mRecyclerAdapter1.getData().get(position).getNewsChannelName(), false);
                    //删除
                    adapter2.add(adapter2.getItemCount(), mRecyclerAdapter1.getData().get(position));

                    mRecyclerAdapter1.delete(position);
                }
            }
        });

        adapter2.setOnItemClickListener(new OnItemClickAdapter() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onItemAddOrRemove(adapter2.getData().get(position).getNewsChannelName(), true);
                mRecyclerAdapter1.add(mRecyclerAdapter1.getItemCount(), adapter2.getData().get(position));
                adapter2.delete(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                super.onItemLongClick(view, position);
            }
        });

    }
}
