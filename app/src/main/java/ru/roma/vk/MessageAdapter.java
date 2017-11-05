package ru.roma.vk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static ru.roma.vk.R.id.body;
import static ru.roma.vk.R.id.leftLiner;

/**
 * Created by Ilan on 28.10.2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private LayoutInflater inflater;
    private List<Message> messages;

    public int getCount(){
        return  messages == null ? 0 :messages.size();
    }

    public MessageAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return messages == null ? 0: messages.size() ;
    }

    public  void setMessages(List<Message> messages){
        if (!(this.messages == null)){
            this.messages.clear();
            this.messages.addAll(messages);
            notifyDataSetChanged();
        }else{
            this.messages = messages;
        }
        notifyDataSetChanged();
        Log.d("my log", "messaages = " + messages.size());
    }

    public  static class MessageHolder extends RecyclerView.ViewHolder {

        public  TextView bodyr, timer ,bodyl, timel, name;

        public RelativeLayout  rightLiner;
        LinearLayout leftLiner;

        public MessageHolder(View itemView) {
            super(itemView);
            timer = itemView.findViewById(R.id.timer_msg);
            bodyr = itemView.findViewById(R.id.bodyr_msg);
            timel = itemView.findViewById(R.id.timel_msg);
            bodyl = itemView.findViewById(R.id.bodyl_msg);
            leftLiner = itemView.findViewById(R.id.leftLiner);
            leftLiner.setVisibility(View.GONE);
            rightLiner = itemView.findViewById(R.id.rightLiner);
            rightLiner.setVisibility(View.GONE);
        }

        void  bind(Message message){

            String time = new TimeHelper().getTime(message.getDate());

            if (message.getOut() == 0){
                leftLiner.setVisibility(View.VISIBLE);
                bodyl.setText(message.getBody());
                timel.setText(time);
                rightLiner.setVisibility(View.GONE);
            }else {
                rightLiner.setVisibility(View.VISIBLE);
                bodyr.setText(message.getBody());
                timer.setText(time);
                leftLiner.setVisibility(View.GONE);

            }
        }
    }
}
