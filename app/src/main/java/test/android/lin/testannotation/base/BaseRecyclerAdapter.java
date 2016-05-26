package test.android.lin.testannotation.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import test.android.lin.testannotation.callback.OnItemClickListener;

/**
 * Created by lxb on 16/5/26.
 */
public class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {


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





}
