package test.android.lin.testannotation.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by linxiaobin on 2016/6/6.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager mFragmentManager, List<BaseFragment> mFragments, List<String> mTitles) {
        super(mFragmentManager);
        this.mFragmentManager = mFragmentManager;
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    public void updateFragment(List<BaseFragment> fragments, List<String> titles) {
        for (int i = 0; i < mFragments.size(); i++) {
            final BaseFragment fragment = mFragments.get(i);
            final FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (i > 2) {
                ft.remove(fragment);
                mFragments.remove(i);
                i--;
            }
            ft.commit();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (i > 2) {
                mFragments.add(fragments.get(i));
            }
        }
        mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
