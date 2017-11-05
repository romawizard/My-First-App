package ru.roma.vk;

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

/**
 * Created by Ilan on 12.09.2017.
 */

public class AllFriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FriendsAdapter friendsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FriendAsyn friendAsyn;
    private ListView friendList;
    public static final String KEY = "key";
    public static final String KEY_INTENT = "intent";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("my log", "       ON CREATE FRAGMENT ALL FRIENDS");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        Log.d("my log", "           ON CRETE VIEW FRAGMENT ALL FRIEND");

        View v = inflater.inflate(R.layout.fragmentallfriends, null);

        friendList = v.findViewById(R.id.list_friends);

        EditText searchFriend = v.findViewById(R.id.search_friend);

        mSwipeRefreshLayout = v.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.blue));


        friendAsyn = new FriendAsyn();
        friendAsyn.execute();

        friendsAdapter = new FriendsAdapter();
        friendList.setAdapter(friendsAdapter);

        searchFriend.addTextChangedListener(new TextWatcher() {
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

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("my log", "ID: " + String.valueOf(l));

                Intent intent = new Intent(container.getContext(),UserActivity.class);
                intent.putExtra(KEY_INTENT,l);
                startActivity(intent);
            }
        });

        return v;

    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        friendAsyn = new FriendAsyn();
        friendAsyn.execute();
    }

    private class FriendAsyn extends AsyncTask<Void, Void, ArrayList<Friend>> {

        @Override
        protected ArrayList<Friend> doInBackground(Void... voids) {

            return new Controller().getTrack().getAllFriends();
        }

        @Override
        protected void onPostExecute(final ArrayList<Friend> friends) {
            super.onPostExecute(friends);
            friendsAdapter.setFriends(friends);

            Log.d("my log", "my friends: " + String.valueOf(friends.size()));

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
