package test.android.lin.testannotation.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import test.android.lin.testannotation.R;
import test.android.lin.testannotation.callback.OnItemClickListener;

/**
 * Created by lxb on 16/5/26.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


    public static final int TYPE_FOOTER = 3;
    protected List<T> mData;
    protected Context mContext;
    protected boolean mUseAnimation;
    protected LayoutInflater mInflater;
    protected OnItemClickListener mClickListener;
    protected boolean isShowFooter;

    private RecyclerView.LayoutManager mLayoutManager;

    private int mLastPosition = -1;

    public BaseRecyclerAdapter(List<T> mData, Context mContext) {
        this(mContext, mData, true);
    }

    public BaseRecyclerAdapter(Context mContext, List<T> mData, boolean mUseAnimation) {
        this(mContext, mData, mUseAnimation, null);
    }

    public BaseRecyclerAdapter(Context mContext, List<T> mData, boolean mUseAnimation, RecyclerView.LayoutManager mLayoutManager) {
        this.mContext = mContext;
        this.mUseAnimation = mUseAnimation;
        this.mLayoutManager = mLayoutManager;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = mData == null ? (new ArrayList<T>()) : mData;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new BaseRecyclerViewHolder(mInflater.inflate(R.layout.item_footer, parent, false), mContext);
        } else {
            final BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(mInflater.inflate(getItemLayoutId(viewType),
                    parent, false), mContext);
            if (mClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.onItemClick(v, holder.getLayoutPosition());
                    }
                });
            }
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (mLayoutManager != null) {
                if (mLayoutManager instanceof StaggeredGridLayoutManager)
                    if (((StaggeredGridLayoutManager) mLayoutManager).getSpanCount() != 1) {
                        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.
                                getLayoutParams();
                        params.setFullSpan(true);
                    } else if (mLayoutManager instanceof GridLayoutManager) {
                        if (((GridLayoutManager) mLayoutManager).getSpanCount() != 1
                                && ((GridLayoutManager) mLayoutManager).getSpanSizeLookup()
                                instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                            throw new RuntimeException("占满一行");
                        }
                    }
            }

        } else {
            bindData(holder, position, mData.get(position));
            if (mUseAnimation) {
                //TODO
            }
        }
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract void bindData(BaseRecyclerViewHolder holder, int position, T item);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public List<T> getData() {
        return mData;
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }
}
