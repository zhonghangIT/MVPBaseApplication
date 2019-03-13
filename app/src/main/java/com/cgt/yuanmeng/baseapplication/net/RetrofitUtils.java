package com.cgt.yuanmeng.baseapplication.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .addHeader("Accept-Encoding", "gzip")
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json; charset=utf-8")
                                .method(originalRequest.method(), originalRequest.body());
//                        requestBuilder.addHeader("Authorization", "Bearer " + BaseConstant.TOKEN);//添加请求头信息，服务器进行token有效性验证
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();
        initGson();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
    }

    public <T> T createRetrofitApi(Class<T> service) {
        return mRetrofit.create(service);
    }
}
