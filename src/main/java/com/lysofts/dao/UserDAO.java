/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class UserDAO {

    EntityManager em;
    EntityManagerFactory emfactory;

    String SQL;

    public UserDAO() {
    }

    public User getUser(int id) {   
        em = new MyEntityManager().getEm();
        User user = em.find(User.class, id);
        return user;
    }

    public User getUser(Map<String, String> params) {
        SQL = "SELECT * FROM User WHERE 1=1 ";
        params.entrySet().forEach((entry) -> {
            SQL += " AND " + entry.getKey() + "='" + entry.getValue() + "'";
        });
        System.out.println(SQL);        
        em = new MyEntityManager().getEm();
        User user = new User();
        try {
            Query query = em.createNativeQuery(SQL, User.class);
            user = (User) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception ex) {
            user=null;
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    public List<User> listUsers() {
        em = new MyEntityManager().getEm();
        List<User> users = new ArrayList<>();
        try {
            SQL = "SELECT * FROM User";
            Query query = em.createNativeQuery(SQL, User.class);
            users = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return users;
    }

    public boolean addUser(User user) {        
        em = new MyEntityManager().getEm();
        try {
            SQL = "INSERT INTO User(username, password) values (?,?)";
            Query query = em.createNativeQuery(SQL);
            query.setParameter(1, user.getUsername());
            query.setParameter(2, user.getPassword());
            em.persist(query);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateUser(User data) {
        em = new MyEntityManager().getEm();
        try {
            User user = getUser(data.getId());
            user.setUsername(data.getUsername());
            user.setPassword(data.getPassword());
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public void deleteUser(int pk) {
        User user = getUser(pk);
        em.remove(user);
        em.getTransaction().commit();
    }
}
