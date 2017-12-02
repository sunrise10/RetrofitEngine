package com.example.yf.retrofitengine.net.intercepter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 过滤下拉刷新时的频繁操作，减少服务器的压力的拦截器
 * 仅限GET请求
 */
public class FilterFastRequestInterceptor implements Interceptor {
    //单位 秒
    private int maxAge;

    public FilterFastRequestInterceptor(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if ("GET".equals(request.method().toUpperCase())) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
        return response;
    }
}
