package com.clock.app;

import android.app.Application;

/**
 * USER: liulei
 * DATE: 2015/6/15
 * TIME: 23:02
 */
public class MyApplication extends Application {

    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
