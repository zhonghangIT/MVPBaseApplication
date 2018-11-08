package com.example.zhonghang.baseapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.frescolibrary.FrescoImageUtils;
import com.example.zhonghang.baseapplication.media.image.GlideApp;

/**
 * @author zhonghang
 */
public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageview);

//        ![](https://ws3.sinaimg.cn/large/006tNbRwly1fwz8lyciwaj30hq0vkgmd.jpg)
        GlideApp.with(this).load("https://ws3.sinaimg.cn/large/006tNbRwly1fwz8lyciwaj30hq0vkgmd.jpg").into(imageView);
    }

}
