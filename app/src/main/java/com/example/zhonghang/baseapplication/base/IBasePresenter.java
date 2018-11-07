package com.example.zhonghang.baseapplication.base;

/**
 * @author zhonghang
 * time: 2018/11/7 下午4:00
 * description:
 */
public interface IBasePresenter<T extends IBaseView> {
    void attachView(T view);

    void detachView();
}
