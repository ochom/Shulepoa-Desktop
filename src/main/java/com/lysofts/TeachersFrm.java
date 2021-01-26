package com.lysofts;

import com.lysofts.dao.TeacherDAO;
import com.lysofts.entities.Teacher;
import com.lysofts.utils.ConnClass;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TeachersFrm extends javax.swing.JFrame {

    private List<String> sal;
    private List<Teacher> teachers = new ArrayList<>();
    private TeacherDAO teacherDAO = null;
    private Teacher selectedTeacher = null;

    public TeachersFrm() {
        this.teacherDAO = new TeacherDAO();
        initComponents();

        new ConnClass().setFrameIcon(this);
        initializeSalutations();
        updateUI();
    }

    private void initializeSalutations() {
        sal = new ArrayList<>();
        sal.add("sir");
        sal.add("hon");
        sal.add("mr");
        sal.add("mrs");
        sal.add("ms");
        sal.add("sir.");
        sal.add("hon.");
        sal.add("mr.");
        sal.add("mrs.");
        sal.add("ms.");
    }

    private void Create_Initials() {
        try {
            String fullname = txtName.getText();
            String[] parts = fullname.split(" ");
            int tparts = parts.length;
            String abbre = "";
            int i = 0;
            if (tparts > 1) {
                if (sal.contains(parts[0].toLowerCase())) {
                    i = 1;
                }
            }
            if (tparts > 1) {
                while (i < parts.length) {
                    abbre += parts[i].substring(0, 1).toUpperCase() + ".";
                    i++;
                }
            }

            txtInitials.setText(abbre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AddTeacher() {
        String name = txtName.getText();
        String staffNumber = txtNO.getText();
        String initials = txtInitials.getText();
        String gender = comboGender.getSelectedItem().toString();
        String phone = txtPhone.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the Teacher's Name", "acme", 1);
        } else if (staffNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the Teacher's Staff Number", "acme", 1);
        } else if (gender.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(null, "Select the Teachers Gender", "acme", 1);
        } else if (initials.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the Teacher's INITIALS", "acme", 1);
        } else {
            if (selectedTeacher == null) {
                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setStaffNumber(staffNumber);
                teacher.setGender(gender);
                teacher.setPhone(phone);
                teacher.setInitials(initials);
                boolean success = teacherDAO.add(teacher);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Teacher details succesfully saved", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while saving the data", "Error", 0);
                }
            } else {
                Teacher teacher = selectedTeacher;
                teacher.setName(name);
                teacher.setStaffNumber(staffNumber);
                teacher.setGender(gender);
                teacher.setPhone(phone);
                teacher.setInitials(initials);
                boolean success = teacherDAO.update(teacher);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Teacher details succesfully updated", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while updating the data", "Error", 0);
                }
            }
            updateUI();
        }
    }

    private void updateUI() {
        this.teachers = teacherDAO.get();
        DefaultTableModel model = (DefaultTableModel) Table_Teachers.getModel();
        model.setRowCount(0);
        teachers.forEach(teacher -> {
            model.addRow(new Object[]{teacher.getId(), teacher.getName(), teacher.getStaffNumber()});
        });
        Table_Teachers.setModel(model);

        selectedTeacher = null;
        txtName.setText("");
        txtNO.setText("");
        txtInitials.setText("");
        txtPhone.setText("");
        comboGender.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtInitials = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Teachers = new javax.swing.JTable();
        lblPhone = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblInitials = new javax.swing.JLabel();
        lblNO = new javax.swing.JLabel();
        txtNO = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        lblInitials1 = new javax.swing.JLabel();
        comboGender = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Teachers");
        setResizable(false);
        setSize(new java.awt.Dimension(490, 320));
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtName.setFont(new java.awt.Font("Cambria", 1, 13)); // NOI18N
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        txtInitials.setFont(new java.awt.Font("Cambria", 1, 13)); // NOI18N

        txtPhone.setFont(new java.awt.Font("Cambria", 1, 13)); // NOI18N

        Table_Teachers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PK", "Name", "Code"
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
        Table_Teachers.setRowHeight(20);
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
            Table_Teachers.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        lblPhone.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblPhone.setText("Phone");

        lblName.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblName.setText("Full Name *");

        lblInitials.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblInitials.setText("Initial *");

        lblNO.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblNO.setText("Staff No. *");

        txtNO.setFont(new java.awt.Font("Cambria", 1, 13)); // NOI18N

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Delete_16x16.png"))
        );
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Save_16x16.png"))
        );
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))
        );
        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        lblInitials1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblInitials1.setText("Gender *");

        comboGender.setFont(new java.awt.Font("Cambria", 1, 13)); // NOI18N
        comboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInitials, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNO, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtInitials, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboGender, 0, 244, Short.MAX_VALUE)
                                .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblInitials1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNO, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtName, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboGender, txtInitials, txtNO, txtName, txtPhone});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addGap(5, 5, 5)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblInitials1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblInitials)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboGender, txtInitials, txtNO, txtName, txtPhone});

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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedTeacher == null) {
            JOptionPane.showMessageDialog(null, "No Teacher selected");
        } else {
            int Response = JOptionPane.showConfirmDialog(null, selectedTeacher.getName() + "'s details will be deleted permanently. Do you want to Continue ?", "Delete", JOptionPane.YES_NO_OPTION);
            if (Response == 0) {
                teacherDAO.delete(selectedTeacher.getId());
                JOptionPane.showMessageDialog(null, "Teacher details deleted from the System", "Deleted", 1);
                updateUI();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddTeacher();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        selectedTeacher = null;
        txtName.setText("");
        txtNO.setText("");
        txtPhone.setText("");
        txtInitials.setText("");
        comboGender.setSelectedItem("Select");
    }//GEN-LAST:event_btnNewActionPerformed

    private void Table_TeachersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_TeachersMouseClicked
        int row = Table_Teachers.getSelectedRow();
        int id = Integer.parseInt(Table_Teachers.getModel().getValueAt(row, 0).toString());
        selectedTeacher = teacherDAO.get(id);
        if (selectedTeacher != null) {
            txtName.setText(selectedTeacher.getName());
            txtNO.setText(selectedTeacher.getStaffNumber());
            comboGender.setSelectedItem(selectedTeacher.getGender());
            txtInitials.setText(selectedTeacher.getInitials());
            txtPhone.setText(selectedTeacher.getPhone());
        }
    }//GEN-LAST:event_Table_TeachersMouseClicked

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        Create_Initials();
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        Create_Initials();
    }//GEN-LAST:event_txtNameKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost

    }//GEN-LAST:event_txtNameFocusLost

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
            java.util.logging.Logger.getLogger(TeachersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeachersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeachersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeachersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeachersFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Teachers;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboGender;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInitials;
    private javax.swing.JLabel lblInitials1;
    private javax.swing.JLabel lblNO;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JTextField txtInitials;
    private javax.swing.JTextField txtNO;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables

}
