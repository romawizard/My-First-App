package ru.roma.vk;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Ilan on 18.10.2017.
 */

public class Pagination {

    private static Pagination pagination = new Pagination();
    private final  static  int MAXLIMIT = 20;
    private int offset = 0;
    private int firstload = 0;
    private ArrayList<Dialogs> cash;


    private Pagination() {
        cash = new ArrayList<>();
    }

    public static Pagination getInstans() {

        return pagination;
    }

    public synchronized ArrayList<Dialogs> next() {

        ArrayList<Dialogs> dialog = ApiVK.getInstance().getAllDialogs(offset);

        if (firstload == 0) {
            cash.addAll(dialog);
            firstload = 1;
            offset = (cash.size() - 1);
            return  cash;
        }

        int n = 0;
        for (Dialogs d : dialog) {
            if (cash.contains(d)) {
                n++;
            }
        }
        if (n >0 && n< MAXLIMIT) {
            Iterator<Dialogs> iterator = dialog.iterator();
            while (iterator.hasNext()) {
                Dialogs d = iterator.next();
                if (cash.contains(d)) {
                    Log.d("my log", "repeat: " + d.toString());
                    iterator.remove();
                }
            }
            cash.addAll(dialog);
        } else {
            offset = 0;
            ArrayList<Dialogs> dialogNew = ApiVK.getInstance().getAllDialogs(offset);
            cash = dialogNew;
        }


        if (offset < Dialogs.getCount())
            offset = (cash.size() - 1);

        return cash;
    }

    public boolean hasCash() {
        if (cash == null){
            return  false;
        }
        if (cash.size() > 0) {
            return true;
        } else
            return false;
    }

    public ArrayList<Dialogs> getCash() {

        return cash;
    }

    public void deletCash(){
        offset = 0;
        firstload = 0;
        cash = new ArrayList<Dialogs>();
    }
}
