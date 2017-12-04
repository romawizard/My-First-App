package ru.roma.vk.post;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import ru.roma.vk.Conected;
import ru.roma.vk.Keys;
import ru.roma.vk.R;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Audio extends Attachment {


    private String title;
    private String url;
    private String artist;
    private int id;
    private int owner_id;
    private long date;
    private long duration;
    MediaPlayer mp;

    @Override
    public View draw() {

        LayoutInflater inflater = (LayoutInflater) Conected.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.audio_view, null);

        TextView audioArtist = view.findViewById(R.id.audio_artist);
        audioArtist.setText(artist);

        TextView audioTitle = view.findViewById(R.id.audio_title);
        audioTitle.setText(title);

        TextView time = view.findViewById(R.id.time_audio);
        time.setText(String.format("%02d:%02d", duration / 60 , duration % 60));

//        ProgressBar pg = view.findViewById(R.id.audio_progressBar);
        return view;
    }

    @Override
    public String toString() {
        return "Audio{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", owner_id=" + owner_id +
                ", duration=" + duration +
                '}';
    }

    @Override
    public void doAction() {
        Intent intent = new Intent(Conected.getInstans(),AudioService.class);
        intent.putExtra(Keys.KEY_URL,url);
        intent.putExtra(Keys.KEY_ARTIST,artist);
        intent.putExtra(Keys.KEY_TITLE,title);
        Conected.getInstans().startService(intent);
    }
}
