package com.lysofts;

import com.lysofts.dao.StudentDAO;
import com.lysofts.entities.Student;
import com.lysofts.utils.ConnClass;
import com.sun.glass.events.KeyEvent;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class FeePanelFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    List<Student> students = new ArrayList<>();
    Student selectedStudent = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String Activation = "";
    InputStream reportFile = null;
    String sql = null, report_bg = null;//"C:/Acme/Exam System/verbg.png"
    String Student_adm, Student_Name, Student_Class, academic_year;

    DefaultTableModel model = null;

    Calendar cal = new GregorianCalendar();
    int day = cal.get(Calendar.DAY_OF_MONTH);
    int month = cal.get(Calendar.MONTH) + 1;
    int year = cal.get(Calendar.YEAR);
    String today = day + "/" + month + "/" + year;

    public FeePanelFrm() {
        initComponents();

        new ConnClass().setFrameIcon(FeePanelFrm.this);
        getFormNames();
        getAcademicYearFromSavedExaminations();

    }

    private void getFormNames() {
        comboForm.removeAllItems();
        comboForm.addItem("Select class");
        comboForm1.removeAllItems();
        comboForm1.addItem("Select class");
        try {
            sql = "SELECT * FROM tblclasses ORDER BY Class_name ASC";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                comboForm.addItem(rs.getString("Class_name"));
                comboForm1.addItem(rs.getString("Class_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 0);
        }
    }

    private void getAcademicYearFromSavedExaminations() {
        comboYear.removeAllItems();
        comboAcademic_Year.removeAllItems();
        comboYear2.removeAllItems();
        try {
            sql = "Select * From tblExams GROUP BY Year  ORDER BY Year DESC ";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                comboYear.addItem(rs.getString("Year"));
                comboAcademic_Year.addItem(rs.getString("Year"));
                comboYear2.addItem(rs.getString("Year"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void getStudents(String classname) {
        academic_year = comboAcademic_Year.getSelectedItem().toString();
        model = (DefaultTableModel) tblBalances.getModel();
        try {
            model.setRowCount(0);
            pst = Conn.prepareStatement("SELECT * ,(SELECT sum(debit) FROM tbl_fee_register WHERE (adm=Student_details.Student_ID AND academic_year='" + academic_year + "')) AS Required,(SELECT sum(credit) FROM tbl_fee_register WHERE (adm=Student_details.Student_ID AND academic_year='" + academic_year + "')) AS paid FROM Student_details WHERE Student_Class='" + classname + "' ORDER BY (Student_ID+0) ASC");
            rs = pst.executeQuery();
            String adm, Name;
            while (rs.next()) {
                adm = rs.getString("Student_ID");
                Name = rs.getString("Student_Name");
                String feeReq = rs.getString("Required");
                String feePaid = rs.getString("paid");
                if (feeReq == null || feeReq.isEmpty()) {
                    feeReq = "0";
                }
                if (feePaid == null || feePaid.isEmpty()) {
                    feePaid = "0";
                }
                model.addRow(new Object[]{adm, Name, feeReq, feePaid});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "System Err : " + e, "Error", 1);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CollectFeeDlg = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblClass = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblAdm = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        txtCollectionAmount = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        comboModeofPayment = new javax.swing.JComboBox<>();
        jLabel39 = new javax.swing.JLabel();
        txtTransactionID = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtDateofPayment = new com.toedter.calendar.JDateChooser();
        jLabel41 = new javax.swing.JLabel();
        txtReceiptNumber = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        comboYear = new javax.swing.JComboBox<>();
        txtServedBy = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        UpdateFeeRequiredDlg = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        comboForm1 = new javax.swing.JComboBox<>();
        comboAcademic_Year = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBalances = new javax.swing.JTable();
        btnSaveRequirements = new javax.swing.JButton();
        progDlg = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();
        ProgressNo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Students = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        comboForm = new javax.swing.JComboBox<>();
        comboYear2 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();

        CollectFeeDlg.setTitle("Fee Collection");
        CollectFeeDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));

        lblClass.setText("Class");

        lblName.setText("Name");

        lblAdm.setText("Adm No");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdm, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClass, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAdm, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClass, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Payment Details"));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText(" Amount (Ksh.)");

        txtCollectionAmount.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        txtCollectionAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCollectionAmountKeyTyped(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("Mode");

        comboModeofPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Cheque", "Mobile-Money" }));
        comboModeofPayment.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setText("Transaction ID");

        txtTransactionID.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("Date");

        txtDateofPayment.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Rec. No.");

        txtReceiptNumber.setBackground(new java.awt.Color(212, 255, 253));
        txtReceiptNumber.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
        txtReceiptNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReceiptNumberKeyTyped(evt);
            }
        });

        jLabel38.setText("Serve By:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Academic Year");

        comboYear.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtServedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTransactionID))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboModeofPayment, 0, 326, Short.MAX_VALUE)
                            .addComponent(txtCollectionAmount)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDateofPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtReceiptNumber)
                            .addComponent(comboYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel35, jLabel36, jLabel37, jLabel39, jLabel41});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {comboModeofPayment, txtCollectionAmount, txtDateofPayment, txtReceiptNumber, txtTransactionID});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtCollectionAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(comboModeofPayment))
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDateofPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtReceiptNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(comboYear))
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtServedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {comboModeofPayment, jLabel35, jLabel36, jLabel37, jLabel39, jLabel41, txtCollectionAmount, txtDateofPayment, txtReceiptNumber, txtTransactionID});

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_16x16.png"))); // NOI18N
        jButton1.setText("Print School Receipt");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Check_16x16.png"))); // NOI18N
        jButton4.setText("Save");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setOpaque(true);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_16x16.png"))); // NOI18N
        jButton5.setText("Print Fee Statement");
        jButton5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout CollectFeeDlgLayout = new javax.swing.GroupLayout(CollectFeeDlg.getContentPane());
        CollectFeeDlg.getContentPane().setLayout(CollectFeeDlgLayout);
        CollectFeeDlgLayout.setHorizontalGroup(
            CollectFeeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        CollectFeeDlgLayout.setVerticalGroup(
            CollectFeeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        UpdateFeeRequiredDlg.setTitle("Set Required Fee");
        UpdateFeeRequiredDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

        comboForm1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select class" }));
        comboForm1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboForm1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        comboAcademic_Year.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboAcademic_YearPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel1.setText("Select Academic Year");

        jLabel3.setText("Select Class");

        tblBalances.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblBalances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADMNO", "NAME", "Required Fee", "Fee Paid"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBalances.setFillsViewportHeight(true);
        tblBalances.setGridColor(new java.awt.Color(204, 204, 204));
        tblBalances.setRowHeight(25);
        tblBalances.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblBalancesKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblBalances);
        if (tblBalances.getColumnModel().getColumnCount() > 0) {
            tblBalances.getColumnModel().getColumn(1).setPreferredWidth(300);
        }

        btnSaveRequirements.setBackground(new java.awt.Color(255, 255, 255));
        btnSaveRequirements.setText("Save");
        btnSaveRequirements.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));
        btnSaveRequirements.setContentAreaFilled(false);
        btnSaveRequirements.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSaveRequirements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveRequirementsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnSaveRequirements, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboAcademic_Year, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboForm1, 0, 235, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboForm1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(comboAcademic_Year))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveRequirements, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout UpdateFeeRequiredDlgLayout = new javax.swing.GroupLayout(UpdateFeeRequiredDlg.getContentPane());
        UpdateFeeRequiredDlg.getContentPane().setLayout(UpdateFeeRequiredDlgLayout);
        UpdateFeeRequiredDlgLayout.setHorizontalGroup(
            UpdateFeeRequiredDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        UpdateFeeRequiredDlgLayout.setVerticalGroup(
            UpdateFeeRequiredDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        progDlg.setType(java.awt.Window.Type.UTILITY);
        progDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                progDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jProgressBar1.setStringPainted(true);

        lblStatus.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(0, 153, 0));
        lblStatus.setText("Running...");

        ProgressNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ProgressNo.setText("0%");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProgressNo, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(ProgressNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout progDlgLayout = new javax.swing.GroupLayout(progDlg.getContentPane());
        progDlg.getContentPane().setLayout(progDlgLayout);
        progDlgLayout.setHorizontalGroup(
            progDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        progDlgLayout.setVerticalGroup(
            progDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fee Manager");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Table_Students.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Adm", "Name", "Class"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_Students.setFillsViewportHeight(true);
        Table_Students.setGridColor(new java.awt.Color(204, 204, 204));
        Table_Students.setRowHeight(25);
        Table_Students.setSelectionBackground(new java.awt.Color(204, 204, 204));
        Table_Students.setSelectionForeground(new java.awt.Color(0, 0, 204));
        Table_Students.setShowVerticalLines(false);
        Table_Students.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_StudentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Students);
        if (Table_Students.getColumnModel().getColumnCount() > 0) {
            Table_Students.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        txtSearch.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(102, 102, 102));
        txtSearch.setText("Search Student");
        txtSearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 0), 1, true));
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

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Select a class"));

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_24x24.png"))); // NOI18N
        jButton6.setText("Print Balance Sheet");
        jButton6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setOpaque(true);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        comboForm.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        comboYear2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(comboYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Refresh_24x24.png"))); // NOI18N
        jButton3.setText("Update fee required");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtSearch)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowDeactivated

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText("");
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        students = StudentDAO.get(txtSearch.getText());
        model = (DefaultTableModel) Table_Students.getModel();
        model.setRowCount(0);
        students.forEach((student) -> {
            model.addRow(new Object[]{student.getRegNumber(), student.getName(), student.getClassroom()});
        });
    }//GEN-LAST:event_txtSearchKeyReleased

    private void Table_StudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_StudentsMouseClicked
        if (Table_Students.getModel().getRowCount() > 0) {
            selectedStudent = students.get(Table_Students.getSelectedRow());

            lblName.setText("Student Name: " + selectedStudent.getName());
            lblAdm.setText("Student Adm: " + selectedStudent.getRegNumber());
            lblClass.setText("Student Class: " + selectedStudent.getClassroom());

            CollectFeeDlg.pack();
            CollectFeeDlg.setLocationRelativeTo(this);
            CollectFeeDlg.setVisible(true);
        }
    }//GEN-LAST:event_Table_StudentsMouseClicked

    private void txtCollectionAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCollectionAmountKeyTyped
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACKSPACE || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
            txtCollectionAmount.getToolTipText();
        }
    }//GEN-LAST:event_txtCollectionAmountKeyTyped

    private void txtReceiptNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReceiptNumberKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReceiptNumberKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println(Student_adm + " " + academic_year);
        String RecNo;
        RecNo = txtReceiptNumber.getText();
        if (Student_adm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student's Admission number is needed for Receipt Printing");
        } else if (RecNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Printing Receipt Number is needed for Receipt Printing");
        } else {
            try {
                reportFile = getClass().getResourceAsStream("reports/fee/StudentPaymentReceipt.jrxml");
                HashMap param = new HashMap();
                param.put("student_id", Student_adm);
                param.put("academic_year", academic_year);
                JasperDesign jd = JRXmlLoader.load(reportFile);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
                JRViewer jv = new JRViewer(jp);

                JFrame jf = new JFrame();
                jf.getContentPane().add(jv);
                jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/Print_16x16.png")));
                jf.setType(Type.UTILITY);
                jf.validate();
                jf.setVisible(true);
                jf.setSize(new Dimension(900, 650));
                jf.setLocationRelativeTo(this);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.setTitle("Student Payment Receipt");
            } catch (HeadlessException | JRException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String ReceiptNumber = txtReceiptNumber.getText();
        String Amount = txtCollectionAmount.getText();
        String mode = comboModeofPayment.getSelectedItem().toString();
        String TransCode = txtTransactionID.getText();
        String DatePaid = ((JTextField) txtDateofPayment.getDateEditor().getUiComponent()).getText();
        academic_year = comboYear.getSelectedItem().toString();
        String Servedby = txtServedBy.getText();

        if (Student_adm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Student is selected");
        } else if (Amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Amount paid not Entered");
        } else if (DatePaid.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the date paid");
        } else if (ReceiptNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the Receipt Number of the paper receipt");
        } else if (Servedby.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter your name as the service person");
        } else {
            int res = JOptionPane.showConfirmDialog(null, "Do you wish to confirm that this payment has been received ?", "acme", JOptionPane.YES_NO_OPTION);
            //<editor-fold defaultstate="collapsed" desc="comment">
            if (res == JOptionPane.YES_OPTION) {
                if (ConnClass.CountRows("SELECT count(*) as total from tbl_fee_register WHERE receipt_no='" + ReceiptNumber + "'") > 0) {
                    JOptionPane.showMessageDialog(null, "This Receipt number has been used already", "Recorded", 1);
                    return;
                }
                try {
                    String AmountinWords = NumberToWordsClass.convert(Integer.parseInt(Amount)) + " only";
                    //Update feeregister
                    sql = "INSERT INTO  tbl_fee_register (adm,code,credit,register_date,mode_of_payment,served_by,amount_in_words,receipt_no,academic_year) values (?,?,?,?,?,?,?,?,?)";
                    pst = Conn.prepareStatement(sql);
                    pst.setString(1, Student_adm);
                    pst.setString(2, TransCode);
                    pst.setString(3, Amount);
                    pst.setString(4, DatePaid);
                    pst.setString(5, mode);
                    pst.setString(6, Servedby);
                    pst.setString(7, AmountinWords);
                    pst.setString(8, ReceiptNumber);
                    pst.setString(9, academic_year);
                    pst.executeUpdate();
                    System.out.println("Fee Register recorded");

                    //Get Register Information
                    sql = "SELECT (SELECT sum(debit+0)) AS required,(SELECT sum(credit+0))as paid FROM tbl_Fee_Register WHERE adm='" + Student_adm + "' AND academic_year='" + academic_year + "'";
                    pst = Conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                    String FeeReq, FeePay, FeeBal;
                    if (rs.next()) {
                        FeeReq = rs.getString("required");
                        FeePay = rs.getString("paid");
                        FeeBal = String.valueOf(Integer.parseInt(FeeReq) - Integer.parseInt(FeePay));
                        try {
                            PreparedStatement pstt = Conn.prepareStatement("Update Student_details set FeeRequired='" + FeeReq + "',FeePaid='" + FeePay + "',FeeBalance='" + FeeBal + "' WHERE Student_id='" + Student_adm + "'");
                            pstt.executeUpdate();
                            System.out.println("Exam System Fee Balance updated succesfully");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Payment recorded and the receipt is ready for printing", "Recorded", 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void comboForm1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboForm1PopupMenuWillBecomeInvisible
        getStudents(comboForm1.getSelectedItem().toString());
    }//GEN-LAST:event_comboForm1PopupMenuWillBecomeInvisible

    private void btnSaveRequirementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveRequirementsActionPerformed
        academic_year = comboAcademic_Year.getSelectedItem().toString();
        lblStatus.setText("Running...");
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int Rows = model.getRowCount();

            @Override
            protected Void doInBackground() throws Exception {
                progDlg.pack();
                progDlg.setLocationRelativeTo(null);
                progDlg.setVisible(true);
                for (int i = 0; i < Rows; i++) {
                    String adm, Name, feereq, feepa;
                    adm = model.getValueAt(i, 0).toString();
                    Name = model.getValueAt(i, 1).toString();
                    feereq = model.getValueAt(i, 2).toString();
                    feepa = model.getValueAt(i, 3).toString();
                    //Update feeregister
                    try {
                        int StudentCount = ConnClass.CountRows("SELECT Count(*) as total from tbl_fee_register WHERE academic_year='" + academic_year + "' AND adm='" + adm + "'");
                        if (StudentCount == 0) {
                            sql = "INSERT INTO tbl_fee_register (adm,code,debit,credit,register_date,academic_year) values (?,?,?,?,?,?)";
                            pst = Conn.prepareStatement(sql);
                            pst.setString(1, adm);
                            pst.setString(2, "N/A");
                            pst.setString(3, feereq);
                            pst.setString(4, feepa);
                            pst.setString(5, today);
                            pst.setString(6, academic_year);
                            pst.executeUpdate();
                            lblStatus.setText("Creating requirements for: " + Name + " Adm: " + adm);
                        } else {
                            sql = "UPDATE tbl_fee_register SET debit=?,credit=?     WHERE adm='" + adm + "' AND academic_year='" + academic_year + "'";
                            pst = Conn.prepareStatement(sql);
                            pst.setString(1, feereq);
                            pst.setString(2, feepa);
                            pst.executeUpdate();
                            lblStatus.setText("Updating requirements for: " + Name + " Adm: " + adm);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    publish(i + 1);
                }
                JOptionPane.showMessageDialog(null, "Fee Requirements updated succesfully", "Success", 1);
                return null;
            }

            @Override
            protected void process(List<Integer> chucks) {
                int progress = chucks.get(chucks.size() - 1);
                ProgressNo.setText((int) ((progress / (Rows * 1.0)) * 100) + "%");
                jProgressBar1.setValue((int) ((progress / (Rows * 1.0)) * 100));
            }

            @Override
            protected void done() {
                progDlg.dispose();
                UpdateFeeRequiredDlg.dispose();
            }
        };
        worker.execute();
    }//GEN-LAST:event_btnSaveRequirementsActionPerformed

    private void tblBalancesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblBalancesKeyReleased

    }//GEN-LAST:event_tblBalancesKeyReleased

    private void comboAcademic_YearPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboAcademic_YearPopupMenuWillBecomeInvisible
        academic_year = comboAcademic_Year.getSelectedItem().toString();
    }//GEN-LAST:event_comboAcademic_YearPopupMenuWillBecomeInvisible

    private void progDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_progDlgWindowGainedFocus

    }//GEN-LAST:event_progDlgWindowGainedFocus

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        UpdateFeeRequiredDlg.pack();
        UpdateFeeRequiredDlg.setLocationRelativeTo(this);
        UpdateFeeRequiredDlg.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        academic_year = comboYear.getSelectedItem().toString();
        if (Student_adm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student's Admission number is needed for Receipt Printing");
        } else if (academic_year == null || academic_year.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the academic year for which you want to print Register");
        } else {
            try {
                reportFile = getClass().getResourceAsStream("reports/fee/FeeRegister.jrxml");
                HashMap param = new HashMap();
                param.put("student_id", Student_adm);
                param.put("academic_year", academic_year);
                JasperDesign jd = JRXmlLoader.load(reportFile);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
                JRViewer jv = new JRViewer(jp);

                JFrame jf = new JFrame();
                jf.getContentPane().add(jv);
                jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/Print_16x16.png")));
                jf.setType(Type.UTILITY);
                jf.validate();
                jf.setVisible(true);
                jf.setSize(new Dimension(900, 650));
                jf.setLocationRelativeTo(this);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.setTitle("Student Fee Payment Register");
            } catch (HeadlessException | JRException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        academic_year = comboYear2.getSelectedItem().toString();
        String form_name = comboForm.getSelectedItem().toString();
        if (form_name.equalsIgnoreCase("Select class")) {
            JOptionPane.showMessageDialog(null, "Select a class to print balances");
        } else if (academic_year == null || academic_year.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter the academic year for which you want to print Register");
        } else {
            try {
                reportFile = getClass().getClassLoader().getResourceAsStream("reports/fee/FeeBalanceReport.jrxml");
                HashMap param = new HashMap();
                param.put("FormName", form_name);
                param.put("academic_year", academic_year);

                JasperDesign jd = JRXmlLoader.load(reportFile);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, param, Conn);
                JRViewer jv = new JRViewer(jp);

                JFrame jf = new JFrame();
                jf.getContentPane().add(jv);
                jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
                jf.setType(Type.UTILITY);
                jf.validate();
                jf.setVisible(true);
                jf.setSize(new Dimension(900, 650));
                jf.setLocationRelativeTo(this);
                jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jf.setTitle("Student Fee Payment Register");
            } catch (HeadlessException | JRException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FeePanelFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FeePanelFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog CollectFeeDlg;
    private javax.swing.JLabel ProgressNo;
    private javax.swing.JTable Table_Students;
    private javax.swing.JDialog UpdateFeeRequiredDlg;
    private javax.swing.JButton btnSaveRequirements;
    private javax.swing.JComboBox<String> comboAcademic_Year;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboForm1;
    private javax.swing.JComboBox<String> comboModeofPayment;
    private javax.swing.JComboBox<String> comboYear;
    private javax.swing.JComboBox<String> comboYear2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdm;
    private javax.swing.JLabel lblClass;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JDialog progDlg;
    private javax.swing.JTable tblBalances;
    private javax.swing.JTextField txtCollectionAmount;
    private com.toedter.calendar.JDateChooser txtDateofPayment;
    private javax.swing.JTextField txtReceiptNumber;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtServedBy;
    private javax.swing.JTextField txtTransactionID;
    // End of variables declaration//GEN-END:variables
}
