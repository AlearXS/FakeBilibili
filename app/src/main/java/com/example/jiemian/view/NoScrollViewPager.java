package com.example.jiemian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


/**
 *  用来处理禁止滑动
 *  用户无法通过滑动切换到该页面。通过禁止滑动，我们可以控制用户只能通过点击其他控件或者其他方式进入该页面
 */
public class NoScrollViewPager extends ViewPager {

    private boolean noScroll = false;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.noScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return noScroll && super.onTouchEvent(ev);

    }
}
