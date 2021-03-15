package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.HouseDAO;
import com.lysofts.dao.SchoolDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.SubjectDAO;
import com.lysofts.dao.TeacherDAO;
import com.lysofts.entities.School;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AdminPanelFrm extends javax.swing.JFrame {

    School school = null;

    public AdminPanelFrm() {
        initComponents();
        ConnClass.setFrameIcon(this);

    }

    private void updateUI() {
        Thread t = new Thread(() -> {
            school = SchoolDAO.get();
            if (school != null) {
                AdminPanelFrm.this.setTitle(school.getName() + " Examination Manager");
                txtSchoolName.setText(school.getName());
                txtClDate.setText(school.getClosingDate());
                txtOpDate.setText(school.getOpeningDate());
                txtCD.setText(school.getClosingDate());
                txtOP.setText(school.getOpeningDate());

                txtNumberOfTeachers.setText(String.valueOf(TeacherDAO.get().size()));
                txtNumberOfClasses.setText(String.valueOf(ClassroomDAO.get().size()));
                txtNumberOfSubjects.setText(String.valueOf(SubjectDAO.get().size()));
                txtNumberofStudents.setText(String.valueOf(StudentDAO.get().size()));
                txtnumberOfHouses.setText(String.valueOf(HouseDAO.get().size()));
            } else {
                AdminPanelFrm.this.setTitle("School Examination Manager");
            }
        });
        t.start();
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
        jText5 = new javax.swing.JLabel();
        txtnumberOfHouses = new javax.swing.JLabel();
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
        jButton13 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        txtSchoolName = new javax.swing.JLabel();
        txtTerm = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtClDate = new javax.swing.JLabel();
        txtOpDate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton15 = new javax.swing.JButton();
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

        DialogDates.setModal(true);
        DialogDates.setName(""); // NOI18N
        DialogDates.setResizable(false);
        DialogDates.setType(java.awt.Window.Type.UTILITY);

        jButton20.setText("Save Dates");
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
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        jText5.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        jText5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jText5.setText("Houses");

        txtnumberOfHouses.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
        txtnumberOfHouses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtnumberOfHouses.setText("0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jText5, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtnumberOfHouses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtnumberOfHouses, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jText5)
                .addContainerGap())
        );

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 10, -1, 110));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jPanel14.setForeground(new java.awt.Color(0, 153, 0));

        txtTerm2.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
        txtTerm2.setForeground(new java.awt.Color(153, 0, 0));
        txtTerm2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTerm2.setText("Classrooms");

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

        jToolBar1.setBackground(new java.awt.Color(204, 255, 204));
        jToolBar1.setRollover(true);

        jButton4.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
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

        jButton17.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
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

    jButton3.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
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

    jButton5.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Science-Classroom-icon.png"))
    );
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

    jButton7.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton7.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Log Out_24x24.png"))
    );
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

    jButton2.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Teacher-icon.png"))
    );
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

    jButton8.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton8.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Properties_24x24.png"))
    );
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

    jButton9.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton9.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Edit_24x24.png"))
    );
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

    jButton16.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton16.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Synchronize_24x24.png"))
    );
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

    jButton13.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton13.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Report Card_24px.png"))
    );
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

    jButton18.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
    jButton18.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/sales-report-icon.png"))
    );
    jButton18.setText("Fee");
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

    txtSchoolName.setFont(new java.awt.Font("Script MT Bold", 1, 36)); // NOI18N
    txtSchoolName.setForeground(new java.awt.Color(0, 153, 0));
    txtSchoolName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    txtSchoolName.setText("School Name");

    txtTerm.setFont(new java.awt.Font("Cambria", 3, 18)); // NOI18N
    txtTerm.setForeground(new java.awt.Color(255, 0, 0));
    txtTerm.setText("Term Dates:");

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel1.setText("Next Term Opens:");

    txtClDate.setText("Date");

    txtOpDate.setText("Date");

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    jLabel4.setText("Closing Date:");

    jPanel2.setBackground(new java.awt.Color(0, 0, 102));
    jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 828, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 3, Short.MAX_VALUE)
    );

    jPanel9.setBackground(new java.awt.Color(0, 204, 51));
    jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Tools"));

    jButton15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton15.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/About_24px.png"))
    );
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

    jButton27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton27.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Download_24x24.png"))
    );
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
    jButton19.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Properties_24x24.png"))

    );
    jButton19.setText("Users");
    jButton19.setToolTipText("Help information on the product");
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
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addComponent(jButton27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
            .addContainerGap())
    );

    jMenu1.setText("Term Dates");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
    jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Settings_16x16.png"))
    );
    jMenuItem1.setText("Set Term Dates");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu5.setText("Themes");

    jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
    jMenuItem2.setText("Default (acme)");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem2ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem2);

    jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
    jMenuItem5.setText("Acryl (acme - Dark)");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem5ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem5);

    jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
    jMenuItem3.setText("Aero (acme)");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem3ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem3);

    jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
    jMenuItem4.setText("Mac (acme)");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem4ActionPerformed(evt);
        }
    });
    jMenu5.add(jMenuItem4);

    jMenuBar1.add(jMenu5);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGroup(layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addComponent(txtSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(30, 30, 30)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(340, 340, 340)
            .addComponent(txtTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(220, 220, 220)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(txtClDate, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(200, 200, 200)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(txtOpDate, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(60, 60, 60)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 790, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGroup(layout.createSequentialGroup()
            .addGap(40, 40, 40)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10)
            .addComponent(txtSchoolName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(5, 5, 5)
            .addComponent(txtTerm)
            .addGap(8, 8, 8)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4)
                .addComponent(txtClDate))
            .addGap(5, 5, 5)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(txtOpDate))
            .addGap(5, 5, 5)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(70, 70, 70)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    setSize(new java.awt.Dimension(940, 566));
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        this.dispose();
        new MarksRecordFrm().setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        this.dispose();
        new MarksAnalysisFrm().setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
        new TeachersFrm().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
        new SchoolFrm().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
        new ClassesFrm().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        new StudentsFrm().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        this.dispose();
        new SubjectsFrm().setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        DialogDates.pack();
        DialogDates.setLocationRelativeTo(this);
        DialogDates.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        this.dispose();
        new ReportsFrm().setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
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
        if (school != null) {
            school.setClosingDate(ClosingDate);
            school.setOpeningDate(OpeningDate);
            boolean success = new SchoolDAO().update(school);
            if (success) {
                JOptionPane.showMessageDialog(null, "Term dates saved", "acme", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        this.dispose();
        new BackUpFrm().setVisible(true);
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        AboutDlg.pack();
        AboutDlg.setLocationRelativeTo(null);
        AboutDlg.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the system ?", "Close", 0);
        if (res == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        this.dispose();
        new SubjectAllocationChoiceFrm().setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        this.dispose();
        new FeePanelFrm().setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        this.dispose();
        new UsersFrm().setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateUI();
    }//GEN-LAST:event_formWindowOpened

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
    private javax.swing.JButton jButton13;
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
    private javax.swing.JLabel jText5;
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
    private javax.swing.JLabel txtnumberOfHouses;
    // End of variables declaration//GEN-END:variables
}
