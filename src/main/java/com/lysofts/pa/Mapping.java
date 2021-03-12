/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.pa;

import com.lysofts.annotations.Column;
import com.lysofts.annotations.Table;
import com.lysofts.annotations.Transient;
import com.lysofts.utils.ConnClass;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mspace-dev
 */
public class Mapping {
    
    public static boolean isTransient(Object object){
        Field field = (Field) object;
        return field.isAnnotationPresent(Transient.class);        
    }

    public static String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName();
        } else {
            return clazz.getAnnotation(Table.class).name();
        }
    }

    public static String getColName(Object object) {
        Field field = (Field) object;
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        } else {
            return field.getAnnotation(Column.class).name();
        }
    }

    public Param insertQuery(Object object) {
        try {
            String fieldString = "", valuesString = "";
            Field[] fields = object.getClass().getDeclaredFields();
            Map<Integer, String> params = new HashMap<>();
            int j = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if (!"id".equals(field.getName()) && !isTransient(field)) {
                    fieldString += getColName(field) + ",";
                    valuesString += "?,";
                    Object value = field.get(object);
                    params.put(j, value != null ? value.toString() : "");
                    j++;
                }
            }
            fieldString = fieldString.substring(0, fieldString.length() - 1);
            valuesString = valuesString.substring(0, valuesString.length() - 1);
            Mapping.Param param;
            param = new Mapping.Param(fieldString, valuesString, params);
            return param;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return null;
        }
    }

    public Param updateQuery(Object object) {
        try {
            String fieldString = "";
            Map<Integer, String> params = new HashMap<>();
            int j = 1;
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!"id".equals(field.getName()) && !isTransient(field)) {
                    field.setAccessible(true);
                    fieldString += " " + Mapping.getColName(field) + "=?,";
                    Object value = field.get(object);
                    params.put(j, value != null ? value.toString() : "");
                    j++;
                }
            }
            fieldString = fieldString.substring(0, fieldString.length() - 1);
            Mapping.Param param;
            param = new Mapping.Param(fieldString, params);
            return param;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return null;
        }
    }

    public class Param {

        private String fieldString, valuesString;
        Map<Integer, String> datMap;

        public Param(String fieldString, String valuesString, Map<Integer, String> datMap) {
            this.fieldString = fieldString;
            this.valuesString = valuesString;
            this.datMap = datMap;
        }

        public Param(String fieldString, Map<Integer, String> datMap) {
            this.fieldString = fieldString;
            this.datMap = datMap;
        }

        public String getFieldString() {
            return fieldString;
        }

        public void setFieldString(String fieldString) {
            this.fieldString = fieldString;
        }

        public String getValuesString() {
            return valuesString;
        }

        public void setValuesString(String valuesString) {
            this.valuesString = valuesString;
        }

        public Map<Integer, String> getDatMap() {
            return datMap;
        }

        public void setDatMap(Map<Integer, String> datMap) {
            this.datMap = datMap;
        }

    }
}
