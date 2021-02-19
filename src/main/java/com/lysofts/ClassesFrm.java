package com.lysofts;

import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.TeacherDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Teacher;
import com.lysofts.utils.ConnClass;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ClassesFrm extends javax.swing.JFrame {

    String sign = "";
    List<Classroom> classrooms;
    Classroom selectedClass;

    public ClassesFrm() {
        initComponents();

        new ConnClass().setFrameIcon(ClassesFrm.this);
        updateUI();
    }

    private void AddClass() {
        String form = comboForm.getSelectedIndex() > 0 ? comboForm.getSelectedItem().toString() : "";
        String stream = txtStream.getText().isEmpty() ? "" : " " + txtStream.getText();
        String teacher = comboTeacher.getSelectedIndex() > 0 ? comboTeacher.getSelectedItem().toString() : "";

        String name = form + stream;
        if (name == null || name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Classroom name is empty, Form is required, Stream is Optional");
        } else if (teacher == null || teacher.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Select a class teacher for this classroom");
        } else {
            Classroom classroom;
            if (selectedClass == null) {
                classroom = new Classroom();
                classroom.setName(name);
                classroom.setClassTeacher(teacher);
                classroom.setSignature(sign);
                boolean success = ClassroomDAO.add(classroom);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Classroom details succesfully saved", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while saving the data", "Error", 0);
                }
            } else {
                classroom = selectedClass;
                classroom.setName(name);
                classroom.setClassTeacher(teacher);
                classroom.setSignature(sign);
                boolean success = ClassroomDAO.update(classroom);
                if (success) {
                    JOptionPane.showMessageDialog(null, "Classroom details succesfully updated", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while updating the data", "Error", 0);
                }
            }
            updateUI();
        }
    }

    private void updateUI() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                classrooms = ClassroomDAO.get();
                List<Teacher> teachers = TeacherDAO.get();
                comboTeacher.removeAllItems();
                comboTeacher.addItem("Select");
                teachers.forEach(teacher -> {
                    comboTeacher.addItem(teacher.getName());
                });

                DefaultTableModel model = new DefaultTableModel(new String[]{"Class", "Class Teacher"}, 0);
                classrooms.forEach(classroom -> {
                    model.addRow(new Object[]{classroom.getName(), classroom.getClassTeacher()});
                });
                Table_Classes.setModel(model);
                Table_Classes.getColumn("Class").setPreferredWidth(50);
                Table_Classes.getColumn("Class Teacher").setPreferredWidth(100);

                selectedClass = null;
                comboForm.setSelectedIndex(0);
                txtStream.setText("");
                comboTeacher.setSelectedIndex(0);
                sign = "";
                signaturePic.setIcon(null);
                return null;
            }
        };
        swingWorker.run();
    }

    private void getsignature() {
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(sign));
            //Resizing the image
            Image dimg = bi.getScaledInstance(170, 70, Image.SCALE_SMOOTH);
            //Creating image icon
            ImageIcon img = new ImageIcon(dimg);
            signaturePic.setIcon(img);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboForm = new javax.swing.JComboBox<>();
        comboTeacher = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Classes = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        signaturePic = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        txtStream = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Classrooms");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4" }));

        comboTeacher.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboTeacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        Table_Classes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Table_Classes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class", "Class Teacher"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Classes.getTableHeader().setReorderingAllowed(false);
        Table_Classes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_ClassesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table_Classes);
        if (Table_Classes.getColumnModel().getColumnCount() > 0) {
            Table_Classes.getColumnModel().getColumn(0).setPreferredWidth(100);
            Table_Classes.getColumnModel().getColumn(1).setPreferredWidth(150);
        }

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 0));
        jLabel17.setText("Class Teacher");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 0));
        jLabel18.setText("Form");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 153, 0));
        jLabel19.setText("Stream Name");

        signaturePic.setBackground(new java.awt.Color(255, 255, 255));
        signaturePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnUpload.setText("Class Teacher Signature");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboTeacher, 0, 180, Short.MAX_VALUE)
                                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(signaturePic, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                    .addComponent(comboForm, 0, 277, Short.MAX_VALUE)
                                    .addComponent(txtStream)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnUpload, comboForm, comboTeacher, signaturePic});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStream, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(comboTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(signaturePic, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboForm, comboTeacher});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Table_ClassesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ClassesMouseClicked
        signaturePic.setIcon(null);
        int row = Table_Classes.getSelectedRow();
        selectedClass = classrooms.get(row);
        if (selectedClass != null) {
            String parts[] = selectedClass.getName().split(" ");
            int partCount = parts.length;
            if (partCount > 1) {
                String FormName = parts[0];
                String StreamName = parts[1];
                comboForm.setSelectedItem(FormName);
                txtStream.setText(StreamName);
            } else {
                comboForm.setSelectedItem(selectedClass.getName());
                txtStream.setText("");
            }
            comboTeacher.setSelectedItem(selectedClass.getClassTeacher());
            sign = selectedClass.getSignature();
            getsignature();
        }
    }//GEN-LAST:event_Table_ClassesMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedClass == null) {
            JOptionPane.showMessageDialog(null, "No classroom is selected");
        } else {
            int res = JOptionPane.showConfirmDialog(null, "Do you want to delete this Classroom permanently ?", "Delete", JOptionPane.YES_NO_OPTION);
            if (res == 0) {
                ClassroomDAO.delete(selectedClass.getId());
                JOptionPane.showMessageDialog(null, "Classroom data deleted");
                updateUI();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddClass();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        updateUI();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filepath = f.getAbsolutePath();

        filepath = filepath.replace("\\", "/");
        sign = filepath;
        //Load Image to label
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(filepath));
            Image dimg = bi.getScaledInstance(170, 70, Image.SCALE_SMOOTH);
            ImageIcon img = new ImageIcon(dimg);
            signaturePic.setIcon(img);
        } catch (IOException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClassesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClassesFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Classes;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTeacher;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel signaturePic;
    private javax.swing.JTextField txtStream;
    // End of variables declaration//GEN-END:variables
}
