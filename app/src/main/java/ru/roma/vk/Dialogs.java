package ru.roma.vk;

import java.util.List;

/**
 * Created by Ilan on 17.09.2017.
 */

public class Dialogs {

    private String title;
    private String body;
    private String first_name;
    private String last_name;
    private String myphoto;
    private String URLPhoto;
    private long time;
    private int out;
    private int userId;
    private int readeState;
    private int onLine;
    private static int count;
    private Integer idMsg;

            Dialogs(){}


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    private  int chatId;

    public List<Integer> getCharActives() {
        return charActives;
    }

    public void setCharActives(List<Integer> charActives) {
        this.charActives = charActives;
    }

    private List<Integer>  charActives;

    @Override
    public String toString() {
        return "Dialogs{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    public int getUserId() {
        if (chatId!=0){
            return 2000000000 + chatId;
        }
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
       Dialogs.count = count;
    }

    public  int getIdMsg() {
        return idMsg;
    }

    public  void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }

    public void setOut(int out) {
        this.out = out;
    }

    public void setMyphoto(String myphoto) {
        this.myphoto = myphoto;
    }

    public String getMyphoto() {
        return myphoto;
    }

    public int getOut() {
        return out;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setURLPhoto(String URLPhoto) {
        this.URLPhoto = URLPhoto;
    }

    public String getURLPhoto() {
        return URLPhoto;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getReadeState() {
        return readeState;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setReadeState(int readeState) {
        this.readeState = readeState;
    }

    public  String getTitle(){
        return title;
    }

    public  String getBody(){
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dialogs dialogs = (Dialogs) o;

        if (time != dialogs.time) return false;
        if (out != dialogs.out) return false;
        if (readeState != dialogs.readeState) return false;
        if (onLine != dialogs.onLine) return false;
        if (title != null ? !title.equals(dialogs.title) : dialogs.title != null) return false;
        if (body != null ? !body.equals(dialogs.body) : dialogs.body != null) return false;
        if (first_name != null ? !first_name.equals(dialogs.first_name) : dialogs.first_name != null)
            return false;
        if (last_name != null ? !last_name.equals(dialogs.last_name) : dialogs.last_name != null)
            return false;
        if (myphoto != null ? !myphoto.equals(dialogs.myphoto) : dialogs.myphoto != null)
            return false;
        if (URLPhoto != null ? !URLPhoto.equals(dialogs.URLPhoto) : dialogs.URLPhoto != null)
            return false;
        return idMsg != null ? idMsg.equals(dialogs.idMsg) : dialogs.idMsg == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (myphoto != null ? myphoto.hashCode() : 0);
        result = 31 * result + (URLPhoto != null ? URLPhoto.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + out;
        result = 31 * result + readeState;
        result = 31 * result + onLine;
        result = 31 * result + (idMsg != null ? idMsg.hashCode() : 0);
        return result;
    }
}
