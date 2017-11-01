package ru.roma.vk;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageModel {

    interface LoadMessage{
        void onLoad(List<Message> messageList);
    }

    public void  loadMessageData(Integer id, LoadMessage callback){
        AsynMessage asynMessage = new AsynMessage(callback);
        asynMessage.execute(id);
    }

    private class AsynMessage extends AsyncTask<Integer ,Void,List<Message>>{

        LoadMessage callBack;

        public AsynMessage(LoadMessage callBack) {
            this.callBack = callBack;
        }

        @Override
        protected List<Message> doInBackground(Integer... integers) {
            return ApiVK.getApiVK().getMessage(integers[0]);
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);

            callBack.onLoad(messages);
        }
    }
}
