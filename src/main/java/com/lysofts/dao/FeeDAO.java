/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.Fee;
import com.lysofts.pa.Mapping;
import com.lysofts.pa.QueryRunner;
import com.lysofts.utils.ConnClass;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mspace-dev
 */
public class FeeDAO {
    
    static String table = Mapping.getTableName(Fee.class);

    public static List<Fee> get() {
        String SQL = String.format("SELECT * FROM %s", table);
        return QueryRunner.run(SQL, null, Fee.class);
    }

    public static List<Fee> get(String receiptNumber) {
        String SQL = String.format("SELECT * FROM %s WHERE receipt_no=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, receiptNumber);
        return QueryRunner.run(SQL, null, Fee.class);
    }

    public static boolean add(Fee data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(), param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(Fee data) {
        try {
            Mapping.Param param = new Mapping().updateQuery(data);
            String SQL = String.format("UPDATE %s SET %s WHERE id=%s", table, param.getFieldString(), data.getId());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean delete(String pk) {
        String SQL = String.format("DELETE FROM %s WHERE id=%s",table, pk);
        Map<Integer, String> params = new HashMap<>();
        return QueryRunner.update(SQL, params);
    }
}
