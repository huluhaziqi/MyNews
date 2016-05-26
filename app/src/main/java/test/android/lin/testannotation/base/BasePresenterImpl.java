package test.android.lin.testannotation.base;

import rx.Subscription;
import test.android.lin.testannotation.callback.RequestCallBack;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public class BasePresenterImpl<T extends BaseView, V> implements BasePresenter, RequestCallBack<V> {
    protected  Subscription subscription;
    protected T mView;

    public BasePresenterImpl(T view) {
        this.mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void requestSuccess(V data) {

    }

    @Override
    public void requestCompleted() {

    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void requsetError(String data) {

    }

    @Override
    public void onDestroy() {

    }
}
