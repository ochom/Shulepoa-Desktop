/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.Student;
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
public class StudentDAO {

    static String table = Mapping.getTableName(Student.class);

    public static List<Student> get() {
        String SQL = String.format("SELECT * FROM %s", table);
        return QueryRunner.run(SQL, null, Student.class);
    }

    public static Student get(String studentId) {
        String SQL = String.format("SELECT * FROM %s  WHERE Student_ID = ?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, studentId);
        List<Student> list = QueryRunner.run(SQL, params, Student.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    public static List<Student> get(String searchText, String other) {
        String SQL = String.format("SELECT * FROM %s  WHERE Student_ID LIKE ? OR Student_name LIKE ?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, "%" + searchText + "%");
        params.put(2, "%" + searchText + "%");
        return QueryRunner.run(SQL, params, Student.class);
    }

    public static List<Student> getInClass(String className) {
        String SQL = String.format("SELECT * FROM %s WHERE Student_Class=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, className);
        return QueryRunner.run(SQL, params, Student.class);
    }

    public static boolean add(Student data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(), param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(Student data) {
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
        String SQL = String.format("DELETE FROM %s WHERE id=%s", table, pk);
        Map<Integer, String> params = new HashMap<>();
        return QueryRunner.update(SQL, params);
    }
}
