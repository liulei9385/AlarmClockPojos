package com.clock.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * USER: liulei
 * DATE: 2015/4/16.
 * TIME: 9:21
 */
public class DrawableUtils {

    private DrawableUtils() {
    }

    public static InsetDrawable createInsertDrawable(Drawable drawable, Rect rect) {
        return new InsetDrawable(drawable, rect.left, rect.top, rect.right, rect.bottom);
    }

    public static StateListDrawable createStateListDrawable(Context context
            , int seletorDrawableId, int normalDrawableId) {
        Drawable selectedDrawable = getDrawable(context, seletorDrawableId);
        Drawable norDrawable = getDrawable(context, normalDrawableId);
        return createStateListDrawable(selectedDrawable, norDrawable);
    }

    public static StateListDrawable createStateListDrawable(Drawable selectDrawable,
                                                            Drawable norDrawable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
        drawable.addState(new int[]{android.R.attr.state_pressed}, selectDrawable);
        drawable.addState(new int[]{}, norDrawable);
        return drawable;
    }

    public static GradientDrawable createGradientDrawable(Context context,
                                                          @ColorRes int solidColor, int radiusDip) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(ViewUtils.dpToPx(context, radiusDip));
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        //solid color
        gradientDrawable.setColor(context.getResources().getColor(solidColor));
        return gradientDrawable;
    }

    public static Drawable getAttrDrawable(Context context, @AttrRes int attResId) {
        final Resources.Theme theme = context.getTheme();
        TypedArray typedArray = theme.obtainStyledAttributes(new int[]{attResId});
        return typedArray.getDrawable(0);
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        Drawable drawable;
        final Resources resources = context.getResources();
        final Resources.Theme theme = context.getTheme();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            drawable = resources.getDrawable(resId, theme);
        else
            drawable = resources.getDrawable(resId);
        return drawable;
    }

    /**
     * 将drawable 转成 byte[]
     *
     * @param context
     * @param drawRes
     * @return
     */
    public static byte[] drawableToBytes(Context context, @DrawableRes int drawRes) {
        Drawable drawable = DrawableUtils.getDrawable(context, drawRes);
        if (drawable == null)
            return null;
        Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable);
        return CommonUtils.bmpToByteArray(bitmap, true);
    }

    public static byte[] drawableToBytes(Drawable drawable, boolean needRecycle) {
        if (drawable == null)
            return null;
        Bitmap bitmap = BitmapUtils.drawableToBitmap(drawable);
        return CommonUtils.bmpToByteArray(bitmap, needRecycle);
    }

    public static boolean drawableToFile(Drawable drawable, File destFile) {
        boolean flag = false;
        try {
            byte[] data = drawableToBytes(drawable, false);
            if (data != null) {
                FileOutputStream fos = new FileOutputStream(destFile, false);
                fos.write(data, 0, data.length);
                flag = true;
                fos.flush();
                fos.close();
            } else flag = false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return flag;
    }

    @SuppressWarnings("deprecation")
    public static void setBackGround(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16)
            view.setBackground(drawable);
        else view.setBackgroundDrawable(drawable);
    }
}
