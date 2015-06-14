package com.clock.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

/**
 * USER: liulei
 * DATE: 2015/5/17
 * TIME: 16:30
 */
public class ImageUtils {

    /**
     * @param context
     * @param httpUrl
     * @param imageView
     */
    public static void loadImageFromHttp(Context context, String httpUrl, ImageView imageView,
                                         Transformation transformation) {
        if (imageView == null || httpUrl == null) return;
        RequestCreator requestCreator = Picasso.with(context)
                .load(httpUrl)
                .tag(httpUrl)
                .fit()
                .centerInside()
                .config(Bitmap.Config.RGB_565);

        if (transformation != null)
            requestCreator.transform(transformation);
        requestCreator.into(imageView);
    }

    public static void loadImageFormUri(Context context, Uri uri, ImageView imageView,
                                        Transformation transformation, boolean skipMemoryCache) {
        if (imageView == null) return;
        RequestCreator requestCreator = Picasso.with(context)
                .load(uri)
                .tag(uri)
                .fit()
                .centerInside()
                .config(Bitmap.Config.RGB_565);
        if (transformation != null)
            requestCreator.transform(transformation);
        if (skipMemoryCache)
            requestCreator.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
        requestCreator.into(imageView);
    }
}
