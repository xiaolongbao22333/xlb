package com.lx.xiaolongbao.utils;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.lx.xiaolongbao.R;

import java.util.Calendar;


public class PickerViewUtils {
    private TimePickerView pickerView;
    private String receiptDate;
    private Calendar startDate;
    private Calendar endDate;

    public PickerViewUtils() {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }


    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    /**
     * @param activity    引用
     * @param startYear   开始年 如：2099
     * @param startMonth  开始月 如 0开始 11结束
     * @param startDay    开始天 如 30
     * @param endYear     结束年 如：2099
     * @param endMonth    结束月 如 0开始 11结束
     * @param endDay      结束天 如 30
     * @param layoutResId 自定义布局  R.layout.picker_date_options
     * @param yearV       年 显示
     * @param monthV      月 显示
     * @param dayV        天 显示
     * @param hoursV      时 显示
     * @param minsV       分 显示
     * @param secondsV    秒 显示
     * @param labelV      label 显示
     * @param isDialog    是否 以dialog状态
     */
    public void initPicker(Activity activity,
                           int startYear, int startMonth, int startDay,
                           int endYear, int endMonth, int endDay,
                           int layoutResId,
                           boolean yearV,
                           boolean monthV,
                           boolean dayV,
                           boolean hoursV,
                           boolean minsV,
                           boolean secondsV, boolean labelV, boolean isDialog) {

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        startDate.set(startYear, startMonth, startDay);
        endDate.set(endYear, endMonth, endDay);

        pickerView = new TimePickerBuilder(activity, (date, v) -> {//选中事件回调
            receiptDate = CommonUtils.getTime(date);
            if (pickerDismissListener != null) {
                pickerDismissListener.setReceiptDate(receiptDate);
            }

        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(layoutResId, v -> {
                    TextView tvSubmit = v.findViewById(R.id.tv_finish);

                    ImageView ivCancel = v.findViewById(R.id.iv_cancel);

                    tvSubmit.setOnClickListener(v1 -> {
                        pickerView.returnData();
                        pickerView.dismiss();
                    });
                    ivCancel.setOnClickListener(v12 -> pickerView.dismiss());
                })
                .setContentTextSize(18)
                .setType(new boolean[]{yearV, monthV, dayV, hoursV, minsV, secondsV})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(labelV) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDecorView(activity.getWindow().getDecorView().findViewById(android.R.id.content))
                .isDialog(isDialog)
                .setLineSpacingMultiplier(2.4f)
                .setContentTextSize(14)
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    public void showPicker() {
        pickerView.show();
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public TimePickerView getPickerView() {
        return pickerView;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public interface PickerDismissListener {
        void setReceiptDate(String receiptDate);
    }

    private PickerDismissListener pickerDismissListener;

    public void setPickerDismissListener(PickerDismissListener pickerDismissListener) {
        this.pickerDismissListener = pickerDismissListener;
    }
}
