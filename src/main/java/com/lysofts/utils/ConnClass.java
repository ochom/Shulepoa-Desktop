package com.lysofts.utils;

import java.awt.Toolkit;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class ConnClass {

    private static Connection conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static String url;

    private final static boolean DEBUG = true;
    
    public static Connection connectDB() {
        try {
            url = DEBUG? 
                    "jdbc:sqlite::resource:database/ExamsDB.db": 
                    "jdbc:sqlite:C:/Acme/Exam System/ExamsDB.db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            printError(e);
        }
        if(conn!=null){
           System.out.println("Connected");
        }
        return conn;
    }

    public void setFrameIcon(JFrame jframe) {
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/A Logo Icon File.png")));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/A logo.png")));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int CountRows(String sql) {
        int rows = -1;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                rows = Integer.parseInt(rs.getString("total"));
            }
            return rows;
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return rows;
        }
    }
    
    public static void printError(Exception ex){
        Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
    }

    public static boolean Query(String sql) {
        try {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static ResultSet QueryWithResult(String sql){
        try {
            return conn.prepareStatement(sql).executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
