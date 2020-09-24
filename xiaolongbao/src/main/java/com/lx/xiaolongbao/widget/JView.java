package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.lx.xiaolongbao.utils.AutoUtils;


public class JView extends View {

    public JView(Context context) {
        super(context);
    }

    public JView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static View inflate(Context context, @LayoutRes int resource) {
        return inflate(context, resource, null, true);
    }

    public static View inflate(Context context, @LayoutRes int resource, boolean auto) {
        return inflate(context, resource, null, auto);
    }

    public static View inflate(Context context, @LayoutRes int resource, ViewGroup root) {
        return inflate(context, resource, root, true);
    }


    public static View inflate(Context context, @LayoutRes int resource, ViewGroup root, boolean auto) {
        View view = LayoutInflater.from(context).inflate(resource, root);
        if (auto) AutoUtils.auto(view);
        return view;
    }

    public void gone(boolean gone){
        setVisibility(gone ? GONE : VISIBLE);
    }

    public void invisible(boolean invisible){
        setVisibility(invisible ? INVISIBLE : VISIBLE);
    }

}
