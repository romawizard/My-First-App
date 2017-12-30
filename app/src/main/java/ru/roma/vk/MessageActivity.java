package ru.roma.vk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.roma.vk.adapters.MessageAdapter;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.holders.Message;
import ru.roma.vk.myRetrofit.ModelResponseLoadServer;
import ru.roma.vk.myRetrofit.ModelResponseSaveMessagePhoto;
import ru.roma.vk.myRetrofit.ModelUploadServer;
import ru.roma.vk.utilitys.DownloadFile;
import ru.roma.vk.utilitys.JSONParser;

public class MessageActivity extends AppCompatActivity implements MessageView {

    private final int ACTIVITY_CHOOSE_FILE = 1;

    private MessageAdapter messageAdapter;
    private MessagePresenter presenter;
    private EditText text;

    private  String token;

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

        token = getSharedPreferences(Keys.MAINPREF, Context.MODE_PRIVATE).getString(Keys.TOKEN, "no token");


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

    @OnClick(R.id.send)
    public void onSendMessage(){
        Log.d("my log", text.getText().toString());
        presenter.sendMessage(text.getText().toString(), getId());
        text.setText("");

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

    private void uploadServer(final File file){

        MainApplication.getQuery().getUploadServer(token).enqueue(new Callback<ModelUploadServer>() {
            @Override
            public void onResponse(Call<ModelUploadServer> call, Response<ModelUploadServer> response) {
                if (response.body() != null){
                    Log.d(Keys.LOG, "RESPONSE RETROFIT = " + response.body());
                    String urlUploadServer =  response.body().getResponse().getUploadUrl();
                    uploadFile(urlUploadServer,file);
                }else {
                    Toast.makeText(MessageActivity.this,"null in the body",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelUploadServer> call, Throwable t) {
                Toast.makeText(MessageActivity.this,"error retrofit",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadFile (String uploadServerURL, File file) {

        // Создаем RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part используется, чтобы передать имя файла
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        // Выполняем запрос
        Call<ModelResponseLoadServer> call = MainApplication.getQuery().loadPhoto(uploadServerURL, body);
        call.enqueue(new Callback<ModelResponseLoadServer>() {
            @Override
            public void onResponse(Call<ModelResponseLoadServer> call, Response<ModelResponseLoadServer> response) {
                Log.d("retrofit", "success");
                Log.d(Keys.LOG,"response uploadserver = " + response.body());
                Log.d(Keys.LOG,"raw response = " + response.raw());
                ModelResponseLoadServer model= response.body();
                savePhoto(model.getPhoto(),model.getServer(),model.getHash());

            }

            @Override
            public void onFailure(Call<ModelResponseLoadServer> call, Throwable t) {
                Log.d("retrofit", "error in the uploadFile");
            }
        });
    }

    private void savePhoto(String photo, Integer server, String hash) {



        String result = "";
        try {
            result  = URLEncoder.encode(photo,"UTF-8");
            Log.d(Keys.LOG,"result encoder = " + result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        Call<ModelResponseSaveMessagePhoto> call = MainApplication.getQuery().savePhoto(photo,server,hash,token);
        call.enqueue(new Callback<ModelResponseSaveMessagePhoto>() {
            @Override
            public void onResponse(Call<ModelResponseSaveMessagePhoto> call, Response<ModelResponseSaveMessagePhoto> response) {
                Log.d(Keys.LOG, "save raw = " + response.raw());
                Log.d(Keys.LOG, "savePhoto = " + response.body());
            }

            @Override
            public void onFailure(Call<ModelResponseSaveMessagePhoto> call, Throwable t) {
                Log.d(Keys.LOG, "error in the savePhoto");
            }
        });
    }

    public String getRealPathFromURI( Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            Log.d(Keys.LOG,"Result getRealPathFromURI = " + result);
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Keys.LOG, "onActivityResult");
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            if (data != null) {
                Log.d(Keys.LOG, "data != null");
                showChoosenFile(data);
                String realPath = getRealPathFromURI(data.getData());
                File file = new File(realPath);
                Log.d(Keys.LOG, "File from URI = " + file.toString());
                uploadServer(file);
            }
        }
    }
}
