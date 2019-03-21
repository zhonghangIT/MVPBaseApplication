package com.cgt.yuanmeng.baseapplication.ui.main;

import com.cgt.yuanmeng.baseapplication.base.IBasePresenter;
import com.cgt.yuanmeng.baseapplication.base.IBaseView;

/**
 * @author zhonghang
 * description:
 */
public class MainContract {
    /**
     * 用于处理界面的业务逻辑，存放业务处理的方法
     */
    public interface View extends IBaseView<Presenter> {
        /**
         * 设置打印机状态
         *
         * @param state 状态值
         */
        void setPrintState(String state);
    }

    /**
     * 用于处理界面的改变，数据发生变化时通知界面做出对应的变化
     */
    public interface Presenter extends IBasePresenter<View> {
        /**
         * 连接打印机
         */
        void connectPrint();
    }
}
