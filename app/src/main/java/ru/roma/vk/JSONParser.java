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

    public static ArrayList<Friend> parseFriend(JSONArray array) {

        ArrayList<Friend> friends = new ArrayList<Friend>();

        for (int i = 0; i < array.length(); i++) {
            Friend friend = new Friend();
            JSONObject oneuser = null;

            try {
                oneuser = array.getJSONObject(i);
                friend.setFirst_name(oneuser.optString("first_name"));
                friend.setLast_name(oneuser.optString("last_name"));
                friend.setOn_line(oneuser.optInt("online"));
                friend.setSex(oneuser.optInt("sex"));
                friend.setURL_photo(oneuser.optString("photo_max_orig"));
                friend.setId(oneuser.optInt("uid"));
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
            return friends;
    }
    public static ArrayList<Dialogs> parseDialog (JSONArray array){

        ArrayList<Dialogs>  dialogs = new ArrayList<>();

        JSONObject object = array.optJSONObject(0);

        int count = object.optInt("count");
        Dialogs.setCount(count);

        JSONArray arrDialog = object.optJSONArray("items");
        JSONArray arrName = array.optJSONArray(1);
        JSONArray myName = array.optJSONArray(2);

        for (int i = 0; i < arrDialog.length(); i++) {
            JSONObject oneDialog = null;
            try {
                oneDialog = arrDialog.getJSONObject(i);
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dialogs;
    }
}

