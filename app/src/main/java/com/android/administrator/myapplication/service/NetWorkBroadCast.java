package com.android.administrator.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/5/12.
 */

public class NetWorkBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//判断wifi开关状态
        if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
//            获取当前wifi状态方法一
//            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            int state = wifiManager.getWifiState();
//            方法二
            int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,0);
            switch (state){
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
            }
        }

//        判断WIFI是否连接到网络
        if(intent.getAction().equals((WifiManager.NETWORK_STATE_CHANGED_ACTION))){
            Parcelable parcelable = intent.getParcelableExtra((WifiManager.EXTRA_NETWORK_INFO));
            NetworkInfo networkInfo = (NetworkInfo) parcelable;
            if(networkInfo.isConnected()){

            }else{

            }
        }

//        判断网络状态以及网络连接类型
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo.isConnected()){
               switch (networkInfo.getType()){
                   case ConnectivityManager.TYPE_WIFI:
                       break;
                   case ConnectivityManager.TYPE_MOBILE:
                       break;
                   default:
                       break;
               }
            }else {
//
            }
        }
    }
}
