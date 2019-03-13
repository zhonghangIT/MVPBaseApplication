package com.cgt.yuanmeng.baseapplication.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @param <T> 指定Presenter
 * @author zhonghang
 */
public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView<T> {
    /**
     * 对应的MVP中得Presenter,初始化在BaseActivity的onCreate中进行初始化，初始化的方法为initPresenter()
     */
    protected T mPresenter;
    /**
     * 使用databinding进行数据的绑定
     */
    protected ViewDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        mPresenter = initPresenter();
        mPresenter.attachView(this);
        initViews();
        initData();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 得到界面的resId
     *
     * @return 得到界面的resId
     */
    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //删除绑定
        mPresenter.detachView();
    }
}