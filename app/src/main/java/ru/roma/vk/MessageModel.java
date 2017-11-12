package ru.roma.vk;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Ref;
import java.util.List;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageModel implements Paginable{

    private final String KEY_TEXT = "text";
    private final  String KEY_ID = "id";
    private final int id;
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
            ApiVK.getInstance().sendMessage(text, id);

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
}
