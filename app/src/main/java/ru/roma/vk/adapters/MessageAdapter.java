package ru.roma.vk.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.roma.vk.ApiVK;
import ru.roma.vk.utilitys.DownloadFile;
import ru.roma.vk.holders.Message;
import ru.roma.vk.R;
import ru.roma.vk.utilitys.TimeHelper;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private LayoutInflater inflater;
    private List<Message> messages;


    public MessageAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        messages = new ArrayList<>(20);
    }

    public int getCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_message, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    public void setMessages(List<Message> messages) {

        this.messages = messages;
        notifyDataSetChanged();
        Log.d("my log", "messaages = " + messages.size());
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {

        private static Map<Integer,String>  cash = new HashMap(20);
        private Handler h;
        private TextView bodyr, timer, bodyl, timel;
        private ImageView photoUser;
        private RelativeLayout rightLiner;
        private LinearLayout leftLiner, content;

        public MessageHolder(View itemView) {
            super(itemView);
            timer = itemView.findViewById(R.id.timer_msg);
            bodyr = itemView.findViewById(R.id.bodyr_msg);
            timel = itemView.findViewById(R.id.timel_msg);
            bodyl = itemView.findViewById(R.id.bodyl_msg);
            content = itemView.findViewById(R.id.content);

            leftLiner = itemView.findViewById(R.id.leftLiner);
            leftLiner.setVisibility(View.GONE);

            rightLiner = itemView.findViewById(R.id.rightLiner);
            rightLiner.setVisibility(View.GONE);

            photoUser =itemView.findViewById(R.id.photo_user_msg);
            photoUser.setVisibility(View.GONE);

        }

        void bind(final Message message) {

            String time = new TimeHelper().getTime(message.getDate());

            if (message.getOut() == 0) {
                leftLiner.setVisibility(View.VISIBLE);
                bodyl.setText(message.getBody());
                timel.setText(time);
                rightLiner.setVisibility(View.GONE);
            } else {
                rightLiner.setVisibility(View.VISIBLE);
                bodyr.setText(message.getBody());
                timer.setText(time);
                leftLiner.setVisibility(View.GONE);
            }


            if (message.getChatId()!= 0 ){

                if (cash.containsKey(message.getUser_id())){
                    message.setURLPhoto(cash.get(message.getUser_id()));
                    Log.d("my log", "  map = " + cash.size());

                }

                if (TextUtils.isEmpty(message.getURLPhoto())) {

                    h = new Handler() {
                        @Override
                        public void handleMessage(android.os.Message msg) {
                            message.setURLPhoto((String) msg.obj);

                            cash.put(message.getUser_id(),message.getURLPhoto());

                            Log.d("my log", "URLPhoto = " +message.getURLPhoto());
                            DownloadFile.downloadInList(message.getURLPhoto(), photoUser);
                            photoUser.setVisibility(View.VISIBLE);
                        }
                    };

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            String URL = ApiVK.getInstance().getUserPhoto(message.getUser_id());
                            android.os.Message m = h.obtainMessage(0,URL);
                            h.sendMessage(m);

                        }
                    });

                    t.start();
                }else {
                    DownloadFile.downloadInList(message.getURLPhoto(),photoUser);
                    photoUser.setVisibility(View.VISIBLE);
                }
            }

            if (message.getContent() != null){
                content.removeAllViews();
                content.addView(message.showContent());
            }else {
                content.removeAllViews();
            }
        }
    }
}
