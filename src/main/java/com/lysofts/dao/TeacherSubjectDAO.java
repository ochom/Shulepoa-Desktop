/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.TeacherSubject;
import com.lysofts.pa.Mapping;
import com.lysofts.pa.QueryRunner;
import com.lysofts.utils.ConnClass;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rick
 */
public class TeacherSubjectDAO {

    static String table = Mapping.getTableName(TeacherSubject.class);

    public static List<TeacherSubject> get(String teacherId) {
        String SQL = String.format("SELECT * FROM %s WHERE Teacher_id=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, teacherId);
        return QueryRunner.run(SQL, params, TeacherSubject.class);
    }

    public static TeacherSubject get(String className, String subjectCode) {
        String SQL = String.format("SELECT * FROM %s WHERE Teaching_class=? AND Subject_code=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, className);
        params.put(2, subjectCode);
        List<TeacherSubject> list = QueryRunner.run(SQL, params, TeacherSubject.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    public static boolean add(TeacherSubject data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(),
                    param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(TeacherSubject data) {
        try {
            Mapping.Param param = new Mapping().updateQuery(data);
            String SQL = String.format("UPDATE %s SET %s WHERE id=%s", table, param.getFieldString(), data.getId());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean delete(TeacherSubject data) {
        return delete(data.getTeacherId(), data.getSubjectCode());
    }

    public static boolean delete(String teacherId, String subjectCode) {
        String SQL = String.format("DELETE FROM %s WHERE Teacher_id=? AND Subject_code=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, teacherId);
        params.put(2, subjectCode);
        return QueryRunner.update(SQL, params);
    }
}
