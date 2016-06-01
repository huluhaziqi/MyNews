package test.android.lin.testannotation.module.news.view;

import java.util.List;

import test.android.lin.testannotation.base.BaseView;
import test.android.lin.testannotation.bean.NeteastNewsSummary;
import test.android.lin.testannotation.common.DataLoadType;

/**
 * Created by lxb on 16/5/30.
 */
public interface INewsListView extends BaseView {
    void updateNewList(List<NeteastNewsSummary> data, @DataLoadType.DataLoadTypeChecker int type);
}
