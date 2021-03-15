package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.StudentSubjectDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Student;
import com.lysofts.entities.StudentSubject;
import com.lysofts.entities.Subject;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentsSubjectFrm extends javax.swing.JFrame {

    List<Subject> subjects = new ArrayList<>();
    List<Classroom> classrooms = new ArrayList<>();
    List<Student> students = new ArrayList<>();
    List<StudentSubject> studentSubjects = new ArrayList<>();

    Classroom selectedClassroom = null;
    Student selectedStudent = null;

    public StudentsSubjectFrm() {
        initComponents();

        ConnClass.setFrameIcon(this);
        updateUI();
    }

    private void updateUI() {
        new Thread(() -> {
            getClasses();
            getSubjects(false);
        }).start();
    }

    private void getClasses() {
        comboForm.removeAllItems();
        comboForm.addItem("Select classroom");
        classrooms = ClassroomDAO.get();
        classrooms.forEach((classroom) -> {
            comboForm.addItem(classroom.getName());
        });
    }

    private void getSubjects(boolean selected) {
        DefaultTableModel model = (DefaultTableModel) Table_Subject.getModel();
        model.setRowCount(0);
        subjects = SubjectDAO.get();
        subjects.forEach((subject) -> {
            model.addRow(new Object[]{subject.getCode(), subject.getName(), selected});
        });
    }

    private void getStudents(boolean selected) {
        DefaultTableModel model = (DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        students = StudentDAO.getInClass(selectedClassroom.getName());
        students.forEach((student) -> {
            model.addRow(new Object[]{selected, student.getRegNumber(), student.getName()});
        });
    }

    private void getStudentSubjects() {
        DefaultTableModel model = (DefaultTableModel) tableDetailSubjects.getModel();
        model.setRowCount(0);
        studentSubjects = StudentSubjectDAO.get(selectedStudent.getRegNumber());
        studentSubjects.forEach((studentSubject) -> {
            model.addRow(new Object[]{studentSubject.getSubjectCode(), studentSubject.getSubjectName()});
        });
        lblMySubjectCount.setText(String.valueOf(studentSubjects.size()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentDetailsDialog = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetailSubjects = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtADMNO = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblMySubjectCount = new javax.swing.JLabel();
        lblMySubjectCount1 = new javax.swing.JLabel();
        ProgressPane = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        ProgressNo = new javax.swing.JLabel();
        ClosingDlg = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        ProgressNo2 = new javax.swing.JLabel();
        deletingDlg = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jProgressBar3 = new javax.swing.JProgressBar();
        ProgressNo1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Subject = new javax.swing.JTable();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Students = new javax.swing.JTable();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnAssignToAll = new javax.swing.JButton();
        btnResetThisSubjectAssignment = new javax.swing.JButton();
        btnStudentDetails = new javax.swing.JButton();
        comboForm = new javax.swing.JComboBox<>();

        StudentDetailsDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        StudentDetailsDialog.setTitle("Student's Subjects");
        StudentDetailsDialog.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        StudentDetailsDialog.setLocation(new java.awt.Point(50, 50));
        StudentDetailsDialog.setMinimumSize(new java.awt.Dimension(430, 501));
        StudentDetailsDialog.setModal(true);
        StudentDetailsDialog.setResizable(false);
        StudentDetailsDialog.setSize(new java.awt.Dimension(541, 350));
        StudentDetailsDialog.setType(java.awt.Window.Type.UTILITY);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Subjects"));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        tableDetailSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODE", "Subject"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetailSubjects.getTableHeader().setReorderingAllowed(false);
        tableDetailSubjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableDetailSubjectsMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableDetailSubjects);
        if (tableDetailSubjects.getColumnModel().getColumnCount() > 0) {
            tableDetailSubjects.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 270));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 180));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("NAME");

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName.setText("Student Name");
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        txtADMNO.setEditable(false);
        txtADMNO.setBackground(new java.awt.Color(255, 255, 255));
        txtADMNO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtADMNO.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtADMNO.setText("ADM NO.");
        txtADMNO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ADM");

        lblMySubjectCount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMySubjectCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMySubjectCount.setText("COUNT");

        lblMySubjectCount1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMySubjectCount1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMySubjectCount1.setText("ENTRY");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMySubjectCount1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMySubjectCount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMySubjectCount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMySubjectCount1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout StudentDetailsDialogLayout = new javax.swing.GroupLayout(StudentDetailsDialog.getContentPane());
        StudentDetailsDialog.getContentPane().setLayout(StudentDetailsDialogLayout);
        StudentDetailsDialogLayout.setHorizontalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        StudentDetailsDialogLayout.setVerticalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ProgressPane.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ProgressPane.setModal(true);
        ProgressPane.setType(java.awt.Window.Type.UTILITY);
        ProgressPane.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ProgressPaneWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 204, 51));
        jLabel11.setText("Assigning subjects to students Please wait...");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        ProgressNo.setText("NO");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout ProgressPaneLayout = new javax.swing.GroupLayout(ProgressPane.getContentPane());
        ProgressPane.getContentPane().setLayout(ProgressPaneLayout);
        ProgressPaneLayout.setHorizontalGroup(
            ProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProgressPaneLayout.setVerticalGroup(
            ProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ClosingDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ClosingDlg.setModal(true);
        ClosingDlg.setType(java.awt.Window.Type.UTILITY);
        ClosingDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ClosingDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 51));
        jLabel13.setText("Updating your data, this may take a while...");

        jProgressBar2.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar2.setString("");
        jProgressBar2.setStringPainted(true);

        ProgressNo2.setText("NO");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ProgressNo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ProgressNo2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ClosingDlgLayout = new javax.swing.GroupLayout(ClosingDlg.getContentPane());
        ClosingDlg.getContentPane().setLayout(ClosingDlgLayout);
        ClosingDlgLayout.setHorizontalGroup(
            ClosingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ClosingDlgLayout.setVerticalGroup(
            ClosingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        deletingDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        deletingDlg.setModal(true);
        deletingDlg.setType(java.awt.Window.Type.UTILITY);
        deletingDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                deletingDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 204, 51));
        jLabel14.setText("Deleting allocated subjects. please wait...");

        jProgressBar3.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar3.setString("");
        jProgressBar3.setStringPainted(true);

        ProgressNo1.setText("NO");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProgressNo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProgressNo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout deletingDlgLayout = new javax.swing.GroupLayout(deletingDlg.getContentPane());
        deletingDlg.getContentPane().setLayout(deletingDlgLayout);
        deletingDlgLayout.setHorizontalGroup(
            deletingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deletingDlgLayout.setVerticalGroup(
            deletingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Students Subject Allocation");
        setResizable(false);
        setSize(new java.awt.Dimension(210, 266));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Subject", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 204, 0))); // NOI18N

        Table_Subject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODE", "SUBJECT", "Check"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Subject.getTableHeader().setReorderingAllowed(false);
        Table_Subject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_SubjectMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_Subject);
        if (Table_Subject.getColumnModel().getColumnCount() > 0) {
            Table_Subject.getColumnModel().getColumn(0).setPreferredWidth(2);
            Table_Subject.getColumnModel().getColumn(1).setPreferredWidth(100);
            Table_Subject.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        jCheckBox2.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 0, 204));
        jCheckBox2.setText("Select All");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assign to students", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        Table_Students.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "ADM NO", "NAME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Students.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table_Students);
        if (Table_Students.getColumnModel().getColumnCount() > 0) {
            Table_Students.getColumnModel().getColumn(0).setPreferredWidth(10);
            Table_Students.getColumnModel().getColumn(1).setPreferredWidth(20);
            Table_Students.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 153));
        jCheckBox1.setText("Select All");
        jCheckBox1.setBorder(null);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAssignToAll.setBackground(new java.awt.Color(0, 0, 153));
        btnAssignToAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAssignToAll.setForeground(new java.awt.Color(255, 255, 255));
        btnAssignToAll.setText("Assign to list");
        btnAssignToAll.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        btnAssignToAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAssignToAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignToAllActionPerformed(evt);
            }
        });

        btnResetThisSubjectAssignment.setBackground(new java.awt.Color(204, 0, 0));
        btnResetThisSubjectAssignment.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnResetThisSubjectAssignment.setForeground(new java.awt.Color(255, 255, 255));
        btnResetThisSubjectAssignment.setText("Delete allocated subjects");
        btnResetThisSubjectAssignment.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        btnResetThisSubjectAssignment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResetThisSubjectAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetThisSubjectAssignmentActionPerformed(evt);
            }
        });

        btnStudentDetails.setBackground(new java.awt.Color(0, 153, 0));
        btnStudentDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentDetails.setForeground(new java.awt.Color(255, 255, 255));
        btnStudentDetails.setText("Student details");
        btnStudentDetails.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        btnStudentDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStudentDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentDetailsActionPerformed(evt);
            }
        });

        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboForm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFormPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAssignToAll, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetThisSubjectAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAssignToAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetThisSubjectAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(733, 568));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Table_SubjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_SubjectMouseClicked

    }//GEN-LAST:event_Table_SubjectMouseClicked
    private void assignSubjects() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = 0;

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < subjects.size(); i++) {
                    if ((boolean) Table_Subject.getValueAt(i, 2)) {
                        for (int j = 0; j < students.size(); j++) {
                            if ((boolean) Table_Students.getValueAt(j, 0)) {
                                Total++;
                            }
                        }
                    }
                }

                int progress = 0;
                for (int i = 0; i < subjects.size(); i++) {
                    if ((boolean) Table_Subject.getValueAt(i, 2)) {
                        Subject subject = subjects.get(i);
                        for (int j = 0; j < students.size(); j++) {
                            if ((boolean) Table_Students.getValueAt(j, 0)) {
                                Student student = students.get(j);
                                StudentSubject studentSubject = StudentSubjectDAO.get(student.getRegNumber(), subject.getCode());
                                if (studentSubject == null) {
                                    studentSubject = new StudentSubject();
                                    studentSubject.setStudentId(student.getRegNumber());
                                    studentSubject.setSubjectCode(subject.getCode());
                                    studentSubject.setSubjectName(subject.getName());
                                    StudentSubjectDAO.add(studentSubject);
                                }
                                jLabel11.setText(String.format("Allocating %s to %s", subject.getName(), student.getName()));
                                progress++;
                                publish(progress);
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                ProgressNo.setText((int) ((progress / (Total * 1.0)) * 100) + "% Done");
                jProgressBar1.setValue((int) ((progress / (Total * 1.0)) * 100));
            }

            @Override
            protected void done() {
                jCheckBox1.setSelected(false);
                jCheckBox2.setSelected(false);
                getStudents(false);
                getSubjects(false);
                ProgressPane.dispose();
                JOptionPane.showMessageDialog(null, "Selected subjects succesfully allocated", "acme", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        worker.execute();
    }

    private void deleteAllocations() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = 0;

            @Override
            protected Void doInBackground() throws Exception {

                for (int i = 0; i < subjects.size(); i++) {
                    if ((boolean) Table_Subject.getValueAt(i, 2)) {
                        for (int j = 0; j < students.size(); j++) {
                            if ((boolean) Table_Students.getValueAt(j, 0)) {
                                Total++;
                            }
                        }
                    }
                }

                int progress = 0;
                for (int i = 0; i < subjects.size(); i++) {
                    Subject subject = subjects.get(i);
                    if ((boolean) Table_Subject.getValueAt(i, 2)) {
                        for (int j = 0; j < students.size(); j++) {
                            if ((boolean) Table_Students.getValueAt(j, 0)) {
                                Student student = students.get(j);
                                progress++;
                                publish(progress);
                                jLabel14.setText(String.format("Deleting %s For: %s", subject.getName(), student.getName()));
                                StudentSubjectDAO.delete(student.getRegNumber(), subject.getCode());
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks
            ) {
                int progress = chunks.get(chunks.size() - 1);
                ProgressNo1.setText((int) ((progress / (Total * 1.0)) * 100) + "% Done");
                jProgressBar3.setValue((int) ((progress / (Total * 1.0)) * 100));
            }

            @Override
            protected void done() {
                jCheckBox1.setSelected(false);
                jCheckBox2.setSelected(false);
                getStudents(false);
                getSubjects(false);
                deletingDlg.dispose();
                JOptionPane.showMessageDialog(null, "Selected subject allocation deleted succesfully", "acme", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        worker.execute();
    }

    private void btnAssignToAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignToAllActionPerformed
        if (selectedClassroom == null) {
            JOptionPane.showMessageDialog(null, "Select A Class", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            jProgressBar1.setIndeterminate(false);
            ProgressPane.pack();
            ProgressPane.setLocationRelativeTo(null);
            ProgressPane.setVisible(true);
        }
    }//GEN-LAST:event_btnAssignToAllActionPerformed

    private void btnResetThisSubjectAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetThisSubjectAssignmentActionPerformed
        if (selectedClassroom == null) {
            JOptionPane.showConfirmDialog(this, "Select a classroom to reset subject allocations in");
        } else {
            int res = JOptionPane.showConfirmDialog(null, "Clear Allocation of Selected Subjects in Form " + selectedClassroom.getName(), "acme", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                jProgressBar3.setIndeterminate(false);
                deletingDlg.pack();
                deletingDlg.setLocationRelativeTo(null);
                deletingDlg.setVisible(true);
            }
        }
    }//GEN-LAST:event_btnResetThisSubjectAssignmentActionPerformed

    private void tableDetailSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDetailSubjectsMousePressed
        int row = tableDetailSubjects.getSelectedRow();
        if (row >= 0) {
            StudentSubject studentSubject = studentSubjects.get(row);
            int res = JOptionPane.showConfirmDialog(null, "Remove " + studentSubject.getSubjectName() + " for this student", "acme", JOptionPane.WARNING_MESSAGE);
            if (res == 0) {
                StudentSubjectDAO.delete(studentSubject);
                getStudentSubjects();
            }
        }
    }//GEN-LAST:event_tableDetailSubjectsMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ClosingDlg.pack();
        ClosingDlg.setLocationRelativeTo(this);
        ClosingDlg.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        try {
            if (jCheckBox1.isSelected()) {
                getStudents(true);
            } else {
                getStudents(false);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnStudentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentDetailsActionPerformed
        int row = Table_Students.getSelectedRow();
        if (row >= 0) {
            selectedStudent = students.get(row);
            txtADMNO.setText(selectedStudent.getRegNumber());
            txtName.setText(selectedStudent.getName());

        }
        getStudentSubjects();
        StudentDetailsDialog.pack();
        StudentDetailsDialog.setLocationRelativeTo(null);
        StudentDetailsDialog.setVisible(true);
    }//GEN-LAST:event_btnStudentDetailsActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        boolean bool;
        try {
            if (jCheckBox2.isSelected()) {
                bool = true;
                getSubjects(bool);
            } else {
                bool = false;
                getSubjects(bool);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void ProgressPaneWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ProgressPaneWindowGainedFocus
        assignSubjects();
    }//GEN-LAST:event_ProgressPaneWindowGainedFocus

    private void ClosingDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ClosingDlgWindowGainedFocus
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Total = 0;

            @Override
            protected Void doInBackground() throws Exception {
                students = StudentDAO.get();
                students.forEach(student -> {
                    if (!student.getClassroom().equalsIgnoreCase("Completed")) {
                        Total++;
                    }
                });
                int progress = 0;
                for (Student student : students) {
                    if (!student.getClassroom().equalsIgnoreCase("Completed")) {
                        jLabel13.setText("Updating subject Entry for: " + student.getName());
                        student.setSubjectEntry(String.valueOf(StudentSubjectDAO.get(student.getRegNumber()).size()));
                        StudentDAO.update(student);
                        progress++;
                        publish(progress);
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                ProgressNo2.setText((int) ((progress / (Total * 1.0)) * 100) + "% Done");
                jProgressBar2.setValue((int) ((progress / (Total * 1.0)) * 100));
            }

            @Override
            protected void done() {
                ClosingDlg.dispose();
                StudentsSubjectFrm.this.dispose();
                new RegisterExaminationsFrm().setVisible(true);
            }
        };
        worker.execute();
    }//GEN-LAST:event_ClosingDlgWindowGainedFocus

    private void comboFormPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboFormPopupMenuWillBecomeInvisible
        int index = comboForm.getSelectedIndex();
        if (index > 0) {
            selectedClassroom = classrooms.get(index - 1);
            getStudents(false);
        }
    }//GEN-LAST:event_comboFormPopupMenuWillBecomeInvisible

    private void deletingDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_deletingDlgWindowGainedFocus
        deleteAllocations();
    }//GEN-LAST:event_deletingDlgWindowGainedFocus

    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DecorationHelper.decorateWindows(false);
            new StudentsSubjectFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ClosingDlg;
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JLabel ProgressNo1;
    private javax.swing.JLabel ProgressNo2;
    private javax.swing.JDialog ProgressPane;
    private javax.swing.JDialog StudentDetailsDialog;
    private javax.swing.JTable Table_Students;
    private javax.swing.JTable Table_Subject;
    private javax.swing.JButton btnAssignToAll;
    private javax.swing.JButton btnResetThisSubjectAssignment;
    private javax.swing.JButton btnStudentDetails;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JDialog deletingDlg;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JProgressBar jProgressBar3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMySubjectCount;
    private javax.swing.JLabel lblMySubjectCount1;
    private javax.swing.JTable tableDetailSubjects;
    private javax.swing.JTextField txtADMNO;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
