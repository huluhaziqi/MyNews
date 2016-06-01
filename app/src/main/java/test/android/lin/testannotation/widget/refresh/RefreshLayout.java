package test.android.lin.testannotation.widget.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import test.android.lin.testannotation.R;

/**
 * Created by lxb on 16/5/30.
 */
public class RefreshLayout extends RelativeLayout {

    //隐藏的状态
    private static final int HIDE = 0;
    //下拉刷新的状态
    private static final int PULL_TO_REFRESH = 1;
    //松开刷新的状态
    private static final int RELEASE_TO_REFRESH = 2;
    //正在刷新的状态
    private static final int REFRESHING = 3;

    //当前的状态

    private int mCurrentState = HIDE;

    //头部的高度
    private int mHeaderViewHeight;
    //内容view
    private View mContentView;
    //内容为RecyclerView
    private RecyclerView mRecyclerView;
    //内容的第一个可视位置
    private int mFirstPosition = -1;

    //可视位置数组
    private int mVisiblePosition[];

    //头部
    private RefreshHead mRefreshHead;

    //控制头部显示移除的动画

    private ValueAnimator mControlHeadAnimator;
    //移动的距离与头部高度的比值

    private float mRatio;

    //最小的移动至
    private int mTouchSlop;

    //移动的Y坐标距离
    private float mMoveY;
    //按下时候的Y坐标距离，作为移动距离参考的基点
    private float mDownY;

    //头部刚开始在投不刷新往下滑动的标志位
    private boolean mIsLoadingPullDown;

    private boolean mIsPostDown;

    private boolean mIsDownAction;

    private float mRecordY;
    private long mOnTouchTime;
    private boolean mMonitorClick;

    private boolean mRefreshable = true;
    private OnRefreshListener listener;

    public interface OnRefreshListener {
        void onRefreshing();
    }

    public RefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    private void init(Context context) {
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        mRefreshHead = new DiamondRefreshHead(context);
        mRefreshHead.setId(R.id.refresh_head);
        addView(mRefreshHead);
        measureHeadHeight();
    }

