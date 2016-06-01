package test.android.lin.testannotation.module.news.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import greendao.NewsChannelTable;
import test.android.lin.testannotation.R;
import test.android.lin.testannotation.base.BaseRecyclerAdapter;
import test.android.lin.testannotation.base.BaseRecyclerViewHolder;
import test.android.lin.testannotation.callback.SimpleItemTouchHelperCallback;
import test.android.lin.testannotation.callback.SimpleItemTouchHelperCallback.OnMoveAndSwipListener;


/**
 * Created by lxb on 16/5/27.
 */
public class NewsChannelAdapter extends BaseRecyclerAdapter<NewsChannelTable> implements
        SimpleItemTouchHelperCallback.OnMoveAndSwipListener {
    private SimpleItemTouchHelperCallback mSimpleItemTouchHelperCallback;
    private OnMoveAndSwipListener onMoveAndSwipListener;

    private OnItemMoveListener onItemMoveListener;

    @Override

    public int getItemLayoutId(int viewType) {
        return R.layout.item_news_channel;
    }

    @Override
    public NewsChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new NewsChannelHolder(mInflater.inflate(R.layout.item_footer, parent, false), mContext);

        } else {
            final NewsChannelHolder holder = new NewsChannelHolder(mInflater.
                    inflate(getItemLayoutId(viewType), parent, false), mContext);
            if (mClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.onItemClick(v, holder.getLayoutPosition());
                    }
                });
            }
            if (mSimpleItemTouchHelperCallback != null) {//设置是否可以长按拖拽
                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mData.get(holder.getLayoutPosition()).getNewsChannelFixed()) {
                            //固定的不让拖拽
                            mSimpleItemTouchHelperCallback.setLongPressDragEnable(false);
                            return true;
                        } else {
                            mSimpleItemTouchHelperCallback.setLongPressDragEnable(true);
                        }
                        return false;
                    }
                });
            }
            return holder;
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //前三项数据不能被交换
        if (!mData.get(fromPosition).getNewsChannelFixed() && !mData.get(toPosition).getNewsChannelFixed()) {
            Collections.swap(mData, fromPosition, toPosition);//交换二者
            notifyItemMoved(fromPosition, toPosition);//更新交换
            if (onItemMoveListener != null) {
                onItemMoveListener.onItemMove(fromPosition, toPosition);
            }
            return true;
        }

        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);

    }

    public void setItemTouchHelper(SimpleItemTouchHelperCallback callback) {
        mSimpleItemTouchHelperCallback = callback;
    }

    public void setOnItemMoveListener(OnItemMoveListener listener) {
        this.onItemMoveListener = listener;
    }

    private class NewsChannelHolder extends BaseRecyclerViewHolder
            implements SimpleItemTouchHelperCallback.OnStateChangedListener {
        public NewsChannelHolder(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        public void onItemSelected() {
            itemView.setEnabled(false);//改变背景颜色
        }

        @Override
        public void onItemClear() {
            itemView.setEnabled(true);
            //恢复背景颜色

        }
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, NewsChannelTable item) {
        holder.getTextView(R.id.tv_news_channel).setText(item.getNewsChannelName());
        holder.getTextView(R.id.tv_news_channel).setSelected(item.getNewsChannelFixed());//设置时候选中等
    }


    public NewsChannelAdapter(List<NewsChannelTable> mData, Context mContext) {
        super(mData, mContext);
    }

    public NewsChannelAdapter(Context mContext, List<NewsChannelTable> mData, boolean mUseAnimation) {
        super(mContext, mData, mUseAnimation);
    }

    public NewsChannelAdapter(Context mContext, List<NewsChannelTable> mData, boolean mUseAnimation, RecyclerView.LayoutManager mLayoutManager) {
        super(mContext, mData, mUseAnimation, mLayoutManager);
    }

    public interface OnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }
}
