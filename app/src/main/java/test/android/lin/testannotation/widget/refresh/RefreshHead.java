package test.android.lin.testannotation.widget.refresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxb on 16/5/27.
 */
public abstract class RefreshHead extends View {
    public RefreshHead(Context context) {
        super(context);
    }

    public RefreshHead(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshHead(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract boolean isLoading();

    public abstract boolean isReadyLoad();

    public abstract void performLoaded();

    public abstract void performLoading();

    public abstract void performPull(float v);
}
