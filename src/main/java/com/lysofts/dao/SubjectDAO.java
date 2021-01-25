/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Subject;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class SubjectDAO {

    public SubjectDAO() {
    }

    public List<Subject> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<Subject> subjects = null;
        try {
            String SQL = "SELECT t FROM Subject t";
            Query query = em.createQuery(SQL, Subject.class);
            subjects = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return subjects;
    }

    public Subject get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        Subject subject = em.find(Subject.class, id);
        em.getTransaction().commit();
        em.close();
        return subject;
    }

    public boolean add(Subject data) {
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

    public boolean update(Subject data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            Subject subject = em.find(Subject.class, data.getId());
            subject.setName(data.getName());
            subject.setNumber(data.getNumber());
            subject.setCode(data.getCode());
            subject.setGrade1(data.getGrade1());
            subject.setGrade2(data.getGrade2());
            subject.setGrade3(data.getGrade3());
            subject.setGrade4(data.getGrade4());
            subject.setGrade5(data.getGrade5());
            subject.setGrade6(data.getGrade6());
            subject.setGrade7(data.getGrade7());
            subject.setGrade8(data.getGrade8());            
            subject.setGrade9(data.getGrade9());
            subject.setGrade10(data.getGrade10());
            subject.setGrade11(data.getGrade11());
            subject.setGrade12(data.getGrade12());
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
        Subject subject = em.find(Subject.class, pk);
        em.remove(subject);
        em.getTransaction().commit();
    }
}
