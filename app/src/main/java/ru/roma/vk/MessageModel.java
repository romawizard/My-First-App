package ru.roma.vk;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageModel implements Paginable{

    private final String KEY_TEXT = "text";
    private  final  String KEY_ID = "id";
    private  int id;
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

    public void  loadMessageData(Integer id, LoadMessage callback){
        AsynMessage asynMessage = new AsynMessage(callback);
        asynMessage.execute(id);
    }

    public void onSendMessage(String text, int id,LoadMessage callback){
        ContentValues cv = new ContentValues();
        cv.put(KEY_TEXT,text);
        cv.put(KEY_ID,id);

        SendMessage sm = new SendMessage(callback);
        sm.execute(cv);

    }

    private class AsynMessage extends AsyncTask<Integer ,Void,List<Message>>{

        LoadMessage callBack;

        public AsynMessage(LoadMessage callBack) {
            this.callBack = callBack;
        }

        @Override
        protected List<Message> doInBackground(Integer... integers) {
//            return ApiVK.getInstance().getMessage(integers[0],integers[1]);
            return p.next();
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            callBack.onLoad(messages);
        }
    }

    private class SendMessage extends AsyncTask<ContentValues,Void,Integer>{

        LoadMessage callback;

        SendMessage(LoadMessage callback){
            this.callback = callback;
        }
        @Override
        protected Integer doInBackground(ContentValues... contentValues) {
            String text = contentValues[0].getAsString(KEY_TEXT);
            int id = contentValues[0].getAsInteger(KEY_ID);
            ApiVK.getInstance().sendMessage(text,id);

            return id;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
//            loadMessageData(integer,callback);
        }
    }
}
