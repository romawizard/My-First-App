package ru.roma.vk.myRetrofit;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Ilan on 19.12.2017.
 */

public interface APIQuery {

    @POST("photos.getMessagesUploadServer?")
    Call<ModelUploadServer> getUploadServer();
}
