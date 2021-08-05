/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.StudentSubject;
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
public class StudentSubjectDAO {

    static String table = Mapping.getTableName(StudentSubject.class);

    public static List<StudentSubject> get(String studentId) {
        String SQL = String.format("SELECT * FROM %s WHERE SS_Student_id=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, studentId);
        return QueryRunner.run(SQL, params, StudentSubject.class);
    }

    public static StudentSubject get(String studentId, String subjectCode) {
        String SQL = String.format("SELECT * FROM %s WHERE SS_Student_id=? AND SS_Subject_code=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, studentId);
        params.put(2, subjectCode);
        List<StudentSubject> list = QueryRunner.run(SQL, params, StudentSubject.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    public static boolean add(StudentSubject data) {
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

    public static boolean delete(StudentSubject data) {
        return delete(data.getStudentId(), data.getSubjectCode());
    }

    public static boolean delete(String studentId, String subjectCode) {
        String SQL = String.format("DELETE FROM %s WHERE SS_Student_id=? AND SS_Subject_code=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, studentId);
        params.put(2, subjectCode);
        return QueryRunner.update(SQL, params);
    }
}
