package test.android.lin.testannotation.module.news.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;


import greendao.NewsChannelTable;
import rx.functions.Action1;
import test.android.lin.testannotation.base.BaseActivity;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.base.BaseFragment;
import test.android.lin.testannotation.base.BaseFragmentAdapter;
import test.android.lin.testannotation.base.INewsPresenter;
import test.android.lin.testannotation.module.news.presenter.INewsPresenterImpl;
import test.android.lin.testannotation.module.news.ui.adapter.BasePageAdapter;
import test.android.lin.testannotation.module.news.view.INewsView;
import test.android.lin.testannotation.util.RxBus;

@ActivityFragmentInject(contentViewId = R.layout.activity_main,
        menuId = R.menu.menu_news, enableSlider = true, hasNavigationView = true, toolBarTitle = 2, toolBatIndicate = 1, menuDefaultCheckItem = 0)

public class MainActivity extends BaseActivity<INewsPresenter> implements INewsView {
    private NavigationView navigationView;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private INewsPresenterImpl iNewsPresenter;

    private rx.Observable<Boolean> mChannelObservable;

    @Override
    protected void initView() {
        super.initView();
        mPresenter = new INewsPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister("channelChange", mChannelObservable);
    }

    @Override
    public void initViewPager(List<NewsChannelTable> newsChannelTables) {
        mContentViewId = R.layout.activity_main;
        mMenuDefaultCheckedItem = R.id.action_news;
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_page);
        List<String> title = new ArrayList<>();
        List<View> view = new ArrayList<>();
        List<BaseFragment> fragments = new ArrayList<>();
        if (newsChannelTables != null) {
            for (NewsChannelTable table : newsChannelTables) {
                title.add(table.getNewsChannelName());
                final NewsListFragment fragment = NewsListFragment.newInstance(table.getNewsChannelId()
                        , table.getNewsChannelType(), table.getNewsChannelIndex());
                fragments.add(fragment);
            }
        }
//        for (int i = 0; i < title.size(); i++) {
//            TextView textview = new TextView(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            textview.setLayoutParams(params);
//            textview.setText("内容是" + i);
//            textview.setGravity(Gravity.CENTER);
//            view.add(textview);
//        }
        if (viewPager.getAdapter() == null) {
            KLog.e("adapter为空");
            BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                    fragments, title);
            viewPager.setAdapter(adapter);
        } else {
            KLog.e("adapter不为空");
            final BaseFragmentAdapter adapter = (BaseFragmentAdapter) viewPager.getAdapter();
            adapter.updateFragment(fragments, title);
        }
        viewPager.setCurrentItem(0, false);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setScrollPosition(0, 0, true);
//        setOnTabSelectEvent(viewPager, tabLayout);
//        setOnT
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_channel_manager)
            this.startActivity(new Intent(this, NewsChannelActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initRxBusEvent() {
        mChannelObservable = RxBus.get().register("channelChange", Boolean.class);
        Log.e("Tag", "initRxBusEvent");
        mChannelObservable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.e("Tag", aBoolean.toString());
                if (aBoolean) {
                    mPresenter.operatorChannelDb();
                }
            }
        });
    }
}
