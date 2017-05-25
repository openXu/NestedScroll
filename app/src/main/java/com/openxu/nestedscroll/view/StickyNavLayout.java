package com.openxu.nestedscroll.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.openxu.nestedscroll.R;

/**
 * author : openXu
 * create at : 2017/5/25 15:02
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : NestedScroll
 * version : 1.0
 * class describe：嵌套滚动父控件
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent {
    private static final String TAG = "StickyNavLayout";

    /*三部分容器*/
    private NestedLinearLayout layout_first;
    private NestedLinearLayout layout_second;
//    private NestedLinearLayout layout_third;
    private SwipeRefreshLayout refresh_layout;
    //第一部分的高度
    private int mTopViewHeight;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    public StickyNavLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }
    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        layout_first = (NestedLinearLayout) findViewById(R.id.id_layout_first);
        layout_second = (NestedLinearLayout) findViewById(R.id.id_layout_second);
//        layout_third =  (NestedLinearLayout) findViewById(R.id.id_layout_third);
        refresh_layout =  (SwipeRefreshLayout) findViewById(R.id.id_refresh_layout);
    }
   /* private void initVelocityTrackerIfNotExists(){
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker(){
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
*/

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        initVelocityTrackerIfNotExists();
//        mVelocityTracker.addMovement(event);
//        int action = event.getAction();
//        float y = event.getY();
//
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished())
//                    mScroller.abortAnimation();
//                mLastY = y;
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                float dy = y - mLastY;
//
//                if (!mDragging && Math.abs(dy) > mTouchSlop)
//                {
//                    mDragging = true;
//                }
//                if (mDragging)
//                {
//                    scrollBy(0, (int) -dy);
//                }
//
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                mDragging = false;
//                recycleVelocityTracker();
//                if (!mScroller.isFinished())
//                {
//                    mScroller.abortAnimation();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                mDragging = false;
//                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
//                int velocityY = (int) mVelocityTracker.getYVelocity();
//                if (Math.abs(velocityY) > mMinimumVelocity)
//                {
//                    fling(-velocityY);
//                }
//                recycleVelocityTracker();
//                break;
//        }
//
//        return super.onTouchEvent(event);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = refresh_layout.getLayoutParams();
        //第三部分的高度=屏幕高度-第二部分高度
        params.height = getMeasuredHeight() - layout_second.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), layout_first.getMeasuredHeight()
                + layout_second.getMeasuredHeight() + refresh_layout.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = layout_first.getMeasuredHeight();
    }



    /**是否可以滑动*/
    public boolean canScroll(){
        if(getScrollY() < mTopViewHeight){
            return true;
        }else if(getScrollY() >= mTopViewHeight){
            return false;
        }
        return true;
    }
    public boolean accepteScroll = true;
    //一定要按照自己的需求返回true，该方法决定了当前控件是否能接收到其内部View(非并非是直接子View)滑动时的参数；
    //假设你只涉及到纵向滑动，这里可以根据nestedScrollAxes这个参数，进行纵向判断。
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "onStartNestedScroll");
        //(nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return true;
    }
    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes){
        Log.e(TAG, "onNestedScrollAccepted");
    }
    @Override
    public void onStopNestedScroll(View target){
        Log.e(TAG, "onStopNestedScroll");
    }
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed){
        Log.e(TAG, "onNestedScroll");
    }
    //该方法的会传入内部View移动的dx,dy，如果你需要消耗一定的dx,dy，
    // 就通过最后一个参数consumed进行指定，例如我要消耗一半的dy，就可以写consumed[1]=dy/2
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed){
//        Log.e(TAG, "onNestedPreScroll:"+target);
        // 如果是上滑且顶部控件未完全隐藏，则消耗掉dy，即consumed[1]=dy;
        // 如果是下滑且内部View已经无法继续下拉，则消耗掉dy，即consumed[1]=dy，
        // 消耗掉的意思，就是自己去执行scrollBy，实际上就是我们的StickNavLayout滑动
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        if(hiddenTop){
            Log.i(TAG, hiddenTop+"头还没有隐藏，向上滚动："+getScrollY());
        }

        boolean canscroll = false;
        if(target instanceof SwipeRefreshLayout){
            View listView = ((SwipeRefreshLayout) target).getChildAt(0);
            if(listView!=null){
                //判断ListView是否可以滚动（是否滑到头或者尾）
                canscroll = ViewCompat.canScrollVertically(listView, -1);
            }
        }

        //向下滑动时分三种情况
        //①、listview可以滑动，让listview滑动
        //②、listview不能滑动，头全部露出，让刷新控件消耗事件
        //③、listview不能滑动，头没有全部露出，在此滚动消费事件
        boolean showTop = dy < 0
                && !canscroll
                && getScrollY() > 0;
        if(showTop){
            Log.i(TAG, showTop+"头不完全可见，向下滚动："+getScrollY());
        }
        if (hiddenTop || showTop) {
            Log.i(TAG, "滚动命令");
            scrollBy(0, dy);
            consumed[1] = dy;
        }


    }
    //你可以捕获对内部View的fling事件，如果return true则表示拦截掉内部View的事件
    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed){
        Log.e(TAG, "onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY){
        Log.e(TAG, "onNestedPreFling："+velocityY);
        velocityY = velocityY-(mTopViewHeight-getScrollY());
        //velocityY<0向下滑动，打开
        if(velocityY<0 && getScrollY()>0){
            fling((int) velocityY);
        }
        //向上滑动，关闭
        if(velocityY>0 && getScrollY()>0){
            fling((int) velocityY);
        }

//        if(getScrollY()<=0)
//            return false;
//        //down - //up+
//        if (getScrollY() >= mTopViewHeight)
//            return false;
//        fling((int) velocityY);
//        return true;
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes(){
        Log.e(TAG, "getNestedScrollAxes");
        return 0;
    }

    public void fling(int velocityY){
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y){
        Log.e(TAG, "滚动"+y);
        if (y < 0){
            y = 0;
        }
        if (y > mTopViewHeight){
            y = mTopViewHeight;
        }
        if (y != getScrollY()){
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll(){
        if (mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }


}
