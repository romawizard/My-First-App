package ru.roma.vk.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.roma.vk.holders.Keys;
import ru.roma.vk.R;

public class LoginActivity extends AppCompatActivity {



    private String[] scope = new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.MESSAGES,VKScope.PAGES,VKScope.PHOTOS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_for_sdk);

        String myToken = getSharedPreferences(Keys.MAINPREF, MODE_PRIVATE).getString(Keys.TOKEN, null);
        if (TextUtils.isEmpty(myToken)) {
            VKSdk.login(this, scope);
        }else {
            Intent intent = new Intent(this,MainActivity.class);
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
                getSharedPreferences(Keys.MAINPREF,MODE_PRIVATE).edit().putString(Keys.TOKEN,res.accessToken).commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
