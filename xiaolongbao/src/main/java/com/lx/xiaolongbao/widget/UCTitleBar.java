package com.lx.xiaolongbao.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jackchong.utils.AutoUtils;
import com.lx.xiaolongbao.R;


public class UCTitleBar extends LinearLayout {

    private Context mContext;
    private View mRoot, mBarDivider;
    private ImageButton mIbLeft, mIbRight;
    private TextView mBarTitle, mTvMenu,mLfText;
    private RelativeLayout mBarRlRoot;

    public UCTitleBar(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public UCTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        mRoot = View.inflate(mContext, R.layout.uc_title_bar, null);
        mIbLeft = (ImageButton) mRoot.findViewById(R.id.ib_left);
        mIbRight = (ImageButton) mRoot.findViewById(R.id.ib_right);
        mBarTitle = (TextView) mRoot.findViewById(R.id.bar_title);
        mTvMenu = (TextView) mRoot.findViewById(R.id.tv_menu);
        mBarDivider = mRoot.findViewById(R.id.bar_divider);
        mBarRlRoot = (RelativeLayout) mRoot.findViewById(R.id.bar_rl_root);
        mLfText = (TextView) mRoot.findViewById(R.id.lf_text);
        AutoUtils.auto(mRoot);
        addView(mRoot);
    }

    /**
     * 默认展示返回按钮 设置返回事件
     *
     * @param listener
     * @return
     */
    public UCTitleBar setBackEvent(OnClickListener listener) {
        if (mIbLeft.getVisibility() != VISIBLE) {
            mIbLeft.setVisibility(VISIBLE);
        }
        mIbLeft.setOnClickListener(listener);
        return this;
    }

   public void setLeftText(String text){
       mLfText.setText(text);
    }
    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public UCTitleBar setTitleText(String text) {
        if (mBarTitle.getVisibility() != VISIBLE) {
            mBarTitle.setVisibility(VISIBLE);
        }
        mBarTitle.setText(text);
        return this;
    }

    /**
     * 设置标题 同时设置文字颜色
     *
     * @param text
     * @param color
     * @return
     */
    public UCTitleBar setTitleText(String text, int color) {
        if (mBarTitle.getVisibility() != VISIBLE) {
            mBarTitle.setVisibility(VISIBLE);
        }
        mBarTitle.setText(text);
        mBarTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置左侧按钮图片
     *
     * @param drawable
     * @return
     */
    public UCTitleBar setLeftDrawable(int drawable) {
        if (drawable != 0) {
            mIbLeft.setImageResource(drawable);
        }
        return this;
    }

    /**
     * 设置左侧事件 默认返回icon 如不需更改icon drawable 传 0
     *
     * @param listener
     * @param drawable
     * @return
     */
    public UCTitleBar setLeftEvent(OnClickListener listener, int drawable) {
        if (drawable != 0) {
            mIbLeft.setImageResource(drawable);
        }
        mIbLeft.setOnClickListener(listener);
        mIbLeft.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧图片事件 默认搜索icon 如不需更改icon drawable 传 0
     *
     * @param listener
     * @param drawable
     * @return
     */
    public UCTitleBar setRightEvent(OnClickListener listener, int drawable) {
        if (drawable != 0) {
            mIbRight.setImageResource(drawable);
        }
        mIbRight.setOnClickListener(listener);
        mIbRight.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧图片事件 显示
     *
     * @return
     */
    public UCTitleBar setRightEventShow(boolean isShow) {
        if (isShow) {
            mIbRight.setVisibility(VISIBLE);
        } else {
            mIbRight.setVisibility(GONE);
        }
        return this;
    }


    /**
     * 设置右侧文字
     *
     * @param menu
     * @return
     */
    public UCTitleBar setRightMessage(String menu) {
        if (!TextUtils.isEmpty(menu)) {
            mTvMenu.setText(menu);
        }
        mTvMenu.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧字体颜色
     *
     * @param color
     * @return
     */
    public UCTitleBar setRightMessageColor(int color) {
        if (color != 0) {
            mTvMenu.setTextColor(color);
        }
        mTvMenu.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    public UCTitleBar setRightTextColor(int color) {
        mTvMenu.setTextColor(color);
        return this;
    }

    /**
     * 设置右侧文字事件 默认文字发送， 如不更改文字 menu 传 空字符串 "" 默认文字ffffff 如不更改 color 传 0
     * 默认字体大小 18 如不更改 传 0
     *
     * @param listener
     * @param menu
     * @return
     */
    public UCTitleBar setRightMenuEvent(OnClickListener listener, String menu, int color, int size) {
        if (!TextUtils.isEmpty(menu)) {
            mTvMenu.setText(menu);
        }
        if (color != 0) {
            mTvMenu.setTextColor(color);
        }
        if (size != 0) {
            mTvMenu.setTextSize(size);
        }
        mTvMenu.setOnClickListener(listener);
        mTvMenu.setVisibility(VISIBLE);
        return this;
    }

    public UCTitleBar setRightMenuEventVisibility(int visibility) {
        mTvMenu.setVisibility(visibility);
        return this;
    }

    public UCTitleBar setRightImageVisibility(int visibility) {
        mIbRight.setVisibility(visibility);
        return this;
    }

    public TextView getRightTextView() {
        return mTvMenu;
    }

    public ImageButton getRightImageView() {
        return mIbRight;
    }

    /**
     * 展示分割线
     *
     * @return
     */
    public UCTitleBar showBarDivider() {
        mBarDivider.setVisibility(VISIBLE);
        return this;
    }

    /**
     * 隐藏分割线
     *
     * @return
     */
    public UCTitleBar hideBarDivider() {
        mBarDivider.setVisibility(GONE);
        return this;
    }

    /**
     * 设置分割线色值，同时展示分割线
     *
     * @return
     */
    public UCTitleBar setBarDivider(int color) {
        mBarDivider.setVisibility(VISIBLE);
        mBarDivider.setBackgroundColor(color);
        return this;
    }

    /**
     * 修改背景色值
     *
     * @param color
     * @return
     */
    public UCTitleBar setBarBackground(int color) {
        mRoot.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置透明色
     *
     * @param color
     * @return
     */
    public UCTitleBar setBarTransparent(int color) {
        mBarRlRoot.setBackgroundColor(color);
        return this;
    }

    /**
     * 获取根部局
     *
     * @return
     */
    public View getRoot() {

        return mRoot;
    }

}
