package com.clock.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * USER: liulei
 * DATE: 2015/4/8
 * TIME: 21:03
 */
public class BitmapUtils {

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        int sampleSize = 1;
        int height = options.outHeight;
        int width = options.outWidth;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio;
            final int widthRatio;
            if (reqHeight == 0) {
                sampleSize = (int) Math.floor((float) width / (float) reqWidth);
            } else if (reqWidth == 0) {
                sampleSize = (int) Math.floor((float) height / (float) reqHeight);
            } else {
                heightRatio = (int) Math.floor((float) height / (float) reqHeight);
                widthRatio = (int) Math.floor((float) width / (float) reqWidth);
                sampleSize = Math.max(heightRatio, widthRatio);
            }
        }
        return sampleSize;
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    public static Bitmap createScaleBitmap(Bitmap src, int dstWidth,
                                           int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true);
        if (src != dst && !src.isRecycled()) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }

    public static Bitmap decodeSampledBitmapAndScale(Context context, String fileName) {
        int[] screenSize = ViewUtils.getScreenSize(context);
        Bitmap bitmap = decodeSampledBitmapFromFd(fileName, screenSize[0], screenSize[1]);
        return createSacleBitmap(context, bitmap);
    }

    public static Bitmap rotateBitmap(Bitmap source, int angle) {
        Matrix matrix = new Matrix();
        if (angle > 0)
            matrix.setRotate(angle);
        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
        if (bitmap != source && !source.isRecycled())
            source.recycle();
        return bitmap;
    }

    public static Bitmap createSacleBitmap(Context context, Bitmap src) {
        final int bWidth = src.getWidth();
        final int bHeight = src.getHeight();
        final int[] screenSize = ViewUtils.getScreenSize(context);
        Matrix matrix = new Matrix();
        final int maxScreenWH = Math.max(screenSize[0], screenSize[1]);
        float scaleX;
        if (bWidth > bHeight) {
            scaleX = (float) bWidth / maxScreenWH;
        } else {
            scaleX = (float) bHeight / maxScreenWH;
        }
        if (bWidth > screenSize[0]) {
            scaleX = (float) screenSize[0] / bWidth;
        }
        Bitmap sourceBitmap;
        if (scaleX > 0) {
            matrix.setScale(scaleX, scaleX);
            sourceBitmap = Bitmap.createBitmap(src, 0, 0, bWidth, bHeight, matrix, true);
            if (sourceBitmap != src && !src.isRecycled()) src.recycle();
        } else sourceBitmap = src;
        return sourceBitmap;
    }

    public static Bitmap createCroppedScaleBitmap(Bitmap src, int reqWidth, int reqHeight) {
        final int bWidth = src.getWidth();
        final int bHeight = src.getHeight();
        Matrix matrix = new Matrix();
        int maxSize = Math.max(reqHeight, reqWidth);
        float scaleX;
        if (bWidth * bHeight < reqWidth * reqHeight)
            scaleX = 0;
        else {
            if (bWidth > bHeight) {
                scaleX = (float) maxSize / bWidth;
            } else scaleX = (float) maxSize / bHeight;
        }
        Bitmap sourceBitmap;
        if (scaleX > 0 && scaleX != 1) {
            matrix.setScale(scaleX, scaleX);
            sourceBitmap = Bitmap.createBitmap(src, 0, 0, bWidth, bHeight, matrix, true);
            if (sourceBitmap != src && !src.isRecycled()) src.recycle();
        } else sourceBitmap = src;
        return sourceBitmap;
    }


    // 从Resources中加载图片
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight); // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    // 从sd卡上加载图片
    public static Bitmap decodeSampledBitmapFromFd(String pathName,
                                                   int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        int degree = readPictureDegree(pathName);
        return rotateBitmap(src, degree);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();
        else {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.draw(canvas);
            return bitmap;
        }
    }

    public static String getMimeTypeOfFile(String pathName) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opt);
        String mineType = opt.outMimeType;
        if (mineType == null)
            mineType = MineType.IMAGE_JPEG;
        return mineType;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * imgage minetype helper
     */
    public interface MineType {
        String IMAGE_PNG = "image/png";
        String IMAGE_JPEG = "image/jpeg";
    }
}
