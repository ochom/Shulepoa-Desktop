/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.Subject;
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
public class SubjectDAO {

    static String table = Mapping.getTableName(Subject.class);

    public static List<Subject> get() {
        String SQL = String.format("SELECT * FROM %s ORDER BY S_NO+0 ASC", table);
        return QueryRunner.run(SQL, null, Subject.class);
    }

    public static Subject getByCode(String subjectCode) {
        String SQL = String.format("SELECT * FROM %s WHERE Subject_Code=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, subjectCode);
        List<Subject> list = QueryRunner.run(SQL, params, Subject.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    public static boolean add(Subject data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(), param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(Subject data) {
        try {
            Mapping.Param param = new Mapping().updateQuery(data);
            String SQL = String.format("UPDATE %s SET %s WHERE Subject_id=%s", table, param.getFieldString(), data.getId());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean delete(String pk) {
        String SQL = String.format("DELETE FROM %s WHERE Subject_id=%s", table, pk);
        Map<Integer, String> params = new HashMap<>();
        return QueryRunner.update(SQL, params);
    }
}
