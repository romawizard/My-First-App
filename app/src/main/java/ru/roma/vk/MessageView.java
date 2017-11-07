package ru.roma.vk;

import java.util.List;

/**
 * Created by Ilan on 28.10.2017.
 */

public interface MessageView {

    void setMessage(List<Message> messages);

    int getId();

}
