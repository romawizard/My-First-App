package ru.roma.vk.post;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.roma.vk.MainApplication;
import ru.roma.vk.PhotoActivity;
import ru.roma.vk.utilitys.DownloadFile;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.R;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Video extends Attachment {

    private String photo_320;
    private String text;
    private String title;
    private String access_key;
    private String first_frame_320;
    private String description;
    private long date;
    private long duration;


    @Override
    public View draw() {
        LayoutInflater inflater = (LayoutInflater) MainApplication.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.view_photo, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        view.setLayoutParams(params);
        final ImageView imageView = view.findViewById(R.id.view_photo);
        imageView.setPadding(2, 2, 2, 2);

        DownloadFile.downloadInUser(photo_320, imageView);

        ImageView iconVideo = view.findViewById(R.id.video_play);


//
//        int costHeight =  view.getHeight()/10;
//        int costWidth =  view.getWidth()/10;
//        Log.d(Keys.LOG,"size width = "+ imageView.getWidth() + " cost size = " + costWidth );
//
//        FrameLayout.LayoutParams paramsIcon = new FrameLayout.LayoutParams(costWidth,costHeight);
//
//        iconVideo.setLayoutParams(paramsIcon);

        iconVideo.setImageResource(R.drawable.video_play);
        iconVideo.setVisibility(View.VISIBLE);

        TextView time = view.findViewById(R.id.time_video);
        time.setText(String.format("%02d:%02d:%02d", duration / 3600, duration / 60 % 60, duration % 60));

        return view;
    }

    @Override
    public void doAction() {
        Intent intent = new Intent(MainApplication.getInstans(), PhotoActivity.class);
        intent.putExtra(Keys.KEY_URL,photo_320);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainApplication.getInstans().startActivity(intent);
    }

    @Override
    public String toString() {
        return "Video{" +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
