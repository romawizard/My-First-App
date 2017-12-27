package ru.roma.vk;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKSdk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.roma.vk.myRetrofit.APIQuery;

/**
 * Created by Ilan on 29.08.2017.
 */

public class MainApplication extends android.app.Application {


    private static MainApplication instans ;

    private static APIQuery query;

    @Override
    public void onCreate() {
        super.onCreate();
        instans = this;
        VKSdk.initialize(this);
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        query = retrofit.create(APIQuery.class);
    }


    public  static MainApplication getInstans(){
        return  instans;
    }

    public static APIQuery getQuery() {
        return query;
    }

}

