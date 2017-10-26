package ru.roma.vk;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ilan on 28.09.2017.
 */

public class UserActivity extends AppCompatActivity {

    long id;
    TextView nameUser, status;
    TextView text_online;
    TextView home_town;
    ImageView photoUser, indexOnline;
    LinearLayout llstatus;
    LinearLayout llhome_town;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layuot);


        id = getIntent().getLongExtra(AllFriendsFragment.KEY_INTENT,1);

        indexOnline = (ImageView) findViewById(R.id.phone_online);
        nameUser = (TextView) findViewById(R.id.name_user);
        status = (TextView) findViewById(R.id.status);
        photoUser = (ImageView) findViewById(R.id.photo_user);
        text_online = (TextView) findViewById(R.id.text_online);
        home_town = (TextView) findViewById(R.id.home_town);

        llstatus = (LinearLayout) findViewById(R.id.llstatus);
        llstatus.setVisibility(View.GONE);

        llhome_town = (LinearLayout) findViewById(R.id.llhome_town);
        llhome_town.setVisibility(View.GONE);

        AsynUser asynUser = new AsynUser();
        asynUser.execute();
    }

    private void showInfo(String info,TextView text,LinearLayout linerVisible){
        if (!TextUtils.isEmpty(info)) {
            linerVisible.setVisibility(View.VISIBLE);
            text.setText(info);
        }
    }

    private class AsynUser extends AsyncTask<Void,Void,Friend>{

        @Override
        protected Friend doInBackground(Void... voids) {
            ApiVK apiVK = ApiVK.getApiVK();
            return  apiVK.getUser(id);
        }

        @Override
        protected void onPostExecute(Friend friend) {
            super.onPostExecute(friend);

            nameUser.setText(friend.getFirst_name() + " " + friend.getLast_name());
            DownloadFile df = new DownloadFile();
            df.downloadInUser(friend.getURL_photo(),photoUser);


            showInfo(friend.getStatus(),status,llstatus);

            showInfo(friend.getHome_town(),home_town,llhome_town);

            if (friend.getOn_line() == 1){
                text_online.setText("Онлайн");
            }else {
                text_online.setText(new TimeHelper().getTime(friend.getTime(),friend.getSex()));
            }
            if (friend.getPlatform() == 1){
                indexOnline.setImageResource(R.drawable.phone);
            }
        }
    }

}
