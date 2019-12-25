package com.zerowater.environment.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zerowater.environment.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by YoungSoo Kim on 2018-10-16.
 * Zero Ltd
 * byzerowater@gmail.com
 * Swipe 안되는 ViewPager
 */
public class SwipeControlViewPager extends ViewPager {

    /**
     * 스와이프 여부
     */
    public boolean mSwipeEnable;

    public SwipeControlViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeControlViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(attrs);
    }

    /**
     * xml에서 attrs 가져와 값 적용
     *
     * @param attrs
     */
    private void setAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeControlViewPager);
        mSwipeEnable = typedArray.getBoolean(R.styleable.SwipeControlViewPager_swipe_enable, false);
    }

    public void setSwipeEnable(boolean swipeEnable) {
        mSwipeEnable = swipeEnable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mSwipeEnable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mSwipeEnable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

}
