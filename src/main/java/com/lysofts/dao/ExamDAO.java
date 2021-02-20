/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Exam;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class ExamDAO {

    public static List<Exam> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<Exam> exams = null;
        try {
            String SQL = "SELECT t FROM Exam t";
            Query query = em.createQuery(SQL, Exam.class);
            exams = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            em.close();
        }
        return exams;
    }

    public static List<Exam> getYears() {
        EntityManager em = new MyEntityManager().getEm();
        List<Exam> exams = null;
        try {
            String SQL = "SELECT t FROM Exam t GROUP BY t.year ORDER BY t.year DESC";
            Query query = em.createQuery(SQL, Exam.class);
            exams = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            em.close();
        }
        return exams;
    }

    public static Exam get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        Exam exam = em.find(Exam.class, id);
        em.getTransaction().commit();
        em.close();
        return exam;
    }

    public static boolean add(Exam data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            em.persist(data);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            em.close();
        }
    }

    public static boolean update(Exam data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            Exam exam = em.find(Exam.class, data.getId());
            exam.setName(data.getName());
            exam.setYear(data.getYear());
            exam.setTerm(data.getTerm());
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            em.close();
        }
    }

    public static void delete(int pk) {
        EntityManager em = new MyEntityManager().getEm();
        Exam exam = em.find(Exam.class, pk);
        em.remove(exam);
        em.getTransaction().commit();
    }
}
