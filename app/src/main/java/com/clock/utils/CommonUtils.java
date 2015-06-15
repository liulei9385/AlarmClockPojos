package com.clock.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.clock.app.MyApplication;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * SunHunk
 * 2014/12/26
 * 10:19
 */
public class CommonUtils {

    /**
     * 返回应用程序的版本名
     */
    public static String getVerisonName(Context context) {
        String versionname;// 版本号
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionname = pi.versionName;// 获取在AndroidManifest.xml中配置的版本号
        } catch (PackageManager.NameNotFoundException e) {
            versionname = "";
        }
        return versionname;
    }

    /**
     * 返回应用程序的版本号
     */
    public static int getVerisonCode(Context context) {
        int versioncode;// 版本号
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;// 获取在AndroidManifest.xml中配置的版本号
        } catch (PackageManager.NameNotFoundException e) {
            versioncode = 1;
        }
        return versioncode;
    }

    /**
     * 从URL中获取连接中的Key和Value
     */
    public static String getValueFormURL(String url, String key) {
        key += "=";
        if (url.contains(key)) {
            int aid = url.indexOf(key) + key.length();
            if (url.indexOf("&", aid) != -1) {
                return url.substring(aid, url.indexOf("&", aid));
            } else {
                return url.substring(aid, url.length());
            }
        } else {
            return null;
        }
    }

    /**
     * 返回手机IMEI（需要读取手机状态的权限）
     */
    public static String getDeviceIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回手机Serial（不需要权限）
     */
    @SuppressWarnings("unchecked")
    public static String getDeviceSerial() {
        String serial = "null";
        try {
            Class clazz = Class.forName("android.os.Build");
            Class paraTypes = Class.forName("java.lang.String");
            Method method = clazz.getDeclaredMethod("getString", paraTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            serial = (String) method.invoke(new Build(), "ro.serialno");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return serial;
    }

    /**
     * MD5
     *
     * @param s 加密文
     * @return 结果
     */
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "null";
    }

    /**
     * 判断当前是否使用的是 WIFI网络
     *
     * @param icontext 上下文
     * @return true or false
     */
    public static boolean isWifiActive(Context icontext) {
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 手机是否连接网络
     *
     * @param context 上下文
     * @return 判定結果
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 暗黙的インテントが有効か判定
     *
     * @param context コンテキスト
     * @param intent  暗黙的インテント
     * @return 判定結果
     */
    public static boolean checkImplicitIntent(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
        return apps.size() > 0;
    }

    /**
     * 将bitmap画到文件中来
     *
     * @param bitmap
     * @param file
     * @return
     */
    public static boolean putBitmapToFile(Bitmap bitmap, File file) {
        if (bitmap == null || file == null)
            return false;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null)
                try {
                    fos.flush();
                    fos.close();
                    if (!bitmap.isRecycled())
                        bitmap.recycle();
                } catch (IOException ignored) {
                }
        }
        return false;
    }

    public static boolean drawDrawableToFile(Drawable drawable, File file) {
        if (drawable == null) return false;
        Bitmap bitmap = drawableToBitmap(drawable);
        return bitmap != null && putBitmapToFile(bitmap, file);
    }

    /**
     * drawable 转成 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 从Assets里面拷贝到SD卡内
     *
     * @param dir      文件夹
     * @param fileName 文件名称
     * @return
     */
    public static void copyAssets(
            Context context,
            String dir,
            String fileName) {
        // String[] files;
        File mWorkingPath = new File(dir);
        if (!mWorkingPath.exists()) {
            if (!mWorkingPath.mkdirs()) {
            }
        }
        try {
            InputStream in = context.getAssets().open(fileName);
            System.err.println("");
            File outFile = new File(mWorkingPath, fileName);
            OutputStream out = new FileOutputStream(outFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        if (bmp == null || bmp.isRecycled())
            return null;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String readJsonFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return null;
        try {
            String fileEncoding = resolveCode(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), fileEncoding);
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            br.close();
            return sb.toString();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static String resolveCode(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        byte[] head = new byte[3];
        inputStream.read(head);

        String code = "gb2312";  //或GBK

        if (head[0] == -1 && head[1] == -2)
            code = "UTF-16";
        else if (head[0] == -2 && head[1] == -1)
            code = "Unicode";
        else if (head[0] == -17 && head[1] == -69 && head[2] == -65)
            code = "UTF-8";

        inputStream.close();
        return code;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkListIsNotEmpty(Collection<?> collection) {
        return (collection != null && collection.size() > 0);
    }

    public static boolean checkMapIsNotEmpty(Map collection) {
        return (collection != null && collection.size() > 0);
    }


    public static <T> String parseArrayToString(T[] imgsIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (T t : imgsIds) {
            stringBuilder.append(t).append(",");
        }
        int index = stringBuilder.lastIndexOf(",");
        if (index == (stringBuilder.toString().length() - 1)) {
            stringBuilder.deleteCharAt(index);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取 当前项目的meta-data  value的值
     *
     * @param context
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getMetaDataValue(Context context, String name, String defaultValue) {
        Object value = null;
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName()
                    , PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return value != null ? value.toString() : defaultValue;
    }
}