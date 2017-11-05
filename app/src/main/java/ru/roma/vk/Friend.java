package ru.roma.vk;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ilan on 02.09.2017.
 */

public class Friend {

    private String first_name;
    private String last_name;
    private String status;
    private String home_town;
    private String URL_photo;
    private int platform;
    private int sex;
    private int on_line;
    private int id;
    private long time;


    public Friend(Friend fr) {
        this.first_name = fr.getFirst_name();
        this.last_name = fr.getLast_name();
        this.status = fr.getStatus();
        this.home_town = fr.getHome_town();
        this.URL_photo = fr.getURL_photo();
        this.platform = fr.getPlatform();
        this.sex = fr.getSex();
        this.on_line = fr.getOn_line();
        this.id = fr.getId();
        this.time = fr.getTime();
    }


    public Friend(){}

    public Friend(String first_name, String last_name, String URL_photo, int on_line, int id) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.on_line = on_line;
        this.URL_photo = URL_photo;
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOn_line(int on_line) {
        this.on_line = on_line;
    }

    public void setURL_photo(String URL_photo) {
        this.URL_photo = URL_photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setHome_town(String home_town) {
        this.home_town = home_town;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getURL_photo() {
        return URL_photo;
    }

    public String getStatus(){
        return status;
    }

    public String getHome_town(){
        return home_town;
    }

    public int getOn_line() {
        return on_line;
    }

    public int getPlatform() {
        return platform;
    }

    public int getSex(){
        return  sex;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

}
