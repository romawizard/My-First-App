package ru.roma.vk.dataBase;

import java.util.ArrayList;

import ru.roma.vk.holders.Dialogs;
import ru.roma.vk.holders.Friend;
import ru.roma.vk.holders.Message;


/**
 * Created by Ilan on 23.09.2017.
 */

public interface DataInformation {

    ArrayList<Dialogs> getAllDialogs(Integer idMsg);

    ArrayList<Friend> getAllFriends();

    Friend getUser(long id);

    ArrayList<Message> getMessage(int userId, int offset);

    void sendMessage(String text, int id);
}
