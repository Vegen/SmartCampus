package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.network.Url;

/**
 * Created by vegen on 2018/3/21.
 */

public class GlideUtils {
    public static void load(Context context, String url, ImageView imageView){
        Glide.with(context).load(Url.ROOT_URL + url).crossFade().into(imageView);
    }

    public static void load(Context context, int imgRes, ImageView imageView){
        Glide.with(context).load(imgRes).crossFade().into(imageView);
    }
}
