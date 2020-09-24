package com.lx.xiaolongbao.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * author  : xiaolongbao
 * time    : 2020/8/26  上午9:34$
 * desc    : 居中对齐
 */
public class CenterAlignImageSpan extends ImageSpan {

    public CenterAlignImageSpan(Drawable drawable) {
        super(drawable);

    }
    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }
}
