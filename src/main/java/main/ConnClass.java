package main;

import java.awt.Toolkit;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class ConnClass {

    private static Connection Conn = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    public static Connection connectDB() {
        try {
            String url;
            url = "jdbc:sqlite:C:/Acme/Exam System/ExamsDB.db";/*  */
            Conn = DriverManager.getConnection(url);
            return Conn;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void setFrameIcon(JFrame jframe) {
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/A Logo Icon File.png")));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            jframe.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/A logo.png")));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int CountRows(String sql) {
        int rows = -1;
        try {
            pst = Conn.prepareStatement(sql);
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
            pst = Conn.prepareStatement(sql);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public static ResultSet QueryWithResult(String sql){
        try {
            return Conn.prepareStatement(sql).executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnClass.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
