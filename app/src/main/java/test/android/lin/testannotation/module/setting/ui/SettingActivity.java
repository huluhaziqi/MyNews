package test.android.lin.testannotation.module.setting.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import test.android.lin.testannotation.base.BaseActivity;
import test.android.lin.testannotation.module.news.ui.adapter.BasePageAdapter;
import test.android.lin.testannotation.R;

/**
 * Created by linxiaobin on 2016/5/22.
 */
public class SettingActivity extends BaseActivity {
    private NavigationView navigationView;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContentViewId = R.layout.activity_main;
        mMenuDefaultCheckedItem = R.id.action_settings;
        super.onCreate(savedInstanceState);

        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.view_page);
        List<String> title = new ArrayList<>();
        List<View> view = new ArrayList<>();
        title.add("名字");
        title.add("性别");
        title.add("住址");
        title.add("成员");
        title.add("年龄");
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
}
