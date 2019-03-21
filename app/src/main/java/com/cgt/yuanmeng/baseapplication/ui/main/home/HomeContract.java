package com.cgt.yuanmeng.baseapplication.ui.main.home;

import com.cgt.yuanmeng.baseapplication.base.IBasePresenter;
import com.cgt.yuanmeng.baseapplication.base.IBaseView;

/**
 * @author zhonghang
 * description:
 */
public class HomeContract {
    /**
     * 用于处理界面的业务逻辑，存放业务处理的方法
     */
    public interface View extends IBaseView<Presenter> {

    }

    /**
     * 用于处理界面的改变，数据发生变化时通知界面做出对应的变化
     */
    public interface Presenter extends IBasePresenter<View> {
        void print();
    }
}
