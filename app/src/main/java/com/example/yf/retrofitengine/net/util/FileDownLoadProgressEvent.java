package com.example.yf.retrofitengine.net.util;

/**
 * Created by yf on 2018/1/23.
 * Email：yunfei10306@163.com
 * 描述：下载进度事件
 */

public class FileDownLoadProgressEvent {
    private long total;
    private long progress;

    public FileDownLoadProgressEvent(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }
}
