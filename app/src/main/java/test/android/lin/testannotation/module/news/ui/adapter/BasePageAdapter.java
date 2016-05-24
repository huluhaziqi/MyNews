package test.android.lin.testannotation.module.news.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by linxiaobin on 2016/5/22.
 */
public class BasePageAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return view.size();
    }

    List<View> view;
    private List<String> mTitles;

    public BasePageAdapter(List<View> view, List<String> mTitles) {
        this.view = view;
        this.mTitles = mTitles;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View views = view.get(position);
        container.addView(views);
        return views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(view.get(position));
    }
}
