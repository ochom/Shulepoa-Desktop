package com.lysofts;

import com.lysofts.dao.UserDAO;
import com.lysofts.entities.User;
import com.lysofts.utils.ConnClass;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class UsersFrm extends javax.swing.JFrame {

    private List<User> users = new ArrayList<>();
    private User selectedUser = null;
    JDialog loadingDlg = ConnClass.loadingDlg(this);
    
    public UsersFrm() {
        initComponents();

        new ConnClass().setFrameIcon(UsersFrm.this);
        loadingDlg.setVisible(true);        
    }


    private void AddUser() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String password = txtPassword.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the User's Name", "acme", 1);
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the User's Email", "acme", 1);
        } else if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the User's Phone", "acme", 1);
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Password is required", "acme", 1);
        } else {
            if (selectedUser == null) {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(password);
                boolean success = UserDAO.add(user);
                if (success) {
                    JOptionPane.showMessageDialog(null, "User details succesfully saved", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while saving the data", "Error", 0);
                }
            } else {
                User user = selectedUser;
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(password);
                boolean success = UserDAO.update(user);
                if (success) {
                    JOptionPane.showMessageDialog(null, "User details succesfully updated", "Succes", 1);
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
                users = UserDAO.get();
                DefaultTableModel model = (DefaultTableModel) tblUsers.getModel();
                model.setRowCount(0);
                users.forEach(user -> {
                    model.addRow(new Object[]{user.getName(), user.getPhone()});
                });
                tblUsers.setModel(model);

                selectedUser = null;
                txtName.setText("");
                txtEmail.setText("");
                txtPassword.setText("");
                txtPhone.setText("");
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        lblPhone = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblNO = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        lblInitials1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Users");
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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtName.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        txtPhone.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsers.setGridColor(new java.awt.Color(0, 153, 0));
        tblUsers.getTableHeader().setReorderingAllowed(false);
        tblUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsers);
        if (tblUsers.getColumnModel().getColumnCount() > 0) {
            tblUsers.getColumnModel().getColumn(0).setPreferredWidth(120);
            tblUsers.getColumnModel().getColumn(1).setPreferredWidth(30);
        }

        lblPhone.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblPhone.setText("Password *");

        lblName.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblName.setText("Full Name *");

        lblNO.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        lblNO.setText("Email *");

        txtEmail.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

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
        lblInitials1.setText("Phone *");

        txtPassword.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtPassword.setText("jPasswordField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                            .addComponent(lblNO, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(txtPhone)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(lblInitials1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(txtEmail)
                            .addComponent(txtPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblInitials1, lblNO, lblName, lblPhone, txtEmail, txtName, txtPassword, txtPhone});

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
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblInitials1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtEmail, txtName, txtPassword, txtPhone});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(null, "No User selected");
        } else {
            int Response = JOptionPane.showConfirmDialog(null, selectedUser.getName() + "'s details will be deleted permanently. Do you want to Continue ?", "Delete", JOptionPane.YES_NO_OPTION);
            if (Response == 0) {
                UserDAO.delete(selectedUser.getId());
                JOptionPane.showMessageDialog(null, "User details deleted from the System", "Deleted", 1);
                updateUI();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddUser();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        selectedUser = null;
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsersMouseClicked
        int row = tblUsers.getSelectedRow();
        selectedUser = users.get(row);
        if (selectedUser != null) {
            txtName.setText(selectedUser.getName());
            txtEmail.setText(selectedUser.getEmail());
            txtPassword.setText(selectedUser.getPassword());
            txtPhone.setText(selectedUser.getPhone());
        }
    }//GEN-LAST:event_tblUsersMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateUI();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(UsersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsersFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsersFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInitials1;
    private javax.swing.JLabel lblNO;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JTable tblUsers;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables

}
