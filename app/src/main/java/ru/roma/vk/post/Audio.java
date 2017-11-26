package ru.roma.vk.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import ru.roma.vk.Conected;
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

    @Override
    public View draw() {

        LayoutInflater inflater = (LayoutInflater) Conected.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.audio_view, null);

        TextView audioArtist = view.findViewById(R.id.audio_artist);
        audioArtist.setText(artist);

        TextView audioTitle = view.findViewById(R.id.audio_title);
        audioTitle.setText(title);

        ProgressBar pg = view.findViewById(R.id.audio_progressBar);
        pg.setMax(100);
        pg.setProgress(0);


        return view;
    }

    @Override
    public void doAction() {

    }
}
