/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Teacher;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class TeacherDAO {

    public TeacherDAO() {
    }

    public List<Teacher> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<Teacher> teachers = null;
        try {
            String SQL = "SELECT t FROM Teacher t";
            Query query = em.createQuery(SQL, Teacher.class);
            teachers = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return teachers;
    }

    public Teacher get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        Teacher teacher = em.find(Teacher.class, id);
        em.getTransaction().commit();
        em.close();
        return teacher;
    }

    public boolean add(Teacher data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            em.persist(data);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean update(Teacher data) {
        EntityManager em = new MyEntityManager().getEm();
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
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void delete(int pk) {
        EntityManager em = new MyEntityManager().getEm();
        Teacher teacher = em.find(Teacher.class, pk);
        em.remove(teacher);
        em.getTransaction().commit();
    }
}
