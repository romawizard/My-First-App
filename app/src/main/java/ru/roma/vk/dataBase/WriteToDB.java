package ru.roma.vk.dataBase;

import android.content.Context;

import java.util.List;

import ru.roma.vk.holders.Friend;

/**
 * Created by Ilan on 04.11.2017.
 */

public class WriteToDB {

    private DB db;


    public WriteToDB(Context context) {
        db = new DB(context);
    }

    public void writeFriends(List<Friend> friends) {
        db.open();

        for (Friend f : friends) {
            String fist_name = f.getFirst_name();
            String last_name = f.getLast_name();
            String URL_photo = f.getURLPhoto();
            int on_line = f.getOn_line();
            int user_id = f.getId();

            db.addFriend(fist_name, last_name, user_id, URL_photo, on_line);
        }
        db.close();
    }
}
