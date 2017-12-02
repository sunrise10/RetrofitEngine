package com.example.yf.retrofitengine.model.net.home;

import android.content.Context;

import com.example.yf.retrofitengine.model.bean.homeBean.response.MovieBean;
import com.example.yf.retrofitengine.net.RetrofitEngine;
import com.example.yf.retrofitengine.net.util.RxUtil;

import io.reactivex.Observable;

/**
 * Created by yf on 2017/11/29.
 * 描述：联网接口的代理实现类
 */

public class HomeModel {

    private HomeApi homeApi;

    public HomeModel(Context context) {
        homeApi = RetrofitEngine.getInstance(context).create(HomeApi.class);
    }

    public Observable<MovieBean> getMovies(String name) {
        return homeApi.getMovies(name).compose(RxUtil.io2main());
    }
}
