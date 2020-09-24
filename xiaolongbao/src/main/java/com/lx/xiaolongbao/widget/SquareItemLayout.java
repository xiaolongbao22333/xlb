package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @ClassName: SquareItemLayout
 * @Author: 冻品
 * @CreateDate: 2020-05-22 14:32
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: 正方形
 */
public class SquareItemLayout extends RelativeLayout {
    public SquareItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareItemLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        heightMeasureSpec =widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
