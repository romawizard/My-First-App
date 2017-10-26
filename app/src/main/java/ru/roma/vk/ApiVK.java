package ru.roma.vk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Ilan on 12.09.2017.
 */

public class ApiVK implements DataInformation {

    static private ApiVK apiVK;
    private final String TOKEN;
    private final String LOG = "my log";
    private DB vkdb;

    private ApiVK() {
        vkdb = new DB(Conected.getInstans());
        TOKEN = Conected.getInstans().getSharedPreferences(LoginActivity.MAINPREF, Context.MODE_PRIVATE).getString(LoginActivity.TOKEN, "no token");
    }

    static public ApiVK getApiVK() {
        if (apiVK == null) {
            apiVK = new ApiVK();
        }
        return apiVK;
    }

    private JSONArray conect(String url) {

        HttpsURLConnection connection;
        BufferedReader reader;
        JSONArray jsonArray = null;

        try {
            URL myurl = new URL(url);

            connection = (HttpsURLConnection) myurl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = (InputStream) connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = reader.readLine();

            if (!TextUtils.isEmpty(line)) {
                buffer.append(line);
                Log.d(LOG, "буфер заполнен");
            }
            String resultjson = buffer.toString();

            JSONObject jsonObject = new JSONObject(resultjson);
            jsonArray = jsonObject.getJSONArray("response");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public ArrayList<Dialogs> getAllDialogs(Integer idMsg) {

        String URLQuery = new String();

//        if (idMsg == null){
//            URLQuery = "https://api.vk.com/method/execute.fulldialog?access_token=" + TOKEN + "&v=5.68";
//        }else {
            URLQuery = "https://api.vk.com/method/execute.fulldialog?offset=" + String.valueOf(idMsg) + "&access_token=" + TOKEN + "&v=5.68";
//        }

        ArrayList<Dialogs> dialog = new JSONParser().parseDialog(conect(URLQuery));

        return dialog;
    }

    public ArrayList<Friend> getAllFriends() {

        String URLQuery = "https://api.vk.com/method/friends.get?fields=nickname,photo_100&order=hints&access_token=" + TOKEN;

        Log.d("my log", TOKEN);
        ArrayList<Friend> myfriend = new ArrayList<Friend>();
        JSONArray friends = conect(URLQuery);

        vkdb.open();

        for (int i = 0; i < friends.length(); i++) {
            JSONObject onefriend = null;
            try {
                onefriend = friends.getJSONObject(i);

                String fist_name = onefriend.getString("first_name");
                String last_name = onefriend.getString("last_name");
                String URL_photo = onefriend.getString("photo_100");
                int on_line = onefriend.getInt("online");
                int user_id = onefriend.getInt("user_id");

                vkdb.addFriend(fist_name, last_name, user_id, URL_photo, on_line);

                Friend friend = new Friend(fist_name, last_name, URL_photo, on_line, user_id);
                myfriend.add(friend);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        vkdb.close();
        return myfriend;
    }

    @Override
    public Friend getUser(long id) {

        String URLQuery = "https://api.vk.com/method/users.get?user_ids=" + String.valueOf(id) + "&fields=bdate,city,last_seen,country,status,sex,home_town,about,education,photo_max_orig,online&access_token=" + TOKEN;

        ArrayList<Friend> friends = new JSONParser().parseFriend(conect(URLQuery));

        return friends.get(0);
    }
}