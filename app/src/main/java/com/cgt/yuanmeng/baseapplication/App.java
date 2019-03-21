package com.cgt.yuanmeng.baseapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.image.frescolibrary.FrescoImageUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * @author zhonghang
 * time: 2018/11/8 下午3:17
 * description:
 */
public class App extends MultiDexApplication {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        FrescoImageUtils.init(this);
//        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new DiskLogAdapter());

    }

    /**
     * 得到主进程的引用
     *
     * @return 主进程引用
     */
    public static App getApp() {
        return app;
    }
}
