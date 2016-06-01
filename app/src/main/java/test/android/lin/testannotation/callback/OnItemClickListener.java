package test.android.lin.testannotation.callback;

import android.view.View;

/**
 * Created by lxb on 16/5/26.
 */
public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
