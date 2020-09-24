package com.lx.xiaolongbao.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.app.BaseApplication;

@SuppressLint("AppCompatCustomView")
public class JImageView extends ImageView{

    private int mJPadding;
    private int mClickGap;
    private long mOldClickTime;

    public JImageView(Context context) {
        super(context);
        init();
    }

    public JImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JImageView);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttr(TypedArray typedArray){
        Drawable drawable = typedArray.getDrawable(R.styleable.JImageView_JSrc);
        if (drawable != null && !BaseApplication.RELEASE_MODE){
//            setImageDrawable(drawable);
        }
        boolean select = typedArray.getBoolean(R.styleable.JImageView_JSelect, false);
        if (select){
            setSelected(select);
        }
        mJPadding = (int) typedArray.getDimension(R.styleable.JImageView_JPadding, 0);
        mClickGap = typedArray.getInteger(R.styleable.JImageView_JClickGap, 0);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {

        if (getPaddingLeft() == 0 && getPaddingTop() == 0 && getPaddingRight() == 0 && getPaddingBottom() == 0 && mJPadding != 0) {
            setPadding(mJPadding, mJPadding, mJPadding, mJPadding);
            params.width += mJPadding * 2;
            params.height += mJPadding * 2;
        }
        super.setLayoutParams(params);

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

    private void init() {

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

    public void setImageUrl(String url) {
        if (!((Activity) getContext()).isDestroyed()) {
            Glide.with(getContext())
                    .load(url)
                    .into(this);
        }

    }

    public void setGif(@DrawableRes int gif){
        Glide.with(getContext())
                .load(gif)
                .into(this);
    }

    public void setImageUrl(String url, @DrawableRes int perch) {
            if (!((Activity) getContext()).isDestroyed()) {
                RequestOptions options = new RequestOptions()
                        .placeholder(perch);
                Glide.with(getContext())
                        .load(url)
                        .apply(options)
                        .into(this);
            }

    }
}
