package com.android.administrator.myapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.android.administrator.myapplication.R;
import com.android.administrator.myapplication.listener.DownloadListener;
import com.android.administrator.myapplication.task.DownloadTask;
import com.android.administrator.myapplication.view.Activity.MainActivity;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MyService extends Service {
    private DownloadTask downloadTask ;
    private Notification notification;
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void progress(int progress) {
            getNotificationManager().notify(1,getNotification("正在下载",progress));
        }

        @Override
        public void faild() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(MyService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void cancle() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(MyService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void pause() {
            downloadTask = null;
            getNotificationManager().notify(1,getNotification("暂停下载",-1));
            Toast.makeText(MyService.this,"暂停下载",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void successful() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(MyService.this,"下载成功",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return  new DownloadBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class DownloadBinder extends Binder{
        private String url;
        public void start(String url){
            this.url = url;
            if(downloadTask==null){
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(url);
                notification = getNotification("正在下载。。。",0);
                startForeground(1,notification);
            }
        }
        public void pause(){
            if(downloadTask!=null){
                downloadTask.pause();
            }
        }
        public void cancle(){
            if (downloadTask!=null){
                downloadTask.cancle();
            }
            if(url!=null){
                String fileName = Environment.getDownloadCacheDirectory()
                        .getAbsolutePath()+url.substring(url.lastIndexOf("/"));
                File file = new File(fileName);
                if(file.exists()){
                    file.delete();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected Notification getNotification(String title,int progress){
        PendingIntent intent = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle("title")
                .setContentIntent(intent)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background);
            if(progress>0){
                notificationBuilder.setContentTitle("已下载"+progress+"%");
                notificationBuilder.setProgress(100,progress,false);
            }
                return  notificationBuilder.build();
    }

    protected NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
}
