package com.android.administrator.myapplication.task;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.android.administrator.myapplication.listener.DownloadListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Created by Administrator on 2018/5/10.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    private DownloadListener listener;
    private static final int  STATE_SUCCESSFUL = 0,
                                STATE_FAILD = 1,
                                STATE_CANCLE = 2,
                                STATE_PAUSE = 3;
    private boolean isCancel = false,isPause = false;

    public  DownloadTask(DownloadListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile accessFile = null;
        long contentLength = 0,downloadLength=0;
        String url = strings[0];

        String fileName = Environment.getDownloadCacheDirectory().getAbsoluteFile()+url.substring( url.lastIndexOf("/"));
        File file = new File(fileName);
        if(file.exists()){
            contentLength = getContentLength(url);
            if(contentLength==file.length()){
                return  1;
            }
            if(file.length() == 0){
                return  STATE_FAILD;
            }
        }
        downloadLength = file.length();
        try {
            accessFile = new RandomAccessFile(file,"rw");
            accessFile.seek(downloadLength);
            OkHttpClient client = new OkHttpClient();
            Request request = new  Request.Builder().url(url)
                    .addHeader("RANGE","bytes="+downloadLength+"-")
                    .build();
            Response response = client.newCall(request).execute();
            if(response==null){
                return  STATE_FAILD;
            }
            is = response.body().byteStream();
            byte [] b = new byte[1];
            int start = 0;
            int len = 0;
            int total = 0;
            Log.d("len",is.read(b)+"");
            while ((len = is.read(b))!=-1){
                if(isCancel){
                    return STATE_CANCLE;
                }
                if(isPause){
                    return  STATE_PAUSE;
                }
                accessFile.write(b,0,len);
                total+=len;
                publishProgress((int)((total+downloadLength)/contentLength)*100);
            }
        } catch (Exception e) {
            Log.d("bug",e.getMessage());

        }finally {
            try {
                if(accessFile!=null){
                    accessFile.close();
                }
                if (is!=null){
                    is.close();
                }
            }catch (Exception e){
                e.getStackTrace();
            }
           if(file.exists()&&downloadLength==0){
                file.delete();
           }
        }
        return STATE_SUCCESSFUL;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress>0){
            listener.progress(progress);

        }
    }

    @Override
    protected void onPostExecute(Integer state) {
        switch (state){
            case STATE_CANCLE:
                listener.cancle();
            case STATE_FAILD:
                listener.faild();
            case STATE_PAUSE:
                listener.pause();
            case STATE_SUCCESSFUL:
                listener.successful();
                break;
                default:
                    break;
        }
    }

    private long getContentLength(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response!=null){
                return  response.body().contentLength();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void pause(){
        isPause = true;
    }

    public void cancle(){
        isCancel = true;
    }
}
