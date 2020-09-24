package com.lx.xiaolongbao.utils;

import android.os.Handler;
import android.os.Looper;


public class ThreadUtils {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void start(Runnable runnable){
        new Thread(runnable).start();
    }

    public static void postDelay(Runnable runnable, long delay){
        sHandler.postDelayed(runnable, delay);
    }

    public static void post(Runnable runnable){
        postDelay(runnable, 0);
    }

    public static void removeCallbacks(Runnable runnable){
        sHandler.removeCallbacks(runnable);
    }
}
