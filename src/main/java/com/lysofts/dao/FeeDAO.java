/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.dao;

import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Fee;
import com.lysofts.utils.ConnClass;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mspace-dev
 */
public class FeeDAO {
    
    public static List<Fee> get() {
        EntityManager em = MyEntityManager.getEm();
        List<Fee> fees = null;
        try {
            String SQL = "SELECT t FROM Fee t";
            Query query = em.createQuery(SQL, Fee.class);
            fees = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return fees;
    }

    public static Fee get(int id) {
        EntityManager em = MyEntityManager.getEm();
        Fee fee = em.find(Fee.class, id);
        em.getTransaction().commit();
        //em.close();
        return fee;
    }

    public static Fee get(String receiptNumber) { 
        EntityManager em = MyEntityManager.getEm();
        Fee fee = null;
        try {
            String SQL = "SELECT t FROM Fee t WHERE t.receiptNumber=?1";
            Query query = em.createQuery(SQL, Fee.class);
            query.setParameter(1, receiptNumber);
            List<Fee> fees = query.getResultList();
            if (fees.size()>0) {
                fee = fees.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            ConnClass.printError(ex);
        } finally {
            //em.close();
        }
        return fee;
    }


    public static boolean add(Fee data) {
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

    public static boolean update(Fee data) {
        EntityManager em = MyEntityManager.getEm();
        try {
            Fee fee = em.find(Fee.class, data.getId());
            fee.setRegNumber(data.getRegNumber());
            fee.setTransCode(data.getTransCode());
            fee.setDebit(data.getDebit());
            fee.setCredit(data.getCredit());
            fee.setYear(data.getYear());
            fee.setMode(data.getMode());
            fee.setReceiptNumber(data.getReceiptNumber());
            fee.setInWords(data.getInWords());
            fee.setCreated(data.getCreated());
            fee.setCreatedBy(data.getCreatedBy());
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
        Fee fee = em.find(Fee.class, pk);
        em.remove(fee);
        em.getTransaction().commit();
    }
}
