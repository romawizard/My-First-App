package ru.roma.vk;

import java.util.List;

import ru.roma.vk.holders.Message;

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

    public  void  sendMessage(String text, int id){
        model.onSendMessage(text, id, new MessageModel.LoadMessage() {
            @Override
            public void onLoad(List<Message> messageList) {
                view.setMessage(messageList);
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
}
