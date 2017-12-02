package com.example.yf.retrofitengine.net;


import com.example.yf.retrofitengine.net.util.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yf on 2017/11/29.
 * Email：yunfei10306@163.com
 * 描述：联网接口结果回调
 */

public abstract class CallBack<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(int code, String message);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        ExceptionHandle.ResponeThrowable responeThrowable = ExceptionHandle.handleException(e);
        onFail(responeThrowable.code, responeThrowable.message);
    }

    @Override
    public void onComplete() {

    }
}
