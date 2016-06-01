package test.android.lin.testannotation.base;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lxb on 16/5/26.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    protected SparseArray<View> mViews;
    protected Context mContext;


    public BaseRecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;
        mViews = new SparseArray<>();
    }

    private <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) findViewById(viewId);
    }
}
