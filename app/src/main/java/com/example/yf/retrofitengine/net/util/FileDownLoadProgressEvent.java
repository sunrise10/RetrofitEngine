package com.example.yf.retrofitengine.net.util;

/**
 * Created by yf on 2018/1/23.
 * Email：yunfei10306@163.com
 * 描述：下载进度事件
 */

public class FileDownLoadProgressEvent {
    public int progress;

    public FileDownLoadProgressEvent(int progress) {
        this.progress = progress;
    }
}
