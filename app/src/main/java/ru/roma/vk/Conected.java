package ru.roma.vk;

import android.app.Application;
import android.content.Intent;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by Ilan on 29.08.2017.
 */

public class Conected extends android.app.Application {


    private static Conected instans ;

    @Override
    public void onCreate() {
        super.onCreate();
        instans = this;
        VKSdk.initialize(this);
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    public  static Conected getInstans(){
        return  instans;
    }

}

