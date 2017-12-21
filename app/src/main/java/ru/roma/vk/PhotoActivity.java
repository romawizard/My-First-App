package ru.roma.vk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.utilitys.DownloadFile;

/**
 * Created by Ilan on 18.11.2017.
 */

public class PhotoActivity extends AppCompatActivity {

    @BindView(R.id.photo_profil)
    ImageView photoProfil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_profil);
        ButterKnife.bind(this);

        DownloadFile.downloadInUser(getIntent().getStringExtra(Keys.KEY_URL),photoProfil);
    }
}
