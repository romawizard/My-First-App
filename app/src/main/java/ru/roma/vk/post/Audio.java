package ru.roma.vk.post;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import ru.roma.vk.MainApplication;
import ru.roma.vk.holders.Keys;
import ru.roma.vk.R;
import ru.roma.vk.utilitys.TimeHelper;

/**
 * Created by Ilan on 08.11.2017.
 */

public class Audio extends Attachment {


    private String title;
    private String url;
    private String artist;
    private int id;
    private int owner_id;
    private long date;
    private long duration;
    private transient TextView timeFulfilled;
    private transient TextView timeLeft;
    private transient Button btnPlay;
    private transient SeekBar seekBar;
    private transient AudioService service;
    private transient boolean mUserIsSeeking = false;
    private transient boolean setMaxSeekBar = false;
    private boolean disconnected = false;
    private transient Handler handler;


    @Override
    public View draw() {
        Log.d(Keys.LOG,"DRAW AUDIO");
        LayoutInflater inflater = (LayoutInflater) MainApplication.getInstans().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.audio_view, null);

        TextView audioArtist = view.findViewById(R.id.audio_artist);
        audioArtist.setText(artist);

        TextView audioTitle = view.findViewById(R.id.audio_title);
        audioTitle.setText(title);

        timeLeft = view.findViewById(R.id.time_audio);
        timeLeft.setText(String.format("%02d:%02d", duration / 60 , duration % 60));

        timeFulfilled = view.findViewById(R.id.time_fulfilled_audio);
        seekBar = view.findViewById(R.id.audio_seekBar);
        setListenerSeekBar();
        btnPlay = view.findViewById(R.id.audio_button);
        setBtnPLayListener();

        initializeHandler();

        return view;
    }

    private void bindService(){
        Intent intent = new Intent(MainApplication.getInstans(),AudioService.class);
        MainApplication.getInstans().bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AudioService.AudioBinder mbinder = (AudioService.AudioBinder) iBinder;
            service = mbinder.getService();
            service.setPlaybackInfoListener(new Listener());
            btnPlay.setSelected(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    private void setBtnPLayListener(){
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (service == null) {
                    Log.d(Keys.LOG,"service in audio == null");
                    Intent intent = new Intent(MainApplication.getInstans(), AudioService.class);
                    intent.putExtra(Keys.KEY_URL, url);
                    intent.putExtra(Keys.KEY_ARTIST, artist);
                    intent.putExtra(Keys.KEY_TITLE, title);
                    MainApplication.getInstans().startService(intent);
                    bindService();
                    return;
                }
                if (disconnected){
                    Log.d(Keys.LOG,"DISCONECTED");
                    service.setPlaybackInfoListener(new Listener());
                    disconnected = false;
                }
                if (service.isPlay()) {
                    btnPlay.setSelected(false);
                }else btnPlay.setSelected(true);
                service.pausePlay();
            }
        });
    }

    private void initializeHandler(){
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                refreshTime(message.arg1,message.arg2);
                initializeSeekBar(message.arg1,message.arg2);
                return false;
            }
        });
    }

    private void initializeSeekBar(int max,int position) {
        if (!setMaxSeekBar){
            seekBar.setMax(max);
            setMaxSeekBar = true;
        }
        seekBar.setProgress(position);
        Log.d(Keys.LOG,"initializeSeekBar position = " + position);
    }

    private void refreshTime(int duration, int position) {
        Log.d(Keys.LOG,"TIME: " + TimeHelper.getFormat(duration) + "int = " + duration );
        timeLeft.setText("-" + TimeHelper.getFormat(duration));
        timeFulfilled.setText(TimeHelper.getFormat(position));
    }

    private void setListenerSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int userSelectedPosition = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    userSelectedPosition = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = false;
                if (service!= null){
                service.seekTo(userSelectedPosition);
                }
            }
        });
    }

    @Override
    public String toString() {
        return "Audio{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", owner_id=" + owner_id +
                ", duration=" + duration +
                '}';
    }

    @Override
    public void doAction() {

    }

    private class Listener implements PlayBackInfoListener{

        int duration;

        @Override
        public void onDurationChanged( final int  duration) {
            this.duration = duration;
            Log.d("PLAYER", "onDurationChanged = "+ this.duration);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = Message.obtain();
                    message.arg1 = duration;
                    message.arg2 = 0;
                    handler.sendMessage(message);
                }
            });
            t.start();

        }

        @Override
        public void onPositionChanged(final int position) {
            if (!mUserIsSeeking) {
                final int leftDuration = (duration - position);
                Log.d("PLAYER", "onPositionChanged = "+ leftDuration);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain();
                        message.arg1 = leftDuration;
                        message.arg2 = position;
                        handler.sendMessage(message);
                    }
                });
                t.start();
            }
        }

        @Override
        public void onDisconect() {
            Log.d(Keys.LOG,"onDisconect()");
            btnPlay.setSelected(false);
            setMaxSeekBar = false;
            disconnected = true;
        }
    }
    }

