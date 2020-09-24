package com.lx.xiaolongbao.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.utils.AutoUtils;
import com.lx.xiaolongbao.utils.DeviceInfo;


@SuppressLint("AppCompatCustomView")
public class MoreStyleImageView extends JImageView {


    /**
     * 圆形样式
     */
    public static final int STYLE_CIRCLE = 1;

    /**
     * 顶部两个圆角
     */
    public static final int STYLE_SEMIROUNDED_RECTANGLE = 2;

    /**
     * 四周圆角
     */
    public static final int STYLE_ROUNDED_RECTANGLE = 3;

    private int mRotationTime = 20 * 1000;

    private int mImageStyle = STYLE_SEMIROUNDED_RECTANGLE;

    private float mRadii = 20 * DeviceInfo.sAutoScaleX;

    private Paint mPaint;

    private ValueAnimator mValueAnimator;

    private float mAngle;

    private Bitmap mBitmap;
    private int mPadding;

    @IntDef({STYLE_CIRCLE, STYLE_SEMIROUNDED_RECTANGLE, STYLE_ROUNDED_RECTANGLE})
    public @interface ImageStyle{}


    public MoreStyleImageView(Context context) {
        this(context,null);
    }

    public MoreStyleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoreStyleImageView);
        mImageStyle = typedArray.getInt(R.styleable.MoreStyleImageView_JStyle, STYLE_CIRCLE);
        mPadding = (int) AutoUtils.getScaleWidth(typedArray.getDimension(R.styleable.MoreStyleImageView_JSrcPadding, 0));
        mRadii = typedArray.getDimension(R.styleable.MoreStyleImageView_JRadian, 20 * DeviceInfo.sAutoScaleX);
        boolean isAnimate = typedArray.getBoolean(R.styleable.MoreStyleImageView_JAnimate, false);

        typedArray.recycle();
        init();

        setRotationEnable(isAnimate);
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setDither(true);

        initAnimator();
    }

    /**
     * 设置图片显示的样式
     * @param imageStyle
     */
    public void setImageStyle(@ImageStyle int imageStyle){
        mImageStyle = imageStyle;
        invalidate();
    }

    /**
     * 设置圆角的弧度
     * @param radii
     */
    public void setRadii(int radii){
        mRadii = radii;
    }

    public void setRotationEnable(boolean enable){
        setRotationEnable(enable, true);
    }

    public void setRotationEnable(boolean enable, boolean isHome){
        if (enable){
            mAngle = 0;
            mValueAnimator.start();
        }else {
            mAngle = isHome ? 0 : mAngle;
            mValueAnimator.cancel();
        }
    }


    /**
     * 默认旋转周期为20秒一次
     * @param time
     */
    public void setRotationTime(int time){
        mRotationTime = time;
    }

    private void initAnimator(){
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(mRotationTime);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            mAngle = ((Float) valueAnimator.getAnimatedValue());
            postInvalidate();
        }
    };


    @Override
    public void setImageBitmap(Bitmap bm) {
        mBitmap = null;
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        mBitmap = null;
        super.setImageResource(resId);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        mBitmap = null;
        super.setImageDrawable(drawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (getDrawable() == null) return;

        if (mBitmap == null){
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            if (bitmap == null) return;
            mBitmap = getBitmap(bitmap);
        }

        canvas.rotate(mAngle * 360,getWidth() / 2,getHeight() / 2);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }



    private Bitmap getBitmap(Bitmap bitmap){

        initPaint();

        mRadii = mRadii < 0 ? 0 : mRadii;

        Bitmap output = Bitmap.createBitmap(getWidth(), getHeight() , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Rect rectSrc = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rectDest = new Rect(0, 0, getWidth(), getHeight() );

        canvas.drawARGB(0, 0, 0, 0);

        if (mImageStyle == STYLE_CIRCLE) drawCircleBitmap(canvas);
        if (mImageStyle == STYLE_SEMIROUNDED_RECTANGLE) drawSemiroundedRectangleBitmap(canvas);
        if (mImageStyle == STYLE_ROUNDED_RECTANGLE) drawRoundedCornersBitmap(canvas);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rectSrc, rectDest, mPaint);

        mPaint.setXfermode(null);

        return output;
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
    }

    /**
     * 绘制圆形图片
     * @return Bitmap
     * @author caizhiming
     */
    private void drawCircleBitmap(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mPadding, mPaint);
    }

    /**
     * 绘制半圆角的图片
     * @return
     */
    private void drawSemiroundedRectangleBitmap(Canvas canvas){
        Path path  = new Path();
        path.moveTo(0,getHeight());
        path.lineTo(0,mRadii);
        path.quadTo(0,0,mRadii,0);
        path.lineTo(getWidth() - mRadii,0);
        path.quadTo(getWidth(),0,getWidth(),mRadii);
        path.lineTo(getWidth(),getHeight());
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 绘制圆角的图片
     * @return
     */
    private void drawRoundedCornersBitmap(Canvas canvas){
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rectF,mRadii,mRadii, mPaint);
    }
}
