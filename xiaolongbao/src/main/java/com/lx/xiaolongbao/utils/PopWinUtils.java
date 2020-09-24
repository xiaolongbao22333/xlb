package com.lx.xiaolongbao.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.PopupWindow;


import com.lx.xiaolongbao.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Red on 2017/4/27.
 */

public class PopWinUtils {

    private static List<PopWinUtils> sPopWinList = new ArrayList<>();
    private OnDismissListener mOnDismissListener;
    private int layout;
    private Activity activity;
    private int wide;
    private int high;
    private PopupWindow mPopWin;
    private float alpha = 0.3f;
    private WindowManager.LayoutParams mLp;
    private static ValueAnimator sAnimator;

    private PopWinUtils(){}

    public PopWinUtils(View view, final Activity activity){
        this(view,activity, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public PopWinUtils(View view, final Activity activity, boolean maxWide, boolean maxHigh){
        this(view,activity, maxWide ?  ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT,
                maxHigh ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public PopWinUtils(int layout, final Activity activity, int wide, int high) {
        this(View.inflate(activity,layout,null),activity,wide,high);
    }

    public PopWinUtils(View view, final Activity activity, int wide, int high) {
        this.activity = activity;
        this.wide = wide;
        this.high = high;
        mPopWin = getPopWin(view, activity, wide, high);
    }


    public void show(){
        show(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
    }

    public void show(int gravity){
        show(activity.getWindow().getDecorView(), gravity, 0, 0);
    }


    public void show(int gravity, int x, int y){
        show(activity.getWindow().getDecorView(), gravity,x,y);
    }

    public void show(View view){
        if (activity.isFinishing())return;
        show(view, 0f, 0f);
    }

    private void startEnterShadow() {
        if (sAnimator != null && sAnimator.isRunning()){
            sAnimator.cancel();
            sAnimator = null;
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(1, alpha);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            setWindowAlpha((float) animation.getAnimatedValue());
        });
        animator.start();
    }

    private void setWindowAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) return;
        mLp.alpha = alpha;
        WindowManager.LayoutParams windowLP = activity.getWindow().getAttributes();
        windowLP.alpha = alpha;
        if (alpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(mLp);
    }


    private void startExitShadow() {

        if (!sPopWinList.isEmpty()){
            for (PopWinUtils popWinUtils : sPopWinList) {
                if (popWinUtils.isShowing()) {
                    return;
                }
            }
        }
        sAnimator = ValueAnimator.ofFloat(alpha, 1);
        sAnimator.setDuration(300);
        sAnimator.setInterpolator(new AccelerateInterpolator());
        sAnimator.addUpdateListener(animation -> {
            setWindowAlpha((float) animation.getAnimatedValue());
        });
        sAnimator.start();
    }


    public void show(View view, float xoff, float yoff){
        show(view, xoff, yoff, Gravity.TOP | Gravity.START);
    }

    public void show(View view, float xoff, float yoff, int gravity){
        if (activity.isFinishing())return;
        mPopWin.showAsDropDown(view,(int) xoff,(int) yoff, gravity);
        startEnterShadow();
    }

    public void show(View view, int gravity, int x, int y){
        if (activity.isFinishing() || view.getWindowToken() == null){
            return;
        }
        mPopWin.showAtLocation(view, gravity,x,y);

        sPopWinList.add(this);
        startEnterShadow();
    }

    public int getWidth(){
        return mPopWin.getWidth();
    }

    public int getHeight(){
        return mPopWin.getHeight();
    }

    public PopWinUtils setAlpha(float alpha){
        this.alpha = alpha;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    public PopWinUtils setOutsideTouchable(boolean b){
        mPopWin.setOutsideTouchable(b);
        mPopWin.setBackgroundDrawable(b ? new BitmapDrawable(activity.getResources(), (Bitmap) null) : null);

        mPopWin.setTouchInterceptor((v, event) -> {
            if (!mPopWin.isOutsideTouchable()) {
                View mView = getContentView();
                if (null != mView)
                    mView.dispatchTouchEvent(event);
            }
            return mPopWin.isFocusable() && !mPopWin.isOutsideTouchable();
        });

        return this;
    }

    public PopWinUtils setOnKeyBackListner(View.OnKeyListener onKeyListener) {
        mPopWin.getContentView().setOnKeyListener(onKeyListener);
        return this;
    }

    public PopWinUtils setOnDismissListener(OnDismissListener onDismissListener){
        mOnDismissListener = onDismissListener;
        return this;
    }

    public PopWinUtils setAnimationStyle(int animationStyle){
        mPopWin.setAnimationStyle(animationStyle);
        return this;
    }

    public PopWinUtils setWidth(int width){
        mPopWin.setWidth(width);
        return this;
    }

    public View getContentView(){
        return mPopWin.getContentView();
    }

    public void dismiss(){
        mPopWin.dismiss();
    }

    public boolean isShowing(){
        return mPopWin.isShowing();
    }

    public PopWinUtils setHeight(int height){
        mPopWin.setHeight(height);
        return this;
    }

    public PopWinUtils update(int x, int y, int width, int height, boolean force){
        mPopWin.update(x, y, width, height, force);
        return this;
    }

    public PopWinUtils setFocusable(boolean focusable){
        mPopWin.setFocusable(focusable);
        return this;
    }

    public PopWinUtils setBackgroundDrawable(Drawable drawable){
        mPopWin.setBackgroundDrawable(drawable);
        return this;
    }

    public PopWinUtils setFocusableInTouchMode(boolean  flag){
        mPopWin.getContentView().setFocusableInTouchMode(flag);
        return this;
    }


    private PopupWindow getPopWin(int layout, final Activity activity, int wide, int high){
        return getPopWin(View.inflate(activity, layout, null),activity,wide,high);
    }

    @SuppressLint("WrongConstant")
    private PopupWindow getPopWin(View view, final Activity activity, int wide, int high){
        mLp = activity.getWindow().getAttributes();

        PopupWindow mPopupWindow = new PopupWindow(view, wide,high,true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.popwin_common_anim_style);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), (Bitmap) null));
        mPopupWindow.setOnDismissListener(() -> {
            startExitShadow();
            if (mOnDismissListener != null)mOnDismissListener.onDismiss();
        });
        return mPopupWindow;
    }

    public static boolean isShow() {
        for (PopWinUtils popWinUtils : sPopWinList) {
            if (popWinUtils.isShowing()) {
                return true;
            }
        }
        return false;
    }

    public interface OnDismissListener{
        void onDismiss();
    }
}
