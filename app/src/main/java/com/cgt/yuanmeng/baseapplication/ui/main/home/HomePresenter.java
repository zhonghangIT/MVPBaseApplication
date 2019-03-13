package com.cgt.yuanmeng.baseapplication.ui.main.home;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhonghang
 * description:
 */
public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private CompositeDisposable compositeDisposable;

    public HomePresenter() {

    }

    @Override
    public void detachView() {
        //此处用于销毁网络连接，或者查询数据库的操作，耗时操作导致view不能被正常回收导致内存泄漏
        compositeDisposable.clear();
        mView = null;
    }

    @Override
    public void attachView(HomeContract.View view) {
        mView = view;
        compositeDisposable = new CompositeDisposable();
    }
}