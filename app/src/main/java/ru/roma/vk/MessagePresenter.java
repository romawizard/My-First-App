package ru.roma.vk;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessagePresenter {

    private MessageModel model;
    private MessageView view;
    private boolean loading = false;

    public MessagePresenter(MessageModel model) {
        this.model = model;
    }

    public void attachView(MessageView view) {
        this.view = view;
    }

    public void isReady() {
        loadMessage();
    }

    private void loadMessage() {
        loading = true;
        model.loadMessageData(new MessageModel.LoadMessage() {
            @Override
            public void onLoad(List<Message> messageList) {
                view.setMessage(messageList);
                loading = false;
            }
        });
    }

    public void sendMessage(String text, int id){
        model.onSendMessage(text, id, new MessageModel.LoadMessage() {
            @Override
            public void onLoad(List<Message> messageList) {
                view.setMessage(messageList);
                view.removeContent();
                loading = false;
            }
        });

    }

    public void dettach(){
        view = null;
    }

    public boolean isLoading(){
        return loading;
    }


    public void uploadServer(final File file) {

        model.onUploadFile(file);

    }


}
