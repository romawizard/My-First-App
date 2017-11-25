package ru.roma.vk;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import ru.roma.vk.post.Attachment;

/**
 * Created by Ilan on 28.10.2017.
 */

public class Message {

    private static int count;
    private String body;
    private int read_state;
    private long date;
    private int user_id;
    private int from_id;
    private int out;
    private int chatId;
    private String URLPhoto;
    private List<Attachment> content;

    public Message() {
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Message.count = count;
    }

    public List<Attachment> getContent() {
        return content;
    }

    public void setContent(List<Attachment> content) {
        this.content = content;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getURLPhoto() {
        return URLPhoto;
    }

    public void setURLPhoto(String URLPhoto) {
        this.URLPhoto = URLPhoto;

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

    public LinearLayout showContent() {

        int size = content.size();
        int position = 0;

        if (size > 0) {

            Log.d(Keys.LOG, "size attachment = " + size);

            LinearLayout mainLayout = new LinearLayout(Conected.getInstans());

            mainLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            mainLayout.setOrientation(LinearLayout.VERTICAL);


            for (int i = 1; i < 4; i++) {

                if (size == 2){
                    LinearLayout layout = new LinearLayout(Conected.getInstans());
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    for (; position < size && position < 3; position++) {

                        View v = content.get(position).draw(1);
                        final String URL = content.get(position).showContent();

                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Conected.getInstans(),PhotoProfil.class);
                                intent.putExtra(Keys.KEY_URL,URL);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Conected.getInstans().startActivity(intent);
                            }
                        });

                        layout.addView(v);
                    }
                    mainLayout.addView(layout);
                    return mainLayout;

                }

                if (i == 1) {

                    LinearLayout layout = new LinearLayout(Conected.getInstans());
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    View v = content.get(position).draw(1);
                     final String URL = content.get(position).showContent();

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Conected.getInstans(),PhotoProfil.class);
                            intent.putExtra(Keys.KEY_URL,URL);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Conected.getInstans().startActivity(intent);
                        }
                    });

                    layout.addView(v);
                    mainLayout.addView(layout);
                    position++;
                    continue;
                }

                if (i == 2) {
                    float weight = 1;
                    if (size>3){
                        weight = (float) 0.5;
                    }
                    LinearLayout layout = new LinearLayout(Conected.getInstans());
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    layout.setOrientation(LinearLayout.HORIZONTAL);

                    for (; position < size && position < 3; position++) {

                        View v = content.get(position).draw(weight);
                        final String URL = content.get(position).showContent();

                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Conected.getInstans(),PhotoProfil.class);
                                intent.putExtra(Keys.KEY_URL,URL);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Conected.getInstans().startActivity(intent);
                            }
                        });

                        layout.addView(v);
                    }
                    mainLayout.addView(layout);
                    continue;
                }

                if (i == 3) {
                    float weight = 1;
                    if (size>3){
                        weight = (float) 1/(size-3);
                    }

                    LinearLayout layout = new LinearLayout(Conected.getInstans());
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

                    layout.setOrientation(LinearLayout.HORIZONTAL);


                    for (; position < size; position++) {
                        View v = content.get(position).draw(weight);
                        final String URL = content.get(position).showContent();

                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Conected.getInstans(),PhotoProfil.class);
                                intent.putExtra(Keys.KEY_URL,URL);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Conected.getInstans().startActivity(intent);
                            }
                        });
                        layout.addView(v);

                    }
                    mainLayout.addView(layout);
                }
            }
            return mainLayout;
        }
        return new LinearLayout(Conected.getInstans());
    }
}
