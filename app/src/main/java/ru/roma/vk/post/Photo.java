package ru.roma.vk.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import ru.roma.vk.Conected;
import ru.roma.vk.DownloadFile;
import ru.roma.vk.R;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Photo extends Attachment {

    private String photo_604;
    private String text;
    private String access_key;
    private long date;

    @Override
    public View draw() {
        LayoutInflater inflater = (LayoutInflater) Conected.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_photo, null);

        ImageView imageView = view.findViewById(R.id.view_photo);
        imageView.setMaxHeight(300);
        imageView.setMaxWidth(300);
        imageView.setPadding(5,5,5,5);


        DownloadFile.downloadInUser(photo_604, imageView);

        return view;
    }

    @Override
    public String toString() {
        return "Photo{" +
                ", photo_604='" + photo_604 + '\'' +
                ", text='" + text + '\'' +
                ", access_key='" + access_key + '\'' +
                ", date=" + date +
                '}';
    }
}
