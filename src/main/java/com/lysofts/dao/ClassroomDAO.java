/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Classroom;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class ClassroomDAO {

    public static List<Classroom> get() {
        EntityManager em = MyEntityManager.getEm();
        List<Classroom> classrooms = null;
        try {
            String SQL = "SELECT t FROM Classroom t";
            Query query = em.createQuery(SQL, Classroom.class);
            classrooms = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return classrooms;
    }

    public static Classroom get(int id) {
        EntityManager em = MyEntityManager.getEm();
        Classroom classroom = em.find(Classroom.class, id);
        em.getTransaction().commit();
        //em.close();
        return classroom;
    }

    public static boolean add(Classroom data) {
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

    public static boolean update(Classroom data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            Classroom classroom = em.find(Classroom.class, data.getId());
            classroom.setName(data.getName());
            classroom.setClassTeacher(data.getClassTeacher());
            classroom.setSignature(data.getSignature());
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
        Classroom classroom = em.find(Classroom.class, pk);
        em.remove(classroom);
        em.getTransaction().commit();
    }
}
