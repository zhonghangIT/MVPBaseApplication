package com.example.zhonghang.baseapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.frescolibrary.FrescoImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author zhonghang
 */
public class MainActivity extends AppCompatActivity {
    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        draweeView = findViewById(R.id.draweeview);

//        ![](https://ws3.sinaimg.cn/large/006tNbRwly1fwz8lyciwaj30hq0vkgmd.jpg)
        FrescoImageUtils.getInstance()
                .createWebImageParamsBuilder(getApplicationContext(), "https://ws3.sinaimg.cn/large/006tNbRwly1fwz8lyciwaj30hq0vkgmd.jpg")
                .build().showWebImage(draweeView);

    }

}
