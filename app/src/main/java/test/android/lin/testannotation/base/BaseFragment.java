package test.android.lin.testannotation.base;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import rx.Observable;
import test.android.lin.testannotation.annomation.ActivityFragmentInject;
import test.android.lin.testannotation.app.App;

/**
 * Created by lxb on 16/5/30.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView, View.OnClickListener {

    protected int mPosition;


    protected T mPresenter;

    protected View mFragmentRootView;
    protected int mContentViewId;

    private boolean mlsStop;

    private Observable<Object> mAppbarOffsetObservable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mFragmentRootView) {
            //如果inject了则做相应的处理
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {

                ActivityFragmentInject annotation = getClass().getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();

            } else {
                throw new RuntimeException("Class must add annotations of ActivityFragmentParams.class");
            }
            mFragmentRootView = inflater.inflate(mContentViewId, container, false);
            //TODO
            initView(mFragmentRootView);
        }
        return mFragmentRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup) mFragmentRootView.getParent();
        if (null != parent)
            parent.removeView(mFragmentRootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected abstract void initView(View fragmentRootView);

    public BaseFragment() {
    }

    @Override
    public void toast(String text) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {

    }
}
