package ru.roma.vk.post;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import ru.roma.vk.Conected;

/**
 * Created by Ilan on 22.11.2017.
 */

public class Empty extends Attachment {
    @Override
    public View draw() {
        return new LinearLayout(Conected.getInstans());
    }

    @Override
    public String showContent() {
        return null;
    }
}
