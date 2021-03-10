/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Teacher;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class TeacherDAO {
    
    public static List<Teacher> get() {
        EntityManager em = MyEntityManager.getEm();
        List<Teacher> teachers = null;
        try {
            String SQL = "SELECT t FROM Teacher t";
            Query query = em.createQuery(SQL, Teacher.class);
            teachers = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return teachers;
    }

    public static Teacher get(int id) {
        EntityManager em = MyEntityManager.getEm();
        Teacher teacher = em.find(Teacher.class, id);
        em.getTransaction().commit();
        //em.close();
        return teacher;
    }

    public static boolean add(Teacher data) {
        EntityManager em = MyEntityManager.getEm();
        try {
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

    public static boolean update(Teacher data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            Teacher teacher = em.find(Teacher.class, data.getId());
            teacher.setName(data.getName());
            teacher.setStaffNumber(data.getStaffNumber());
            teacher.setGender(data.getGender());
            teacher.setPhone(data.getPhone());
            teacher.setInitials(data.getInitials());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            //em.close();
        }
    }

    public static void delete(int pk) {
        EntityManager em = MyEntityManager.getEm();
        Teacher teacher = em.find(Teacher.class, pk);
        em.remove(teacher);
        em.getTransaction().commit();
    }
}
