package ru.roma.vk;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.roma.vk.post.Attachment;

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
                if (TextUtils.isEmpty(friend.getURLPhoto())) {
                    friend.setURLPhoto(oneuser.optString("photo_100"));
                }
                friend.setId(oneuser.optInt("uid"));
                if ((friend.getId() == 0)) {
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

    public static ArrayList<Dialogs> parseDialog(JSONObject jsonObject) {

        ArrayList<Dialogs> dialogs = new ArrayList<>();

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
            Dialogs dialog = new Dialogs();
            JSONObject oneDialog = null;
            try {
                oneDialog = items.getJSONObject(i);
                JSONObject msg = oneDialog.optJSONObject("message");

                int id = msg.optInt("user_id");
                String title = msg.optString("title");
                String body = msg.optString("body");
                int readState = msg.optInt("read_state");
                String first_name = "no name";
                String last_name = "no name";
                String URLPhoto = msg.optString("photo_100");
                long time = msg.optLong("date");
                int out = msg.optInt("out");
                int onLine = 0;
                int idMsg = msg.optInt("id");
                int chatId = msg.optInt("chat_id");

                if (msg.has("chat_active")) {

                    JSONArray charActives = msg.optJSONArray("chat_active");
                    List active = new ArrayList<Integer>();
                    for (int n = 0, t = charActives.length(); n < t; n++) {
                        active.add(charActives.get(n));
                    }
                    dialog.setCharActives(active);
                }
                for (int n = 0; n < arrName.length(); n++) {
                    JSONObject oneName = arrName.getJSONObject(n);

                    int idName = oneName.optInt("id");
                    if (id == idName) {
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
                dialog.setTitle(title);
                dialog.setBody(body);
                dialog.setReadeState(readState);
                dialog.setFirst_name(first_name);
                dialog.setLast_name(last_name);
                dialog.setURLPhoto(URLPhoto);
                dialog.setTime(time);
                dialog.setOut(out);
                dialog.setMyphoto(myPhoto);
                dialog.setOnLine(onLine);
                dialog.setIdMsg(idMsg);
                dialog.setUserId(id);
                dialog.setChatId(chatId);
                dialogs.add(dialog);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dialogs;
    }

    public static ArrayList<Message> parseMessage(JSONObject jsonObject) {

        ArrayList<Message> messages = new ArrayList<Message>();

        JSONArray items = null;
        JSONObject object = null;
        try {
            object = jsonObject.getJSONObject("response");

            int count = object.optInt("count");
            Message.setCount(count);

            items = object.getJSONArray("items");





        for (int i = 0; i < items.length(); i++) {

            Message m = new Message();

            JSONObject msg = items.optJSONObject(i);

            String body = msg.optString("body");
            int readState = msg.optInt("read_state");
            int userId = msg.optInt("user_id");
            int userFrom = msg.optInt("user_from");
            int out = msg.optInt("out");
            long date = msg.optLong("date");
            int chatId = msg.optInt("chat_id");

            if (msg.has("attachments")){

                List<Attachment> allAttacments = new ArrayList<>();

                Log.d(Keys.LOG,"json = " + msg.has("attachments"));

                JSONArray attachmentsArray = msg.getJSONArray("attachments");

                for (int n=0;n<attachmentsArray.length();n++){
                    JSONObject oneAttach = attachmentsArray.getJSONObject(n);
                    String type = oneAttach.getString("type");


                    JSONObject content = oneAttach.getJSONObject(type);
                    Log.d(Keys.LOG,"json = " + content.toString());
                    Attachment attachment = Attachment.getInstance(type,content);
                    allAttacments.add(attachment);
                }
                m.setContent(allAttacments);
            }


            m.setBody(body);
            m.setRead_state(readState);
            m.setDate(date);
            m.setUser_id(userId);
            m.setFrom_id(userFrom);
            m.setOut(out);
            m.setChatId(chatId);

            messages.add(m);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return messages;
    }

    public static int connectNotifiny(JSONObject jsonObject) {

        int result = 0;

        try {
            Log.d("my log", "JSON: " + jsonObject.toString());
            result = jsonObject.getInt("response");
        } catch (JSONException e) {
            Log.d("my log", "НЕУДАЧНОЕ ПОДКЛЮЧЕНИЕ NOTIFICATION");
        }
        return result;
    }

    public static String parseUserPhoto(JSONObject jsonObject) {

        String result = "";
        JSONArray array = null;
        try {
            array = jsonObject.getJSONArray("response");
            JSONObject object = array.optJSONObject(0);
            result = object.optString("photo_100");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}

