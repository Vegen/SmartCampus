package com.itculturalfestival.smartcampus.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: Glide图片加载器
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .crossFade()
                .into(imageView);
    }
}
