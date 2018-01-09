package ru.roma.vk;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.holders.Message;
import ru.roma.vk.myRetrofit.ModelResponseLoadServer;
import ru.roma.vk.myRetrofit.ModelResponseSaveMessagePhoto;
import ru.roma.vk.myRetrofit.ModelUploadServer;
import ru.roma.vk.utilitys.Paginable;
import ru.roma.vk.utilitys.Pagination;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageModel implements Paginable {

    private final String KEY_TEXT = "text";
    private final String KEY_ID = "id";
    private final String KEY_OWNER_ID = "ownerId";
    private final String KEY_MEDIA_ID = "mediaId";
    private final int id;
    private int ownerId;
    private int mediaId;
    private Pagination<Message> p;

    MessageModel(int id){
        this.id = id;
        p = new Pagination<>(20,this);
    }

    @Override
    public List getData(int offset) {
        return  ApiVK.getInstance().getMessage(id,offset);
    }

    @Override
    public int getCount() {
        Log.d("my log", "countMessage= " + Message.getCount());
        return Message.getCount();
    }

    interface LoadMessage{
        void onLoad(List<Message> messageList);
    }

    public void  loadMessageData( LoadMessage callback){
        LoadingMessage asynMessage = new LoadingMessage(callback);
        asynMessage.execute(id);
    }

    public void onSendMessage(String text, int id,LoadMessage callback){
        ContentValues cv = new ContentValues();
        cv.put(KEY_TEXT,text);
        cv.put(KEY_ID,id);
        cv.put(KEY_OWNER_ID,ownerId);
        cv.put(KEY_MEDIA_ID,mediaId);

        SendMessage sm = new SendMessage(callback);
        sm.execute(cv);

    }

    public void onRefreshMessage(LoadMessage callback){
        RefreshMessage refreshMessage = new RefreshMessage(callback);
        refreshMessage.execute();
    }

    private class LoadingMessage extends AsyncTask<Integer ,Void,List<Message>>{

        LoadMessage callBack;

        public LoadingMessage(LoadMessage callBack) {
            this.callBack = callBack;
        }

        @Override
        protected List<Message> doInBackground(Integer... integers) {
            return p.next();
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            callBack.onLoad(messages);
        }
    }

    private class SendMessage extends AsyncTask<ContentValues,Void,Integer> {

        LoadMessage callback;

        SendMessage(LoadMessage callback) {
            this.callback = callback;
        }

        @Override
        protected Integer doInBackground(ContentValues... contentValues) {
            String text = contentValues[0].getAsString(KEY_TEXT);
            int id = contentValues[0].getAsInteger(KEY_ID);
            int ownerId = contentValues[0].getAsInteger(KEY_OWNER_ID);
            int mediaId = contentValues[0].getAsInteger(KEY_MEDIA_ID);

            if (ownerId == 0 || mediaId == 0) {
                ApiVK.getInstance().sendMessage(text, id);
            }else {
                ApiVK.getInstance().sendMessage(text,id,ownerId,mediaId);
                MessageModel.this.ownerId = 0;
                MessageModel.this.mediaId = 0;
            }
            return id;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
           onRefreshMessage(callback);
        }
    }

    private class RefreshMessage extends AsyncTask<Integer,Void,List<Message>>{

       LoadMessage callback;

        public RefreshMessage(LoadMessage callback){
            this.callback =callback;
        }

        @Override
        protected List<Message> doInBackground(Integer... integers) {
            return p.reload();
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);
            callback.onLoad(messages);
        }
    }

    public void onUploadFile(final File file){

        String token = MainApplication.getInstans()
                .getSharedPreferences(Keys.MAINPREF, Context.MODE_PRIVATE).getString(Keys.TOKEN, "no token");

        MainApplication.getQuery().getUploadServer(token).enqueue(new Callback<ModelUploadServer>() {
            @Override
            public void onResponse(Call<ModelUploadServer> call, Response<ModelUploadServer> response) {
                if (response.body() != null){
                    Log.d(Keys.LOG, "RESPONSE RETROFIT = " + response.body());
                    String urlUploadServer =  response.body().getResponse().getUploadUrl();
                    uploadFile(urlUploadServer,file);
                }else {
                    Toast.makeText(MainApplication.getInstans(),"null in the body",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelUploadServer> call, Throwable t) {
                Toast.makeText(MainApplication.getInstans(),"error retrofit",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadFile(String urlUploadServer, File file) {

        // Создаем RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part используется, чтобы передать имя файла
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        // Выполняем запрос
        Call<ModelResponseLoadServer> call = MainApplication.getQuery().loadPhoto(urlUploadServer, body);
        call.enqueue(new Callback<ModelResponseLoadServer>() {
            @Override
            public void onResponse(Call<ModelResponseLoadServer> call, Response<ModelResponseLoadServer> response) {
                Log.d(Keys.LOG,"response uploadserver = " + response.body());
                ModelResponseLoadServer model= response.body();
                savePhoto(model);
            }

            @Override
            public void onFailure(Call<ModelResponseLoadServer> call, Throwable t) {
                Log.d("retrofit", "error in the uploadFile");
            }
        });
    }

    private void savePhoto(ModelResponseLoadServer model) {
        SaveTask task = new SaveTask();
        task.execute(model);

    }

    private class SaveTask extends AsyncTask<ModelResponseLoadServer,Void,List<ModelResponseSaveMessagePhoto.Response>> {

        @Override
        protected List doInBackground(ModelResponseLoadServer... modelResponseLoadServers) {
            String photo = modelResponseLoadServers[0].getPhoto();
            String hash = modelResponseLoadServers[0].getHash();
            int server = modelResponseLoadServers[0].getServer();

            try {
                photo = URLEncoder.encode(photo,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return   ApiVK.getInstance().savePhoto(photo,server,hash);
        }

        @Override
        protected void onPostExecute(List<ModelResponseSaveMessagePhoto.Response> responses) {

            Log.d(Keys.LOG,"savePhoto asynTask = " +responses.toString());

            ownerId = responses.get(0).getOwnerId();
            mediaId = responses.get(0).getId();

            super.onPostExecute(responses);
        }
    }

}
