package ru.roma.vk.post;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.roma.vk.R;
import ru.roma.vk.TimeHelper;


public class AudioPlayer extends AppCompatActivity {

    private AudioService service;
    private boolean isConnected = false;
    private boolean mUserIsSeeking = false;
    private Handler handler;

    @BindView(R.id.player_progress_bar)
    SeekBar playerSeekBar;
    @BindView(R.id.time_fulfilled)
    TextView timeFulfilled;
    @BindView(R.id.time_left)
    TextView timeLeft;
    @BindView(R.id.line_button)
    LinearLayout lineButton;
    @BindView(R.id.player_titel)
    TextView playerTitel;
    @BindView(R.id.player_artist)
    TextView playerArtist;
    @BindView(R.id.button_rew)
    Button buttonRew;
    @BindView(R.id.button_stop)
    Button buttonStop;
    @BindView(R.id.button_play)
    Button buttonPlay;
    @BindView(R.id.button_fwd)
    Button buttonFwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        ButterKnife.bind(this);
        initializeSeekBar();
        initializeHandler();
    }

    private void initializeSeekBar() {
        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int userSelectedPosition = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    userSelectedPosition =i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = false;
                service.seekTo(userSelectedPosition);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AudioService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isConnected) {
            unbindService(connection);
            isConnected = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (service != null){
            service.stopUpdatingCallbackWithPosition(true);
        }
    }

    @OnClick(R.id.button_stop)
    public void onClickStop() {
        service.stop();
        buttonStop.setSelected(true);
        buttonPlay.setSelected(false);
    }

    @OnClick(R.id.button_play)
    public void onClickPlay() {
        service.pausePlay();
        buttonStop.setSelected(false);
        if (buttonPlay.isSelected()) {
            buttonPlay.setSelected(false);
        } else {
            buttonPlay.setSelected(true);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AudioService.AudioBinder binder = (AudioService.AudioBinder) iBinder;
            service = binder.getService();
            service.setPlaybackInfoListener(new PlaybackListener());
            isConnected = true;
            buttonPlay.setSelected(true);
            playerArtist.setText(service.getArtist());
            playerTitel.setText(service.getTitle());

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnected = false;
        }
    };

    public void refreshTime(int duration, int position){
        timeLeft.setText("-" + TimeHelper.getFormat(duration));
        timeFulfilled.setText(TimeHelper.getFormat(position));
    }

    public class PlaybackListener extends PlayBackInfoListener {

        private int duration;

        @Override
        void onLogUpdated(String formattedMessage) {

        }

        @Override
        void onDurationChanged(final int duration) {
            this.duration = duration;
            Log.d("PLAYER", "onDurationChanged = "+ this.duration);
            playerSeekBar.setMax(duration);
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
        void onPositionChanged(final int position) {
            if (!mUserIsSeeking) {
                playerSeekBar.setProgress(position);
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
        void onStateChanged(@State int state) {

        }

        @Override
        void onPlaybackCompleted() {

        }
    }

    private void initializeHandler(){
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                refreshTime(message.arg1,message.arg2);
                return false;
            }
        });
    }
}
