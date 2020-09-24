package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;


public abstract class JViewGroup extends ViewGroup {

    public JViewGroup(Context context) {
        super(context);
    }

    public JViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static class JLayoutParams extends LayoutParams{

        public JLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public JLayoutParams(int width, int height) {
            super(width, height);
        }

        public JLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
