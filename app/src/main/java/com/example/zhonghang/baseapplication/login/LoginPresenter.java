package com.example.zhonghang.baseapplication.login;

import com.example.zhonghang.baseapplication.base.IBaseView;

/**
 * @author zhonghang
 * time: 2018/11/7 下午4:15
 * description:
 */
public class LoginPresenter implements LoginContract.Presenter {
    LoginContract.View mView;

    @Override
    public void attachView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void init() {
    }
}
