package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.ExamDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.StudentExamDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.dao.TeacherSubjectDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Student;
import com.lysofts.entities.StudentExam;
import com.lysofts.entities.Subject;
import com.lysofts.entities.TeacherSubject;
import com.lysofts.utils.ConnClass;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class MarksRecordFrm extends javax.swing.JFrame {

    String Year = "", Term = "", Exam = "", Code = "";

    List<Classroom> classrooms = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    List<StudentExam> marksList;

    Classroom selectedClassroom = null;
    Subject selectedSubject = null;

    Map<String, String[]> gradeMap;

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
        List<StudentExam> studentExams;
        if (selectedClassroom != null && selectedSubject != null && comboExam.getSelectedIndex() > 0) {
            setGrading();
            studentExams = StudentExamDAO.get(selectedClassroom.getName(), Year, Term);
        } else {
            studentExams = new ArrayList<>();
        }
        marksList = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tblMarks.getModel();
        model.setRowCount(0);
        String exam = Exam.equalsIgnoreCase("Exam 1") ? "1" : Exam.equalsIgnoreCase("Exam 2") ? "2" : "3";
        studentExams.forEach(studentExam -> {
            String num = selectedSubject.getNumber();
            String admno = studentExam.getStudentId();
            Student student = StudentDAO.get(admno);
            if (!(student==null)) {
                String name = student.getName();
                try {
                    Field codeField = studentExam.getClass().getDeclaredField(String.format("S%sCODE", num));
                    Field marksField = studentExam.getClass().getDeclaredField(String.format("S%sE%sMarks", num, exam));
                    Field gradeField = studentExam.getClass().getDeclaredField(String.format("S%sE%sGrade", num, exam));
                    Field pointsField = studentExam.getClass().getDeclaredField(String.format("S%sE%sPoints", num, exam));
                    codeField.setAccessible(true);
                    Object code = codeField.get(studentExam);
                    if (!code.toString().isEmpty()) {
                        marksField.setAccessible(true);
                        gradeField.setAccessible(true);
                        pointsField.setAccessible(true);
                        marksList.add(studentExam);
                        model.addRow(new Object[]{admno, name, marksField.get(studentExam), gradeField.get(studentExam), pointsField.get(studentExam)});
                    }
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                    ConnClass.printError(ex);
                }
            }
        });
    }

    private void setGrading() {
        gradeMap = new HashMap<>();
        gradeMap.put("A", new String[]{"100", selectedSubject.getGrade1(), "12"});
        gradeMap.put("A-", new String[]{selectedSubject.getGrade1(), selectedSubject.getGrade2(), "11"});
        gradeMap.put("B+", new String[]{selectedSubject.getGrade2(), selectedSubject.getGrade3(), "10"});
        gradeMap.put("B", new String[]{selectedSubject.getGrade3(), selectedSubject.getGrade4(), "9"});
        gradeMap.put("B-", new String[]{selectedSubject.getGrade4(), selectedSubject.getGrade5(), "8"});
        gradeMap.put("C+", new String[]{selectedSubject.getGrade5(), selectedSubject.getGrade6(), "7"});
        gradeMap.put("C", new String[]{selectedSubject.getGrade6(), selectedSubject.getGrade7(), "6"});
        gradeMap.put("C-", new String[]{selectedSubject.getGrade7(), selectedSubject.getGrade8(), "5"});
        gradeMap.put("D+", new String[]{selectedSubject.getGrade8(), selectedSubject.getGrade9(), "4"});
        gradeMap.put("D", new String[]{selectedSubject.getGrade9(), selectedSubject.getGrade10(), "3"});
        gradeMap.put("D-", new String[]{selectedSubject.getGrade10(), selectedSubject.getGrade11(), "2"});
        gradeMap.put("E", new String[]{selectedSubject.getGrade11(), selectedSubject.getGrade12(), "1"});
        gradeMap.put("X", new String[]{selectedSubject.getGrade12(), "0", "0"});
    }

    private String[] getGrading(String str) {
        if (str.isEmpty()) {
            return new String[]{"", ""};
        }

        int marks = Integer.parseInt(str);
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

    private String getSubjectTeacher() {
        TeacherSubject subjectTeacher = TeacherSubjectDAO.get(selectedClassroom.getName(), selectedSubject.getCode());
        return subjectTeacher != null ? subjectTeacher.getTeacherInitials() : "";
    }

    private void validateTable() {
        if (tblMarks.getCellEditor() != null) {
            tblMarks.getCellEditor().stopCellEditing();
        }
    }

    private void saveMarks() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = marksList.size();

            @Override
            protected Void doInBackground() throws Exception {
                validateTable();

                for (int i = 0; i < marksList.size(); i++) {
                    StudentExam studentExam = marksList.get(i);
                    String marks = tblMarks.getValueAt(i, 2) == null ? "" : tblMarks.getValueAt(i, 2).toString();

                    String[] grading = getGrading(marks);
                    String grade = grading[0], points = grading[1];
                    String E = Exam.equalsIgnoreCase("Exam 1") ? "1" : Exam.equalsIgnoreCase("Exam 2") ? "2" : "3";
                    String num = selectedSubject.getNumber();
                    String teacher = getSubjectTeacher();

                    try {
                        Field marksField = studentExam.getClass().getDeclaredField(String.format("S%sE%sMarks", num, E));
                        Field gradeField = studentExam.getClass().getDeclaredField(String.format("S%sE%sGrade", num, E));
                        Field pointsField = studentExam.getClass().getDeclaredField(String.format("S%sE%sPoints", num, E));
                        Field teacherField = studentExam.getClass().getDeclaredField(String.format("S%sTeacher", num));

                        marksField.setAccessible(true);
                        gradeField.setAccessible(true);
                        pointsField.setAccessible(true);
                        teacherField.setAccessible(true);

                        marksField.set(studentExam, marks);
                        gradeField.set(studentExam, grade);
                        pointsField.set(studentExam, points);
                        teacherField.set(studentExam, teacher);

                        StudentExamDAO.update(studentExam);

                        publish(i);
                        jLabel3.setText("Student: " + studentExam.getStudentId() + " Marks Saved");
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                        ConnClass.printError(ex);
                    }
                }
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
                getStudentExams();
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
        jPanel3 = new javax.swing.JPanel();
        comboForm = new javax.swing.JComboBox<>();
        comboExam = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        comboCode = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMarks = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Marks Entry Forms");
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

        tblMarks.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblMarks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADM NO", "NAME", "MARKS", "GRADE", "POINTS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMarks.getTableHeader().setReorderingAllowed(false);
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
        btnSave.setText("Save marks");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
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
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        if (selectedClassroom == null) {
            JOptionPane.showMessageDialog(null, "Select a Class from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Year.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Year of examination", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Term.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Term from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (Exam.equalsIgnoreCase("SELECT")) {
            JOptionPane.showMessageDialog(null, "Select the Examinaiton from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else if (selectedSubject == null) {
            JOptionPane.showMessageDialog(null, "Select a Subject from drop down", "Required", JOptionPane.PLAIN_MESSAGE);
        } else {
            int res = JOptionPane.showConfirmDialog(null, "Proceed with saving Marks?", "Procced", JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                SavingmarksProgressPane.pack();
                SavingmarksProgressPane.setLocationRelativeTo(null);
                SavingmarksProgressPane.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void SavingmarksProgressPaneWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_SavingmarksProgressPaneWindowGainedFocus
        jProgressBar1.setValue(0);
        saveMarks();
    }//GEN-LAST:event_SavingmarksProgressPaneWindowGainedFocus

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
    private javax.swing.JDialog SavingmarksProgressPane;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboCode;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMarks;
    // End of variables declaration//GEN-END:variables
}
