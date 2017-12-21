package ru.roma.vk.utilitys;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.roma.vk.ApiVK;
import ru.roma.vk.MainApplication;
import ru.roma.vk.dataBase.DataFromBD;
import ru.roma.vk.dataBase.DataInformation;

/**
 * Created by Ilan on 23.09.2017.
 */

public class Controller {


    private static boolean hasConnection() {

        ConnectivityManager cm = (ConnectivityManager) MainApplication.getInstans().getSystemService(Context.CONNECTIVITY_SERVICE);
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

    public static DataInformation getTrack (){
        if (hasConnection()){
            return ApiVK.getInstance();
        }else
            return new DataFromBD();
    }
}
