package com.example.yf.retrofitengine.net.intercepter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yf on 2017/11/28.
 * Email：yunfei10306@163.com
 * 添加请求头
 */

public class HeadIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("yourRequestHead1", "yourRequestHead1")
                .addHeader("yourRequestHead2", "yourRequestHead2")
                .addHeader("yourRequestHead3", "yourRequestHead3")
                .build();
        return chain.proceed(request);
    }
}
