package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class MarksRecordFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null, pst1 = null;
    ResultSet rs = null, rs1 = null;
    String sql = null, SubjectCode = "", sql1 = null;
    String SubjectName,SubjectNumber,SubjectTeacher,TeacherINIT;
    Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    int Grade1, Grade2, Grade3, Grade4, Grade5, Grade6, Grade7, Grade8, Grade9, Grade10, Grade11;

    ;

    
    public MarksRecordFrm() {
        initComponents();

        new ConnClass().setFrameIcon(this);
        getForm();
        getSubjectCode();
        GetAcedmicYears();

    }



    private void GetAcedmicYears() {
        cmbYear.removeAllItems();
        cmbYear.addItem("SELECT");
        try {
            pst = Conn.prepareStatement("SELECT * FROM students_exams group by Year ORDER BY Year DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                String classname = rs.getString("Year");
                cmbYear.addItem(classname);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void getForm() {
        try {
            sql = "SELECT * FROM tblClasses ORDER BY Class_name ASC";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                comboForm.addItem(rs.getString("Class_name"));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void getSubjectCode() {
        try {
            sql = "SELECT * FROM subjects ORDER BY (S_NO+0) ASC";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                comboCode.addItem(rs.getString("Subject_code"));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void updateGradingSystem(String SubjectCode) {
        try {
            sql = "SELECT * FROM Subjects WHERE Subject_Code='" + SubjectCode + "'";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                SubjectName = rs.getString("Subject_name");
                SubjectNumber = rs.getString("S_NO");
                txtGrade1.setText("100 -" + rs.getString("Grade1"));
                txtGrade2.setText(Integer.parseInt(rs.getString("Grade1")) - 1 + " - " + rs.getString("Grade2"));
                txtGrade3.setText(Integer.parseInt(rs.getString("Grade2")) - 1 + " - " + rs.getString("Grade3"));
                txtGrade4.setText(Integer.parseInt(rs.getString("Grade3")) - 1 + " - " + rs.getString("Grade4"));
                txtGrade5.setText(Integer.parseInt(rs.getString("Grade4")) - 1 + " - " + rs.getString("Grade5"));
                txtGrade6.setText(Integer.parseInt(rs.getString("Grade5")) - 1 + " - " + rs.getString("Grade6"));
                txtGrade7.setText(Integer.parseInt(rs.getString("Grade6")) - 1 + " - " + rs.getString("Grade7"));
                txtGrade8.setText(Integer.parseInt(rs.getString("Grade7")) - 1 + " - " + rs.getString("Grade8"));
                txtGrade9.setText(Integer.parseInt(rs.getString("Grade8")) - 1 + " - " + rs.getString("Grade9"));
                txtGrade10.setText(Integer.parseInt(rs.getString("Grade9")) - 1 + " - " + rs.getString("Grade10"));
                txtGrade11.setText(Integer.parseInt(rs.getString("Grade10")) - 1 + " - " + rs.getString("Grade11"));
                txtGrade12.setText(Integer.parseInt(rs.getString("Grade11")) - 1 + " - " + rs.getString("Grade12"));

                Grade1 = Integer.parseInt(rs.getString("Grade1"));
                Grade2 = Integer.parseInt(rs.getString("Grade2"));
                Grade3 = Integer.parseInt(rs.getString("Grade3"));
                Grade4 = Integer.parseInt(rs.getString("Grade4"));
                Grade5 = Integer.parseInt(rs.getString("Grade5"));
                Grade6 = Integer.parseInt(rs.getString("Grade6"));
                Grade7 = Integer.parseInt(rs.getString("Grade7"));
                Grade8 = Integer.parseInt(rs.getString("Grade8"));
                Grade9 = Integer.parseInt(rs.getString("Grade9"));
                Grade10 = Integer.parseInt(rs.getString("Grade10"));
                Grade11 = Integer.parseInt(rs.getString("Grade11"));

            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    private void retriveSavedMarks(String Form, String Year, String Term, String Exam, String SubjectNumber, String SubjectCode) {
        DefaultTableModel model = (DefaultTableModel) tblMarks.getModel();
        model.setRowCount(0);
        try {
            if (Exam.equalsIgnoreCase("EXAM 1")) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                switch (SubjectNumber) {
                    case "1":
                        sql = "select student_details.*,students_exams.S1E1Marks as  Marks,students_exams.S1E1Points as Points,students_exams.S1E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E1Marks as Marks,students_exams.S2E1Points as Points,students_exams.S2E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E1Marks as Marks,students_exams.S3E1Points as Points,students_exams.S3E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E1Marks as Marks,students_exams.S4E1Points as Points,students_exams.S4E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E1Marks as Marks,students_exams.S5E1Points as Points,students_exams.S5E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E1Marks as Marks,students_exams.S6E1Points as Points,students_exams.S6E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E1Marks as Marks,students_exams.S7E1Points as Points,students_exams.S7E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E1Marks as Marks,students_exams.S8E1Points as Points,students_exams.S8E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E1Marks as Marks,students_exams.S9E1Points as Points,students_exams.S9E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E1Marks as Marks,students_exams.S10E1Points as Points,students_exams.S10E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E1Marks as Marks,students_exams.S11E1Points as Points,students_exams.S11E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E1Marks as Marks,students_exams.S12E1Points as Points,students_exams.S12E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E1Marks as Marks,students_exams.S13E1Points as Points,students_exams.S13E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E1Marks as Marks,students_exams.S14E1Points as Points,students_exams.S14E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    default:
                        break;
                }
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String admno = rs.getString("Student_id");
                    String name = rs.getString("Student_name");
                    String marks = rs.getString("Marks");
                    String grade = rs.getString("Grade");
                    String points = rs.getString("Points");
                    model.addRow(new Object[]{admno, name, marks, grade, points});
                }
//</editor-fold>                
            } else if (Exam.equalsIgnoreCase("EXAM 2")) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                switch (SubjectNumber) {
                    case "1":
                        sql = "select student_details.*,students_exams.S1E2Marks as  Marks,students_exams.S1E2Points as Points,students_exams.S1E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E2Marks as Marks,students_exams.S2E2Points as Points,students_exams.S2E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E2Marks as Marks,students_exams.S3E2Points as Points,students_exams.S3E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E2Marks as Marks,students_exams.S4E2Points as Points,students_exams.S4E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E2Marks as Marks,students_exams.S5E2Points as Points,students_exams.S5E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E2Marks as Marks,students_exams.S6E2Points as Points,students_exams.S6E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E2Marks as Marks,students_exams.S7E2Points as Points,students_exams.S7E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E2Marks as Marks,students_exams.S8E2Points as Points,students_exams.S8E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E2Marks as Marks,students_exams.S9E2Points as Points,students_exams.S9E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E2Marks as Marks,students_exams.S10E2Points as Points,students_exams.S10E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E2Marks as Marks,students_exams.S11E2Points as Points,students_exams.S11E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E2Marks as Marks,students_exams.S12E2Points as Points,students_exams.S12E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E2Marks as Marks,students_exams.S13E2Points as Points,students_exams.S13E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E2Marks as Marks,students_exams.S14E2Points as Points,students_exams.S14E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    default:
                        break;
                }
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String admno = rs.getString("Student_id");
                    String name = rs.getString("Student_name");
                    String marks = rs.getString("Marks");
                    String grade = rs.getString("Grade");
                    String points = rs.getString("Points");
                    model.addRow(new Object[]{admno, name, marks, grade, points});
                }
//</editor-fold>             
            } else if (Exam.equalsIgnoreCase("EXAM 3")) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                switch (SubjectNumber) {
                    case "1":
                        sql = "select student_details.*,students_exams.S1E3Marks as  Marks,students_exams.S1E3Points as Points,students_exams.S1E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E3Marks as Marks,students_exams.S2E3Points as Points,students_exams.S2E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E3Marks as Marks,students_exams.S3E3Points as Points,students_exams.S3E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E3Marks as Marks,students_exams.S4E3Points as Points,students_exams.S4E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E3Marks as Marks,students_exams.S5E3Points as Points,students_exams.S5E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E3Marks as Marks,students_exams.S6E3Points as Points,students_exams.S6E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E3Marks as Marks,students_exams.S7E3Points as Points,students_exams.S7E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E3Marks as Marks,students_exams.S8E3Points as Points,students_exams.S8E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E3Marks as Marks,students_exams.S9E3Points as Points,students_exams.S9E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E3Marks as Marks,students_exams.S10E3Points as Points,students_exams.S10E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E3Marks as Marks,students_exams.S11E3Points as Points,students_exams.S11E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E3Marks as Marks,students_exams.S12E3Points as Points,students_exams.S12E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E3Marks as Marks,students_exams.S13E3Points as Points,students_exams.S13E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E3Marks as Marks,students_exams.S14E3Points as Points,students_exams.S14E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + Form + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    default:
                        break;
                }
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String admno = rs.getString("Student_id");
                    String name = rs.getString("Student_name");
                    String marks = rs.getString("Marks");
                    String grade = rs.getString("Grade");
                    String points = rs.getString("Points");
                    model.addRow(new Object[]{admno, name, marks, grade, points});
                }
//</editor-fold>             
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void backgroundSaveMarks() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = tblMarks.getRowCount();
            @Override
            protected Void doInBackground() throws Exception {
                String Year = cmbYear.getSelectedItem().toString();
                String Form = comboForm.getSelectedItem().toString();
                String Term = comboTerm.getSelectedItem().toString();
                String Exam = comboExam.getSelectedItem().toString();
                SubjectCode = comboCode.getSelectedItem().toString(); 
                
                //<editor-fold defaultstate="collapsed" desc="comment">
                    try {
                        int SubjectIntegerNumber = Integer.parseInt(SubjectNumber);
                        switch (SubjectIntegerNumber) {
                            case 1:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S1E1Marks=?,S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S1E2Marks=?,S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S1E3Marks=?,S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 2:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S2E1Marks=?,S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S2E2Marks=?,S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S2E3Marks=?,S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 3:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S3E1Marks=?,S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S3E2Marks=?,S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S3E3Marks=?,S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 4:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S4E1Marks=?,S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S4E2Marks=?,S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S4E3Marks=?,S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 5:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S5E1Marks=?,S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S5E2Marks=?,S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S5E3Marks=?,S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 6:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S6E1Marks=?,S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S6E2Marks=?,S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S6E3Marks=?,S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 7:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S7E1Marks=?,S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S7E2Marks=?,S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S7E3Marks=?,S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 8:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S8E1Marks=?,S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S8E2Marks=?,S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S8E3Marks=?,S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 9:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S9E1Marks=?,S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S9E2Marks=?,S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S9E3Marks=?,S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 10:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S10E1Marks=?,S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S10E2Marks=?,S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S10E3Marks=?,S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 11:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S11E1Marks=?,S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S11E2Marks=?,S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S11E3Marks=?,S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 12:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S12E1Marks=?,S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S12E2Marks=?,S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S12E3Marks=?,S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 13:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S13E1Marks=?,S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S13E2Marks=?,S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S13E3Marks=?,S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            case 14:
                                if (Exam.equalsIgnoreCase("Exam 1")) {
                                    sql = "UPDATE students_exams SET S14E1Marks=?,S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                    sql = "UPDATE students_exams SET S14E2Marks=?,S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                    sql = "UPDATE students_exams SET S14E3Marks=?,S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                                }
                                break;
                            default:
                                break;
                        }
                        pst = Conn.prepareStatement(sql);
                        int Rows = tblMarks.getRowCount();
                        for (int row = 0; row < Rows; row++) {
                            String StudentID = tblMarks.getValueAt(row, 0).toString();
                            publish(row);
                            jLabel3.setText("Saving marks for Student: "+ tblMarks.getValueAt(row, 1).toString());
                            String marksString;
                            if (tblMarks.getValueAt(row, 2) == null) {
                                marksString = "";
                            } else {
                                marksString = tblMarks.getValueAt(row, 2).toString();
                                marksString = marksString.replaceAll(" ", "");
                            }
                            pst.setString(1, marksString);
                            pst.setString(2, SubjectCode);
                            pst.setString(3, SubjectName);
                            pst.setString(4, TeacherINIT);
                            pst.setString(5, StudentID);
                            pst.setString(6, Form);
                            pst.setString(7, Year);
                            pst.setString(8, Term);
                            
                            pst.executeUpdate();
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }
//</editor-fold>
                

            //<editor-fold defaultstate="collapsed" desc="comment">
try { 
    int SubjectIntegerNumber = Integer.parseInt(SubjectNumber);
    switch (SubjectIntegerNumber) {
        //<editor-fold defaultstate="collapsed" desc="comment">
        case 1:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S1E1Points=?,S1E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S1CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S1E2Points=?,S1E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S1CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S1E3Points=?,S1E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S1CODE=?)";
            }
            break;
        case 2:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S2E1Points=?,S2E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S2CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S2E2Points=?,S2E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S2CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S2E3Points=?,S2E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S2CODE=?)";
            }
            break;
        case 3:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S3E1Points=?,S3E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S3CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S3E2Points=?,S3E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S3CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S3E3Points=?,S3E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S3CODE=?)";
            }
            break;
        case 4:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S4E1Points=?,S4E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S4CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S4E2Points=?,S4E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S4CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S4E3Points=?,S4E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S4CODE=?)";
            }
            break;
        case 5:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S5E1Points=?,S5E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S5CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S5E2Points=?,S5E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S5CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S5E3Points=?,S5E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S5CODE=?)";
            }
            break;
        case 6:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S6E1Points=?,S6E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S6CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S6E2Points=?,S6E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S6CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S6E3Points=?,S6E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S6CODE=?)";
            }
            break;
        case 7:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S7E1Points=?,S7E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S7CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S7E2Points=?,S7E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S7CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S7E3Points=?,S7E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S7CODE=?)";
            }
            break;
        case 8:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S8E1Points=?,S8E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S8CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S8E2Points=?,S8E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S8CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S8E3Points=?,S8E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S8CODE=?)";
            }
            break;
        case 9:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S9E1Points=?,S9E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S9CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S9E2Points=?,S9E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S9CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S9E3Points=?,S9E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S9CODE=?)";
            }
            break;
        case 10:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S10E1Points=?,S10E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S10CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S10E2Points=?,S10E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S10CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S10E3Points=?,S10E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S10CODE=?)";
            }
            break;
        case 11:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S11E1Points=?,S11E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S11CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S11E2Points=?,S11E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S11CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S11E3Points=?,S11E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S11CODE=?)";
            }
            break;
        case 12:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S12E1Points=?,S12E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S12CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S12E2Points=?,S12E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S12CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S12E3Points=?,S12E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S12CODE=?)";
            }
            break;
        case 13:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S13E1Points=?,S13E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S13CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S13E2Points=?,S13E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S13CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S13E3Points=?,S13E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S13CODE=?)";
            }
            break;
        case 14:
            if (Exam.equalsIgnoreCase("Exam 1")) {
                sql = "UPDATE students_exams SET S14E1Points=?,S14E1Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S14CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                sql = "UPDATE students_exams SET S14E2Points=?,S14E2Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S14CODE=?)";
            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                sql = "UPDATE students_exams SET S14E3Points=?,S14E3Grade=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=? AND S14CODE=?)";
            }
            break;
        default:
            break;
            //</editor-fold>
    }
    pst = Conn.prepareStatement(sql);
    int Rows = tblMarks.getRowCount();
    for (int row = 0; row < Rows; row++) {
        String StudentID = tblMarks.getValueAt(row, 0).toString();
        publish(row);
        jLabel3.setText("Analysing Grades and Points For: "+tblMarks.getValueAt(row, 1).toString());
        String MarkstoGrade;
        if (tblMarks.getValueAt(row, 2) == null) {
            MarkstoGrade = "";
        } else {
            MarkstoGrade = tblMarks.getValueAt(row, 2).toString();
            MarkstoGrade = MarkstoGrade.replaceAll(" ", "");
        }
        String Points;
        String Grade;
        if (MarkstoGrade.isEmpty()) {
            Points = "";
            Grade = "";
        } else {
            int Marks = Integer.parseInt(MarkstoGrade);
            if (Marks >= Grade1) {
                Points = "12";
                Grade = "A";
            } else if (Marks >= Grade2) {
                Points = "11";
                Grade = "A-";
            } else if (Marks >= Grade3) {
                Points = "10";
                Grade = "B+";
            } else if (Marks >= Grade4) {
                Points = "9";
                Grade = "B";
            } else if (Marks >= Grade5) {
                Points = "8";
                Grade = "B-";
            } else if (Marks >= Grade6) {
                Points = "7";
                Grade = "C+";
            } else if (Marks >= Grade7) {
                Points = "6";
                Grade = "C";
            } else if (Marks >= Grade8) {
                Points = "5";
                Grade = "C-";
            } else if (Marks >= Grade9) {
                Points = "4";
                Grade = "D+";
            } else if (Marks >= Grade10) {
                Points = "3";
                Grade = "D";
            } else if (Marks >= Grade11) {
                Points = "2";
                Grade = "D-";
            } else {
                Points = "1";
                Grade = "E";
            }
        }
        pst.setString(1, Points);
        pst.setString(2, Grade);
        pst.setString(3, StudentID);
        pst.setString(4, Form);
        pst.setString(5, Year);
        pst.setString(6, Term);
        pst.setString(7, SubjectCode);
        
        pst.executeUpdate();
    }
} catch (NumberFormatException | SQLException e) {
    System.out.println(e);
}
//</editor-fold>


                jLabel3.setText("Retrieving saved marks...");
                retriveSavedMarks(Form, Year, Term, Exam, SubjectNumber, SubjectCode);
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size()-1);
                ProgressNo.setText((int) ((progress/(Total * 1.0))*100) +"% Done");
                jProgressBar1.setValue((int) ((progress/(Total * 1.0))*100));
            }

            @Override
            protected void done() {
                SavingmarksProgressPane.dispose();
            }
        };
        worker.execute();
    }

    private void backgroundRetrivingStudents() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = 0;
            @Override
            protected Void doInBackground() throws Exception {
                String Year = cmbYear.getSelectedItem().toString();
                String Form = comboForm.getSelectedItem().toString();
                String Term = comboTerm.getSelectedItem().toString();
                String Exam = comboExam.getSelectedItem().toString();
                SubjectCode = comboCode.getSelectedItem().toString();
                try {
                    pst = Conn.prepareStatement("SELECT * FROM Subjects WHERE Subject_code = '" + SubjectCode + "'");
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        SubjectName = rs.getString("Subject_name");
                        SubjectNumber = rs.getString("S_NO");

                    }
                    sql = "SELECT T_name,T_initials FROM tblTeachers WHERE (T_Code=(SELECT Teacher_id FROM tblTeacherstoSubjects WHERE (Teaching_class='" + Form + "' AND Subject_code='" + SubjectCode + "')))";
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        SubjectTeacher = rs.getString("T_name");
                        TeacherINIT = rs.getString("T_initials");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                retriveSavedMarks(Form, Year, Term, Exam, SubjectNumber, SubjectCode);
                updateGradingSystem(SubjectCode);
                
                jProgressBar2.setIndeterminate(false);
                //starts here
                //<editor-fold defaultstate="collapsed" desc="comment">
                try {
                    int SubjectIntegerNumber = Integer.parseInt(SubjectNumber);
                    switch (SubjectIntegerNumber) {
                        case 1:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S1CODE=?, S1NAME=?, S1Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 2:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S2CODE=?, S2NAME=?, S2Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 3:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S3CODE=?, S3NAME=?, S3Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 4:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S4CODE=?, S4NAME=?, S4Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 5:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S5CODE=?, S5NAME=?, S5Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 6:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S6CODE=?, S6NAME=?, S6Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 7:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S7CODE=?, S7NAME=?, S7Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 8:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S8CODE=?, S8NAME=?, S8Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 9:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S9CODE=?, S9NAME=?, S9Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 10:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S10CODE=?, S10NAME=?, S10Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 11:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S11CODE=?, S11NAME=?, S11Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 12:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S12CODE=?, S12NAME=?, S12Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 13:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S13CODE=?, S13NAME=?, S13Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        case 14:
                            if (Exam.equalsIgnoreCase("Exam 1")) {
                                sql = "UPDATE students_exams SET S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 2")) {
                                sql = "UPDATE students_exams SET S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            } else if (Exam.equalsIgnoreCase("Exam 3")) {
                                sql = "UPDATE students_exams SET S14CODE=?, S14NAME=?, S14Teacher=? WHERE (SE_Student_id=? AND SE_studentClass=? AND Year=? AND Term=?)";
                            }
                            break;
                        default:
                            break;
                    }
                    pst = Conn.prepareStatement(sql);
                    int Rows = tblMarks.getRowCount();
                    Total = tblMarks.getRowCount();
                    for (int row = 0; row < Rows; row++) {
                        publish(row);
                        jLabel10.setText("Updating Current marks for: "+tblMarks.getValueAt(row, 1).toString());
                        
                        String StudentID = tblMarks.getValueAt(row, 0).toString();
                        pst.setString(1, SubjectCode);
                        pst.setString(2, SubjectName);
                        pst.setString(3, TeacherINIT);
                        pst.setString(4, StudentID);
                        pst.setString(5, Form);
                        pst.setString(6, Year);
                        pst.setString(7, Term);
                        
                        pst.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
//</editor-fold>
               
                //ends here
                
                retriveSavedMarks(Form, Year, Term, Exam, SubjectNumber, SubjectCode);
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
               int progress = chucks.get(chucks.size()-1);
               ProgressNo2.setText((int) ((progress/(Total * 1.0))*100) +"% Done");
               jProgressBar2.setValue((int) ((progress/(Total * 1.0))*100));
            }

            @Override
            protected void done() {
                RestrievingStudentsProgressPane.dispose();
                jProgressBar2.setIndeterminate(true);
            }
        };
        worker.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SavingmarksProgressPane = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        ProgressNo = new javax.swing.JLabel();
        RestrievingStudentsProgressPane = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        ProgressNo2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboForm = new javax.swing.JComboBox<>();
        comboExam = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboCode = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMarks = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboTerm = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtGrade2 = new javax.swing.JLabel();
        txtGrade1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtGrade4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtGrade3 = new javax.swing.JLabel();
        txtGrade8 = new javax.swing.JLabel();
        txtGrade7 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtGrade5 = new javax.swing.JLabel();
        txtGrade6 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtGrade10 = new javax.swing.JLabel();
        txtGrade9 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtGrade11 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtGrade12 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        cmbYear = new javax.swing.JComboBox<>();

        SavingmarksProgressPane.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        SavingmarksProgressPane.setModal(true);
        SavingmarksProgressPane.setType(java.awt.Window.Type.UTILITY);
        SavingmarksProgressPane.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                SavingmarksProgressPaneWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 51));
        jLabel3.setText("Analysing and saving marks...");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        ProgressNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProgressNo.setText("Done");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ProgressNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout SavingmarksProgressPaneLayout = new javax.swing.GroupLayout(SavingmarksProgressPane.getContentPane());
        SavingmarksProgressPane.getContentPane().setLayout(SavingmarksProgressPaneLayout);
        SavingmarksProgressPaneLayout.setHorizontalGroup(
            SavingmarksProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SavingmarksProgressPaneLayout.setVerticalGroup(
            SavingmarksProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        RestrievingStudentsProgressPane.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        RestrievingStudentsProgressPane.setModal(true);
        RestrievingStudentsProgressPane.setType(java.awt.Window.Type.UTILITY);
        RestrievingStudentsProgressPane.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                RestrievingStudentsProgressPaneWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 51));
        jLabel10.setText("Retrieving data...");

        jProgressBar2.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar2.setIndeterminate(true);
        jProgressBar2.setString("");
        jProgressBar2.setStringPainted(true);

        ProgressNo2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProgressNo2.setText("ProgressNo2");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ProgressNo2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ProgressNo2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RestrievingStudentsProgressPaneLayout = new javax.swing.GroupLayout(RestrievingStudentsProgressPane.getContentPane());
        RestrievingStudentsProgressPane.getContentPane().setLayout(RestrievingStudentsProgressPaneLayout);
        RestrievingStudentsProgressPaneLayout.setHorizontalGroup(
            RestrievingStudentsProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RestrievingStudentsProgressPaneLayout.setVerticalGroup(
            RestrievingStudentsProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(650, 457));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);
        setSize(new java.awt.Dimension(650, 457));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Admin Marks Entry Panel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Form" }));

        comboExam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboExam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "EXAM 1", "EXAM 2", "EXAM 3" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("YEAR");

        comboCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));
        comboCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCodeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("SUBJECT ");

        tblMarks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADM NO", "NAME", "MARKS", "GRADE", "POINTS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMarks.getTableHeader().setReorderingAllowed(false);
        tblMarks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMarksMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMarksMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblMarks);
        if (tblMarks.getColumnModel().getColumnCount() > 0) {
            tblMarks.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblMarks.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblMarks.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblMarks.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblMarks.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        btnSave.setForeground(new java.awt.Color(0, 153, 51));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnClear.setForeground(new java.awt.Color(204, 0, 0));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Cancel_16x16.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        btnClear.setContentAreaFilled(false);
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("FORM");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("EXAMINATION");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("TERM");

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
        comboTerm.setToolTipText("");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Grading System for this Subject", 0, 0, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 204));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("A-");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade2.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade1.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("A");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 204));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("B");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade4.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 204));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("B+");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade3.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade8.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade7.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 204));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("C");
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 204));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("C-");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 204));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("C+");
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 204));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("B-");
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade5.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade6.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 204));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("D+");
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 204));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("D");
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade10.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade9.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 204));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("D-");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade11.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 204));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("E");
        jLabel29.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtGrade12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtGrade12.setForeground(new java.awt.Color(0, 0, 204));
        txtGrade12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGrade12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 204));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("X");
        jLabel31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 204));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("-");
        jLabel32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtGrade1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGrade2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrade12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel13, jLabel17, jLabel18, jLabel19, jLabel20, jLabel23, jLabel24, jLabel27, jLabel29, jLabel31, jLabel32, jLabel8, jLabel9, txtGrade1, txtGrade10, txtGrade11, txtGrade12, txtGrade2, txtGrade3, txtGrade4, txtGrade5, txtGrade6, txtGrade7, txtGrade8, txtGrade9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtGrade12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmbYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));
        cmbYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(comboCode, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String Year = cmbYear.getSelectedItem().toString();
        String Form = comboForm.getSelectedItem().toString();
        String Term = comboTerm.getSelectedItem().toString();
        String Exam = comboExam.getSelectedItem().toString();
        SubjectCode = comboCode.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select a Class from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Year.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Examinaiton from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (SubjectCode.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select a Subject from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else {
            int res = JOptionPane.showConfirmDialog(null, "Proceed with saving Marks?", "Procced", JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                SavingmarksProgressPane.pack();
                SavingmarksProgressPane.setLocationRelativeTo(null);
                SavingmarksProgressPane.setVisible(true);
            }
    }//GEN-LAST:event_btnSaveActionPerformed
    }
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        //verify that fields are all selected
        String Form = comboForm.getSelectedItem().toString();
        String Year = cmbYear.getSelectedItem().toString();
        String Term = comboTerm.getSelectedItem().toString();
        String Exam = comboExam.getSelectedItem().toString();
        int res = JOptionPane.showConfirmDialog(null, "Proceed with Caution, This  Action will DELETE even your previous marks", "Clear Marks", JOptionPane.YES_NO_OPTION);
        if (res == 0) {
            try {
                sql = "DELETE FROM students_exams WHERE (SE_studentClass=? AND Year=? AND Term=?)";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, Form);
                pst.setString(2, Year);
                pst.setString(3, Term);
                pst.executeUpdate();
                retriveSavedMarks(Form, Year, Term, Exam, SubjectNumber, SubjectCode);
                JOptionPane.showMessageDialog(null, "All data Cleared succesfully", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void comboCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCodeActionPerformed
        String Year = cmbYear.getSelectedItem().toString();
        String Form = comboForm.getSelectedItem().toString();
        String Term = comboTerm.getSelectedItem().toString();
        String Exam = comboExam.getSelectedItem().toString();
        SubjectCode = comboCode.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select a Class from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Year.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Examinaiton from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (SubjectCode.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select a Subject from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else {
            RestrievingStudentsProgressPane.pack();
            RestrievingStudentsProgressPane.setLocationRelativeTo(null);
            RestrievingStudentsProgressPane.setVisible(true);
        }
    }//GEN-LAST:event_comboCodeActionPerformed

    private void SavingmarksProgressPaneWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_SavingmarksProgressPaneWindowGainedFocus
        backgroundSaveMarks();
    }//GEN-LAST:event_SavingmarksProgressPaneWindowGainedFocus

    private void RestrievingStudentsProgressPaneWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_RestrievingStudentsProgressPaneWindowGainedFocus
        backgroundRetrivingStudents();
    }//GEN-LAST:event_RestrievingStudentsProgressPaneWindowGainedFocus

    private void tblMarksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMarksMousePressed
        
    }//GEN-LAST:event_tblMarksMousePressed

    private void tblMarksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMarksMouseClicked
        
    }//GEN-LAST:event_tblMarksMouseClicked

    private void cmbYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbYearActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarksRecordFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarksRecordFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarksRecordFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarksRecordFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarksRecordFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JLabel ProgressNo2;
    private javax.swing.JDialog RestrievingStudentsProgressPane;
    private javax.swing.JDialog SavingmarksProgressPane;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JComboBox<String> comboCode;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMarks;
    private javax.swing.JLabel txtGrade1;
    private javax.swing.JLabel txtGrade10;
    private javax.swing.JLabel txtGrade11;
    private javax.swing.JLabel txtGrade12;
    private javax.swing.JLabel txtGrade2;
    private javax.swing.JLabel txtGrade3;
    private javax.swing.JLabel txtGrade4;
    private javax.swing.JLabel txtGrade5;
    private javax.swing.JLabel txtGrade6;
    private javax.swing.JLabel txtGrade7;
    private javax.swing.JLabel txtGrade8;
    private javax.swing.JLabel txtGrade9;
    // End of variables declaration//GEN-END:variables
}
