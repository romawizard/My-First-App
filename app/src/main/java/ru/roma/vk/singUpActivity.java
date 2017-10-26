package ru.roma.vk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class singUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText number_phone, your_name, your_surname;
    ImageView imageTop, imegeCenter, imageButtom;
    Button singUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        number_phone = (EditText) findViewById(R.id.number_phone);
        your_name = (EditText) findViewById(R.id.your_name);
        your_surname = (EditText) findViewById(R.id.your_surname);
        imageTop = (ImageView) findViewById(R.id.imageTop);
        imegeCenter = (ImageView) findViewById(R.id.imageCenter);
        imageButtom = (ImageView) findViewById(R.id.imageButtom);


        singUp = (Button) findViewById(R.id.startsingup);
        singUp.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.startsingup :
                if (TextUtils.equals(number_phone.getText().toString(),"")){
                    imageTop.setImageResource(R.drawable.error);
                }
                else {
                    imageTop.setImageResource(R.drawable.ok);
                }

                if (TextUtils.equals(your_name.getText().toString(),"")){
                    imegeCenter.setImageResource(R.drawable.error);
                }
                else {
                    imegeCenter.setImageResource(R.drawable.ok);
                }

                if (TextUtils.equals(your_surname.getText().toString(),"")){
                    imageButtom.setImageResource(R.drawable.error);
                }
                else {
                    imageButtom.setImageResource(R.drawable.ok);
                }
                break;


        }



    }
}
