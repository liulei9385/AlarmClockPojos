package com.clock.helper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.clock.anno.Field;
import com.clock.model.BaseTableBean;
import com.clock.model.DataTypes;
import com.clock.sqlite.ContentValuesBuilder;

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
        String table = ContentValuesBuilder.getTableName(bean);
        StringBuilder executeSql = new StringBuilder();
        executeSql.append("ceate table ")
                .append(table)
                .append("(");
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        System.out.println(fields.length);
        int count = 0;
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Field.class)) {
                Field ann = field.getAnnotation(Field.class);
                String annovV = ann.name();
                executeSql.append(annovV)
                        .append(" ")
                        .append(ann.type())
                        .append(count + 1 == fields.length ? "" : ",");
            } else {
                executeSql.append(field.getName())
                        .append(DataTypes.getTypeString(field.getType()))
                        .append(count + 1 == fields.length ? "" : ",");
            }
            field.setAccessible(false);
            count++;
        }
        executeSql.append(")");
        System.out.println("DbCurdHelper.create" + executeSql.toString());
        //dataBase.execSQL(executeSql.toString());
    }

    public long insert(BaseTableBean bean) {
        String table = ContentValuesBuilder.getTableName(bean);
        ContentValues contentValues = ContentValuesBuilder.getContentValues(bean);
        return dataBase.insert(table, null, contentValues);
    }
}
