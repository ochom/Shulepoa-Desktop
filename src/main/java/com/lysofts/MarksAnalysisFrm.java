package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.ExamDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.StudentExamDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Student;
import com.lysofts.entities.StudentExam;
import com.lysofts.entities.Subject;
import com.lysofts.pa.QueryRunner;
import com.lysofts.utils.ConnClass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class MarksAnalysisFrm extends javax.swing.JFrame {

    String sql = "", Form = "", Year = "", Term = "", Exam = "";

    List<Classroom> classrooms = new ArrayList<>();
    List<StudentExam> studentExams = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();

    private Timer timer;
    private ActionListener al;

    public MarksAnalysisFrm() {
        initComponents();
        ConnClass.setFrameIcon(MarksAnalysisFrm.this);

        updateUI();
        runprogressBar();
    }

    private void updateUI() {
        getYears();
        getSubjects();
    }

    private void runprogressBar() {
        al = (ActionEvent e) -> {
            if ((AnalysisProg.getValue() < 100) || (AnalysisProg1.getValue() < 100)) {
                AnalysisProg.setValue(AnalysisProg.getValue() + 1);
                AnalysisProg1.setValue(AnalysisProg1.getValue() + 1);
            } else if (AnalysisProg.getValue() == 100) {
                timer.stop();
                AnalysisProg.setValue(0);
                AnalysisProg1.setValue(0);
                LowerFormsProgressDlg.dispose();
                UpperFormsProgressDlg.dispose();
                JOptionPane.showMessageDialog(null, "Analysis completed successfully", "acme", 1);
            }
        };
        timer = new Timer(25, al);
    }

    private void getYears() {
        comboYear.removeAllItems();
        comboYear.addItem("SELECT");
        ExamDAO.getYears().forEach(exam -> {
            comboYear.addItem(exam.getYear());
        });
    }

    private void getSubjects() {
        subjects = SubjectDAO.get();
    }

    private void getClassrooms() {
        classrooms = new ArrayList<>();
        ClassroomDAO.get().forEach(classroom -> {
            if (classroom.getName().substring(0, 1).equals(Form)) {
                classrooms.add(classroom);
            }
        });
    }

    private void getStudentExams() {
        DefaultTableModel model = (DefaultTableModel) tableStudentMarks.getModel();
        model.setRowCount(0);
        studentExams = StudentExamDAO.getByForm(Form, Year, Term);
        studentExams.forEach(studentExam -> {
            Student student = StudentDAO.get(studentExam.getStudentId());
            model.addRow(new Object[] { student.getRegNumber(), student.getName(), student.getClassroom() });
        });
    }

    private String[] getSubjectGrade(String subjectCode, String marks) {
        Subject subject = new Subject();
        for (Subject sub : subjects) {
            if (sub.getCode().equals(subjectCode)) {
                subject = sub;
            }
        }

        Map<String, String[]> gradeMap = new HashMap<>();
        gradeMap.put("A", new String[] { "100", subject.getGrade1(), "12" });
        gradeMap.put("A-", new String[] { subject.getGrade1(), subject.getGrade2(), "11" });
        gradeMap.put("B+", new String[] { subject.getGrade2(), subject.getGrade3(), "10" });
        gradeMap.put("B", new String[] { subject.getGrade3(), subject.getGrade4(), "9" });
        gradeMap.put("B-", new String[] { subject.getGrade4(), subject.getGrade5(), "8" });
        gradeMap.put("C+", new String[] { subject.getGrade5(), subject.getGrade6(), "7" });
        gradeMap.put("C", new String[] { subject.getGrade6(), subject.getGrade7(), "6" });
        gradeMap.put("C-", new String[] { subject.getGrade7(), subject.getGrade8(), "5" });
        gradeMap.put("D+", new String[] { subject.getGrade8(), subject.getGrade9(), "4" });
        gradeMap.put("D", new String[] { subject.getGrade9(), subject.getGrade10(), "3" });
        gradeMap.put("D-", new String[] { subject.getGrade10(), subject.getGrade11(), "2" });
        gradeMap.put("E", new String[] { subject.getGrade11(), subject.getGrade12(), "1" });
        gradeMap.put("X", new String[] { subject.getGrade12(), "0", "0" });

        return getGrading(gradeMap, Integer.parseInt(marks));
    }

    private String[] getGrading(Map<String, String[]> gradeMap, int marks) {
        String[] data = new String[2];
        for (Map.Entry<String, String[]> entry : gradeMap.entrySet()) {
            int max = Integer.parseInt(entry.getValue()[0]);
            int min = Integer.parseInt(entry.getValue()[1]);
            if (marks >= min && marks < max) {
                data[0] = entry.getKey();
                data[1] = entry.getValue()[2];
                return data;
            }
        }
        return data;
    }

    private void getExamMarksForExport() {
        DefaultTableModel model = new DefaultTableModel(new String[] { "Phone Number", "Text" }, 0);

        String exam = Exam.equalsIgnoreCase("Exam 1") ? "1" : Exam.equalsIgnoreCase("Exam 2") ? "2" : "3";
        studentExams = StudentExamDAO.getByForm(Form, Year, Term);
        studentExams.forEach(studentExam -> {
            Student student = StudentDAO.get(studentExam.getStudentId());
            String text = String.format("%s CAT %s RESULTS:", student.getName(), exam);
            for (int i = 1; i < 15; i++) {
                try {
                    Field codeField = studentExam.getClass().getDeclaredField(String.format("S%dCODE", i));
                    codeField.setAccessible(true);
                    String code = codeField.get(studentExam).toString();
                    if (!code.isEmpty()) {
                        Field marksField = studentExam.getClass()
                                .getDeclaredField(String.format("S%dE%sMarks", i, exam));
                        Field gradeField = studentExam.getClass()
                                .getDeclaredField(String.format("S%dE%sGrade", i, exam));
                        marksField.setAccessible(true);
                        gradeField.setAccessible(true);
                        String marks = marksField.get(studentExam).toString();
                        String grade = gradeField.get(studentExam).toString();
                        text += code + " " + marks + grade + ", ";
                    }
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                        | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            }
            text += " Total";
            text += exam.equals("1") ? studentExam.getExam1TotalMarks()
                    : exam.equals("2") ? studentExam.getExam2TotalMarks() : studentExam.getExam3TotalMarks();
            text += exam.equals("1") ? studentExam.getExam1MeanGrade()
                    : exam.equals("2") ? studentExam.getExam2MeanGrade() : studentExam.getExam3MeanGrade();
            model.addRow(new Object[] { student.getKinPhone(), text });
        });

        tableStudentMarks.setModel(model);
        tableStudentMarks.getColumn("Phone Number").setPreferredWidth(100);
        tableStudentMarks.getColumn("Text").setPreferredWidth(250);
    }

    private void getEndTermMarksForExport() {
        DefaultTableModel model = new DefaultTableModel(new String[] { "Phone Number", "Text" }, 0);
        studentExams = StudentExamDAO.getByForm(Form, Year, Term);
        studentExams.forEach(studentExam -> {
            Student student = StudentDAO.get(studentExam.getStudentId());
            String text = String.format("%s %s RESULTS:", student.getName(), Term);
            for (int i = 1; i < 15; i++) {
                try {
                    Field codeField = studentExam.getClass().getDeclaredField(String.format("S%dCODE", i));
                    codeField.setAccessible(true);
                    String code = codeField.get(studentExam).toString();
                    if (!code.isEmpty()) {
                        Field marksField = studentExam.getClass().getDeclaredField(String.format("S%dAVGMarks", i));
                        Field gradeField = studentExam.getClass().getDeclaredField(String.format("S%dAVGGrade", i));
                        marksField.setAccessible(true);
                        gradeField.setAccessible(true);
                        String marks = marksField.get(studentExam).toString();
                        String grade = gradeField.get(studentExam).toString();
                        text += code + " " + marks + grade + ", ";
                    }
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                        | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            }
            text += " Total";
            text += studentExam.getTOTALMarks();
            text += studentExam.getMeanGrade();
            model.addRow(new Object[] { student.getKinPhone(), text });
        });

        tableStudentMarks.setModel(model);
        tableStudentMarks.getColumn("Phone Number").setPreferredWidth(100);
        tableStudentMarks.getColumn("Text").setPreferredWidth(250);
    }

    private void populateSubjectMeanMarks() {
        lblProgress.setText("Calculating subject mean marks");
        lblProgress2.setText("Calculating subject mean marks");
        try {
            int E = Exam.equalsIgnoreCase("Exam 1") ? 1 : Exam.equalsIgnoreCase("Exam 2") ? 2 : 3;
            for (StudentExam exam : studentExams) {
                for (int i = 1; i < 15; i++) {
                    Field codeField = exam.getClass().getDeclaredField(String.format("S%dCODE", i, 1));
                    codeField.setAccessible(true);
                    String subjectCode = codeField.get(exam).toString();

                    Field subjectTeacherField = exam.getClass().getDeclaredField(String.format("S%dTeacher", i));
                    Field marks1Field = exam.getClass().getDeclaredField(String.format("S%dE%dMarks", i, 1));
                    Field marks2Field = exam.getClass().getDeclaredField(String.format("S%dE%dMarks", i, 2));
                    Field marks3Field = exam.getClass().getDeclaredField(String.format("S%dE%dMarks", i, 3));

                    Field avgMarksField = exam.getClass().getDeclaredField(String.format("S%dAVGMarks", i));
                    Field avgGradeField = exam.getClass().getDeclaredField(String.format("S%dAVGGrade", i));
                    Field avgMPointsField = exam.getClass().getDeclaredField(String.format("S%dAVGPoints", i));

                    subjectTeacherField.setAccessible(true);
                    marks1Field.setAccessible(true);
                    marks2Field.setAccessible(true);
                    marks3Field.setAccessible(true);
                    avgMarksField.setAccessible(true);
                    avgGradeField.setAccessible(true);
                    avgMarksField.setAccessible(true);
                    avgMPointsField.setAccessible(true);

                    if (subjectCode.isEmpty() || subjectCode == null) {
                        subjectTeacherField.set(exam, "");
                        avgMarksField.set(exam, "");
                        avgGradeField.set(exam, "");
                        avgMPointsField.set(exam, "");
                    } else {

                        int cat1 = marks1Field.get(exam).toString().isEmpty() ? 0
                                : Integer.parseInt(marks1Field.get(exam).toString());
                        int cat2 = marks2Field.get(exam).toString().isEmpty() ? 0
                                : Integer.parseInt(marks2Field.get(exam).toString());
                        int cat3 = marks3Field.get(exam).toString().isEmpty() ? 0
                                : Integer.parseInt(marks3Field.get(exam).toString());

                        Map<Integer, Integer> marksMap = new HashMap<>();
                        marksMap.put(1, cat1);
                        marksMap.put(2, (int) ((cat1 + cat2) / 2.0));
                        marksMap.put(3, (int) ((cat1 + cat2 + cat3) / 3.0));

                        int avgMarks = marksMap.get(E);

                        String grades[] = getSubjectGrade(subjectCode, String.valueOf(avgMarks));
                        String avgGrade = grades[0];
                        String avgPoints = grades[1];
                        avgMarksField.set(exam, String.valueOf(avgMarks));
                        avgGradeField.set(exam, avgGrade);
                        avgMPointsField.set(exam, avgPoints);
                    }
                }
                StudentExamDAO.update(exam);
            }

        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
            ConnClass.printError(ex);
        }
    }

    private void populateSubjectRemarks() {
        lblProgress.setText("Remarking subjects perfomance");
        lblProgress2.setText("Remarking subjects perfomance");
        sql = "UPDATE students_exams SET S1Remarks=(CASE WHEN S1AVGPoints+0>11 THEN 'Excellent' WHEN S1AVGPoints+0>10 THEN 'Very Good' WHEN S1AVGPoints+0>9 THEN 'Good' WHEN S1AVGPoints+0>8 THEN 'Good' WHEN S1AVGPoints+0>7 THEN 'Average' WHEN S1AVGPoints+0>6 THEN 'Average' WHEN S1AVGPoints+0>5 THEN 'Average' WHEN S1AVGPoints+0>4 THEN 'Put more effort' WHEN S1AVGPoints+0>3 THEN 'Put more effort' WHEN S1AVGPoints+0>2 THEN 'Put more effort' WHEN S1AVGPoints+0>1 THEN 'Put more effort' WHEN S1AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S2Remarks=(CASE WHEN S2AVGPoints+0>11 THEN 'Excellent' WHEN S2AVGPoints+0>10 THEN 'Very Good' WHEN S2AVGPoints+0>9 THEN 'Good' WHEN S2AVGPoints+0>8 THEN 'Good' WHEN S2AVGPoints+0>7 THEN 'Average' WHEN S2AVGPoints+0>6 THEN 'Average' WHEN S2AVGPoints+0>5 THEN 'Average' WHEN S2AVGPoints+0>4 THEN 'Put more effort' WHEN S2AVGPoints+0>3 THEN 'Put more effort' WHEN S2AVGPoints+0>2 THEN 'Put more effort' WHEN S2AVGPoints+0>1 THEN 'Put more effort' WHEN S2AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S3Remarks=(CASE WHEN S3AVGPoints+0>11 THEN 'Excellent' WHEN S3AVGPoints+0>10 THEN 'Very Good' WHEN S3AVGPoints+0>9 THEN 'Good' WHEN S3AVGPoints+0>8 THEN 'Good' WHEN S3AVGPoints+0>7 THEN 'Average' WHEN S3AVGPoints+0>6 THEN 'Average' WHEN S3AVGPoints+0>5 THEN 'Average' WHEN S3AVGPoints+0>4 THEN 'Put more effort' WHEN S3AVGPoints+0>3 THEN 'Put more effort' WHEN S3AVGPoints+0>2 THEN 'Put more effort' WHEN S3AVGPoints+0>1 THEN 'Put more effort' WHEN S3AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S4Remarks=(CASE WHEN S4AVGPoints+0>11 THEN 'Excellent' WHEN S4AVGPoints+0>10 THEN 'Very Good' WHEN S4AVGPoints+0>9 THEN 'Good' WHEN S4AVGPoints+0>8 THEN 'Good' WHEN S4AVGPoints+0>7 THEN 'Average' WHEN S4AVGPoints+0>6 THEN 'Average' WHEN S4AVGPoints+0>5 THEN 'Average' WHEN S4AVGPoints+0>4 THEN 'Put more effort' WHEN S4AVGPoints+0>3 THEN 'Put more effort' WHEN S4AVGPoints+0>2 THEN 'Put more effort' WHEN S4AVGPoints+0>1 THEN 'Put more effort' WHEN S4AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S5Remarks=(CASE WHEN S5AVGPoints+0>11 THEN 'Excellent' WHEN S5AVGPoints+0>10 THEN 'Very Good' WHEN S5AVGPoints+0>9 THEN 'Good' WHEN S5AVGPoints+0>8 THEN 'Good' WHEN S5AVGPoints+0>7 THEN 'Average' WHEN S5AVGPoints+0>6 THEN 'Average' WHEN S5AVGPoints+0>5 THEN 'Average' WHEN S5AVGPoints+0>4 THEN 'Put more effort' WHEN S5AVGPoints+0>3 THEN 'Put more effort' WHEN S5AVGPoints+0>2 THEN 'Put more effort' WHEN S5AVGPoints+0>1 THEN 'Put more effort' WHEN S5AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S6Remarks=(CASE WHEN S6AVGPoints+0>11 THEN 'Excellent' WHEN S6AVGPoints+0>10 THEN 'Very Good' WHEN S6AVGPoints+0>9 THEN 'Good' WHEN S6AVGPoints+0>8 THEN 'Good' WHEN S6AVGPoints+0>7 THEN 'Average' WHEN S6AVGPoints+0>6 THEN 'Average' WHEN S6AVGPoints+0>5 THEN 'Average' WHEN S6AVGPoints+0>4 THEN 'Put more effort' WHEN S6AVGPoints+0>3 THEN 'Put more effort' WHEN S6AVGPoints+0>2 THEN 'Put more effort' WHEN S6AVGPoints+0>1 THEN 'Put more effort' WHEN S6AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S7Remarks=(CASE WHEN S7AVGPoints+0>11 THEN 'Excellent' WHEN S7AVGPoints+0>10 THEN 'Very Good' WHEN S7AVGPoints+0>9 THEN 'Good' WHEN S7AVGPoints+0>8 THEN 'Good' WHEN S7AVGPoints+0>7 THEN 'Average' WHEN S7AVGPoints+0>6 THEN 'Average' WHEN S7AVGPoints+0>5 THEN 'Average' WHEN S7AVGPoints+0>4 THEN 'Put more effort' WHEN S7AVGPoints+0>3 THEN 'Put more effort' WHEN S7AVGPoints+0>2 THEN 'Put more effort' WHEN S7AVGPoints+0>1 THEN 'Put more effort' WHEN S7AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S8Remarks=(CASE WHEN S8AVGPoints+0>11 THEN 'Excellent' WHEN S8AVGPoints+0>10 THEN 'Very Good' WHEN S8AVGPoints+0>9 THEN 'Good' WHEN S8AVGPoints+0>8 THEN 'Good' WHEN S8AVGPoints+0>7 THEN 'Average' WHEN S8AVGPoints+0>6 THEN 'Average' WHEN S8AVGPoints+0>5 THEN 'Average' WHEN S8AVGPoints+0>4 THEN 'Put more effort' WHEN S8AVGPoints+0>3 THEN 'Put more effort' WHEN S8AVGPoints+0>2 THEN 'Put more effort' WHEN S8AVGPoints+0>1 THEN 'Put more effort' WHEN S8AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S9Remarks=(CASE WHEN S9AVGPoints+0>11 THEN 'Excellent' WHEN S9AVGPoints+0>10 THEN 'Very Good' WHEN S9AVGPoints+0>9 THEN 'Good' WHEN S9AVGPoints+0>8 THEN 'Good' WHEN S9AVGPoints+0>7 THEN 'Average' WHEN S9AVGPoints+0>6 THEN 'Average' WHEN S9AVGPoints+0>5 THEN 'Average' WHEN S9AVGPoints+0>4 THEN 'Put more effort' WHEN S9AVGPoints+0>3 THEN 'Put more effort' WHEN S9AVGPoints+0>2 THEN 'Put more effort' WHEN S9AVGPoints+0>1 THEN 'Put more effort' WHEN S9AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S10Remarks=(CASE WHEN S10AVGPoints+0>11 THEN 'Excellent' WHEN S10AVGPoints+0>10 THEN 'Very Good' WHEN S10AVGPoints+0>9 THEN 'Good' WHEN S10AVGPoints+0>8 THEN 'Good' WHEN S10AVGPoints+0>7 THEN 'Average' WHEN S10AVGPoints+0>6 THEN 'Average' WHEN S10AVGPoints+0>5 THEN 'Average' WHEN S10AVGPoints+0>4 THEN 'Put more effort' WHEN S10AVGPoints+0>3 THEN 'Put more effort' WHEN S10AVGPoints+0>2 THEN 'Put more effort' WHEN S10AVGPoints+0>1 THEN 'Put more effort' WHEN S10AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S11Remarks=(CASE WHEN S11AVGPoints+0>11 THEN 'Excellent' WHEN S11AVGPoints+0>10 THEN 'Very Good' WHEN S11AVGPoints+0>9 THEN 'Good' WHEN S11AVGPoints+0>8 THEN 'Good' WHEN S11AVGPoints+0>7 THEN 'Average' WHEN S11AVGPoints+0>6 THEN 'Average' WHEN S11AVGPoints+0>5 THEN 'Average' WHEN S11AVGPoints+0>4 THEN 'Put more effort' WHEN S11AVGPoints+0>3 THEN 'Put more effort' WHEN S11AVGPoints+0>2 THEN 'Put more effort' WHEN S11AVGPoints+0>1 THEN 'Put more effort' WHEN S11AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S12Remarks=(CASE WHEN S12AVGPoints+0>11 THEN 'Excellent' WHEN S12AVGPoints+0>10 THEN 'Very Good' WHEN S12AVGPoints+0>9 THEN 'Good' WHEN S12AVGPoints+0>8 THEN 'Good' WHEN S12AVGPoints+0>7 THEN 'Average' WHEN S12AVGPoints+0>6 THEN 'Average' WHEN S12AVGPoints+0>5 THEN 'Average' WHEN S12AVGPoints+0>4 THEN 'Put more effort' WHEN S12AVGPoints+0>3 THEN 'Put more effort' WHEN S12AVGPoints+0>2 THEN 'Put more effort' WHEN S12AVGPoints+0>1 THEN 'Put more effort' WHEN S12AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S13Remarks=(CASE WHEN S13AVGPoints+0>11 THEN 'Excellent' WHEN S13AVGPoints+0>10 THEN 'Very Good' WHEN S13AVGPoints+0>9 THEN 'Good' WHEN S13AVGPoints+0>8 THEN 'Good' WHEN S13AVGPoints+0>7 THEN 'Average' WHEN S13AVGPoints+0>6 THEN 'Average' WHEN S13AVGPoints+0>5 THEN 'Average' WHEN S13AVGPoints+0>4 THEN 'Put more effort' WHEN S13AVGPoints+0>3 THEN 'Put more effort' WHEN S13AVGPoints+0>2 THEN 'Put more effort' WHEN S13AVGPoints+0>1 THEN 'Put more effort' WHEN S13AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S14Remarks=(CASE WHEN S14AVGPoints+0>11 THEN 'Excellent' WHEN S14AVGPoints+0>10 THEN 'Very Good' WHEN S14AVGPoints+0>9 THEN 'Good' WHEN S14AVGPoints+0>8 THEN 'Good' WHEN S14AVGPoints+0>7 THEN 'Average' WHEN S14AVGPoints+0>6 THEN 'Average' WHEN S14AVGPoints+0>5 THEN 'Average' WHEN S14AVGPoints+0>4 THEN 'Put more effort' WHEN S14AVGPoints+0>3 THEN 'Put more effort' WHEN S14AVGPoints+0>2 THEN 'Put more effort' WHEN S14AVGPoints+0>1 THEN 'Put more effort' WHEN S14AVGPoints+0>0 THEN 'Put more effort' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";

        Map<Integer, String> params = new HashMap<>();
        params.put(1, Form);
        params.put(2, Year);
        params.put(3, Term);
        QueryRunner.update(sql, params);
    }

    private void populateStudentSubjectPositions() {
        classrooms.forEach(classroom -> {
            lblProgress.setText("Calculating Subject positions for Form :" + Form);
            lblProgress2.setText("Calculating Subject positions for Form :" + Form);

            String s1p, s2p, s3p, s4p, s5p, s6p, s7p, s8p, s9p, s10p, s11p, s12p, s13p, s14p;
            s1p = "(CASE WHEN (S1AVGMarks =' ' OR S1AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S1AVGMarks+0 > students_exams.S1AVGMarks+0 AND (students_exams.S1AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s2p = "(CASE WHEN (S2AVGMarks =' ' OR S2AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S2AVGMarks+0 > students_exams.S2AVGMarks+0 AND (students_exams.S2AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s3p = "(CASE WHEN (S3AVGMarks =' ' OR S3AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S3AVGMarks+0 > students_exams.S3AVGMarks+0 AND (students_exams.S3AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s4p = "(CASE WHEN (S4AVGMarks =' ' OR S4AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S4AVGMarks+0 > students_exams.S4AVGMarks+0 AND (students_exams.S4AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s5p = "(CASE WHEN (S5AVGMarks =' ' OR S5AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S5AVGMarks+0 > students_exams.S5AVGMarks+0 AND (students_exams.S5AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s6p = "(CASE WHEN (S6AVGMarks =' ' OR S6AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S6AVGMarks+0 > students_exams.S6AVGMarks+0 AND (students_exams.S6AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s7p = "(CASE WHEN (S7AVGMarks =' ' OR S7AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S7AVGMarks+0 > students_exams.S7AVGMarks+0 AND (students_exams.S7AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s8p = "(CASE WHEN (S8AVGMarks =' ' OR S8AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S8AVGMarks+0 > students_exams.S8AVGMarks+0 AND (students_exams.S8AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s9p = "(CASE WHEN (S9AVGMarks =' ' OR S9AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S9AVGMarks+0 > students_exams.S9AVGMarks+0 AND (students_exams.S9AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s10p = "(CASE WHEN (S10AVGMarks =' ' OR S10AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S10AVGMarks+0 > students_exams.S10AVGMarks+0 AND (students_exams.S10AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s11p = "(CASE WHEN (S11AVGMarks =' ' OR S11AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S11AVGMarks+0 > students_exams.S11AVGMarks+0 AND (students_exams.S11AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s12p = "(CASE WHEN (S12AVGMarks =' ' OR S12AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S12AVGMarks+0 > students_exams.S12AVGMarks+0 AND (students_exams.S12AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s13p = "(CASE WHEN (S13AVGMarks =' ' OR S13AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S13AVGMarks+0 > students_exams.S13AVGMarks+0 AND (students_exams.S13AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            s14p = "(CASE WHEN (S14AVGMarks =' ' OR S14AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S14AVGMarks+0 > students_exams.S14AVGMarks+0 AND (students_exams.S14AVGMarks !='') AND t2.SE_StudentClass='"
                    + classroom.getName() + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
            sql = ("UPDATE students_exams SET S1AVGPosition=" + s1p + ",S2AVGPosition=" + s2p + ",S3AVGPosition=" + s3p
                    + ",S4AVGPosition=" + s4p + ",S5AVGPosition=" + s5p + ",S6AVGPosition=" + s6p + ",S7AVGPosition="
                    + s7p + ",S8AVGPosition=" + s8p + ",S9AVGPosition=" + s9p + ",S10AVGPosition=" + s10p
                    + ",S11AVGPosition=" + s11p + ",S12AVGPosition=" + s12p + ",S13AVGPosition=" + s13p
                    + ",S14AVGPosition=" + s14p + " WHERE  (SE_StudentClass='" + classroom.getName() + "' AND Year='"
                    + Year + "' AND Term='" + Term + "')");
            QueryRunner.update(sql, null);
        });
    }

    private String getMyGrade(double p) {
        if (p > 11) {
            return "A";
        }
        if (p > 10) {
            return "A-";
        }
        if (p > 9) {
            return "B+";
        }
        if (p > 8) {
            return "B";
        }
        if (p > 7) {
            return "B-";
        }
        if (p > 6) {
            return "C+";
        }
        if (p > 5) {
            return "C";
        }
        if (p > 4) {
            return "C-";
        }
        if (p > 3) {
            return "D+";
        }
        if (p > 2) {
            return "D";
        }
        if (p > 1) {
            return "D-";
        } else {
            return "E";
        }
    }

    private String getMyGrade2(double p) {
        if (p > 11.43) {
            return "A";
        }
        if (p > 10.43) {
            return "A-";
        }
        if (p > 9.43) {
            return "B+";
        }
        if (p > 8.43) {
            return "B";
        }
        if (p > 7.43) {
            return "B-";
        }
        if (p > 6.43) {
            return "C+";
        }
        if (p > 5.43) {
            return "C";
        }
        if (p > 4.43) {
            return "C-";
        }
        if (p > 3.43) {
            return "D+";
        }
        if (p > 2.43) {
            return "D";
        }
        if (p > 1.43) {
            return "D-";
        } else {
            return "E";
        }
    }

    private void elevenSubjectAnalysis(String TYPE) {
        try {
            lblProgress2.setText("Calculating Exam Total Marks");
            int E = Exam.equalsIgnoreCase("Exam 1") ? 1 : Exam.equalsIgnoreCase("Exam 2") ? 2 : 3;
            List<StudentExam> exams = StudentExamDAO.getByForm(Form, Year, Term);
            exams.forEach(exam -> {
                try {
                    int totalMarks = 0;
                    if (TYPE.equals("CAT")) {
                        // calculate total marks
                        for (int i = 1; i < 15; i++) {
                            try {
                                String fieldName = String.format("S%dE%dMarks", i, E);
                                Field marksField = exam.getClass().getDeclaredField(fieldName);
                                marksField.setAccessible(true);
                                String marks = marksField.get(exam).toString();
                                totalMarks += marks.isEmpty() ? 0 : Integer.parseInt(marks);
                            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                                    | SecurityException ex) {
                                ConnClass.printError(ex);
                            }
                        }

                        double points = totalMarks * 12.0 / 1046;
                        String grade = getMyGrade(points);

                        Field marksField = exam.getClass().getDeclaredField(String.format("Exam%dTotalMarks", E));
                        Field pointField = exam.getClass().getDeclaredField(String.format("Exam%dMeanPoints", E));
                        Field gradeField = exam.getClass().getDeclaredField(String.format("Exam%dMeanGrade", E));

                        marksField.setAccessible(true);
                        pointField.setAccessible(true);
                        gradeField.setAccessible(true);

                        marksField.set(exam, String.valueOf(totalMarks));
                        pointField.set(exam, String.format("%.2f", points));
                        gradeField.set(exam, grade);
                    } else {
                        // calculate total marks
                        for (int i = 1; i < 15; i++) {
                            try {
                                String fieldName = String.format("S%dAVGMarks", i);
                                Field marksField = exam.getClass().getDeclaredField(fieldName);
                                marksField.setAccessible(true);
                                String marks = marksField.get(exam).toString();
                                totalMarks += marks.isEmpty() ? 0 : Integer.parseInt(marks);
                            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                                    | SecurityException ex) {
                                ConnClass.printError(ex);
                            }
                        }
                        double avgPoints = totalMarks * 12.0 / 1046;
                        String avgGrade = getMyGrade(avgPoints);

                        exam.setTOTALMarks(String.valueOf((int) totalMarks));
                        exam.setMeanPoints(String.format("%.2f", avgPoints));
                        exam.setMeanGrade(avgGrade);
                    }

                    exam.setTotalOutOf("1100");
                    StudentExamDAO.update(exam);

                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                        | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            });

        } catch (Exception ex) {
            ConnClass.printError(ex);
        }
    }

    private int calculateTotalPoints(int[] S) {
        int GroupA = S[0] + S[1] + S[2];
        int FirstOfGroupBPicked;
        int GroupBRemainder;
        int FirstOfGroupCPicked;
        int GroupCRemainders;
        int GroupBCDRemainderPicked;
        int GroupD = S[9] + S[10] + S[11] + S[12] + S[13];

        if ((S[3] <= S[4]) && (S[3] <= S[5])) {
            FirstOfGroupBPicked = S[4] + S[5];
            GroupBRemainder = S[3];
        } else if ((S[4] <= S[3]) && (S[4] <= S[5])) {
            FirstOfGroupBPicked = S[3] + S[5];
            GroupBRemainder = S[4];
        } else {
            FirstOfGroupBPicked = S[3] + S[4];
            GroupBRemainder = S[5];
        }

        if ((S[6] >= S[7]) && (S[6] >= S[8])) {
            FirstOfGroupCPicked = S[6];
            GroupCRemainders = S[7] + S[8];
        } else if ((S[7] >= S[6]) && (S[7] >= S[8])) {
            FirstOfGroupCPicked = S[7];
            GroupCRemainders = S[6] + S[8];
        } else {
            FirstOfGroupCPicked = S[8];
            GroupCRemainders = S[6] + S[7];
        }

        if (GroupBRemainder >= GroupCRemainders && GroupBRemainder >= GroupD) {
            GroupBCDRemainderPicked = GroupBRemainder;
        } else if (GroupCRemainders >= GroupBRemainder && GroupCRemainders > GroupD) {
            GroupBCDRemainderPicked = GroupCRemainders;
        } else {
            GroupBCDRemainderPicked = GroupD;
        }

        return GroupA + FirstOfGroupBPicked + FirstOfGroupCPicked + GroupBCDRemainderPicked;
    }

    private void sevenPointAnalysis(String TYPE) {
        int E = Exam.equalsIgnoreCase("Exam 1") ? 1 : Exam.equalsIgnoreCase("Exam 2") ? 2 : 3;
        List<StudentExam> exams = StudentExamDAO.getByForm(Form, Year, Term);
        exams.forEach(exam -> {
            int[] S = new int[14];
            if (TYPE.equals("CAT")) {
                try {
                    for (int i = 1; i < 15; i++) {
                        Field pointField = exam.getClass().getDeclaredField(String.format("S%dE%dPoints", i, E));
                        pointField.setAccessible(true);
                        String point = pointField.get(exam).toString();
                        S[i - 1] = point.isEmpty() ? 0 : Integer.parseInt(point);
                    }
                    int totalPoints = calculateTotalPoints(S);

                    double points = totalPoints / 7.0;
                    String grade = getMyGrade2(points);

                    Field marksField = exam.getClass().getDeclaredField(String.format("Exam%dTotalMarks", E));
                    Field pointField = exam.getClass().getDeclaredField(String.format("Exam%dMeanPoints", E));
                    Field gradeField = exam.getClass().getDeclaredField(String.format("Exam%dMeanGrade", E));

                    marksField.setAccessible(true);
                    pointField.setAccessible(true);
                    gradeField.setAccessible(true);

                    marksField.set(exam, String.valueOf(totalPoints));
                    pointField.set(exam, String.format("%.2f", points));
                    gradeField.set(exam, grade);
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                        | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            } else {
                try {
                    for (int i = 1; i < 15; i++) {
                        Field pointField = exam.getClass().getDeclaredField(String.format("S%dAVGPoints", i));
                        pointField.setAccessible(true);
                        String point = pointField.get(exam).toString();
                        S[i - 1] = point.isEmpty() ? 0 : Integer.parseInt(point);
                    }
                    int totalPoints = calculateTotalPoints(S);

                    double avgPoints = totalPoints / 7.0;
                    String avgGrade = getMyGrade(avgPoints);

                    exam.setTOTALMarks(String.valueOf((int) totalPoints));
                    exam.setMeanPoints(String.format("%.2f", avgPoints));
                    exam.setMeanGrade(avgGrade);

                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException
                        | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            }

            exam.setTotalOutOf("84");
            StudentExamDAO.update(exam);
        });
    }

    private void populateExamPosition() {
        if (Exam.equalsIgnoreCase("Exam 1")) {
            lblProgress.setText("Populating Exam Position for :" + Form);
            lblProgress2.setText("Populating Exam Position for :" + Form);
            String Exam1StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam1TotalMarks + 0 > Students_exams.Exam1TotalMarks + 0 AND SE_StudentClass='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            String Exam1OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam1TotalMarks + 0  > Students_exams.Exam1TotalMarks + 0 AND substr(SE_StudentClass,1,1)='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            sql = "UPDATE students_exams SET Exam1StreamPosition=" + Exam1StreamPosition + ",Exam1OverallPosition="
                    + Exam1OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year
                    + "' AND Term='" + Term + "')";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
            lblProgress.setText("Populating Exam Position for :" + Form);
            lblProgress2.setText("Populating Exam Position for :" + Form);
            String Exam2StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam2TotalMarks + 0 > Students_exams.Exam2TotalMarks + 0 AND SE_StudentClass='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            String Exam2OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam2TotalMarks + 0  > Students_exams.Exam2TotalMarks + 0 AND substr(SE_StudentClass,1,1)='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            sql = "UPDATE students_exams SET Exam2StreamPosition=" + Exam2StreamPosition + ",Exam2OverallPosition="
                    + Exam2OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year
                    + "' AND Term='" + Term + "')";
        } else {
            lblProgress.setText("Populating Exam Position for :" + Form);
            lblProgress2.setText("Populating Exam Position for :" + Form);
            String Exam3StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam3TotalMarks + 0 > Students_exams.Exam3TotalMarks + 0 AND SE_StudentClass='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            String Exam3OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam3TotalMarks + 0  > Students_exams.Exam3TotalMarks + 0 AND substr(SE_StudentClass,1,1)='"
                    + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
            sql = "UPDATE students_exams SET Exam3StreamPosition=" + Exam3StreamPosition + ",Exam3OverallPosition="
                    + Exam3OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year
                    + "' AND Term='" + Term + "')";
        }

        QueryRunner.update(sql, null);
    }

    private void EndTermPosition() {
        lblProgress.setText("Populating end term position for :" + Form);
        lblProgress2.setText("Populating end term position for :" + Form);
        String StreamPosition = "(1+(SELECT Count(*) FROM students_exams AS t2 WHERE (t2.TotalMarks + 0 > students_exams.TotalMarks + 0 AND SE_StudentClass='"
                + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
        String OverallPosition = "(1+(SELECT Count(*) FROM students_exams AS t2 WHERE (t2.TotalMarks + 0 >students_exams.TotalMarks + 0 AND substr(SE_StudentClass,1,1)='"
                + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
        String StreamOutOf = "(SELECT Count(*) FROM students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='"
                + Year + "' AND Term='" + Term + "'))";
        String OverallOutOf = "(SELECT Count(*) FROM students_exams WHERE (substr(SE_StudentClass,1,1)='" + Form
                + "' AND Year='" + Year + "' AND Term='" + Term + "'))";

        sql = "UPDATE students_exams SET StreamPosition=" + StreamPosition + ",StreamOutOf=" + StreamOutOf
                + ",OverallPosition=" + OverallPosition + ",OverallOutOf=" + OverallOutOf + " WHERE (SE_StudentClass='"
                + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        QueryRunner.update(sql, null);
    }

    private void populateEndTermRemarks() {
        lblProgress.setText("Remarking Students perfomance...");
        lblProgress2.setText("Remarking Students perfomance...");
        sql = "UPDATE students_exams SET OverallRemarks=(CASE WHEN (MeanPoints+0)>9.5 THEN 'This is a wonderful performance. Keep working on your strengths to be able to maintain it or achieve even better results' WHEN (MeanPoints+0)>7.5 THEN 'This is work well done, continue working on your weak subjects. You have the potential and can achieve better results' WHEN (MeanPoints+0)>5.5 THEN 'This is an average performance. Consult your teachers on the various subjects. You can do better than this, work harder next term.' ELSE 'Your performance is below average. Better performance is expected of you. Work hard to improve your grades.' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        Map<Integer, String> params = new HashMap<>();
        params.put(1, Form);
        params.put(2, Year);
        params.put(3, Term);
        QueryRunner.update(sql, params);
    }

    private void SaveMarksToTermlyPerfomanceOnStudentsTable() {
        List<StudentExam> exams = StudentExamDAO.getByForm(Form, Year, Term);
        String t = Term.equalsIgnoreCase("Term 1") ? "1" : Term.equalsIgnoreCase("Term 2") ? "2" : "3";
        String f = Form;
        exams.forEach(exam -> {
            String points = exam.getMeanPoints();
            String grade = exam.getMeanGrade();
            String p = exam.getOverallPosition();
            String op = exam.getOverallOutOf();
            sql = String.format(
                    "UPDATE student_details SET F%sT%sM=?,F%sT%sMG = ?, F%sT%sOP=?, F%sT%sP=? WHERE student_id=?", f, t,
                    f, t, f, t, f, t);
            Map<Integer, String> params = new HashMap<>();
            params.put(1, points);
            params.put(2, grade);
            params.put(3, op);
            params.put(4, p);
            params.put(5, exam.getStudentId());
            QueryRunner.update(sql, params);
        });

        lblProgress.setText("Refreshing...");
        lblProgress2.setText("Refreshing...");
    }

    private void AnalyzeLowerForms() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                AnalysisModeDlg.dispose();
                AnalysisProg.setIndeterminate(true);

                populateSubjectMeanMarks();
                populateStudentSubjectPositions();
                populateSubjectRemarks();

                elevenSubjectAnalysis("CAT");
                elevenSubjectAnalysis("END");

                populateExamPosition();

                EndTermPosition();
                populateEndTermRemarks();
                SaveMarksToTermlyPerfomanceOnStudentsTable();
                return null;
            }

            @Override
            protected void done() {
                AnalysisProg.setIndeterminate(false);
                AnalysisProg.setValue(0);
                timer.start();
            }
        };
        worker.execute();
    }

    private void AnalyzeUpperForms() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                AnalysisModeDlg.dispose();
                AnalysisProg1.setIndeterminate(true);

                populateSubjectMeanMarks();
                populateStudentSubjectPositions();
                populateSubjectRemarks();

                sevenPointAnalysis("CAT");
                sevenPointAnalysis("END");

                populateExamPosition();

                EndTermPosition();
                populateEndTermRemarks();
                SaveMarksToTermlyPerfomanceOnStudentsTable();
                return null;
            }

            @Override
            protected void done() {
                AnalysisProg1.setIndeterminate(false);
                AnalysisProg1.setValue(0);
                timer.start();
            }
        };
        worker.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LowerFormsProgressDlg = new javax.swing.JDialog();
        AnalysisProg = new javax.swing.JProgressBar();
        lblProgress = new javax.swing.JLabel();
        UpperFormsProgressDlg = new javax.swing.JDialog();
        AnalysisProg1 = new javax.swing.JProgressBar();
        lblProgress2 = new javax.swing.JLabel();
        AnalysisModeDlg = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnExamResult = new javax.swing.JButton();
        btnAnalyze = new javax.swing.JButton();
        comboForm = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboExam = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnTermResult = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableStudentMarks = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStudentMarks1 = new javax.swing.JTable();
        comboTerm = new javax.swing.JComboBox<>();
        btnExportToExcel = new javax.swing.JButton();
        comboYear = new javax.swing.JComboBox<>();

        LowerFormsProgressDlg.setModal(true);
        LowerFormsProgressDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                LowerFormsProgressDlgWindowGainedFocus(evt);
            }

            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblProgress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProgress.setForeground(new java.awt.Color(0, 153, 0));
        lblProgress.setText("Analysing Exams please wait . . .");

        javax.swing.GroupLayout LowerFormsProgressDlgLayout = new javax.swing.GroupLayout(
                LowerFormsProgressDlg.getContentPane());
        LowerFormsProgressDlg.getContentPane().setLayout(LowerFormsProgressDlgLayout);
        LowerFormsProgressDlgLayout.setHorizontalGroup(LowerFormsProgressDlgLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(LowerFormsProgressDlgLayout.createSequentialGroup().addContainerGap()
                        .addGroup(LowerFormsProgressDlgLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AnalysisProg, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                                .addComponent(lblProgress, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        LowerFormsProgressDlgLayout.setVerticalGroup(
                LowerFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        LowerFormsProgressDlgLayout.createSequentialGroup().addContainerGap(22, Short.MAX_VALUE)
                                .addComponent(lblProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AnalysisProg, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)));

        UpperFormsProgressDlg.setModal(true);
        UpperFormsProgressDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                UpperFormsProgressDlgWindowGainedFocus(evt);
            }

            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblProgress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProgress2.setForeground(new java.awt.Color(0, 153, 0));
        lblProgress2.setText("Analysing Exams please wait . . .");

        javax.swing.GroupLayout UpperFormsProgressDlgLayout = new javax.swing.GroupLayout(
                UpperFormsProgressDlg.getContentPane());
        UpperFormsProgressDlg.getContentPane().setLayout(UpperFormsProgressDlgLayout);
        UpperFormsProgressDlgLayout.setHorizontalGroup(UpperFormsProgressDlgLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UpperFormsProgressDlgLayout.createSequentialGroup().addContainerGap()
                        .addGroup(UpperFormsProgressDlgLayout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AnalysisProg1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                                .addComponent(lblProgress2, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        UpperFormsProgressDlgLayout.setVerticalGroup(
                UpperFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                UpperFormsProgressDlgLayout.createSequentialGroup().addContainerGap()
                                        .addComponent(lblProgress2, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(AnalysisProg1, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(44, Short.MAX_VALUE)));

        AnalysisModeDlg.setTitle("Select Analysis Type");
        AnalysisModeDlg.setModal(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("11 Subjects Analysis");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("7 Subjects Analysis");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AnalysisModeDlgLayout = new javax.swing.GroupLayout(AnalysisModeDlg.getContentPane());
        AnalysisModeDlg.getContentPane().setLayout(AnalysisModeDlgLayout);
        AnalysisModeDlgLayout
                .setHorizontalGroup(AnalysisModeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                AnalysisModeDlgLayout.createSequentialGroup().addContainerGap(31, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 266,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(40, 40, 40)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 281,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)));
        AnalysisModeDlgLayout.setVerticalGroup(AnalysisModeDlgLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AnalysisModeDlgLayout.createSequentialGroup().addGap(29, 29, 29)
                        .addGroup(AnalysisModeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE)));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exams Analysis");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnExamResult.setText("Exam Result");
        btnExamResult.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnExamResult.setContentAreaFilled(false);
        btnExamResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExamResultActionPerformed(evt);
            }
        });

        btnAnalyze.setBackground(new java.awt.Color(0, 102, 51));
        btnAnalyze.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAnalyze.setText("Analyze");
        btnAnalyze.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        btnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyzeActionPerformed(evt);
            }
        });

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[] { "Select Form", "Form 1", "Form 2", "Form 3", "Form 4" }));
        comboForm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }

            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFormPopupMenuWillBecomeInvisible(evt);
            }

            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("FORM");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("EXAMINATION");

        comboExam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboExam.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "EXAM 1", "EXAM 2", "EXAM 3" }));
        comboExam.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }

            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboExamPopupMenuWillBecomeInvisible(evt);
            }

            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("TERM");

        btnTermResult.setText("Endterm Result");
        btnTermResult.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnTermResult.setContentAreaFilled(false);
        btnTermResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTermResultActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("YEAR");

        tableStudentMarks.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

        }, new String[] { "ADM NO", "NAME", "CLASS" }) {
            boolean[] canEdit = new boolean[] { false, false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableStudentMarks.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableStudentMarks);
        if (tableStudentMarks.getColumnModel().getColumnCount() > 0) {
            tableStudentMarks.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableStudentMarks.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        tableStudentMarks1.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

        }, new String[] { "ADM NO", "NAME", "CLASS" }) {
            boolean[] canEdit = new boolean[] { false, false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tableStudentMarks1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableStudentMarks1);
        if (tableStudentMarks1.getColumnModel().getColumnCount() > 0) {
            tableStudentMarks1.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableStudentMarks1.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboTerm.setModel(
                new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
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

        btnExportToExcel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExportToExcel.setText("Export to SMS File");
        btnExportToExcel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnExportToExcel.setContentAreaFilled(false);
        btnExportToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportToExcelActionPerformed(evt);
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
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 164,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnExamResult, javax.swing.GroupLayout.PREFERRED_SIZE, 164,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnTermResult, javax.swing.GroupLayout.PREFERRED_SIZE, 164,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExportToExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(9, 9, 9)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 130,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(
                                                jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel3Layout.createSequentialGroup().addGap(18, 18, 18).addComponent(
                                                        comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, 110,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(8, Short.MAX_VALUE)));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnExportToExcel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnExamResult, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2).addComponent(btnTermResult,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 39,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap()));

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL,
                new java.awt.Component[] { btnAnalyze, btnExportToExcel });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        setSize(new java.awt.Dimension(525, 499));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAnalyzeActionPerformed
        if (comboForm.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a Form from the dropdown", "Error", 1);
        } else if (comboYear.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Enter the Year of examination", "Error", 1);
        } else if (comboTerm.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a Term from the dropdown", "Error", 1);
        } else if (comboExam.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a the Examination from the dropdown", "Error", 1);
        } else {
            getToolkit().beep();
            int res = JOptionPane.showConfirmDialog(null,
                    "Analysing " + Exam + " will affect the End Term Perfomance. Do you want to proceed?", "acme", 0);
            if (res == 0) {
                AnalysisModeDlg.pack();
                AnalysisModeDlg.setLocationRelativeTo(null);
                AnalysisModeDlg.setVisible(true);
            }
        }
    }// GEN-LAST:event_btnAnalyzeActionPerformed

    private void LowerFormsProgressDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_LowerFormsProgressDlgWindowGainedFocus
        AnalyzeLowerForms();
    }// GEN-LAST:event_LowerFormsProgressDlgWindowGainedFocus

    private void btnExportToExcelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExportToExcelActionPerformed
        JFileChooser fc = new JFileChooser();
        int option = fc.showSaveDialog(this);
        if (option == 0) {
            String filename = fc.getSelectedFile().getName();
            String path = fc.getSelectedFile().getParentFile().getPath();
            ExportTableToCSV.ExportTable(tableStudentMarks, filename, path);
        }
    }// GEN-LAST:event_btnExportToExcelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }// GEN-LAST:event_formWindowClosing

    private void btnExamResultActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExamResultActionPerformed
        getExamMarksForExport();
    }// GEN-LAST:event_btnExamResultActionPerformed

    private void btnTermResultActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTermResultActionPerformed
        getEndTermMarksForExport();
    }// GEN-LAST:event_btnTermResultActionPerformed

    private void UpperFormsProgressDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_UpperFormsProgressDlgWindowGainedFocus
        AnalyzeUpperForms();
    }// GEN-LAST:event_UpperFormsProgressDlgWindowGainedFocus

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        AnalysisModeDlg.dispose();
        AnalysisProg.setIndeterminate(true);
        LowerFormsProgressDlg.pack();
        LowerFormsProgressDlg.setLocationRelativeTo(null);
        LowerFormsProgressDlg.setVisible(true);
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
        AnalysisModeDlg.dispose();
        AnalysisProg.setIndeterminate(true);
        UpperFormsProgressDlg.pack();
        UpperFormsProgressDlg.setLocationRelativeTo(null);
        UpperFormsProgressDlg.setVisible(true);
    }// GEN-LAST:event_jButton2ActionPerformed

    private void comboTermPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {// GEN-FIRST:event_comboTermPopupMenuWillBecomeInvisible
        Form = String.valueOf(comboForm.getSelectedIndex());
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        getClassrooms();
        getStudentExams();
    }// GEN-LAST:event_comboTermPopupMenuWillBecomeInvisible

    private void comboFormPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {// GEN-FIRST:event_comboFormPopupMenuWillBecomeInvisible
        Form = String.valueOf(comboForm.getSelectedIndex());
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        getClassrooms();
        getStudentExams();
    }// GEN-LAST:event_comboFormPopupMenuWillBecomeInvisible

    private void comboYearPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {// GEN-FIRST:event_comboYearPopupMenuWillBecomeInvisible
        Form = String.valueOf(comboForm.getSelectedIndex());
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        getClassrooms();
        getStudentExams();
    }// GEN-LAST:event_comboYearPopupMenuWillBecomeInvisible

    private void comboExamPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {// GEN-FIRST:event_comboExamPopupMenuWillBecomeInvisible
        Form = String.valueOf(comboForm.getSelectedIndex());
        Year = comboYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        getClassrooms();
        getStudentExams();
    }// GEN-LAST:event_comboExamPopupMenuWillBecomeInvisible

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
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarksAnalysisFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AnalysisModeDlg;
    private javax.swing.JProgressBar AnalysisProg;
    private javax.swing.JProgressBar AnalysisProg1;
    private javax.swing.JDialog LowerFormsProgressDlg;
    private javax.swing.JDialog UpperFormsProgressDlg;
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JButton btnExamResult;
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnTermResult;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JLabel lblProgress2;
    private javax.swing.JTable tableStudentMarks;
    private javax.swing.JTable tableStudentMarks1;
    // End of variables declaration//GEN-END:variables
}
