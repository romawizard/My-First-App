package ru.roma.vk.post;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ru.roma.vk.Keys;
import ru.roma.vk.R;

/**
 * Created by Ilan on 29.11.2017.
 */

public class AudioService extends Service implements MediaPlayer.OnPreparedListener {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;
    private String URL;
    private MediaPlayer mp;
    private String title;
    private String artist;
    private AudioBinder binder;
    private boolean isStoop = false;
    private PlayBackInfoListener mListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;


    @Override
    public void onCreate() {
        super.onCreate();
        binder = new AudioBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        releaseMP();

        URL = intent.getStringExtra(Keys.KEY_URL);
        title = intent.getStringExtra(Keys.KEY_TITLE);
        artist = intent.getStringExtra(Keys.KEY_ARTIST);
        connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    public void releaseMP() {
        if (mp != null) {
            try {
                mp.release();
                mp = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showNotification() {

        Intent intent = new Intent(this, AudioPlayer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.musique_audio_music)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.musique_audio_music))
                .setContentTitle(title)
                .setContentText(artist)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            isStoop = true;
            stopUpdatingCallbackWithPosition(true);
        }
    }

    public void pause() {
        if (mp != null && mp.isPlaying()) {
            mp.pause();
        } else {
            if (isStoop) {
                connect();
                startUpdatingCallbackWithPosition();
                isStoop = false;
            } else {
                mp.start();
            }
        }
    }

    public boolean isPlay() {
        return mp.isPlaying();
    }

    private void connect() {
        mp = new MediaPlayer();
        try {
            mp.setDataSource(URL);
            mp.prepareAsync();
            mp.setOnPreparedListener(this);
            showNotification();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void seekTo(int position){
        if (mp!= null){
            mp.seekTo(position);
        }
    }

    public void initializeProgressCallback() {
    final int duration = mp.getDuration();
        if (mListener != null) {
            mListener.onDurationChanged(duration);
            mListener.onPositionChanged(0);
        }
    }

    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    Log.d(Keys.LOG,"RUNNABLE");
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && mListener != null) {
                mListener.onPositionChanged(0);
            }
        }
    }

    private void updateProgressCallbackTask() {
        if (mp != null && mp.isPlaying()) {
            int currentPosition = mp.getCurrentPosition();
            if (mListener != null) {
                mListener.onPositionChanged(currentPosition);
            }
        }
    }

    public void setPlaybackInfoListener(PlayBackInfoListener listener) {
        mListener = listener;
        initializeProgressCallback();
        startUpdatingCallbackWithPosition();

    }

    public class AudioBinder extends Binder {
        AudioService getService() {
            return AudioService.this;
        }
    }
}
