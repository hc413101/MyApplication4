package com.android.administrator.myapplication.units;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2018/5/11.
 */

public class NetWorkCheekUnit {
    public static enum NetWorkState{NO_NETWOK,MOBLE,WIFI};
        public static NetWorkState cheekNetWorkState(Context context){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isConnected()){
                if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    return NetWorkState.WIFI;
                }else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    return NetWorkState.MOBLE;
                }
            }
            return NetWorkState.NO_NETWOK;
        }
}
