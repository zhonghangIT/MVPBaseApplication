package com.example.zhonghang.baseapplication.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @param <T> 指定Presenter
 * @author zhonghang
 */
public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView<T> {
    /**
     * 对应的MVP中得Presenter,初始化在BaseActivity的onCreate中进行初始化，初始化的方法为initPresenter()
     */
    protected T mPresenter;
    /**
     * 对Fragment的getActivity()方法可能为空进行处理。getActivity()在Attach()方法和Detach()方法之间不为空。mParentActivity在Attach()中加入在onDestroy()方法时置空
     */
    protected FragmentActivity mParentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initViews(view);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 对控件进行初始化
     *
     * @param parentView 该fragment的view
     */
    protected abstract void initViews(View parentView);

    /**
     * 返回该界面的resId
     *
     * @return 返回该界面的resId
     */
    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mParentActivity = (FragmentActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mParentActivity = null;
    }

}
