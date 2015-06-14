package com.clock.model;

import android.content.ContentValues;

import java.util.Date;

/**
 * USER: liulei
 * DATE: 2015/6/14
 * TIME: 17:40
 */
public enum DataTypes {

    INT("int"), LONG("long"), STRING("string"), DATE("datetime"), BYTE("byte"),
    DOUBLE("double"), FLOAT("float"), BYTES("byte[]");

    private String str;

    DataTypes(String str) {
        this.str = str;
    }

    public String getTypeName() {
        return str;
    }

    @SuppressWarnings("unchecked")
    public static DataTypes getType(Class<?> clazz) {
        String objName = clazz.getSimpleName().toUpperCase();
        return DataTypes.valueOf(objName);
    }

    public static String getTypeString(Class<?> clazz) {
        DataTypes dataTypes = getType(clazz);
        String formatString = "";
        if (dataTypes != null) {
            switch (dataTypes) {
                case INT:
                    formatString += "INTEGER";
                    break;
                case DOUBLE:
                    formatString += "double";
                    break;
                case FLOAT:
                    formatString += "float";
                    break;
                case LONG:
                    formatString += "long";
                    break;
                case BYTE:
                    formatString += "byte";
                    break;
                case BYTES:
                    formatString += "byte[]";
                    break;
                case STRING:
                    formatString += "varchar(20)";
                    break;
                case DATE:
                    formatString += "date";
                    break;
            }
        }
        return formatString;
    }

    public static void putData(ContentValues values, String key, Object obj) {
        DataTypes types = getType(obj.getClass());
        switch (types) {
            case INT:
                values.put(key, (Integer) obj);
                break;
            case DOUBLE:
                values.put(key, (Double) obj);
                break;
            case FLOAT:
                values.put(key, (Float) obj);
                break;
            case LONG:
                values.put(key, (Long) obj);
                break;
            case BYTE:
                values.put(key, (Byte) obj);
                break;
            case BYTES:
                values.put(key, (byte[]) obj);
                break;
            case STRING:
                values.put(key, (String) obj);
                break;
            case DATE:
                values.put(key, ((Date) obj).getTime());
                break;
        }
    }

}
