package test.android.lin.testannotation.module.news.ui;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BaseActivity;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.module.news.presenter.INewsPresenterImpl;
import test.android.lin.testannotation.module.news.ui.adapter.BasePageAdapter;
import test.android.lin.testannotation.module.news.view.INewsView;

@ActivityFragmentInject(contentViewId = R.layout.activity_main,
        menuId = R.menu.menu_news, enableSlider = true, hasNavigationView = true, toolBarTitle = 2, toolBatIndicate = 1, menuDefaultCheckItem = 0)

public class MainActivity extends BaseActivity implements INewsView {
    private NavigationView navigationView;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private INewsPresenterImpl iNewsPresenter;
    @Override
    protected void initView() {
        super.initView();
        iNewsPresenter = new INewsPresenterImpl(this);
    }

    @Override
    public void initViewPager(List<NewsChannelTable> newsChannelTables) {
        mContentViewId = R.layout.activity_main;
        mMenuDefaultCheckedItem = R.id.action_news;
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_page);
        List<String> title = new ArrayList<>();
        List<View> view = new ArrayList<>();
        if(newsChannelTables!=null){
            for(NewsChannelTable table : newsChannelTables){
                title.add(table.getNewsChannelName());
            }
        }
        for (int i = 0; i < title.size(); i++) {
            TextView textview = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textview.setLayoutParams(params);
            textview.setText("内容是" + i);
            textview.setGravity(Gravity.CENTER);
            view.add(textview);
        }
        BasePageAdapter basePageAdapter = new BasePageAdapter(view, title);
        viewPager.setAdapter(basePageAdapter);
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_channel_manager)
            this.startActivity(new Intent(this,NewsChannelActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initRxBusEvent() {

    }
}
