package com.example.yf.retrofitengine.net.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.example.yf.retrofitengine.R;
import com.example.yf.retrofitengine.net.RetrofitEngine;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by yf on 2018/1/23.
 * Email：yunfei10306@163.com
 * 描述：更新
 */

public class UpdateUtil {
    public static final String UPDATE = "update";
    private static final String UPDATEURL = "your company updateURL";
    private Activity activity;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    public UpdateUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 更新进度
     *
     * @param downloadProgressEvent
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@com.hwangjr.rxbus.annotation.Tag(UPDATE)}
    )
    public void updateProgress(FileDownLoadProgressEvent downloadProgressEvent) {
        setProgress(downloadProgressEvent);
    }

    /**
     * 下载更新通知
     */
    public void showUpdateNotifation() {
        notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder = new NotificationCompat.Builder(activity, null)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(activity.getString(getPackageInfo().applicationInfo.labelRes) + "下载中...")
                .setProgress(100, 0, false)
                .setAutoCancel(true);
    }

    public void download() {
        register();
        RetrofitEngine.getInstanceForDownload().create(DownloadApi.class).downloadAPK(UPDATEURL)
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        return writeFile(responseBody.source());
                    }
                })
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .subscribe(new DisposableObserver<File>() {
                    @Override
                    public void onNext(File file) {
                        installApk(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        endUpdate();
                    }

                    @Override
                    public void onComplete() {
                        endUpdate();
                    }
                });
    }


    private void register() {
        RxBus.get().register(this);
    }

    private void unregester() {
        RxBus.get().unregister(this);
    }

    /**
     * 更新结束
     */
    private void endUpdate() {
        notificationManager.cancel(0);
        unregester();
    }

    /**
     * 设置进度
     *
     * @param downloadProgressEvent
     */
    private void setProgress(FileDownLoadProgressEvent downloadProgressEvent) {
        int progress = (int) ((downloadProgressEvent.getProgress() * 100) / downloadProgressEvent.getTotal());
        notificationBuilder.setProgress(100, progress, false);
        notificationBuilder.setContentText(progress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }

    interface DownloadApi {
        @Streaming
        @GET
        Observable<ResponseBody> downloadAPK(@Url String url);
    }

    /**
     * 写入文件
     */
    private File writeFile(BufferedSource source) throws IOException {
        String appName = activity.getString(getPackageInfo().applicationInfo.labelRes);
        //下载后的保存路径
        File file = new File(Environment.getExternalStorageDirectory() + "/" + appName, getPackageInfo().versionName + ".apk");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
            file.delete();
        }

        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeAll(source);
        bufferedSink.close();
        source.close();
        return file;
    }

    /**
     * 安装 apk 文件
     *
     * @param apkFile
     */
    private void installApk(File apkFile) {
        String appName = activity.getString(getPackageInfo().applicationInfo.labelRes);
        File file = new File(Environment.getExternalStorageDirectory() + "/" + appName, "app.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(activity, intent, "application/vnd.android.package-archive", file, true);
        activity.startActivity(intent);
    }

    private PackageInfo getPackageInfo() {
        PackageManager pm = activity.getPackageManager();
        try {
            return pm.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
