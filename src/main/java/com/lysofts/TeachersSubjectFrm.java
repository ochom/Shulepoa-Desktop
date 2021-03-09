
package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TeachersSubjectFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst=null;
    ResultSet rs =null;
    String sql = null;
    
    public TeachersSubjectFrm() {
        initComponents();
        new ConnClass().setFrameIcon(TeachersSubjectFrm.this);
        
        getClassesForMe();
        getSubjectForMe();
    }
    
    private void updateMySubjects(){
        try{
            try{
                sql = "SELECT Subject_code,Subject_name,Teaching_class FROM tblTeachersToSubjects "
                        + "WHERE Teacher_id='"+txtNO.getText()+"' ORDER BY Subject_code ASC";
                pst=Conn.prepareStatement(sql);
                rs=pst.executeQuery();
                DefaultTableModel model = (DefaultTableModel)tableDetailSubjects.getModel();
                model.setRowCount(0);
                while(rs.next()){
                    String SubjectCode =rs.getString("Subject_code");
                    String SubjectName =rs.getString("Subject_name");
                    String TeachingClass =rs.getString("Teaching_class");
                    model.addRow(new Object[]{SubjectCode,SubjectName,TeachingClass});
                }
            }
            catch(HeadlessException | SQLException e){
                System.out.println(e);
            }
            sql = "SELECT COUNT(Subject_code) FROM tblTeachersToSubjects WHERE Teacher_id='"+txtNO.getText()+"'";
            pst = Conn.prepareStatement(sql);
            rs  = pst.executeQuery();
            lblCount.setText(rs.getString("COUNT(Subject_code)"));
        }
        catch(SQLException ex){
            Logger.getLogger(TeachersSubjectFrm.class.getName()).log(Level.SEVERE, null,ex);  
        }
    }
    private void getClassesForMe(){
    try{
            String sql = "SELECT Class_name FROM tblclasses";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
              comboFormToTeach.addItem(rs.getString("Class_name"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void getSubjectForMe(){
       try{
        sql = "SELECT Subject_code FROM subjects ORDER BY Subject_group ASC";
        pst=Conn.prepareStatement(sql);
        rs = pst.executeQuery();
            while(rs.next()){
                comboSubjectForMe.addItem(rs.getString("Subject_code"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } 
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

        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Teachers = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        lblCount = new javax.swing.JLabel();
        comboSubjectForMe = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDetailSubjects = new javax.swing.JTable();
        StatusText = new javax.swing.JLabel();
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
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
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

        lblCount.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        lblCount.setForeground(new java.awt.Color(0, 0, 153));
        lblCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboSubjectForMe.setBackground(new java.awt.Color(0, 204, 0));
        comboSubjectForMe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        comboSubjectForMe.setToolTipText("");
        comboSubjectForMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSubjectForMeActionPerformed(evt);
            }
        });

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

        StatusText.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        StatusText.setForeground(new java.awt.Color(0, 204, 0));
        StatusText.setText("Waiting...");

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
                        .addComponent(comboFormToTeach, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(StatusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(383, 383, 383)
                .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblCount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSubjectForMe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFormToTeach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(1, 1, 1)
                .addComponent(StatusText)
                .addContainerGap())
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        Refresh_Table();
    }//GEN-LAST:event_formWindowGainedFocus

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
                txtGender.setText(rs.getString("T_gender"));
                txtInitials.setText(rs.getString("T_initials"));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }        
        updateMySubjects();   //Get Suubjects for this teacher
        
    }//GEN-LAST:event_Table_TeachersMouseClicked

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased
        
    }//GEN-LAST:event_txtNameKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String SubjectCodeToAdd = comboSubjectForMe.getSelectedItem().toString();
        String TeachingClass =comboFormToTeach.getSelectedItem().toString();
        String SubjectName  = StatusText.getText();
        String TeacherCode  = txtNO.getText();
        if(SubjectCodeToAdd.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select a Subject to Assign","acme",1);
        }else if(TeachingClass.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select a Class you want to Assign Subject To","acme",1);
        }else if(TeacherCode.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select the teacher you want to assign the Subject to","acme",1);
        }else{
        try{            
            //find if subject is already added
            sql = "SELECT COUNT(*) from tblTeachersToSubjects WHERE Subject_code='"+SubjectCodeToAdd+"' AND Teaching_Class='"+TeachingClass+"'";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            int NumberOfSubjectsAllocated = Integer.parseInt(rs.getString("COUNT(*)"));
            if(NumberOfSubjectsAllocated==0){
                pst=Conn.prepareStatement("INSERT INTO tblTeachersToSubjects(Teacher_id,Subject_code,Subject_name,Teacher_initials,Teaching_class) VALUES(?,?,?,?,?)");
                pst.setString(1, TeacherCode);
                pst.setString(2, SubjectCodeToAdd);
                pst.setString(3, SubjectName);
                pst.setString(4, txtInitials.getText());
                pst.setString(5, TeachingClass);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Subject Assigned to " +txtName.getText()+ " Succesfully");
                updateMySubjects();
            } else{
                pst = Conn.prepareStatement("SELECT tblTeachers.*, tblTeachersToSubjects.* FROM tblTeachersToSubjects INNER JOIN tblTeachers ON (tblTeachers.T_Code = tblTeachersToSubjects.Teacher_id) WHERE (Subject_code='"+SubjectCodeToAdd+"' AND Teaching_Class='"+TeachingClass+"')");
                rs = pst.executeQuery();
                if(rs.next()){
                    String AssignedTeacher = rs.getString("T_name");
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, SubjectName + " has already been assigned to "+AssignedTeacher+" in Form "+TeachingClass,"acme",1);                   
                }     
            }
        }
        catch(NumberFormatException | SQLException e){
            System.out.println(e);
        }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tableDetailSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDetailSubjectsMousePressed
        int row  = tableDetailSubjects.getSelectedRow();
        String SubjectCode = (String)tableDetailSubjects.getModel().getValueAt(row, 0);
        String TeachingClass = (String)tableDetailSubjects.getValueAt(row, 2);
        int res = JOptionPane.showConfirmDialog(null, "Remove "+ SubjectCode  + " for this teacher ?", "Remove",JOptionPane.WARNING_MESSAGE);
        if (res==0){
            try{
                pst = Conn.prepareStatement("DELETE FROM tblTeachersToSubjects WHERE (Teacher_id='"+txtNO.getText()+"' AND Subject_code='"+SubjectCode +"' AND Teaching_Class='"+TeachingClass+"')");
                pst.executeUpdate();
                updateMySubjects();
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_tableDetailSubjectsMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void comboSubjectForMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSubjectForMeActionPerformed
       String SubjectCode = comboSubjectForMe.getSelectedItem().toString();
       if(SubjectCode.equalsIgnoreCase("Select")){
       }else{
        try {            
            sql = "SELECT Subject_name FROM Subjects WHERE Subject_code='"+SubjectCode+"'";
            pst = Conn.prepareStatement(sql);
            rs= pst.executeQuery();
            if(rs.next()){ 
                StatusText.setText(rs.getString("Subject_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeachersSubjectFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
       }              
    }//GEN-LAST:event_comboSubjectForMeActionPerformed

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TeachersSubjectFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel StatusText;
    private javax.swing.JTable Table_Teachers;
    private javax.swing.JComboBox<String> comboFormToTeach;
    private javax.swing.JComboBox<String> comboSubjectForMe;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCount;
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
