/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mspace-dev
 */
public class MyEntityManager {

    EntityManagerFactory emf;
    EntityManager em;

    public MyEntityManager() {
        emf = Persistence.createEntityManagerFactory("DbPersistanceUnit");
    }

    public EntityManager getEm() {
        em = emf.createEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        return em;
    }
}
