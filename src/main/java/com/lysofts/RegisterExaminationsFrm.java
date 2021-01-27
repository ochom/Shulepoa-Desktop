
package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class RegisterExaminationsFrm extends javax.swing.JFrame {
    
    Connection Conn=ConnClass.connectDB();
    PreparedStatement pst=null; 
    ResultSet rs=null; 
    String sql=null;
    
    
    public RegisterExaminationsFrm() {
        initComponents();        
        
        jLabel3.setVisible(false);
        jProgressBar1.setVisible(false);
        ProgressNo.setVisible(false);
        
        new ConnClass().setFrameIcon(this);
        getSavedExaminations();
        getMostRecentSavedExaminations();
        
    }
    
    private void UpdateStudentsEntriesForAnExam(String Year,String Term){
        SwingWorker<Void,Integer> worker = new SwingWorker<Void,Integer>(){
            int Total = 0; 
           @Override
           protected Void doInBackground() throws Exception {    
                jLabel3.setVisible(true);
                jProgressBar1.setVisible(true);
                ProgressNo.setVisible(true);
                 try{                    
                    PreparedStatement ps = Conn.prepareStatement("Select Count(*)  AS Total From student_details WHERE (Student_class != 'Completed')");
                    ResultSet rst = ps.executeQuery();
                    if(rst.next()){
                        Total = Integer.parseInt(rst.getString("Total"));
                    }
                    pst  = Conn.prepareStatement("SELECT * FROM Student_details WHERE (Student_class != 'Completed')");
                    rs =pst.executeQuery();
                    int i=0;                   
                    while(rs.next()){ 
                        i++;
                       Thread.sleep(10);
                       String StudentID =rs.getString("Student_id");
                       String StudentClass = rs.getString("Student_Class");              
                       String sql1 = "Select Count(*) from Students_exams WHERE (SE_Student_id = '"+StudentID+"' AND Year='"+Year+"' AND Term='"+Term+"' AND SE_StudentClass='"+StudentClass+"')";
                       PreparedStatement pst1 = Conn.prepareStatement(sql1);
                       ResultSet rs1 = pst1.executeQuery();
                       if(rs1.next()){
                           int Count = Integer.parseInt(rs1.getString("Count(*)")); 
                           if(Count<1){
                               pst  = Conn.prepareStatement("INSERT INTO Students_Exams (SE_Student_id,SE_StudentClass,Year,Term)VALUES(?,?,?,?)");
                               pst.setString(1, StudentID);    pst.setString(2, StudentClass);
                               pst.setString(3, Year);     pst.setString(4, Term);   
                               pst.executeUpdate();
                           }
                       }
                    publish(i);
                 }
                }catch(SQLException e){
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
           protected void process( List <Integer> chucks){
               int progress = chucks.get(chucks.size()-1);
               ProgressNo.setText((int) ((progress/(Total * 1.0))*100) +"%");
               jProgressBar1.setValue((int) ((progress/(Total * 1.0))*100));
           }
           @Override
           protected void done(){
                jLabel3.setVisible(false);
                jProgressBar1.setVisible(false);
                ProgressNo.setVisible(false);
                
                RegisterExaminationsFrm.this.dispose();
                new AdminPanelFrm().setVisible(true); 
           }
       };
       worker.execute();
    
    }
    private void getSavedExaminations(){
        comboYear.removeAllItems();
        try{
            sql = "Select * From tblExams GROUP BY Year  ORDER BY Year DESC ";
            pst = Conn.prepareStatement(sql);
            rs =pst.executeQuery();
            while(rs.next()){
                comboYear.addItem(rs.getString("Year"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    private void getMostRecentSavedExaminations(){
        try{
            sql = "Select * From tblExams ORDER BY Examination_id DESC limit 1";
            pst = Conn.prepareStatement(sql);
            rs =pst.executeQuery();
            if(rs.next()){
                comboYear.setSelectedItem(rs.getString("Year"));
                comboTerm.setSelectedItem(rs.getString("Term"));
            }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboTerm = new javax.swing.JComboBox<>();
        comboYear = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JButton();
        ProgressNo = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(479, 249));
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);
        setSize(new java.awt.Dimension(479, 249));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update term details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Academic Year");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Academic Term");

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
        comboTerm.setToolTipText("");

        comboYear.setEditable(true);
        comboYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboYear.setToolTipText("");

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 153, 0));
        btnSave.setText("Next > >");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0)));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboYear, javax.swing.GroupLayout.Alignment.LEADING, 0, 174, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboTerm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 29, 498, -1));

        ProgressNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ProgressNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ProgressNo.setText("NO");
        jPanel3.add(ProgressNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 70, 15));

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 51));
        jProgressBar1.setString("");
        jProgressBar1.setStringPainted(true);
        jPanel3.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 500, 10));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 51));
        jLabel3.setText("Updating Students details for this exams please wait...");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 390, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String Year = comboYear.getSelectedItem().toString();
        String Term = comboTerm.getSelectedItem().toString();
        if(Year.isEmpty()){
            JOptionPane.showMessageDialog(null, "Enter the Year of Examination");
        }else if(Term.equalsIgnoreCase("Select")){
            JOptionPane.showMessageDialog(null, "Select the current term of Examination");
        }else{
            btnSave.setEnabled(false);
            try{
                sql ="SELECT Count(*) FROM tblExams Where (Year='"+Year+"' AND TERM='"+Term+"')";
                pst = Conn.prepareStatement(sql);
                rs = pst.executeQuery();
                if(rs.next()){
                    int Count = Integer.parseInt(rs.getString("Count(*)"));
                    if(Count<=0){
                        sql = "INSERT INTO tblExams (Year,Term,Exam_name)VALUES(?,?,?)";
                        pst = Conn.prepareStatement(sql);
                        pst.setString(1, Year); pst.setString(2, Term); pst.setString(3, "Default");
                        pst.executeUpdate();
                        System.out.println("Examination details updated Succesfully");  
                    } 
                }
                UpdateStudentsEntriesForAnExam(Year,Term);
            }catch(SQLException e){
                btnSave.setEnabled(true);
                JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(),"System Error",0); 
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try{
            Conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ConnClass.printError(ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DecorationHelper.decorateWindows(false);
                new RegisterExaminationsFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
