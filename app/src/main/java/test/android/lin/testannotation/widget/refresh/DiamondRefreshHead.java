package test.android.lin.testannotation.widget.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import test.android.lin.testannotation.R;

/**
 * Created by lxb on 16/5/30.
 */
public class DiamondRefreshHead extends RefreshHead {
    private Paint paint;
    private int mCubeSize;
    private int mWidth;
    private int mHeight;
    private Point mScreenSize = new Point();
    private int mTotalWidth;
    private int mFirstBottomOffset;
    private int mSecondBottomOffset;
    private int mThridBottomOffset;
    private int mForthBottomOffset;
    private int mBaseBottomOffset;
    private int mLoadingOffset;
    private ValueAnimator mLoadingAnimator;

    private int mAlpha = 255;

    public DiamondRefreshHead(Context context) {
        super(context);
        init();
    }

    public DiamondRefreshHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        //填充
        paint.setStyle(Paint.Style.FILL);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(mScreenSize);//获取屏幕高宽数据
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measureSize(widthMeasureSpec, mScreenSize.x + getPaddingLeft() + getPaddingRight());
        if (mCubeSize == 0) mCubeSize = (int) (mWidth * 1.0f / 100);
        if (mTotalWidth == 0) mTotalWidth = mCubeSize * 9;//一共九个方块
        if (mBaseBottomOffset == 0) {
            mBaseBottomOffset = mFirstBottomOffset = mSecondBottomOffset = mThridBottomOffset
                    = mForthBottomOffset = mFirstBottomOffset = mCubeSize * 14;
        }
        mHeight = measureSize(heightMeasureSpec, mCubeSize * 9 + getPaddingTop() + getPaddingBottom());
        if (mCubeSize * 9 + getPaddingTop() + getPaddingBottom() < 0)
            mHeight = 0;
        setMeasuredDimension(mWidth, mHeight);//主要就是设置尺寸长宽
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //重新绘制
        paint.setColor(ContextCompat.getColor(getContext(), R.color.material_green_a700));
        //设置颜色
        //设置alpha
        paint.setAlpha(mAlpha);
        //第一组方阵
        for (int i = 0; i < 2; i++) {
            int offset = i == 1 ? mLoadingOffset : 0;
            canvas.drawRect((mWidth - mTotalWidth) / 2 + mLoadingOffset * 2,
                    mCubeSize * 2 + mCubeSize * (i + 1) * 2 - mFirstBottomOffset + getPaddingTop() - offset,
                    (mWidth - mTotalWidth) / 2 + mLoadingOffset * 2 + mCubeSize,
                    mCubeSize * 2 + mCubeSize * (i + 1) * 2 - mFirstBottomOffset + getPaddingTop() + mCubeSize - offset,
                    paint);
        }
        canvas.drawRect((mWidth - mTotalWidth) / 2 + mCubeSize * 2 + mLoadingOffset,
                mCubeSize * 2 + mCubeSize * 2 * 2 - mFirstBottomOffset - mLoadingOffset + getPaddingTop(),
                (mWidth - mTotalWidth) / 2 + mCubeSize * 2 + mCubeSize * 5 - mLoadingOffset,
                mCubeSize * 2 + mCubeSize * 2 * 2 + mCubeSize - mFirstBottomOffset + getPaddingTop() - mLoadingOffset,
                paint);
        paint.setColor(ContextCompat.getColor(getContext(),R.color.material_purple_700));
        paint.setAlpha(mAlpha);

        for (int i = 0; i < 3; i++) {
            int offset = i == 2 ? -mLoadingOffset : 0;
            canvas.drawRect((mWidth - mTotalWidth) / 2 + mCubeSize * 2 * (i + 1) + offset,
                    mCubeSize * 2 + mCubeSize * 2 + getPaddingTop() - mSecondBottomOffset,
                    (mWidth - mTotalWidth) / 2 + mCubeSize * 2 * (i + 1) + mCubeSize + offset,
                    mCubeSize * 2 + mCubeSize * 2 + getPaddingTop() - mSecondBottomOffset + mCubeSize,
                    paint);
        }
        paint.setColor(ContextCompat.getColor(getContext(), R.color.material_deep_purple_a700));
        paint.setAlpha(mAlpha);

