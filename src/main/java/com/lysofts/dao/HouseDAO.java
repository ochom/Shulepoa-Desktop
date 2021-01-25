/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.House;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class HouseDAO {

    public HouseDAO() {
    }

    public List<House> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<House> house = null;
        try {
            String SQL = "SELECT t FROM House t";
            Query query = em.createQuery(SQL, House.class);
            house = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return house;
    }

    public House get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        House classroom = em.find(House.class, id);
        em.getTransaction().commit();
        em.close();
        return classroom;
    }

    public boolean add(House data) {
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

    public boolean update(House data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            House classroom = em.find(House.class, data.getId());
            classroom.setName(data.getName());
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
        House classroom = em.find(House.class, pk);
        em.remove(classroom);
        em.getTransaction().commit();
    }
}
