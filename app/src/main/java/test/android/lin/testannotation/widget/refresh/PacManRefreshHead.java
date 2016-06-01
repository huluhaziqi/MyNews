package test.android.lin.testannotation.widget.refresh;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.WindowManager;

import test.android.lin.testannotation.R;
import test.android.lin.testannotation.util.MeasureUtil;

/**
 * Created by lxb on 16/5/27.
 */
public class PacManRefreshHead extends RefreshHead {

    private Paint paint;

    private int mPacManRadius;

    private int mWidth;
    private int mHeigth;

    private Point mScreenSize = new Point();

    private int mTotalLength;

    private ValueAnimator mLoadingAnimator1;
    private ValueAnimator mLoadingAnimator2;
    private ValueAnimator mLoadingAnimator3;
    private ValueAnimator mLoadingAnimator4;
    private AnimatorSet mLoadAnimatorSet;

    /**
     * @return
     */
    private float mEatAngle;
    private RectF mRectF = new RectF();
    private int mStep;
    private int mRotateSweep = -1;
    private int mBeanRadius;

    /**
     * 被吃掉的豆豆的位置
     *
     * @return
     */
    private int[] mEatBeanPos;

    public PacManRefreshHead(Context context) {
        super(context);
        init(context, null);
    }

    public PacManRefreshHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //初始化
    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);//扛锯齿，进行有力的抖动的位掩码标志
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshHead);
            paint.setColor(typedArray.getColor(R.styleable.PacManRefreshHead_paclconColor
                    , ContextCompat.getColor(context, R.color.material_black55)));
            typedArray.recycle();
        }
        paint.setStyle(Paint.Style.FILL);//画笔填充

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(mScreenSize);

        mEatBeanPos = new int[]{-1, -1, -1, -1, -1};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measureSize(widthMeasureSpec, mScreenSize.x + getPaddingLeft() + getPaddingRight());
        paint.setStrokeWidth(mWidth * 1.0f / 200);//设置画笔的空心线宽度

        mPacManRadius = (int) (mWidth * 10.f / 50);

        mTotalLength = mPacManRadius * 12;
        mBeanRadius = mPacManRadius / 3;

        mHeigth = measureSize(heightMeasureSpec, mPacManRadius * 4 + getPaddingTop() + getPaddingBottom());


        if (mPacManRadius * 4 + getPaddingBottom() + getPaddingTop() < 0)
            mHeigth = 0;
        setMeasuredDimension(mWidth, mHeigth);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    private int measureSize(int measureSpec, int defaultSize) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else if (mode == MeasureSpec.AT_MOST) {
            return Math.min(size, defaultSize);
        } else if (mode == MeasureSpec.UNSPECIFIED)
            return defaultSize;
        return size;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public boolean isLoading() {
        return false;
    }

    @Override
    public boolean isReadyLoad() {
        return false;
    }

    @Override
    public void performLoaded() {

    }

    @Override
    public void performLoading() {

    }

    @Override
    public void performPull(float v) {

    }
}
