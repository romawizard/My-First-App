package ru.roma.vk.wall;

import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.roma.vk.post.Audio;
import ru.roma.vk.post.Empty;
import ru.roma.vk.post.Photo;
import ru.roma.vk.post.Video;

/**
 * Created by Ilan on 07.01.2018.
 */

public class NewAttachment {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("audio")
    @Expose
    private Audio audio;
    @SerializedName("video")
    @Expose
    private Video video;
    @SerializedName("photo")
    @Expose
    private Photo photo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "NewAttachment{" +
                "type='" + type + '\'' +
                ", audio=" + audio +
                ", video=" + video +
                ", photo=" + photo +
                '}';
    }

    public View draw(){
        switch (type){
            case "video" :
                return  video.draw();
            case  "photo":
                return  photo.draw();
            case "audio":
                return audio.draw();
            default:
                return new Empty().draw();

        }
    }
}
