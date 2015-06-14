package com.clock.sqlite;

import android.content.ContentValues;
import com.clock.anno.Table;
import com.clock.model.BaseTableBean;
import com.clock.model.DataTypes;

import java.lang.reflect.Field;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:49
 */
public class ContentValuesBuilder {

    public static String getTableName(Class<? extends BaseTableBean> clazz) {
        String tableName = "";
        if (clazz != null) {
            if (clazz.isAnnotationPresent(Table.class)) {
                Table table = clazz.getAnnotation(Table.class);
                return table.value();
            }
        }
        return tableName;
    }

    public static ContentValues getContentValues(BaseTableBean bean) {
        if (bean == null)
            return null;
        ContentValues values = new ContentValues();
        Class<? extends BaseTableBean> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(bean);
                DataTypes.putData(values, fieldName, fieldValue);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

}
