package com.lx.xiaolongbao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.app.BaseApplication;
import com.lx.xiaolongbao.utils.AutoUtils;


@SuppressLint("AppCompatCustomView")
public class JTextView extends TextView {

    private OnSelectedChangeListener mOnSelectedChangeListener;
    private OnCopyClickListener mOnCopyClickListener;
    private OnClickListener mOnClickListener;
    private int mClickGap;
    private long mOldClickTime;

    private float mHintSize;

    private Typeface mTypeface;
    private Typeface mHintTypeface;

    private static class TextStyle {
        /* 粗体 */
        public static final int BOLD = 1;
        /* 斜体 */
        public static final int ITALIC = 2;
        /* 常规 */
        public static final int NORMAL = 3;
    }

    public JTextView(Context context) {
        this(context, null);
    }

    public JTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JTextView);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttr(TypedArray typedArray) {
        boolean pLine = typedArray.getBoolean(R.styleable.JTextView_JText_P, false);
        String text = typedArray.getString(R.styleable.JTextView_JText);
        boolean select = typedArray.getBoolean(R.styleable.JTextView_JSelect, false);
        mClickGap = typedArray.getInteger(R.styleable.JTextView_JClickGap, 0);
        if (select) {
            setSelected(select);
        }
        if (!TextUtils.isEmpty(text) && !BaseApplication.RELEASE_MODE) {
//            setText(text);
        }
        if (pLine) {
            getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        mHintSize = AutoUtils.getScaleWidth(typedArray.getDimension(R.styleable.JTextView_JHintSize, 0));
        int hintTextStyle = typedArray.getInt(R.styleable.JTextView_JHintTextStyle, 0);
        int textStyle = typedArray.getInt(R.styleable.JTextView_JTextStyle, 0);

        if (hintTextStyle == TextStyle.BOLD) {
            mHintTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        } else if (hintTextStyle == TextStyle.ITALIC) {
            mHintTypeface = Typeface.defaultFromStyle(Typeface.ITALIC);
        } else if (hintTextStyle == TextStyle.NORMAL) {
            mHintTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
        }

        if (textStyle == TextStyle.BOLD) {
            mTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        } else if (textStyle == TextStyle.ITALIC) {
            mTypeface = Typeface.defaultFromStyle(Typeface.ITALIC);
        } else if (textStyle == TextStyle.NORMAL) {
            mTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
        }
    }


    public void gone(boolean gone) {
        setVisibility(gone ? GONE : VISIBLE);
    }

    public void invisible(boolean invisible) {
        setVisibility(invisible ? INVISIBLE : VISIBLE);
    }

    public void gone() {
        setVisibility(GONE);
    }

    public void invisible() {
        setVisibility(INVISIBLE);
    }

    public void visible() {
        setVisibility(VISIBLE);
    }


    public boolean isEmpty() {
        return getStrLength() == 0;
    }

    public String getString() {
        return getText().toString();
    }

    public int getStrLength() {
        return getString().length();
    }

    public String text() {
        return getText().toString();
    }

    public void text(CharSequence text) {
        super.setText(text);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {


        if (TextUtils.isEmpty(text) && mHintTypeface != null) {
            getPaint().setTypeface(mHintTypeface);
        } else if (mTypeface != null) {
            getPaint().setTypeface(mTypeface);
        }
        super.setText(text, type);
    }

    public void text(@StringRes int resid) {
        try {
            super.setText(resid);
        } catch (Resources.NotFoundException e) {
            super.setText(String.valueOf(resid));
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (mOnSelectedChangeListener != null) {
            mOnSelectedChangeListener.onSelectedChange(this, selected);
        }

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    @Override
    public boolean performClick() {
        long millis = System.currentTimeMillis();
        if (millis - mOldClickTime < mClickGap) {
            return false;
        }
        mOldClickTime = millis;
        if (mOnClickListener != null && mOnCopyClickListener != null) {
            mOnCopyClickListener.onClick(this);
        }
        return super.performClick();
    }

    public interface OnSelectedChangeListener {
        void onSelectedChange(View v, boolean selected);

    }

    public interface OnCopyClickListener {
        void onClick(View v);
    }

    public void setOnCopyClickListener(OnCopyClickListener onCopyClickListener) {
        mOnCopyClickListener = onCopyClickListener;
    }

    public void setOnSelectedChangeListener(OnSelectedChangeListener onSelectedChangeListener) {
        mOnSelectedChangeListener = onSelectedChangeListener;
    }

   public void setFlag(int painFlag) {
//      getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
//
//      getPaint().setAntiAlias(true);//抗锯齿
//
//      getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        getPaint().setFlags(painFlag);

    }
}
