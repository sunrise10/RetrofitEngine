package com.example.yf.retrofitengine.model.net.home;

import com.example.yf.retrofitengine.model.bean.homeBean.response.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yf on 2017/11/29.
 * 描述：联网接口
 */

public interface HomeApi {
    String MOVIE = "v2/movie/search";

    @GET(MOVIE)
    Observable<MovieBean> getMovies(@Query("q") String name);
}
