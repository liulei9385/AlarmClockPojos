package com.clock.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.clock.anno.Field;
import com.clock.anno.Id;
import com.clock.model.BaseTableBean;
import com.clock.model.DataTypes;
import com.clock.sqlite.ContentValuesBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 19:13
 */
public class DbCurdHelper {

    public SQLiteDatabase dataBase;

    public static final int INTERGE = 1;
    public static final int FLOAT = 2;
    public static final int DOUBLE = 3;
    public static final int STRING = 4;

    public DbCurdHelper(SQLiteDatabase dataBase) {
        this.dataBase = dataBase;
    }

    private static int getType(java.lang.reflect.Field field) {
        DataTypes types = DataTypes.getType(field.getType());
        int type;
        switch (types) {
            case INT:
                type = INTERGE;
                break;

            case FLOAT:
                type = FLOAT;
                break;

            case DOUBLE:
                type = DOUBLE;
                break;

            case STRING:
                type = STRING;
                break;

            default:
                type = INTERGE;
                break;
        }
        return type;
    }

    public void create(Class<? extends BaseTableBean> clazz) {
        String table = ContentValuesBuilder.getTableName(clazz);
        StringBuilder executeSql = new StringBuilder();

        executeSql.append("drop table if exists ")
                .append(table).append(";");

        dataBase.execSQL(executeSql.toString());

        executeSql.delete(0, executeSql.length());

        executeSql.append("create table ")
                .append(table)
                .append("(");

        java.lang.reflect.Field[] currFields = getAllFields(clazz);

        int count = 0;
        for (java.lang.reflect.Field field : currFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                Id ann = field.getAnnotation(Id.class);
                executeSql.append(ann.name())
                        .append(" ")
                        .append(DataTypes.getTypeString(field.getType()))
                        .append(" primary key autoincrement")
                        .append(count + 1 == currFields.length ? "" : ",");
            } else if (field.isAnnotationPresent(Field.class)) {
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
        System.out.println("DbCurdHelper.create \t" + executeSql.toString());
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

    public void dropTable(Class<? extends BaseTableBean> clazz) {
        String sql = "drop table if exists " + ContentValuesBuilder.getTableName(clazz);
        this.dataBase.execSQL(sql);
    }

    public void isTableExist(Class<? extends BaseTableBean> clazz) {
        String sql = "if exists " + ContentValuesBuilder.getTableName(clazz);
        this.dataBase.execSQL(sql);
    }

    public <T extends BaseTableBean> List<T> queryAll(Class<T> clazz) {
        List<T> list = new ArrayList<T>(5);
        String table = ContentValuesBuilder.getTableName(clazz);
        String[] columns = new String[]{};
        String orderBy = "";
        Cursor cursor = this.dataBase.query(table, columns, null,
                null, null, null, orderBy, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                try {
                    BaseTableBean bean = clazz.newInstance();
                    java.lang.reflect.Field[] fields = getAllFields(clazz);
                    for (java.lang.reflect.Field field : fields) {
                        field.setAccessible(true);
                        String culomnName = field.getName();
                        if (field.isAnnotationPresent(Field.class)) {
                            culomnName = field.getAnnotation(Field.class).name();
                        } else if (field.isAnnotationPresent(Id.class)) {
                            culomnName = field.getAnnotation(Id.class).name();
                        }
                        int index = cursor.getColumnIndex(culomnName);
                        if (index != -1) {
                            int type = getType(field);
                            Object obj = null;
                            switch (type) {
                                case INTERGE:
                                    obj = cursor.getInt(index);
                                    break;

                                case FLOAT:
                                    obj = cursor.getFloat(index);
                                    break;

                                case DOUBLE:
                                    obj = cursor.getDouble(index);
                                    break;

                                case STRING:
                                    obj = cursor.getString(index);
                                    break;
                            }
                            field.set(bean, obj);
                        }
                        list.add((T) bean);
                        field.setAccessible(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cursor.close();
        return list;
    }

}
