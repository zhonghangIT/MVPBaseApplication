package com.cgt.yuanmeng.baseapplication.ui.splash;

import com.cgt.yuanmeng.baseapplication.base.IBasePresenter;
import com.cgt.yuanmeng.baseapplication.base.IBaseView;

/**
 * @author zhonghang
 * description:
 */
public class SplashContract {
    /**
     * 用于处理界面的业务逻辑，存放业务处理的方法
     */
    public interface View extends IBaseView<Presenter> {
        /**
         * 跳转到主界面
         */
        void startToMainActivity();
    }

    /**
     * 用于处理界面的改变，数据发生变化时通知界面做出对应的变化
     */
    public interface Presenter extends IBasePresenter<View> {
        /**
         * 启动页后延时操作
         */
        void delayedOperation();
    }
}
