package ru.roma.vk;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ilan on 23.09.2017.
 */

public class DataFromBD implements DataInformation {

    Context context;
    DB db;

    DataFromBD(Context  context){
        this.context = context;
        db = new DB(context);
    }

    @Override
    public ArrayList<Dialogs> getAllDialogs(Integer idMsg) {
      return  null;
    }

    @Override
    public ArrayList<Friend> getAllFriends() {
        ArrayList<Friend> friends = new ArrayList<Friend>();
        db.open();
        Cursor cursor = db.getAllData();
        if (cursor != null) {
            Log.d("my log", "Records count = " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    String first_name = cursor.getString(cursor.getColumnIndex(DB.FIRST_MAME));
                    String last_name = cursor.getString(cursor.getColumnIndex(DB.LAST_NAME));
                    String URL_photo =  cursor.getString(cursor.getColumnIndex(DB.URL_PHOTO));
                    int on_line = cursor.getInt(cursor.getColumnIndex(DB.ON_LINE));
                    long id = cursor.getInt(cursor.getColumnIndex(DB.USER_ID));
                    Friend friend = new Friend(first_name,last_name,URL_photo,on_line,id);
                    friends.add(friend);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return friends;
    }

    @Override
    public Friend getUser(long id) {
        return null;
    }
}
