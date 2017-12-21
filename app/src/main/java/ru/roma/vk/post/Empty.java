package ru.roma.vk.post;

import android.view.View;
import android.widget.LinearLayout;

import ru.roma.vk.MainApplication;

/**
 * Created by Ilan on 22.11.2017.
 */

public class Empty extends Attachment {
    @Override
    public View draw() {
        return new LinearLayout(MainApplication.getInstans());
    }

    @Override
    public void doAction() {
    }
}
