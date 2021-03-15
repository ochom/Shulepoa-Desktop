package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.ExamDAO;
import com.lysofts.utils.ConnClass;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class ReportsFrm extends javax.swing.JFrame {

    Connection conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;

    String sql = null, report_bg = null, reportTitle = "";
    String Form, Year, Term, Exam, ExamFormLevel, NumberOfChamps, Report_Request = "";
    String reportName;

    public ReportsFrm() {
        initComponents();
        ConnClass.setFrameIcon(this);
        GetAcedmicYears();
        getFormNames();

    }

    private void GetAcedmicYears() {
        cmbYear.removeAllItems();
        cmbYear.addItem("SELECT");
        ExamDAO.getYears().forEach(exam -> {
            cmbYear.addItem(exam.getYear());
        });
    }

    private void getFormNames() {
        comboForm.removeAllItems();
        comboForm.addItem("Select Form");
        ClassroomDAO.get().forEach(classroom -> {
            comboForm.addItem(classroom.getName());
        });
    }

    private void showReport(HashMap params) {
        try {
            String fileName = String.format("reports/%s.jrxml", reportName);
            InputStream report = getClass().getClassLoader().getResourceAsStream(fileName);
            JasperDesign jd = JRXmlLoader.load(report);

            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, conn);
            JRViewer jv = new JRViewer(jp);

            JFrame jf = new JFrame(reportTitle);
            jf.getContentPane().add(jv);
            jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
            jf.setType(Type.NORMAL);
            jf.validate();
            jf.setSize(new Dimension(900, 650));
            jf.setLocationRelativeTo(this);
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(ReportsFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printMarksSheet(String Form) {
        reportName = "MarkSheet";
        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("bgImage", null);
        reportTitle = "Form " + Form + " Marksheet";
        showReport(param);
    }

    private void printMarksConfirmationSheet(String Form, String Year, String Term, String Exam) {
        if (Exam.equalsIgnoreCase("Exam 1")) {
            reportName = "Exam1ConfirmationSheet";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
            reportName = "Exam2ConfirmationSheet";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
            reportName = "Exam3ConfirmationSheet";
        }
        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " Marks Confirmation Sheet";
        showReport(param);
    }

    private void printResultSlips(String Form, String Year, String Term, String Exam) {
        if (Exam.equalsIgnoreCase("Exam 1")) {
            reportName = "Exam1ResultSlip";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
            reportName = "Exam2ResultSlip";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
            reportName = "Exam3ResultSlip";
        }
        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " Result Slips";
        showReport(param);
    }

    private void printExamStreamRankingList(String Form, String Year, String Term, String Exam) {
        switch (Exam) {
            case "EXAM 1":
                reportName = "Exam1RankingList";
                break;
            case "EXAM 2":
                reportName = "Exam2RankingList";
                break;
            case "EXAM 3":
                reportName = "Exam3RankingList";
                break;
            default:
                break;
        }
        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " " + Exam + " Ranking List";
        showReport(param);
    }

    private void printExamOverallRankingList(String Form, String Year, String Term, String Exam, String ExamFormLevel) {

        switch (Exam) {
            case "EXAM 1":
                reportName = "Exam1OverallRankingList";
                break;
            case "EXAM 2":
                reportName = "Exam2OverallRankingList";
                break;
            case "EXAM 3":
                reportName = "Exam3OverallRankingList";
                break;
            default:
                break;
        }
        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("ExamFormLevel", ExamFormLevel);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " " + Exam + " Ranking List";
        showReport(param);
    }

    private void printTermStreamRankingList(String Form, String Year, String Term) {
        reportName = "EndTermStreamRankingList";

        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " " + " End Term Ranking List";
        showReport(param);
    }

    private void printTermOverallRankingList(String Form, String Year, String Term, String ExamFormLevel) {
        reportName = "EndTermOverallRankingList";

        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("ExamFormLevel", ExamFormLevel);
        param.put("bgImage", null);

        reportTitle = "Form " + ExamFormLevel + " " + " End Term Ranking List";
        showReport(param);
    }

    private void printReportForms(String Form, String Year, String Term) {
        reportName = "ReportForm";

        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("bgImage", null);
        reportTitle = "Form " + Form + " report Form";
        showReport(param);
    }

    private void AnalyseSubjectPerformance(String Form, String Year, String Term, String Exam) {
        try {
            sql = "DELETE  FROM ClassSubjectPerfomance";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Space created for subject perfomance");

            InsertSubjectsToSubjectAnalysisTable(Form, Year, Term);

            sql = "SELECT * FROM ClassSubjectPerfomance";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String GradeCol = null;
            String MarksCol = null;
            String PointsCol = null;
            String SubjectNO;
            String SubjectName;
            switch (Exam) {
                case "EXAM 1":
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    while (rs.next()) {
                        SubjectNO = rs.getString("SubjectNO");
                        SubjectName = rs.getString("SubjectName");
                        if (SubjectNO.equalsIgnoreCase("1")) {
                            GradeCol = "S1E1Grade";
                            MarksCol = "S1E1Marks";
                            PointsCol = "S1E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("2")) {
                            GradeCol = "S2E1Grade";
                            MarksCol = "S2E1Marks";
                            PointsCol = "S2E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("3")) {
                            GradeCol = "S3E1Grade";
                            MarksCol = "S3E1Marks";
                            PointsCol = "S3E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("4")) {
                            GradeCol = "S4E1Grade";
                            MarksCol = "S4E1Marks";
                            PointsCol = "S4E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("5")) {
                            GradeCol = "S5E1Grade";
                            MarksCol = "S5E1Marks";
                            PointsCol = "S5E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("6")) {
                            GradeCol = "S6E1Grade";
                            MarksCol = "S6E1Marks";
                            PointsCol = "S6E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("7")) {
                            GradeCol = "S7E1Grade";
                            MarksCol = "S7E1Marks";
                            PointsCol = "S7E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("8")) {
                            GradeCol = "S8E1Grade";
                            MarksCol = "S8E1Marks";
                            PointsCol = "S8E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("9")) {
                            GradeCol = "S9E1Grade";
                            MarksCol = "S9E1Marks";
                            PointsCol = "S9E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("10")) {
                            GradeCol = "S10E1Grade";
                            MarksCol = "S10E1Marks";
                            PointsCol = "S10E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("11")) {
                            GradeCol = "S11E1Grade";
                            MarksCol = "S11E1Marks";
                            PointsCol = "S11E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("12")) {
                            GradeCol = "S12E1Grade";
                            MarksCol = "S12E1Marks";
                            PointsCol = "S12E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("13")) {
                            GradeCol = "S13E1Grade";
                            MarksCol = "S13E1Marks";
                            PointsCol = "S13E1Points";
                        } else if (SubjectNO.equalsIgnoreCase("14")) {
                            GradeCol = "S14E1Grade";
                            MarksCol = "S14E1Marks";
                            PointsCol = "S14E1Points";
                        }

                        getDefiningSubjectAnalysisQueries(Form, Year, Term, MarksCol, PointsCol, GradeCol, SubjectNO, SubjectName);
                    }

                    break;
                //</editor-fold>
                case "EXAM 2":
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    while (rs.next()) {
                        SubjectNO = rs.getString("SubjectNO");
                        SubjectName = rs.getString("SubjectName");
                        if (SubjectNO.equalsIgnoreCase("1")) {
                            GradeCol = "S1E2Grade";
                            MarksCol = "S1E2Marks";
                            PointsCol = "S1E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("2")) {
                            GradeCol = "S2E2Grade";
                            MarksCol = "S2E2Marks";
                            PointsCol = "S2E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("3")) {
                            GradeCol = "S3E2Grade";
                            MarksCol = "S3E2Marks";
                            PointsCol = "S3E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("4")) {
                            GradeCol = "S4E2Grade";
                            MarksCol = "S4E2Marks";
                            PointsCol = "S4E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("5")) {
                            GradeCol = "S5E2Grade";
                            MarksCol = "S5E2Marks";
                            PointsCol = "S5E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("6")) {
                            GradeCol = "S6E2Grade";
                            MarksCol = "S6E2Marks";
                            PointsCol = "S6E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("7")) {
                            GradeCol = "S7E2Grade";
                            MarksCol = "S7E2Marks";
                            PointsCol = "S7E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("8")) {
                            GradeCol = "S8E2Grade";
                            MarksCol = "S8E2Marks";
                            PointsCol = "S8E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("9")) {
                            GradeCol = "S9E2Grade";
                            MarksCol = "S9E2Marks";
                            PointsCol = "S9E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("10")) {
                            GradeCol = "S10E2Grade";
                            MarksCol = "S10E2Marks";
                            PointsCol = "S10E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("11")) {
                            GradeCol = "S11E2Grade";
                            MarksCol = "S11E2Marks";
                            PointsCol = "S11E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("12")) {
                            GradeCol = "S12E2Grade";
                            MarksCol = "S12E2Marks";
                            PointsCol = "S12E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("13")) {
                            GradeCol = "S13E2Grade";
                            MarksCol = "S13E2Marks";
                            PointsCol = "S13E2Points";
                        } else if (SubjectNO.equalsIgnoreCase("14")) {
                            GradeCol = "S14E2Grade";
                            MarksCol = "S14E2Marks";
                            PointsCol = "S14E2Points";
                        }
                        getDefiningSubjectAnalysisQueries(Form, Year, Term, MarksCol, PointsCol, GradeCol, SubjectNO, SubjectName);
                    }

                    break;
                //</editor-fold>
                case "EXAM 3":
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    while (rs.next()) {
                        SubjectNO = rs.getString("SubjectNO");
                        SubjectName = rs.getString("SubjectName");
                        if (SubjectNO.equalsIgnoreCase("1")) {
                            GradeCol = "S1E3Grade";
                            MarksCol = "S1E3Marks";
                            PointsCol = "S1E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("2")) {
                            GradeCol = "S2E3Grade";
                            MarksCol = "S2E3Marks";
                            PointsCol = "S2E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("3")) {
                            GradeCol = "S3E3Grade";
                            MarksCol = "S3E3Marks";
                            PointsCol = "S3E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("4")) {
                            GradeCol = "S4E3Grade";
                            MarksCol = "S4E3Marks";
                            PointsCol = "S4E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("5")) {
                            GradeCol = "S5E3Grade";
                            MarksCol = "S5E3Marks";
                            PointsCol = "S5E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("6")) {
                            GradeCol = "S6E3Grade";
                            MarksCol = "S6E3Marks";
                            PointsCol = "S6E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("7")) {
                            GradeCol = "S7E3Grade";
                            MarksCol = "S7E3Marks";
                            PointsCol = "S7E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("8")) {
                            GradeCol = "S8E3Grade";
                            MarksCol = "S8E3Marks";
                            PointsCol = "S8E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("9")) {
                            GradeCol = "S9E3Grade";
                            MarksCol = "S9E3Marks";
                            PointsCol = "S9E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("10")) {
                            GradeCol = "S10E3Grade";
                            MarksCol = "S10E3Marks";
                            PointsCol = "S10E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("11")) {
                            GradeCol = "S11E3Grade";
                            MarksCol = "S11E3Marks";
                            PointsCol = "S11E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("12")) {
                            GradeCol = "S12E3Grade";
                            MarksCol = "S12E3Marks";
                            PointsCol = "S12E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("13")) {
                            GradeCol = "S13E3Grade";
                            MarksCol = "S13E3Marks";
                            PointsCol = "S13E3Points";
                        } else if (SubjectNO.equalsIgnoreCase("14")) {
                            GradeCol = "S14E3Grade";
                            MarksCol = "S14E3Marks";
                            PointsCol = "S14E3Points";
                        }
                        getDefiningSubjectAnalysisQueries(Form, Year, Term, MarksCol, PointsCol, GradeCol, SubjectNO, SubjectName);
                    }
                    break;
                //</editor-fold>
                case "END TERM":
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    while (rs.next()) {
                        SubjectNO = rs.getString("SubjectNO");
                        SubjectName = rs.getString("SubjectName");
                        if (SubjectNO.equalsIgnoreCase("1")) {
                            GradeCol = "S1AVGGrade";
                            MarksCol = "S1AVGMarks";
                            PointsCol = "S1AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("2")) {
                            GradeCol = "S2AVGGrade";
                            MarksCol = "S2AVGMarks";
                            PointsCol = "S2AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("3")) {
                            GradeCol = "S3AVGGrade";
                            MarksCol = "S3AVGMarks";
                            PointsCol = "S3AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("4")) {
                            GradeCol = "S4AVGGrade";
                            MarksCol = "S4AVGMarks";
                            PointsCol = "S4AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("5")) {
                            GradeCol = "S5AVGGrade";
                            MarksCol = "S5AVGMarks";
                            PointsCol = "S5AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("6")) {
                            GradeCol = "S6AVGGrade";
                            MarksCol = "S6AVGMarks";
                            PointsCol = "S6AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("7")) {
                            GradeCol = "S7AVGGrade";
                            MarksCol = "S7AVGMarks";
                            PointsCol = "S7AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("8")) {
                            GradeCol = "S8AVGGrade";
                            MarksCol = "S8AVGMarks";
                            PointsCol = "S8AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("9")) {
                            GradeCol = "S9AVGGrade";
                            MarksCol = "S9AVGMarks";
                            PointsCol = "S9AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("10")) {
                            GradeCol = "S10AVGGrade";
                            MarksCol = "S10AVGMarks";
                            PointsCol = "S10AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("11")) {
                            GradeCol = "S11AVGGrade";
                            MarksCol = "S11AVGMarks";
                            PointsCol = "S11AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("12")) {
                            GradeCol = "S12AVGGrade";
                            MarksCol = "S12AVGMarks";
                            PointsCol = "S12AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("13")) {
                            GradeCol = "S13AVGGrade";
                            MarksCol = "S13AVGMarks";
                            PointsCol = "S13AVGPoints";
                        } else if (SubjectNO.equalsIgnoreCase("14")) {
                            GradeCol = "S14AVGGrade";
                            MarksCol = "S14AVGMarks";
                            PointsCol = "S14AVGPoints";
                        }

                        getDefiningSubjectAnalysisQueries(Form, Year, Term, MarksCol, PointsCol, GradeCol, SubjectNO, SubjectName);
                    }
                    break;
                //</editor-fold>
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void InsertSubjectsToSubjectAnalysisTable(String Form, String Year, String Term) {
        try {
            sql = "SELECT * FROM Subjects";
            pst = conn.prepareStatement(sql);
            rs = rs = pst.executeQuery();
            //Add subjects to the table
            while (rs.next()) {
                String SubjectNO = rs.getString("S_NO");
                String SubjectName = rs.getString("Subject_name");
                sql = "INSERT INTO ClassSubjectPerfomance (Form,Year,Term,SubjectNO,SubjectName) values ('" + Form + "','" + Year + "','" + Term + "','" + SubjectNO + "','" + SubjectName + "')";
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }

    }

    private void getDefiningSubjectAnalysisQueries(String Form, String Year, String Term, String MarksCol, String PointsCol, String GradeCol, String SubjectNumber, String SubjectName) {
        String Ap = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='A' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Amns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='A-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Bpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Bp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Bmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Cpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Cp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Cmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Dpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Dp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Dmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        String Ep = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='E' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";

        String SubjectEntry = "SELECT count(*) FROM student_details where (Student_id IN (select SS_Student_id from tblstudents_subjects where SS_subject_Name='" + SubjectName + "') AND Student_class='" + Form + "')";
        String SubjectMarks = "SELECT Avg(" + MarksCol + ") FROM Students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "' AND " + MarksCol + "+0>0)";
        String SubjectMean = "SELECT Avg(" + PointsCol + ") FROM Students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "' AND " + MarksCol + "+0>0)";

        try {
            sql = "UPDATE ClassSubjectPerfomance set SubjectMeanMarks=(" + SubjectMarks + "),SubjectMeanPoints=(" + SubjectMean + "), SubjectEntry = (" + SubjectEntry + "), AP = (" + Ap + "), 'Amns' = (" + Amns + "), 'Bpls' = (" + Bpls + "),BP = (" + Bp + "), 'Bmns' = (" + Bmns + "), 'Cpls' = (" + Cpls + "), 'CP' = (" + Cp + "), 'Cmns' = (" + Cmns + "), 'Dpls' = (" + Dpls + "), 'DP' = (" + Dp + "), 'Dmns' = (" + Dmns + "), 'EP' = (" + Ep + ") WHERE SubjectNO = '" + SubjectNumber + "'";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setTheSubjectPosition() {
        try {
            sql = "UPDATE ClassSubjectPerfomance set SubjectPosition=(1+(SELECT Count(*) From ClassSubjectPerfomance as t2 WHERE ((t2.SubjectMeanPoints+0)>(ClassSubjectPerfomance.SubjectMeanPoints+0)))) WHERE(SubjectMeanPoints+0>0)";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void printExamSubjectPerfomance(String Form, String Year, String Term, String Exam) {
        reportName = "SubjectMeans";

        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("ExamName", Exam);
        param.put("bgImage", null);
        reportTitle = "Form " + Form + " Marksheet";
        showReport(param);
    }

    private void AnalyzeClassesPerfomance(String Form, String Year, String Term, String Exam, String ExamFormLevel) {
        String MarksCol = null;
        String PointsCol = null;
        String GradeCol = null;
        switch (Exam) {
            case "EXAM 1":
                MarksCol = "Exam1TotalMarks";
                PointsCol = "Exam1MeanPoints";
                GradeCol = "Exam1MeanGrade";
                break;
            case "EXAM 2":
                MarksCol = "Exam2TotalMarks";
                PointsCol = "Exam2MeanPoints";
                GradeCol = "Exam2MeanGrade";
                break;
            case "EXAM 3":
                MarksCol = "Exam3TotalMarks";
                PointsCol = "Exam3MeanPoints";
                GradeCol = "Exam3MeanGrade";
                break;
            case "END TERM":
                MarksCol = "TotalMarks";
                PointsCol = "MeanPoints";
                GradeCol = "MeanGrade";
                break;

        }
        try {
            PreparedStatement ps = conn.prepareStatement("Select * from tblClasses WHERE substr(Class_name,1,1) = '" + ExamFormLevel + "'");
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                Form = rst.getString("Class_name");
                String Ap = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='A' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Amns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='A-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Bpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Bp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Bmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='B-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Cpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Cp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Cmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='C-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Dpls = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D+' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Dp = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Dmns = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='D-' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                String Ep = "SELECT Count(*) FROM Students_exams WHERE (" + GradeCol + "='E' AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";

                String ClassEntry = "SELECT count(*) FROM student_details where (Student_class='" + Form + "')";
                String ClassMarks = "SELECT Avg(" + MarksCol + ") FROM Students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "' AND " + MarksCol + "+0>0)";
                String ClassMean = "SELECT Avg(" + PointsCol + ") FROM Students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "' AND " + PointsCol + "+0>0)";

                try {
                    sql = "UPDATE tblClasses set Entry=(" + ClassEntry + "),AverageMarks=(" + ClassMarks + "),MeanPoints=(" + ClassMean + "),"
                            + " Ap = (" + Ap + "), 'Amns' = (" + Amns + "), 'Bpls' = (" + Bpls + "),BP = (" + Bp + "), 'Bmns' = (" + Bmns + "), 'Cpls' = (" + Cpls + "), 'CP' = (" + Cp + "), 'Cmns' = (" + Cmns + "), 'Dpls' = (" + Dpls + "), 'DP' = (" + Dp + "), 'Dmns' = (" + Dmns + "), 'EP' = (" + Ep + ") WHERE Class_name = '" + Form + "'";
                    pst = conn.prepareStatement(sql);
                    pst.executeUpdate();
                    System.out.println("Classes Ranked, Form: " + Form);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTheClassPosition(String ExamFormLevel) {
        try {
            sql = "UPDATE tblClasses set Position=(1+(SELECT Count(*) From tblClasses as t2 WHERE ((t2.MeanPoints+0)>(tblClasses.MeanPoints+0) AND substr(Class_name,1,1)='" + ExamFormLevel + "') )) WHERE(MeanPoints+0>0)";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void printClassPerformance(String ExamFormLevel, String Year, String Term, String Exam) {
        reportName = "ClassMeans";

        HashMap param = new HashMap();
        param.put("ExamForm", ExamFormLevel);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("ExamName", Exam);
        param.put("bgImage", null);
        reportTitle = "Form " + ExamFormLevel + " Marksheet";
        showReport(param);
    }

    private void AnalyzeSubjectChampions(String Form, String Year, String Term, String Exam, String NumberOfChamps) {
        String MarksCol = null;
        String GradeCol = null;
        try {
            sql = "DELETE  FROM SubjectChampions";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            System.out.println("Space created for subject perfomance");

            sql = "SELECT * FROM Subjects";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            switch (Exam) {
                case "EXAM 1"://<editor-fold defaultstate="collapsed" desc="comment">

                    while (rs.next()) {
                        String SubjectName = rs.getString("Subject_name");
                        String SubjectNumber = rs.getString("S_NO");
                        System.out.println(SubjectName);
                        if (SubjectNumber.equalsIgnoreCase("1")) {
                            MarksCol = "S1E1Marks";
                            GradeCol = "S1E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("2")) {
                            MarksCol = "S2E1Marks";
                            GradeCol = "S2E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("3")) {
                            MarksCol = "S3E1Marks";
                            GradeCol = "S3E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("4")) {
                            MarksCol = "S4E1Marks";
                            GradeCol = "S4E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("5")) {
                            MarksCol = "S5E1Marks";
                            GradeCol = "S5E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("6")) {
                            MarksCol = "S6E1Marks";
                            GradeCol = "S6E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("7")) {
                            MarksCol = "S7E1Marks";
                            GradeCol = "S7E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("8")) {
                            MarksCol = "S8E1Marks";
                            GradeCol = "S8E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("9")) {
                            MarksCol = "S9E1Marks";
                            GradeCol = "S9E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("10")) {
                            MarksCol = "S10E1Marks";
                            GradeCol = "S10E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("11")) {
                            MarksCol = "S11E1Marks";
                            GradeCol = "S11E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("12")) {
                            MarksCol = "S12E1Marks";
                            GradeCol = "S12E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("13")) {
                            MarksCol = "S13E1Marks";
                            GradeCol = "S13E1Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("14")) {
                            MarksCol = "S14E1Marks";
                            GradeCol = "S14E1Grade";
                        }
                        getSubjectChampionsForThisSubject(Form, Year, Term, SubjectName, NumberOfChamps, MarksCol, GradeCol);
                    }
                    break;
//</editor-fold>
                case "EXAM 2"://<editor-fold defaultstate="collapsed" desc="comment">

                    while (rs.next()) {
                        String SubjectName = rs.getString("Subject_name");
                        String SubjectNumber = rs.getString("S_NO");
                        System.out.println(SubjectName);
                        if (SubjectNumber.equalsIgnoreCase("1")) {
                            MarksCol = "S1E2Marks";
                            GradeCol = "S1E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("2")) {
                            MarksCol = "S2E2Marks";
                            GradeCol = "S2E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("3")) {
                            MarksCol = "S3E2Marks";
                            GradeCol = "S3E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("4")) {
                            MarksCol = "S4E2Marks";
                            GradeCol = "S4E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("5")) {
                            MarksCol = "S5E2Marks";
                            GradeCol = "S5E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("6")) {
                            MarksCol = "S6E2Marks";
                            GradeCol = "S6E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("7")) {
                            MarksCol = "S7E2Marks";
                            GradeCol = "S7E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("8")) {
                            MarksCol = "S8E2Marks";
                            GradeCol = "S8E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("9")) {
                            MarksCol = "S9E2Marks";
                            GradeCol = "S9E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("10")) {
                            MarksCol = "S10E2Marks";
                            GradeCol = "S10E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("11")) {
                            MarksCol = "S11E2Marks";
                            GradeCol = "S11E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("12")) {
                            MarksCol = "S12E2Marks";
                            GradeCol = "S12E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("13")) {
                            MarksCol = "S13E2Marks";
                            GradeCol = "S13E2Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("14")) {
                            MarksCol = "S14E2Marks";
                            GradeCol = "S14E2Grade";
                        }
                        getSubjectChampionsForThisSubject(Form, Year, Term, SubjectName, NumberOfChamps, MarksCol, GradeCol);
                    }
                    break;
//</editor-fold>
                case "EXAM 3"://<editor-fold defaultstate="collapsed" desc="comment">

                    while (rs.next()) {
                        String SubjectName = rs.getString("Subject_name");
                        String SubjectNumber = rs.getString("S_NO");
                        System.out.println(SubjectName);
                        if (SubjectNumber.equalsIgnoreCase("1")) {
                            MarksCol = "S1E3Marks";
                            GradeCol = "S1E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("2")) {
                            MarksCol = "S2E3Marks";
                            GradeCol = "S2E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("3")) {
                            MarksCol = "S3E3Marks";
                            GradeCol = "S3E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("4")) {
                            MarksCol = "S4E3Marks";
                            GradeCol = "S4E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("5")) {
                            MarksCol = "S5E3Marks";
                            GradeCol = "S5E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("6")) {
                            MarksCol = "S6E3Marks";
                            GradeCol = "S6E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("7")) {
                            MarksCol = "S7E3Marks";
                            GradeCol = "S7E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("8")) {
                            MarksCol = "S8E3Marks";
                            GradeCol = "S8E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("9")) {
                            MarksCol = "S9E3Marks";
                            GradeCol = "S9E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("10")) {
                            MarksCol = "S10E3Marks";
                            GradeCol = "S10E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("11")) {
                            MarksCol = "S11E3Marks";
                            GradeCol = "S11E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("12")) {
                            MarksCol = "S12E3Marks";
                            GradeCol = "S12E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("13")) {
                            MarksCol = "S13E3Marks";
                            GradeCol = "S13E3Grade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("14")) {
                            MarksCol = "S14E3Marks";
                            GradeCol = "S14E3Grade";
                        }
                        getSubjectChampionsForThisSubject(Form, Year, Term, SubjectName, NumberOfChamps, MarksCol, GradeCol);
                    }
                    break;
//</editor-fold>
                case "END TERM"://<editor-fold defaultstate="collapsed" desc="comment">
                    while (rs.next()) {
                        String SubjectName = rs.getString("Subject_name");
                        String SubjectNumber = rs.getString("S_NO");
                        System.out.println(SubjectName);
                        if (SubjectNumber.equalsIgnoreCase("1")) {
                            MarksCol = "S1AVGMarks";
                            GradeCol = "S1AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("2")) {
                            MarksCol = "S2AVGMarks";
                            GradeCol = "S2AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("3")) {
                            MarksCol = "S3AVGMarks";
                            GradeCol = "S3AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("4")) {
                            MarksCol = "S4AVGMarks";
                            GradeCol = "S4AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("5")) {
                            MarksCol = "S5AVGMarks";
                            GradeCol = "S5AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("6")) {
                            MarksCol = "S6AVGMarks";
                            GradeCol = "S6AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("7")) {
                            MarksCol = "S7AVGMarks";
                            GradeCol = "S7AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("8")) {
                            MarksCol = "S8AVGMarks";
                            GradeCol = "S8AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("9")) {
                            MarksCol = "S9AVGMarks";
                            GradeCol = "S9AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("10")) {
                            MarksCol = "S10AVGMarks";
                            GradeCol = "S10AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("11")) {
                            MarksCol = "S11AVGMarks";
                            GradeCol = "S11AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("12")) {
                            MarksCol = "S12AVGMarks";
                            GradeCol = "S12AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("13")) {
                            MarksCol = "S13AVGMarks";
                            GradeCol = "S13AVGGrade";
                        }
                        if (SubjectNumber.equalsIgnoreCase("14")) {
                            MarksCol = "S14AVGMarks";
                            GradeCol = "S14AVGGrade";
                        }
                        getSubjectChampionsForThisSubject(Form, Year, Term, SubjectName, NumberOfChamps, MarksCol, GradeCol);
                    }
                    break;
//</editor-fold>
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void getSubjectChampionsForThisSubject(String Form, String Year, String Term, String SubjectName, String NumberOfChamps, String MarksCol, String GradeCol) {
        try {
            sql = "Select students_exams.*,Student_details.* from students_exams INNER JOIN Student_details ON (Student_id=SE_Student_id) Where(SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "' AND " + MarksCol + "+0>0) ORDER BY " + MarksCol + "+0 DESC LIMIT " + NumberOfChamps + "";
            pst = conn.prepareStatement(sql);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                String StudentId = rst.getString("Student_id");
                String StudentClass = rst.getString("Student_Class");
                String StudentName = rst.getString("Student_name");
                String Marks = rst.getString(MarksCol);
                String Grade = rst.getString(GradeCol);
                sql = "INSERT INTO SubjectChampions (SubjectName,StudentID,StudentName,StudentClass,Marks,Grade) Values ('" + SubjectName + "',"
                        + "'" + StudentId + "','" + StudentName + "','" + StudentClass + "','" + Marks + "','" + Grade + "')";
                pst = conn.prepareStatement(sql);
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }

    }

    private void printSubjectCHamps(String Form, String Year, String Term, String Exam, String NumberOfChamps) {
        reportName = "SubjectTopStudents";

        HashMap param = new HashMap();
        param.put("ExamForm", Form);
        param.put("ExamYear", Year);
        param.put("ExamTerm", Term);
        param.put("ExamName", Exam);
        param.put("NoChamps", NumberOfChamps);
        param.put("bgImage", null);

        reportTitle = "Form " + Form + " Subject CHampions";
        showReport(param);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ReportDlg = new javax.swing.JDialog();
        jProgressBar1 = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        comboForm = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        comboTerm = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        comboExam = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbNumberOfChamps = new javax.swing.JComboBox<>();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        cmbYear = new javax.swing.JComboBox<>();

        ReportDlg.setResizable(false);
        ReportDlg.setType(java.awt.Window.Type.UTILITY);
        ReportDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ReportDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jProgressBar1.setIndeterminate(true);

        lblStatus.setText("Running please wait...");

        javax.swing.GroupLayout ReportDlgLayout = new javax.swing.GroupLayout(ReportDlg.getContentPane());
        ReportDlg.getContentPane().setLayout(ReportDlgLayout);
        ReportDlgLayout.setHorizontalGroup(
            ReportDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportDlgLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(ReportDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        ReportDlgLayout.setVerticalGroup(
            ReportDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportDlgLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Print Reports");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Print the Following System Reports", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 0, 153))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("FORM");

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Form" }));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("YEAR");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("TERM");

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
        comboTerm.setToolTipText("");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("EXAM");

        comboExam.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboExam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "EXAM 1", "EXAM 2", "EXAM 3" }));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("1. Marks Sheets");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("2. Marks Confirmation Sheets");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("3. Exam Result Slip");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("4. Exam Stream Ranking List");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusPainted(false);
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("5. Exam Overall Ranking List");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setFocusPainted(false);
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("6. Endterm Stream Ranking List");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setFocusPainted(false);
        jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("7. Endterm Overall Ranking List");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton7.setContentAreaFilled(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusPainted(false);
        jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton8.setText("8. Report Forms");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton8.setContentAreaFilled(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusPainted(false);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton9.setText("9. Exam Subject Performance");
        jButton9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton9.setContentAreaFilled(false);
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.setFocusPainted(false);
        jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setText("10. Endterm Subject Perfomance");
        jButton10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setFocusPainted(false);
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setText("11. Exam Classes Ranking");
        jButton11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton11.setContentAreaFilled(false);
        jButton11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton11.setFocusPainted(false);
        jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setText("12. Endterm Classes Ranking");
        jButton12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton12.setContentAreaFilled(false);
        jButton12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton12.setFocusPainted(false);
        jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Subject Champions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel3.setText("Number of Champions");

        cmbNumberOfChamps.setEditable(true);
        cmbNumberOfChamps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setText("Print for this Exam");
        jButton13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton13.setContentAreaFilled(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton13.setFocusPainted(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 255, 255));
        jButton14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton14.setText("Print for End Term");
        jButton14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton14.setContentAreaFilled(false);
        jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton14.setFocusPainted(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbNumberOfChamps, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbNumberOfChamps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        cmbYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8)
                    .addComponent(jButton2))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton6))
                .addGap(24, 24, 24)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = "END TERM";
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Endterm subject perfomance...");
            Report_Request = "ensubper";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the marks sheet...");
            Report_Request = "ms";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Level of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Report_Request = "mcs";
            lblStatus.setText("Preparing the marks confirmation sheets...");
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Level of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the result slip...");
            Report_Request = "rs";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Level of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Exam stream ranking list...");
            Report_Request = "exsr";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        ExamFormLevel = Form.replaceAll(" ", "").substring(0, 1);
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Level of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the exam overall ranking list...");
            Report_Request = "exor";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the endterm ranking list...");
            Report_Request = "ensr";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        String Exam = "END TERM";
        String ExamFormLevel = Form.replaceAll(" ", "").substring(0, 1);
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Endterm class ranking...");
            Report_Request = "enclassper";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        ExamFormLevel = Form.replaceAll(" ", "").substring(0, 1);
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Endterm Overall ranking...");
            Report_Request = "enor";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the report Forms...");
            Report_Request = "rf";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the exam Subject perfomance");
            Report_Request = "exsubper";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        ExamFormLevel = Form.replaceAll(" ", "").substring(0, 1);
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Exam class ranking...");
            Report_Request = "exclassper";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        NumberOfChamps = cmbNumberOfChamps.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Exam subject champions...");
            Report_Request = "extop";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = "END TERM";
        NumberOfChamps = cmbNumberOfChamps.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form")) {
            JOptionPane.showMessageDialog(null, "Select the Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Year.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term of examination", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            lblStatus.setText("Preparing the Endterm subject champions...");
            Report_Request = "entop";
            ReportDlg.pack();
            ReportDlg.setLocationRelativeTo(this);
            ReportDlg.setVisible(true);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void ReportDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ReportDlgWindowGainedFocus
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                switch (Report_Request) {
                    case "ms":
                        printMarksSheet(Form);
                        break;
                    case "mcs":
                        printMarksConfirmationSheet(Form, Year, Term, Exam);
                        break;
                    case "rs":
                        printResultSlips(Form, Year, Term, Exam);
                        break;
                    case "exsr":
                        printExamStreamRankingList(Form, Year, Term, Exam);
                        break;
                    case "exor":
                        printExamOverallRankingList(Form, Year, Term, Exam, ExamFormLevel);
                        break;
                    case "ensr":
                        printTermStreamRankingList(Form, Year, Term);
                        break;
                    case "enor":
                        printTermOverallRankingList(Form, Year, Term, ExamFormLevel);
                        break;
                    case "rf":
                        printReportForms(Form, Year, Term);
                        break;
                    case "exsubper":
                        AnalyseSubjectPerformance(Form, Year, Term, Exam);
                        setTheSubjectPosition();
                        printExamSubjectPerfomance(Form, Year, Term, Exam);
                        break;
                    case "ensubper":
                        AnalyseSubjectPerformance(Form, Year, Term, Exam);
                        setTheSubjectPosition();
                        printExamSubjectPerfomance(Form, Year, Term, Exam);
                        break;
                    case "exclassper":
                        AnalyzeClassesPerfomance(Form, Year, Term, Exam, ExamFormLevel);
                        setTheClassPosition(ExamFormLevel);
                        printClassPerformance(ExamFormLevel, Year, Term, Exam);
                        break;
                    case "enclassper":
                        AnalyzeClassesPerfomance(Form, Year, Term, Exam, ExamFormLevel);
                        setTheClassPosition(ExamFormLevel);
                        printClassPerformance(ExamFormLevel, Year, Term, Exam);
                        break;
                    case "extop":
                        AnalyzeSubjectChampions(Form, Year, Term, Exam, NumberOfChamps);
                        printSubjectCHamps(Form, Year, Term, Exam, NumberOfChamps);
                        break;
                    case "entop":
                        AnalyzeSubjectChampions(Form, Year, Term, Exam, NumberOfChamps);
                        printSubjectCHamps(Form, Year, Term, Exam, NumberOfChamps);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Selected report CODE does not exits", "Error", 1);
                        break;
                }
                return null;
            }

            @Override
            protected void done() {
                ReportDlg.setVisible(false);
            }
        };
        worker.execute();
    }//GEN-LAST:event_ReportDlgWindowGainedFocus

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowDeactivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReportsFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ReportsFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ReportDlg;
    private javax.swing.JComboBox<String> cmbNumberOfChamps;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
