package ru.roma.vk;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Ilan on 12.09.2017.
 */

public class ApiVK implements DataInformation {

    private static ApiVK apiVK;
    private final String TOKEN;

    private ApiVK() {
        TOKEN = Conected.getInstans().getSharedPreferences(Keys.MAINPREF, Context.MODE_PRIVATE).getString(Keys.TOKEN, "no token");
    }

    static public ApiVK getInstance() {
        if (apiVK == null) {
            apiVK = new ApiVK();
        }
        return apiVK;
    }

    private JSONObject conect(String url) {

        HttpsURLConnection connection;
        BufferedReader reader;
        JSONObject jsonObject = null;

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
                Log.d(Keys.LOG, "буфер заполнен");
            }
            String resultjson = buffer.toString();

            jsonObject = new JSONObject(resultjson);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public ArrayList<Dialogs> getAllDialogs(Integer offset) {

        String URLQuery = "https://api.vk.com/method/execute.fulldialog?offset=" + String.valueOf(offset) + "&access_token=" + TOKEN + "&v=5.68";

        ArrayList<Dialogs> dialog = new JSONParser().parseDialog(conect(URLQuery));

        Log.d(Keys.LOG, TOKEN);

        return dialog;
    }

    public ArrayList<Friend> getAllFriends() {

        String URLQuery = "https://api.vk.com/method/friends.get?fields=nickname,photo_100&order=hints&access_token=" + TOKEN;

        return JSONParser.parseFriend(conect(URLQuery));
    }

    @Override
    public Friend getUser(long id) {

        String URLQuery = "https://api.vk.com/method/users.get?user_ids=" + String.valueOf(id) + "&fields=bdate,city,last_seen,country,status,sex,home_town,about,education,photo_max_orig,online&access_token=" + TOKEN;

        ArrayList<Friend> friends = new JSONParser().parseFriend(conect(URLQuery));

        return friends.get(0);
    }

    @Override
    public ArrayList<Message> getMessage(int userId, int offset) {

        String URLQuery = "https://api.vk.com/method/messages.getHistory?user_id=" + userId + "&offset=" + offset + "&access_token=" + TOKEN + "&v=5.68";

        return new JSONParser().parseMessage(conect(URLQuery));
    }

    @Override
    public void sendMessage(String text, int id) {

        String utf = "";

        try {
            utf = URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("my log", "кодировка не прошла");
        }

        String URLQuery = "https://api.vk.com/method/messages.send?user_id=" + id + "&message=" + utf + "&access_token=" + TOKEN + "&v=5.68";
        Log.d("my log", URLQuery);
        conect(URLQuery);
    }

    public int nonification() {

        String nonifToken = Conected.getInstans().getSharedPreferences(Keys.MAINPREF, Context.MODE_PRIVATE).getString(Keys.TOKEN_NOTIF, "no");

        String androidID = Settings.Secure.getString(Conected.getInstans().getContentResolver(), Settings.Secure.ANDROID_ID);

        String json = "{\"msg\":\"on\", \"chat\":[\"no_sound\",\"no_text\"], \"friend\":\"on\", \"reply\":\"on\", \"mention\":\"fr_of_fr\"}";
        json = URLEncoder.encode(json);
        String URLQuery = "https://api.vk.com/method/account.registerDevice?token=" + nonifToken + "&device_id="
                + androidID + "&access_token=" + TOKEN + "&v=5.68";


//         + "&setting=" + json
        return JSONParser.connectNotifiny(conect(URLQuery));
    }

    public String getUserPhoto(int userId) {

        final String URLQuery = "https://api.vk.com/method/users.get?user_ids=" + userId + "&fields=photo_100&access_token=" + TOKEN + "&v=5.68";

        return JSONParser.parseUserPhoto(conect(URLQuery));
    }
}