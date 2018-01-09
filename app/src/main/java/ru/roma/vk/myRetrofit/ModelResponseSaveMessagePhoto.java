package ru.roma.vk.myRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilan on 27.12.2017.
 */

public class ModelResponseSaveMessagePhoto {

    @SerializedName("response")
    @Expose
    private List<Response> response = null;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ModelResponseSaveMessagePhoto{" +
                "response=" + response +
                '}';
    }

    public static class Response{


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("album_id")
        @Expose
        private Integer albumId;
        @SerializedName("owner_id")
        @Expose
        private Integer ownerId;
        @SerializedName("photo_75")
        @Expose
        private String photo75;
        @SerializedName("photo_130")
        @Expose
        private String photo130;
        @SerializedName("photo_604")
        @Expose
        private String photo604;
        @SerializedName("width")
        @Expose
        private Integer width;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("date")
        @Expose
        private Integer date;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAlbumId() {
            return albumId;
        }

        public void setAlbumId(Integer albumId) {
            this.albumId = albumId;
        }

        public Integer getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Integer ownerId) {
            this.ownerId = ownerId;
        }

        public String getPhoto75() {
            return photo75;
        }

        public void setPhoto75(String photo75) {
            this.photo75 = photo75;
        }

        public String getPhoto130() {
            return photo130;
        }

        public void setPhoto130(String photo130) {
            this.photo130 = photo130;
        }

        public String getPhoto604() {
            return photo604;
        }

        public void setPhoto604(String photo604) {
            this.photo604 = photo604;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getDate() {
            return date;
        }

        public void setDate(Integer date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    ", albumId=" + albumId +
                    ", ownerId=" + ownerId +
                    ", photo75='" + photo75 + '\'' +
                    ", photo130='" + photo130 + '\'' +
                    ", photo604='" + photo604 + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", text='" + text + '\'' +
                    ", date=" + date +
                    '}';
        }
    }
}
