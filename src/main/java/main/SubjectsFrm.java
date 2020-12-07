
package main;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class SubjectsFrm extends javax.swing.JFrame {
    Connection Conn =ConnClass.connectDB();
    PreparedStatement pst =null;
    ResultSet rs =null;

    public SubjectsFrm() {
        initComponents();
        getIcon();
        
        Update_Table();
    }
    private void getIcon(){
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/A Logo Icon File.png")));
          
        }catch(Exception e){
            System.out.println(e);
        }
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/A logo.png")));
          
        }catch(Exception e){
            System.out.println(e);
      
        }
    }
    private void AddSubject(){
        if(txtCODE.getText().trim().isEmpty() || txtSUBJECT.getText().trim().isEmpty() || txtGroup.getSelectedItem().toString().equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null,"Subject Code, Name and group are required");
        }else if(txtGrade1.getText().equalsIgnoreCase("")||
                    txtGrade2.getText().equalsIgnoreCase("")||
                    txtGrade3.getText().equalsIgnoreCase("")||
                    txtGrade4.getText().equalsIgnoreCase("")||
                    txtGrade5.getText().equalsIgnoreCase("")||
                    txtGrade6.getText().equalsIgnoreCase("")||
                    txtGrade7.getText().equalsIgnoreCase("")||
                    txtGrade8.getText().equalsIgnoreCase("")||
                    txtGrade9.getText().equalsIgnoreCase("")||
                    txtGrade10.getText().equalsIgnoreCase("")||
                    txtGrade11.getText().equalsIgnoreCase("")||
                    txtGrade12.getText().equalsIgnoreCase("")
                ){
                    JOptionPane.showMessageDialog(null,"All the grading mark points are required");
        }
        else{
        try{
           String sql = "INSERT INTO subjects(Subject_code,Subject_name,Subject_group,Grade1,Grade2,Grade3,Grade4,Grade5,Grade6,Grade7,Grade8,Grade9,Grade10,Grade11,Grade12,S_NO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           pst=Conn.prepareStatement(sql);
           
           pst.setString(16, txtNO.getSelectedItem().toString());
           pst.setString(1,txtCODE.getText().toUpperCase());
           pst.setString(2,txtSUBJECT.getText().toUpperCase());
           String S_group  = txtGroup.getSelectedItem().toString();
           if(S_group.equalsIgnoreCase("Select")){
               JOptionPane.showMessageDialog(null,"Select subject group");
           }else{
               pst.setString(3,S_group.toUpperCase());
           }
           //Grading system saving
            int Grade1 = Integer.parseInt(txtGrade1.getText());
            int Grade2 = Integer.parseInt(txtGrade2.getText());
            int Grade3 = Integer.parseInt(txtGrade3.getText());
            int Grade4 = Integer.parseInt(txtGrade4.getText());
            int Grade5 = Integer.parseInt(txtGrade5.getText());
            int Grade6 = Integer.parseInt(txtGrade6.getText());
            int Grade7 = Integer.parseInt(txtGrade7.getText());
            int Grade8 = Integer.parseInt(txtGrade8.getText());
            int Grade9 = Integer.parseInt(txtGrade9.getText());
            int Grade10 = Integer.parseInt(txtGrade10.getText());
            int Grade11 = Integer.parseInt(txtGrade11.getText());
            int Grade12 = Integer.parseInt(txtGrade12.getText());
            
        //Passing values to prepared statements        
           if(Grade1 > Grade2){
               pst.setString(4,txtGrade1.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between A and A-");
           }
           if(Grade2 > Grade3){
               pst.setString(5,txtGrade2.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between A- and B+");
           }
           if(Grade3 > Grade4){
               pst.setString(6,txtGrade3.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B+ and B");
           }
           if(Grade4 > Grade5){
               pst.setString(7,txtGrade4.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B and B-");
           }
           if(Grade5 > Grade6){
               pst.setString(8,txtGrade5.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B- and C+");
           }
           if(Grade6 > Grade7){
               pst.setString(9,txtGrade6.getText()); 
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C+ and C");
           }
           if(Grade7 > Grade8){
               pst.setString(10,txtGrade7.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C and C-");
           }
           if(Grade8 > Grade9){
               pst.setString(11,txtGrade8.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C- and D+");
           }
           if(Grade9 > Grade10){
               pst.setString(12,txtGrade9.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D+ and D");
           }
           if(Grade10 > Grade11){
               pst.setString(13,txtGrade10.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D and D-");
           }
           if(Grade11 > Grade12){
               pst.setString(14,txtGrade11.getText());
               pst.setString(15,txtGrade12.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D- and E");
           }        
           
           
           pst.executeUpdate();
           JOptionPane.showMessageDialog(null,"Subject Added Succesfully");
           Update_Table();
        }
        catch(HeadlessException | NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(null, "This subject number already registered "+ e);
        }
      }

    }
    private void Update_Table(){   
    try{
        DefaultTableModel  model = (DefaultTableModel) Table_subjects.getModel();
        model.setRowCount(0);
        String sql = "SELECT * FROM subjects ORDER BY (S_NO+0) ASC";
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            String NO =rs.getString("S_No");
            String CODE =rs.getString("Subject_code");
            String Name =rs.getString("Subject_name");
            String group =rs.getString("Subject_group");            
            model.addRow(new Object[]{NO,CODE,Name,group});
        }
        }
    catch(SQLException e){
        System.out.println(e);
        }
    }
    private void AddSubjectToSubjectNumbers(){
    String SubjectCode= txtCODE.getText().toUpperCase();
    String SubjectNumber =txtNO.getSelectedItem().toString();
    String SubjectName=txtSUBJECT.getText().toUpperCase();
    String sql=null;
    try{
        if(SubjectNumber.equalsIgnoreCase("1")){
           sql ="UPDATE Basic_SubjectNumbers SET S1CODE='"+SubjectCode+"', S1NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("2")){
           sql ="UPDATE Basic_SubjectNumbers SET S2CODE='"+SubjectCode+"', S2NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("3")){
           sql ="UPDATE Basic_SubjectNumbers SET S3CODE='"+SubjectCode+"', S3NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("4")){
           sql ="UPDATE Basic_SubjectNumbers SET S4CODE='"+SubjectCode+"', S4NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("5")){
           sql ="UPDATE Basic_SubjectNumbers SET S5CODE='"+SubjectCode+"', S5NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("6")){
           sql ="UPDATE Basic_SubjectNumbers SET S6CODE='"+SubjectCode+"', S6NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("7")){
           sql ="UPDATE Basic_SubjectNumbers SET S7CODE='"+SubjectCode+"', S7NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("8")){
           sql ="UPDATE Basic_SubjectNumbers SET S8CODE='"+SubjectCode+"', S8NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("9")){
           sql ="UPDATE Basic_SubjectNumbers SET S9CODE='"+SubjectCode+"', S9NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("10")){
           sql ="UPDATE Basic_SubjectNumbers SET S10CODE='"+SubjectCode+"', S10NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("11")){
           sql ="UPDATE Basic_SubjectNumbers SET S11CODE='"+SubjectCode+"', S11NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("12")){
           sql ="UPDATE Basic_SubjectNumbers SET S12CODE='"+SubjectCode+"', S12NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("13")){
           sql ="UPDATE Basic_SubjectNumbers SET S13CODE='"+SubjectCode+"', S13NAME='"+SubjectName+"'";
        }else if(SubjectNumber.equalsIgnoreCase("14")){
           sql ="UPDATE Basic_SubjectNumbers SET S14CODE='"+SubjectCode+"', S14NAME='"+SubjectName+"'";
        }
        pst=Conn.prepareStatement(sql);
        pst.executeUpdate();
        System.out.println("Subjects Basic Numbers Registered....");
        Update_Table();
    }
    catch(SQLException e){
        System.out.println(e);
    }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_subjects = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtGrade1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtGrade2 = new javax.swing.JTextField();
        txtGrade4 = new javax.swing.JTextField();
        txtGrade3 = new javax.swing.JTextField();
        txtGrade8 = new javax.swing.JTextField();
        txtGrade7 = new javax.swing.JTextField();
        txtGrade6 = new javax.swing.JTextField();
        txtGrade5 = new javax.swing.JTextField();
        txtGrade11 = new javax.swing.JTextField();
        txtGrade10 = new javax.swing.JTextField();
        txtGrade9 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtGrade12 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtSUBJECT = new javax.swing.JTextField();
        txtCODE = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtGroup = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txtNO = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(529, 430));
        setPreferredSize(new java.awt.Dimension(529, 430));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 204, 0));

        jLabel16.setFont(new java.awt.Font("Old English Text MT", 3, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Easy Subjects SetUp");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
        );

        Table_subjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "CODE", "SUBJECT", "GROUP"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_subjects.getTableHeader().setReorderingAllowed(false);
        Table_subjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_subjectsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_subjects);
        if (Table_subjects.getColumnModel().getColumnCount() > 0) {
            Table_subjects.getColumnModel().getColumn(0).setPreferredWidth(30);
            Table_subjects.getColumnModel().getColumn(1).setPreferredWidth(30);
            Table_subjects.getColumnModel().getColumn(2).setPreferredWidth(100);
            Table_subjects.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel3.setText("NAME");

        jPanel3.setBackground(new java.awt.Color(0, 0, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "This subject's grading system", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("GRADE");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 17, 89, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("A");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 39, 89, -1));

        txtGrade1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade1KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade1, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 39, 50, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("A-");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 61, 89, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("B+");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 83, 89, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("B");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 105, 89, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("B-");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 127, 89, -1));

        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("C+");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 149, 89, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("C");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 171, 89, -1));

        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("C-");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 193, 89, -1));

        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("D+");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 215, 89, -1));

        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("D");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 237, 89, -1));

        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("D-");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 259, 89, -1));

        txtGrade2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade2KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade2, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 61, 50, -1));

        txtGrade4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade4KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade4, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 105, 50, -1));

        txtGrade3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade3KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade3, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 83, 50, -1));

        txtGrade8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade8KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade8, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 193, 50, -1));

        txtGrade7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade7KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade7, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 171, 50, -1));

        txtGrade6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade6KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade6, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 149, 50, -1));

        txtGrade5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade5KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade5, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 127, 50, -1));

        txtGrade11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade11KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade11, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 259, 50, -1));

        txtGrade10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade10KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade10, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 237, 50, -1));

        txtGrade9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade9KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade9, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 215, 50, -1));

        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("E");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 281, 89, -1));

        txtGrade12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade12KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade12, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 281, 50, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("MIN");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 17, 50, -1));

        txtSUBJECT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtSUBJECT.setText("e.g English");
        txtSUBJECT.setToolTipText("e.g English");
        txtSUBJECT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSUBJECTMouseClicked(evt);
            }
        });
        txtSUBJECT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSUBJECTKeyReleased(evt);
            }
        });

        txtCODE.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCODE.setText("e.g ENG");
        txtCODE.setToolTipText("e.g ENG");
        txtCODE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCODEMouseClicked(evt);
            }
        });
        txtCODE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCODEKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCODEKeyTyped(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Save_16x16.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Delete_16x16.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel17.setText("CODE");

        jLabel19.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel19.setText("GROUP");

        txtGroup.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Group A", "Group B", "Group C", "Group D", "Group E" }));

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/images/Check_16x16.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        jLabel20.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel20.setText("S.N #");

        txtNO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtCODE, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtSUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel17)
                    .addComponent(jLabel3)
                    .addComponent(jLabel19))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCODE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        setSize(new java.awt.Dimension(629, 443));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddSubject();
        AddSubjectToSubjectNumbers();      
        btnNew.setEnabled(true);btnSave.setEnabled(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void Table_subjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_subjectsMouseClicked
        try{
            int row =Table_subjects.getSelectedRow();
            String Table_click = Table_subjects.getModel().getValueAt(row,1).toString();
            String sql  ="SELECT * FROM subjects WHERE Subject_code='"+Table_click+"'";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                btnUpdate.setEnabled(true);
                txtCODE.setEnabled(true);
                txtNO.setEnabled(false);
                txtNO.setSelectedItem(rs.getString("S_NO"));
                txtCODE.setText(rs.getString("Subject_code"));
                txtSUBJECT.setText(rs.getString("Subject_name"));
                String Group = rs.getString("Subject_group");
                    if(Group.equalsIgnoreCase("GROUP A")){
                        txtGroup.setSelectedIndex(1);
                    }else if(Group.equalsIgnoreCase("GROUP B")){
                        txtGroup.setSelectedIndex(2);
                    }else if(Group.equalsIgnoreCase("GROUP C")){
                        txtGroup.setSelectedIndex(3);
                    }else if(Group.equalsIgnoreCase("GROUP D")){
                        txtGroup.setSelectedIndex(4);
                    }else if(Group.equalsIgnoreCase("GROUP E")){
                        txtGroup.setSelectedIndex(5);
                    } else{
                        txtGroup.setSelectedIndex(0);
                    }
                
                //Grading system 
                txtGrade1.setText(rs.getString("Grade1"));
                txtGrade2.setText(rs.getString("Grade2"));
                txtGrade3.setText(rs.getString("Grade3"));
                txtGrade4.setText(rs.getString("Grade4"));
                txtGrade5.setText(rs.getString("Grade5"));
                txtGrade6.setText(rs.getString("Grade6"));
                txtGrade7.setText(rs.getString("Grade7"));
                txtGrade8.setText(rs.getString("Grade8"));
                txtGrade9.setText(rs.getString("Grade9"));
                txtGrade10.setText(rs.getString("Grade10"));
                txtGrade11.setText(rs.getString("Grade11"));
                txtGrade12.setText(rs.getString("Grade12"));
                
                //Button status
               btnNew.setEnabled(true); btnSave.setEnabled(false);
               btnDelete.setEnabled(true);
            }
        }
        catch(SQLException e){
        System.out.println(e);
        }
    }//GEN-LAST:event_Table_subjectsMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       try{
        if(txtCODE.getText().trim().isEmpty() || txtSUBJECT.getText().trim().isEmpty() || txtGroup.getSelectedItem().toString().equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null,"Subject Code, Name and group are required");
        }else if(   txtGrade1.getText().equalsIgnoreCase("")||
                    txtGrade2.getText().equalsIgnoreCase("")||
                    txtGrade3.getText().equalsIgnoreCase("")||
                    txtGrade4.getText().equalsIgnoreCase("")||
                    txtGrade5.getText().equalsIgnoreCase("")||
                    txtGrade6.getText().equalsIgnoreCase("")||
                    txtGrade7.getText().equalsIgnoreCase("")||
                    txtGrade8.getText().equalsIgnoreCase("")||
                    txtGrade9.getText().equalsIgnoreCase("")||
                    txtGrade10.getText().equalsIgnoreCase("")||
                    txtGrade11.getText().equalsIgnoreCase("")||
                    txtGrade12.getText().equalsIgnoreCase("")
                ){
                    JOptionPane.showMessageDialog(null,"All the grading mark points are required");
        }
        else{
            String S_code = txtCODE.getText().toUpperCase();
            String SNO  = txtNO.getSelectedItem().toString().toUpperCase();
            String sql = "UPDATE subjects SET Grade1=?,Grade2=?,Grade3=?,Grade4=?,Grade5=?,Grade6=?,Grade7=?,Grade8=?,Grade9=?,Grade10=?,Grade11=?,Grade12=?,Subject_name=?,Subject_group=?,Subject_Code=? WHERE S_NO='"+SNO+"'";
            pst=Conn.prepareStatement(sql);
            
            //Grading system saving
            int Grade1 = Integer.parseInt(txtGrade1.getText());
            int Grade2 = Integer.parseInt(txtGrade2.getText());
            int Grade3 = Integer.parseInt(txtGrade3.getText());
            int Grade4 = Integer.parseInt(txtGrade4.getText());
            int Grade5 = Integer.parseInt(txtGrade5.getText());
            int Grade6 = Integer.parseInt(txtGrade6.getText());
            int Grade7 = Integer.parseInt(txtGrade7.getText());
            int Grade8 = Integer.parseInt(txtGrade8.getText());
            int Grade9 = Integer.parseInt(txtGrade9.getText());
            int Grade10 = Integer.parseInt(txtGrade10.getText());
            int Grade11 = Integer.parseInt(txtGrade11.getText());
            int Grade12 = Integer.parseInt(txtGrade12.getText());
            
            //Passing values to prepared statements        
           if(Grade1 > Grade2){
               pst.setString(1,txtGrade1.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between A and A-");
           }
           if(Grade2 > Grade3){
               pst.setString(2,txtGrade2.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between A- and B+");
           }
           if(Grade3 > Grade4){
               pst.setString(3,txtGrade3.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B+ and B");
           }
           if(Grade4 > Grade5){
               pst.setString(4,txtGrade4.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B and B-");
           }
           if(Grade5 > Grade6){
               pst.setString(5,txtGrade5.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between B- and C+");
           }
           if(Grade6 > Grade7){
               pst.setString(6,txtGrade6.getText()); 
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C+ and C");
           }
           if(Grade7 > Grade8){
               pst.setString(7,txtGrade7.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C and C-");
           }
           if(Grade8 > Grade9){
               pst.setString(8,txtGrade8.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between C- and D+");
           }
           if(Grade9 > Grade10){
               pst.setString(9,txtGrade9.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D+ and D");
           }
           if(Grade10 > Grade11){
               pst.setString(10,txtGrade10.getText());
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D and D-");
           }
           if(Grade11 > Grade12){
            pst.setString(11,txtGrade11.getText());
            pst.setString(12,txtGrade12.getText()); 
           }else{
               JOptionPane.showMessageDialog(null,"Check the range between D- and E");
           }
           pst.setString(13,txtSUBJECT.getText().toUpperCase());
           pst.setString(14,txtGroup.getSelectedItem().toString().toUpperCase());
           pst.setString(15, txtCODE.getText());
          
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Succesful update");
        
        //After running the update
               btnNew.setEnabled(true); btnSave.setEnabled(false);
               btnUpdate.setEnabled(false);btnDelete.setEnabled(false);
               txtCODE.setEnabled(true);
            AddSubjectToSubjectNumbers();
            Update_Table();
        }
       }
        catch(HeadlessException | NumberFormatException | SQLException e){
            System.out.println(e);
         }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String Delete_code = txtCODE.getText().toUpperCase();
        int response = JOptionPane.showConfirmDialog(null, "The Subject will be deleted Permanently","Delete",JOptionPane.YES_NO_OPTION);
        if (response==0){
        try{
            String sql = "DELETE FROM subjects WHERE Subject_code='"+Delete_code+"'";
            pst=Conn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Subject deleted");  
            Update_Table();
            txtCODE.setText("");txtSUBJECT.setText("");txtGroup.setSelectedIndex(0);
            btnNew.setEnabled(true);btnSave.setEnabled(false); btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
        }
            
        catch(HeadlessException | SQLException e){
            System.out.println(e);
        }  
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtNO.setEnabled(true);txtCODE.setEnabled(true);btnNew.setEnabled(false);
        btnSave.setEnabled(true);btnUpdate.setEnabled(false);btnDelete.setEnabled(false);
    }//GEN-LAST:event_btnNewActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
        this.dispose();
        new AdminPanelFrm().setVisible(true);        
    }//GEN-LAST:event_formWindowClosing

    private void txtSUBJECTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSUBJECTMouseClicked
        txtSUBJECT.setText("");
    }//GEN-LAST:event_txtSUBJECTMouseClicked

    private void txtCODEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCODEMouseClicked
        txtCODE.setText("");
    }//GEN-LAST:event_txtCODEMouseClicked

    private void txtCODEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCODEKeyTyped
        char c = evt.getKeyChar();
        int length = txtCODE.getText().length();
        if(length>=3){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtCODEKeyTyped

    private void txtGrade1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade1KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade1.getText().length();
        if(length>=2){
            if (!(c == KeyEvent.VK_BACK_SPACE || c ==KeyEvent.VK_DELETE)) {
                txtGrade2.requestFocus();
                evt.consume();
            }
        }
        
    }//GEN-LAST:event_txtGrade1KeyTyped

    private void txtGrade2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade2KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade2.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade3.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade2KeyTyped

    private void txtGrade3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade3KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade3.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade4.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade3KeyTyped

    private void txtGrade4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade4KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade4.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade5.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade4KeyTyped

    private void txtGrade5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade5KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade5.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade6.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade5KeyTyped

    private void txtGrade6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade6KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade6.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade7.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade6KeyTyped

    private void txtGrade7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade7KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade7.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade8.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade7KeyTyped

    private void txtGrade8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade8KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade8.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade9.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade8KeyTyped

    private void txtGrade9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade9KeyTyped
       char c = evt.getKeyChar();
        int length = txtGrade9.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade10.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade9KeyTyped

    private void txtGrade10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade10KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade10.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade11.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade10KeyTyped

    private void txtGrade11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade11KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade11.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade12.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade11KeyTyped

    private void txtGrade12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade12KeyTyped
       char c = evt.getKeyChar();
        int length = txtGrade12.getText().length();
        if(length>=2){
            if (!(c ==KeyEvent.VK_BACK_SPACE  || c ==KeyEvent.VK_DELETE)) {
                txtGrade1.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade12KeyTyped

    private void txtCODEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCODEKeyReleased
        txtCODE.setText(txtCODE.getText().toUpperCase());
    }//GEN-LAST:event_txtCODEKeyReleased

    private void txtSUBJECTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSUBJECTKeyReleased
        txtSUBJECT.setText(txtSUBJECT.getText().toUpperCase());
    }//GEN-LAST:event_txtSUBJECTKeyReleased


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SubjectsFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SubjectsFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SubjectsFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SubjectsFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SubjectsFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_subjects;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCODE;
    private javax.swing.JTextField txtGrade1;
    private javax.swing.JTextField txtGrade10;
    private javax.swing.JTextField txtGrade11;
    private javax.swing.JTextField txtGrade12;
    private javax.swing.JTextField txtGrade2;
    private javax.swing.JTextField txtGrade3;
    private javax.swing.JTextField txtGrade4;
    private javax.swing.JTextField txtGrade5;
    private javax.swing.JTextField txtGrade6;
    private javax.swing.JTextField txtGrade7;
    private javax.swing.JTextField txtGrade8;
    private javax.swing.JTextField txtGrade9;
    private javax.swing.JComboBox<String> txtGroup;
    private javax.swing.JComboBox<String> txtNO;
    private javax.swing.JTextField txtSUBJECT;
    // End of variables declaration//GEN-END:variables
}
