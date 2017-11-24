package ru.roma.vk.post;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.gson.Gson;

import org.json.JSONObject;

import ru.roma.vk.Conected;
import ru.roma.vk.post.Attachment;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Video extends Attachment {

    private String photo_320;
    private String text;
    private String title;
    private String access_key;
    private long date;
    private long duration;



    @Override
    public View draw() {
        return new LinearLayout(Conected.getInstans());
    }


    @Override
    public String toString() {
        return "Video{" +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
