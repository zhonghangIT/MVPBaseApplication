package com.cgt.yuanmeng.baseapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.image.frescolibrary.FrescoImageUtils;


/**
 * @author zhonghang
 * time: 2018/11/8 下午3:17
 * description:
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication sBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;
        FrescoImageUtils.init(this);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 得到主进程的引用
     *
     * @return 主进程引用
     */
    public static BaseApplication getBaseApplication() {
        return sBaseApplication;
    }
}
