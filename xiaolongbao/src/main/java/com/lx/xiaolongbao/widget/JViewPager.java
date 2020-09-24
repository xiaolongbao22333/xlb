package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.lx.xiaolongbao.R;


public class JViewPager extends ViewPager {

    private boolean mAutoX;
    private boolean mScrollable;
    private PointF mDPint = new PointF();
    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private int mOrientation;

    public JViewPager(Context context) {
        super(context);
    }

    public JViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JViewPager);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    private void parseAttr(TypedArray typedArray) {
        mAutoX = typedArray.getBoolean(R.styleable.JViewPager_JAutoX, false);
        mScrollable = typedArray.getBoolean(R.styleable.JViewPager_JScrollable, true);
        mOrientation = typedArray.getInt(R.styleable.JViewPager_JOrientation, HORIZONTAL);

        if (mOrientation == VERTICAL){
            setPageTransformer(false, new VerticalPageTransformer());
        }
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!mScrollable){
            return false;
        }

        if (!mAutoX){
            if (mOrientation == VERTICAL){
                boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
                swapXY(ev);
                return intercepted;
            }
            return super.onInterceptTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDPint.x = ev.getRawX();
                mDPint.y = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(ev.getRawX() - mDPint.x);
                float deltaY = Math.abs(ev.getRawY() - mDPint.y);
                if (deltaX > deltaY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }


    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View view, float position) {
            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                alpha = position + 1;
            }
            view.setAlpha(alpha);
            view.setTranslationX(view.getWidth() * -position);
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable){
            if (mOrientation == VERTICAL){
                return super.onTouchEvent(swapXY(ev));
            }
            return super.onTouchEvent(ev);
        }
        return false;
    }

}