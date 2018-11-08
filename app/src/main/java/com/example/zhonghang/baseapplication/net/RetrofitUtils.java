package com.example.zhonghang.baseapplication.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhonghang
 * time: 2018/11/8 下午4:50
 * description:
 */
public class RetrofitUtils {
    private RetrofitUtils() {
        initRetrofit();
    }

    public static RetrofitUtils instance;

    public static RetrofitUtils newInstance() {
        if (instance == null) {
            instance = new RetrofitUtils();
        }
        return instance;
    }

    private Retrofit mRetrofit;
    private Gson mGson;

    private void initGson() {
        mGson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    private void initRetrofit() {
        initGson();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    public <T> T createRetrifitApi(Class<T> service) {
        return mRetrofit.create(service);
    }
}
