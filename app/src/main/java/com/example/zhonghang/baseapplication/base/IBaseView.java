package com.example.zhonghang.baseapplication.base;

/**
 * @author zhonghang
 * time: 2018/11/7 下午3:59
 * description:
 */
public interface IBaseView<T extends IBasePresenter>{
    T initPresenter();
}
