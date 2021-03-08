/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import com.lysofts.utils.ConnClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mspace-dev
 */
public class MyEntityManager {

    public static EntityManager getEm() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("DbPersistanceUnit");
            EntityManager em;
            em = emf.createEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
//            int a = Integer.parseInt("Hello");
//            System.out.println(a);
            return em;
        } catch (Exception e) {
            ConnClass.printError(e);
            return null;
        }
    }
}
