package com.android.administrator.myapplication.view.Activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.administrator.myapplication.R;
import com.android.administrator.myapplication.service.MyBroadCast;

public class Main2Activity extends AppCompatActivity implements MyBroadCast.NetWorkChangeListener {
    public static final int RESULTCODE = 2;
    private EditText editText;
    private IntentFilter filter;
    private MyBroadCast myBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadCast, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadCast);
    }

    private void initView() {
        myBroadCast = new MyBroadCast(this);
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");

    }

    public void tiao(View view){
    }

    @Override
    public void isMoble() {
        Toast.makeText(this,"现在使用的是移动网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noNetWork() {
        Toast.makeText(this,"现在没有网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void wifi() {
        Toast.makeText(this,"现在使用的是wifi网络",Toast.LENGTH_SHORT).show();
    }
}
