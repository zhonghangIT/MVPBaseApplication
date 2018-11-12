package com.example.zhonghang.baseapplication.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zhonghang.baseapplication.R;
import com.example.zhonghang.baseapplication.base.BaseActivity;
import com.example.zhonghang.baseapplication.databinding.ActivityMainBinding;

/**
 * @author zhonghang
 * description:
 */
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MainContract.Presenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        ActivityMainBinding mainBinding = (ActivityMainBinding) binding;
        mainBinding.bottomNavigation.inflateMenu(R.menu.navigation);
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (!menuItem.isChecked()) {
            Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
