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

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity implements MessageView {

    private MessageAdapter messageAdapter;
    private MessagePresenter presenter;
    private EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        messageAdapter = new MessageAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);

        final RecyclerView userList = (RecyclerView) findViewById(R.id.list_message);
        userList.setLayoutManager(layoutManager);
        userList.setAdapter(messageAdapter);

        userList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastitem = (((LinearLayoutManager) userList.getLayoutManager()).findLastVisibleItemPosition()) + 1;

                if (lastitem < Message.getCount() && lastitem == messageAdapter.getCount() && !presenter.isLoading()) {
                    presenter.isReady();
                }
            }
        });

        text = (EditText) findViewById(R.id.text_msg);

        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("my log", text.getText().toString());
                presenter.sendMessage(text.getText().toString(), getId());
                text.setText("");
            }
        });

        TextView name = (TextView) findViewById(R.id.name_msg);
        name.setText(getIntent().getStringExtra(Keys.KEY_NAME));

        ImageView photo = (ImageView) findViewById(R.id.photo_msg);
        DownloadFile.downloadInList(getIntent().getStringExtra(Keys.KEY_PHOTO), photo);

        ImageView online = (ImageView) findViewById(R.id.online_msg);
        if (getIntent().getIntExtra(Keys.KEY_ONLINE, 0) == 1) {
            online.setVisibility(View.VISIBLE);
        } else {
            online.setVisibility(View.GONE);
        }

        MessageModel model = new MessageModel(getId());
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
        return (int) getIntent().getLongExtra(Keys.KEY_ID, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dettach();
    }

    @OnClick(R.id.back_button)
    public void click(){
        Log.d("my log", "finish");
        finish();
    }
}
