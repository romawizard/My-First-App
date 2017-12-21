package ru.roma.vk.utilitys;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ru.roma.vk.R;

/**
 * Created by Ilan on 21.09.2017.
 */

public class DownloadFile {

  private static   DisplayImageOptions dioList = new DisplayImageOptions.Builder().
            showImageOnLoading(R.drawable.contact_nophoto).
            showImageOnFail(R.drawable.contact_nophoto).
            cacheInMemory(true).
            cacheOnDisk(true).
            considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).
            displayer(new RoundedBitmapDisplayer(5)).build();

  private static   DisplayImageOptions dioUser = new DisplayImageOptions.Builder().
            showImageOnLoading(R.drawable.contact_nophoto).
            showImageOnFail(R.drawable.contact_nophoto).
            cacheInMemory(true).
            cacheOnDisk(true).
            considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).
            build();


    private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public static void downloadInList(String URL, ImageView imageView) {


        ImageLoader.getInstance().displayImage(URL, imageView, dioList, animateFirstListener);

    }
    public static void downloadInUser(String URL, ImageView imageView){


        ImageLoader.getInstance().displayImage(URL, imageView, dioUser, animateFirstListener);


    }


    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
