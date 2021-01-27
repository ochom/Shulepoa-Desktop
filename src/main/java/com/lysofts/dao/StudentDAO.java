/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Student;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class StudentDAO {

    public StudentDAO() {
    }

    public List<Student> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<Student> students = null;
        try {
            String SQL = "SELECT t FROM Student t";
            Query query = em.createQuery(SQL, Student.class);
            students = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            em.close();
        }
        return students;
    }

    public List<Student> get(String searchText) {
        searchText = searchText.toUpperCase();
        EntityManager em = new MyEntityManager().getEm();
        List<Student> students = null;
        try {
            String SQL = "SELECT t FROM Student t WHERE t.regNumber LIKE UPPER(?1) OR t.name LIKE UPPER(?2)";
            Query query = em.createQuery(SQL, Student.class);
            query.setParameter(1, "%"+searchText+"%");
            query.setParameter(2, "%"+searchText+"%");
            students = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            em.close();
        }
        return students;
    }

    public Student get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        Student student = em.find(Student.class, id);
        em.getTransaction().commit();
        em.close();
        return student;
    }

    public boolean add(Student data) {
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

    public boolean update(Student data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            Student student = em.find(Student.class, data.getId());
            student.setName(data.getName());
            student.setRegNumber(data.getRegNumber());
            student.setSex(data.getSex());
            student.setClassroom(data.getClassroom());
            student.setDoa(data.getDoa());
            student.setDob(data.getDob());
            student.setKcpeMarks(data.getKcpeMarks());
            student.setKcpeGrade(data.getKcpeGrade());
            student.setPassport(data.getPassport());
            student.setHouse(data.getHouse());
            student.setKinName(data.getKinName());
            student.setKinPhone(data.getKinPhone());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            em.close();
        }
    }

    public void delete(int pk) {
        EntityManager em = new MyEntityManager().getEm();
        Student student = em.find(Student.class, pk);
        em.remove(student);
        em.getTransaction().commit();
    }
}
