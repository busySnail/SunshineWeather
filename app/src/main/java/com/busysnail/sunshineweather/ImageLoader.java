package com.busysnail.sunshineweather;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * author: malong on 2016/9/1
 * email: malong_ilp@163.com
 * address: Xidian University
 */

public class ImageLoader {
    public static void load(Context context, int image, ImageView view){
        Picasso.with(context).load(image).into(view);
    }
}
