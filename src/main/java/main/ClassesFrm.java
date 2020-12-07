
package main;

import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ClassesFrm extends javax.swing.JFrame {
    Connection Conn=ConnClass.connectDB();
    PreparedStatement pst=null;
    ResultSet rs=null;
    String sql=null, Sign="";
    
    public ClassesFrm() {
        initComponents();
        
        getStreams();
        Update_TeachersCombo();
        Refresh_Table();
        new ConnClass().setFrameIcon(this);
    }
    
    private void getStreams(){
        DefaultTableModel model = (DefaultTableModel)Table_Streams.getModel();
        model.setRowCount(0);
        comboStream.removeAllItems();
        comboStream.addItem("Select");
        try{          
          sql= "SELECT * FROM tblstreams";
          pst=Conn.prepareStatement(sql);
          rs=pst.executeQuery();       
          while(rs.next()){
              String StreamName = rs.getString("Stream_name");              
              comboStream.addItem(StreamName);
              model.addRow(new Object[]{StreamName});
          }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void AddClass(){
        boolean proceed = true;
        String Stream;
        if(comboStream.getSelectedItem().toString().equalsIgnoreCase("Select")){
            Stream = "";
        }else{
            Stream = comboStream.getSelectedItem().toString().toUpperCase();
        }
        String Class = comboForm.getSelectedItem().toString();
        String TeacherName = comboTeacher.getSelectedItem().toString();
        String FullClassName;
            if(comboStream.getSelectedItem().toString().equalsIgnoreCase("Select")){
                FullClassName=Class;
            }else{
                FullClassName=Class+" "+Stream;
            }
        if(Class.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select the Class you want to save","acme",1);
        }else if(TeacherName.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select the Class Teacher for this Class","acme",1);
        }else{
            try{  
                if(comboStream.getSelectedItem().toString().equalsIgnoreCase("Select")){
                        int res=JOptionPane.showConfirmDialog(null, "A Stream is not selected. Do you still want to proceed","acme",JOptionPane.YES_NO_OPTION);
                        if(res==JOptionPane.YES_OPTION){
                            proceed=true;                            
                        }else{
                            proceed = false;
                        }
                    }
                if(Sign.isEmpty()){
                        int res=JOptionPane.showConfirmDialog(null, "The Class Teacher Signature is not uploaded","acme",JOptionPane.YES_NO_OPTION);
                        if(res==JOptionPane.YES_OPTION){
                            proceed= true;
                        }else{
                            proceed  = false;
                        }
                    }
                 if(proceed){
                     sql = "SELECT Count(*) FROM tblClasses WHERE Class_name ='"+FullClassName+"'";
                     pst=Conn.prepareStatement(sql);
                     rs =pst.executeQuery();
                     if(rs.next()){
                         int Count  = Integer.parseInt(rs.getString("Count(*)"));
                         if(Count>0){
                             int res=JOptionPane.showConfirmDialog(null, "This class is already registered. Do you want to update details ?","acme",JOptionPane.YES_NO_OPTION);
                             if(res==JOptionPane.YES_OPTION){
                                sql= "UPDATE tblclasses SET Class_teacher=?,ClassTeacher_Signature=? WHERE Class_name ='"+FullClassName+"' ";
                                pst=Conn.prepareStatement(sql);
                                pst.setString(1, TeacherName); pst.setString(2, Sign);
                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Class details updated succesfully","Succes", 1);
                                Refresh_Table();
                             }
                         }else{
                            sql= "INSERT INTO tblclasses(Class_name,Class_teacher,ClassTeacher_Signature)VALUES(?,?,?)";            
                            pst=Conn.prepareStatement(sql);
                                pst.setString(1,FullClassName);
                                pst.setString(2, comboTeacher.getSelectedItem().toString());
                                pst.setString(3, Sign);
                                pst.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Class details saved succesfully","acme", 1);

                                Refresh_Table();
                         }
                     }
                 }
                    
                  
              }
              catch(HeadlessException | SQLException e){
                  System.out.println(e);
            }  
        }  
    }
    private void Update_TeachersCombo(){
        comboTeacher.removeAllItems();
        comboTeacher.addItem("Select");
        try{
          sql= "SELECT * FROM tblteachers";
          pst=Conn.prepareStatement(sql);
          rs=pst.executeQuery();          
          while(rs.next()){
             comboTeacher.addItem(rs.getString("T_name"));
          }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void Refresh_Table(){
        DefaultTableModel model=new DefaultTableModel(new String[]{"Class","Class Teacher"},0);
        try{
            sql= "SELECT Class_name,Class_teacher FROM tblclasses ORDER BY Class_name";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String Class=rs.getString("Class_name");
                String ClassTeacher=rs.getString("Class_Teacher");
                model.addRow(new Object[]{Class,ClassTeacher});
            }
            Table_Classes.setModel(model);
            Table_Classes.getColumn("Class").setPreferredWidth(100);
            Table_Classes.getColumn("Class Teacher").setPreferredWidth(150);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void getSignature(){
        BufferedImage bi;
        try{
            bi=ImageIO.read(new File(Sign));
            //Resizing the image
            Image dimg = bi.getScaledInstance(170,70, Image.SCALE_SMOOTH);
           //Creating image icon
           ImageIcon img = new ImageIcon(dimg);
           SignaturePic.setIcon(img);
        }
        catch(IOException e){
            System.out.println(e);
        }        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboForm = new javax.swing.JComboBox<>();
        comboStream = new javax.swing.JComboBox<>();
        comboTeacher = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Classes = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        SignaturePic = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        btnSave1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Streams = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4" }));
        jPanel1.add(comboForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 180, 20));

        comboStream.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboStream.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jPanel1.add(comboStream, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 180, 20));

        comboTeacher.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboTeacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        jPanel1.add(comboTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 180, 20));

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

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 260, 260));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 0));
        jLabel17.setText("Class Teacher");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 130, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 0));
        jLabel18.setText("Form");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 50, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 153, 0));
        jLabel19.setText("Stream");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 50, 20));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Delete_16x16.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 90, 30));

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Save_16x16.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 180, 30));

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Add_16x16.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel1.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 80, 30));

        SignaturePic.setBackground(new java.awt.Color(255, 255, 255));
        SignaturePic.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.add(SignaturePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 170, 70));

        btnUpload.setText("Class Teacher Signature");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpload, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 170, 30));

        jPanel2.setBackground(new java.awt.Color(0, 153, 51));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Easy Classes & Streams Management");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 0, 540, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, -1));

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 255, 255)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 204, 51));

        jLabel2.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Manage Streams");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, -1));

        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNameKeyTyped(evt);
            }
        });
        jPanel3.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 78, 190, -1));

        btnSave1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Check_16x16.png"))); // NOI18N
        btnSave1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnSave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 100, 30));

        btnDelete1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Delete_16x16.png"))); // NOI18N
        btnDelete1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 80, 30));

        Table_Streams.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stream Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Streams.getTableHeader().setReorderingAllowed(false);
        Table_Streams.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_StreamsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Streams);
        if (Table_Streams.getColumnModel().getColumnCount() > 0) {
            Table_Streams.getColumnModel().getColumn(0).setPreferredWidth(100);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 190, 160));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 204, 0));
        jLabel3.setText("Stream Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 120, 19));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 210, 320));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 430));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Table_ClassesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_ClassesMouseClicked
        SignaturePic.setIcon(null);
        try{
           int row =Table_Classes.getSelectedRow();
           String TableClick  = Table_Classes.getModel().getValueAt(row, 0).toString();
           sql ="SELECT * FROM tblclasses WHERE Class_name ='"+TableClick+"' ";
           
           pst=Conn.prepareStatement(sql);
           rs = pst.executeQuery();
           if(rs.next()){
               String classname  =rs.getString("Class_name"); 
               String parts[] = classname.split(" ");
               int partCount = parts.length;
               if(partCount>1){
                   String FormName = parts[0];
                   String StreamName =parts[1];
                   comboForm.setSelectedItem(FormName);
                   comboStream.setSelectedItem(StreamName);
               }else{
                    comboForm.setSelectedItem(classname);
                    comboStream.setSelectedItem("Select");
               }
              comboForm.setSelectedItem(classname);
              comboTeacher.setSelectedItem(rs.getString("Class_teacher"));
              Sign = rs.getString("ClassTeacher_Signature");
            }
           
        }
        catch(SQLException e){
            System.out.println(e);
        } 
        try{
            getSignature();
        }catch(Exception e){System.out.println(e);}
    }//GEN-LAST:event_Table_ClassesMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String Stream = comboStream.getSelectedItem().toString();
        String Class = comboForm.getSelectedItem().toString();
        String FullClassName;
            if(comboStream.getSelectedItem().toString().equalsIgnoreCase("Select")){
                FullClassName=Class;
            }else{
                FullClassName=Class+" "+Stream;
            }
      int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this class ?","acme",JOptionPane.YES_NO_OPTION);
      if(res==0){
        try{
            sql= "DELETE FROM tblclasses WHERE Class_name ='"+FullClassName+"' ";
            pst=Conn.prepareStatement(sql);
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Class details deleted.","acme", 1);
            Refresh_Table();
            
            comboForm.setSelectedItem("Select");
            comboStream.setSelectedItem("Select");
            comboTeacher.setSelectedItem("Select");
            Sign = "";
            SignaturePic.setIcon(null);
         }
        catch(HeadlessException | SQLException e){
            e.printStackTrace();
        }
      } 
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddClass();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        comboForm.setSelectedItem("Select");
        comboStream.setSelectedItem("Select");
        comboTeacher.setSelectedItem("Select");
        Sign = "";
        SignaturePic.setIcon(null);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","jpeg"); 
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);        
        File f = chooser.getSelectedFile();
        String filepath = f.getAbsolutePath();
        
        filepath=filepath.replace("\\", "/");
        Sign = filepath;
        //Load Image to label
            BufferedImage bi;
            try{
                bi=ImageIO.read(new File(filepath));
                Image dimg = bi.getScaledInstance(170,70, Image.SCALE_SMOOTH);
                ImageIcon img = new ImageIcon(dimg);
                SignaturePic.setIcon(img); 
            }
            catch(IOException e){
                System.out.println(e);
            }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        String StreamName = txtName.getText().toUpperCase();
        try{
            if(txtName.getText().toUpperCase().isEmpty()){
                JOptionPane.showMessageDialog(null, "A stream name to be saved is required","Waiting...",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                sql ="INSERT INTO tblStreams(Stream_name)VALUES ('"+StreamName+"')";
                pst=Conn.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Stream Saved","Succes",JOptionPane.INFORMATION_MESSAGE);
                txtName.setText("");
                getStreams();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        try{
            String StreamName = (String)Table_Streams.getValueAt(Table_Streams.getSelectedRow(),0);
            int Response = JOptionPane.showConfirmDialog(null, "This stream will be delted permanently. Do you want to Proceed?","Delete",JOptionPane.YES_NO_OPTION);
            if(Response==JOptionPane.YES_OPTION){
                sql= "DELETE FROM tblstreams WHERE Stream_name='"+StreamName+"' ";
                pst=Conn.prepareStatement(sql);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Stream details removed from the System","Deleted",JOptionPane.INFORMATION_MESSAGE);
                txtName.setText("");
                getStreams();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void Table_StreamsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_StreamsMouseClicked
        int row =Table_Streams.getSelectedRow();
        String SName  =Table_Streams.getModel().getValueAt(row, 0).toString();
        txtName.setText(SName);
    }//GEN-LAST:event_Table_StreamsMouseClicked

    private void txtNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyTyped
        char c = evt.getKeyChar();        
        if(Character.isDigit(c)){
            evt.consume();
            getToolkit().beep();
        }
    }//GEN-LAST:event_txtNameKeyTyped
    

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
    private javax.swing.JLabel SignaturePic;
    private javax.swing.JTable Table_Classes;
    private javax.swing.JTable Table_Streams;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btnUpload;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboStream;
    private javax.swing.JComboBox<String> comboTeacher;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
