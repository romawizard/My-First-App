package ru.roma.vk.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ru.roma.vk.utilitys.Controller;
import ru.roma.vk.dataBase.DataInformation;
import ru.roma.vk.holders.Friend;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.R;
import ru.roma.vk.UserActivity;
import ru.roma.vk.adapters.FriendsAdapter;

/**
 * Created by Ilan on 24.09.2017.
 */

public class OnlineFriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

     SwipeRefreshLayout  mSwipeRefreshLayout;
    private DataInformation data;
    private ListView listonline;
    private AsynOnline online;
    private FriendsAdapter friendsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        data = new Controller().getTrack();

        View view = inflater.inflate(R.layout.fragmentonlinefiends,null);
        EditText search = view.findViewById(R.id.search_online);

        listonline = view.findViewById(R.id.list_onlinefriends);

        friendsAdapter = new FriendsAdapter();
        listonline.setAdapter(friendsAdapter);

        mSwipeRefreshLayout = view.findViewById(R.id.swiperefresh_online);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.blue));

        online = new AsynOnline();
        online.execute();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                friendsAdapter.getFilter().filter(editable.toString());
            }
        });

        listonline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("my log", "ID: " + String.valueOf(l));

                Intent intent = new Intent(container.getContext(),UserActivity.class);
                intent.putExtra(Keys.KEY_INTENT,l);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onRefresh() {
        listonline.invalidate();
        mSwipeRefreshLayout.setRefreshing(true);
        online = new AsynOnline();
        online.execute();

    }

    private class AsynOnline extends AsyncTask<Void,Void,ArrayList<Friend>>{


        @Override
        protected ArrayList<Friend> doInBackground(Void... voids) {
            data = new Controller().getTrack();
            return data.getAllFriends();
        }

        @Override
        protected void onPostExecute(ArrayList<Friend> friends) {
            super.onPostExecute(friends);

            ArrayList<Friend> onlyonline = new ArrayList<Friend>();

            for (Friend fr: friends){
                if (fr.getOn_line() == 1){
                    onlyonline.add(fr);
                }
            }
            friendsAdapter.setFriends(onlyonline);

            Log.d("my log","my online friends: "+ String.valueOf(onlyonline.size()));

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
