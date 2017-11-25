package ru.roma.vk.post;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.roma.vk.Conected;
import ru.roma.vk.DownloadFile;
import ru.roma.vk.Keys;
import ru.roma.vk.R;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Photo extends Attachment {

    public String getPhoto_604() {
        return photo_604;
    }

    public void setPhoto_604(String photo_604) {
        this.photo_604 = photo_604;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    private String photo_604;
    private String text;
    private String access_key;
    private long date;

    @Override
    public View draw(float weight) {
        LayoutInflater inflater = (LayoutInflater) Conected.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_photo, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,weight);

        view.setLayoutParams(params);

        ImageView imageView = view.findViewById(R.id.view_photo);
        imageView.setPadding(2,2,2,2);


        DownloadFile.downloadInUser(photo_604, imageView);

        return view;
    }

    @Override
    public String showContent() {
        return getPhoto_604();
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
