package com.cgt.yuanmeng.baseapplication.ui.main.lottery;

import android.os.Bundle;
import android.view.View;

import com.cgt.yuanmeng.baseapplication.R;
import com.cgt.yuanmeng.baseapplication.base.BaseFragment;

/**
 * @author zhonghang
 * description:
 */
public class LotteryFragment extends BaseFragment<LotteryContract.Presenter> implements LotteryContract.View {

    /**
     * 创建一个静态方法用于创建fragment的对象
     * 主要是在参数列表中传递初始化fragment需要的数据
     *
     * @return 一个该对象的新的实例
     */
    public static LotteryFragment newInstance() {
        LotteryFragment fragment = new LotteryFragment();
        Bundle args = new Bundle();
        //创建Fragment需要传递的参数
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //创建时传递进来的参数，在此处能够取到
        }
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lottery;
    }

    @Override
    public LotteryContract.Presenter initPresenter() {
        return new LotteryPresenter();
    }
}
