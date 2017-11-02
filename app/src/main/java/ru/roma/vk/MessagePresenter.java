package ru.roma.vk;

import java.util.List;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessagePresenter {

    MessageModel model;
    MessageView view;

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
        model.loadMessageData(view.getId(), new MessageModel.LoadMessage() {
            @Override
            public void onLoad(List<Message> messageList) {
                view.setMessage(messageList);
            }
        });
    }

    public  void  sendMessage(String text, int id){
        model.onSendMessage(text,id);

    }

    public void dettach(){
        view = null;
    }
}