    //刷新头部的高宽
    private void measureHeadHeight() {
        mRefreshHead.getViewTreeObserver().addOnGlobalLayoutListener(//注册一个回调函数，当视图树全局布局发生改变或者视图数的某个视图的可是状态改变时候调用
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mRefreshHead.getMeasuredHeight();
                        mRefreshHead.setPadding(0, -mHeaderViewHeight, 0, 0);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            mRefreshHead.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        } else
                            mRefreshHead.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    }
                });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mRefreshable) {
            mDownY = ev.getY();
            return super.dispatchTouchEvent(ev);
        }
        calculateRecyclerViewFirstPosition();

        mRecordY = ev.getY();//记录Y坐标
        if (mFirstPosition != 0) {
            mIsPostDown = false;
            if (mControlHeadAnimator == null || !mControlHeadAnimator.isRunning()) {
                mDownY = ev.getY();
                if (mRefreshHead.isLoading()) {
                    mIsLoadingPullDown = false;
                }
                mIsDownAction = false;
            }
            return super.dispatchTouchEvent(ev);
        }
        if (mMonitorClick) {
            //模拟点击事件
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                ev.setAction(MotionEvent.ACTION_UP);
            }
            mMonitorClick = false;
            refreshFinish();
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOnTouchTime = System.currentTimeMillis();
                mDownY = ev.getY();
                mMoveY = 0;
                mIsDownAction = true;
                if (mRefreshHead.isLoading() && mRefreshHead.getPaddingTop() > -mHeaderViewHeight
                        || (mControlHeadAnimator != null && mControlHeadAnimator.isRunning())) {
                    mIsLoadingPullDown = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY() - mDownY;
                if (mControlHeadAnimator != null && mControlHeadAnimator.isRunning()) {
                    return true;
                }
                if (mMoveY > 0) {
                    if (mRefreshHead.isReadyLoad() && mMoveY * 0.35f > mHeaderViewHeight)//拉动超过一定距离的时候才刷新
                    {
                        mCurrentState = RELEASE_TO_REFRESH;
                    } else {
                        mCurrentState = PULL_TO_REFRESH;//还没准备好，为下拉刷新
                    }

                    if (mIsLoadingPullDown) {
                        mRefreshHead.setPadding(0, (int) (mMoveY * 0.35f), 0, 0);
                    } else {
                        mRefreshHead.setPadding(0, (int) (-mHeaderViewHeight + mMoveY * 0.35), 0, 0);
                        mRatio = mMoveY * 1.0f / 2.5f / mHeaderViewHeight;
                        if (!mRefreshHead.isLoading()) mRefreshHead.performPull(mRatio);//执行下拉效果
                    }
                    return true;
                } else if (mMoveY < 0) {
                    //往上滑动
                    if (mIsLoadingPullDown && mIsDownAction &&
                            mRefreshHead.getPaddingTop() > -mHeaderViewHeight
                            && mRefreshHead.isLoading()) {
                        mRefreshHead.setPadding(0, (int) (0 + mMoveY * 0.35f), 0, 0);
                        mCurrentState = PULL_TO_REFRESH;
                        return true;
                    } else {
                        mCurrentState = HIDE;
                    }
                    if (!mIsPostDown) {
                        ev.setAction(MotionEvent.ACTION_DOWN);
                        mIsPostDown = true;
                    } else {
                        ev.setAction(MotionEvent.ACTION_MOVE);
                    }
                    if (mRefreshHead.getPaddingTop() > -mHeaderViewHeight) {
                        mRefreshHead.setPadding(0, -mHeaderViewHeight, 0, 0);
                    }
                    return super.dispatchTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsPostDown = false;
                mIsLoadingPullDown = false;
                mIsDownAction = false;

                if (System.currentTimeMillis() - mOnTouchTime <= 1000 && Math.abs(mMoveY) < mTouchSlop / 2) {
                    mMoveY = 0;
                    mMonitorClick = true;
                    ev.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(ev);
                    return true;
                }
                mMoveY = 0;
                if (mControlHeadAnimator != null && mControlHeadAnimator.isRunning()
                        || mCurrentState == HIDE || mRefreshHead.getPaddingTop() == -mHeaderViewHeight) {
                    return super.onTouchEvent(ev);
                }
                //控制头部的动画
                if (mControlHeadAnimator == null) {
                    mControlHeadAnimator = new ValueAnimator();
                    mControlHeadAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mRefreshHead.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                            if (mCurrentState == PULL_TO_REFRESH) {
                                mRefreshHead.performPull(mRatio * (1 - animation.getAnimatedFraction()));
                            }
                        }
                    });
                    mControlHeadAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            if (mCurrentState == RELEASE_TO_REFRESH && mRefreshHead.isReadyLoad()) {
                                //释放刷新，头部开启刷新动画
                                mRefreshHead.performLoading();
                                mCurrentState = REFRESHING;
                                if (listener != null) {
                                    listener.onRefreshing();
                                }

                            }
                        }
                    });
                    mControlHeadAnimator.setDuration(250);
                }
                if (mCurrentState == RELEASE_TO_REFRESH) {
                    mControlHeadAnimator.setIntValues(mRefreshHead.getPaddingTop(), 0);
                } else if (mCurrentState == PULL_TO_REFRESH) {
                    mControlHeadAnimator.setIntValues(mRefreshHead.getPaddingTop(), -mHeaderViewHeight);
                } else {
                    mControlHeadAnimator.setIntValues(mRefreshHead.getPaddingTop(), -mHeaderViewHeight);
                }
                mControlHeadAnimator.start();
                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void refreshFinish() {
        mRefreshHead.post(new Runnable() {
            @Override
            public void run() {
                mRefreshHead.performLoaded();
                if (mRefreshHead.getPaddingTop() <= -mHeaderViewHeight)
                    return;
                mControlHeadAnimator = new ValueAnimator();
                mControlHeadAnimator.setIntValues(mRefreshHead.getPaddingTop(), -mHeaderViewHeight);
                mControlHeadAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mRefreshHead.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0);
                    }
                });
                mControlHeadAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mIsLoadingPullDown = false;
                        mDownY = mRecordY;
                    }
                });
                mControlHeadAnimator.setDuration(250);
                mControlHeadAnimator.start();
            }
        });
    }

    private void calculateRecyclerViewFirstPosition() {
        if (mRecyclerView != null && mRecyclerView.getLayoutManager() != null) {
            if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                if (mVisiblePosition == null) {
                    mVisiblePosition = new int[((StaggeredGridLayoutManager)
                            mRecyclerView.getLayoutManager()).getSpanCount()];
                }
                ((StaggeredGridLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPositions(mVisiblePosition);
                mFirstPosition = mVisiblePosition[0];
            } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                mFirstPosition = ((GridLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            } else if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                mFirstPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mControlHeadAnimator != null && mControlHeadAnimator.isRunning()) {
            mControlHeadAnimator.removeAllUpdateListeners();
            mControlHeadAnimator.removeAllListeners();
            mControlHeadAnimator.cancel();
            mControlHeadAnimator = null;
        }
    }
}
