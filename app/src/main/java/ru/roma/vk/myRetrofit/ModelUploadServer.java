package ru.roma.vk.myRetrofit;

/**
 * Created by Ilan on 20.12.2017.
 */

public class ModelUploadServer {

    private  String upload_url;
    private int aid;
    private int mid;

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
