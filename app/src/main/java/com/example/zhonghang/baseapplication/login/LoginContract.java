package com.example.zhonghang.baseapplication.login;

import com.example.zhonghang.baseapplication.base.IBasePresenter;
import com.example.zhonghang.baseapplication.base.IBaseView;

import java.util.List;

/**
 * @author zhonghang
 * time: 2018/11/7 下午4:14
 * description:
 */
public class LoginContract {
    interface Presenter extends IBasePresenter<View> {
        /**
         * 初始化数据
         */
        void init();
    }

    interface View extends IBaseView<Presenter> {
        /**
         * 展示数据
         * @param data 加载的数据
         */
        void showList(List<String> data);
    }
}
