package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.utils.AutoUtils;
import com.lx.xiaolongbao.utils.ReflexUtils;
import com.lx.xiaolongbao.utils.SpaceItemDecoration;


public class JRecyclerView extends RecyclerView {

    private boolean mAutoHeight;
    private boolean mHorizontal;
    private boolean mReverse;
    private int mSpanCount;
    private int mEmptyLayout;
    private boolean mInterceptEvent;

    public JRecyclerView(Context context) {
        this(context, null);
    }

    public JRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JRecyclerView);
        parseAttr(typedArray);
        typedArray.recycle();

        init();
    }

    private void parseAttr(TypedArray typedArray){
        mAutoHeight = typedArray.getBoolean(R.styleable.JRecyclerView_JAutoHeight, false);

        float dimension = typedArray.getDimension(R.styleable.JRecyclerView_JDividerHeight, 0);

        mHorizontal = typedArray.getBoolean(R.styleable.JRecyclerView_JHorizontal, false);
        mReverse = typedArray.getBoolean(R.styleable.JRecyclerView_JReverse, false);
        mSpanCount = typedArray.getInt(R.styleable.JRecyclerView_JSpanCount, 1);
        mEmptyLayout = typedArray.getResourceId(R.styleable.JRecyclerView_JEmptyLayout, -1);
        if (mSpanCount <= 0){
            throw new IllegalArgumentException("spanCount 不能 <= 0");
        }

        if (mHorizontal){
            addItemDecoration(new SpacesItemDecoration(0,  (int) AutoUtils.getScaleWidth(dimension)));
        }else {
            addItemDecoration(new SpacesItemDecoration( (int) AutoUtils.getScaleWidth(dimension), 0));
        }

        boolean autoRoll = typedArray.getBoolean(R.styleable.JRecyclerView_JAutoRoll, false);
        if (autoRoll) {
            setHasFixedSize(true);
            setFocusable(false);
            setNestedScrollingEnabled(false);
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null && mEmptyLayout != -1 && ReflexUtils.is(adapter.getClass(), "BaseQuickAdapter")){
            ReflexUtils.setMethod(adapter, adapter.getClass(), "setEmptyView", new ReflexUtils.ClassParam(LayoutInflater.from(getContext()).inflate(mEmptyLayout, null), View.class));
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        if (mInterceptEvent){
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }

    public void setInterceptEvent(boolean interceptEvent){
        mInterceptEvent = interceptEvent;
    }

    private void init() {
        if (mSpanCount == 1){
            setLayoutManager(new LinearLayoutManager(getContext(), mHorizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, mReverse));
        }else {
            setLayoutManager(new GridLayoutManager(getContext(), mSpanCount, mHorizontal ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL, mReverse));
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mAutoHeight){
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
