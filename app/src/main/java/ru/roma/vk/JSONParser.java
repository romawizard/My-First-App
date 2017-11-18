package ru.roma.vk;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ilan on 09.10.2017.
 */

public class JSONParser {

    public static ArrayList<Friend> parseFriend(JSONObject jsonObject) {

        JSONArray object = null;
        try {
            object = jsonObject.getJSONArray("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONArray items = object.optJSONArray("items");

        final ArrayList<Friend> friends = new ArrayList<Friend>();

        for (int i = 0; i < object.length(); i++) {
            Friend friend = new Friend();
            JSONObject oneuser = null;

            try {
                oneuser = object.getJSONObject(i);
                friend.setFirst_name(oneuser.optString("first_name"));
                friend.setLast_name(oneuser.optString("last_name"));
                friend.setOn_line(oneuser.optInt("online"));
                friend.setSex(oneuser.optInt("sex"));
                friend.setURLPhoto(oneuser.optString("photo_max_orig"));
                if (TextUtils.isEmpty(friend.getURLPhoto())){
                    friend.setURLPhoto(oneuser.optString("photo_100"));
                }
                friend.setId(oneuser.optInt("uid"));
                if ((friend.getId() == 0)){
                    friend.setId(oneuser.optInt("id"));
                }
                friend.setStatus(oneuser.optString("status"));
                friend.setHome_town(oneuser.optString("home_town"));

                JSONObject last_seen = oneuser.optJSONObject("last_seen");
                if (last_seen != null) {
                    friend.setTime(last_seen.optLong("time"));
                    friend.setPlatform(last_seen.optInt("platform"));
                }
                friends.add(friend);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                WriteToDB wtdb = new WriteToDB(Conected.getInstans());
                wtdb.writeFriends(friends);
                Log.d("my log", "info write o DB");
            }
        });
        t.start();

            return friends;
    }

    public static ArrayList<Dialogs> parseDialog (JSONObject jsonObject){

        ArrayList<Dialogs>  dialogs = new ArrayList<>();

        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject object = array.optJSONObject(0);

        int count = object.optInt("count");
        Dialogs.setCount(count);

        JSONArray items = object.optJSONArray("items");
        JSONArray arrName = array.optJSONArray(1);
        JSONArray myName = array.optJSONArray(2);

        for (int i = 0; i < items.length(); i++) {
            JSONObject oneDialog = null;
            try {
                oneDialog = items.getJSONObject(i);
                JSONObject msg = oneDialog.optJSONObject("message");

                int id = msg.optInt("user_id");
                String title = msg.optString("title");
                String body = msg.optString("body");
                int readState = msg.optInt("read_state");
                String first_name = "no name";
                String last_name ="no name";
                String URLPhoto = msg.optString("photo_100");
                long time = msg.optLong("date");
                int out = msg.optInt("out");
                int onLine = 0;
                int idMsg = msg.optInt("id");

                for (int n =0; n<arrName.length();n++){
                    JSONObject oneName = arrName.getJSONObject(n);

                    int idName = oneName.optInt("id");
                    if (id == idName){
                        first_name = oneName.optString("first_name");
                        last_name = oneName.optString("last_name");
                        onLine = oneName.optInt("online");
                        if (TextUtils.isEmpty(URLPhoto)) {
                            URLPhoto = oneName.optString("photo_100");
                        }
                    }
                }
                JSONObject objectName = myName.optJSONObject(0);
                String myPhoto = objectName.optString("photo_100");
                Dialogs dialog = new Dialogs(title, body, readState,first_name,last_name);
                dialog.setURLPhoto(URLPhoto);
                dialog.setTime(time);
                dialog.setOut(out);
                dialog.setMyphoto(myPhoto);
                dialog.setOnLine(onLine);
                dialog.setIdMsg(idMsg);
                dialogs.add(dialog);
                dialog.setUserId(id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dialogs;
    }

    public static ArrayList<Message> parseMessage(JSONObject jsonObject){

        JSONObject object = jsonObject.optJSONObject("response");
        int count = object.optInt("count");
        Message.setCount(count);
        JSONArray items = object.optJSONArray("items");

        ArrayList<Message> messages = new ArrayList<Message>();

        for ( int i =0; i< items.length();i++){
            JSONObject msg = items.optJSONObject(i);

            String body = msg.optString("body");
            int readState = msg.optInt("read_state");
            int userId = msg.optInt("user_id");
            int userFrom = msg.optInt("user_from");
            int out = msg.optInt("out");
            long date = msg.optLong("date");
            Message m = new Message(body,readState,date,userId,userFrom,out);
            messages.add(m);
        }
        return messages;
    }

    public static int connectNotifiny(JSONObject jsonObject){

        int result = 0;

        try {
            Log.d("my log","JSON: " + jsonObject.toString());
            result = jsonObject.getInt("response");
        } catch (JSONException e) {
            Log.d("my log","НЕУДАЧНОЕ ПОДКЛЮЧЕНИЕ NOTIFICATION");
        }
        return result;
    }
}

