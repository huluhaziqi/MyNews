package test.android.lin.testannotation.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by lxb on 16/5/30.
 */
public class AutoLoadMoreRecyclerView extends RecyclerView {
    public static final int STATE_MORE_LOADING = 1;
    public static final int STATE_MORE_LOADED = 2;
    public static final int STATE_ALL_LOADED = 3;

    private int[] mVisiblePositions;

    private int mCurrentState = STATE_MORE_LOADED;

    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public AutoLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoLoadMoreRecyclerView(Context context) {
        super(context);
    }

    public AutoLoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurrentState == STATE_MORE_LOADED && calculateRecyclerViewFirstPosition() == getAdapter()
                        .getItemCount() - 1 && onLoadMoreListener != null) {
                    Log.e("tag", "加载更多数据");
                    onLoadMoreListener.loadMore();
                    mCurrentState = STATE_MORE_LOADING;//加载更多数据中
                }
            }
        });
    }

    public boolean isMoreLoading() {
        return mCurrentState == STATE_MORE_LOADING;
    }

    public boolean isMoreLoaded() {
        return mCurrentState == STATE_MORE_LOADED;
    }

    public boolean isAllLoaded() {
        return mCurrentState == STATE_ALL_LOADED;
    }


    public void notifyMoreLoaded() {
        mCurrentState = STATE_MORE_LOADED;
    }

    public void notifyAllLoaded() {
        mCurrentState = STATE_ALL_LOADED;
    }

    //计算RecyclerView当前第一个完全可视位置。
    private int calculateRecyclerViewFirstPosition() {
        if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //瀑布流
            if (mVisiblePositions == null) {
                mVisiblePositions = new int[((StaggeredGridLayoutManager) getLayoutManager()).getSpanCount()];
            }//分栏的项目
            ((StaggeredGridLayoutManager) getLayoutManager())
                    .findFirstCompletelyVisibleItemPositions(mVisiblePositions);
            int max = -1;
            for (int pos : mVisiblePositions) {
                max = Math.max(max, pos);
            }
            return max;
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            return ((GridLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } else
            return ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();

    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.onLoadMoreListener = loadMoreListener;
    }

    public AutoLoadMoreRecyclerView setAutoLayoutManager(LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        return this;
    }

    public AutoLoadMoreRecyclerView addAutoItemDecoration(ItemDecoration itemDecoration) {
        super.addItemDecoration(itemDecoration);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoItemAnimator(ItemAnimator animator) {
        super.setItemAnimator(animator);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoHasFixedSize(boolean fixed) {
        super.setHasFixedSize(fixed);//保持固定的大小
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoItemAnimatorDuration(int duration) {
        super.getItemAnimator().setAddDuration(duration);
        super.getItemAnimator().setRemoveDuration(duration);
        super.getItemAnimator().setChangeDuration(duration);
        super.getItemAnimator().setMoveDuration(duration);
        return this;
    }

}
