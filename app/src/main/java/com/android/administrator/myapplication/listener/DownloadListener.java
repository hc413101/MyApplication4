package com.android.administrator.myapplication.listener;

/**
 * Created by Administrator on 2018/5/10.
 */

public interface DownloadListener  {
    void progress(int progress);
    void faild();
    void cancle();
    void pause();
    void successful();
}
