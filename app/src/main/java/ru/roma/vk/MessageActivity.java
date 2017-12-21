package ru.roma.vk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.roma.vk.adapters.MessageAdapter;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.holders.Message;
import ru.roma.vk.utilitys.DownloadFile;

public class MessageActivity extends AppCompatActivity implements MessageView {

    private final int ACTIVITY_CHOOSE_FILE = 1;

    private MessageAdapter messageAdapter;
    private MessagePresenter presenter;
    private EditText text;

    @BindView(R.id.content_layout)
    LinearLayout contentLayout;

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
    protected void onStart() {
        super.onStart();
        Log.d(Keys.LOG, "OnStart Message Activity");
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
    public void click() {
        Log.d("my log", "finish");
        finish();
    }

    @OnClick(R.id.photo_msg)
    public void onClick() {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(Keys.KEY_INTENT, getId());
        startActivity(intent);
    }

    @OnClick(R.id.chose_media)
    public void onChose() {
        Intent chooseFile;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("image/ *");
        startActivityForResult(chooseFile, ACTIVITY_CHOOSE_FILE);
    }

    private void showChoosenFile(Intent data){
        final Uri imageUri = data.getData();
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT,1);
        ImageView image = new ImageView(this);
        image.setLayoutParams(params);
        image.setImageBitmap(selectedImage);
        image.setPadding(5,5,5,5);
        contentLayout.addView(image);
        contentLayout.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Keys.LOG, "onActivityResult");
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            if (data != null) {
                Log.d(Keys.LOG, "data != null");
                showChoosenFile(data);

            }
        }
    }
}
