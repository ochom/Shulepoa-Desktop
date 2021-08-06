/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Ritch
 */
public class KCSE_AnalysisFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst;
    ResultSet rs;

    // project
    DefaultTableModel model1, model2, model3;
    String project_title = "";
    int no_of_subjects = 0;
    ArrayList<Subject> SubsForRanking;

    SwingWorker<Void, Integer> worker = null;

    public KCSE_AnalysisFrm() {
        initComponents();

        jLabel5.setVisible(false);
        jProgressBar1.setVisible(false);
        ProgressNo.setVisible(false);
        ConnClass.setFrameIcon(this);
        getClasses();
    }

    private void getClasses() {
        try {
            comboForm.removeAllItems();
            comboForm.addItem("Select class");
            pst = Conn.prepareStatement("SELECT * FROM tblclasses ORDER BY Class_name ASC");
            rs = pst.executeQuery();
            while (rs.next()) {
                comboForm.addItem(rs.getString("Class_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void getStudents(String classname) {
        model2 = (DefaultTableModel) table_students.getModel();
        try {
            model2.setRowCount(0);
            pst = Conn.prepareStatement("SELECT * FROM Student_details WHERE Student_Class='" + classname
                    + "' ORDER BY (Student_ID+0) ASC");
            rs = pst.executeQuery();
            while (rs.next()) {
                String adm = rs.getString("Student_ID");
                String Name = rs.getString("Student_Name");
                String Gender = rs.getString("Gender");
                model2.addRow(new Object[] { false, adm, "", Name, Gender });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 1);
            e.printStackTrace();
        }
    }

    private void GetStudentsInExamsTable() {
        int subjectCount = ConnClass.CountRows("SELECT count(*) as total FROM subjects");
        String[] column_names = new String[3 + subjectCount];
        column_names[0] = "Adm No.";
        column_names[1] = "Index";
        column_names[2] = "Student Name";
        try {
            pst = Conn.prepareStatement("SELECT * FROM  subjects");
            rs = pst.executeQuery();
            int j = 3;
            while (rs.next()) {
                column_names[j] = rs.getString("Subject_code");
                j++;
            }
            model3 = new DefaultTableModel(null, column_names);
            model3.setRowCount(0);

            pst = Conn.prepareStatement("SELECT * FROM tbl_kcse_marks WHERE project_title='" + project_title
                    + "' ORDER BY (index_no + 0) ASC,(adm + 0) ASC");
            rs = pst.executeQuery();
            String[] row_data = null;
            while (rs.next()) {
                row_data = new String[3 + subjectCount];
                row_data[0] = rs.getString("adm");
                row_data[1] = rs.getString("index_no");
                row_data[2] = rs.getString("name");

                for (int i = 3; i < subjectCount + 3; i++) {
                    row_data[i] = rs.getString("sub_" + (i - 2) + "_G");
                }

                model3.addRow(row_data);
            }
            jTable1.setModel(model3);
            jTable1.getColumn("Student Name").setPreferredWidth(200);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 1);
            e.printStackTrace();
        }

    }

    private String toPoints(String grade) {
        switch (grade.toLowerCase()) {
            case "a":
                return "12";
            case "a-":
                return "11";
            case "b+":
                return "10";
            case "b":
                return "9";
            case "b-":
                return "8";
            case "c+":
                return "7";
            case "c":
                return "6";
            case "c-":
                return "5";
            case "d+":
                return "4";
            case "d":
                return "3";
            case "d-":
                return "2";
            case "e":
                return "1";
            default:
                return "";
        }
    }

    private String getMeanP(String Total_Points) {
        String p = "Y";
        if (Total_Points.equalsIgnoreCase("Y")) {
            return p;
        }
        return String.valueOf(Integer.parseInt(Total_Points) / 7.00);
    }

    private String getMeanG(String Total_Points) {
        String p = "Y";
        if (Total_Points.equalsIgnoreCase("Y")) {
            return p;
        }
        float mp = Math.round(Float.parseFloat(String.valueOf(Integer.parseInt(Total_Points) / 7.00)));
        double[] checks = { 11.43, 10.43, 9.43, 8.43, 7.43, 6.43, 5.43, 4.43, 3.43, 2.43, 1.43, 0 };
        String[] grades = { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "E" };
        int i = 0;
        for (double k : checks) {
            if (k > mp) {
                i++;
            }
        }
        return grades[i];
    }

    private void CalculateStudentsPositions() {
        try {
            String position = "(1+(SELECT COUNT(*) FROM tbl_kcse_marks as t2 WHERE ((t2.total_p+0)>(tbl_kcse_marks.total_p+0) AND t2.project_title='"
                    + project_title + "')))";
            String gender_position = "(1+(SELECT COUNT(*) FROM tbl_kcse_marks as t2 WHERE ((t2.total_p+0)>(tbl_kcse_marks.total_p+0) AND t2.sex=tbl_kcse_marks.sex AND t2.project_title='"
                    + project_title + "')))";
            pst = Conn.prepareStatement("UPDATE tbl_kcse_marks SET position=" + position + ",gender_position="
                    + gender_position + " WHERE project_title='" + project_title + "'");
            pst.executeUpdate();

            registerSubjectsForRanking();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerSubjectsForRanking() {
        try {
            SubsForRanking = new ArrayList<>();
            rs = Conn.prepareStatement("SELECT * FROM Subjects").executeQuery();
            int i = 0;
            while (rs.next()) {
                i++;
                String subject_name = rs.getString("Subject_name");
                String sub_p = "sub_" + i + "_p";
                String sub_g = "sub_" + i + "_g";
                int entry = ConnClass.CountRows(
                        "SELECT count(*) as total FROM tblstudents_subjects WHERE SS_Subject_name='" + subject_name
                                + "' AND SS_Student_id IN (SELECT adm from tbl_kcse_marks WHERE project_title='"
                                + project_title + "')");
                String sub_mean = String.valueOf(Float.parseFloat(ConnClass
                        .QueryWithResult("SELECT sum(" + sub_p
                                + ")  AS field1 from tbl_kcse_marks WHERE (project_title='" + project_title + "')")
                        .getString("field1")) / entry * 1.0);

                int b_entry = ConnClass.CountRows(
                        "SELECT count(*) as total FROM tblstudents_subjects WHERE SS_Subject_name='" + subject_name
                                + "' AND SS_Student_id IN (SELECT adm from tbl_kcse_marks WHERE project_title='"
                                + project_title + "' AND sex='Male')");
                int bA = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='A')");
                int bA_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='A-')");
                int bB_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='B+')");
                int bB = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='B')");
                int bB_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='B-')");
                int bC_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='C+')");
                int bC = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='C')");
                int bC_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='C-')");
                int bD_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='D+')");
                int bD = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='D')");
                int bD_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='D-')");
                int bE = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='E')");
                int bX = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='X')");
                int bY = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Male' AND " + sub_g + " ='Y')");

                String b_m_points = String
                        .valueOf(Float
                                .parseFloat(ConnClass.QueryWithResult(
                                        "SELECT sum(" + sub_p + ") AS field1 from tbl_kcse_marks WHERE (project_title='"
                                                + project_title + "' AND  Sex='Male')")
                                        .getString("field1"))
                                / b_entry * 1.0);

                int g_entry = ConnClass.CountRows(
                        "SELECT count(*) as total FROM tblstudents_subjects WHERE SS_Subject_name='" + subject_name
                                + "' AND SS_Student_id IN (SELECT adm from tbl_kcse_marks WHERE project_title='"
                                + project_title + "' AND sex='Female')");
                int gA = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='A')");
                int gA_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='A-')");
                int gB_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='B+')");
                int gB = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='B')");
                int gB_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='B-')");
                int gC_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='C+')");
                int gC = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='C')");
                int gC_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='C-')");
                int gD_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='D+')");
                int gD = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='D')");
                int gD_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='D-')");
                int gE = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='E')");
                int gX = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='X')");
                int gY = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND Sex='Female' AND " + sub_g + " ='Y')");
                String g_m_points = String
                        .valueOf(Float
                                .parseFloat(ConnClass.QueryWithResult(
                                        "SELECT sum(" + sub_p + ") AS field1 from tbl_kcse_marks WHERE (project_title='"
                                                + project_title + "' AND  Sex='Female')")
                                        .getString("field1"))
                                / b_entry * 1.0);

                int oA = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='A')");
                int oA_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='A-')");
                int oB_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='B+')");
                int oB = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='B')");
                int oB_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='B-')");
                int oC_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='C+')");
                int oC = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='C')");
                int oC_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='C-')");
                int oD_plus = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='D+')");
                int oD = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='D')");
                int oD_min = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='D-')");
                int oE = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='E')");
                int oX = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='X')");
                int oY = ConnClass.CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                        + project_title + "' AND " + sub_g + " ='Y')");

                SubsForRanking.add(new Subject(subject_name, sub_mean, null, entry, b_entry, bA, bA_min, bB_plus, bB,
                        bB_min, bC_plus, bC, bC_min, bD_plus, bD, bD_min, bE, bX, bY, b_m_points, g_entry, gA, gA_min,
                        gB_plus, gB, gB_min, gC_plus, gC, gC_min, gD_plus, gD, gD_min, gE, gX, gY, g_m_points, oA,
                        oA_min, oB_plus, oB, oB_min, oC_plus, oC, oC_min, oD_plus, oD, oD_min, oE, oX, oY));
            }
        } catch (SQLException ex) {
            Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnClass.Query("DELETE FROM tbl_kcse_subject_ranks");
        SubsForRanking.forEach((sub) -> {
            String vals = "";
            for (Object SubjectArrayDetail : sub.SubjectArrayDetails()) {
                vals += "\'" + SubjectArrayDetail + "\'" + ",";
            }
            String subs = vals.substring(0, vals.lastIndexOf(","));
            // System.out.println(subs);
            ConnClass.Query("INSERT INTO tbl_kcse_subject_ranks VALUES (" + subs + ")");
        });
        String position = "1+(SELECT count(*) FROM tbl_kcse_subject_ranks as t2 WHERE t2.mean_points+0>tbl_kcse_subject_ranks.mean_points+0)";
        ConnClass.Query("UPDATE tbl_kcse_subject_ranks SET position=" + position + "");
        jButton4.setEnabled(true);
    }

    class Subject {

        String name, mean, position;
        int entry;
        int b_entry, bA, bA_min, bB_plus, bB, bB_min, bC_plus, bC, bC_min, bD_plus, bD, bD_min, bE, bX, bY;
        String b_m_points;

        int g_entry, gA, gA_min, gB_plus, gB, gB_min, gC_plus, gC, gC_min, gD_plus, gD, gD_min, gE, gX, gY;

        String g_m_points;

        int oA, oA_min, oB_plus, oB, oB_min, oC_plus, oC, oC_min, oD_plus, oD, oD_min, oE, oX, oY;

        public Subject(String name, String sub_mean, String position, int entry, int b_entry, int bA, int bA_min,
                int bB_plus, int bB, int bB_min, int bC_plus, int bC, int bC_min, int bD_plus, int bD, int bD_min,
                int bE, int bX, int bY, String b_m_points, int g_entry, int gA, int gA_min, int gB_plus, int gB,
                int gB_min, int gC_plus, int gC, int gC_min, int gD_plus, int gD, int gD_min, int gE, int gX, int gY,
                String g_m_points, int oA, int oA_min, int oB_plus, int oB, int oB_min, int oC_plus, int oC, int oC_min,
                int oD_plus, int oD, int oD_min, int oE, int oX, int oY) {
            this.name = name;
            this.mean = sub_mean;
            this.position = position;
            this.entry = entry;
            this.b_entry = b_entry;
            this.bA = bA;
            this.bA_min = bA_min;
            this.bB_plus = bB_plus;
            this.bB = bB;
            this.bB_min = bB_min;
            this.bC_plus = bC_plus;
            this.bC = bC;
            this.bC_min = bC_min;
            this.bD_plus = bD_plus;
            this.bD = bD;
            this.bD_min = bD_min;
            this.bE = bE;
            this.bX = bX;
            this.bY = bY;
            this.b_m_points = b_m_points;
            this.g_entry = g_entry;
            this.gA = gA;
            this.gA_min = gA_min;
            this.gB_plus = gB_plus;
            this.gB = gB;
            this.gB_min = gB_min;
            this.gC_plus = gC_plus;
            this.gC = gC;
            this.gC_min = gC_min;
            this.gD_plus = gD_plus;
            this.gD = gD;
            this.gD_min = gD_min;
            this.gE = gE;
            this.gX = gX;
            this.gY = gY;
            this.g_m_points = g_m_points;
            this.oA = oA;
            this.oA_min = oA_min;
            this.oB_plus = oB_plus;
            this.oB = oB;
            this.oB_min = oB_min;
            this.oC_plus = oC_plus;
            this.oC = oC;
            this.oC_min = oC_min;
            this.oD_plus = oD_plus;
            this.oD = oD;
            this.oD_min = oD_min;
            this.oE = oE;
            this.oX = oX;
            this.oY = oY;
        }

        public Object[] SubjectArrayDetails() {
            return new Object[] { name, mean, null, entry, b_entry, bA, bA_min, bB_plus, bB, bB_min, bC_plus, bC,
                    bC_min, bD_plus, bD, bD_min, bE, bX, bY, b_m_points, g_entry, gA, gA_min, gB_plus, gB, gB_min,
                    gC_plus, gC, gC_min, gD_plus, gD, gD_min, gE, gX, gY, g_m_points, oA, oA_min, oB_plus, oB, oB_min,
                    oC_plus, oC, oC_min, oD_plus, oD, oD_min, oE, oX, oY };
        }

        public String getName() {
            return name;
        }

        public String getMean() {
            return mean;
        }

        public String getPosition() {
            return position;
        }

        public int getEntry() {
            return entry;
        }

        public int getB_entry() {
            return b_entry;
        }

        public int getbA() {
            return bA;
        }

        public int getbA_min() {
            return bA_min;
        }

        public int getbB_plus() {
            return bB_plus;
        }

        public int getbB() {
            return bB;
        }

        public int getbB_min() {
            return bB_min;
        }

        public int getbC_plus() {
            return bC_plus;
        }

        public int getbC() {
            return bC;
        }

        public int getbC_min() {
            return bC_min;
        }

        public int getbD_plus() {
            return bD_plus;
        }

        public int getbD() {
            return bD;
        }

        public int getbD_min() {
            return bD_min;
        }

        public int getbE() {
            return bE;
        }

        public int getbX() {
            return bX;
        }

        public int getbY() {
            return bY;
        }

        public String getB_m_points() {
            return b_m_points;
        }

        public int getG_entry() {
            return g_entry;
        }

        public int getgA() {
            return gA;
        }

        public int getgA_min() {
            return gA_min;
        }

        public int getgB_plus() {
            return gB_plus;
        }

        public int getgB() {
            return gB;
        }

        public int getgB_min() {
            return gB_min;
        }

        public int getgC_plus() {
            return gC_plus;
        }

        public int getgC() {
            return gC;
        }

        public int getgC_min() {
            return gC_min;
        }

        public int getgD_plus() {
            return gD_plus;
        }

        public int getgD() {
            return gD;
        }

        public int getgD_min() {
            return gD_min;
        }

        public int getgE() {
            return gE;
        }

        public int getgX() {
            return gX;
        }

        public int getgY() {
            return gY;
        }

        public String getG_m_points() {
            return g_m_points;
        }

        public int getoA() {
            return oA;
        }

        public int getoA_min() {
            return oA_min;
        }

        public int getoB_plus() {
            return oB_plus;
        }

        public int getoB() {
            return oB;
        }

        public int getoB_min() {
            return oB_min;
        }

        public int getoC_plus() {
            return oC_plus;
        }

        public int getoC() {
            return oC;
        }

        public int getoC_min() {
            return oC_min;
        }

        public int getoD_plus() {
            return oD_plus;
        }

        public int getoD() {
            return oD;
        }

        public int getoD_min() {
            return oD_min;
        }

        public int getoE() {
            return oE;
        }

        public int getoX() {
            return oX;
        }

        public int getoY() {
            return oY;
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OpenProjectDLG = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_projects_list = new javax.swing.JTable();
        NewProjectDLG = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtProjectTitle = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_no_of_subjects = new javax.swing.JTextField();
        SelectStudentsDLG = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_students = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        comboForm = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ProgressNo = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        progressDlg = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        ProgressNo1 = new javax.swing.JLabel();
        ViewReportsDlg = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        OpenProjectDLG.setTitle("Select project");
        OpenProjectDLG.setType(java.awt.Window.Type.UTILITY);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        table_projects_list.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        table_projects_list.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

        }, new String[] { "Project", "Title" }) {
            boolean[] canEdit = new boolean[] { false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table_projects_list.setFillsViewportHeight(true);
        table_projects_list.setGridColor(new java.awt.Color(204, 204, 204));
        table_projects_list.setIntercellSpacing(new java.awt.Dimension(2, 5));
        table_projects_list.setRowHeight(30);
        table_projects_list.setSelectionBackground(new java.awt.Color(204, 204, 204));
        table_projects_list.setSelectionForeground(new java.awt.Color(0, 51, 255));
        table_projects_list.setShowVerticalLines(false);
        table_projects_list.setSurrendersFocusOnKeystroke(true);
        table_projects_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_projects_listMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_projects_list);
        if (table_projects_list.getColumnModel().getColumnCount() > 0) {
            table_projects_list.getColumnModel().getColumn(0).setPreferredWidth(10);
            table_projects_list.getColumnModel().getColumn(1).setPreferredWidth(350);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));
        jPanel6Layout
                .setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));

        javax.swing.GroupLayout OpenProjectDLGLayout = new javax.swing.GroupLayout(OpenProjectDLG.getContentPane());
        OpenProjectDLG.getContentPane().setLayout(OpenProjectDLGLayout);
        OpenProjectDLGLayout
                .setHorizontalGroup(OpenProjectDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
        OpenProjectDLGLayout
                .setVerticalGroup(OpenProjectDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

        NewProjectDLG.setTitle("Enter Project Title");
        NewProjectDLG.setType(java.awt.Window.Type.UTILITY);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        jLabel1.setText("Project Title");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Next  >");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel7.setText("How many subjects are offered at KCSE by the School");

        txt_no_of_subjects.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_no_of_subjectsKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txt_no_of_subjects)
                        .addComponent(txtProjectTitle)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1).addComponent(jLabel7,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 288,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 160, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                jPanel5Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
                                        jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap()));
        jPanel5Layout
                .setVerticalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtProjectTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18).addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_no_of_subjects, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(70, Short.MAX_VALUE)));

        javax.swing.GroupLayout NewProjectDLGLayout = new javax.swing.GroupLayout(NewProjectDLG.getContentPane());
        NewProjectDLG.getContentPane().setLayout(NewProjectDLGLayout);
        NewProjectDLGLayout.setHorizontalGroup(
                NewProjectDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel5,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        NewProjectDLGLayout
                .setVerticalGroup(NewProjectDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(NewProjectDLGLayout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        SelectStudentsDLG.setTitle("Select students");
        SelectStudentsDLG.setType(java.awt.Window.Type.UTILITY);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        jCheckBox1.setText("Select all");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Find By Admission Number or name");

        table_students.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        table_students.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

        }, new String[] { "#", "ADM", "Index No.", "NAME", "Gender" }) {
            Class[] types = new Class[] { java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                    java.lang.Object.class, java.lang.Object.class };
            boolean[] canEdit = new boolean[] { true, false, true, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        table_students.setFillsViewportHeight(true);
        table_students.setGridColor(new java.awt.Color(204, 204, 204));
        table_students.setIntercellSpacing(new java.awt.Dimension(2, 5));
        table_students.setRowHeight(25);
        table_students.setSelectionBackground(new java.awt.Color(204, 204, 204));
        table_students.setSelectionForeground(new java.awt.Color(0, 51, 255));
        table_students.setSurrendersFocusOnKeystroke(true);
        jScrollPane2.setViewportView(table_students);
        if (table_students.getColumnModel().getColumnCount() > 0) {
            table_students.getColumnModel().getColumn(0).setPreferredWidth(5);
            table_students.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_students.getColumnModel().getColumn(2).setPreferredWidth(50);
            table_students.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_students.getColumnModel().getColumn(4).setHeaderValue("Gender");
        }

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))); // NOI18N
        jButton2.setText("Add");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 204), 1, true));
        jButton2.setContentAreaFilled(false);
        jButton2.setOpaque(true);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select class" }));
        comboForm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }

            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFormPopupMenuWillBecomeInvisible(evt);
            }

            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel3.setText("Find By Class");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 51));
        jLabel5.setText("Updating Students details for this exams please wait...");

        ProgressNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ProgressNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProgressNo.setText("NO");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                        jPanel4Layout.createSequentialGroup().addGap(29, 29, 29)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 270,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ProgressNo, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                        jPanel4Layout.createSequentialGroup().addGap(20, 20, 20).addComponent(
                                                jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 754,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createSequentialGroup().addGap(20, 20, 20).addGroup(jPanel4Layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 772,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(comboForm, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 189,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel4Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextField2).addComponent(jLabel4,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 233,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(14, Short.MAX_VALUE)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3).addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup().addGap(15, 15, 15)
                                        .addGroup(jPanel4Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel5).addComponent(ProgressNo,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 15,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jProgressBar1,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)));

        javax.swing.GroupLayout SelectStudentsDLGLayout = new javax.swing.GroupLayout(
                SelectStudentsDLG.getContentPane());
        SelectStudentsDLG.getContentPane().setLayout(SelectStudentsDLGLayout);
        SelectStudentsDLGLayout.setHorizontalGroup(
                SelectStudentsDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SelectStudentsDLGLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        SelectStudentsDLGLayout
                .setVerticalGroup(SelectStudentsDLGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SelectStudentsDLGLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        progressDlg.setUndecorated(true);
        progressDlg.setResizable(false);
        progressDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));

        jProgressBar2.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar2.setString("");
        jProgressBar2.setStringPainted(true);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 51));
        jLabel6.setText("Updating Students details for this exams please wait...");

        ProgressNo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ProgressNo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProgressNo1.setText("NO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup().addGap(114, 114, 114)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 270,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ProgressNo1, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 527,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6).addComponent(ProgressNo1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jProgressBar2,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()));

        javax.swing.GroupLayout progressDlgLayout = new javax.swing.GroupLayout(progressDlg.getContentPane());
        progressDlg.getContentPane().setLayout(progressDlgLayout);
        progressDlgLayout.setHorizontalGroup(
                progressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3,
                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE));
        progressDlgLayout.setVerticalGroup(
                progressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel3,
                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE));

        ViewReportsDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setText("School Mean (Grade distribution)");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton6.setContentAreaFilled(false);
        jButton6.setOpaque(true);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Subject Means (Gender)");
        jButton7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton7.setContentAreaFilled(false);
        jButton7.setOpaque(true);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Top 10 Boys");
        jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton8.setContentAreaFilled(false);
        jButton8.setOpaque(true);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Top 10 Girls");
        jButton9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton9.setContentAreaFilled(false);
        jButton9.setOpaque(true);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Top 10 Overall");
        jButton10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton10.setContentAreaFilled(false);
        jButton10.setOpaque(true);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Subjects Ranking");
        jButton11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton11.setContentAreaFilled(false);
        jButton11.setOpaque(true);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup().addGap(18, 18, 18).addGroup(jPanel7Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18).addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 298,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                jPanel7Layout.createSequentialGroup()
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 200,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(20, 20, 20)));

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
                new java.awt.Component[] { jButton10, jButton8, jButton9 });

        jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup().addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32).addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE)));

        javax.swing.GroupLayout ViewReportsDlgLayout = new javax.swing.GroupLayout(ViewReportsDlg.getContentPane());
        ViewReportsDlg.getContentPane().setLayout(ViewReportsDlgLayout);
        ViewReportsDlgLayout.setHorizontalGroup(ViewReportsDlgLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel7,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        ViewReportsDlgLayout.setVerticalGroup(ViewReportsDlgLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel7,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Custom Exams Analysis");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 255, 204));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

        }, new String[] {

        }));
        jTable1.setFillsViewportHeight(true);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowHeight(25);
        jScrollPane3.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Marks Entry");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                        jPanel2Layout.createSequentialGroup().addContainerGap()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                .addComponent(jScrollPane3));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane3,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_16x16.png"))); // NOI18N
        jButton3.setText("View Reports");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0), 2));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setIconTextGap(10);
        jButton3.setOpaque(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        jButton4.setText("Save");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0), 2));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setIconTextGap(10);
        jButton4.setOpaque(true);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        jButton5.setText("Add Students");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0), 2));
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setIconTextGap(10);
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout
                .setHorizontalGroup(
                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap())
                                .addGroup(jPanel1Layout.createSequentialGroup().addGap(95, 95, 95)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 134,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 148,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(262, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(16, 16, 16)));

        jMenu1.setText("Projects");

        jMenuItem1.setText("New Project");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Open Project");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
        NewProjectDLG.pack();
        NewProjectDLG.setLocationRelativeTo(this);
        NewProjectDLG.setVisible(true);
    }// GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
        OpenProjectDLG.pack();
        OpenProjectDLG.setLocationRelativeTo(this);
        OpenProjectDLG.setVisible(true);

        model1 = (DefaultTableModel) table_projects_list.getModel();
        model1.setRowCount(0);
        try {
            pst = Conn.prepareStatement("SELECT * FROM tbl_kcse_year");
            rs = pst.executeQuery();
            while (rs.next()) {
                model1.addRow(new Object[] { rs.getString("id"), rs.getString("project_title") });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_jMenuItem2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        project_title = txtProjectTitle.getText();
        no_of_subjects = Integer.parseInt(txt_no_of_subjects.getText());
        if (no_of_subjects < 7 || no_of_subjects > 15) {
            JOptionPane.showMessageDialog(this, "The system takes atleast 7 subjects and at most 14 Subbjects");
            return;
        }
        if (project_title.equals("")) {
            JOptionPane.showMessageDialog(this, "Enter the title of the new project u want to create");
            return;
        }
        int total_rows = ConnClass
                .CountRows("SELECT Count(*) AS total FROM tbl_kcse_year WHERE project_title='" + project_title + "'");
        if (total_rows == 0) {
            if (ConnClass.Query("insert into tbl_kcse_year (project_title) values ('" + project_title + "')")) {
                this.setTitle(project_title);
                project_title = txtProjectTitle.getText();
                jLabel2.setText(project_title + " Grades Entry Panel");

                NewProjectDLG.setVisible(false);
                SelectStudentsDLG.pack();
                SelectStudentsDLG.setLocationRelativeTo(this);
                SelectStudentsDLG.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "This project name is already used");
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void comboFormPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {// GEN-FIRST:event_comboFormPopupMenuWillBecomeInvisible
        String classname = comboForm.getSelectedItem().toString();
        getStudents(classname);
    }// GEN-LAST:event_comboFormPopupMenuWillBecomeInvisible

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBox1ActionPerformed
        int rows = model2.getRowCount();
        if (jCheckBox1.isSelected()) {
            for (int i = 0; i < rows; i++) {
                model2.setValueAt(true, i, 0);
            }
        } else {
            for (int i = 0; i < rows; i++) {
                model2.setValueAt(false, i, 0);
            }
        }
    }// GEN-LAST:event_jCheckBox1ActionPerformed

    private void table_projects_listMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_table_projects_listMouseClicked
        project_title = model1.getValueAt(table_projects_list.getSelectedRow(), 1).toString();
        GetStudentsInExamsTable();
        jLabel2.setText(project_title + " Grades Entry Panel");
        this.setTitle(project_title);
        OpenProjectDLG.setVisible(false);
    }// GEN-LAST:event_table_projects_listMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        int rows = model2.getRowCount();
        int total_selected = 0;
        if (rows <= 0) {
            JOptionPane.showMessageDialog(this, "There are not students on the list of selection", "NO Students",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < rows; i++) {
            if ((boolean) model2.getValueAt(i, 0)) {
                total_selected++;
            }
        }
        if (total_selected <= 0) {
            JOptionPane.showMessageDialog(this, "You have not selected any student to add", "NO Student Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        worker = new SwingWorker<Void, Integer>() {
            int Total = 0;

            @Override
            protected Void doInBackground() throws Exception {
                jProgressBar1.setValue(0);
                ProgressNo.setText("0%");
                jLabel5.setVisible(true);
                jProgressBar1.setVisible(true);
                ProgressNo.setVisible(true);
                ArrayList<String[]> subjects = new ArrayList<>();
                try {
                    pst = Conn.prepareStatement("SELECT * FROM subjects");
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        String[] subjectsDetails = { rs.getString("S_NO"), rs.getString("Subject_Code"),
                                rs.getString("Subject_name") };
                        subjects.add(subjectsDetails);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < rows; i++) {
                    if ((boolean) model2.getValueAt(i, 0)) {
                        Total++;
                    }
                }
                int count = 0;
                for (int i = 0; i < rows; i++) {
                    String adm = model2.getValueAt(i, 1).toString();
                    String index_no = model2.getValueAt(i, 2).toString();
                    String name = model2.getValueAt(i, 3).toString();
                    String sex = model2.getValueAt(i, 4).toString();

                    if ((boolean) model2.getValueAt(i, 0)) {
                        try {
                            int total_rows = ConnClass
                                    .CountRows("SELECT count(*) as total FROM tbl_kcse_marks WHERE (project_title='"
                                            + project_title + "' AND adm='" + adm + "')");
                            if (total_rows == 0) {
                                pst = Conn.prepareStatement(
                                        "INSERT INTO tbl_kcse_marks (project_title,adm,index_no,name,sex) values (?,?,?,?,?)");
                                pst.setString(1, project_title);
                                pst.setString(2, adm);
                                pst.setString(3, index_no);
                                pst.setString(4, name);
                                pst.setString(5, sex);
                                pst.executeUpdate();
                            }
                            // loop through the subjects and update subject details
                            // loop subjects
                            for (String[] subject : subjects) {
                                total_rows = ConnClass.CountRows(
                                        "SELECT count(*) as total FROM tblstudents_subjects WHERE SS_Student_id='" + adm
                                                + "' AND SS_Subject_code='" + subject[1] + "'");
                                if (total_rows > 0) {
                                    String s_code = "sub_" + subject[0] + "_C", s_name = "sub_" + subject[0] + "_N";
                                    pst = Conn.prepareStatement("UPDATE tbl_kcse_marks SET " + s_code + "=?," + s_name
                                            + "=?" + " WHERE adm='" + adm + "' AND project_title='" + project_title
                                            + "'");
                                    pst.setString(1, subject[1]);
                                    pst.setString(2, subject[2]);
                                    pst.executeUpdate();
                                }
                            }
                            count++;
                            publish(count);
                        } catch (Exception ex) {
                            ConnClass.printError(ex);
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Students addded successfully", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                GetStudentsInExamsTable();
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size() - 1);
                ProgressNo.setText((int) ((progress / (Total * 1.0)) * 100) + "%");
                jProgressBar1.setValue((int) ((progress / (Total * 1.0)) * 100));
            }

            @Override
            protected void done() {
                jLabel5.setVisible(false);
                jProgressBar1.setVisible(false);
                ProgressNo.setVisible(false);

                SelectStudentsDLG.dispose();
            }
        };

        worker.execute();
    }// GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        if (project_title.equals("")) {
            JOptionPane.showMessageDialog(this, "There is no open project. Create or open a project first", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            SelectStudentsDLG.pack();
            SelectStudentsDLG.setLocationRelativeTo(this);
            SelectStudentsDLG.setVisible(true);
        }
    }// GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        try {
            Conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }// GEN-LAST:event_formWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        jButton4.setEnabled(false);
        if (project_title.equals("")) {
            JOptionPane.showMessageDialog(this, "There is no open project. Create or open a project first", "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        model3 = (DefaultTableModel) jTable1.getModel();
        int rows = model3.getRowCount();
        ArrayList<String> adms = new ArrayList<>();
        ArrayList<String[]> grades = new ArrayList<>();
        ArrayList<String[]> points = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            adms.add(model3.getValueAt(i, 0).toString());
            String[] subjects_grades = new String[14];
            String[] subjects_points = new String[14];

            for (int j = 0; j < 14; j++) {
                subjects_grades[j] = "";
                subjects_points[j] = "";
            }
            for (int j = 0; j < model3.getColumnCount() - 3; j++) {
                subjects_grades[j] = String.valueOf(model3.getValueAt(i, j + 3).toString().toUpperCase());
                subjects_points[j] = toPoints(subjects_grades[j]);
            }
            grades.add(subjects_grades);
            points.add(subjects_points);
        }

        if (rows <= 0) {
            return;
        }

        progressDlg.pack();
        progressDlg.setLocationRelativeTo(this);
        progressDlg.setVisible(true);
        worker = new SwingWorker<Void, Integer>() {

            int Total = grades.size();

            @Override
            protected Void doInBackground() throws Exception {
                jLabel6.setText("Saving marks...");
                jProgressBar2.setValue(0);
                jLabel6.setVisible(true);
                jProgressBar2.setVisible(true);
                ProgressNo1.setVisible(true);
                try {
                    for (int i = 0; i < grades.size(); i++) {
                        String adm = adms.get(i);
                        String[] subjects_points = points.get(i);
                        String[] subjects_grades = grades.get(i);
                        // Grading starts here

                        String Total_Points = "Y";
                        int GroupA = 0;
                        int FirstOfGroupBPicked = 0;
                        int GroupBRemainder = 0;
                        int FirstOfGroupCPicked = 0;
                        int GroupCRemainders = 0;
                        int GroupD;
                        int GroupCRemaindersToGroupD = 0;
                        int GroupBRemainderToGroupCToGroupD = 0;

                        int S1 = 0;
                        if (!(subjects_points[0] == null || subjects_points[0].isEmpty())) {
                            S1 = Integer.parseInt(subjects_points[0]);
                        }
                        int S2 = 0;
                        if (!(subjects_points[1] == null || subjects_points[1].isEmpty())) {
                            S2 = Integer.parseInt(subjects_points[1]);
                        }
                        int S3 = 0;
                        if (!(subjects_points[2] == null || subjects_points[2].isEmpty())) {
                            S3 = Integer.parseInt(subjects_points[2]);
                        }
                        int S4 = 0;
                        if (!(subjects_points[3] == null || subjects_points[3].isEmpty())) {
                            S4 = Integer.parseInt(subjects_points[3]);
                        }
                        int S5 = 0;
                        if (!(subjects_points[4] == null || subjects_points[4].isEmpty())) {
                            S5 = Integer.parseInt(subjects_points[4]);
                        }
                        int S6 = 0;
                        if (!(subjects_points[5] == null || subjects_points[5].isEmpty())) {
                            S6 = Integer.parseInt(subjects_points[5]);
                        }
                        int S7 = 0;
                        if (!(subjects_points[5] == null || subjects_points[6].isEmpty())) {
                            S7 = Integer.parseInt(subjects_points[6]);
                        }
                        int S8 = 0;
                        if (!(subjects_points[7] == null || subjects_points[7].isEmpty())) {
                            S8 = Integer.parseInt(subjects_points[7]);
                        }
                        int S9 = 0;
                        if (!(subjects_points[8] == null || subjects_points[8].isEmpty())) {
                            S9 = Integer.parseInt(subjects_points[8]);
                        }
                        int S10 = 0;
                        if (!(subjects_points[9] == null || subjects_points[9].isEmpty())) {
                            S10 = Integer.parseInt(subjects_points[9]);
                        }
                        int S11 = 0;
                        if (!(subjects_points[10] == null || subjects_points[10].isEmpty())) {
                            S11 = Integer.parseInt(subjects_points[10]);
                        }
                        int S12 = 0;
                        if (!(subjects_points[11] == null || subjects_points[11].isEmpty())) {
                            S12 = Integer.parseInt(subjects_points[11]);
                        }
                        int S13 = 0;
                        if (!(subjects_points[12] == null || subjects_points[12].isEmpty())) {
                            S13 = Integer.parseInt(subjects_points[12]);
                        }
                        int S14 = 0;
                        if (!(subjects_points[13] == null || subjects_points[13].isEmpty())) {
                            S14 = Integer.parseInt(subjects_points[13]);
                        }

                        GroupA = S1 + S2 + S3;
                        GroupD = S10 + S11 + S12 + S13 + S14;
                        // start from here

                        if ((S4 <= S5) && (S4 <= S6)) {
                            FirstOfGroupBPicked = S5 + S6;
                            GroupBRemainder = S4;
                        } else if ((S5 <= S4) && (S5 <= S6)) {
                            FirstOfGroupBPicked = S4 + S6;
                            GroupBRemainder = S5;
                        } else if ((S6 <= S4) && (S6 <= S5)) {
                            FirstOfGroupBPicked = S4 + S5;
                            GroupBRemainder = S6;
                        }
                        if ((S7 >= S8) && (S7 >= S9)) {
                            FirstOfGroupCPicked = S7;
                            GroupCRemainders = S8 + S9;
                        } else if ((S8 >= S7) && (S8 >= S9)) {
                            FirstOfGroupCPicked = S8;
                            GroupCRemainders = S7 + S9;
                        } else if ((S9 >= S7) && (S9 >= S8)) {
                            FirstOfGroupCPicked = S9;
                            GroupCRemainders = S7 + S8;
                        }
                        if (GroupCRemainders >= GroupD) {
                            GroupCRemaindersToGroupD = GroupCRemainders;
                        } else if (GroupD >= GroupCRemainders) {
                            GroupCRemaindersToGroupD = GroupD;
                        }
                        if ((GroupBRemainder <= FirstOfGroupCPicked) && (GroupBRemainder <= GroupCRemaindersToGroupD)) {
                            GroupBRemainderToGroupCToGroupD = FirstOfGroupCPicked + GroupCRemaindersToGroupD;
                        } else if ((FirstOfGroupCPicked <= GroupBRemainder)
                                && (FirstOfGroupCPicked <= GroupCRemaindersToGroupD)) {
                            GroupBRemainderToGroupCToGroupD = GroupBRemainder + GroupCRemaindersToGroupD;
                        } else if ((GroupCRemaindersToGroupD <= GroupBRemainder)
                                && (GroupCRemaindersToGroupD <= FirstOfGroupCPicked)) {
                            GroupBRemainderToGroupCToGroupD = GroupBRemainder + FirstOfGroupCPicked;
                        }
                        int Points = GroupA + FirstOfGroupBPicked + GroupBRemainderToGroupCToGroupD;
                        if (Points > 0) {
                            Total_Points = String.valueOf(Points);
                        }
                        String MP = getMeanP(Total_Points), MG = getMeanG(Total_Points);
                        pst = Conn.prepareStatement("UPDATE tbl_kcse_marks SET " + "sub_1_G=?,sub_1_P=?,"
                                + "sub_2_G=?,sub_2_P=?," + "sub_3_G=?,sub_3_P=?," + "sub_4_G=?,sub_4_P=?,"
                                + "sub_5_G=?,sub_5_P=?," + "sub_6_G=?,sub_6_P=?," + "sub_7_G=?,sub_7_P=?,"
                                + "sub_8_G=?,sub_8_P=?," + "sub_9_G=?,sub_9_P=?," + "sub_10_G=?,sub_10_P=?,"
                                + "sub_11_G=?,sub_11_P=?," + "sub_12_G=?,sub_12_P=?," + "sub_13_G=?,sub_13_P=?,"
                                + "sub_14_G=?,sub_14_P=?," + "total_p=?,mean_P=?,mean_G=? WHERE " + "adm='" + adm
                                + "' AND project_title='" + project_title + "' ");

                        for (int m = 1; m < 29; m++) {
                            if (m % 2 == 0) {
                                pst.setString(m, subjects_points[(m - 2) / 2]);
                            } else {
                                pst.setString(m, subjects_grades[(m - 1) / 2]);
                            }
                        }
                        pst.setString(29, Total_Points);
                        pst.setString(30, MP);
                        pst.setString(31, MG);
                        pst.executeUpdate();
                        publish(i + 1);
                    }
                    jLabel6.setText("Calculating Positions...");
                    CalculateStudentsPositions();
                    JOptionPane.showMessageDialog(null, "Grades saved and analysed successfully", "Info",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size() - 1);
                ProgressNo1.setText((int) ((progress / (Total * 1.0)) * 100) + "%");
                jProgressBar2.setValue((int) ((progress / (Total * 1.0)) * 100));
            }

            @Override
            protected void done() {
                progressDlg.dispose();
            }
        };
        worker.execute();

    }// GEN-LAST:event_jButton4ActionPerformed

    private void txt_no_of_subjectsKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_no_of_subjectsKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }// GEN-LAST:event_txt_no_of_subjectsKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        if (project_title.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not opened any project", "Acme",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        ViewReportsDlg.pack();
        ViewReportsDlg.setLocationRelativeTo(this);
        ViewReportsDlg.setVisible(true);

    }// GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/School_Mean.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/Subject_gender.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/TopGender.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            param.put("gender_name", "Boys");
            param.put("gender", "Male");
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton9ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/TopGender.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            param.put("gender_name", "Girls");
            param.put("gender", "Female");
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/TopOverall.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton11ActionPerformed
        InputStream Report = null;
        try {
            Report = getClass().getClassLoader().getResourceAsStream("reports/kcse/Subject_Ranking.jrxml");
            HashMap<String, Object> param = new HashMap<>();
            param.put("project_title", project_title);
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame();
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.UTILITY);
            jf.validate();
            jf.setVisible(true);
            jf.setSize(new Dimension(1200, 700));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setTitle("Report");
        } catch (HeadlessException | JRException e) {
            System.out.println(e);
        }
    }// GEN-LAST:event_jButton11ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KCSE_AnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KCSE_AnalysisFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog NewProjectDLG;
    private javax.swing.JDialog OpenProjectDLG;
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JLabel ProgressNo1;
    private javax.swing.JDialog SelectStudentsDLG;
    private javax.swing.JDialog ViewReportsDlg;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JDialog progressDlg;
    private javax.swing.JTable table_projects_list;
    private javax.swing.JTable table_students;
    private javax.swing.JTextField txtProjectTitle;
    private javax.swing.JTextField txt_no_of_subjects;
    // End of variables declaration//GEN-END:variables

}
