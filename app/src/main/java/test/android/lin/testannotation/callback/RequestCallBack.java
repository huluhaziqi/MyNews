package test.android.lin.testannotation.callback;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public interface RequestCallBack<T> {


    void requestSuccess(T data);

    void requestCompleted();

    void beforeRequest();

    void requsetError(String data);
}
