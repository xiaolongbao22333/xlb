package com.lx.xiaolongbao.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


/**
 * 2017/10/28.
 */
public class ToastUtils {
    private static Context sContext;

    public static final String CHECK_UPDATE = "发现新版本,点击更新";
    public static final String NETWORK_REQUEST_TEXT = "当前无网络,请连接网络";
    public static final String NETWORK_REQUEST_PROMPT_FAIL = "请求失败";
    public static final String NETWORK_REQUEST_PROMPT_TIMEOUT = "连接超时";
    public static final String NETWORK_REQUEST_PARAMETER_ERROR = "请求参数错误";
    public static final String NETWORK_REQUEST_NOT_FOUND = "找不到服务器";
    public static final String NETWORK_REQUEST_SERVICE_ERROR = "服务器未响应";
    public static final String NETWORK_REQUEST_HTTP_ERROR = "网络异常";

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        Toasty.Config.getInstance()
            .allowQueue(false)
            .apply();
    }

    public static void show(String text) {
        show(text, false);
    }

    public static void show(int text) {
        show(sContext.getString(text), false);
    }


    public static void show(String text, boolean isTimeLong) {
        Toast.makeText(sContext, text, isTimeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }


    public static void i(Object text) {
        if (text == null) return;
        Toasty.info(sContext, text.toString(), Toast.LENGTH_SHORT, true).show();
    }

    public static void e(Object text) {
        if (text == null) return;
        Toasty.error(sContext, text.toString(), Toast.LENGTH_LONG, true).show();
    }


    public static void w(Object text) {
        if (text == null) return;
        Toasty.warning(sContext, text.toString(), Toast.LENGTH_SHORT, true).show();
    }

    public static void d(Object text) {
        if (text == null) return;
        Toasty.normal(sContext, text.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void success(Object text) {
        if (text == null) return;
        Toasty.success(sContext, text.toString(), Toast.LENGTH_SHORT, true).show();
    }


}
