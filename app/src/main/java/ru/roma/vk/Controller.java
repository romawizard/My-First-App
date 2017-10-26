package ru.roma.vk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ilan on 23.09.2017.
 */

public class Controller {


    private  boolean hasConnection() {

        ConnectivityManager cm = (ConnectivityManager)Conected.getInstans().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public  DataInformation getTrack (){
        if (hasConnection()){
            return ApiVK.getApiVK();
        }else
            return new DataFromBD(Conected.getInstans());
    }
}
