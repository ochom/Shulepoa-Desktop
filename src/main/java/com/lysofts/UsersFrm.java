
package com.lysofts;
import com.lysofts.utils.ConnClass;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsersFrm extends javax.swing.JFrame {
    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql, UserID;

    public UsersFrm() {
        initComponents();
        new ConnClass().setFrameIcon(UsersFrm.this);
        getUsers();
    }
    
    private void getUsers(){
        DefaultTableModel model=(DefaultTableModel) tableUsers.getModel();
        model.setRowCount(0);
        try{
            sql="SELECT * FROM User";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                String User=rs.getString("User_name");
                String Level=rs.getString("User_level");
                model.addRow(new Object[]{User,Level});
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblStatus = new javax.swing.JLabel();
        txtPassword1 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtUserLevel = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImages(null);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 204, 51));
        jLabel6.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("System Users");
        jLabel6.setOpaque(true);

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User Name", "Security Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableUsers);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Details", 0, 0, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(0, 0, 204))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Cambria", 3, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Password");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        txtPassword.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
        });
        jPanel3.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 240, 20));

        lblStatus.setFont(new java.awt.Font("Cambria", 3, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 51, 51));
        lblStatus.setText("Waiting . . .");
        jPanel3.add(lblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 240, -1));

        txtPassword1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        txtPassword1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassword1KeyReleased(evt);
            }
        });
        jPanel3.add(txtPassword1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 240, -1));

        jLabel5.setFont(new java.awt.Font("Cambria", 3, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Confirm Password");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        txtUserName.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        jPanel3.add(txtUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 39, 240, -1));

        jLabel2.setFont(new java.awt.Font("Cambria", 3, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("User Level");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, -1));

        jLabel4.setFont(new java.awt.Font("Cambria", 3, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Username");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, -1));

        txtUserLevel.setEditable(false);
        txtUserLevel.setForeground(new java.awt.Color(204, 204, 204));
        txtUserLevel.setText("Administrator");
        jPanel3.add(txtUserLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 240, -1));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Delete_16x16.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 0), 2));
        btnDelete.setContentAreaFilled(false);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setName("btnLogin"); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        btnUpdate.setText("Save");
        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 51), 2));
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setName("btnExit"); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(98, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        setSize(new java.awt.Dimension(614, 419));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String Username = txtUserName.getText();
        String Password = txtPassword.getText();
        String Password1 = txtPassword1.getText();
        
        if(Username.isEmpty()){
            JOptionPane.showMessageDialog(null, "User name reuired","acme",1);
            return;
        }
        if(Password.isEmpty()){
            JOptionPane.showMessageDialog(null, "Password is required","acme",1);
            return;
        }        
        if(Password.equals(Password1)){
            try{
                int total = 0;
                pst = Conn.prepareStatement("Select count(*) as Total from user WHERE user_id='"+UserID+"'");
                rs = pst.executeQuery();
                if(rs.next()){
                     total = Integer.parseInt(rs.getString("Total"));                    
                }
                if(total>0){
                    int res = JOptionPane.showConfirmDialog(null,"This user already exists in the system. Do you want to update details ?","Update",0);
                    if(res==0){
                        sql="UPDATE user SET User_name=?,User_level=?,User_password=? where user_id='"+UserID+"'";
                        pst=Conn.prepareStatement(sql);
                        pst.setString(1, Username); pst.setString(2, "Administrator");
                        pst.setString(3, Password);
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "User details updated","acme",1);
                        getUsers();
                        return;
                    }
                }else{
                    sql = "insert into User (user_name,User_level,User_password)VALUES('"+Username+"','Administrator','"+Password+"')";
                    pst = Conn.prepareStatement(sql);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Registration succesful","acme",1);
                    getUsers();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error - "+e.getMessage(),"System Error",0);  
            }
        }else{
            JOptionPane.showMessageDialog(null, "Passwords did not match");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int res=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?","acme",JOptionPane.YES_NO_OPTION);
        if(res==0){
        try{
            sql = "select Count(*) as Total from user";
            pst=Conn.prepareStatement(sql);
            rs =pst.executeQuery();
            if(rs.next()){
                int Total  =Integer.parseInt(rs.getString("Total"));
                if(Total<2){
                    JOptionPane.showMessageDialog(null, "There must be at least on user in the system","amce",1);
                }else{
                    sql="DELETE FROM user WHERE user_id='"+UserID+"'";
                    pst=Conn.prepareStatement(sql);
                    pst.executeUpdate();
                    getUsers();
                }
            }
            
        }catch(SQLException e){
            System.out.println(e);
        }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtPassword1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassword1KeyReleased
        String Pass=txtPassword.getText();
        String Pass2=txtPassword1.getText();
        if(Pass.equals(Pass2)){
            lblStatus.setText("Passwords matched . . .");
            lblStatus.setForeground(Color.blue);
        }else{
            lblStatus.setText("Passwords do not match . . .");            
        }
    }//GEN-LAST:event_txtPassword1KeyReleased

    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsersMouseClicked
        int row=tableUsers.getSelectedRow();
        String Username=tableUsers.getValueAt(row, 0).toString();
        try{
            sql="SELECT * FROM USER WHERE User_name='"+Username+"'";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                UserID = rs.getString("User_id");
                txtUserName.setText(rs.getString("User_name"));
                txtUserLevel.setText(rs.getString("User_level"));
                txtPassword.setText(rs.getString("User_password"));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_tableUsersMouseClicked

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        lblStatus.setText("Waiting . . .");
        txtPassword1.setText("");
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus

    }//GEN-LAST:event_formWindowLostFocus

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
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
                if ("Metal".equals(info.getName())) {
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
        java.awt.EventQueue.invokeLater(() -> {
            new UsersFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tableUsers;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JTextField txtUserLevel;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
