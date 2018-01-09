package ru.roma.vk;

import java.util.List;

import ru.roma.vk.holders.Message;

/**
 * Created by Ilan on 28.10.2017.
 */

public interface MessageView {

    void setMessage(List<Message> messages);

    int getId();
    void  removeContent();

}
