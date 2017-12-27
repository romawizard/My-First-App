package ru.roma.vk.myRetrofit;

import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;

import ru.roma.vk.UserActivity;
import ru.roma.vk.holders.Keys;

/**
 * Created by Ilan on 23.12.2017.
 */

public class ModelUploadServer {

    @SerializedName("response")
    @Expose
    private UploadServer response;

    public UploadServer getResponse() {
        return response;
    }

    public void setResponse(UploadServer response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ModelUploadServer{" +
                "response=" + response +
                '}';
    }

    public class UploadServer{
        @SerializedName("upload_url")
        @Expose
        private String uploadUrl;
        @SerializedName("aid")
        @Expose
        private Integer aid;
        @SerializedName("mid")
        @Expose
        private Integer mid;

        public String getUploadUrl() {
            return uploadUrl;
        }

        public void setUploadUrl(String uploadUrl) {
            this.uploadUrl = uploadUrl;
        }

        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public Integer getMid() {
            return mid;
        }

        public void setMid(Integer mid) {
            this.mid = mid;
        }

        public String getLastParhURL(){

            String result = null;
            try {
                result = new URL(uploadUrl).getFile();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d(Keys.LOG,"LAST PATH = " + result );
            return result;
        }

        @Override
        public String toString() {
            return "UploadServer{" +
                    "uploadUrl='" + uploadUrl + '\'' +
                    ", aid=" + aid +
                    ", mid=" + mid +
                    '}';
        }
    }
}
