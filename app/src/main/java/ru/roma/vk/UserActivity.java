package ru.roma.vk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ilan on 28.09.2017.
 */

public class UserActivity extends AppCompatActivity {

    long id;
    private Friend friend;

    @BindView(R.id.photo_user)
    ImageView photoUser;
    @BindView(R.id.name_user)
    TextView nameUser;
    @BindView(R.id.text_online)
    TextView textOnline;
    @BindView(R.id.phone_online)
    ImageView phoneOnline;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.llstatus)
    LinearLayout llstatus;
    @BindView(R.id.home_town)
    TextView homeTown;
    @BindView(R.id.llhome_town)
    LinearLayout llhomeTown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layuot);
        ButterKnife.bind(this);

        id = getIntent().getLongExtra(AllFriendsFragment.KEY_INTENT, 1);

        llstatus.setVisibility(View.GONE);

        llhomeTown.setVisibility(View.GONE);

        AsynUser asynUser = new AsynUser();
        asynUser.execute();
    }

    private void showInfo(String info, TextView text, LinearLayout linerVisible) {
        if (!TextUtils.isEmpty(info)) {
            linerVisible.setVisibility(View.VISIBLE);
            text.setText(info);
        }
    }

    private void ready(Friend friend) {
        nameUser.setText(friend.getFirst_name() + " " + friend.getLast_name());
        DownloadFile df = new DownloadFile();
        df.downloadInUser(friend.getURLPhoto(), photoUser);


        showInfo(friend.getStatus(), status, llstatus);

        showInfo(friend.getHome_town(),homeTown, llhomeTown);

        if (friend.getOn_line() == 1) {
         textOnline.setText("Онлайн");
        } else {
            textOnline.setText(new TimeHelper().getTime(friend.getTime(), friend.getSex()));
        }
        if (friend.getPlatform() == 1) {
            phoneOnline.setImageResource(R.drawable.phone);
        }

    }

    @OnClick(R.id.send_message)
    public void click() {

        String name = friend.getFirst_name() + " " + friend.getLast_name();
        String URL = friend.getURLPhoto();
        int online = friend.getOn_line();

        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(Keys.KEY_ID, id);
        intent.putExtra(Keys.KEY_NAME, name);
        intent.putExtra(Keys.KEY_PHOTO, URL);
        intent.putExtra(Keys.KEY_ONLINE, online);
        startActivity(intent);
    }

    private class AsynUser extends AsyncTask<Void, Void, Friend> {

        @Override
        protected Friend doInBackground(Void... voids) {
            ApiVK apiVK = ApiVK.getInstance();
            return apiVK.getUser(id);
        }

        @Override
        protected void onPostExecute(Friend friend) {
            super.onPostExecute(friend);
            ready(friend);
            UserActivity.this.friend = friend;
        }
    }

}
