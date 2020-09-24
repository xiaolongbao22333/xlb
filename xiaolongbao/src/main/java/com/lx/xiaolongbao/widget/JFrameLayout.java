package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lx.xiaolongbao.R;


public class JFrameLayout extends FrameLayout {

    private boolean mJRadio;
    private int mClickGap;
    private long mOldClickTime;

    public JFrameLayout(@NonNull Context context) {
        super(context);
    }

    public JFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JFrameLayout);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttr(TypedArray typedArray) {
        mJRadio = typedArray.getBoolean(R.styleable.JFrameLayout_JRadio, false);
        mClickGap = typedArray.getInteger(R.styleable.JFrameLayout_JClickGap, 0);
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

    @Override
    public boolean performClick() {
        long millis = System.currentTimeMillis();
        if (millis - mOldClickTime < mClickGap){
            return false;
        }
        mOldClickTime = millis;
        return super.performClick();
    }

    public void gone(boolean gone){
        setVisibility(gone ? GONE : VISIBLE);
    }

    public void invisible(boolean invisible){
        setVisibility(invisible ? INVISIBLE : VISIBLE);
    }



    public void gone(){
        setVisibility(GONE);
    }

    public void invisible(){
        setVisibility(INVISIBLE);
    }

    public void visible(){
        setVisibility(VISIBLE);
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
