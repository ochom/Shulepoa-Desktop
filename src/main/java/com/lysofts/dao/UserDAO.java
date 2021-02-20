/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.User;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class UserDAO {

    public static List<User> get() {
        EntityManager em = new MyEntityManager().getEm();
        List<User> users = null;
        try {
            String SQL = "SELECT t FROM User t";
            Query query = em.createQuery(SQL, User.class);
            users = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            em.close();
        }
        return users;
    }


    public static User get(int id) {
        EntityManager em = new MyEntityManager().getEm();
        User user = em.find(User.class, id);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public static boolean add(User data) {
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

    public static boolean update(User data) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            User user = em.find(User.class, data.getId());
            user.setName(data.getName());
            user.setPassword(data.getPassword());
            user.setPhone(data.getPhone());
            user.setEmail(data.getEmail());
            em.getTransaction().commit();
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
        User user = em.find(User.class, pk);
        em.remove(user);
        em.getTransaction().commit();
    }
}
