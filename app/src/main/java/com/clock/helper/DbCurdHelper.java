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

    public void create(Class<? extends BaseTableBean> clazz) {
        String table = ContentValuesBuilder.getTableName(clazz);
        StringBuilder executeSql = new StringBuilder();

        executeSql.append("drop table ")
                .append(table)
                .append("if exists;");

        dataBase.execSQL(executeSql.toString());

        executeSql.delete(0, executeSql.length());

        executeSql.append("ceate table ")
                .append(table)
                .append("(");

        java.lang.reflect.Field[] currFields = getAllFields(clazz);

        int count = 0;
        for (java.lang.reflect.Field field : currFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Field.class)) {
                Field ann = field.getAnnotation(Field.class);
                String annovV = ann.name();
                executeSql.append(annovV)
                        .append(" ")
                        .append(ann.type())
                        .append(count + 1 == currFields.length ? "" : ",");
            } else {
                executeSql.append(field.getName())
                        .append(" ")
                        .append(DataTypes.getTypeString(field.getType()))
                        .append(count + 1 == currFields.length ? "" : ",");
            }
            field.setAccessible(false);
            count++;
        }
        executeSql.append(");");
        System.out.println("DbCurdHelper.create" + executeSql.toString());
        dataBase.execSQL(executeSql.toString());
        //dataBase.execSQL(executeSql.toString());
    }

    private java.lang.reflect.Field[] getAllFields(Class<? extends BaseTableBean> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        java.lang.reflect.Field[] superFields = superClazz.getDeclaredFields();
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        java.lang.reflect.Field[] currFields = new java.lang.reflect
                .Field[superFields.length + fields.length];
        System.arraycopy(superFields, 0, currFields, 0, superFields.length);
        System.arraycopy(fields, 0, currFields, superFields.length, fields.length);

        return currFields;
    }

    public long insert(BaseTableBean bean) {
        String table = ContentValuesBuilder.getTableName(bean.getClass());
        ContentValues contentValues = ContentValuesBuilder.getContentValues(bean);
        return dataBase.insert(table, null, contentValues);
    }
}
