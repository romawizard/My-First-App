package ru.roma.vk;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Ilan on 16.11.2017.
 */

public class MyService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("my log", "Refreshed token : " + refreshedToken);
        getSharedPreferences(Keys.MAINPREF, MODE_PRIVATE).edit().putString(Keys.TOKEN_NOTIF, refreshedToken).commit();
    }
}
