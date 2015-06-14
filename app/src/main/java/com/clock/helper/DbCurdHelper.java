package com.clock.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.clock.anno.Name;
import com.clock.model.BaseTableBean;
import com.clock.sqlite.ContentValuesBuilder;

import java.lang.reflect.Field;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 19:13
 */
public class DbCurdHelper {

    public SQLiteDatabase dataBase;

    public DbCurdHelper(SQLiteDatabase dataBase) {
        this.dataBase = dataBase;
    }

    public void create(BaseTableBean bean) {
        final Class<? extends BaseTableBean> clazz = bean.getClass();
        String table = ContentValuesBuilder.getTableName(clazz);
        StringBuilder executeSql = new StringBuilder();
        executeSql.append("ceate table ")
                .append(table)
                .append("(");
        Field[] fields = clazz.getDeclaredFields();
        int count = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Name.class)) {
                String annovV = field.getAnnotation(Name.class).value();
                executeSql.append(annovV)
                        .append(count + 1 == fields.length ? "" : ",");
            } else {
                executeSql.append(field.getName())
                        .append(count + 1 == fields.length ? "" : ",");
            }
            count++;
        }
        executeSql.append(")");
    }

    public long insert(BaseTableBean bean) {
        final Class<? extends BaseTableBean> clazz = bean.getClass();
        String table = ContentValuesBuilder.getTableName(clazz);
        ContentValues contentValues = ContentValuesBuilder.getContentValues(clazz);
        return dataBase.insert(table, null, contentValues);
    }
}
