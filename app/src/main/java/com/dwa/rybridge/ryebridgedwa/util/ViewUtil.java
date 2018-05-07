package com.dwa.rybridge.ryebridgedwa.util;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class ViewUtil {

    public static void loadImage(ImageView imageView, Uri uri) {
        if (uri != null) {
            Picasso.get().load(uri).fit().centerCrop().into(imageView);
        }
    }
}
