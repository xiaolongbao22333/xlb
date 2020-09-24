package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class JHorizontalScrollView extends HorizontalScrollView {
    public JHorizontalScrollView(Context context) {
        super(context);
    }

    public JHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
