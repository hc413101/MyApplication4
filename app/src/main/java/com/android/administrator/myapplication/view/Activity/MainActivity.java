package com.android.administrator.myapplication.view.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.administrator.myapplication.R;
import com.android.administrator.myapplication.service.MyBroadCast;
import com.android.administrator.myapplication.service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private Button pause;
    private Button cancle;
    DownloadConnection downloadConnection = new DownloadConnection();
    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService(new Intent(MainActivity.this, MyService.class),downloadConnection,BIND_AUTO_CREATE);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }
        initView();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//        filter.addAction("android.net.wifi.STATE_CHANGE");
//        registerReceiver(new MyBroadCast(),filter);
        Intent intent = new Intent(this,MyBroadCast.class);
        sendBroadcast(intent);
    }

    private void initView() {
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);
        cancle = findViewById(R.id.cancel);
        cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                if(downloadBinder!=null){
                    downloadBinder.start("http://27.38.240.177/(S-Cute)(438)Emiri%20%233%E3%80%8C%E6%84%9B%E3%80%8D%E6%92%AB%E3%81%AE%E3%81%82%E3%82%8B%E3%82%A8%E3%83%83%E3%83%81.wmv");
                }
                break;
            case R.id.pause:
                if(downloadBinder!=null){
                    downloadBinder.pause();
                }
                break;
            case R.id.cancel:
                if(downloadBinder!=null){
                    downloadBinder.pause();
                }
                break;
            default:
                break;
        }
    }

    class DownloadConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(downloadConnection);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"没有权限将无法运行程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
                default:
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
