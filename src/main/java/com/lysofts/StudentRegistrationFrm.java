
package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
public class StudentRegistrationFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql=null;
    String filepath = null;
    
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public StudentRegistrationFrm() {
        initComponents();
        
        new ConnClass().setFrameIcon(this);
        getHouses();
        getClasses(); 
    }

    private void getHouses(){        
        try{
            sql="SELECT * FROM tblhouses ORDER BY HouseName";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                comboHouse.addItem(rs.getString("HouseName"));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void getClasses(){
        DefaultTableModel model = (DefaultTableModel) Table_class.getModel();
        comboClassFromPromotion.removeAllItems();
        comboClassToPromotion.removeAllItems();
        
        comboClassFromPromotion.addItem("Select class");
        comboClassToPromotion.addItem("Select class");
        comboClassToPromotion.addItem("Completed");
        try{
            sql = "SELECT Class_name AS FORM FROM tblclasses ORDER BY Class_name Desc";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String ClassName = rs.getString("FORM");
                model.addRow(new Object[]{ClassName});
                
                comboClassFromPromotion.addItem(ClassName);
                comboClassToPromotion.addItem(ClassName);
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void UpdateTableStudents(){
        String className = (String) Table_class.getValueAt(Table_class.getSelectedRow(), 0);
        DefaultTableModel model= (DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        try{           
            sql = "SELECT * FROM student_details WHERE Student_class='"+className+"' ";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});            
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void AddStudent(){
        if(txtADMNO.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Adm No is neccesary data required","acme",JOptionPane.INFORMATION_MESSAGE);
        }else if(txtName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Student Name is neccesary data required","acme",JOptionPane.INFORMATION_MESSAGE);
        }else{
            try{
                String AdmNo = txtADMNO.getText();
                sql = "SELECT Count(*) from Student_details where Student_id ='"+AdmNo+"'";
                pst = Conn.prepareStatement(sql);
                rs= pst.executeQuery();
                if(rs.next()){
                    int Count = Integer.parseInt(rs.getString("Count(*)"));
                    if(Count>0){
                        int res = JOptionPane.showConfirmDialog(null,"This Admission Number already Used for Registration, Do you want to change Or Update details","Update ?",JOptionPane.YES_NO_OPTION);
                        if(res==JOptionPane.YES_OPTION){
                        //Update Data
                        sql = "UPDATE student_details SET Student_name=?,Student_class=?,kcpe_marks=?,kcpe_grade=?,Picture=?,"
                                    + "DOA=?,DOB=?,Gender=?,Mode_of_study=?,House=?,Father=?,Phone1=?,Phone2=?  WHERE Student_id='"+txtADMNO.getText()+"'";
                                pst=Conn.prepareStatement(sql);
                                pst.setString(1,txtName.getText());
                                pst.setString(2,txtClass.getText());
                                pst.setString(3,txtKCPE.getText());
                                pst.setString(4,txtKcpeGrade.getText());
                                pst.setString(5,txtPath.getText());
                                pst.setString(6,((JTextField)txtDOA.getDateEditor().getUiComponent()).getText());
                                pst.setString(7,((JTextField)txtDOB.getDateEditor().getUiComponent()).getText());
                                pst.setString(8,cmbGender.getSelectedItem().toString());   
                                pst.setString(9,comboMSTUDY.getSelectedItem().toString());
                                pst.setString(10,comboHouse.getSelectedItem().toString());
                                pst.setString(11,txtFather.getText());
                                pst.setString(12,txtPhone1.getText());
                                pst.setString(13,txtPhone1.getText());

                            pst.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Data updated succesfully","acme",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{
                        //Add Student
                        //<editor-fold defaultstate="collapsed" desc="comment">
                        sql = "INSERT INTO student_details(Student_id,Student_name,Student_class,kcpe_marks,kcpe_grade,Picture,"
                                + "DOA,DOB,Gender,Mode_of_study,House,Father,Phone1,Phone2)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        pst=Conn.prepareStatement(sql);
                        pst.setString(1,txtADMNO.getText());
                        pst.setString(2,txtName.getText());
                        pst.setString(3,txtClass.getText());
                        pst.setString(4,txtKCPE.getText());
                        pst.setString(5,txtKcpeGrade.getText());
                        pst.setString(6,txtPath.getText());
                        pst.setString(7,((JTextField)txtDOA.getDateEditor().getUiComponent()).getText());
                        pst.setString(8,((JTextField)txtDOB.getDateEditor().getUiComponent()).getText());
                        pst.setString(9,cmbGender.getSelectedItem().toString());
                        pst.setString(10,comboMSTUDY.getSelectedItem().toString());
                        pst.setString(11,comboHouse.getSelectedItem().toString());
                        pst.setString(12,txtFather.getText());
                        pst.setString(13,txtPhone1.getText());
                        pst.setString(14,txtPhone1.getText());
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Student Registred succesfully","acme",JOptionPane.INFORMATION_MESSAGE);
                    }
//</editor-fold>
                                       
                    }
                }catch(HeadlessException | SQLException e){
                        System.out.println(e);
                }               
                
        }
    }
    private void GradeKCPE(){
        if(!txtKCPE.getText().isEmpty()){
        try{            
            int Marks = Integer.parseInt(txtKCPE.getText());
            String Grade = "";
                if (Marks >=400 && Marks<500){
                    Grade  ="A";
                 }
                else if (Marks >= 375 && Marks <400){
                    Grade  ="A-";
                 }
                else if (Marks >= 350 && Marks <375){
                    Grade  ="B+";
                 }
                else if (Marks >= 325 && Marks <350){
                    Grade  ="B";
                 }
                else if (Marks >= 300 && Marks <325){
                    Grade  ="B-";
                 }
                else if (Marks >= 275 && Marks <300){
                    Grade  ="C+";
                 }
                else if (Marks >= 250 && Marks < 275){
                    Grade  ="C";
                 }
                else if (Marks >= 225 && Marks <250){
                    Grade  ="C-";
                 }
                else if (Marks >= 200 && Marks <225){
                    Grade  ="D+";
                 }
                else if (Marks >= 175 && Marks <200){
                    Grade  ="D";
                 }
                else if (Marks >= 150 && Marks <175){
                    Grade  ="D-";
                 }
                else{
                    Grade  ="E";
                 }
                txtKcpeGrade.setText(Grade);
            }
        catch(NumberFormatException e){
            System.out.println(e);
        }    
        }           
    }
    private void getProfilePic(){
        filepath=txtPath.getText();
        txtPath.setText(filepath);
        SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                BufferedImage bi;
                if(new File(filepath).exists()){
                    try{
                        bi = ImageIO.read(new File(filepath));
                        Image dimg = bi.getScaledInstance(110,100, Image.SCALE_SMOOTH);
                        ImageIcon img = new ImageIcon(dimg);
                        profilePic.setIcon(img); 
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
    private void updateMySubjects(){
        try{
           sql = "SELECT SS_Subject_code,SS_Subject_name FROM tblstudents_subjects "
                   + "WHERE SS_Student_id='"+txtADMNO.getText()+"' ";           
           pst=Conn.prepareStatement(sql);
           rs=pst.executeQuery();
           DefaultTableModel model = (DefaultTableModel)tableDetailSubjects.getModel();
           model.setRowCount(0);
           while(rs.next()){
               String sCODE =rs.getString("SS_Subject_code");
               String sName =rs.getString("SS_Subject_name");
               model.addRow(new Object[]{sCODE,sName});
           }
           
           sql = "SELECT COUNT(SS_Subject_code) FROM tblstudents_subjects WHERE SS_Student_id='"+txtADMNO.getText()+"'";
           pst = Conn.prepareStatement(sql);
           rs  = pst.executeQuery();
           if(rs.next()){
               lblMySubjectCount.setText(rs.getString("COUNT(SS_Subject_code)"));
           }           
        }
        catch(HeadlessException | SQLException e){
          System.out.println(e);  
        }
    }
    
    private void clearForNewStudent(){
        txtName.setText("");
        txtADMNO.setText("");
        txtKCPE.setText("");
        txtKcpeGrade.setText("");
        txtDOA.setDate(null);
        txtDOB.setDate(null);
        txtFather.setText("");
        txtPhone1.setText("");
        txtPath.setText("");
        DefaultTableModel model = (DefaultTableModel)tableDetailSubjects.getModel();
        model.setRowCount(0);
        profilePic.setIcon(null);
        btnNew.setEnabled(true);
        btnSave.setEnabled(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentDetailsDialog = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        txtClass = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPath = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetailSubjects = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txtFather = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPhone1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtADMNO = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtKCPE = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comboMSTUDY = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtKcpeGrade = new javax.swing.JTextField();
        comboHouse = new javax.swing.JComboBox<>();
        cmbGender = new javax.swing.JComboBox<>();
        txtDOA = new com.toedter.calendar.JDateChooser();
        txtDOB = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        lblCount1 = new javax.swing.JLabel();
        profilePic = new javax.swing.JLabel();
        btnUploadProfilePicture = new javax.swing.JButton();
        lblMySubjectCount = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        PromotionDlg = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        comboClassFromPromotion = new javax.swing.JComboBox<>();
        comboClassToPromotion = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PromotionsTableFromClass = new javax.swing.JTable();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        PromotionsTableToClass = new javax.swing.JTable();
        jCheckBox3 = new javax.swing.JCheckBox();
        RemarksDlg = new javax.swing.JDialog();
        jPanel16 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taRmarks = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        txtLeaveDate = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cmbFamditted = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_class = new javax.swing.JTable();
        btnOpenStudentsDialog1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Students = new javax.swing.JTable();
        btnStudentDetails = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnOpenStudentsDialog = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        StudentDetailsDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        StudentDetailsDialog.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        StudentDetailsDialog.setLocation(new java.awt.Point(50, 50));
        StudentDetailsDialog.setMinimumSize(new java.awt.Dimension(541, 311));
        StudentDetailsDialog.setModal(true);
        StudentDetailsDialog.setResizable(false);
        StudentDetailsDialog.setSize(new java.awt.Dimension(541, 350));
        StudentDetailsDialog.setType(java.awt.Window.Type.UTILITY);
        StudentDetailsDialog.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                StudentDetailsDialogWindowLostFocus(evt);
            }
        });
        StudentDetailsDialog.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtClass.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        txtClass.setForeground(new java.awt.Color(255, 0, 0));
        txtClass.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtClass.setText("Selected Class");
        jPanel15.add(txtClass, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 157, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Form");
        jPanel15.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 60, -1));

        txtPath.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtPath.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel15.add(txtPath, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 60, 310, 15));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Subjects"));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        tableDetailSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODE", "Subject"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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
        jScrollPane1.setViewportView(tableDetailSubjects);
        if (tableDetailSubjects.getColumnModel().getColumnCount() > 0) {
            tableDetailSubjects.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 210, 210));

        jPanel15.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 230, 240));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Parent"));
        jPanel12.setPreferredSize(new java.awt.Dimension(175, 184));

        txtFather.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Parent/Guardian's Name");

        txtPhone1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Phone");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPhone1)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFather))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFather, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 210, 150));

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Delete_16x16.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel15.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, 110, 30));

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Save_16x16.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel15.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 130, 30));

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))); // NOI18N
        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel15.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 140, 30));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 180));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("NAME");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtADMNO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtADMNO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtADMNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtADMNOActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ADM");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("D.O.A");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("D.O.B");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("KCPE M");

        txtKCPE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtKCPE.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtKCPE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKCPEKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("GENDER");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("STUDY");

        comboMSTUDY.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboMSTUDY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REGULAR", "DAYSCHOLAR" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("HOUSE");

        txtKcpeGrade.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtKcpeGrade.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        comboHouse.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboHouse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));

        cmbGender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        txtDOA.setBackground(new java.awt.Color(102, 255, 204));
        txtDOA.setDateFormatString("d MMMM, yyyy");
        txtDOA.setMaxSelectableDate(new java.util.Date(253370757666000L));
        txtDOA.setMinSelectableDate(new java.util.Date(-62135776734000L));

        txtDOB.setBackground(new java.awt.Color(102, 255, 102));
        txtDOB.setDateFormatString("d MMMM, yyyy");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboHouse, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(txtKCPE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtKcpeGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(86, 86, 86))
                                    .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 17, Short.MAX_VALUE))
                            .addComponent(comboMSTUDY, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDOA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbGender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKCPE, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKcpeGrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDOA, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(comboMSTUDY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(comboHouse, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        jPanel15.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 310, 260));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Properties_24x24.png"))); // NOI18N
        jButton5.setText("Leaving Certificate");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, 210, 30));

        lblCount1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCount1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCount1.setText("Url");
        jPanel15.add(lblCount1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 43, -1));

        profilePic.setBackground(new java.awt.Color(255, 255, 255));
        profilePic.setBorder(javax.swing.BorderFactory.createBevelBorder(1, null, new java.awt.Color(255, 255, 0), null, null));
        jPanel15.add(profilePic, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 110, 100));

        btnUploadProfilePicture.setBackground(new java.awt.Color(0, 255, 0));
        btnUploadProfilePicture.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnUploadProfilePicture.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/User_24x24.png"))); // NOI18N
        btnUploadProfilePicture.setText("Upload");
        btnUploadProfilePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadProfilePictureActionPerformed(evt);
            }
        });
        jPanel15.add(btnUploadProfilePicture, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 100, 20));

        lblMySubjectCount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMySubjectCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMySubjectCount.setText("COUNT");
        jPanel15.add(lblMySubjectCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, 60, 30));

        jPanel7.setBackground(new java.awt.Color(0, 204, 0));

        jLabel22.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Easy Students Management and Registration");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel15.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        StudentDetailsDialog.getContentPane().add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 420));

        PromotionDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PromotionDlg.setModal(true);
        PromotionDlg.setResizable(false);
        PromotionDlg.setType(java.awt.Window.Type.UTILITY);
        PromotionDlg.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(0, 204, 0));

        jLabel4.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Easy Students promotion to Next Class");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(321, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        PromotionDlg.getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel5.setText("Promote Students in Top Most Classes followed by preceeding Classes in that Order");
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tip", 0, 0, new java.awt.Font("Calisto MT", 2, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        comboClassFromPromotion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboClassFromPromotion.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboClassFromPromotionPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        comboClassToPromotion.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboClassToPromotion.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboClassToPromotionPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("From");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 204));
        jLabel19.setText("To");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Stock Index Up_16x16.png"))); // NOI18N
        jButton3.setText("Promote");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Stock Index Down_16x16.png"))); // NOI18N
        jButton4.setText("Reverse or Demote");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 3, true));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class Promoting From", 0, 0, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel13.setOpaque(false);
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PromotionsTableFromClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECT", "ADM NO", "NAME", "CLASS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PromotionsTableFromClass.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(PromotionsTableFromClass);
        if (PromotionsTableFromClass.getColumnModel().getColumnCount() > 0) {
            PromotionsTableFromClass.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        jPanel13.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 350, 240));

        jCheckBox4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(0, 204, 0));
        jCheckBox4.setText("Select All");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel13.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 138, -1));

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class Promoting To", 0, 0, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel14.setOpaque(false);
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PromotionsTableToClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECT", "ADM NO", "NAME", "CLASS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PromotionsTableToClass.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(PromotionsTableToClass);
        if (PromotionsTableToClass.getColumnModel().getColumnCount() > 0) {
            PromotionsTableToClass.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        jPanel14.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 360, 240));

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(255, 51, 0));
        jCheckBox3.setText("Select All");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });
        jPanel14.add(jCheckBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 138, -1));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClassFromPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClassToPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(32, 32, 32)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboClassFromPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboClassToPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel19)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PromotionDlg.getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 42, 820, 400));

        RemarksDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        RemarksDlg.setModal(true);
        RemarksDlg.setResizable(false);
        RemarksDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(0, 204, 0));

        jLabel11.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Print Student's Leaving Certificate");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        taRmarks.setColumns(20);
        taRmarks.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        taRmarks.setLineWrap(true);
        taRmarks.setRows(5);
        taRmarks.setWrapStyleWord(true);
        taRmarks.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Remarks on the Student's conduct, industry and ability", 0, 0, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 0, 204))); // NOI18N
        jScrollPane3.setViewportView(taRmarks);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_16x16.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtLeaveDate.setBackground(new java.awt.Color(102, 255, 204));
        txtLeaveDate.setDateFormatString("d MMMM, yyyy");
        txtLeaveDate.setMaxSelectableDate(new java.util.Date(253370757666000L));
        txtLeaveDate.setMinSelectableDate(new java.util.Date(-62135776734000L));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("KCSE DATE");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("Form Admitted");

        cmbFamditted.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ONE", "TWO", "THREE", "FOUR" }));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLeaveDate, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(190, 190, 190))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbFamditted, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLeaveDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbFamditted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout RemarksDlgLayout = new javax.swing.GroupLayout(RemarksDlg.getContentPane());
        RemarksDlg.getContentPane().setLayout(RemarksDlgLayout);
        RemarksDlgLayout.setHorizontalGroup(
            RemarksDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        RemarksDlgLayout.setVerticalGroup(
            RemarksDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(210, 266));
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Class", 0, 0, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_class.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " FORM"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_class.getTableHeader().setReorderingAllowed(false);
        Table_class.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_classMouseClicked(evt);
            }
        });
        Table_class.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Table_classKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(Table_class);
        if (Table_class.getColumnModel().getColumnCount() > 0) {
            Table_class.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, 290));

        btnOpenStudentsDialog1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenStudentsDialog1.setForeground(new java.awt.Color(255, 0, 0));
        btnOpenStudentsDialog1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Stock Index Up_16x16.png"))); // NOI18N
        btnOpenStudentsDialog1.setText("Promotion");
        btnOpenStudentsDialog1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0)));
        btnOpenStudentsDialog1.setIconTextGap(10);
        btnOpenStudentsDialog1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenStudentsDialog1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnOpenStudentsDialog1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 140, 30));

        jPanel5.setBackground(new java.awt.Color(153, 255, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Students List", 0, 0, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        Table_Students.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADM NO", "NAME", "CLASS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Students.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table_Students);
        if (Table_Students.getColumnModel().getColumnCount() > 0) {
            Table_Students.getColumnModel().getColumn(0).setPreferredWidth(30);
            Table_Students.getColumnModel().getColumn(1).setPreferredWidth(150);
            Table_Students.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        btnStudentDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentDetails.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Teacher-icon.png"))); // NOI18N
        btnStudentDetails.setText("Details");
        btnStudentDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentDetailsActionPerformed(evt);
            }
        });

        txtSearch.setBackground(new java.awt.Color(255, 255, 102));
        txtSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSearch.setText("Search Student");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Search_16x16.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnOpenStudentsDialog.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenStudentsDialog.setForeground(new java.awt.Color(255, 0, 0));
        btnOpenStudentsDialog.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))); // NOI18N
        btnOpenStudentsDialog.setText("New");
        btnOpenStudentsDialog.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0)));
        btnOpenStudentsDialog.setIconTextGap(10);
        btnOpenStudentsDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenStudentsDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(btnOpenStudentsDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnOpenStudentsDialog, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student Registration");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void Table_classMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_classMouseClicked
        UpdateTableStudents();   
    }//GEN-LAST:event_Table_classMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(!txtADMNO.getText().isEmpty()){
            int response = JOptionPane.showConfirmDialog(null, "This students data will be deleted permanently.","acme",JOptionPane.YES_NO_OPTION);
            if (response==0){
                try{
                    sql = "DELETE FROM student_details WHERE Student_id='"+txtADMNO.getText()+"'";
                    pst=Conn.prepareStatement(sql);
                    pst.executeUpdate();
                    clearForNewStudent();
                    JOptionPane.showMessageDialog(null, "Student deleted","acme",JOptionPane.INFORMATION_MESSAGE);
                    UpdateTableStudents(); 
                    StudentDetailsDialog.setVisible(false);
                }
                catch(HeadlessException | SQLException e){
                    System.out.println(e);
                }
            }            
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddStudent();
        UpdateTableStudents(); 
        btnNew.setEnabled(true);btnSave.setEnabled(true);
        clearForNewStudent();
        StudentDetailsDialog.setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed
    
    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForNewStudent();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUploadProfilePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadProfilePictureActionPerformed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files","jpg","png","jpeg"); 
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);        
        File f = chooser.getSelectedFile();
        filepath = f.getAbsolutePath();
        
        filepath=filepath.replace("\\", "/");
        txtPath.setText(filepath);
        //Load Image to label
        getProfilePic();
    }//GEN-LAST:event_btnUploadProfilePictureActionPerformed

    private void txtKCPEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKCPEKeyReleased
        GradeKCPE();
    }//GEN-LAST:event_txtKCPEKeyReleased

    private void tableDetailSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDetailSubjectsMousePressed
       int row  = tableDetailSubjects.getSelectedRow();
        String SelectedCell =tableDetailSubjects.getModel().getValueAt(row, 0).toString();
        int res = JOptionPane.showConfirmDialog(null, "Remove "+ SelectedCell + " for this student", "acme",JOptionPane.WARNING_MESSAGE);
        if (res==0){
            try{
            sql = "DELETE FROM tblStudents_subjects WHERE SS_Subject_code='"+SelectedCell+"' AND SS_student_id='"+txtADMNO.getText()+"'";
            pst = Conn.prepareStatement(sql);
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

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String SearchText=txtSearch.getText();
        DefaultTableModel model=(DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        try{
            sql="SELECT * FROM student_details WHERE Student_id='"+SearchText+"' ORDER BY Student_id";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});           
            }            
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
            sql="SELECT * FROM student_details WHERE Student_name LIKE '%"+SearchText+"%' ORDER BY Student_id ";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});           
            }        
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
            sql="SELECT * FROM student_details WHERE Student_Class='"+SearchText+"' ORDER BY Student_id";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});           
            }  
        
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnOpenStudentsDialog1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenStudentsDialog1ActionPerformed
        PromotionDlg.pack();
        PromotionDlg.setLocationRelativeTo(this);
        PromotionDlg.setVisible(true);
    }//GEN-LAST:event_btnOpenStudentsDialog1ActionPerformed

    private void btnStudentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentDetailsActionPerformed
        int row = Table_Students.getSelectedRow();
        String Selected_cell = Table_Students.getValueAt(row, 0).toString();
        if(!Selected_cell.isEmpty()){
        try{
            sql = "SELECT * FROM student_details WHERE Student_id = '"+Selected_cell+"' ";
            pst=Conn.prepareStatement(sql);
            rs  =pst.executeQuery();
            if(rs.next()){
                txtADMNO.setText(rs.getString("Student_id"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }
        try{            
            sql = "SELECT * FROM Student_details WHERE Student_id='"+txtADMNO.getText()+"' ";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txtClass.setText(rs.getString("Student_class"));
                txtName.setText(rs.getString("Student_name"));
                txtKCPE.setText(rs.getString("kcpe_marks"));  
                txtKcpeGrade.setText(rs.getString("kcpe_grade"));
                String doa = rs.getString("DOA");
                String dob = rs.getString("DOB");
                ((JTextField)txtDOA.getDateEditor().getUiComponent()).setText(doa);
                ((JTextField)txtDOB.getDateEditor().getUiComponent()).setText(dob);
                cmbGender.setSelectedItem(rs.getString("Gender"));
                comboMSTUDY.setSelectedItem(rs.getString("Mode_of_study"));
                comboHouse.setSelectedItem(rs.getString("House"));
                txtPath.setText(rs.getString("Picture"));                     
                getProfilePic();
                txtFather.setText(rs.getString("Father"));
                txtPhone1.setText(rs.getString("Phone1"));
            }
        }catch(HeadlessException | SQLException e){
                System.out.println(e);
        }
        try{
            DefaultTableModel model = (DefaultTableModel)tableDetailSubjects.getModel();
            model.setRowCount(0);
            sql ="SELECT * FROM tblstudents_subjects  WHERE SS_Student_id ='"+Selected_cell+"' ORDER BY SS_Subject_name ASC";
            pst =Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String Code = rs.getString("SS_Subject_code");
                String Name = rs.getString("SS_Subject_name");
                model.addRow(new Object[]{Code,Name});
            }
        }catch(HeadlessException | SQLException e){
            System.out.println(e);
        }
        }
        updateMySubjects();
        StudentDetailsDialog.pack();
        StudentDetailsDialog.setLocationRelativeTo(null);
        StudentDetailsDialog.setVisible(true);
    }//GEN-LAST:event_btnStudentDetailsActionPerformed

    private void comboClassFromPromotionPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboClassFromPromotionPopupMenuWillBecomeInvisible
        boolean bool=false;
        getClassPromotingFrom(bool);
    }//GEN-LAST:event_comboClassFromPromotionPopupMenuWillBecomeInvisible
    private void getClassPromotingFrom(boolean bool){
    DefaultTableModel model = (DefaultTableModel)PromotionsTableFromClass.getModel();
    model.setRowCount(0);
        String Form  = (String)comboClassFromPromotion.getSelectedItem();
        if(!Form.equalsIgnoreCase("Select Class")){
        try{
            sql = "SELECT * FROM student_details WHERE Student_Class = '"+Form+"'";
            pst =Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                String ADM = rs.getString("Student_id");
                String Name = rs.getString("Student_name");
                String Class = rs.getString("Student_Class");
                
                model.addRow(new Object[]{bool,ADM,Name,Class});
            }
            
        }catch(SQLException e){
        System.out.println(e);
        }
        }
    }
    private void comboClassToPromotionPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboClassToPromotionPopupMenuWillBecomeInvisible
        boolean bool=false;
        getClassPromotingTo(bool);
    }//GEN-LAST:event_comboClassToPromotionPopupMenuWillBecomeInvisible
    private void getClassPromotingTo(boolean bool){
        DefaultTableModel model = (DefaultTableModel)PromotionsTableToClass.getModel();
        model.setRowCount(0);
            String Form  = (String)comboClassToPromotion.getSelectedItem();
            if(!Form.equalsIgnoreCase("Select Class")){
            try{
                sql = "SELECT * FROM student_details WHERE Student_Class = '"+Form+"'";
                pst =Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()){
                    String ADM = rs.getString("Student_id");
                    String Name = rs.getString("Student_name");
                    String Class = rs.getString("Student_Class");
                    
                    model.addRow(new Object[]{bool,ADM,Name,Class});
                }
            }catch(SQLException e){
            System.out.println(e);
            }
            }
        }
    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        boolean bool;
        try{
            if(jCheckBox4.isSelected()){
                bool = true;
                getClassPromotingFrom(bool);
            }else{
                bool  = false;
                getClassPromotingFrom(bool);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        boolean bool;
        try{
            if(jCheckBox3.isSelected()){
                bool = true;
                getClassPromotingTo(bool);
            }else{
                bool  = false;
                getClassPromotingTo(bool);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String FromClass = (String)comboClassFromPromotion.getSelectedItem();
        String ToClass = (String)comboClassToPromotion.getSelectedItem();
        if(FromClass.equalsIgnoreCase("Select Class")){
            JOptionPane.showMessageDialog(null, "Select a class you are promoting From","acme",JOptionPane.INFORMATION_MESSAGE);
        }else if(ToClass.equalsIgnoreCase("Select Class")){
            JOptionPane.showMessageDialog(null, "Select a class you are promoting To","acme",JOptionPane.INFORMATION_MESSAGE);
        }else if(FromClass.equalsIgnoreCase(ToClass)){
            JOptionPane.showMessageDialog(null, "You cannot promote to the same Class, Try other options","acme",JOptionPane.INFORMATION_MESSAGE);
        }else{
            int res = JOptionPane.showConfirmDialog(null,"Promote selected student From "+FromClass+" To "+ToClass,"acme",JOptionPane.YES_NO_OPTION);
            if(res ==JOptionPane.YES_OPTION){
                int ClassFromInt = 0; Integer.parseInt(FromClass.substring(0, 1));
                int ClassToInt = 0;
                try{
                    ClassFromInt = 0; Integer.parseInt(FromClass.substring(0, 1));
                    ClassToInt = Integer.parseInt(ToClass.substring(0, 1));
                }catch(NumberFormatException e){
                    //Ignore number fomat error
                }
                if(ClassToInt<ClassFromInt){
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    int res2 = JOptionPane.showConfirmDialog(null,"You are trying to promote students to a Lower Class, Proceed ? "+FromClass+" To "+ToClass,"acme",JOptionPane.YES_NO_OPTION);
                    if(res2 ==JOptionPane.YES_OPTION){
                        try{
                            int Rows = PromotionsTableFromClass.getRowCount();
                            for(int row=0;row<Rows;row++){
                                boolean checked = (boolean)PromotionsTableFromClass.getValueAt(row,0);
                                if(checked){
                                    String StuId = (String)PromotionsTableFromClass.getValueAt(row,1);
                                    sql = "UPDATE Student_details SET Student_Class =? WHERE Student_id =?";
                                    pst = Conn.prepareStatement(sql);
                                    pst.setString(1,ToClass);   pst.setString(2,StuId);
                                    pst.executeUpdate();
                                }
                            }
                            boolean bool = false;
                            getClassPromotingFrom(bool);
                            getClassPromotingTo(bool);
                            JOptionPane.showMessageDialog(null, "Process completed","acme",JOptionPane.INFORMATION_MESSAGE);
                        }catch(HeadlessException | SQLException e){
                            System.out.println(e);
                        }
                    }
//</editor-fold>
                }else{
                    //<editor-fold defaultstate="collapsed" desc="comment">
                    try{
                        int Rows = PromotionsTableFromClass.getRowCount();
                        for(int row=0;row<Rows;row++){
                            boolean checked = (boolean)PromotionsTableFromClass.getValueAt(row,0);
                            if(checked){
                                String StuId = (String)PromotionsTableFromClass.getValueAt(row,1);
                                sql = "UPDATE Student_details SET Student_Class =? WHERE Student_id =?";
                                pst = Conn.prepareStatement(sql);
                                pst.setString(1,ToClass);   pst.setString(2,StuId);
                                pst.executeUpdate();
                            }
                        }
                        boolean bool = false;
                        getClassPromotingFrom(bool);
                        getClassPromotingTo(bool);
                        JOptionPane.showMessageDialog(null, "Process completed","acme",JOptionPane.INFORMATION_MESSAGE);
                    }catch(HeadlessException | SQLException e){
                        System.out.println(e);
                    }
//</editor-fold>
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       String FromClass = (String)comboClassFromPromotion.getSelectedItem();
       String ToClass = (String)comboClassToPromotion.getSelectedItem();
        if(FromClass.equalsIgnoreCase("Select Class")){
            JOptionPane.showMessageDialog(null, "Select a class you are Reversing or Demoting Students To. (labeled From.)","acme",JOptionPane.INFORMATION_MESSAGE);
        }else if(ToClass.equalsIgnoreCase("Select Class")){
            JOptionPane.showMessageDialog(null, "Select a class you are Reversing or Demoting Students From. (labeled To.)","acme",JOptionPane.INFORMATION_MESSAGE);
        }else if(FromClass.equalsIgnoreCase(ToClass)){
            JOptionPane.showMessageDialog(null, "You cannot Reverse or demote to the same Class, Try other options","acme",JOptionPane.INFORMATION_MESSAGE);
        }else{
            int res = JOptionPane.showConfirmDialog(null,"Demote or Reverse selected student From "+ToClass+" To "+FromClass,"acme",JOptionPane.YES_NO_OPTION);
            if(res ==JOptionPane.YES_OPTION){
                    try{
                        int Rows = PromotionsTableToClass.getRowCount();
                        for(int row=0;row<Rows;row++){
                            boolean checked = (boolean)PromotionsTableToClass.getValueAt(row,0);
                            if(checked){
                                String StuId = (String)PromotionsTableToClass.getValueAt(row,1);
                                sql = "UPDATE Student_details SET Student_Class =? WHERE Student_id =?";
                                pst = Conn.prepareStatement(sql);
                                pst.setString(1,FromClass);   pst.setString(2,StuId);
                                pst.executeUpdate();
                            }
                        }
                        boolean bool = false;
                        getClassPromotingFrom(bool);
                        getClassPromotingTo(bool);
                        JOptionPane.showMessageDialog(null, "Process completed","acme",JOptionPane.INFORMATION_MESSAGE);
                    }catch(HeadlessException | SQLException e){
                        System.out.println(e);
                    }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        RemarksDlg.pack();
        RemarksDlg.setLocationRelativeTo(this);
        RemarksDlg.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnOpenStudentsDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenStudentsDialogActionPerformed
        clearForNewStudent();
        String ClassName  = (String)(Table_class.getValueAt(Table_class.getSelectedRow(), 0)) ;
        if(ClassName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select a class","acme",JOptionPane.INFORMATION_MESSAGE);
        }else{
            txtClass.setText(ClassName);
            StudentDetailsDialog.pack();
            StudentDetailsDialog.setLocationRelativeTo(null);
            StudentDetailsDialog.setVisible(true);
        }
    }//GEN-LAST:event_btnOpenStudentsDialogActionPerformed

    private void StudentDetailsDialogWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StudentDetailsDialogWindowLostFocus
        profilePic.setIcon(null);
    }//GEN-LAST:event_StudentDetailsDialogWindowLostFocus

    private void Table_classKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_classKeyReleased
        if(evt.isActionKey()){
            UpdateTableStudents();
        }
    }//GEN-LAST:event_Table_classKeyReleased

    private void txtADMNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtADMNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtADMNOActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String SearchText=txtSearch.getText();
        DefaultTableModel model=(DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        try{
            sql="SELECT * FROM student_details WHERE Student_id='"+SearchText+"' ORDER BY Student_id";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});            
            }            
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
            sql="SELECT * FROM student_details WHERE Student_name LIKE '%"+SearchText+"%' ORDER BY Student_id ";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});            
            }        
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
//            sql="SELECT * FROM student_details WHERE Student_Class='"+SearchText+"' ORDER BY Student_id";
//            pst=Conn.prepareStatement(sql);
//            rs=pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");  
                String ClassName = rs.getString("Student_class");
                model.addRow(new Object[]{ADMNO,Name,ClassName});            
            }  
        
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String name,idno,doa,dob,fadmited,leavedate,remarks;
        InputStream Report,logo = getClass().getClassLoader().getResourceAsStream("/main/images/kenya.png");
        //extract the court of arm logo
        String fileLocation =  FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Acme";
        String filename = fileLocation+"/logo.png";
        File lg = new File(filename);
        if(lg.exists()){
            lg.delete();
            //ystem.out.println("File found and deleted");
        }
        try{
           
            FileOutputStream fos = new FileOutputStream(filename);
            byte[]b = new byte[1024];
            int i;
            while((i=logo.read(b))>=0){
                fos.write(b, 0, i);
                fos.flush();
            }
            //System.out.println("File extracted Succesfully to :"+filename);
            fos.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
         idno = txtADMNO.getText();
         name = txtName.getText();
         doa = ((JTextField)txtDOA.getDateEditor().getUiComponent()).getText();
         dob = ((JTextField)txtDOB.getDateEditor().getUiComponent()).getText();
         fadmited = cmbFamditted.getSelectedItem().toString();
         leavedate = ((JTextField)txtLeaveDate.getDateEditor().getUiComponent()).getText();
         remarks = taRmarks.getText();
         try{
             
             Report = getClass().getClassLoader().getResourceAsStream("Reports/LeavingCert.jrxml");
             String C_arm = filename;
             
             HashMap param = new HashMap();
             param.put("idno", idno);
             param.put("sname", name);
             param.put("doa", doa);
             param.put("dob", dob);
             param.put("fadmitted", fadmited);
             param.put("leavedate", leavedate);
             param.put("remarks", remarks);
             param.put("C_arm",C_arm);
             
            JasperDesign jd = JRXmlLoader.load(Report);
            JasperReport jr=JasperCompileManager.compileReport(jd);
            JasperPrint jp=JasperFillManager.fillReport(jr, param, Conn);
            JRViewer jv = new JRViewer(jp);
            RemarksDlg.setVisible(false);
            StudentDetailsDialog.setVisible(false);
                JFrame jf =new JFrame();
                jf.getContentPane().add(jv);
                jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
                jf.setType(Type.UTILITY);
                jf.validate();
                jf.setVisible(true); 
                jf.setResizable(true);
                jf.setSize(new Dimension(900,650));
                jf.setLocationRelativeTo(this);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.setTitle("Students Leaving Certificate for: "+ name);
         }catch(Exception e){
             e.printStackTrace();
         }
         //Delete file after loading it to the report
         if(lg.exists()){
            lg.delete();
            System.out.println("File found and deleted");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DecorationHelper.decorateWindows(false);
            new StudentRegistrationFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog PromotionDlg;
    private javax.swing.JTable PromotionsTableFromClass;
    private javax.swing.JTable PromotionsTableToClass;
    private javax.swing.JDialog RemarksDlg;
    private javax.swing.JDialog StudentDetailsDialog;
    private javax.swing.JTable Table_Students;
    private javax.swing.JTable Table_class;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpenStudentsDialog;
    private javax.swing.JButton btnOpenStudentsDialog1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStudentDetails;
    private javax.swing.JButton btnUploadProfilePicture;
    private javax.swing.JComboBox<String> cmbFamditted;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<String> comboClassFromPromotion;
    private javax.swing.JComboBox<String> comboClassToPromotion;
    private javax.swing.JComboBox<String> comboHouse;
    private javax.swing.JComboBox<String> comboMSTUDY;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblCount1;
    private javax.swing.JLabel lblMySubjectCount;
    private javax.swing.JLabel profilePic;
    private javax.swing.JTextArea taRmarks;
    private javax.swing.JTable tableDetailSubjects;
    private javax.swing.JTextField txtADMNO;
    private javax.swing.JLabel txtClass;
    private com.toedter.calendar.JDateChooser txtDOA;
    private com.toedter.calendar.JDateChooser txtDOB;
    private javax.swing.JTextField txtFather;
    private javax.swing.JTextField txtKCPE;
    private javax.swing.JTextField txtKcpeGrade;
    private com.toedter.calendar.JDateChooser txtLeaveDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtPath;
    private javax.swing.JTextField txtPhone1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
