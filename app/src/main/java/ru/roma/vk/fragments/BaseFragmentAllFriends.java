package ru.roma.vk.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import ru.roma.vk.R;

/**
 * Created by Ilan on 23.09.2017.
 */

public class BaseFragmentAllFriends extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_ALL_FRIEND = "all_friend";
    public static final String FRAGMENT_ONLINE_FRIEND = "online friend";
    public static final String FRAGMENT_CONTACT = "contact";
    public static final String keyId = "keyId";
    private  static  int lastSeen;
    private Button friend, online, contact;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.d("my log", "   ON CREATE VIEW BY BASEFRAGMENT ALL FRIENDS");

        View view = inflater.inflate(R.layout.basefragmentallfriend, null);

        friend = view.findViewById(R.id.button_friend);
        online = view.findViewById(R.id.button_online);
        contact = view.findViewById(R.id.button_contact);

        friend.setOnClickListener((View.OnClickListener) this);
        online.setOnClickListener((View.OnClickListener) this);
        contact.setOnClickListener((View.OnClickListener) this);

        int key;
        if (savedInstanceState != null){
            Log.d("my log","id in base fragment " + savedInstanceState.getInt(keyId));
            key = savedInstanceState.getInt(keyId);
        }else {
            key = 0;
        }
        switch (key){
            case R.id.button_friend:
                onClick(friend);
                break;
            case R.id.button_online:
                onClick(online);
                break;
            case R.id.button_contact:
                onClick(contact);
                break;
            default:
                onClick(friend);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("my log", "ON SAVE INSTANCE STATE IN THE BASE FRAGMENT");
        outState.putInt(keyId,lastSeen);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTran = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.button_friend:
                Log.d("my log","ON CLICK BUTTON FRIEND ONLINE");
                onToggleClick(friend, online, contact);
                AllFriendsFragment fragmentAllFriends = (AllFriendsFragment) getChildFragmentManager().findFragmentByTag(FRAGMENT_ALL_FRIEND);
                if (fragmentAllFriends == null) {
                    Log.d("my log","null fragmentAllFriends");
                    fragmentAllFriends = new AllFriendsFragment();
                }
                fTran.replace(R.id.contain, fragmentAllFriends, FRAGMENT_ALL_FRIEND);
                lastSeen = R.id.button_friend;
                break;

            case R.id.button_online:
                onToggleClick(online, contact, friend);
                OnlineFriendsFragment onlineFriends = (OnlineFriendsFragment) getChildFragmentManager().findFragmentByTag(FRAGMENT_ONLINE_FRIEND);
                if (onlineFriends == null) {
                    Log.d("my log","null onlineFriends");
                    onlineFriends = new OnlineFriendsFragment();
                }
                fTran.replace(R.id.contain, onlineFriends, FRAGMENT_ONLINE_FRIEND);
                lastSeen = R.id.button_online;
                break;

            case R.id.button_contact:
                onToggleClick(contact, friend, online);
                FragmentContact fragmentContact = (FragmentContact) getChildFragmentManager().findFragmentByTag(FRAGMENT_CONTACT);
                if (fragmentContact == null) {
                    Log.d("my log","null fragmentContact");
                    fragmentContact = new FragmentContact();
                }
                fTran.replace(R.id.contain, fragmentContact, FRAGMENT_CONTACT);
                lastSeen = R.id.button_contact;
                break;
        }

        fTran.commit();
    }

    public void onToggleClick(View view, View restart1, View restart2) {

        ((ToggleButton) view).setChecked(true);
        ((ToggleButton) restart1).setChecked(false);
        ((ToggleButton) restart2).setChecked(false);

        view.setSelected(true);
        restart1.setSelected(false);
        restart2.setSelected(false);

    }
}
