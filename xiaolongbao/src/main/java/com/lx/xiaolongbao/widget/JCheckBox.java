package com.lx.xiaolongbao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.lx.xiaolongbao.R;


@SuppressLint("AppCompatCustomView")
public class JCheckBox extends CheckBox {


    private OnCheckedChangeListener mListener;

    private boolean mAutoEvent;

    public JCheckBox(Context context) {
        super(context);
    }

    public JCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JCheckBox);
        parseAttr(typedArray);
        typedArray.recycle();

    }

    public JCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void parseAttr(TypedArray typedArray){
        mAutoEvent = typedArray.getBoolean(R.styleable.JCheckBox_JAutoEvent, false);

    }

    @Override
    public boolean performClick() {

        final boolean handled = super.performClick();

        if (mListener != null){
            mListener.onCheckedChanged(this, isChecked());
        }

        return handled;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (!mAutoEvent && mListener != null){
            mListener.onCheckedChanged(this, isChecked());
        }
    }

    public void setOnCheckedListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }

}
