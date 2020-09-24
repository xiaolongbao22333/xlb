package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridLayout;


public class JGridLayout extends GridLayout {


    public JGridLayout(Context context) {
        super(context);
    }

    public JGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static JLayoutParams obtainParams(){
        return new JLayoutParams();
    }


    public static class JLayoutParams extends LayoutParams{

        public JLayoutParams(Spec rowSpec, Spec columnSpec) {
            super(rowSpec, columnSpec);
        }

        public JLayoutParams() {
        }

        public JLayoutParams(ViewGroup.LayoutParams params) {
            super(params);
        }

        public JLayoutParams(MarginLayoutParams params) {
            super(params);
        }

        public JLayoutParams(LayoutParams source) {
            super(source);
        }

        public JLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

}
