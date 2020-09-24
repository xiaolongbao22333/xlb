package com.lx.xiaolongbao.app;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * @ClassName: BaseApplication
 * @Author: 冻品
 * @CreateDate: 2020-05-22 09:09
 * @UpdateUser: 小笼包
 * @Version: 1.0
 * @Description: java类作用描述
 */
public abstract class BaseApplication extends MultiDexApplication {
    public static boolean RELEASE_MODE = true;

    private static BaseApplication sInstance;

    public static Context getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag("xiaolongbao")
                .showThreadInfo(false)
                .methodOffset(1)
                .methodCount(1)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return super.isLoggable(priority, tag);
            }
        });

        init();
    }

    protected abstract void init();
}
