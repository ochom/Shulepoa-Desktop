package com.lysofts.utils;

import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.sql.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConnClass {

    private static Connection conn;
    private static PreparedStatement pst;
    private static ResultSet rs;
    private static String url;

    private final static boolean DEBUG = false;

    static final Logger logger = Logger.getLogger(ConnClass.class.getName());

    public static String API_END = DEBUG
            ? "http://localhost:8000/accounts/"
            : "https://acme-accounts.herokuapp.com/accounts/";

    public static Connection connectDB() {
        try {
            url = "jdbc:sqlite:struct";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            printError(e);
        }
        if (conn != null) {
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
    
    public static JDialog loadingDlg(JFrame frame){
        JDialog jDialog = new JDialog(frame);
        jDialog.add(new JLabel("Processing please wait..."));
        jDialog.setType(Window.Type.POPUP);
        jDialog.setUndecorated(true);
        jDialog.pack();
        jDialog.setLocationRelativeTo(frame);
        return jDialog;
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
            boolean append = true;
            FileHandler handler = new FileHandler("logs.txt", append);
            logger.addHandler(handler);
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException | SecurityException ex1) {
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
