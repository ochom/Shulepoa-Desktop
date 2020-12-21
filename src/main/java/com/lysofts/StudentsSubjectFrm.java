
package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class StudentsSubjectFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String selectedClass = null, sql=null;
    
    public StudentsSubjectFrm() {
        initComponents();
        
        getIcon();
        boolean selectedSub = false;
        getSubjects(selectedSub);
        getClasses(); 
    }
    private void getIcon(){
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/A Logo Icon File.png")));
          
        }catch(Exception e){
            System.out.println(e);
        }
        try {
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/A logo.png")));
          
        }catch(Exception e){
            System.out.println(e);
      
        }
    }
    private void getClasses(){
        DefaultTableModel model = (DefaultTableModel) Table_class.getModel();
        model.setRowCount(0);
        try{
            sql = "SELECT Class_name AS FORM FROM tblclasses";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String ClassName = rs.getString("FORM");
                model.addRow(new Object[]{ClassName});
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void getSubjects(boolean bool){
        DefaultTableModel model = (DefaultTableModel) Table_Subject.getModel();
        model.setRowCount(0);
        try{
            sql = "SELECT Subject_code AS CODE,Subject_name AS SUBJECT FROM subjects ORDER BY (S_NO+0) ASC";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String CODE = rs.getString("CODE");
                String SubjName = rs.getString("SUBJECT");
                model.addRow(new Object[]{CODE,SubjName,bool});
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    private void UpdateTableStudents(boolean check){
        DefaultTableModel model= (DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        try{
            int row  = Table_class.getSelectedRow();
            String Clicked_cell = Table_class.getValueAt(row, 0).toString();            
            sql = "SELECT * FROM student_details WHERE Student_class='"+Clicked_cell+"' ORDER BY (Student_id + 0) ASC ";
            pst=Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");                
                model.addRow(new Object[]{check,ADMNO,Name});            
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentDetailsDialog = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetailSubjects = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtADMNO = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        lblMySubjectCount = new javax.swing.JLabel();
        lblMySubjectCount1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        ProgressPane = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        ProgressNo = new javax.swing.JLabel();
        ClosingDlg = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        ProgressNo2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Table_class = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_Subject = new javax.swing.JTable();
        jCheckBox2 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table_Students = new javax.swing.JTable();
        btnAssignToAll = new javax.swing.JButton();
        btnResetThisSubjectAssignment = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        btnStudentDetails = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        StudentDetailsDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        StudentDetailsDialog.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        StudentDetailsDialog.setLocation(new java.awt.Point(50, 50));
        StudentDetailsDialog.setMinimumSize(new java.awt.Dimension(430, 501));
        StudentDetailsDialog.setModal(true);
        StudentDetailsDialog.setResizable(false);
        StudentDetailsDialog.setSize(new java.awt.Dimension(541, 350));
        StudentDetailsDialog.setType(java.awt.Window.Type.UTILITY);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

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

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 290, 270));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 180));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("NAME");

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(255, 255, 255));
        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtName.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtName.setText("Student Name");
        txtName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        txtADMNO.setEditable(false);
        txtADMNO.setBackground(new java.awt.Color(255, 255, 255));
        txtADMNO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtADMNO.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtADMNO.setText("ADM NO.");
        txtADMNO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("ADM");

        lblMySubjectCount.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMySubjectCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMySubjectCount.setText("COUNT");

        lblMySubjectCount1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMySubjectCount1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMySubjectCount1.setText("ENTRY");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMySubjectCount1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMySubjectCount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMySubjectCount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMySubjectCount1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(0, 204, 0));

        jLabel22.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Allocated Subjects");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout StudentDetailsDialogLayout = new javax.swing.GroupLayout(StudentDetailsDialog.getContentPane());
        StudentDetailsDialog.getContentPane().setLayout(StudentDetailsDialogLayout);
        StudentDetailsDialogLayout.setHorizontalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        StudentDetailsDialogLayout.setVerticalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ProgressPane.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ProgressPane.setModal(true);
        ProgressPane.setType(java.awt.Window.Type.UTILITY);
        ProgressPane.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ProgressPaneWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 204, 51));
        jLabel11.setText("Assigning subjects to students Please wait...");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);

        ProgressNo.setText("NO");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout ProgressPaneLayout = new javax.swing.GroupLayout(ProgressPane.getContentPane());
        ProgressPane.getContentPane().setLayout(ProgressPaneLayout);
        ProgressPaneLayout.setHorizontalGroup(
            ProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ProgressPaneLayout.setVerticalGroup(
            ProgressPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ClosingDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ClosingDlg.setModal(true);
        ClosingDlg.setType(java.awt.Window.Type.UTILITY);
        ClosingDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                ClosingDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 51));
        jLabel13.setText("Updating your data, this may take a while...");

        jProgressBar2.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar2.setString("");
        jProgressBar2.setStringPainted(true);

        ProgressNo2.setText("NO");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ProgressNo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ProgressNo2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ClosingDlgLayout = new javax.swing.GroupLayout(ClosingDlg.getContentPane());
        ClosingDlg.getContentPane().setLayout(ClosingDlgLayout);
        ClosingDlgLayout.setHorizontalGroup(
            ClosingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ClosingDlgLayout.setVerticalGroup(
            ClosingDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, 300));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Subject", 0, 0, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 204, 0))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_Subject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODE", "SUBJECT", "Check"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Subject.getTableHeader().setReorderingAllowed(false);
        Table_Subject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_SubjectMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table_Subject);
        if (Table_Subject.getColumnModel().getColumnCount() > 0) {
            Table_Subject.getColumnModel().getColumn(0).setPreferredWidth(2);
            Table_Subject.getColumnModel().getColumn(1).setPreferredWidth(100);
            Table_Subject.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 260, 340));

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 0, 204));
        jCheckBox2.setText("Select All");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 170, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assign to students", 0, 0, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        Table_Students.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CHECK", "ADM NO", "NAME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Students.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Table_Students);
        if (Table_Students.getColumnModel().getColumnCount() > 0) {
            Table_Students.getColumnModel().getColumn(0).setPreferredWidth(20);
            Table_Students.getColumnModel().getColumn(1).setPreferredWidth(20);
            Table_Students.getColumnModel().getColumn(2).setPreferredWidth(120);
        }

        btnAssignToAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAssignToAll.setForeground(new java.awt.Color(255, 0, 0));
        btnAssignToAll.setText("Assign to list");
        btnAssignToAll.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        btnAssignToAll.setContentAreaFilled(false);
        btnAssignToAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAssignToAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssignToAllActionPerformed(evt);
            }
        });

        btnResetThisSubjectAssignment.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnResetThisSubjectAssignment.setForeground(new java.awt.Color(255, 0, 0));
        btnResetThisSubjectAssignment.setText("Reset");
        btnResetThisSubjectAssignment.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        btnResetThisSubjectAssignment.setContentAreaFilled(false);
        btnResetThisSubjectAssignment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResetThisSubjectAssignment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetThisSubjectAssignmentActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(0, 0, 153));
        jCheckBox1.setText("Select All");
        jCheckBox1.setBorder(null);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        btnStudentDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnStudentDetails.setForeground(new java.awt.Color(255, 0, 0));
        btnStudentDetails.setText("Details");
        btnStudentDetails.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        btnStudentDetails.setContentAreaFilled(false);
        btnStudentDetails.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStudentDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentDetailsActionPerformed(evt);
            }
        });

        txtSearch.setBackground(new java.awt.Color(255, 255, 102));
        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAssignToAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnStudentDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(btnResetThisSubjectAssignment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAssignToAll, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnResetThisSubjectAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStudentDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        jLabel2.setText("Select an Item by Mouse Click in the order of Class >> Subject >> Students to be allocated");
        jLabel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tip", 0, 0, new java.awt.Font("Calisto MT", 2, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Subject Allocation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(929, 572));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void Table_classMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_classMouseClicked
        selectedClass = Table_class.getValueAt(Table_class.getSelectedRow(), 0).toString(); 
        UpdateTableStudents(false);   
    }//GEN-LAST:event_Table_classMouseClicked

    private void Table_SubjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_SubjectMouseClicked
          
    }//GEN-LAST:event_Table_SubjectMouseClicked
    private void AssignSubjects(){
    SwingWorker<Void,Integer> worker = new SwingWorker<Void,Integer>(){
         int Total = 0;
           @Override
           protected Void doInBackground() throws Exception {
                int Rows = Table_Subject.getRowCount();
                int Rows1 = Table_Students.getRowCount();
                for(int row=0; row<Rows; row++){
                    if((boolean)Table_Subject.getValueAt(row, 2)){
                        for(int row1=0; row1<Rows1; row1++){
                            if((boolean)Table_Students.getValueAt(row1, 0)){
                                Total ++; //total number of students selected for this subject                                
                            }
                        }
                    }
                }  
                System.out.println(Total);
                int i = 0; //The total number of loops
                for(int row=0; row<Rows; row++){
                    boolean CheckedSubj = (boolean)Table_Subject.getValueAt(row, 2);
                    if(CheckedSubj){
                        String CODE = (String) Table_Subject.getValueAt(row, 0);
                        String Subject = (String) Table_Subject.getValueAt(row, 1);
                        try{
                            for(int row1=0;row1<Rows1;row1++){
                                boolean CheckedStud = (boolean)Table_Students.getValueAt(row1, 0);
                                if(CheckedStud){
                                    i++;
                                    publish(i);
                                    String Std_id = (String)Table_Students.getValueAt(row1, 1);
                                    sql = "SELECT COUNT(*) from tblstudents_subjects WHERE SS_Student_id='"+Std_id+"' AND SS_Subject_code='"+CODE+"'";
                                    pst = Conn.prepareStatement(sql);            
                                    rs = pst.executeQuery();
                                    if(rs.next()){
                                        int NumberOfSubjectsAllocated = Integer.parseInt(rs.getString("COUNT(*)"));
                                        if(NumberOfSubjectsAllocated<1){
                                            jLabel11.setText("Assigning "+Subject+" to "+Std_id);
                                            sql = "INSERT INTO tblstudents_subjects(SS_Student_id,SS_Subject_Code,SS_Subject_name)VALUES(?,?,?)";
                                            pst = Conn.prepareStatement(sql);
                                            pst.setString(1, Std_id);
                                            pst.setString(2, CODE);
                                            pst.setString(3, Subject);
                                        pst.executeUpdate();       
                                        }  
                                    }
                                    //System.out.println(" Allocated succesfully");
                                }
                            }                
                    }catch(HeadlessException | SQLException e){
                            System.out.println(e);
                        }                
                    }
                }
                jCheckBox1.setSelected(false);
                UpdateTableStudents(false);                     
               return null;
           }
           @Override
           protected void process( java.util.List <Integer> chunks){
               int progress = chunks.get(chunks.size()-1);
               ProgressNo.setText((int) ((progress/(Total * 1.0))*100) +"% Done");
               jProgressBar1.setValue((int) ((progress/(Total * 1.0))*100));
           }
           @Override
           protected void done(){   
               ProgressPane.dispose();
               JOptionPane.showMessageDialog(null, "Selected subjects succesfully allocated","acme",JOptionPane.INFORMATION_MESSAGE);           
           }
       };
       worker.execute();    
    } 
    private void btnAssignToAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssignToAllActionPerformed
        String ClassName  = (String)(Table_class.getValueAt(Table_class.getSelectedRow(), 0)) ;
        if(ClassName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Select A Class","acme",JOptionPane.INFORMATION_MESSAGE);
        }else{
            jProgressBar1.setIndeterminate(false);
            ProgressPane.pack();
            ProgressPane.setLocationRelativeTo(null);
            ProgressPane.setVisible(true);
        }      
    }//GEN-LAST:event_btnAssignToAllActionPerformed

    private void btnResetThisSubjectAssignmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetThisSubjectAssignmentActionPerformed
        String ClassName  = (String)(Table_class.getValueAt(Table_class.getSelectedRow(), 0)) ;
        if(!ClassName.isEmpty()){
        int res = JOptionPane.showConfirmDialog(null, "Clear Allocation of Selected Subjects in Form "+ClassName,"acme",JOptionPane.YES_NO_OPTION);
        if(res==JOptionPane.YES_OPTION){
         try{
             int Rows = Table_Subject.getRowCount();
             for(int row =0;row<Rows;row++){
                 boolean CheckedSubj = (boolean)Table_Subject.getValueAt(row, 2);
                if(CheckedSubj){
                String SubjecCode =(String)Table_Subject.getValueAt(row,0);
                    sql = "DELETE FROM tblstudents_subjects WHERE SS_Student_id IN(SELECT Student_id FROM"
                            + " student_details WHERE student_class='"+ClassName+"') AND SS_Subject_code='"+SubjecCode+"'";
                    pst =Conn.prepareStatement(sql);
                    pst .executeUpdate();
                }
            } 
             UpdateTableStudents(false); 
             JOptionPane.showMessageDialog(null, "Subject Allocation reset succcesfully","acme",JOptionPane.INFORMATION_MESSAGE);
         }catch(SQLException e){
                    System.out.println(e);
          }        
        }
        }       
    }//GEN-LAST:event_btnResetThisSubjectAssignmentActionPerformed

    private void tableDetailSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDetailSubjectsMousePressed
       int row  = tableDetailSubjects.getSelectedRow();
        String SelectedCell =tableDetailSubjects.getModel().getValueAt(row, 0).toString();
        int res = JOptionPane.showConfirmDialog(null, "Remove "+ SelectedCell + " for this student", "acme",JOptionPane.WARNING_MESSAGE);
        if (res==0){
            try{
            sql = "DELETE FROM tblStudents_subjects WHERE SS_Subject_code='"+SelectedCell+"' AND SS_student_id='"+txtADMNO.getText()+"'";
            pst = Conn.prepareStatement(sql);
            pst.executeUpdate();
            int count = Integer.parseInt(lblMySubjectCount.getText())-1;
            lblMySubjectCount.setText(String.valueOf(count));
            updateMySubjects();
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }  
    }//GEN-LAST:event_tableDetailSubjectsMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       ClosingDlg.pack();
       ClosingDlg.setLocationRelativeTo(this);
       ClosingDlg.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String SearchText=txtSearch.getText();
        DefaultTableModel model=(DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        try{
            sql="SELECT * FROM student_details WHERE Student_id='"+SearchText+"' ORDER BY (Student_id + 0) ASC";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                boolean checked = false;
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");
                model.addRow(new Object[]{checked,ADMNO,Name});            
            }            
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
            sql="SELECT * FROM student_details WHERE Student_name LIKE '%"+SearchText+"%' ORDER BY (Student_id + 0) ASC ";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                boolean checked = false;
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");
                model.addRow(new Object[]{checked,ADMNO,Name});             
            }        
        }
        catch(SQLException e){
            System.out.println(e);
        }
        try{
            sql="SELECT * FROM student_details WHERE Student_Class='"+SearchText+"' ORDER BY (Student_id + 0) ASC";
            pst=Conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while (rs.next()){
                boolean checked = false;
                String ADMNO=rs.getString("Student_id");
                String Name=rs.getString("Student_name");
                model.addRow(new Object[]{checked,ADMNO,Name});             
            }  
        
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        try{
            if(jCheckBox1.isSelected()){            
            UpdateTableStudents(true); 
            }else{
            UpdateTableStudents(false);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btnStudentDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentDetailsActionPerformed
        int row = Table_Students.getSelectedRow();
        String Selected_cell = Table_Students.getValueAt(row, 1).toString();
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
                txtName.setText(rs.getString("Student_name"));
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

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
       boolean bool;
        try{
            if(jCheckBox2.isSelected()){
            bool  = true;            
            getSubjects(bool);
            }else{
            bool  = false;            
            getSubjects(bool);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jCheckBox2ActionPerformed
    
    private void ProgressPaneWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ProgressPaneWindowGainedFocus
        AssignSubjects();
    }//GEN-LAST:event_ProgressPaneWindowGainedFocus

    private void Table_classKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_classKeyReleased
        if(evt.isActionKey()){
            UpdateTableStudents(false);
        }
    }//GEN-LAST:event_Table_classKeyReleased

    private void ClosingDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_ClosingDlgWindowGainedFocus
       SwingWorker<Void,Integer> worker = new SwingWorker<Void,Integer>(){
           int Total = 0;
           @Override
           protected Void doInBackground() throws Exception {
               try{ 
                    sql = "SELECT Count(*) as Total FROM Student_details WHERE Student_Class = '"+selectedClass+"'";
                    pst=Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    if(rs.next()){
                        Total = Integer.parseInt(rs.getString("Total"));
                    }
                 }
                 catch(SQLException e){
                     System.out.println(e);
                 }
               try{ 
                    sql = "SELECT * FROM Student_details WHERE Student_Class = '"+selectedClass+"'";
                    pst=Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    int i = 0;
                    while(rs.next()){
                        i++;
                        publish(i);
                        String StudentID = rs.getString("Student_id");
                        sql ="UPDATE student_details SET SubjectEntries=(Select Count(*) from tblStudents_subjects where SS_Student_id=?) WHERE student_id=? ";
                        pst=Conn.prepareStatement(sql);
                        pst.setString(1, StudentID); pst.setString(2, StudentID);               
                        pst.executeUpdate(); 
                        jLabel13.setText("Updating subject Entry for Admission: "+StudentID);
                    }
                 }
                 catch(SQLException e){
                     System.out.println(e);
                 }finally{
                     try{
                         Conn.close();
                     }catch(SQLException e){
                         System.out.println(e);
                     }
                 }
               return null;
           }

           @Override
           protected void process(java.util.List<Integer> chunks) {
               int progress = chunks.get(chunks.size()-1);
               ProgressNo2.setText((int) ((progress/(Total * 1.0))*100) +"% Done");
               jProgressBar2.setValue((int) ((progress/(Total * 1.0))*100));
           }
           
           @Override
           protected void done(){
                ClosingDlg.dispose();                 
                StudentsSubjectFrm.this.dispose();        
                new RegisterExaminationsFrm().setVisible(true);
            }
        };
       worker.execute();
    }//GEN-LAST:event_ClosingDlgWindowGainedFocus
   
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
            new StudentsSubjectFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ClosingDlg;
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JLabel ProgressNo2;
    private javax.swing.JDialog ProgressPane;
    private javax.swing.JDialog StudentDetailsDialog;
    private javax.swing.JTable Table_Students;
    private javax.swing.JTable Table_Subject;
    private javax.swing.JTable Table_class;
    private javax.swing.JButton btnAssignToAll;
    private javax.swing.JButton btnResetThisSubjectAssignment;
    private javax.swing.JButton btnStudentDetails;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMySubjectCount;
    private javax.swing.JLabel lblMySubjectCount1;
    private javax.swing.JTable tableDetailSubjects;
    private javax.swing.JTextField txtADMNO;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
