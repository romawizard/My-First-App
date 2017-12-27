package ru.roma.vk.myRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilan on 23.12.2017.
 */

public class ModelResponseLoadServer {

    @SerializedName("server")
    @Expose
    private Integer server;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("hash")
    @Expose
    private String hash;

    public Integer getServer() {
        return server;
    }

    public void setServer(Integer server) {
        this.server = server;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "ModelResponseLoadServer{" +
                "server=" + server +
                ", photo='" + photo + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}
