package test.android.lin.testannotation.callback;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by lxb on 16/5/27.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final OnMoveAndSwipListener mAdapter;

    private boolean mIsLongPressDragEnable = true;

    public interface OnMoveAndSwipListener {
        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }

    public interface OnStateChangedListener {
        void onItemSelected();

        void onItemClear();
    }

    public SimpleItemTouchHelperCallback(OnMoveAndSwipListener listener) {
        this.mAdapter = listener;
    }

    @Override//这个 方法是用来设置我们拖动的方向以及侧滑的方向。
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;//不支持侧滑
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int draFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

            return makeMovementFlags(draFlags, swipeFlags);//设置侧滑和上下滑动
        }
    }

    public void setLongPressDragEnable(boolean enable) {//设置长按
        mIsLongPressDragEnable = enable;
    }

    @Override//拖动时候回调
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() != target.getItemViewType())
            return false;
        return mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override//侧滑时候调用的方法
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override//状态改变时候回调的方法
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //不是闲置状态的时候说明正在滑动
            if (viewHolder instanceof OnStateChangedListener) {
                OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
                listener.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    //拖拽完或者侧滑完毕之后清除施加在item上面的一些在状态
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof OnStateChangedListener) {
            OnStateChangedListener listener = (OnStateChangedListener) viewHolder;
            listener.onItemClear();
        }
    }

    @Override//判断当前是拖拽还是侧滑
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //根据侧滑的位移来改变item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }
}

