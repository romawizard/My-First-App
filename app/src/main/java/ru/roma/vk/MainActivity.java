package ru.roma.vk;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BASE_FRAGMENT = "base_Fragment";
    public static final String FRAGMENT_DIALOG = "fragment_dialog";
    public static final String FRAGMENT_SEARCH = "fragment_search";
    public static final String FRAGMENT_SETTINGS = "fragment_settings";
    private static final String keyInt = "button";
    private   int lastScreen;

    LinearLayout msg, contact, search, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.d("my log", "ON CREATE MAIN ACTIVITY");

        msg = (LinearLayout) findViewById(R.id.msg);
        msg.setOnClickListener(this);

        contact = (LinearLayout) findViewById(R.id.contact);
        contact.setOnClickListener(this);

        search = (LinearLayout) findViewById(R.id.search);
        search.setOnClickListener(this);

        settings = (LinearLayout) findViewById(R.id.settings);
        settings.setOnClickListener(this);

        int key;
        if (savedInstanceState == null) {
            key = 0;
        } else {
            Log.d("my log","id fragment " + String.valueOf(savedInstanceState.getInt(keyInt)));
            key = savedInstanceState.getInt(keyInt);
        }
//        switch (key) {
//            case R.id.msg:
//                onClick(msg);
//                break;
//            case R.id.contact:
//                onClick(contact);
//                break;
//            case R.id.settings:
//                onClick(settings);
//                break;
//            case R.id.search:
//                onClick(search);
//                break;
//            default:
//                onClick(contact);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("my log", "OnSaveInstanceState MAIN ACTIVITY");
        outState.putInt(keyInt, lastScreen);
    }


    public void onToggleClick(View view, View restart1, View restart2, View restart3) {

        view.setSelected(true);
        restart1.setSelected(false);
        restart2.setSelected(false);
        restart3.setSelected(false);
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction fTran = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.msg:
                Log.d("my log", "on click button msg");
                onToggleClick(msg, search, settings, contact);
                 FragmentDialogs frgDialogs = (FragmentDialogs) getFragmentManager().findFragmentByTag(FRAGMENT_DIALOG);
                if (frgDialogs == null)
                    Log.d("my log", "       null in the msg");{
                    frgDialogs = new FragmentDialogs();
                }
                fTran.replace(R.id.fragcont, frgDialogs, FRAGMENT_DIALOG);
                lastScreen = R.id.msg;
                break;

            case R.id.contact:
                Log.d("my log", "on click button contact");
                onToggleClick(contact, msg, search, settings);
                BaseFragmentAllFriends basefrgAllFriends = (BaseFragmentAllFriends) getFragmentManager().findFragmentByTag(BASE_FRAGMENT);
                if (basefrgAllFriends == null) {
                    Log.d("my log", "       null in the contact");
                    basefrgAllFriends = new BaseFragmentAllFriends();
                }
                fTran.replace(R.id.fragcont, basefrgAllFriends, BASE_FRAGMENT);
                lastScreen = R.id.contact;
                break;

            case R.id.search:
                Log.d("my log", "on click button search");
                onToggleClick(search, settings, contact, msg);
               FragmentSearch frgsearch = (FragmentSearch) getFragmentManager().findFragmentByTag(FRAGMENT_SEARCH);
                if (frgsearch == null) {
                    Log.d("my log", "       null in the search");
                    frgsearch = new FragmentSearch();
                }
                fTran.replace(R.id.fragcont, frgsearch);
                lastScreen = R.id.search;
                break;

            case R.id.settings:
                Log.d("my log", "on click button settings");
                onToggleClick(settings, search, msg, contact);
                FragmentSetting frgsettings = (FragmentSetting) getFragmentManager().findFragmentByTag(FRAGMENT_SETTINGS);
                if (frgsettings == null) {
                    Log.d("my log", "       null in the settings");
                    frgsettings = new FragmentSetting();
                }
                fTran.replace(R.id.fragcont, frgsettings);
                lastScreen = R.id.settings;
                break;
        }
           fTran.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("my log", "resume main Activity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("my log", "restart main Activity");
        if (lastScreen == R.id.msg){
            Pagination p = Pagination.getInstans();
            p.deletCash();
            FragmentDialogs.DialogAsyn dialogAsyn = new FragmentDialogs.DialogAsyn();
            dialogAsyn.execute();
        }
    }
}
