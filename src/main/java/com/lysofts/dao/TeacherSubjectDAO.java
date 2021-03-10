/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.TeacherSubject;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class TeacherSubjectDAO {

    public static List<TeacherSubject> get(String teacherId) {
        EntityManager em = MyEntityManager.getEm();
        List<TeacherSubject> list = null;
        try {
            String SQL = "SELECT t FROM TeacherSubject t WHERE t.teacherId=:id";
            Query query = em.createQuery(SQL, TeacherSubject.class);
            query.setParameter("id", teacherId);
            list = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return list;
    }

    public static TeacherSubject get(String subjectCode, String teacherId) {
        EntityManager em = MyEntityManager.getEm();
        TeacherSubject data = null;
        try {
            String SQL = "SELECT t FROM TeacherSubject t WHERE t.teacherId=:id";
            Query query = em.createQuery(SQL, TeacherSubject.class);
            query.setParameter("id", teacherId);
            List<TeacherSubject> list = query.getResultList();
            if (list.size() > 0) {
                data = list.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return data;
    }

    public static boolean add(TeacherSubject data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            Query q = em.createQuery("DELETE  FROM TeacherSubject t WHERE t.subjectCode=:sc AND t.classroom=:c");
            q.setParameter("sc", data.getSubjectCode());
            q.setParameter("c", data.getClassroom());
            q.executeUpdate();
            em.persist(data);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            //em.close();
        }
    }

    public static boolean update(TeacherSubject data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            TeacherSubject tSubject = em.find(TeacherSubject.class, data.getId());
            tSubject.setTeacherId(data.getTeacherId());
            tSubject.setSubjectName(data.getSubjectName());
            tSubject.setSubjectCode(data.getSubjectCode());
            tSubject.setClassroom(data.getClassroom());
            tSubject.setTeacherInitials(data.getTeacherInitials());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            //em.close();
        }
    }

    public static void delete(TeacherSubject data) {
        EntityManager em = MyEntityManager.getEm();
        TeacherSubject ts = em.find(TeacherSubject.class, data.getId());
        em.remove(ts);
        em.getTransaction().commit();
    }
}
