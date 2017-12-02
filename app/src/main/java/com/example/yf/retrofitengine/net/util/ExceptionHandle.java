package com.example.yf.retrofitengine.net.util;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;


/**
 * Created by yf on 2017/11/29.
 * Email：yunfei10306@163.com
 * 描述：异常处理，和服务器约定的异常由于每个公司返回的Response结构都不一样这里就没有统一封装,可在回调onSuccess前处理
 */

public class ExceptionHandle {
    //未知错误
    private static final int UNKNOWN = 1000;
    //解析错误
    private static final int PARSE_ERROR = 1001;
    //网络错误
    private static final int NETWORD_ERROR = 1002;
    //证书出错
    private static final int SSL_ERROR = 1003;
    private static ResponeThrowable ex;
    public static ResponeThrowable handleException(Throwable e) {
        if(ex == null){
            ex = new ResponeThrowable();
        }
        //协议HTTP异常
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex.code = httpException.code();
            ex.message = httpException.message();
        }  else if (e instanceof JsonSyntaxException || e instanceof JSONException) {
            ex.code = PARSE_ERROR;
            ex.message = "数据解析错误";
        } else if (e instanceof ConnectException) {
            ex.code = NETWORD_ERROR;
            ex.message = "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex.code = SSL_ERROR;
            ex.message = "证书验证失败";
        } else {
            ex.code = UNKNOWN;
            ex.message = "未知错误";
        }
        return ex;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;
    }
}
