package test.android.lin.testannotation.module.news.view;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BaseView;

/**
 * Created by linxiaobin on 2016/5/24.
 */
public interface INewsView extends BaseView {
    void initViewPager(List<NewsChannelTable> newsChannelTables);

    void initRxBusEvent();
}
