package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.ExamDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.StudentExamDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.StudentExam;
import com.lysofts.entities.Subject;
import com.lysofts.utils.ConnClass;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class MarksRecordFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null, pst1 = null;
    ResultSet rs = null, rs1 = null;
    String sql = null, SubjectCode = "", sql1 = null;
    String SubjectName, SubjectNumber, SubjectTeacher, TeacherINIT;
    Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    String Year = "", Term = "", Exam = "", Code = "";
    List<Classroom> classrooms = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    List<StudentExam> studentExams = new ArrayList<>();
    Classroom selectedClassroom = null;
    Subject selectedSubject = null;
    Map<String, String[]> grading;

    int Grade1, Grade2, Grade3, Grade4, Grade5, Grade6, Grade7, Grade8, Grade9, Grade10, Grade11;

    public MarksRecordFrm() {
        initComponents();
        ConnClass.setFrameIcon(this);
        updateUI();
    }

    private void updateUI() {
        new Thread(() -> {
            getClassrooms();
            getYears();
            getSubjects();
        }).start();
    }

    private void getYears() {
        comboYear.removeAllItems();
        comboYear.addItem("SELECT");
        ExamDAO.getYears().forEach(exam -> {
            comboYear.addItem(exam.getYear());
        });
    }

    private void getClassrooms() {
        comboForm.removeAllItems();
        comboForm.addItem("SELECT");
        classrooms = ClassroomDAO.get();
        classrooms.forEach(cl -> {
            comboForm.addItem(cl.getName());
        });
    }

    private void getSubjects() {
        comboCode.removeAllItems();
        comboCode.addItem("SELECT");
        subjects = SubjectDAO.get();
        subjects.forEach(sub -> {
            comboCode.addItem(sub.getCode());
        });
    }

    private void getStudentExams() {
        if (selectedClassroom != null && selectedSubject != null) {
            studentExams = StudentExamDAO.get(selectedClassroom.getName(), Year, Term);
            DefaultTableModel model = (DefaultTableModel) tblMarks.getModel();
            model.setRowCount(0);
            studentExams.forEach(studentExam -> {
                String admno = studentExam.getStudentId();
                String name = StudentDAO.get(admno).getName();
                String num = selectedSubject.getNumber();
                String exam = Exam.equals("Exam 1") ? "1" : Exam.equals("Exam 2") ? "2" : "3";
                try {
                    Field codeField = studentExam.getClass().getDeclaredField(String.format("S%sCODE", num));
                    Field marksField = studentExam.getClass().getDeclaredField(String.format("S%sE%sMarks", num, exam));
                    Field gradeField = studentExam.getClass().getDeclaredField(String.format("S%sE%sGrade", num, exam));
                    Field pointsField = studentExam.getClass().getDeclaredField(String.format("S%sE%sPoints", num, exam));
                    codeField.setAccessible(true);
                    Object code = codeField.get(studentExam);
                    if (code.toString() != null && !code.toString().isEmpty()) {
                        marksField.setAccessible(true);
                        gradeField.setAccessible(true);
                        pointsField.setAccessible(true);
                        model.addRow(new Object[]{admno, name, marksField.get(studentExam), gradeField.get(studentExam), pointsField.get(studentExam)});
                    }
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            });
        }
    }

    private void setGrading() {
        grading = new HashMap<>();
        grading.put("A", new String[]{"100", selectedSubject.getGrade12()});
        grading.put("A-", new String[]{selectedSubject.getGrade12(), selectedSubject.getGrade11()});
        grading.put("B+", new String[]{selectedSubject.getGrade11(), selectedSubject.getGrade10()});
        grading.put("B", new String[]{selectedSubject.getGrade10(), selectedSubject.getGrade9()});
        grading.put("B-", new String[]{selectedSubject.getGrade9(), selectedSubject.getGrade8()});
        grading.put("C+", new String[]{selectedSubject.getGrade8(), selectedSubject.getGrade7()});
        grading.put("C", new String[]{selectedSubject.getGrade7(), selectedSubject.getGrade6()});
        grading.put("C-", new String[]{selectedSubject.getGrade6(), selectedSubject.getGrade5()});
        grading.put("D+", new String[]{selectedSubject.getGrade5(), selectedSubject.getGrade4()});
        grading.put("D", new String[]{selectedSubject.getGrade4(), selectedSubject.getGrade3()});
        grading.put("D-", new String[]{selectedSubject.getGrade3(), selectedSubject.getGrade2()});
        grading.put("E", new String[]{selectedSubject.getGrade2(), selectedSubject.getGrade1()});
        grading.put("X", new String[]{selectedSubject.getGrade1(), "0"});
    }

    private void retriveSavedMarks(String SubjectNumber, String SubjectCode) {
        DefaultTableModel model = (DefaultTableModel) tblMarks.getModel();
        model.setRowCount(0);
        try {
            if (Exam.equalsIgnoreCase("EXAM 1")) {
                //<editor-fold defaultstate="collapsed" desc="comment">
                switch (SubjectNumber) {
                    case "1":
                        sql = "select student_details.*,students_exams.S1E1Marks as  Marks,students_exams.S1E1Points as Points,students_exams.S1E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E1Marks as Marks,students_exams.S2E1Points as Points,students_exams.S2E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E1Marks as Marks,students_exams.S3E1Points as Points,students_exams.S3E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E1Marks as Marks,students_exams.S4E1Points as Points,students_exams.S4E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E1Marks as Marks,students_exams.S5E1Points as Points,students_exams.S5E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E1Marks as Marks,students_exams.S6E1Points as Points,students_exams.S6E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E1Marks as Marks,students_exams.S7E1Points as Points,students_exams.S7E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E1Marks as Marks,students_exams.S8E1Points as Points,students_exams.S8E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E1Marks as Marks,students_exams.S9E1Points as Points,students_exams.S9E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E1Marks as Marks,students_exams.S10E1Points as Points,students_exams.S10E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E1Marks as Marks,students_exams.S11E1Points as Points,students_exams.S11E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E1Marks as Marks,students_exams.S12E1Points as Points,students_exams.S12E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E1Marks as Marks,students_exams.S13E1Points as Points,students_exams.S13E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E1Marks as Marks,students_exams.S14E1Points as Points,students_exams.S14E1Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC";
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
                        sql = "select student_details.*,students_exams.S1E2Marks as  Marks,students_exams.S1E2Points as Points,students_exams.S1E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E2Marks as Marks,students_exams.S2E2Points as Points,students_exams.S2E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E2Marks as Marks,students_exams.S3E2Points as Points,students_exams.S3E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E2Marks as Marks,students_exams.S4E2Points as Points,students_exams.S4E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E2Marks as Marks,students_exams.S5E2Points as Points,students_exams.S5E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E2Marks as Marks,students_exams.S6E2Points as Points,students_exams.S6E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E2Marks as Marks,students_exams.S7E2Points as Points,students_exams.S7E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E2Marks as Marks,students_exams.S8E2Points as Points,students_exams.S8E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E2Marks as Marks,students_exams.S9E2Points as Points,students_exams.S9E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E2Marks as Marks,students_exams.S10E2Points as Points,students_exams.S10E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E2Marks as Marks,students_exams.S11E2Points as Points,students_exams.S11E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E2Marks as Marks,students_exams.S12E2Points as Points,students_exams.S12E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E2Marks as Marks,students_exams.S13E2Points as Points,students_exams.S13E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E2Marks as Marks,students_exams.S14E2Points as Points,students_exams.S14E2Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
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
                        sql = "select student_details.*,students_exams.S1E3Marks as  Marks,students_exams.S1E3Points as Points,students_exams.S1E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "2":
                        sql = "select student_details.*,students_exams.S2E3Marks as Marks,students_exams.S2E3Points as Points,students_exams.S2E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "3":
                        sql = "select student_details.*,students_exams.S3E3Marks as Marks,students_exams.S3E3Points as Points,students_exams.S3E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "4":
                        sql = "select student_details.*,students_exams.S4E3Marks as Marks,students_exams.S4E3Points as Points,students_exams.S4E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "5":
                        sql = "select student_details.*,students_exams.S5E3Marks as Marks,students_exams.S5E3Points as Points,students_exams.S5E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "6":
                        sql = "select student_details.*,students_exams.S6E3Marks as Marks,students_exams.S6E3Points as Points,students_exams.S6E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "7":
                        sql = "select student_details.*,students_exams.S7E3Marks as Marks,students_exams.S7E3Points as Points,students_exams.S7E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "8":
                        sql = "select student_details.*,students_exams.S8E3Marks as Marks,students_exams.S8E3Points as Points,students_exams.S8E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "9":
                        sql = "select student_details.*,students_exams.S9E3Marks as Marks,students_exams.S9E3Points as Points,students_exams.S9E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "10":
                        sql = "select student_details.*,students_exams.S10E3Marks as Marks,students_exams.S10E3Points as Points,students_exams.S10E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "11":
                        sql = "select student_details.*,students_exams.S11E3Marks as Marks,students_exams.S11E3Points as Points,students_exams.S11E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "12":
                        sql = "select student_details.*,students_exams.S12E3Marks as Marks,students_exams.S12E3Points as Points,students_exams.S12E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "13":
                        sql = "select student_details.*,students_exams.S13E3Marks as Marks,students_exams.S13E3Points as Points,students_exams.S13E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
                        break;
                    case "14":
                        sql = "select student_details.*,students_exams.S14E3Marks as Marks,students_exams.S14E3Points as Points,students_exams.S14E3Grade as Grade From student_details INNER JOIN students_exams ON student_details.Student_ID=students_exams.SE_Student_id WHERE ( (student_details.Student_id IN (SELECT SS_Student_id FROM tblstudents_subjects WHERE (SS_Subject_Code='" + SubjectCode + "'))) AND SE_StudentClass='" + selectedClassroom.getName() + "' AND students_exams.Year='" + Year + "' AND students_exams.Term='" + Term + "') ORDER BY (student_details.Student_id + 0) ASC ";
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
                        jLabel3.setText("Saving marks for Student: " + tblMarks.getValueAt(row, 1).toString());
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
                        pst.setString(6, selectedClassroom.getName());
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
                        jLabel3.setText("Analysing Grades and Points For: " + tblMarks.getValueAt(row, 1).toString());
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
                        pst.setString(4, selectedClassroom.getName());
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
                retriveSavedMarks(SubjectNumber, SubjectCode);
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size() - 1);
                ProgressNo.setText((int) ((progress / (Total * 1.0)) * 100) + "% Done");
                jProgressBar1.setValue((int) ((progress / (Total * 1.0)) * 100));
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
                Year = comboYear.getSelectedItem().toString();
                selectedClassroom = classrooms.get(comboForm.getSelectedIndex() - 1);
                Term = comboTerm.getSelectedItem().toString();
                Exam = comboExam.getSelectedItem().toString();
                SubjectCode = comboCode.getSelectedItem().toString();
                try {
                    pst = Conn.prepareStatement("SELECT * FROM Subjects WHERE Subject_code = '" + SubjectCode + "'");
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        SubjectName = rs.getString("Subject_name");
                        SubjectNumber = rs.getString("S_NO");

                    }
                    sql = "SELECT T_name,T_initials FROM tblTeachers WHERE (T_Code=(SELECT Teacher_id FROM tblTeacherstoSubjects WHERE (Teaching_class='" + selectedClassroom.getName() + "' AND Subject_code='" + SubjectCode + "')))";
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        SubjectTeacher = rs.getString("T_name");
                        TeacherINIT = rs.getString("T_initials");
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
                retriveSavedMarks(SubjectNumber, SubjectCode);

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
                        jLabel10.setText("Updating Current marks for: " + tblMarks.getValueAt(row, 1).toString());

                        String StudentID = tblMarks.getValueAt(row, 0).toString();
                        pst.setString(1, SubjectCode);
                        pst.setString(2, SubjectName);
                        pst.setString(3, TeacherINIT);
                        pst.setString(4, StudentID);
                        pst.setString(5, selectedClassroom.getName());
                        pst.setString(6, Year);
                        pst.setString(7, Term);

                        pst.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
//</editor-fold>

                //ends here
                retriveSavedMarks(SubjectNumber, SubjectCode);
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size() - 1);
                ProgressNo2.setText((int) ((progress / (Total * 1.0)) * 100) + "% Done");
                jProgressBar2.setValue((int) ((progress / (Total * 1.0)) * 100));
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
        comboYear = new javax.swing.JComboBox<>();

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

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Form" }));
        comboForm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFormPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        comboExam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboExam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "EXAM 1", "EXAM 2", "EXAM 3" }));
        comboExam.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboExamPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("YEAR");

        comboCode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboCode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));
        comboCode.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboCodePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
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
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))
        );
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
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Cancel_16x16.png"))
        );
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
        comboTerm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboTermPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        comboYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));
        comboYear.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboYearPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(comboCode, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
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
                        .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Year = comboYear.getSelectedItem().toString();
        selectedClassroom = classrooms.get(comboForm.getSelectedIndex() - 1);
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        SubjectCode = comboCode.getSelectedItem().toString();
        if (selectedClassroom.getName().equalsIgnoreCase("Select selectedClassroom.getName()")) {
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
        int res = JOptionPane.showConfirmDialog(null, "Proceed with Caution, This  Action will DELETE even your previous marks", "Clear Marks", JOptionPane.YES_NO_OPTION);
        if (res == 0) {
            try {
                sql = "DELETE FROM students_exams WHERE (SE_studentClass=? AND Year=? AND Term=?)";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, selectedClassroom.getName());
                pst.setString(2, Year);
                pst.setString(3, Term);
                pst.executeUpdate();
                retriveSavedMarks(SubjectNumber, SubjectCode);
                JOptionPane.showMessageDialog(null, "All data Cleared succesfully", "Deleted", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

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

    private void comboFormPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboFormPopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        selectedClassroom = index > 0 ? classrooms.get(index - 1) : null;
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        index = comboCode.getSelectedIndex();
        selectedSubject = index > 0 ? subjects.get(index - 1) : null;
        getStudentExams();
    }//GEN-LAST:event_comboFormPopupMenuWillBecomeInvisible

    private void comboYearPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboYearPopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        selectedClassroom = index > 0 ? classrooms.get(index - 1) : null;
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        index = comboCode.getSelectedIndex();
        selectedSubject = index > 0 ? subjects.get(index - 1) : null;
        getStudentExams();
    }//GEN-LAST:event_comboYearPopupMenuWillBecomeInvisible

    private void comboTermPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboTermPopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        selectedClassroom = index > 0 ? classrooms.get(index - 1) : null;
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        index = comboCode.getSelectedIndex();
        selectedSubject = index > 0 ? subjects.get(index - 1) : null;
        getStudentExams();
    }//GEN-LAST:event_comboTermPopupMenuWillBecomeInvisible

    private void comboExamPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboExamPopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        selectedClassroom = index > 0 ? classrooms.get(index - 1) : null;
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        index = comboCode.getSelectedIndex();
        selectedSubject = index > 0 ? subjects.get(index - 1) : null;
        getStudentExams();
    }//GEN-LAST:event_comboExamPopupMenuWillBecomeInvisible

    private void comboCodePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboCodePopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        selectedClassroom = index > 0 ? classrooms.get(index - 1) : null;
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        index = comboCode.getSelectedIndex();
        selectedSubject = index > 0 ? subjects.get(index - 1) : null;
        if (selectedSubject != null) {
            setGrading();
        }
        getStudentExams();
    }//GEN-LAST:event_comboCodePopupMenuWillBecomeInvisible

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
    private javax.swing.JComboBox<String> comboCode;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMarks;
    // End of variables declaration//GEN-END:variables
}
