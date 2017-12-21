package ru.roma.vk.post;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Ilan on 03.12.2017.
 */

 interface PlayBackInfoListener {
//
//    @IntDef({State.INVALID, State.PLAYING, State.PAUSED, State.RESET, State.COMPLETED})
//    @Retention(RetentionPolicy.SOURCE)
//    @interface State {
//
//        int INVALID = -1;
//        int PLAYING = 0;
//        int PAUSED = 1;
//        int RESET = 2;
//        int COMPLETED = 3;
//    }
//
//    public static String convertStateToString(@State int state) {
//        String stateString;
//        switch (state) {
//            case State.COMPLETED:
//                stateString = "COMPLETED";
//                break;
//            case State.INVALID:
//                stateString = "INVALID";
//                break;
//            case State.PAUSED:
//                stateString = "PAUSED";
//                break;
//            case State.PLAYING:
//                stateString = "PLAYING";
//                break;
//            case State.RESET:
//                stateString = "RESET";
//                break;
//            default:
//                stateString = "N/A";
//        }
//        return stateString;
//    }
//
//    abstract void onLogUpdated(String formattedMessage);

     void onDurationChanged(int duration);

     void onPositionChanged(int position);

    void onDisconect();


//    abstract void onStateChanged(@State int state);
//
//    abstract void onPlaybackCompleted();
}
