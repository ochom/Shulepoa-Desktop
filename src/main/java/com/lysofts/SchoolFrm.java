package com.lysofts;

import com.lysofts.dao.SchoolDAO;
import com.lysofts.dao.TeacherDAO;
import com.lysofts.entities.School;
import com.lysofts.entities.Teacher;
import com.lysofts.utils.ConnClass;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SchoolFrm extends javax.swing.JFrame {

    String Logo = "", Sign = "";
    School selectedSchool = null;
    List<Teacher> teachers;
    JDialog loadingDlg = ConnClass.loadingDlg(this);

    public SchoolFrm() {
        initComponents();
        
        new ConnClass().setFrameIcon(SchoolFrm.this);
        loadingDlg.setVisible(true);        
    }

    private void updateUI() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                selectedSchool = SchoolDAO.get();
                teachers = TeacherDAO.get();
                comboPrincipal.removeAllItems();
                comboPrincipal.addItem("Select");

                teachers.forEach(teacher -> {
                    comboPrincipal.addItem(teacher.getName());
                });

                if (selectedSchool == null) {
                    txtSchoolName.setText("");
                    txtPostalAddress.setText("");
                    txtMotto.setText("");
                    comboPrincipal.setSelectedIndex(0);
                    Logo = "";
                    Sign = "";
                    getLogo();
                    getSignature();
                } else {
                    txtSchoolName.setText(selectedSchool.getName());
                    txtPostalAddress.setText(selectedSchool.getPostalAddress());
                    txtMotto.setText(selectedSchool.getMotto());
                    if (selectedSchool.getPrincipal() == null || selectedSchool.getPrincipal().isEmpty()) {
                        comboPrincipal.setSelectedIndex(0);
                    } else {
                        comboPrincipal.setSelectedItem(selectedSchool.getPrincipal());
                    }
                    Logo = selectedSchool.getLogo();
                    Sign = selectedSchool.getSignature();
                    getLogo();
                    getSignature();
                }
                return null;
            }

            @Override
            protected void done() {
                super.done();
                loadingDlg.setVisible(false);
            }
        };
        swingWorker.run();
    }

    private void getLogo() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                BufferedImage bi;
                if (new File(Logo).exists()) {
                    try {
                        bi = ImageIO.read(new File(Logo));
                        Image dimg = bi.getScaledInstance(144, 146, Image.SCALE_SMOOTH);
                        ImageIcon img = new ImageIcon(dimg);
                        LogoImageLabel.setText("");
                        LogoImageLabel.setIcon(img);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    private void getSignature() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                BufferedImage bi;
                if (new File(Sign).exists()) {
                    try {
                        bi = ImageIO.read(new File(Sign));
                        Image dimg = bi.getScaledInstance(155, 40, Image.SCALE_SMOOTH);
                        ImageIcon img = new ImageIcon(dimg);
                        SignatureImageLabel.setText("");
                        SignatureImageLabel.setIcon(img);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSchoolName = new javax.swing.JTextField();
        txtPostalAddress = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMotto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        SignatureImageLabel = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        comboPrincipal = new javax.swing.JComboBox<>();
        LogoImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("School Details");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Institution Name");

        txtSchoolName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtPostalAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Postal Address");

        txtMotto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Institution Motto");

        SignatureImageLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        SignatureImageLabel.setForeground(new java.awt.Color(153, 153, 153));
        SignatureImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SignatureImageLabel.setText("Add Sinature");
        SignatureImageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        SignatureImageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignatureImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SignatureImageLabelMouseClicked(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))
        );
        btnSave.setText("Save");
        btnSave.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setIconTextGap(10);
        btnSave.setPreferredSize(new java.awt.Dimension(57, 25));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Principal's Name");

        comboPrincipal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        LogoImageLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LogoImageLabel.setForeground(new java.awt.Color(153, 153, 153));
        LogoImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LogoImageLabel.setText("Click To Add Logo");
        LogoImageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        LogoImageLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogoImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoImageLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(416, 416, 416)
                .addComponent(SignatureImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(252, 252, 252))
                                    .addComponent(txtSchoolName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addComponent(LogoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMotto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboPrincipal, txtMotto, txtPostalAddress, txtSchoolName});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(LogoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(SignatureImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMotto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(comboPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 46, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboPrincipal, txtMotto, txtPostalAddress, txtSchoolName});

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
        School school;
        if (selectedSchool == null) {
            school = new School();
            school.setName(txtSchoolName.getText());
            school.setPostalAddress(txtPostalAddress.getText());
            school.setMotto(txtMotto.getText());
            school.setLogo(Logo);
            school.setPrincipal(comboPrincipal.getSelectedItem().toString());
            school.setSignature(Sign);
            if (SchoolDAO.add(school)) {
                JOptionPane.showMessageDialog(null, "School details saved succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error occured while saving data", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            school = selectedSchool;
            school.setName(txtSchoolName.getText());
            school.setPostalAddress(txtPostalAddress.getText());
            school.setMotto(txtMotto.getText());
            school.setLogo(Logo);
            school.setPrincipal(comboPrincipal.getSelectedItem().toString());
            school.setSignature(Sign);
            if (SchoolDAO.update(school)) {
                JOptionPane.showMessageDialog(null, "School details updated succesfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error occured while updating data", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void SignatureImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignatureImageLabelMouseClicked
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int option = fc.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getAbsolutePath();

            filepath = filepath.replace("\\", "/");
            Sign = filepath;
            if (!(filepath.equalsIgnoreCase(null))) {
                //Load Image to label
                BufferedImage bi;
                try {
                    bi = ImageIO.read(new File(filepath));
                    Image dimg = bi.getScaledInstance(SignatureImageLabel.getWidth(), SignatureImageLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(dimg);
                    SignatureImageLabel.setText("");
                    SignatureImageLabel.setIcon(img);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }//GEN-LAST:event_SignatureImageLabelMouseClicked

    private void LogoImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoImageLabelMouseClicked
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int option = fc.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String filepath = f.getAbsolutePath();

            filepath = filepath.replace("\\", "/");
            Logo = filepath;
            if (!(filepath.equalsIgnoreCase(null))) {
                //Load Image to label
                BufferedImage bi;
                try {
                    bi = ImageIO.read(new File(filepath));
                    Image dimg = bi.getScaledInstance(LogoImageLabel.getWidth(), LogoImageLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(dimg);
                    LogoImageLabel.setText("");
                    LogoImageLabel.setIcon(img);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_LogoImageLabelMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateUI();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(SchoolFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SchoolFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SchoolFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SchoolFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SchoolFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LogoImageLabel;
    private javax.swing.JLabel SignatureImageLabel;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboPrincipal;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtMotto;
    private javax.swing.JTextField txtPostalAddress;
    private javax.swing.JTextField txtSchoolName;
    // End of variables declaration//GEN-END:variables
}
