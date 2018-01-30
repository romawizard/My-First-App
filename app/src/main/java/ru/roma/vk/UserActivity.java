package ru.roma.vk;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roma.vk.holders.Friend;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.utilitys.DownloadFile;
import ru.roma.vk.utilitys.TimeHelper;
import ru.roma.vk.wall.ModelResponseWall;
import ru.roma.vk.wall.WallPost;

/**
 * Created by Ilan on 28.09.2017.
 */

public class UserActivity extends AppCompatActivity {

    int id;
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

        id = getIntent().getIntExtra(Keys.KEY_INTENT, 1);

        Log.d("my log", "id user " + id);

        AsynWall wall = new AsynWall();
        wall.execute(id);

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

    @OnClick(R.id.photo_user)
    public  void onClick(){
        Intent intent = new Intent(this,PhotoActivity.class);
        intent.putExtra(Keys.KEY_URL,friend.getURLPhoto());
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

    private class AsynWall extends AsyncTask<Integer,Void,List<WallPost>>{

        @Override
        protected List<WallPost> doInBackground(Integer... integers) {
            return ApiVK.getInstance().getWallPost(integers[0],0);
        }

        @Override
        protected void onPostExecute(List<WallPost> wallPosts) {
            super.onPostExecute(wallPosts);
            Log.d(Keys.LOG,"wallPost = " + wallPosts.toString());

            Log.d("example", " size = "+wallPosts.size());
        }
    }

}
