package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lx.xiaolongbao.R;


public class JRelativeLayout extends RelativeLayout {


    private boolean mJRadio;
    private int mClickGap;
    private long mOldClickTime;

    public JRelativeLayout(Context context) {
        super(context, null);
    }

    public JRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JRelativeLayout);

        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttr(TypedArray typedArray) {
        mJRadio = typedArray.getBoolean(R.styleable.JRelativeLayout_JRadio, false);
        mClickGap = typedArray.getInteger(R.styleable.JRelativeLayout_JClickGap, 0);
    }


    @Override
    public boolean performClick() {
        long millis = System.currentTimeMillis();
        if (millis - mOldClickTime < mClickGap){
            return false;
        }
        mOldClickTime = millis;
        return super.performClick();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mJRadio) {
            radio();
        }
    }

    private void radio() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (!(view instanceof JTextView)) {
                throw new UnsupportedOperationException("子控件只支持JTextView");

            }
            int pos = i;
            ((JTextView) view).setOnCopyClickListener(v -> {
                for (int j = 0; j < getChildCount(); j++) {
                    getChildAt(j).setSelected(pos == j);
                }
            });
        }
    }

    public static JLayoutParams obtainParams() {
        return new JLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
    }

    public static class JLayoutParams extends LayoutParams {
        public JLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public JLayoutParams(int w, int h) {
            super(w, h);
        }

        public JLayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public JLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public JLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
