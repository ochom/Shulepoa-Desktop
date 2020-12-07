
package main;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TeachersFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst=null;
    ResultSet rs =null;
    String sql = null;
    
    private ArrayList<String> sal;
    public TeachersFrm() {
        initComponents();
        
        new ConnClass().setFrameIcon(this);
        initializeSalutations();
        Refresh_Table();
    }
    private void initializeSalutations(){
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
    private void Create_Initials(){
        try{
            String fullname = txtName.getText();
            String[] parts = fullname.split(" ");
            int tparts = parts.length;
            String abbre = "";
            int i = 0;
            if(tparts>1){
                if(sal.contains(parts[0].toLowerCase())){
                    i = 1;
                }
            }
            if(tparts>1){
                while(i<parts.length){
                    abbre += parts[i].substring(0,1).toUpperCase()+".";
                    i++;
                }
            }
            
            txtInitials.setText(abbre);        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void AddTeacher(){
        String Tname = txtName.getText();
        String Tno = txtNO.getText();
        String Tinit = txtInitials.getText();
        String Tgender  =comboGender.getSelectedItem().toString();
        String Tphone  = txtPhone.getText();
        if(Tno.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Teacher's Staff Number","acme",1);
        }else if(Tname.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Teacher's Name","acme",1);
        }else if(Tinit.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Teacher's INITIALS","acme",1);
        }else if(Tgender.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select the Teachers Gender","acme",1);
        }else{
       try{
           sql = "SELECT Count(*) FROM tblTeachers WHERE T_code='"+Tno+"'";
           pst=Conn.prepareStatement(sql);
           rs = pst.executeQuery();
           if(rs.next()){
           int Count = Integer.parseInt(rs.getString("Count(*)"));
           if(Count>0){
               int res = JOptionPane.showConfirmDialog(null, "This Teacher's details are already saved. Do you want to update them?","acme",JOptionPane.YES_NO_OPTION);
               if(res==JOptionPane.YES_OPTION){
                    sql = "UPDATE tblteachers SET T_name=?,T_initials=?,T_phone=?,T_gender=?,T_Password=? WHERE T_code ='"+txtNO.getText()+"' ";
                    pst=Conn.prepareStatement(sql);
                    pst.setString(1, txtName.getText());
                    pst.setString(2, txtInitials.getText());
                    pst.setString(3, txtPhone.getText());
                    pst.setString(4, comboGender.getSelectedItem().toString());
                    pst.setString(5, txtPhone.getText());
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Details updated succesfully","Succes", 1);
               }
           }else{
                sql = "INSERT INTO tblteachers(T_code,T_name,T_initials,T_phone,T_gender,T_Password)VALUES(?,?,?,?,?,?)";               
                 pst=Conn.prepareStatement(sql);
                 pst.setString(1,Tno);
                 pst.setString(2, Tname);
                 pst.setString(3, Tinit);
                 pst.setString(4, Tphone);
                 pst.setString(5, Tgender);
                 pst.setString(6, Tphone);
                 pst.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Teacher details succesfully saved","Succes", 1);
             }
             }
           }catch(HeadlessException | SQLException e){
                 JOptionPane.showMessageDialog(null,"Error"+ e.getMessage(),"System Error",0);
             }
            
        }
        Refresh_Table();
    }
    private void Refresh_Table(){
        DefaultTableModel model = (DefaultTableModel)Table_Teachers.getModel();
        model.setRowCount(0);
        try{
            String sql = "SELECT T_code AS SNo,T_name AS Name,T_initials AS Initials FROM tblteachers ORDER BY SNo ASC";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String SNo = rs.getString("SNo");
                String Name = rs.getString("Name");
                String Initials = rs.getString("Initials");
                
                model.addRow(new Object[]{SNo,Name,Initials});
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
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
        setResizable(false);
        setSize(new java.awt.Dimension(490, 320));
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 204, 0));

        jLabel4.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Register Teachers");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

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
                "SNo", "Name", "Initials"
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
            Table_Teachers.getColumnModel().getColumn(0).setPreferredWidth(30);
            Table_Teachers.getColumnModel().getColumn(1).setPreferredWidth(120);
            Table_Teachers.getColumnModel().getColumn(2).setPreferredWidth(20);
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
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Delete_16x16.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Save_16x16.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Add_16x16.png"))); // NOI18N
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
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtInitials, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboGender, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPhone)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblInitials, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNO, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNO)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInitials1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblNO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblName)
                        .addGap(5, 5, 5)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInitials1)
                        .addGap(16, 16, 16)
                        .addComponent(comboGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblInitials)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 607, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String Tcode = (String)Table_Teachers.getValueAt(Table_Teachers.getSelectedRow(), 0);
        String Tname = (String)Table_Teachers.getValueAt(Table_Teachers.getSelectedRow(), 1);
        try{ 
            int Response = JOptionPane.showConfirmDialog(null, Tname+"'s details will be deleted permanently. Do you want to Continue ?", "Delete", JOptionPane.YES_NO_OPTION);
            if(Response == 0){
             sql = "DELETE FROM tblteachers where T_code ='"+Tcode+"' "; 
             pst = Conn.prepareStatement(sql);
             pst.executeUpdate();
             
             sql = "DELETE FROM tblteachersToSubjects WHERE Teacher_id ='"+Tcode+"' ";
             pst = Conn.prepareStatement(sql);             
             pst.executeUpdate();
             
             JOptionPane.showMessageDialog(null, "Teacher details deleted from the System", "Deleted", 1);             
             txtName.setText("");txtNO.setText("");txtPhone.setText("");txtInitials.setText("");
            }
        }
        catch(HeadlessException | SQLException e){
            System.out.println(e);;
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddTeacher();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtName.setText("");txtNO.setText("");txtPhone.setText("");txtInitials.setText("");
        comboGender.setSelectedItem("Select");
    }//GEN-LAST:event_btnNewActionPerformed

    private void Table_TeachersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_TeachersMouseClicked
        int row =Table_Teachers.getSelectedRow();
        String Tno  =Table_Teachers.getModel().getValueAt(row, 0).toString();
        try{          
            sql  ="SELECT * FROM tblteachers WHERE T_code ='"+Tno+"' ";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txtName.setText(rs.getString("T_name"));
                txtNO.setText(rs.getString("T_code"));
                comboGender.setSelectedItem(rs.getString("T_gender"));
                txtInitials.setText(rs.getString("T_initials"));
                txtPhone.setText(rs.getString("T_phone"));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
    }//GEN-LAST:event_Table_TeachersMouseClicked

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        Create_Initials();
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        Create_Initials();
    }//GEN-LAST:event_txtNameKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
