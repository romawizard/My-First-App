package ru.roma.vk;

import java.util.ArrayList;



/**
 * Created by Ilan on 23.09.2017.
 */

public interface DataInformation {

    ArrayList<Dialogs> getAllDialogs(Integer idMsg);

    ArrayList<Friend> getAllFriends();

    Friend getUser(long id);

    ArrayList<Message> getMessage(int msgId);

    void sendMessage(String text, int id);
}
