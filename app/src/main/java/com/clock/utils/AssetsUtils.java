package com.clock.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * USER: liulei
 * DATE: 2015/5/20
 * TIME: 8:17
 */
public class AssetsUtils {

    public static String readFromAsstes(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        ByteArrayOutputStream bos = null;
        InputStream inputStream = null;
        String result = null;
        try {
            inputStream = assetManager.open(fileName, AssetManager.ACCESS_STREAMING);
            bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int length;
            while ((length = inputStream.read(data)) != -1) {
                bos.write(data, 0, length);
            }
            result = bos.toString().replaceAll("\\s*", "");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ignored) {
            }
        }
        return result;
    }
}
