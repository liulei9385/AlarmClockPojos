package com.clock.sqlite;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.clock.helper.DbCurdHelper;
import com.clock.model.AlarmClockBean;
import com.clock.utils.Constant;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:00
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                          DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(Context context) {
        this(context, Constant.DB_NAME, null, Constant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        new DbCurdHelper(db).create(AlarmClockBean.class);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("DatabaseHelper.onUpgrade a db.");
    }
}
