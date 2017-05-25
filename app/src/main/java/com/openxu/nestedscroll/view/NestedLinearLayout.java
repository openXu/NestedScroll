package com.openxu.nestedscroll.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author : openXu
 * create at : 2017/5/25 15:02
 * blog : http://blog.csdn.net/xmxkf
 * gitHub : https://github.com/openXu
 * project : NestedScroll
 * version : 1.0
 * class describe：嵌套滚动子控件
 */
public class NestedLinearLayout extends LinearLayout implements NestedScrollingChild {

    private static final String TAG = "NestedLinearLayout";

    private final int[] offset = new int[2];
    private final int[] consumed = new int[2];

    private NestedScrollingChildHelper mScrollingChildHelper;
    private int lastY;

    public NestedLinearLayout(Context context) {
        this(context, null);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
            mScrollingChildHelper.setNestedScrollingEnabled(true);
        }
    }
    StickyNavLayout parent;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(parent==null)
            parent = (StickyNavLayout)getParent();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG ,"按下");
                lastY = (int)event.getRawY();
                // 当开始滑动的时候，告诉父view
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL| ViewCompat.SCROLL_AXIS_VERTICAL);
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG ,"移动");
                int y = (int)(event.getRawY());
                int dy =lastY - y;
                lastY = y;
               /* if(parent.accepteScroll){
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                            && dispatchNestedPreScroll(0, dy, consumed, offset)) {// 父类进行了一部分滚动
                    }
                }else{
                    //父控件不接受滑动了，说明化到顶了，如果要向下滑动时，需要判断
                    if(dy>0){
                        Log.i(TAG, "向上滑动"+dy);
                    }else{
                        Log.i(TAG, "向下滑动"+dy);
                        parent.accepteScroll = true;
                        startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL| ViewCompat.SCROLL_AXIS_VERTICAL);
                        return true;
                    }
                }*/
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                        && dispatchNestedPreScroll(0, dy, consumed, offset)) {// 父类进行了一部分滚动
                }

//                if(!parent.accepteScroll && )

               /* if(){
                    stopNestedScroll();
                }*/

        }
        return super.onTouchEvent(event);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        return mScrollingChildHelper;
    }

    // 接口实现--------------------------------------------------

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed,
                                           int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy,
                consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY,
                                       boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX,
                velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX,
                velocityY);
    }
}
