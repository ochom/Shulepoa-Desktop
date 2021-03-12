/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.pa;

import com.lysofts.utils.ConnClass;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mspace-dev
 */
public class QueryRunner {

    public static List run(String SQL, Map<Integer, String> params, Class<?> clazz) {
        Connection CONNECTION = ConnClass.getDB();
        List list = new ArrayList<>();
        try {
            PreparedStatement pst = CONNECTION.prepareStatement(SQL);
            if (params != null) {
                for (Map.Entry<Integer, String> entry : params.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    pst.setString(key, value);
                }
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Object dto = clazz.newInstance();
                for (Field field : clazz.getDeclaredFields()) {
                    if (!Mapping.isTransient(field)) {
                        field.setAccessible(true);
                        String value = rs.getString(Mapping.getColName(field));
                        field.set(dto, field.getType().cast(value));
                    }
                }
                list.add(dto);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | SQLException e) {
            ConnClass.printError(e);
        } finally {
            try {
                CONNECTION.close();
            } catch (SQLException ex) {
                ConnClass.printError(ex);
            }
        }
        return list;
    }

    public static boolean update(String SQL, Map<Integer, String> params) {
        Connection CONNECTION = ConnClass.getDB();
        try {
            PreparedStatement pst = CONNECTION.prepareStatement(SQL);
            if (params != null) {
                for (Map.Entry<Integer, String> entry : params.entrySet()) {
                    Integer key = entry.getKey();
                    String value = entry.getValue();
                    pst.setString(key, value);
                }
            }
            return pst.executeUpdate() >= 0;
        } catch (SQLException e) {
            ConnClass.printError(e);
            return false;
        } finally {
            try {
                CONNECTION.close();
            } catch (SQLException ex) {
                ConnClass.printError(ex);
            }
        }
    }
}
