package ru.roma.vk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements MessageView {

    MessageAdapter messageAdapter;
    MessagePresenter presenter;
    private TextView name;
    private ImageView photo, online;
    private EditText text;
    private Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }

    private void init() {

        messageAdapter = new MessageAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        RecyclerView userList = (RecyclerView) findViewById(R.id.list_message);
        userList.setLayoutManager(layoutManager);
        userList.setAdapter(messageAdapter);

        text = (EditText) findViewById(R.id.text_msg);

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("my log",text.getText().toString());
                presenter.sendMessage(text.getText().toString(),getId());
                text.setText("");
            }
        });

        name = (TextView) findViewById(R.id.name_msg);
        name.setText(getIntent().getStringExtra(FragmentDialogs.KEY_NAME));

        photo = (ImageView) findViewById(R.id.photo_msg);
        DownloadFile.downloadInList(getIntent().getStringExtra(FragmentDialogs.KEY_PHOTO),photo);

        online = (ImageView) findViewById(R.id.online_msg);
        if (getIntent().getIntExtra(FragmentDialogs.KEY_ONLINE,0) == 1){
            online.setVisibility(View.VISIBLE);
        }else {
            online.setVisibility(View.GONE);
        }

        MessageModel model = new MessageModel();
        presenter = new MessagePresenter(model);
        presenter.attachView(this);
        presenter.isReady();
    }

    @Override
    public void setMessage(List<Message> messages) {
        messageAdapter.setMessages(messages);
    }

    @Override
    public int getId() {
        return (int) getIntent().getLongExtra(FragmentDialogs.KEY_ID, 0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }
}
