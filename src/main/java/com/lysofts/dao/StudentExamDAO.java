/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.StudentExam;
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
public class StudentExamDAO {

    static String table = Mapping.getTableName(StudentExam.class);

    public static List<StudentExam> get() {
        String SQL = String.format("SELECT * FROM %s", table);
        return QueryRunner.run(SQL, null, StudentExam.class);
    }

    public static StudentExam get(String studentId, String classroom, String year, String term) {
        String SQL = String
                .format("SELECT * FROM %s  WHERE SE_Student_id=? AND SE_StudentClass=? AND Year=? AND Term=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, studentId);
        params.put(2, classroom);
        params.put(3, year);
        params.put(4, term);
        List<StudentExam> list = QueryRunner.run(SQL, params, StudentExam.class);
        return list.size() > 0 ? list.get(0) : null;
    }

    public static List<StudentExam> get(String classroom, String year, String term) {
        String SQL = String.format("SELECT * FROM %s  WHERE SE_StudentClass=? AND Year=? AND Term=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, classroom);
        params.put(2, year);
        params.put(3, term);
        return QueryRunner.run(SQL, params, StudentExam.class);
    }

    public static List<StudentExam> getByForm(String form, String year, String term) {
        String SQL = String.format("SELECT * FROM %s  WHERE substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?",
                table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, form);
        params.put(2, year);
        params.put(3, term);
        return QueryRunner.run(SQL, params, StudentExam.class);
    }

    public static List<StudentExam> getInClass(String className) {
        String SQL = String.format("SELECT * FROM %s WHERE Student_Class=?", table);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, className);
        return QueryRunner.run(SQL, params, StudentExam.class);
    }

    public static boolean add(StudentExam data) {
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

    public static boolean update(StudentExam data) {
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
