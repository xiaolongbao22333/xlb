package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lx.xiaolongbao.R;


public class JScrollView extends ScrollView {

    private boolean mInterceptX;
    private float mDownX, mDownY;
    private OnScrollListener mOnScrollListener;

    public JScrollView(Context context) {
        super(context);
    }

    public JScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JScrollView);
        mInterceptX = typedArray.getBoolean(R.styleable.JScrollView_JInterceptX, false);

        typedArray.recycle();
    }

    public JScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!mInterceptX){
            return super.onInterceptTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(ev.getX() - mDownX);
                float dy = Math.abs(ev.getY() - mDownY);
                if (dx > dy) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface OnScrollListener{
        void onScroll(int scrollY, int oldScrollY);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollListener != null){
            mOnScrollListener.onScroll(t, oldl);
        }
    }


    public static JLayoutParams obtainParams(){
        return new JLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static class JLayoutParams extends LayoutParams{
        public JLayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
        }

        public JLayoutParams(int width, int height) {
            super(width, height);
        }

        public JLayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public JLayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public JLayoutParams(@NonNull MarginLayoutParams source) {
            super(source);
        }

        public JLayoutParams(@NonNull LayoutParams source) {
            super(source);
        }
    }
}
