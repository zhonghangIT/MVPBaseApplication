package com.cgt.yuanmeng.baseapplication.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author zhonghang
 * time: 2018/11/8 下午4:54
 * description:
 */
public interface CgtRetrofitApi {
    /**
     * 登录方法
     *
     * @param userName 登录用户名
     * @return 登录成功与否的回调
     */
    @GET()
    Observable<String> login(String userName);
}
