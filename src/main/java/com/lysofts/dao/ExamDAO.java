/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import static com.lysofts.dao.ExamDAO.table;
import com.lysofts.entities.Exam;
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
public class ExamDAO {

    static String table = Mapping.getTableName(Exam.class);

    public static List<Exam> getYears() {
        String SQL = String.format("SELECT * FROM %s GROUP BY year ORDER BY year DESC", table);
        return QueryRunner.run(SQL, null, Exam.class);
    }

    public static List<Exam> get() {
        String SQL = String.format("SELECT * FROM %s", table);
        Map<Integer, String> params = new HashMap<>();
        return QueryRunner.run(SQL, params, Exam.class);
    }

    public static Exam getByYearAndTerm(String year, String term) {
        String SQL = String.format("SELECT * FROM %s WHERE Year=? AND Term=?", table, year, term);
        Map<Integer, String> params = new HashMap<>();
        params.put(1, year);
        params.put(2, term);
        List l = QueryRunner.run(SQL, params, Exam.class);
        return l.size() > 0 ? (Exam) l.get(0) : null;
    }

    public static boolean add(Exam data) {
        try {
            Mapping.Param param = new Mapping().insertQuery(data);
            String SQL = String.format("INSERT INTO %s (%s) VALUES (%s)", table, param.getFieldString(), param.getValuesString());
            return QueryRunner.update(SQL, param.getDatMap());
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        }
    }

    public static boolean update(Exam data) {
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
