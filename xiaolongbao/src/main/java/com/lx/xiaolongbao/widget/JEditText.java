package com.lx.xiaolongbao.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import androidx.annotation.StringRes;

import com.lx.xiaolongbao.R;
import com.lx.xiaolongbao.utils.AutoUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class JEditText extends EditText implements ViewTreeObserver.OnGlobalLayoutListener {

    private OnChangedAfterTextListener mOnChangedAfterTextListener;
    private OnKeyboardStatusListener mOnKeyboardStatusListener;
    private OnCursorVisibilitysListener mOnCursorVisibilitysListener;
    private boolean isSoftKeyboardOpened;
    private boolean mCursorEnd;
    private float mHintSize;
    private int mHintTextStyle;
    private String mOldText;

    private int mJInputType;

    private Typeface mTypeface;
    private Typeface mHintTypeface;
    private static class TextStyle{
        /* 粗体 */
        public static final int BOLD = 1;
        /* 斜体 */
        public static final int ITALIC = 2;
        /* 常规 */
        public static final int NORMAL = 3;
    }

    public static class JInputType{
        //金额到分
        public static final int MONEY = 1;
        public static final int MONEY_INT = 2;
    }


    public JEditText(Context context) {
        super(context);
    }

    public JEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JEditText);
        parseAttr(typedArray);
        typedArray.recycle();
    }

    public JEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void parseAttr(TypedArray typedArray){
        mCursorEnd = typedArray.getBoolean(R.styleable.JEditText_JCursorEnd, false);

        mHintSize = AutoUtils.auto(typedArray.getDimension(R.styleable.JEditText_JHintSize, 0));
        int textStyle = typedArray.getInt(R.styleable.JEditText_JHintTextStyle, 0);
        mJInputType = typedArray.getInt(R.styleable.JEditText_JInputType, 0);

        if (textStyle == TextStyle.BOLD){
            mHintTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        }else if (textStyle == TextStyle.ITALIC){
            mHintTypeface = Typeface.defaultFromStyle(Typeface.ITALIC);
        }else if (textStyle == TextStyle.NORMAL){
            mHintTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
        }


        mTypeface = getTypeface();

        if (textStyle != 0){
            setOnChangedAfterTextListener(text -> { });
            getPaint().setTypeface(mHintTypeface);
        }

        if (mJInputType == JInputType.MONEY){
            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            setFilters(new InputFilter[]{new EditInputFilter()});
        }else if (mJInputType == JInputType.MONEY_INT){
            setInputType(InputType.TYPE_CLASS_NUMBER);
            setFilters(new InputFilter[]{new EditInputFilter()});
        }
    }

    public static class EditInputFilter implements InputFilter {

        /**
         * 最大数字
         */
        private long MAX_VALUE = 999999;

        /**
         * 小数点后的数字的位数
         */
        public static final int POINTER_LENGTH = 2;

        private static final String POINTER = ".";

        Pattern p;

        public EditInputFilter() {
            //用于匹配输入的是0-9  .  这几个数字和字符
            p = Pattern.compile("([0-9]|\\.)*");
        }

        public EditInputFilter(long MAX_VALUE) {
            this.MAX_VALUE = MAX_VALUE;
            p = Pattern.compile("([0-9]|\\.)*");
        }


        /**
         * source    新输入的字符串
         * start    新输入的字符串起始下标，一般为0
         * end    新输入的字符串终点下标，一般为source长度-1
         * dest    输入之前文本框内容
         * dstart    原内容起始坐标，一般为0
         * dend    原内容终点坐标，一般为dest长度-1
         */

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {

            String sourceText = source.toString();
            String destText = dest.toString();
//验证删除等按键
            if (TextUtils.isEmpty(sourceText)) {
                if (dstart == 0 && destText.indexOf(POINTER) == 1) {//保证小数点不在第一个位置
                    return "0";
                }
                return "";
            }
            Matcher matcher = p.matcher(source);
            //已经输入小数点的情况下，只能输入数字
            if (destText.contains(POINTER)) {
                if (!matcher.matches()) {
                    return "";
                } else {
                    if (POINTER.equals(source)) { //只能输入一个小数点
                        return "";
                    }
                }
                //验证小数点精度，保证小数点后只能输入两位
                int index = destText.indexOf(POINTER);
                int length = destText.trim().length() - index;
                if (length > POINTER_LENGTH && dstart > index) {
                    return "";
                }
            } else {
                //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
                if (!matcher.matches()) {
                    return "";
                } else {
                    if ((POINTER.equals(source)) && dstart == 0) {//第一个位置输入小数点的情况
                        return "0.";
                    } else if ("0".equals(source) && dstart == 0){
                        //用于修复能输入多位0
                        return "";
                    }
                }
            }
//        dstart
            //修复当光标定位到第一位的时候 还能输入其他的    这个是为了修复以下的情况
            /**
             * <>
             *     当如下情况的时候  也就是 已经输入了23.45   这个时候限制是500元
             *     那么这个时候如果把光标移动2前面  也就是第0位  在输入一个5  那么这个实际的参与下面的
             *     判断的sumText > MAX_VALUE  是23.455  这个是不大于 500的   但是实际情况是523  这个时候
             *     已经大于500了  所以之前的是存在bug的   这个要进行修正 也就是拿到的比较数应该是523.45  而不是23.455
             *     所以有了下面的分隔  也就是  把23.45  (因为这个时候dstart=0)  分隔成 ""  和23.45  然后把  5放到中间
             *     进行拼接 也就是  "" + 5 + 23.45  也就是523.45  然后在进行和500比较
             *     还有一个比较明显的就是   23.45   这个时候光标在2和3 之间  那么如果修正之前  是23.455   修正之后  dstart = 1
             *     这个时候分隔是 "2"  "3.45"   这个时候拼接是253.45  然后和500比较  以此类推
             * </>
             */
            String first = destText.substring(0,dstart);

            String second = destText.substring(dstart,destText.length());
//        dend
            String sum = first + sourceText + second;
            //验证输入金额的大小
            double sumText = Double.parseDouble(sum);
            //这里得到输入完之后需要计算的金额  如果这个金额大于了事先设定的金额,那么久直接返回  不需要加入输入的字符
            if (MAX_VALUE != -1 && sumText > MAX_VALUE) {
                //
                return dest.subSequence(dstart, dend);
            }
            //如果输入的金额小于事先规定的金额
            return dest.subSequence(dstart, dend) + sourceText;
        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        if (TextUtils.isEmpty(text) && mHintTypeface != null){
            getPaint().setTypeface(mHintTypeface);
        }
    }

    @Override
    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
    }

    @Override
    public void setCursorVisible(boolean visible) {
        super.setCursorVisible(visible);
        if (mOnCursorVisibilitysListener != null){
            mOnCursorVisibilitysListener.onVisibilitys(visible);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public String getString(){
        return getText().toString();
    }

    public void text(CharSequence text){
        super.setText(text);
    }

    public void text(@StringRes int resid){
        try {
            super.setText(resid);
        }catch (Resources.NotFoundException e){
            super.setText(String.valueOf(resid));
        }
    }

    public void notEdit(boolean notEdit) {
        setEnabled(!notEdit);
     /*   if (notEdit){
            setFocusableInTouchMode(false);
            setFocusable(false);
            setFilters(new InputFilter[] {(source, start, end, dest, dstart, dend) -> source.length() < 1 ? dest.subSequence(dstart, dend) : ""});
        }else {
            setFocusableInTouchMode(true);
            setFocusable(true);
            requestFocus();
            setFilters(new InputFilter[] {(source, start, end, dest, dstart, dend) -> null});
        }*/
    }

    public String text(){
        return getText().toString();
    }



    public int getStrLength(){
        return getString().trim().length();
    }

    public boolean isEmpty(){
        return getStrLength() == 0;
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

    public void addTextChangedListener(JTextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    public void setOnChangedAfterTextListener(OnChangedAfterTextListener onChangedAfterTextListener){
        addTextChangedListener(new JTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()){
                    getPaint().setTypeface(mHintTypeface);
                }else {
                    getPaint().setTypeface(mTypeface);
                }

                if (mOnChangedAfterTextListener != null){
                    mOnChangedAfterTextListener.onChanged(s.toString());

                }
            }
        });

        mOnChangedAfterTextListener = onChangedAfterTextListener;
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (mCursorEnd && selStart == selEnd) {
            if (getText().length() == 0) {
                setSelection(0);
            } else {
                setSelection(getText().length());
            }

        }
    }

    public interface OnChangedAfterTextListener{
        void onChanged(String text);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (getContext() instanceof BaseToolsActivity) {
//            ((BaseToolsActivity) getContext()).cancelHideInputKey();
//        }
        return super.onTouchEvent(event);
    }

    public abstract static class JTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s){
        }
    }

    public interface OnKeyboardStatusListener{
        void keyboardStatus(boolean open);
    }

    public interface OnCursorVisibilitysListener{
        void onVisibilitys(boolean visible);
    }


    public void setOnKeyboardStatusListener(OnKeyboardStatusListener onKeyboardStatusListener){
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        mOnKeyboardStatusListener = onKeyboardStatusListener;
    }

    public void setOnCursorVisibilitysListener(OnCursorVisibilitysListener onCursorVisibilitysListener){
        mOnCursorVisibilitysListener = onCursorVisibilitysListener;
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        getWindowVisibleDisplayFrame(r);
        final int heightDiff = getRootView().getHeight() - (r.bottom - r.top);
        if (!isSoftKeyboardOpened && heightDiff > 100) {
            isSoftKeyboardOpened = true;
            if (mOnKeyboardStatusListener != null) {
                mOnKeyboardStatusListener.keyboardStatus(true);
            }
        } else if (isSoftKeyboardOpened && heightDiff < 100) {
            isSoftKeyboardOpened = false;
            if (mOnKeyboardStatusListener != null) {
                mOnKeyboardStatusListener.keyboardStatus(false);
            }

        }
    }
}
