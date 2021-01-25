package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AdminPanelFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String system_level = null;
    boolean activated = false;

    public AdminPanelFrm() {
        initComponents();
        
        new ConnClass().setFrameIcon(this);
        updateRunningDate();
        getNumberOfPersons();
        getSchool();
    }


    private void updateRunningDate() {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 1000000; i++) {
                    Thread.sleep(1000);
                    Calendar cal = new GregorianCalendar();
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH)+1;
                    int year = cal.get(Calendar.YEAR);
                    MenuDate.setText(day + "/" + month + "/" + year);

                    int secs = cal.get(Calendar.SECOND);
                    int mins = cal.get(Calendar.MINUTE);
                    int hours = cal.get(Calendar.HOUR_OF_DAY);
                    MenuTime.setText(hours + ":" + mins + ":" + secs);
                }
                return null;
            }

            protected void process(java.util.List<Integer> chucks) {
            }

            protected void done() {
            }
        };
        worker.execute();
    }

    private void getSchool() {
        try {
            String sql = "SELECT * FROM tblschool";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                this.setTitle(rs.getString("School_name") + " Examination Manager");
                txtSchoolName.setText(rs.getString("School_name"));
                txtClDate.setText(rs.getString("ClosingDate"));
                txtOpDate.setText(rs.getString("OpeningDate"));
                String cd = rs.getString("ClosingDate");
                String op = rs.getString("OpeningDate");
                txtCD.setText(cd);
                txtOP.setText(op);
                if (rs.getString("Full_purchase").equals("3")) {
                    activated = true;
                }
            } else {
                this.setTitle("School Examination Manager");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void getNumberOfPersons() {
        try {
            String sql = "SELECT (SELECT count(*) FROM tblteachers) AS Teachers,(SELECT count(*) FROM tblclasses) AS Classes,(SELECT count(*) FROM Subjects) AS Subjects,(SELECT count(*) FROM student_details) AS Students,(SELECT count(*) FROM user) AS Users";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNumberOfTeachers.setText(rs.getString("Teachers"));
                txtNumberOfClasses.setText(rs.getString("Classes"));
                txtNumberOfSubjects.setText(rs.getString("Subjects"));
                txtNumberofStudents.setText(rs.getString("Students"));
                txtnumberOfusers.setText(rs.getString("Users"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void closeConnection(){
        try {
            Conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogDates = new javax.swing.JDialog();
        jButton20 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtOP = new javax.swing.JTextField();
        txtCD = new javax.swing.JTextField();
        AboutDlg = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTerm1 = new javax.swing.JLabel();
        txtNumberOfTeachers = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtTerm3 = new javax.swing.JLabel();
        txtNumberOfSubjects = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        txtTerm5 = new javax.swing.JLabel();
        txtnumberOfusers = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        txtTerm2 = new javax.swing.JLabel();
        txtNumberOfClasses = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txtTerm4 = new javax.swing.JLabel();
        txtNumberofStudents = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton9 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton18 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        txtSchoolName = new javax.swing.JLabel();
        txtTerm = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtClDate = new javax.swing.JLabel();
        txtOpDate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        MenuDate = new javax.swing.JMenu();
        MenuTime = new javax.swing.JMenu();

        DialogDates.setModal(true);
        DialogDates.setName(""); // NOI18N
        DialogDates.setResizable(false);
        DialogDates.setType(java.awt.Window.Type.UTILITY);

        jButton20.setBackground(new java.awt.Color(60, 45, 115));
        jButton20.setForeground(new java.awt.Color(254, 254, 254));
        jButton20.setText("Save Dates");
        jButton20.setContentAreaFilled(false);
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Closing Date:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Next Term Opens:");

        javax.swing.GroupLayout DialogDatesLayout = new javax.swing.GroupLayout(DialogDates.getContentPane());
        DialogDates.getContentPane().setLayout(DialogDatesLayout);
        DialogDatesLayout.setHorizontalGroup(
            DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogDatesLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOP)
                    .addComponent(txtCD, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        DialogDatesLayout.setVerticalGroup(
            DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogDatesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogDatesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        AboutDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        AboutDlg.setModal(true);
        AboutDlg.setResizable(false);
        AboutDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(0, 204, 51));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("About");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel11))
        );

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Phone:    +254797969142\nEmail:     woramaslysofts@gmail.com\nWebsite: www.lysofts.co.ke");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacts"));
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
        jTextArea1.setOpaque(false);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Cambria", 0, 13)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Acme Examination Management System is Developed and Managed by lySOFTS. It is an Upgrade and Stable version of the RD Examination Management System. \nBoth EMSs are developed to suit the most possible Examination Systems in Kenyan Secondary Schools. ");
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setBorder(javax.swing.BorderFactory.createTitledBorder("Review"));
        jTextArea2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea2.setEnabled(false);
        jTextArea2.setOpaque(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AboutDlgLayout = new javax.swing.GroupLayout(AboutDlg.getContentPane());
        AboutDlg.getContentPane().setLayout(AboutDlgLayout);
        AboutDlgLayout.setHorizontalGroup(
            AboutDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        AboutDlgLayout.setVerticalGroup(
            AboutDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        jPanel3.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm1.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm1.setForeground(new java.awt.Color(0, 153, 0));
        txtTerm1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm1.setText("Teachers");

        txtNumberOfTeachers.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtNumberOfTeachers.setForeground(new java.awt.Color(0, 153, 0));
        txtNumberOfTeachers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberOfTeachers.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTerm1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNumberOfTeachers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumberOfTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm1)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 10, -1, 110));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 204)));
        jPanel4.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm3.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm3.setForeground(new java.awt.Color(0, 102, 204));
        txtTerm3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm3.setText("Subjects");

        txtNumberOfSubjects.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtNumberOfSubjects.setForeground(new java.awt.Color(0, 102, 204));
        txtNumberOfSubjects.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberOfSubjects.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTerm3, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNumberOfSubjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumberOfSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm3)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, 110));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm5.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm5.setText("Users");

        txtnumberOfusers.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtnumberOfusers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtnumberOfusers.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTerm5, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtnumberOfusers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtnumberOfusers, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm5)
                .addContainerGap())
        );

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 10, -1, 110));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jPanel14.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm2.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm2.setForeground(new java.awt.Color(153, 0, 0));
        txtTerm2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm2.setText("Classes");

        txtNumberOfClasses.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtNumberOfClasses.setForeground(new java.awt.Color(153, 0, 0));
        txtNumberOfClasses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberOfClasses.setText("0");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTerm2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNumberOfClasses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumberOfClasses, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm2)
                .addContainerGap())
        );

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(166, 10, -1, 110));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));
        jPanel15.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm4.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm4.setForeground(new java.awt.Color(0, 153, 153));
        txtTerm4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm4.setText("Students");

        txtNumberofStudents.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtNumberofStudents.setForeground(new java.awt.Color(0, 153, 153));
        txtNumberofStudents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNumberofStudents.setText("0");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTerm4, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNumberofStudents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumberofStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTerm4)
                .addContainerGap())
        );

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 10, -1, 110));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 790, 130));

        jToolBar1.setBackground(new java.awt.Color(204, 255, 204));
        jToolBar1.setRollover(true);

        jButton4.setIcon(new javax.swing.ImageIcon(
            getClass().getClassLoader().getResource("images/Categories-applications-education-school-icon.png")
        ));
        jButton4.setText("School");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setPreferredSize(new java.awt.Dimension(55, 40));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton17.setIcon(new javax.swing.ImageIcon(
            getClass().getClassLoader().getResource("images/Categories-applications-education-science-icon.png"))
    );
    jButton17.setText("Subjects");
    jButton17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton17.setFocusable(false);
    jButton17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton17.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton17.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton17.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton17ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton17);
    jToolBar1.add(jSeparator4);

    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Science-Classroom-icon.png"))
    );
    jButton3.setText("Teachers");
    jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton3.setFocusable(false);
    jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton3.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton3);

    jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Science-Classroom-icon.png"))); // NOI18N
    jButton5.setText("Classes");
    jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton5.setFocusable(false);
    jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton5.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton5);

    jButton7.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Log Out_24x24.png"))); // NOI18N
    jButton7.setText("Houses");
    jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton7.setFocusable(false);
    jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton7.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton7.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton7ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton7);
    jToolBar1.add(jSeparator3);

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Teacher-icon.png"))); // NOI18N
    jButton2.setText("Students");
    jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton2.setFocusable(false);
    jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton2.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton2);

    jButton8.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Properties_24x24.png"))); // NOI18N
    jButton8.setText("Subject Alloc.");
    jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton8.setFocusable(false);
    jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton8.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton8ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton8);
    jToolBar1.add(jSeparator1);

    jButton9.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Edit_24x24.png"))); // NOI18N
    jButton9.setText("Record");
    jButton9.setToolTipText("Record and update exams");
    jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton9.setFocusable(false);
    jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton9.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton9.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton9ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton9);

    jButton16.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Synchronize_24x24.png"))); // NOI18N
    jButton16.setText("Analysis");
    jButton16.setToolTipText("Record and update exams");
    jButton16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton16.setFocusable(false);
    jButton16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton16.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton16.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton16.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton16ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton16);
    jToolBar1.add(jSeparator2);

    jButton18.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/sales-report-icon.png"))); // NOI18N
    jButton18.setText("Fee Manager");
    jButton18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton18.setFocusable(false);
    jButton18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton18.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton18.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton18.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton18ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton18);

    jButton13.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Report Card_24px.png"))); // NOI18N
    jButton13.setText("Reports");
    jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton13.setFocusable(false);
    jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton13.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton13.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton13ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton13);

    getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 70));

    txtSchoolName.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
    txtSchoolName.setForeground(new java.awt.Color(0, 153, 0));
    txtSchoolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    txtSchoolName.setText("School Name");
    getContentPane().add(txtSchoolName, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 820, 40));

    txtTerm.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
    txtTerm.setForeground(new java.awt.Color(255, 0, 0));
    txtTerm.setText("Term Dates:");
    getContentPane().add(txtTerm, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 110, -1));

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText("Next Term Opens:");
    getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 100, -1));

    txtClDate.setText("Date");
    getContentPane().add(txtClDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 140, -1));

    txtOpDate.setText("Date");
    getContentPane().add(txtOpDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 140, -1));

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel4.setText("Closing Date:");
    getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 100, -1));

    jPanel2.setBackground(new java.awt.Color(0, 0, 102));
    jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 3, Short.MAX_VALUE)
    );

    getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 830, 5));

    jPanel9.setBackground(new java.awt.Color(0, 204, 51));
    jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Tools"));

    jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton15.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/About_24px.png"))); // NOI18N
    jButton15.setText("About");
    jButton15.setToolTipText("Help information on the product");
    jButton15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton15.setFocusable(false);
    jButton15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton15.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton15.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton15.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton15ActionPerformed(evt);
        }
    });

    jButton14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton14.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/User_24x24.png"))); // NOI18N
    jButton14.setText("Users");
    jButton14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton14.setFocusable(false);
    jButton14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton14.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton14.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton14.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton14ActionPerformed(evt);
        }
    });

    jButton27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton27.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Download_24x24.png"))); // NOI18N
    jButton27.setText("Back up");
    jButton27.setToolTipText("Help information on the product");
    jButton27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton27.setFocusable(false);
    jButton27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton27.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton27.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton27.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton27ActionPerformed(evt);
        }
    });

    jButton19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton19.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Training_24px.png"))); // NOI18N
    jButton19.setText("KCSE");
    jButton19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jButton19.setFocusable(false);
    jButton19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton19.setPreferredSize(new java.awt.Dimension(55, 40));
    jButton19.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton19.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton19ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
            .addContainerGap())
    );

    getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 420, 80));

    jMenu1.setText("Term Dates");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Settings_16x16.png"))); // NOI18N
    jMenuItem1.setText("Set Term Dates");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu5.setText("Themes");

    jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
    jMenuItem2.setText("Default (acme)");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem2);

    jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_DOWN_MASK));
    jMenuItem5.setText("Acryl (acme - Dark)");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem5ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem5);

    jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
    jMenuItem3.setText("Aero (acme)");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem3ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem3);

    jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_DOWN_MASK));
    jMenuItem4.setText("Mac (acme)");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem4ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem4);

    jMenuBar1.add(jMenu5);

    MenuDate.setText("Date");
    jMenuBar1.add(MenuDate);

    MenuTime.setText("Time");
    jMenuBar1.add(MenuTime);

    setJMenuBar(jMenuBar1);

    setSize(new java.awt.Dimension(917, 566));
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        closeConnection();
        this.dispose();
        new UsersFrm().setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        closeConnection();
        this.dispose();
        new MarksRecordFrm().setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        closeConnection();
        this.dispose();
        new MarksAnalysisFrm().setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        closeConnection();
        this.dispose();
        new TeachersFrm().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        closeConnection();
        this.dispose();
        new SchoolFrm().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        closeConnection();
        this.dispose();
        new ClassesFrm().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        closeConnection();
        this.dispose();
        new StudentRegistrationFrm().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        closeConnection();
        this.dispose();
        new SubjectsFrm().setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DialogDates.pack();
        DialogDates.setLocationRelativeTo(this);
        DialogDates.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        closeConnection();
        this.dispose();
        new ReportsFrm().setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        closeConnection();
        this.dispose();
        new HousesFrm().setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            DecorationHelper.decorateWindows(false);
            for (Frame f : Frame.getFrames()) {
                SwingUtilities.updateComponentTreeUI(f);
            }
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AdminPanelFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            DecorationHelper.decorateWindows(false);
            for (Frame f : Frame.getFrames()) {
                SwingUtilities.updateComponentTreeUI(f);
            }
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AdminPanelFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
            DecorationHelper.decorateWindows(false);
            for (Frame f : Frame.getFrames()) {
                SwingUtilities.updateComponentTreeUI(f);
            }
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AdminPanelFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
            DecorationHelper.decorateWindows(false);
            for (Frame f : Frame.getFrames()) {
                SwingUtilities.updateComponentTreeUI(f);
            }
        } catch (UnsupportedLookAndFeelException e) {
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AdminPanelFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        String OpeningDate = txtOP.getText();
        String ClosingDate = txtCD.getText();
        try {
            String sql = "UPDATE tblschool SET ClosingDate=?, OPeningDate=?";
            pst = Conn.prepareStatement(sql);
            pst.setString(1, ClosingDate);
            pst.setString(2, OpeningDate);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Term dates saved", "acme", JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        closeConnection();
        this.dispose();
        new BackUpFrm().setVisible(true);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        AboutDlg.pack();
        AboutDlg.setLocationRelativeTo(null);
        AboutDlg.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the system ?","Close",0);
        if(res==0){
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        closeConnection();
        this.dispose();
        new SubjectAllocationChoiceFrm().setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
//        closeConnection();
//        this.dispose();
//        new FeePanelFrm().setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed
    
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        JOptionPane.showMessageDialog(this,"KCSE Analysis module is yet to be released.\n \t We will update your system once the release is ready","Acme",1);
        closeConnection();
        this.dispose();
        new KCSE_AnalysisFrm().setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanelFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DecorationHelper.decorateWindows(false);
            AdminPanelFrm frm = new AdminPanelFrm();
            frm.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AboutDlg;
    private javax.swing.JDialog DialogDates;
    private javax.swing.JMenu MenuDate;
    private javax.swing.JMenu MenuTime;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txtCD;
    private javax.swing.JLabel txtClDate;
    private javax.swing.JLabel txtNumberOfClasses;
    private javax.swing.JLabel txtNumberOfSubjects;
    private javax.swing.JLabel txtNumberOfTeachers;
    private javax.swing.JLabel txtNumberofStudents;
    private javax.swing.JTextField txtOP;
    private javax.swing.JLabel txtOpDate;
    private javax.swing.JLabel txtSchoolName;
    private javax.swing.JLabel txtTerm;
    private javax.swing.JLabel txtTerm1;
    private javax.swing.JLabel txtTerm2;
    private javax.swing.JLabel txtTerm3;
    private javax.swing.JLabel txtTerm4;
    private javax.swing.JLabel txtTerm5;
    private javax.swing.JLabel txtnumberOfusers;
    // End of variables declaration//GEN-END:variables
}
