package com.cgt.yuanmeng.baseapplication.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.cgt.yuanmeng.baseapplication.R;
import com.cgt.yuanmeng.baseapplication.base.BaseActivity;
import com.cgt.yuanmeng.baseapplication.ui.main.MainActivity;

/**
 * @author zhonghang
 * description:
 */
public class SplashActivity extends BaseActivity<SplashContract.Presenter> implements SplashContract.View {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public SplashContract.Presenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.delayedOperation();
    }

    @Override
    public void startToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
