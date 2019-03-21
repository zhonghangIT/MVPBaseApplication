package com.cgt.yuanmeng.baseapplication.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.cgt.yuanmeng.baseapplication.R;
import com.cgt.yuanmeng.baseapplication.base.BaseFragment;
import com.cgt.yuanmeng.baseapplication.databinding.FragmentHomeBinding;
import com.cgt.yuanmeng.baseapplication.print.DeviceConnFactoryManager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author zhonghang
 * description:
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, View.OnClickListener {
    FragmentHomeBinding mHomeBinding;

    /**
     * 创建一个静态方法用于创建fragment的对象
     * 主要是在参数列表中传递初始化fragment需要的数据
     *
     * @return 一个该对象的新的实例
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        mHomeBinding = (FragmentHomeBinding) mBinding;
        mHomeBinding.btnPrint.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeContract.Presenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_print:
                mPresenter.print();
                break;
            default:
                break;
        }
    }
}
