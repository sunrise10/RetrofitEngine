package com.example.yf.retrofitengine.net.intercepter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * 判断当前网络状况，如果有网络，则不强制加载缓存，否则强制加载缓存的拦截器
 * 仅限GET请求
 *
 */
public class ForceCacheInterceptor implements Interceptor {
    private Context mContext;

    public ForceCacheInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!checkNetwork(mContext) && "GET".equals(request.method().toUpperCase())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            return chain.proceed(request);
        }
        return chain.proceed(request);
    }

    /**
     * 检查网络有无连接的状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
