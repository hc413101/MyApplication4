package com.android.administrator.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.android.administrator.myapplication.units.NetWorkCheekUnit;

/**
 * Created by Administrator on 2018/5/11.
 */

public class MyBroadCast extends BroadcastReceiver {
    private NetWorkChangeListener netWorkChangeListener;

    public MyBroadCast(NetWorkChangeListener listener){
        this.netWorkChangeListener = listener;
    }

    public   MyBroadCast(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            NetWorkCheekUnit.NetWorkState netWorkState = NetWorkCheekUnit.cheekNetWorkState(context);
                switch (netWorkState){
                    case WIFI:
                        netWorkChangeListener.wifi();
                        break;
                    case MOBLE:
                        netWorkChangeListener.isMoble();
                        break;
                    case NO_NETWOK:
                        netWorkChangeListener.noNetWork();
                        break;
                }
        }
    }

    public interface NetWorkChangeListener{
        void isMoble();
        void noNetWork();
        void wifi();
    }
}
