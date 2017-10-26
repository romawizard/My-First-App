package ru.roma.vk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class LoginActivity extends AppCompatActivity {

     public final static  String MAINPREF = "mainPref";
    public  final  static  String TOKEN = "token";

    String[] scope = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.MESSAGES,VKScope.PAGES};


    String myToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_for_sdk);

        myToken = getSharedPreferences(MAINPREF,MODE_PRIVATE).getString(TOKEN,null);
        if (TextUtils.isEmpty(myToken)) {
            VKSdk.login(this, scope);
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(TOKEN,myToken);
            startActivity(intent);
            finish();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                getSharedPreferences(MAINPREF,MODE_PRIVATE).edit().putString("token",res.accessToken).commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);


            }

            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
