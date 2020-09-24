package com.lx.xiaolongbao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.app.BaseApplication;


@SuppressLint("AppCompatCustomView")
public class JButton extends Button {

    private int mClickGap;
    private long mOldClickTime;

    private JTextView.OnSelectedChangeListener mOnSelectedChangeListener;

    public JButton(Context context) {
        this(context, null);
    }

    public JButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JButton);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }

    private void parseAttr(TypedArray typedArray){
        boolean pLine = typedArray.getBoolean(R.styleable.JButton_JText_P, false);
        String text = typedArray.getString(R.styleable.JButton_JText);
        boolean select = typedArray.getBoolean(R.styleable.JButton_JSelect, false);
        mClickGap = typedArray.getInteger(R.styleable.JButton_JClickGap, 0);
        if (select){
            setSelected(select);
        }
        if (!TextUtils.isEmpty(text) && !BaseApplication.RELEASE_MODE){
            setText(text);
        }
        if (pLine){
            getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    public String getString(){
        return getText().toString();
    }

    public int getStrLength(){
        return getString().length();
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
    public boolean performClick() {
        long millis = System.currentTimeMillis();
        if (millis - mOldClickTime < mClickGap){
            return false;
        }
        mOldClickTime = millis;
        return super.performClick();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (mOnSelectedChangeListener != null){
            mOnSelectedChangeListener.onSelectedChange(this, selected);
        }
    }

    public interface OnSelectedChangeListener{
        void onSelectedChange(View v, boolean selected);

    }

    public void setOnSelectedChangeListener(JTextView.OnSelectedChangeListener onSelectedChangeListener){
        mOnSelectedChangeListener = onSelectedChangeListener;
    }
}
