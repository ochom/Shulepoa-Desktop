/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import com.lysofts.dao.SchoolDAO;
import com.lysofts.entities.School;
import java.text.SimpleDateFormat;
import javax.swing.UIManager;

/**
 *
 * @author Ritch
 */
public class Launcher {
    
    static School school = null;

    private boolean hasExpired() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = dateFormat.format(new java.util.Date());
        int leo = Integer.parseInt(today), installed = Integer.parseInt(school.getInstalled());
        return (leo - installed) >= 15;
    }
    
    private  void checkExpiry(){  
        school = SchoolDAO.get();
        if (school == null || (school.isActivated().equals("0") && hasExpired())) {
           new ActivationFrm().setVisible(true);
        } else {
        new LoginFrm().setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ConnClass.printError(ex);
        }

        DecorationHelper.decorateWindows(false);
        
        new Launcher().checkExpiry();
    }
}
