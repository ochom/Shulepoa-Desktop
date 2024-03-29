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
    private static final String URL = "jdbc:sqlite:struct";

    static final Logger LOG = Logger.getLogger(ConnClass.class.getName());

    public static Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            printError(e);
        }
        return conn;
    }
    
    public static Connection getDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            printError(e);
        }
        return conn;
    }

    public static void setFrameIcon(JFrame jframe) {
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(ConnClass.class.getClassLoader().getResource("images/A Logo Icon File.png")));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(ConnClass.class.getClassLoader().getResource("images/A logo.png")));

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

    public static void printError(Exception ex) {
        try {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex1) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex1);
        }
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

    public static ResultSet QueryWithResult(String sql) {
        try {
            return conn.prepareStatement(sql).executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
