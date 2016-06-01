package test.android.lin.testannotation.base;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by lxb on 16/5/26.
 */
public class BaseSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public BaseSpacesItemDecoration(int mSpace) {
        this.mSpace = mSpace;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        outRect.left = mSpace;
        outRect.right = mSpace;

        outRect.top = 0;
        outRect.bottom = mSpace;
        if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
            if (position < ((StaggeredGridLayoutManager) parent.getLayoutManager()).getSpanCount()) {
                outRect.top = mSpace;
            }
        } else if (parent.getLayoutManager() instanceof GridLayoutManager) {
            if (position < ((GridLayoutManager) parent.getLayoutManager()).getSpanCount()) {
                outRect.top = mSpace;
            }
        } else if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            if (position == 0)
                outRect.top = mSpace;
        }
    }
}
