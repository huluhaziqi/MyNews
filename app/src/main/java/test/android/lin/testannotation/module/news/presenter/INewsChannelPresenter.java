package test.android.lin.testannotation.module.news.presenter;

import test.android.lin.testannotation.base.BasePresenter;

/**
 * Created by lxb on 16/5/25.
 */
public interface INewsChannelPresenter extends BasePresenter {
    void onItemAddOrRemove(String channelName, boolean selectState);

    void onItemSwap(int fromPos, int toPos);
}
