package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.dao.TeacherDAO;
import com.lysofts.dao.TeacherSubjectDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Subject;
import com.lysofts.entities.Teacher;
import com.lysofts.entities.TeacherSubject;
import com.lysofts.utils.ConnClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TeachersSubjectFrm extends javax.swing.JFrame {

    List<Teacher> teachers = new ArrayList<>();
    List<Subject> subjects = new ArrayList<>();
    List<Classroom> classrooms = new ArrayList<>();
    Teacher selectedTeacher = null;
    Map<String, Teacher> tsMap = null;

    public TeachersSubjectFrm() {
        initComponents();

        ConnClass.setFrameIcon(TeachersSubjectFrm.this);

    }

    private void updateUI() {
        Thread t = new Thread(() -> {
            getClassesForMe();
            getSubjectForMe();
            getTeachers();
        });
        t.start();
    }

    private void updateMySubjects() {
        selectedTeacher.setSubjects(TeacherSubjectDAO.get(selectedTeacher.getStaffNumber()));
        DefaultTableModel model = (DefaultTableModel) tableDetailSubjects.getModel();
        model.setRowCount(0);
        selectedTeacher.getSubjects().forEach(s -> {
            model.addRow(new Object[]{s.getSubjectCode(), s.getSubjectName(), s.getClassroom()});
        });
    }

    private void getClassesForMe() {
        classrooms = ClassroomDAO.get();
        comboFormToTeach.removeAllItems();
        comboFormToTeach.addItem("Class");
        classrooms.forEach(cl -> {
            comboFormToTeach.addItem(cl.getName());
        });
    }

    private void getSubjectForMe() {
        subjects = SubjectDAO.get();
        comboSubjectForMe.removeAllItems();
        comboSubjectForMe.addItem("Subject");
        subjects.forEach(subj -> {
            comboSubjectForMe.addItem(subj.getCode());
        });
    }

    private void getTeachers() {
        teachers = TeacherDAO.get();
        tsMap = new HashMap<>();
        DefaultTableModel model = (DefaultTableModel) Table_Teachers.getModel();
        model.setRowCount(0);
        teachers.forEach(t -> {
            model.addRow(new Object[]{t.getStaffNumber(), t.getName(), t.getInitials()});
            TeacherSubjectDAO.get(t.getStaffNumber()).forEach(ts -> {
                tsMap.put(String.format("%s_%s", ts.getSubjectCode(), ts.getClassroom()), t);
            });
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Teachers = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        comboSubjectForMe = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDetailSubjects = new javax.swing.JTable();
        comboFormToTeach = new javax.swing.JComboBox<>();
        lblNO = new javax.swing.JLabel();
        txtNO = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblInitials1 = new javax.swing.JLabel();
        txtGender = new javax.swing.JTextField();
        lblInitials = new javax.swing.JLabel();
        txtInitials = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Teachers Subject Allocation");
        setResizable(false);
        setSize(new java.awt.Dimension(490, 320));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        Table_Teachers.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        Table_Teachers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Init"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Teachers.setGridColor(new java.awt.Color(0, 153, 0));
        Table_Teachers.getTableHeader().setReorderingAllowed(false);
        Table_Teachers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_TeachersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Teachers);
        if (Table_Teachers.getColumnModel().getColumnCount() > 0) {
            Table_Teachers.getColumnModel().getColumn(0).setPreferredWidth(20);
            Table_Teachers.getColumnModel().getColumn(1).setPreferredWidth(120);
            Table_Teachers.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Teaching Subjects"));

        comboSubjectForMe.setBackground(new java.awt.Color(0, 204, 0));
        comboSubjectForMe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        comboSubjectForMe.setToolTipText("");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))
        );
        jButton3.setText("Assign this Subject Subject");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tableDetailSubjects.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableDetailSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODE", "Subject", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        jScrollPane2.setViewportView(tableDetailSubjects);
        if (tableDetailSubjects.getColumnModel().getColumnCount() > 0) {
            tableDetailSubjects.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableDetailSubjects.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        comboFormToTeach.setBackground(new java.awt.Color(0, 204, 0));
        comboFormToTeach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        comboFormToTeach.setToolTipText("");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(comboSubjectForMe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboFormToTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSubjectForMe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFormToTeach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(20, 20, 20))
        );

        lblNO.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblNO.setText("Staff No. ");

        txtNO.setEditable(false);
        txtNO.setBackground(new java.awt.Color(255, 255, 255));
        txtNO.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        txtNO.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblName.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblName.setText("Full Name");

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblInitials1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblInitials1.setText("Gender ");

        txtGender.setEditable(false);
        txtGender.setBackground(new java.awt.Color(255, 255, 255));
        txtGender.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        txtGender.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblInitials.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblInitials.setText("Initial ");

        txtInitials.setEditable(false);
        txtInitials.setBackground(new java.awt.Color(255, 255, 255));
        txtInitials.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        txtInitials.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInitials1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInitials, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNO, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInitials, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNO, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblInitials, lblInitials1, lblNO, lblName, txtGender, txtInitials, txtNO, txtName});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblName)
                        .addGap(5, 5, 5)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblInitials1)
                        .addGap(8, 8, 8)
                        .addComponent(txtGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblInitials)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblInitials, lblInitials1, lblNO, lblName, txtGender, txtInitials, txtNO, txtName});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Table_TeachersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_TeachersMouseClicked
        int row = Table_Teachers.getSelectedRow();
        if (row >= 0) {
            selectedTeacher = teachers.get(row);
            txtName.setText(selectedTeacher.getName());
            txtNO.setText(selectedTeacher.getStaffNumber());
            txtGender.setText(selectedTeacher.getGender());
            txtInitials.setText(selectedTeacher.getInitials());
        }
        updateMySubjects();
    }//GEN-LAST:event_Table_TeachersMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        evt.getID();
        if (selectedTeacher == null) {
            JOptionPane.showMessageDialog(null, "Select the teacher you want to assign the Subject to", "acme", 1);
        } else if (comboSubjectForMe.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a Subject to Assign", "acme", 1);
        } else if (comboFormToTeach.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a Class you want to Assign Subject To", "acme", 1);
        } else {
            String subjectCode = comboSubjectForMe.getSelectedItem().toString();
            String classroom = comboFormToTeach.getSelectedItem().toString();
            String subjectName = subjects.get(comboSubjectForMe.getSelectedIndex() - 1).getName();
            if (tsMap.containsKey(String.format("%s_%s", subjectCode, classroom))) {
                getToolkit().beep();
                Teacher teacher = tsMap.get(String.format("%s_%s", subjectCode, classroom));
                JOptionPane.showMessageDialog(null, String.format("%s has already been assigned to %s in Form %s", subjectName, teacher.getName(), classroom), "acme", 1);
            } else {
                TeacherSubject ts = new TeacherSubject();
                ts.setSubjectCode(subjectCode);
                ts.setSubjectName(subjectName);
                ts.setTeacherId(selectedTeacher.getStaffNumber());
                ts.setTeacherInitials(selectedTeacher.getInitials());
                ts.setClassroom(classroom);
                if (TeacherSubjectDAO.add(ts)) {
                    JOptionPane.showMessageDialog(null, "Subject Assigned to " + selectedTeacher.getName() + " Succesfully");
                    
                    tsMap.put(String.format("%s_%s", ts.getSubjectCode(), ts.getClassroom()), selectedTeacher);
                    updateMySubjects();
                } else {
                    JOptionPane.showMessageDialog(null, "Subject could not be assigned");
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tableDetailSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDetailSubjectsMousePressed
        int row = tableDetailSubjects.getSelectedRow();
        if (row >= 0) {
            TeacherSubject ts = selectedTeacher.getSubjects().get(row);
            int res = JOptionPane.showConfirmDialog(null, String.format("Remove %s in Form %s for this teacher?", ts.getSubjectName(), ts.getClassroom()), "Remove", JOptionPane.WARNING_MESSAGE);
            if (res == 0) {
                TeacherSubjectDAO.delete(ts);
                getTeachers();
                updateMySubjects();
            }
        }
    }//GEN-LAST:event_tableDetailSubjectsMousePressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateUI();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TeachersSubjectFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeachersSubjectFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeachersSubjectFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeachersSubjectFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TeachersSubjectFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Teachers;
    private javax.swing.JComboBox<String> comboFormToTeach;
    private javax.swing.JComboBox<String> comboSubjectForMe;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblInitials;
    private javax.swing.JLabel lblInitials1;
    private javax.swing.JLabel lblNO;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tableDetailSubjects;
    private javax.swing.JTextField txtGender;
    private javax.swing.JTextField txtInitials;
    private javax.swing.JTextField txtNO;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
