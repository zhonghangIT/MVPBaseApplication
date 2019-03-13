package com.cgt.yuanmeng.baseapplication.base;

/**
 * @author zhonghang
 * time: 2018/11/7 下午4:00
 * description:
 */
public interface IBasePresenter<T extends IBaseView> {
    /**
     * 绑定界面的方法
     *
     * @param view 要绑定的界面
     */
    void attachView(T view);

    /**
     * 解绑界面的方法
     */
    void detachView();
}
