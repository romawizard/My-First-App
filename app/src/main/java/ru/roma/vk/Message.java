package ru.roma.vk;

/**
 * Created by Ilan on 28.10.2017.
 */

public class Message {

    private String body;
    private int read_state;
    private long date;
    private int user_id;
    private int from_id;
    private int out;

    public Message(String body, int read_state, long date, int user_id, int from_id, int out) {
        this.body = body;
        this.read_state = read_state;
        this.date = date;
        this.user_id = user_id;
        this.from_id = from_id;
        this.out = out;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRead_state() {
        return read_state;
    }

    public void setRead_state(int read_state) {
        this.read_state = read_state;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }
}
