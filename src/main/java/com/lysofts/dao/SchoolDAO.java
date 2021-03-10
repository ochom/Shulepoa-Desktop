/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.School;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class SchoolDAO {

    public static School get() {
        String SQL = "SELECT t FROM School t";
        School school = null;
        EntityManager em = MyEntityManager.getEm();
        try {
            Query query = em.createQuery(SQL, School.class);
            List<School> schools = query.getResultList();
            if (schools.size() > 0) {
                school = schools.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return school;
    }

    public static School get(int id) {
        EntityManager em = MyEntityManager.getEm();
        School school = em.find(School.class, id);
        em.getTransaction().commit();
        //em.close();
        return school;
    }

    public static boolean add(School data) {
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

    public static boolean update(School data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            School school = em.find(School.class, data.getId());
            school.setName(data.getName());
            school.setPostalAddress(data.getPostalAddress());
            school.setMotto(data.getMotto());
            school.setContact(data.getContact());
            school.setPrincipal(data.getPrincipal());
            school.setLogo(data.getLogo());
            school.setSignature(data.getSignature());
            school.setClosingDate(data.getClosingDate());
            school.setOpeningDate(data.getOpeningDate());
            school.setActivated(data.isActivated());
            school.setInstalled(data.getInstalled());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ConnClass.printError(ex);
            return false;
        } finally {
            //em.close();
        }
    }
}
