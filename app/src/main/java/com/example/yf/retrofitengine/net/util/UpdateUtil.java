package com.example.yf.retrofitengine.net.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.yf.retrofitengine.R;
import com.example.yf.retrofitengine.net.CallBack;
import com.example.yf.retrofitengine.net.RetrofitEngine;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.thread.EventThread;
import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
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
 * 描述：更新(建议在APP启动的时候判断下Apk存放路径是否有该版本apk存在，存在就删除，防止出现下载后的各版本apk存在SD卡中)
 * 更新的原理其实就是在
 */

public class UpdateUtil {
    public static final String UPDATE = "update";
    //设置下载url
    private static final String UPDATEURL = "set your company download Apk url";
    private Activity activity;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private File apkFile;
    //是否静默下载
    private boolean silentdownload = false;

    public UpdateUtil(Activity activity) {
        this.activity = activity;
    }

    /**
     * 更新进度
     */
    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {@com.hwangjr.rxbus.annotation.Tag(UPDATE)}
    )
    public void updateProgress(FileDownLoadProgressEvent downloadProgressEvent) {
        setProgress(downloadProgressEvent.progress);
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

    /**
     * 下载APK安装包
     * 如果APK已存在，直接安装，否则下载后安装
     */
    public void download() {
        File downloadPathFile = getDownloadPathFile();
        if (downloadPathFile.exists()) {
            installApk(downloadPathFile);
        } else {
            register();
            /*
            如不想生成通知栏形式显示下载进度，可替换成dialog形式，如头条更新
            可自定义一个dialog，下载进度在updateProgress()参数downloadProgressEvent.progress中
            因为公司不同，dialog长的也不一样这里也就没写
            如需静默下载，改变silentdownload为true即可
             */
            showUpdateNotifation();
            Toast.makeText(activity, "下载中...", Toast.LENGTH_SHORT).show();
            RetrofitEngine.getInstanceForDownload(silentdownload).create(DownloadApi.class).downloadAPK(UPDATEURL)
                    .subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                    .subscribe(new CallBack<ResponseBody>() {
                        @Override
                        public void onSuccess(ResponseBody responseBody) {
                            try {
                                apkFile = writeFile(responseBody.source());
                            } catch (IOException e) {
                                e.printStackTrace();
                                if(!silentdownload){
                                    endUpdate();
                                }
                            }
                        }

                        @Override
                        public void onFail(int code, String message) {
                            Toast.makeText(activity, "下载失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                            if(!silentdownload){
                                endUpdate();
                            }
                        }
                    });
        }
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
     * @param progress
     */
    private void setProgress(int progress) {
        Log.e("Update", "下载接收 progress:    " + progress);
        if (progress < 0) {
            endUpdate();
            throw new IllegalArgumentException("请先设置你的Apk下载url，设置请到UpdateUtil");
        } else {
            notificationBuilder.setProgress(100, progress, false);
            notificationBuilder.setContentText(progress + "%");
            notificationManager.notify(0, notificationBuilder.build());
            if (progress == 100) {
                endUpdate();
                installApk(apkFile);
            }
        }
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
        File file = getDownloadPathFile();
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
     * APK存放路径
     */
    @NonNull
    private File getDownloadPathFile() {
        String appName = activity.getString(getPackageInfo().applicationInfo.labelRes);
        //下载后的保存路径 如以retrofitEngine1.1.apk命名
        return new File(Environment.getExternalStorageDirectory() + "/" + appName, getPackageInfo().versionName + ".apk");
    }

    /**
     * 安装 apk 文件
     */
    private void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(activity, intent, "application/vnd.android.package-archive", apkFile, true);
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
