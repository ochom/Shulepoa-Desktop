
package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SchoolFrm extends javax.swing.JFrame {
    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql=null,purchaseLevel,Logo = "",Sign = "",current_school_name;
    
    public SchoolFrm() {
        initComponents();
        
        
        new ConnClass().setFrameIcon(this);
        getSchoolDetails();
        getTeachers();
        getLogo();
        getSignature(); 
    }
    private void getTeachers(){
        try{
            sql="SELECT * FROM tblTeachers";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                comboPrincipal.addItem(rs.getString("T_Name"));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void getSchoolDetails(){
        try{
            sql= "select * from tblSchool";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){                
                txtSchoolName.setText(rs.getString("School_name"));
                txtPostalAddress.setText(rs.getString("School_posta"));
                txtMotto.setText(rs.getString("School_motto"));
                    if(rs.getString("Principal_name").isEmpty()){
                        comboPrincipal.setSelectedItem("Select");
                    }else{
                        comboPrincipal.setSelectedItem(rs.getString("Principal_name"));
                    }
                Logo = rs.getString("School_logo");
                Sign = rs.getString("Principal_signature");
                current_school_name = rs.getString("School_name");
                purchaseLevel = rs.getString("Full_purchase");
            }          
        }
        catch(SQLException | HeadlessException ex){
            JOptionPane.showMessageDialog(null, ex);
        
        }
    }
    private void getLogo(){
        SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                BufferedImage bi;
                if(new File(Logo).exists()){
                    try{
                        bi = ImageIO.read(new File(Logo));
                        Image dimg = bi.getScaledInstance(144,146, Image.SCALE_SMOOTH);
                        ImageIcon img = new ImageIcon(dimg);
                        LogoImageLabel.setText("");
                        LogoImageLabel.setIcon(img); 
                    }
                    catch(IOException e){
                        System.out.println(e);
                    } 
                }
            return null;
            }
        };
        worker.execute();
    }
    private void getSignature(){
        SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                BufferedImage bi;
                if(new File(Sign).exists()){
                    try{
                        bi = ImageIO.read(new File(Sign));
                        Image dimg = bi.getScaledInstance(155,40, Image.SCALE_SMOOTH);
                        ImageIcon img = new ImageIcon(dimg);
                        SignatureImageLabel.setText("");
                        SignatureImageLabel.setIcon(img);
                    }
                    catch(IOException e){
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        btnSave.setText("Update");
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

        comboPrincipal.setEditable(true);
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

        jPanel2.setBackground(new java.awt.Color(0, 204, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Institution Details");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 0, 418, 40));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(comboPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMotto, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(LogoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SignatureImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(LogoImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SignatureImageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(15, 15, 15)
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(jLabel4))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(txtPostalAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(txtMotto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(comboPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(!current_school_name.equalsIgnoreCase(txtSchoolName.getText()) && !new ConfigFileClass().activeState(1)){
            new ConfigFileClass().setTrialEnded();
        }
        try{
            sql = "Update tblSchool SET School_name=?,School_posta=?,School_motto=?,school_logo=?,Principal_name=?,Principal_signature=?,Full_Purchase=? ";
                pst = Conn.prepareStatement(sql);
                pst.setString(1, txtSchoolName.getText());
                pst.setString(2, txtPostalAddress.getText());
                pst.setString(3, txtMotto.getText());
                pst.setString(4, Logo);
                pst.setString(5, comboPrincipal.getSelectedItem().toString());
                pst.setString(6, Sign);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "School details updated succesfully");  
            }
        catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"System error",0);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

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

    private void SignatureImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignatureImageLabelMouseClicked
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","jpeg"); 
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int option=fc.showOpenDialog(null);  
        if(option==JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            String filepath = f.getAbsolutePath();

            filepath=filepath.replace("\\", "/");
            Sign = filepath;
            if(!(filepath.equalsIgnoreCase(null))){
                //Load Image to label
                BufferedImage bi;
                try{
                    bi=ImageIO.read(new File(filepath));
                    Image dimg = bi.getScaledInstance(SignatureImageLabel.getWidth(),SignatureImageLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(dimg);
                    SignatureImageLabel.setText("");
                    SignatureImageLabel.setIcon(img); 
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }
            
        }        
    }//GEN-LAST:event_SignatureImageLabelMouseClicked

    private void LogoImageLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoImageLabelMouseClicked
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","jpeg"); 
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int option=fc.showOpenDialog(null);  
        if(option==JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            String filepath = f.getAbsolutePath();

            filepath=filepath.replace("\\", "/");
            Logo = filepath;
            if(!(filepath.equalsIgnoreCase(null))){
                //Load Image to label
                BufferedImage bi;
                try{
                    bi=ImageIO.read(new File(filepath));
                    Image dimg = bi.getScaledInstance(LogoImageLabel.getWidth(),LogoImageLabel.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(dimg);
                    LogoImageLabel.setText("");
                    LogoImageLabel.setIcon(img); 
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }            
        }     
    }//GEN-LAST:event_LogoImageLabelMouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtMotto;
    private javax.swing.JTextField txtPostalAddress;
    private javax.swing.JTextField txtSchoolName;
    // End of variables declaration//GEN-END:variables
}
