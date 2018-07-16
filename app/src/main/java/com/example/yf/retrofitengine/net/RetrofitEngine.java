package com.example.yf.retrofitengine.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.yf.retrofitengine.BuildConfig;
import com.example.yf.retrofitengine.net.cookie.PersistentCookieJar;
import com.example.yf.retrofitengine.net.cookie.cache.SetCookieCache;
import com.example.yf.retrofitengine.net.cookie.persistence.SharedPrefsCookiePersistor;
import com.example.yf.retrofitengine.net.intercepter.FilterFastRequestInterceptor;
import com.example.yf.retrofitengine.net.intercepter.ForceCacheInterceptor;
import com.example.yf.retrofitengine.net.intercepter.LogIntercepter;
import com.example.yf.retrofitengine.net.util.ProgressInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yf on 2017/11/28.
 * Email：yunfei10306@163.com
 * 特点：
 * ① 支持无网络时数据缓存，无需服务器的支持(仅限GET请求)
 * ② 支持过滤恶意频繁网络请求，减轻服务器压力
 * ③ 支持cookie头数据的自动加载及持久化
 * ④ 支持版本更新
 */
public class RetrofitEngine {

    //缓存的默认大小 5M 根据需要修改
    private static final int DEFAULT_CACHE_SIZE = 1024 * 1024 * 5;

    //缓存的默认文件夹
    private static final String DEFAULT_CACHE_FILE = "okhttp_cache";

    //缓存cookie及加载cookie的工具类，内存中缓存，初始化时，会利用SharedPrefsCookiePersistor将持久化的cookie加载到内存
    private static PersistentCookieJar cookieJar;

    //持久化cookie的工具类，利用sp实现
    private static SharedPrefsCookiePersistor cookiePersistor;

    private static OkHttpClient okHttpClient;
    private static OkHttpClient okHttpClientForDownload;
    private static Retrofit retrofit;
    private static Retrofit retrofitForDownload;

    private RetrofitEngine() {
    }

    /**
     * 获取retrofit 实例
     *
     * @param context
     * @return retrofit实例
     */
    public static Retrofit getInstance(Context context) {
        if (okHttpClient == null) {
            initOkhttpClient(context);
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.it120.cc/")
                    //.baseUrl("https://api.douban.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getInstanceForDownload() {
        if (okHttpClientForDownload == null) {
            initOkhttpClientForDownload();
        }
        if (retrofitForDownload == null) {
            retrofitForDownload = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClientForDownload)
                    .build();
        }

        return retrofitForDownload;
    }

    /**
     * 初始化 okhttpClient
     *
     * @param context
     */
    private static void initOkhttpClient(Context context) {
        cookiePersistor = new SharedPrefsCookiePersistor(context);
        cookieJar = new PersistentCookieJar(new SetCookieCache(), cookiePersistor);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //支持自动持久化cookie和自动添加cookie
                .cookieJar(cookieJar)
                //错误重连
                .retryOnConnectionFailure(true)
                //没有网络，加载缓存(仅限GET)
                .addInterceptor(new ForceCacheInterceptor(context))
                //添加请求头(用时注意打开)
                //.addInterceptor(new HeadIntercepter())
                //过滤频繁请求，5s为缓存时间，单位秒,5秒之内反复请求，取缓存，超出5秒，取服务器数据
                .addNetworkInterceptor(new FilterFastRequestInterceptor(5))
                //缓存
                .cache(new Cache(new File(context.getCacheDir(), DEFAULT_CACHE_FILE), DEFAULT_CACHE_SIZE));
        //打印请求日志
        if (BuildConfig.DEBUG) {
            //builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            builder.addInterceptor(new LogIntercepter());
        }
        okHttpClient = builder.build();
    }

    private static void initOkhttpClientForDownload() {
        okHttpClientForDownload = new OkHttpClient.Builder().connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new ProgressInterceptor()).build();
    }

    /**
     * 退出登录时，清除cookie数据
     */
    public void logout() {
        cookieJar.clear();
    }

    /**
     * 判断是否处于登录状态
     *
     * @param loginUrl
     * @return
     */
    public boolean isLogin(String loginUrl) {
        return cookiePersistor.isLogin(loginUrl);
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
