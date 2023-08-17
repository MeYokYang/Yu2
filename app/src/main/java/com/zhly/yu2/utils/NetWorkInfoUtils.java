package com.zhly.yu2.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetWorkInfoUtils {
    /**
     * check the network connection
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){

        //判断网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取网路信息对象
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }
}
