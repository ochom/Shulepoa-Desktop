package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class SystemInitialization {

    Connection Conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = null;

    ConfigFileClass configClass = new ConfigFileClass();

    public void run() {
        DatabaseUpdate();
        checkActivation();
    }

    private void DatabaseUpdate() {
        if (configClass.agreement.exists() && configClass.AgreedToTerms()) {
            if (configClass.db_update.exists()) {
                try {
                    String SQL = configClass.ReadFile(configClass.db_update);
                    System.out.println(SQL);
                    String[] sqls = SQL.split(";");
                    for (String sql : sqls) {
                        runQuery(sql);
                        System.out.println("Success");
                        configClass.DeleteFile(configClass.db_update);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    private void runQuery(String QueryText) {
        Conn = ConnClass.connectDB();
        try {
            pst = Conn.prepareStatement(QueryText);
            pst.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            try {
                Conn.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    private void checkActivation() {
        if (configClass.isNewInstallation()) {//New Installation
            if (configClass.activeState(0)) {
                configClass.setTrialDeadline();
                new LoginFrm().setVisible(true);
            } else if (configClass.activeState(1)) {
                if (configClass.isPeriodEnded()) {
                    configClass.setTrialEnded();
                    System.out.println("Deactivated");
                }
                new LoginFrm().setVisible(true);
            } else if (configClass.activeState(2)) {
                JOptionPane.showMessageDialog(null, "Your trial license has Expired. Enter your product key to Continue using this program", "Activation", 1);
                new ActivationFrm("new").setVisible(true);
            } else if (configClass.activeState(3)) {
                new LoginFrm().setVisible(true);
            }
        } else {//Update Installation
            if (configClass.activeState(0) && configClass.AgreedToTerms()) {
                configClass.setTrialDeadline();
                new LoginFrm().setVisible(true);
            } else if (configClass.activeState(1)) {
                if (configClass.isPeriodEnded()) {
                    configClass.setTrialEnded();
                    System.out.println("Deactivated");
                }
                new LoginFrm().setVisible(true);
            } else if (configClass.activeState(2)) {
                JOptionPane.showMessageDialog(null, "Your Update trial license has Expired. Enter your product key to Continue using this program", "Activation", 1);
                new ActivationFrm("update").setVisible(true);
            } else if (configClass.activeState(3)) {
                new LoginFrm().setVisible(true);
            }else{
                new LoginFrm().setVisible(true);            
            }
        }
    }

}