        for (int i = 0; i < 3; i++) {
            int offset = i == 2 ? -mLoadingOffset : 0;
            canvas.drawRect((mWidth - mTotalWidth) / 2 + mCubeSize * 2 * 4 - mLoadingOffset * 2,
                    mCubeSize * 2 + mCubeSize * 2 * i + offset + getPaddingTop() - mThridBottomOffset,
                    (mWidth - mTotalWidth) / 2 + mCubeSize * 2 * 4 + mCubeSize - mLoadingOffset * 2,
                    mCubeSize * 2 + mCubeSize * 2 * i + mCubeSize + offset + getPaddingTop() - mThridBottomOffset,
                    paint);
        }
        paint.setColor(ContextCompat.getColor(getContext(), R.color.material_deep_origin_a200));
        paint.setAlpha(mAlpha);
        for (int i = 0; i < 3; i++) {
            int offset = i == 2 ? -mLoadingOffset : 0;
            canvas.drawRect((mWidth - mTotalWidth) / 2 + offset + mCubeSize * 2 * i,
                    mCubeSize * 2 - mForthBottomOffset + getPaddingTop() + mLoadingOffset,
                    (mWidth - mTotalWidth) / 2 + offset + mCubeSize * 2 * i + mCubeSize,
                    mCubeSize * 2 - mForthBottomOffset + getPaddingTop() + mLoadingOffset + mCubeSize,
                    paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }

    private void stopAnimator() {
        if (mLoadingAnimator != null && mLoadingAnimator.isRunning()) {
            mLoadingAnimator.removeAllUpdateListeners();
            mLoadingAnimator.removeAllListeners();
            mLoadingAnimator.cancel();
        }
        mLoadingOffset = 0;
        mAlpha = 255;
    }

    private int measureSize(int measureSpec, int defaultSize) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY)
            return size;
        else if (mode == MeasureSpec.UNSPECIFIED) {
            return defaultSize;
        } else if (mode == MeasureSpec.AT_MOST)
            return Math.min(defaultSize, size);
        return size;
    }

    @Override
    public boolean isLoading() {
        return mLoadingAnimator != null && mLoadingAnimator.isRunning();
    }

    @Override
    public boolean isReadyLoad() {
        return 0 == mForthBottomOffset;
    }

    @Override
    public void performLoaded() {
        stopAnimator();
        postInvalidate();
    }

    @Override
    public void performLoading() {
        if (mLoadingAnimator != null && mLoadingAnimator.isRunning()) {
            return;
        } else if (mLoadingAnimator != null) {
            mLoadingAnimator.start();
            return;
        }
        mLoadingAnimator = new ValueAnimator();
        mLoadingAnimator.setIntValues(0, mCubeSize * 2);
        mLoadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoadingOffset = (int) animation.getAnimatedValue();
                mAlpha = (int) (255 * (1 - animation.getAnimatedFraction() / 1.5));
                postInvalidate();//在主进程以外启动
            }
        });
        mLoadingAnimator.setDuration(300);
        mLoadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLoadingAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mLoadingAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mLoadingAnimator.start();
    }

    @Override
    public void performPull(float ratio) {
        ratio = ratio - 0.9f;
        if (ratio > 1) {
            mFirstBottomOffset = mSecondBottomOffset = mThridBottomOffset = mForthBottomOffset = 0;
        } else if (mLoadingAnimator != null && mLoadingAnimator.isRunning()) {
            mFirstBottomOffset = mSecondBottomOffset = mThridBottomOffset = mForthBottomOffset = 0;
        } else if (ratio <= 0.25) {
            mFirstBottomOffset = (int) (mBaseBottomOffset * ((0.25 - ratio) * 1.0f / 0.25));
            mSecondBottomOffset = mThridBottomOffset = mForthBottomOffset = mBaseBottomOffset;
        } else if (ratio <= 0.5) {
            mSecondBottomOffset = (int) (mBaseBottomOffset * (0.5 - ratio) * 1.0f / 0.25);
            mFirstBottomOffset = 0;
            mThridBottomOffset = mForthBottomOffset = mBaseBottomOffset;
        } else if (ratio <= 0.75) {
            mThridBottomOffset = (int) (mBaseBottomOffset * (0.75 - ratio) * 1.0f / 0.25);
            mFirstBottomOffset = mSecondBottomOffset = 0;
            mForthBottomOffset = mBaseBottomOffset;
        } else if (ratio <= 1) {
            mForthBottomOffset = (int) (mBaseBottomOffset * (1 - ratio) * 1.0f / 0.25);
            mFirstBottomOffset = mSecondBottomOffset = mThridBottomOffset = 0;
        }
        postInvalidate();
    }
}
