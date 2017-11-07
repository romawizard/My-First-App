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
    private static int count;

    public Message(String body, int read_state, long date, int user_id, int from_id, int out) {
        this.body = body;
        this.read_state = read_state;
        this.date = date;
        this.user_id = user_id;
        this.from_id = from_id;
        this.out = out;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Message.count = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (getRead_state() != message.getRead_state()) return false;
        if (getDate() != message.getDate()) return false;
        if (getUser_id() != message.getUser_id()) return false;
        if (getFrom_id() != message.getFrom_id()) return false;
        if (getOut() != message.getOut()) return false;
        return getBody() != null ? getBody().equals(message.getBody()) : message.getBody() == null;

    }

    @Override
    public int hashCode() {
        int result = getBody() != null ? getBody().hashCode() : 0;
        result = 31 * result + getRead_state();
        result = 31 * result + (int) (getDate() ^ (getDate() >>> 32));
        result = 31 * result + getUser_id();
        result = 31 * result + getFrom_id();
        result = 31 * result + getOut();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", user_id=" + user_id +
                '}';
    }
}
