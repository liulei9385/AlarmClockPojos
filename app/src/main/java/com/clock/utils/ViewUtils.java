package com.clock.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


/**
 * USER: liulei
 * DATA: 2015/1/31
 * TIME: 10:31
 */
public class ViewUtils {

    private ViewUtils() {
    }

    /**
     * 转化dip为px
     *
     * @param context
     * @param value
     * @return
     */
    public static float dpToPx(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getScreenMetric(context));
    }

    /**
     * 获取屏幕相关的参数
     *
     * @param context
     * @return
     */
    private static DisplayMetrics getScreenMetric(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 显示toast消息
     *
     * @param context context
     * @param text    text
     * @param isLong  islong
     */
    public static void showToast(final Context context, final CharSequence text, boolean isLong) {
        if (context == null)
            return;
        int duration = Toast.LENGTH_LONG;
        if (!isLong)
            duration = Toast.LENGTH_SHORT;

        if (isRuninMain())
            Toast.makeText(context, text, duration).show();
        else {
            final int showDuration = duration;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @SuppressWarnings("ResourceType")
                @Override
                public void run() {
                    Toast.makeText(context, text, showDuration).show();
                }
            });
        }
    }

    public static void showToast(final Context context,
                                 int stirngResId, boolean isLong) {
        if (stirngResId > 0)
            showToast(context, context.getString(stirngResId), isLong);
    }

    /**
     * 获取颜色
     *
     * @param context
     * @param colorResId
     * @return
     */
    public static int getResColor(Context context, int colorResId) {
        return context.getResources().getColor(colorResId);
    }


    /**
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics metrics = getScreenMetric(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 是否运行在主线程
     *
     * @return
     */
    public static boolean isRuninMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    /**
     * 关闭输入法
     *
     * @param context 上下文
     * @param View    View.getWindowToken()
     */
    public static void hideSofoInputMethod(Context context, View requestView) {
        if (requestView == null)
            return;
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager.isActive()) {
                inputMethodManager.hideSoftInputFromWindow(requestView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception ignored) {
        }
    }


}
