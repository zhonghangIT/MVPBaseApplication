package com.example.zhonghang.baseapplication.login;

import android.os.Bundle;

import com.example.zhonghang.baseapplication.R;
import com.example.zhonghang.baseapplication.base.BaseActivity;

import java.util.List;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter.init();
    }

    @Override
    public LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showList(List<String> data) {
        //TODO 得到数据后进行展示
    }
}
