package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import com.lysofts.dao.ExamDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.StudentExamDAO;
import com.lysofts.dao.StudentSubjectDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.entities.Exam;
import com.lysofts.entities.Student;
import com.lysofts.entities.StudentExam;
import com.lysofts.entities.StudentSubject;
import com.lysofts.entities.Subject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class RegisterExaminationsFrm extends javax.swing.JFrame {

    List<Exam> exams = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    Map<String, Subject> subjectsMap = null;

    public RegisterExaminationsFrm() {
        initComponents();
        ConnClass.setFrameIcon(this);

        jLabel3.setVisible(false);
        jProgressBar1.setVisible(false);
        ProgressNo.setVisible(false);

    }

    private void updateUI() {
        Thread t = new Thread(() -> {

            subjectsMap = new HashMap<>();
            SubjectDAO.get().forEach(subject -> {
                subjectsMap.put(subject.getCode(), subject);
            });

            comboYear.removeAllItems();
            ExamDAO.getYears().forEach(exam -> {
                comboYear.addItem(exam.getYear());
            });

            exams = ExamDAO.get();
            if (exams.size() > 0) {
                Exam latestExam = exams.get(exams.size() - 1);
                comboTerm.setSelectedItem(latestExam.getTerm());
            }
        });
        t.start();
    }

    private Subject getSubjectNumber(String subjectCode) {
        return subjectsMap.get(subjectCode);
    }

    private void UpdateStudentsEntriesForAnExam(String Year, String Term) {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = 0;

            @Override
            protected Void doInBackground() throws Exception {
                jLabel3.setVisible(true);
                jProgressBar1.setVisible(true);
                ProgressNo.setVisible(true);
                try {
                    List<Student> students = StudentDAO.get();
                    students.forEach(student -> {
                        if (!student.getClassroom().equalsIgnoreCase("Completed")) {
                            Total++;
                        }
                    });

                    int i = 0;
                    for (Student student : students) {
                        if (!student.getClassroom().equalsIgnoreCase("Completed")) {
                            StudentExam studentExam = StudentExamDAO.get(student.getRegNumber(), student.getClassroom(), Year, Term);
                            if (studentExam == null) {
                                studentExam = new StudentExam();
                                studentExam.setStudentId(student.getRegNumber());
                                studentExam.setForm(student.getClassroom());
                                studentExam.setYear(Year);
                                studentExam.setTerm(Term);

                                List<StudentSubject> mySubjects = StudentSubjectDAO.get(student.getRegNumber());
                                for (StudentSubject mySubject : mySubjects) {
                                    Subject subject = getSubjectNumber(mySubject.getSubjectCode());
                                    try {
                                        Field codeField = studentExam.getClass().getDeclaredField(String.format("S%sCODE", subject.getNumber()));
                                        Field nameField = studentExam.getClass().getDeclaredField(String.format("S%sNAME", subject.getNumber()));
                                        codeField.setAccessible(true);
                                        nameField.setAccessible(true);
                                        codeField.set(studentExam, subject.getCode());
                                        nameField.set(studentExam, subject.getName());
                                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                                        ConnClass.printError(ex);
                                    }
                                }
                                i++;
                                StudentExamDAO.add(studentExam);
                            } else {
                                studentExam.setStudentId(student.getRegNumber());
                                studentExam.setForm(student.getClassroom());
                                studentExam.setYear(Year);
                                studentExam.setTerm(Term);

                                List<StudentSubject> mySubjects = StudentSubjectDAO.get(student.getRegNumber());

                                //Empty code and subject name fields
                                for (int j = 1; j < 15; j++) {
                                    Field codeField = studentExam.getClass().getDeclaredField(String.format("S%dCODE", j));
                                    Field nameField = studentExam.getClass().getDeclaredField(String.format("S%dNAME", j));
                                    codeField.setAccessible(true);
                                    nameField.setAccessible(true);
                                    codeField.set(studentExam, "");
                                    nameField.set(studentExam, "");
                                }

                                // set code and subject name fields
                                for (StudentSubject mySubject : mySubjects) {
                                    Subject subject = getSubjectNumber(mySubject.getSubjectCode());
                                    try {
                                        Field codeField = studentExam.getClass().getDeclaredField(String.format("S%sCODE", subject.getNumber()));
                                        Field nameField = studentExam.getClass().getDeclaredField(String.format("S%sNAME", subject.getNumber()));
                                        codeField.setAccessible(true);
                                        nameField.setAccessible(true);
                                        codeField.set(studentExam, subject.getCode());
                                        nameField.set(studentExam, subject.getName());
                                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
                                        ConnClass.printError(ex);
                                    }
                                }
                                i++;
                                StudentExamDAO.update(studentExam);
                            }
                        }
                        publish(i);
                    }
                } catch (Exception ex) {
                    ConnClass.printError(ex);
                }
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
                jLabel3.setVisible(false);
                jProgressBar1.setVisible(false);
                ProgressNo.setVisible(false);
                RegisterExaminationsFrm.this.dispose();
                new AdminPanelFrm().setVisible(true);
            }
        };
        worker.execute();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboTerm = new javax.swing.JComboBox<>();
        comboYear = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        ProgressNo = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(479, 249));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);
        setSize(new java.awt.Dimension(479, 249));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update term details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Liberation Sans", 0, 15), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Academic Year");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Academic Term");

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
        comboTerm.setToolTipText("");

        comboYear.setEditable(true);
        comboYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboYear.setToolTipText("");

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 153, 0));
        btnSave.setText("Next > >");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                            .addComponent(comboYear, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboTerm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ProgressNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ProgressNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProgressNo.setText("NO");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 51));
        jLabel3.setText("Updating Students details for this exams please wait...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(ProgressNo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(ProgressNo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String Year = comboYear.getSelectedItem().toString();
        String Term = comboTerm.getSelectedItem().toString();
        if (Year.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the Year of Examination");
        } else if (Term.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(null, "Select the current term of Examination");
        } else {
            Exam exam = new Exam();
            exam.setYear(Year);
            exam.setTerm(Term);
            exam.setName("Default");
            if(ExamDAO.getByYearAndTerm(Year, Term) == null){
                ExamDAO.add(exam);                
            }
            UpdateStudentsEntriesForAnExam(Year, Term);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateUI();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ConnClass.printError(ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            DecorationHelper.decorateWindows(false);
            new RegisterExaminationsFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
