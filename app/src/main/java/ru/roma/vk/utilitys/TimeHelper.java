package ru.roma.vk.utilitys;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilan on 30.09.2017.
 */

public class TimeHelper {

    private static final String JUNUARY = "янв";
    private static final String FEBRUARY = "фев";
    private static final String MARCH = "мар";
    private static final String APRIL = "апр";
    private static final String MAY = "мая";
    private static final String JUNE = "июн";
    private static final String JULY = "июл";
    private static final String AUGUST = "авг";
    private static final String SEPTEMBER = "сен";
    private static final String OKTOBER = "окт";
    private static final String NOVEMBER = "ноя";
    private static final String DESEMBER = "дек";


    public String getTime(long time, int sex) {

        String lastSeen = "";
        String showDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Long timeNow = (new Date().getTime()) ;
        String [] todayTime = sdf.format(timeNow).split("-");
        int simpleDayNow = Integer.parseInt(todayTime[0]);

        Date date = new Date(time * 1000L);
        String thisTime = timeFormat.format(date);
        String[] formattedDate = sdf.format(date).split("-");
        int simpleDaySeen = Integer.parseInt(formattedDate[0]);
        long fullThisTime = time*1000L;

        String mounth = getMounth(Integer.valueOf(formattedDate[1]));

        if ((simpleDayNow - simpleDaySeen) == 0 && ((timeNow - fullThisTime) < 86400000)) {
            showDate = "сегодня";
        } else {
            if ((simpleDayNow - simpleDaySeen) == 1 && ((timeNow - fullThisTime) < 172800000)) {
                showDate = "вчера";
            } else {
                showDate = formattedDate[0] + " " + mounth;
                if (Integer.parseInt(todayTime[2]) != Integer.parseInt(formattedDate[2])){
                    showDate += " " +  formattedDate[2] ;
                }
            }
        }
        Log.d("time", String.valueOf(time) + " today " + timeNow + " - " +todayTime[0]+   " " + todayTime[1] + " " + todayTime[2]);

        if (sex == 1) {
            lastSeen = "былa в сети " + showDate + " в " + thisTime;
        } else {
            lastSeen = "был в сети " + showDate +  " в " + thisTime;
        }
        return lastSeen;
    }

    public String getTime(long time){

        String lastSeen = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        Long timeNow = (new Date().getTime()) ;
        String [] todayTime = sdf.format(timeNow).split("-");
        int simpleDayNow = Integer.parseInt(todayTime[0]);

        Date date = new Date(time * 1000L);
        String thisTime = timeFormat.format(date);
        String[] formattedDate = sdf.format(date).split("-");
        int simpleDaySeen = Integer.parseInt(formattedDate[0]);
        long fullThisTime = time*1000L;

//        Log.d("time ", "time user: " + fullThisTime + "now " + timeNow);

        String mounth = getMounth(Integer.valueOf(formattedDate[1]));
        if ((simpleDayNow - simpleDaySeen == 0 && ((timeNow - fullThisTime) < 86400000) )) {
            lastSeen = thisTime;
        } else {
            if ((simpleDayNow - simpleDaySeen) == 1 && ((timeNow - fullThisTime) < 172800000)) {
                lastSeen = "вчера";
            } else {
                lastSeen = formattedDate[0] + " " + mounth;
                if (Integer.parseInt(todayTime[2]) != Integer.parseInt(formattedDate[2])){
                    lastSeen +=" " +  formattedDate[2] ;
                }
            }
        }
        return  lastSeen;
    }

    public static String getFormat(int time){
        int shortTime = time/1000;
        return String.format("%02d:%02d", shortTime / 60 , shortTime % 60);
    }

    private String getMounth(int moun) {
        String mounth = "";
        switch (moun) {
            case 01:
                mounth = JUNUARY;
                break;
            case 02:
                mounth = FEBRUARY;
                break;
            case 03:
                mounth = MARCH;
                break;
            case 04:
                mounth = APRIL;
                break;
            case 05:
                mounth = MAY;
                break;
            case 06:
                mounth = JUNE;
                break;
            case 07:
                mounth = JULY;
                break;
            case 8:
                mounth = AUGUST;
                break;
            case 9:
                mounth = SEPTEMBER;
                break;
            case 10:
                mounth = OKTOBER;
                break;
            case 11:
                mounth = NOVEMBER;
                break;
            case 12:
                mounth = DESEMBER;
                break;
            default:
                mounth = "месяц";
                break;
        }
        return mounth;
    }
}