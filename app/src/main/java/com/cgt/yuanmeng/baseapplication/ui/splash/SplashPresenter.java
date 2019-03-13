package com.cgt.yuanmeng.baseapplication.ui.splash;

import com.cgt.yuanmeng.baseapplication.net.CgtRetrofitApi;
import com.cgt.yuanmeng.baseapplication.net.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhonghang
 * description:
 */
public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.View mView;
    private CompositeDisposable compositeDisposable;

    public SplashPresenter() {

    }

    @Override
    public void detachView() {
        //此处用于销毁网络连接，或者查询数据库的操作，耗时操作导致view不能被正常回收导致内存泄漏
        compositeDisposable.clear();
        mView = null;
    }

    @Override
    public void attachView(SplashContract.View view) {
        mView = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void delayedOperation() {
        compositeDisposable.add(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    Thread.sleep(2000);
                    //休眠两秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.onNext("test");
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        mView.startToMainActivity();
                    }
                }));
    }
}
