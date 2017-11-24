package ru.roma.vk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static ru.roma.vk.R.layout.itemfriend;

/**
 * Created by Ilan on 02.09.2017.
 */

public class FriendsAdapter extends BaseAdapter implements Filterable {

    private FilterFriend filter;
    private ArrayList<Friend> friends;
    private ArrayList<Friend> filterFriend;

    @Override
    public int getCount() {
        return filterFriend == null ? 0 : filterFriend.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return filterFriend.get(i).getId();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder;
        try {

            if (view == null) {
                LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(itemfriend, viewGroup, false);
                holder = new Holder();
                holder.name = view.findViewById(R.id.friend_name);
                holder.photo = view.findViewById(R.id.image_friend);
                holder.online = view.findViewById(R.id.on_line);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            Friend friend = filterFriend.get(i);
            holder.name.setText(friend.getFirst_name() + " " + friend.getLast_name());

            DownloadFile.downloadInList(friend.getURLPhoto(), holder.photo);

            if (new Controller().getTrack() instanceof ApiVK) {
                if (friend.getOn_line() == 1) {
                    holder.online.setImageResource(R.drawable.online_list);
                } else
                    holder.online.setImageDrawable(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterFriend();
        }
        return filter;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
        filterFriend = new ArrayList<Friend>();
        filterFriend.addAll(friends);
        notifyDataSetChanged();
    }

    private static class Holder {
        TextView name;
        ImageView photo;
        ImageView online;
    }

    private class FilterFriend extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (charSequence.length() > 0) {
                ArrayList<Friend> filterItems = new ArrayList<Friend>();

                for (int i = 0; i < friends.size(); i++) {

                    String s = friends.get(i).getFirst_name().toLowerCase() + " " + friends.get(i).getLast_name().toLowerCase();

                    Log.d("my log", s + " " + charSequence);
                    if (s.contains(charSequence)) {
                        filterItems.add(friends.get(i));
                    }
                    result.count = filterItems.size();
                    result.values = filterItems;
                }
            } else {
                synchronized (this) {
                    result.values = friends;
                    result.count = friends == null ? 0 : friends.size();
                }
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filterFriend = (ArrayList<Friend>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

