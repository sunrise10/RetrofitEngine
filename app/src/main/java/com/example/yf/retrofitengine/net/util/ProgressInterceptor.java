package com.example.yf.retrofitengine.net.util;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by yf on 2018/1/23.
 * Email：yunfei10306@163.com
 * 描述：下载进度监听
 */

public class ProgressInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new FileDownloadResponseBody(originalResponse.body()))
                .build();
    }
}
