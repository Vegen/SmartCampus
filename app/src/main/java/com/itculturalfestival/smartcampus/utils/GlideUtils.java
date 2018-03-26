package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.network.Url;

/**
 * Created by vegen on 2018/3/21.
 */

public class GlideUtils {
    public static void load(Context context, boolean addRoot, String url, ImageView imageView){
        Glide.with(context).load((addRoot ? Url.ROOT_URL : "") + url).placeholder(R.drawable.anim_loading_view).error(R.drawable.app_empty_content).crossFade().into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView){
        Glide.with(context).load((false ? Url.ROOT_URL : "") + url).placeholder(R.drawable.anim_loading_view).error(R.drawable.app_empty_content).crossFade().into(imageView);
    }

    public static void load(Context context, int imgRes, ImageView imageView){
        Glide.with(context).load(imgRes).crossFade().placeholder(R.drawable.anim_loading_view).error(R.drawable.app_empty_content).into(imageView);
    }
}
