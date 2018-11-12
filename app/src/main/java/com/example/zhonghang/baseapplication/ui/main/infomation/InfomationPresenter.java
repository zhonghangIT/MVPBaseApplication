package com.example.zhonghang.baseapplication.ui.main.infomation;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author zhonghang
 * description:
 */
public class InfomationPresenter implements InfomationContract.Presenter {
    private InfomationContract.View mView;
    private CompositeDisposable compositeDisposable;

    public InfomationPresenter() {

    }

    @Override
    public void detachView() {
        //此处用于销毁网络连接，或者查询数据库的操作，耗时操作导致view不能被正常回收导致内存泄漏
        compositeDisposable.clear();
        mView = null;
    }

    @Override
    public void attachView(InfomationContract.View view) {
        mView = view;
        compositeDisposable = new CompositeDisposable();
    }
}