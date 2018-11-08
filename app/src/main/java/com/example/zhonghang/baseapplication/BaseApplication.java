package com.example.zhonghang.baseapplication;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.example.frescolibrary.FrescoImageUtils;

/**
 * @author zhonghang
 * time: 2018/11/8 下午3:17
 * description:
 */
public class BaseApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FrescoImageUtils.init(this);
    }
}
