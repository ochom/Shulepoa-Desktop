package com.lysofts;

import com.lysofts.utils.ConnClass;
import com.jtattoo.plaf.DecorationHelper;
import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.HouseDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.entities.Classroom;
import com.lysofts.entities.Student;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class StudentsFrm extends javax.swing.JFrame {

    String filepath = null;
    private List<Student> students = new ArrayList<>();
    private List<Student> studentsToPromote;
    private Student selectedStudent = null;

    private List<Classroom> classrooms = new ArrayList<>();
    private Classroom selectedClassroom = null;

    public StudentsFrm() {
        this.classrooms = ClassroomDAO.get();

        initComponents();
        new ConnClass().setFrameIcon(StudentsFrm.this);
    }

    private void updateMainUI() {
        Thread thread = new Thread(() -> {
            comboHouse.removeAllItems();
            comboHouse.addItem("Select");
            HouseDAO.get().forEach(house -> {
                comboHouse.addItem(house.getName());
            });

            DefaultTableModel model = (DefaultTableModel) tableClassrooms.getModel();
            model.setRowCount(0);
            txtClass.removeAllItems();
            comboClassFromPromotion.removeAllItems();
            comboClassToPromotion.removeAllItems();

            txtClass.addItem("Select");
            comboClassFromPromotion.addItem("Select");
            comboClassToPromotion.addItem("Select");
            classrooms.forEach(classroom -> {
                model.addRow(new Object[]{classroom.getName()});
                txtClass.addItem(classroom.getName());
                comboClassFromPromotion.addItem(classroom.getName());
                comboClassToPromotion.addItem(classroom.getName());
            });
            comboClassFromPromotion.addItem("Completed");
            comboClassToPromotion.addItem("Completed");

            DefaultTableModel model2 = (DefaultTableModel) tableStudents.getModel();
            model2.setRowCount(0);
            students.forEach(student -> {
                model2.addRow(new Object[]{student.getRegNumber(), student.getName(), student.getClassroom()});
            });
        });
        thread.start();
    }

    private void updateStudentUI() {
        Thread thread = new Thread(() -> {
            if (selectedStudent == null) {
                txtClass.setSelectedItem(selectedClassroom.getName());
                txtName.setText("");
                txtADMNO.setText("");
                txtKCPE.setText("");
                txtKcpeGrade.setText("");
                txtDOA.setDate(null);
                txtDOB.setDate(null);
                txtFather.setText("");
                txtPhone1.setText("");
                cmbGender.setSelectedIndex(0);
                comboHouse.setSelectedIndex(0);
                filepath = "";
                profilePic.setIcon(null);
            } else {
                txtClass.setSelectedItem(selectedStudent.getClassroom());
                txtName.setText(selectedStudent.getName());
                txtADMNO.setText(selectedStudent.getRegNumber());
                txtKCPE.setText(selectedStudent.getKcpeMarks());
                txtKcpeGrade.setText(selectedStudent.getKcpeGrade());
                ((JTextField) txtDOA.getDateEditor().getUiComponent()).setText(selectedStudent.getDoa());
                ((JTextField) txtDOB.getDateEditor().getUiComponent()).setText(selectedStudent.getDob());
                cmbGender.setSelectedItem(selectedStudent.getSex());
                comboHouse.setSelectedItem(selectedStudent.getHouse());
                txtFather.setText(selectedStudent.getKinName());
                txtPhone1.setText(selectedStudent.getKinPhone());
                filepath = selectedStudent.getPassport();
                getProfilePic();
            }
        });
        thread.start();
    }

    private void AddStudent() {
        if (txtADMNO.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adm No is neccesary data required", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student Name is neccesary data required", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Student student;
            if (selectedStudent == null) {
                student = new Student();
                student.setName(txtName.getText());
                student.setRegNumber(txtADMNO.getText());
                student.setSex(cmbGender.getSelectedItem().toString());
                student.setClassroom(txtClass.getSelectedItem().toString());
                student.setDoa(((JTextField) txtDOA.getDateEditor().getUiComponent()).getText());
                student.setDob(((JTextField) txtDOB.getDateEditor().getUiComponent()).getText());
                student.setKcpeMarks(txtKCPE.getText());
                student.setKcpeGrade(txtKcpeGrade.getText());
                student.setPassport(filepath);
                student.setHouse(comboHouse.getSelectedIndex() > 0 ? comboHouse.getSelectedItem().toString() : "");
                student.setKinName(txtFather.getText());
                student.setKinPhone(txtPhone1.getText());
                if (StudentDAO.add(student)) {
                    JOptionPane.showMessageDialog(null, "Student details succesfully saved", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while saving the data", "Error", 0);
                }
            } else {
                student = selectedStudent;
                student.setName(txtName.getText());
                student.setRegNumber(txtADMNO.getText());
                student.setSex(cmbGender.getSelectedItem().toString());
                student.setClassroom(txtClass.getSelectedItem().toString());
                student.setDoa(((JTextField) txtDOA.getDateEditor().getUiComponent()).getText());
                student.setDob(((JTextField) txtDOB.getDateEditor().getUiComponent()).getText());
                student.setKcpeMarks(txtKCPE.getText());
                student.setKcpeGrade(txtKcpeGrade.getText());
                student.setPassport(filepath);
                student.setHouse(comboHouse.getSelectedIndex() > 0 ? comboHouse.getSelectedItem().toString() : "");
                student.setKinName(txtFather.getText());
                student.setKinPhone(txtPhone1.getText());
                if (StudentDAO.update(student)) {
                    JOptionPane.showMessageDialog(null, "Student details succesfully updated", "Succes", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while updating the data", "Error", 0);
                }
            }
        }
    }

    private void GradeKCPE() {
        Map<String, int[]> gradeMap = new HashMap<>();
        gradeMap.put("A", new int[]{400, 500});
        gradeMap.put("A-", new int[]{375, 400});
        gradeMap.put("B+", new int[]{350, 375});
        gradeMap.put("B", new int[]{325, 350});
        gradeMap.put("B-", new int[]{300, 325});
        gradeMap.put("C+", new int[]{275, 300});
        gradeMap.put("C", new int[]{250, 275});
        gradeMap.put("C-", new int[]{225, 250});
        gradeMap.put("D+", new int[]{200, 225});
        gradeMap.put("D", new int[]{175, 200});
        gradeMap.put("D-", new int[]{150, 175});
        gradeMap.put("E", new int[]{0, 150});
        if (!txtKCPE.getText().isEmpty()) {
            try {
                int marks = Integer.parseInt(txtKCPE.getText());
                for (Map.Entry<String, int[]> entry : gradeMap.entrySet()) {
                    if (marks >= entry.getValue()[0] && marks < entry.getValue()[1]) {
                        txtKcpeGrade.setText(entry.getKey());
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }
    }

    private void getProfilePic() {
        Thread thread = new Thread(() -> {
            BufferedImage bi;
            if (new File(filepath).exists()) {
                try {
                    bi = ImageIO.read(new File(filepath));
                    Image dimg = bi.getScaledInstance(110, 100, Image.SCALE_SMOOTH);
                    ImageIcon img = new ImageIcon(dimg);
                    profilePic.setIcon(img);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });
        thread.start();
    }

    private void getClassPromotingFrom(boolean select) {
        DefaultTableModel model = (DefaultTableModel) promotionTable.getModel();
        model.setRowCount(0);
        String className = comboClassFromPromotion.getSelectedIndex() > 0 ? comboClassFromPromotion.getSelectedItem().toString() : "";
        studentsToPromote = StudentDAO.getInClass(className);
        studentsToPromote.forEach(student -> {
            model.addRow(new Object[]{select, student.getRegNumber(), student.getName(), student.getClassroom()});
        });
    }

    private void promoteStudents() {
        String fromClassroom = (String) comboClassFromPromotion.getSelectedItem();
        String toClassroom = (String) comboClassToPromotion.getSelectedItem();
        if (comboClassFromPromotion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a classroom to move students from", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (comboClassToPromotion.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Select a classroom to move students to", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else if (comboClassFromPromotion.getSelectedIndex() == comboClassToPromotion.getSelectedIndex()) {
            JOptionPane.showMessageDialog(null, "You cannot move students within a classroom. Try other options", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int res = JOptionPane.showConfirmDialog(null, String.format("Move selected students from Form %s to Form %s", fromClassroom, toClassroom), "Acme", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                int rows = promotionTable.getRowCount();
                Thread t;
                t = new Thread() {
                    @Override
                    public void run() {
                        int barTotal = 0;
                        int selected = 0;
                        for (int i = 0; i < rows; i++) {
                            if ((boolean) promotionTable.getValueAt(i, 0)) {
                                barTotal++;
                            }
                        }
                        for (int i = 0; i < rows; i++) {
                            if ((boolean) promotionTable.getValueAt(i, 0)) {
                                Student student = studentsToPromote.get(selected);
                                student.setClassroom(toClassroom);
                                StudentDAO.update(student);
                                selected++;
                                final int tt = barTotal;
                                final int ss = selected;
                                SwingUtilities.invokeLater(() -> {
                                    jLabel1.setText(String.format("Promoting students: %d/%d", ss, tt));
                                    jProgressBar1.setValue((int) ((ss*1.0 / tt) * 100));
                                    jProgressBar1.update(jProgressBar1.getGraphics());
                                });
                            }
                        }
                        selectAll.setSelected(false);
                        getClassPromotingFrom(false);
                        promoting.setVisible(false);
                        JOptionPane.showMessageDialog(null, "All selected students have been promoted to the new class");
                    }
                };

                jProgressBar1.setValue(0);
                promoting.pack();
                promoting.setLocationRelativeTo(this);
                promoting.setVisible(true);
                t.start();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StudentDetailsDialog = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
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
        jLabel18 = new javax.swing.JLabel();
        txtKcpeGrade = new javax.swing.JTextField();
        comboHouse = new javax.swing.JComboBox<>();
        cmbGender = new javax.swing.JComboBox<>();
        txtDOA = new com.toedter.calendar.JDateChooser();
        txtDOB = new com.toedter.calendar.JDateChooser();
        txtClass = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        profilePic = new javax.swing.JLabel();
        btnUploadProfilePicture = new javax.swing.JButton();
        PromotionDlg = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        comboClassFromPromotion = new javax.swing.JComboBox<>();
        comboClassToPromotion = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        promotionTable = new javax.swing.JTable();
        selectAll = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
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
        promoting = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        processing = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStudents = new javax.swing.JTable();
        btnOpenStudentsDialog = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableClassrooms = new javax.swing.JTable();
        btnOpenStudentsDialog1 = new javax.swing.JButton();

        StudentDetailsDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        StudentDetailsDialog.setTitle("Student Registration");
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
        StudentDetailsDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                StudentDetailsDialogWindowClosing(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Parent"));
        jPanel12.setPreferredSize(new java.awt.Dimension(175, 184));

        txtFather.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Kin's Name");

        txtPhone1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Phone");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFather)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel8, jLabel9, txtFather, txtPhone1});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFather, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel8, jLabel9, txtFather, txtPhone1});

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Delete_16x16.png"))
        );
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Save_16x16.png"))
        );
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))
        );
        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 180));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Full name");

        txtName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtADMNO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtADMNO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtADMNOActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Admission Number");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Date of Admission");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Date of Birth");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("KCPE Marks");

        txtKCPE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtKCPE.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtKCPE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKCPEKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Sex");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Dormitory");

        txtKcpeGrade.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtKcpeGrade.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        comboHouse.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        comboHouse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));

        cmbGender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Male", "Female" }));

        txtDOA.setBackground(new java.awt.Color(102, 255, 204));
        txtDOA.setDateFormatString("d MMMM, yyyy");
        txtDOA.setMaxSelectableDate(new java.util.Date(253370757666000L));
        txtDOA.setMinSelectableDate(new java.util.Date(-62135776734000L));

        txtDOB.setBackground(new java.awt.Color(102, 255, 102));
        txtDOB.setDateFormatString("d MMMM, yyyy");

        txtClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("KCPE Grade");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Classroom");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtKCPE, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtKcpeGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDOA, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClass, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboHouse, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtADMNO, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKCPE, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKcpeGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtDOA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cmbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel23)
                .addGap(6, 6, 6)
                .addComponent(txtClass, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel18)
                .addGap(6, 6, 6)
                .addComponent(comboHouse, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbGender, comboHouse, txtADMNO, txtClass, txtDOA, txtDOB, txtKCPE, txtKcpeGrade, txtName});

        jButton5.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Properties_24x24.png"))
        );
        jButton5.setText("Leaving Certificate");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        profilePic.setBackground(new java.awt.Color(255, 255, 255));
        profilePic.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(255, 255, 0), null, null));

        btnUploadProfilePicture.setBackground(new java.awt.Color(0, 255, 0));
        btnUploadProfilePicture.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnUploadProfilePicture.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/User_24x24.png"))
        );
        btnUploadProfilePicture.setText("Upload");
        btnUploadProfilePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadProfilePictureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUploadProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(profilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUploadProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout StudentDetailsDialogLayout = new javax.swing.GroupLayout(StudentDetailsDialog.getContentPane());
        StudentDetailsDialog.getContentPane().setLayout(StudentDetailsDialogLayout);
        StudentDetailsDialogLayout.setHorizontalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StudentDetailsDialogLayout.setVerticalGroup(
            StudentDetailsDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        PromotionDlg.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        PromotionDlg.setTitle("Promote Students");
        PromotionDlg.setModal(true);
        PromotionDlg.setResizable(false);
        PromotionDlg.setType(java.awt.Window.Type.UTILITY);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("From");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 204));
        jLabel19.setText("To");

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class Promoting From", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 12), new java.awt.Color(102, 0, 102))); // NOI18N
        jPanel13.setOpaque(false);

        promotionTable.setModel(new javax.swing.table.DefaultTableModel(
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
        promotionTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(promotionTable);
        if (promotionTable.getColumnModel().getColumnCount() > 0) {
            promotionTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        selectAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        selectAll.setForeground(new java.awt.Color(0, 204, 0));
        selectAll.setText("Select All");
        selectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Stock Index Up_16x16.png"))
        );
        jButton3.setText("Move students");
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 3, true));
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(selectAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClassFromPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboClassToPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboClassFromPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboClassToPromotion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PromotionDlgLayout = new javax.swing.GroupLayout(PromotionDlg.getContentPane());
        PromotionDlg.getContentPane().setLayout(PromotionDlgLayout);
        PromotionDlgLayout.setHorizontalGroup(
            PromotionDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        PromotionDlgLayout.setVerticalGroup(
            PromotionDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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
        taRmarks.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Remarks on the Student's conduct, industry and ability", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 0, 204))); // NOI18N
        jScrollPane3.setViewportView(taRmarks);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Print_16x16.png"))
        );
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

        promoting.setTitle("Processing...");
        promoting.setAlwaysOnTop(true);
        promoting.setType(java.awt.Window.Type.POPUP);

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Promoting students. please wait...");

        jProgressBar1.setForeground(new java.awt.Color(0, 204, 0));
        jProgressBar1.setValue(50);
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout promotingLayout = new javax.swing.GroupLayout(promoting.getContentPane());
        promoting.getContentPane().setLayout(promotingLayout);
        promotingLayout.setHorizontalGroup(
            promotingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(promotingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(promotingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addGroup(promotingLayout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        promotingLayout.setVerticalGroup(
            promotingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(promotingLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        processing.setResizable(false);
        processing.setType(java.awt.Window.Type.POPUP);

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Creating certificate. Please wait...");

        javax.swing.GroupLayout processingLayout = new javax.swing.GroupLayout(processing.getContentPane());
        processing.getContentPane().setLayout(processingLayout);
        processingLayout.setHorizontalGroup(
            processingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, processingLayout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        processingLayout.setVerticalGroup(
            processingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Students Management");
        setResizable(false);
        setSize(new java.awt.Dimension(210, 266));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 255, 153));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Students List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(204, 0, 0))); // NOI18N

        tableStudents.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reg", "Name", "Classroom"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStudents.getTableHeader().setReorderingAllowed(false);
        tableStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStudentsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableStudents);
        if (tableStudents.getColumnModel().getColumnCount() > 0) {
            tableStudents.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableStudents.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableStudents.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        btnOpenStudentsDialog.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenStudentsDialog.setForeground(new java.awt.Color(255, 0, 0));
        btnOpenStudentsDialog.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Add_16x16.png"))
        );
        btnOpenStudentsDialog.setText("New");
        btnOpenStudentsDialog.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0)));
        btnOpenStudentsDialog.setIconTextGap(10);
        btnOpenStudentsDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenStudentsDialogActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

        txtSearch.setBackground(new java.awt.Color(255, 255, 102));
        txtSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129)
                        .addComponent(btnOpenStudentsDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOpenStudentsDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Class", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        tableClassrooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Classrooms"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableClassrooms.getTableHeader().setReorderingAllowed(false);
        tableClassrooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClassroomsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableClassrooms);
        if (tableClassrooms.getColumnModel().getColumnCount() > 0) {
            tableClassrooms.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        btnOpenStudentsDialog1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenStudentsDialog1.setForeground(new java.awt.Color(255, 0, 0));
        btnOpenStudentsDialog1.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("images/Stock Index Up_16x16.png"))
        );
        btnOpenStudentsDialog1.setText("Promotion");
        btnOpenStudentsDialog1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 0)));
        btnOpenStudentsDialog1.setIconTextGap(10);
        btnOpenStudentsDialog1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenStudentsDialog1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOpenStudentsDialog1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpenStudentsDialog1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableClassroomsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClassroomsMouseClicked
        int row = tableClassrooms.getSelectedRow();
        if (row >= 0) {
            this.selectedClassroom = classrooms.get(row);
            this.students = StudentDAO.getInClass(selectedClassroom.getName());
            updateMainUI();
        }
    }//GEN-LAST:event_tableClassroomsMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(null, "No student selected");
        } else {
            int Response = JOptionPane.showConfirmDialog(null, selectedStudent.getName() + "'s details will be deleted permanently. Do you want to Continue ?", "Delete", JOptionPane.YES_NO_OPTION);
            if (Response == 0) {
                StudentDAO.delete(selectedStudent.getId());
                this.students = new ArrayList<>();
                StudentDAO.get().forEach(student -> {
                    if (student.getClassroom().equals(selectedClassroom.getName())) {
                        students.add(student);
                    }
                });
                updateMainUI();
                JOptionPane.showMessageDialog(null, "Student details deleted from the System", "Deleted", 1);
                StudentDetailsDialog.setVisible(false);
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddStudent();
        this.students = new ArrayList<>();
        StudentDAO.get().forEach(student -> {
            if (student.getClassroom().equals(selectedClassroom.getName())) {
                students.add(student);
            }
        });
        updateMainUI();
        StudentDetailsDialog.setVisible(false);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        selectedStudent = null;
        updateStudentUI();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnUploadProfilePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadProfilePictureActionPerformed
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(filter);
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filepath = f.getAbsolutePath();

        filepath = filepath.replace("\\", "/");
        getProfilePic();
    }//GEN-LAST:event_btnUploadProfilePictureActionPerformed

    private void txtKCPEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKCPEKeyReleased
        GradeKCPE();
    }//GEN-LAST:event_txtKCPEKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String searchText = txtSearch.getText();
        students = StudentDAO.get(searchText, "");
        updateMainUI();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnOpenStudentsDialog1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenStudentsDialog1ActionPerformed
        PromotionDlg.pack();
        PromotionDlg.setLocationRelativeTo(this);
        PromotionDlg.setVisible(true);
    }//GEN-LAST:event_btnOpenStudentsDialog1ActionPerformed

    private void comboClassFromPromotionPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboClassFromPromotionPopupMenuWillBecomeInvisible
        getClassPromotingFrom(false);
    }//GEN-LAST:event_comboClassFromPromotionPopupMenuWillBecomeInvisible

    private void selectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllActionPerformed
        if (selectAll.isSelected()) {
            getClassPromotingFrom(true);
        } else {
            getClassPromotingFrom(false);
        }
    }//GEN-LAST:event_selectAllActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        promoteStudents();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        RemarksDlg.pack();
        RemarksDlg.setLocationRelativeTo(this);
        RemarksDlg.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnOpenStudentsDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenStudentsDialogActionPerformed
        if (selectedClassroom == null) {
            JOptionPane.showMessageDialog(null, "Select a classroom", "acme", JOptionPane.INFORMATION_MESSAGE);
        } else {
            selectedStudent = null;
            updateStudentUI();
            txtClass.setSelectedItem(selectedClassroom.getName());
            StudentDetailsDialog.pack();
            StudentDetailsDialog.setLocationRelativeTo(null);
            StudentDetailsDialog.setVisible(true);
        }
    }//GEN-LAST:event_btnOpenStudentsDialogActionPerformed

    private void StudentDetailsDialogWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StudentDetailsDialogWindowLostFocus
        profilePic.setIcon(null);
    }//GEN-LAST:event_StudentDetailsDialogWindowLostFocus

    private void txtADMNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtADMNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtADMNOActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        processing.pack();
        processing.setLocationRelativeTo(this);
        processing.setVisible(true);
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                String fadmited, leavedate, remarks;
                InputStream report, logo;

                logo = getClass().getClassLoader().getResourceAsStream("images/kenya.png");
                BufferedImage image = null;
                if (logo != null) {
                    image = ImageIO.read(logo);
                } else {
                    System.out.print("Image not Found");
                }

                fadmited = cmbFamditted.getSelectedItem().toString();
                leavedate = ((JTextField) txtLeaveDate.getDateEditor().getUiComponent()).getText();
                remarks = taRmarks.getText();

                try {
                    HashMap param = new HashMap();
                    param.put("idno", selectedStudent.getRegNumber());
                    param.put("sname", selectedStudent.getName());
                    param.put("doa", selectedStudent.getDoa());
                    param.put("dob", selectedStudent.getDob());
                    param.put("fadmitted", fadmited);
                    param.put("leavedate", leavedate);
                    param.put("remarks", remarks);
                    param.put("courtOfArm", image);

                    report = getClass().getClassLoader().getResourceAsStream("reports/LeavingCert.jrxml");
                    JasperDesign jd = JRXmlLoader.load(report);
                    JasperReport jr = JasperCompileManager.compileReport(jd);
                    JasperPrint jp = JasperFillManager.fillReport(jr, param, ConnClass.connectDB());
                    JRViewer jv = new JRViewer(jp);

                    JFrame jf = new JFrame("Students Leaving Certificate: " + selectedStudent.getRegNumber());
                    jf.getContentPane().add(jv);
                    jf.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
                    jf.setType(Type.NORMAL);
                    jf.validate();
                    jf.setSize(new Dimension(900, 650));
                    jf.setLocationRelativeTo(StudentsFrm.this);
                    jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jf.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                processing.setVisible(false);
                RemarksDlg.dispose();
                StudentDetailsDialog.dispose();
            }
        };
        swingWorker.run();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStudentsMouseClicked
        if (tableStudents.getRowCount() > 0) {
            int row = tableStudents.getSelectedRow();
            selectedStudent = students.get(row);
            if (selectedStudent != null) {
                updateStudentUI();
                StudentDetailsDialog.pack();
                StudentDetailsDialog.setLocationRelativeTo(null);
                StudentDetailsDialog.setVisible(true);
            }
        }
    }//GEN-LAST:event_tableStudentsMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        updateMainUI();
    }//GEN-LAST:event_formWindowOpened

    private void StudentDetailsDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_StudentDetailsDialogWindowClosing
        updateMainUI();
    }//GEN-LAST:event_StudentDetailsDialogWindowClosing

    public static void main(String args[]) {
        try {
            com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "acme");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            ConnClass.printError(ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DecorationHelper.decorateWindows(false);
            new StudentsFrm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog PromotionDlg;
    private javax.swing.JDialog RemarksDlg;
    private javax.swing.JDialog StudentDetailsDialog;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOpenStudentsDialog;
    private javax.swing.JButton btnOpenStudentsDialog1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUploadProfilePicture;
    private javax.swing.JComboBox<String> cmbFamditted;
    private javax.swing.JComboBox<String> cmbGender;
    private javax.swing.JComboBox<String> comboClassFromPromotion;
    private javax.swing.JComboBox<String> comboClassToPromotion;
    private javax.swing.JComboBox<String> comboHouse;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JDialog processing;
    private javax.swing.JLabel profilePic;
    private javax.swing.JDialog promoting;
    private javax.swing.JTable promotionTable;
    private javax.swing.JCheckBox selectAll;
    private javax.swing.JTextArea taRmarks;
    private javax.swing.JTable tableClassrooms;
    private javax.swing.JTable tableStudents;
    private javax.swing.JTextField txtADMNO;
    private javax.swing.JComboBox<String> txtClass;
    private com.toedter.calendar.JDateChooser txtDOA;
    private com.toedter.calendar.JDateChooser txtDOB;
    private javax.swing.JTextField txtFather;
    private javax.swing.JTextField txtKCPE;
    private javax.swing.JTextField txtKcpeGrade;
    private com.toedter.calendar.JDateChooser txtLeaveDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

}
