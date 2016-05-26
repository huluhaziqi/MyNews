package test.android.lin.testannotation.module.news.view;

import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.base.BaseView;

/**
 * Created by lxb on 16/5/25.
 */
public interface INewsChannelView extends BaseView {

    void initTwoRecyclerView(List<NewsChannelTable> selectChannels, List<NewsChannelTable> unSelectedChannels);
}
