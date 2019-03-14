package com.cgt.yuanmeng.baseapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.cgt.yuanmeng.baseapplication.R;
import com.cgt.yuanmeng.baseapplication.base.BaseActivity;
import com.cgt.yuanmeng.baseapplication.databinding.ActivityMainBinding;
import com.cgt.yuanmeng.baseapplication.ui.main.activities.ActivitiesFragment;
import com.cgt.yuanmeng.baseapplication.ui.main.home.HomeFragment;
import com.cgt.yuanmeng.baseapplication.ui.main.infomation.InfomationFragment;
import com.cgt.yuanmeng.baseapplication.ui.main.lottery.LotteryFragment;

/**
 * @author zhonghang
 * description:
 */
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener {
    private HomeFragment homeFragment;
    private InfomationFragment infomationFragment;
    private ActivitiesFragment activitiesFragment;
    private LotteryFragment lotteryFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ActivityMainBinding mainBinding;

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
        return R.layout.activity_download;
    }

    @Override
    public void initViews() {
        mainBinding = (ActivityMainBinding) binding;
        mainBinding.bottomNavigation.inflateMenu(R.menu.navigation);
        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(this);
        setSupportActionBar(mainBinding.toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void initData() {
        homeFragment = HomeFragment.newInstance();
        infomationFragment = InfomationFragment.newInstance();
        activitiesFragment = ActivitiesFragment.newInstance();
        lotteryFragment = LotteryFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_content, homeFragment)
                .add(R.id.frame_content, infomationFragment)
                .add(R.id.frame_content, activitiesFragment)
                .add(R.id.frame_content, lotteryFragment)
                .hide(infomationFragment)
                .hide(activitiesFragment)
                .hide(lotteryFragment)
                .show(homeFragment).commit();
        mainBinding.textTitle.setText(R.string.title_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (!menuItem.isChecked()) {
            Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
            fragmentTransaction = fragmentManager.beginTransaction().hide(homeFragment).hide(infomationFragment).hide(lotteryFragment).hide(activitiesFragment);
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.show(homeFragment);
                    break;
                case R.id.navigation_activities:
                    fragmentTransaction.show(activitiesFragment);
                    break;
                case R.id.navigation_lottery:
                    fragmentTransaction.show(lotteryFragment);
                    break;
                case R.id.navigation_infomation:
                    fragmentTransaction.show(infomationFragment);
                    break;
                default:
                    break;
            }
            fragmentTransaction.commit();
            mainBinding.textTitle.setText(menuItem.getTitle());
            return true;
        }
        return false;
    }
}
