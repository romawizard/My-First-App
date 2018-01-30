package ru.roma.vk.utilitys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ilan on 18.10.2017.
 */

public class Pagination<T> {

    private static int MAX_LIMIT;
    private Paginable p;
    private int offset = 0;
    private int firstload = 0;
    private List<T> cash;


    public Pagination(int maxOffset, Paginable p) {
        cash = new ArrayList<T>();
        MAX_LIMIT = maxOffset;
        this.p = p;
    }


    public  List<T> next() {

        List<T> data = p.getData(offset);
        if (firstload == 0) {
            cash.addAll(data);
            firstload = 1;
            offset = (cash.size() - 1);
            return cash;
        }

        int n = 0;
        for (T t : data) {
            if (cash.contains(t)) {
                n++;
            }
        }
        if (n > 0 && n < MAX_LIMIT) {
            Iterator<T> iterator = data.iterator();
            while (iterator.hasNext()) {
                T t = iterator.next();
                if (cash.contains(t)) {
                    iterator.remove();
                }
            }
            cash.addAll(data);
        } else {
            offset = 0;
            List<T> DataNew = p.getData(offset);
            cash.clear();
            cash.addAll(DataNew);
        }


        if (offset < p.getCount())
            offset = (cash.size() - 1);

        return cash;
    }

    public boolean hasCash() {
        if (cash == null) {
            return false;
        }
        if (cash.size() > 0) {
            return true;
        } else
            return false;
    }

    public List<T> getCash() {

        return cash;
    }

    public void deletCash() {
        offset = 0;
        firstload = 0;
        cash.clear();
    }

    public List<T> reload() {

                offset = 0;
                firstload = 0;
                cash.clear();
                next();

        return cash;
    }
}
