/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.User;
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
public class UserDAO {

    static String table = Mapping.getTableName(User.class);

    public static List<User> get() {
        String SQL = String.format("SELECT * FROM %s", table);
        return QueryRunner.run(SQL, null, User.class);
    }

    public static boolean add(User data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(), param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(User data) {
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
