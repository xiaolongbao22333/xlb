package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.lx.xiaolongbao.R;


public class JLinearLayout extends LinearLayout {

    private boolean mJRadio;
    private OnCopyClickListener mOnCopyClickListener    ;
    private OnClickListener mOnClickListener;
    private int mClickGap;
    private long mOldClickTime;

    public JLinearLayout(Context context) {
        this(context, null);
    }

    public JLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JLinearLayout);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttr(TypedArray typedArray) {
        mJRadio = typedArray.getBoolean(R.styleable.JLinearLayout_JRadio, false);
        boolean select = typedArray.getBoolean(R.styleable.JLinearLayout_JSelect, false);
        mClickGap = typedArray.getInteger(R.styleable.JLinearLayout_JClickGap, 0);
        if (select){
            setSelected(select);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mJRadio) {
            radio();
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (mJRadio) {
            radio();
        }
    }


    @Override
    public void removeView(View view) {
        super.removeView(view);
        if (mJRadio) {
            radio();
        }
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        if (mJRadio) {
            radio();
        }
    }

    private void radio() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);

            int pos = i;
            view.setOnClickListener(v -> {
                for (int j = 0; j < getChildCount(); j++) {
                    check(getChildAt(j), pos == j);
                }
            });
        }
    }

    private void check(View view, boolean checked){
        if (view instanceof ViewGroup){
            check(((ViewGroup) view), checked);
        }else {
            view.setSelected(checked);
        }
    }


    private void check(ViewGroup group, boolean checked){
        group.setSelected(checked);
        for (int i = 0; i < group.getChildCount(); i++) {
            View childAt = group.getChildAt(i);
            check(childAt, checked);
        }
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

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    @Override
    public boolean performClick() {
        long millis = System.currentTimeMillis();
        if (millis - mOldClickTime < mClickGap){
            return false;
        }
        mOldClickTime = millis;
        boolean click = super.performClick();
        if (mOnClickListener != null && mOnCopyClickListener != null){
            mOnCopyClickListener.onClick(this);
        }
        return click;
    }

    public interface OnCopyClickListener{
        void onClick(View v);
    }

    public void setOnCopyClickListener(OnCopyClickListener onCopyClickListener) {
        mOnCopyClickListener = onCopyClickListener;
    }


    public static JLayoutParams obtainParams(){
        return new JLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static class JLayoutParams extends LayoutParams {
        public JLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public JLayoutParams(int width, int height) {
            super(width, height);
        }

        public JLayoutParams(int width, int height, float weight) {
            super(width, height, weight);
        }

        public JLayoutParams(ViewGroup.LayoutParams p) {
            super(p);
        }

        public JLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public JLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
