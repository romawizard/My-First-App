package ru.roma.vk.myRetrofit;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Ilan on 19.12.2017.
 */

public interface APIQuery {

    @GET("photos.getMessagesUploadServer")
    Call<ModelUploadServer> getUploadServer(@Query("access_token")String token);

    @Multipart
    @POST
    Call<ModelResponseLoadServer> loadPhoto(@Url String urlServer, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @GET("photos.saveMessagesPhoto")
    Call<ModelResponseSaveMessagePhoto> savePhoto(@Query("photo")String photo, @Query("server") int server
            , @Query("hash") String hash, @Query("access_token")String token);
}
