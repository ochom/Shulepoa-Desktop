package com.lysofts;

import com.lysofts.dao.SubjectDAO;
import com.lysofts.entities.MyEntityManager;
import com.lysofts.entities.Subject;
import com.lysofts.utils.ConnClass;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SubjectsFrm extends javax.swing.JFrame {

    List<Subject> subjects;
    SubjectDAO subjectDAO;
    Subject selectedSubject;

    public SubjectsFrm() {
        this.subjectDAO = new SubjectDAO();

        initComponents();
        new ConnClass().setFrameIcon(SubjectsFrm.this);

        updateUI();
    }

    public boolean isValidGrades() {
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

        if (Grade1 > Grade2) {
            if (Grade2 > Grade3) {
                if (Grade3 > Grade4) {
                    if (Grade4 > Grade5) {
                        if (Grade5 > Grade6) {
                            if (Grade6 > Grade7) {
                                if (Grade7 > Grade8) {
                                    if (Grade8 > Grade9) {
                                        if (Grade9 > Grade10) {
                                            if (Grade10 > Grade11) {
                                                if (Grade11 > Grade12) {
                                                    return true;
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Check the range between D- and E");
                                                    return false;
                                                }

                                            } else {
                                                JOptionPane.showMessageDialog(null, "Check the range between D and D-");
                                                return false;
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(null, "Check the range between D+ and D");
                                            return false;
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(null, "Check the range between C- and D+");
                                        return false;
                                    }

                                } else {
                                    JOptionPane.showMessageDialog(null, "Check the range between C and C-");
                                    return false;
                                }

                            } else {
                                JOptionPane.showMessageDialog(null, "Check the range between C+ and C");
                                return false;
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Check the range between B- and C+");
                            return false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Check the range between B and B-");
                        return false;
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Check the range between B+ and B");
                    return false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "Check the range between A- and B+");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Check the range between A and A-");
            return false;
        }
    }

    private void updateUI() {
        List<Subject> subjects = subjectDAO.get();
        DefaultTableModel model = (DefaultTableModel) tableSubjects.getModel();
        model.setRowCount(0);
        subjects.forEach(subject -> {
            model.addRow(new Object[]{subject.getId(), subject.getNumber(), subject.getName(), subject.getCode()});
        });
        
        selectedSubject=null;
        txtNO.setSelectedIndex(0);
        txtSUBJECT.setText("");
        txtCODE.setText("");
        txtGrade1.setText("");
        txtGrade2.setText("");
        txtGrade3.setText("");
        txtGrade4.setText("");
        txtGrade5.setText("");
        txtGrade6.setText("");
        txtGrade7.setText("");
        txtGrade8.setText("");
        txtGrade9.setText("");
        txtGrade10.setText("");
        txtGrade11.setText("");
        txtGrade12.setText("");
    }

    private void AddSubject() {
        if (txtNO.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Subject number is required and must be unique.");
            return;
        }
        if (txtCODE.getText().isEmpty() || txtSUBJECT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Subject Name and code are required");
        } else if (txtGrade1.getText().equalsIgnoreCase("")
                || txtGrade2.getText().equalsIgnoreCase("")
                || txtGrade3.getText().equalsIgnoreCase("")
                || txtGrade4.getText().equalsIgnoreCase("")
                || txtGrade5.getText().equalsIgnoreCase("")
                || txtGrade6.getText().equalsIgnoreCase("")
                || txtGrade7.getText().equalsIgnoreCase("")
                || txtGrade8.getText().equalsIgnoreCase("")
                || txtGrade9.getText().equalsIgnoreCase("")
                || txtGrade10.getText().equalsIgnoreCase("")
                || txtGrade11.getText().equalsIgnoreCase("")
                || txtGrade12.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "All the grading mark points are required");
        } else if (isValidGrades()) {
            Subject subject;
            if (selectedSubject == null) {
                subject = new Subject();
                subject.setName(txtSUBJECT.getText());
                subject.setNumber(txtNO.getSelectedItem().toString());
                subject.setCode(txtCODE.getText());
                subject.setGrade1(txtGrade1.getText());
                subject.setGrade2(txtGrade2.getText());
                subject.setGrade3(txtGrade3.getText());
                subject.setGrade4(txtGrade4.getText());
                subject.setGrade5(txtGrade5.getText());
                subject.setGrade6(txtGrade6.getText());
                subject.setGrade7(txtGrade7.getText());
                subject.setGrade8(txtGrade8.getText());
                subject.setGrade9(txtGrade9.getText());
                subject.setGrade10(txtGrade10.getText());
                subject.setGrade11(txtGrade11.getText());
                subject.setGrade12(txtGrade12.getText());
                if (subjectDAO.add(subject)) {
                    addSubjectToSubjectNumbers(subject);
                    JOptionPane.showMessageDialog(null, "Subject details saved successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while saving the data", "Error", 0);
                }
            } else {
                subject = selectedSubject;
                subject.setName(txtSUBJECT.getText());
                subject.setNumber(txtNO.getSelectedItem().toString());
                subject.setCode(txtCODE.getText());
                subject.setGrade1(txtGrade1.getText());
                subject.setGrade2(txtGrade2.getText());
                subject.setGrade3(txtGrade3.getText());
                subject.setGrade4(txtGrade4.getText());
                subject.setGrade5(txtGrade5.getText());
                subject.setGrade6(txtGrade6.getText());
                subject.setGrade7(txtGrade7.getText());
                subject.setGrade8(txtGrade8.getText());
                subject.setGrade9(txtGrade9.getText());
                subject.setGrade10(txtGrade10.getText());
                subject.setGrade11(txtGrade11.getText());
                subject.setGrade12(txtGrade12.getText());
                if (subjectDAO.update(subject)) {
                    addSubjectToSubjectNumbers(subject);
                    JOptionPane.showMessageDialog(null, "Subject details updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Error occcured while updating the data", "Error", 0);
                }
            }
        }
    }

    private void addSubjectToSubjectNumbers(Subject subject) {
        EntityManager em = new MyEntityManager().getEm();
        try {
            String SQL = String.format("UPDATE Basic_SubjectNumbers SET S%sCODE='%s', S%sNAME='%s'",
                    subject.getNumber(), subject.getCode(), subject.getNumber(), subject.getName());
            Query query = em.createNativeQuery(SQL);
            query.executeUpdate();
            em.getTransaction().commit();
            System.out.println("Subjects Basic Numbers Registered....");
            updateUI();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableSubjects = new javax.swing.JTable();
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
        jLabel20 = new javax.swing.JLabel();
        txtNO = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Subjects & Subject Ranking");
        setMinimumSize(new java.awt.Dimension(529, 430));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tableSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Number", "Subject Name", "Code"
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
        tableSubjects.getTableHeader().setReorderingAllowed(false);
        tableSubjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSubjectsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSubjects);
        if (tableSubjects.getColumnModel().getColumnCount() > 0) {
            tableSubjects.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableSubjects.getColumnModel().getColumn(1).setPreferredWidth(20);
            tableSubjects.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableSubjects.getColumnModel().getColumn(3).setPreferredWidth(30);
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
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 89, -1));

        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("A");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 89, -1));

        txtGrade1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade1KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 50, -1));

        jLabel6.setForeground(new java.awt.Color(255, 255, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("A-");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 89, -1));

        jLabel7.setForeground(new java.awt.Color(255, 255, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("B+");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 89, -1));

        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("B");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 89, -1));

        jLabel9.setForeground(new java.awt.Color(255, 255, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("B-");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 89, -1));

        jLabel10.setForeground(new java.awt.Color(255, 255, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("C+");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 89, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("C");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 89, -1));

        jLabel12.setForeground(new java.awt.Color(255, 255, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("C-");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 89, -1));

        jLabel13.setForeground(new java.awt.Color(255, 255, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("D+");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 89, -1));

        jLabel14.setForeground(new java.awt.Color(255, 255, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("D");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 89, -1));

        jLabel15.setForeground(new java.awt.Color(255, 255, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("D-");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 89, -1));

        txtGrade2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade2KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 50, -1));

        txtGrade4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade4KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 50, -1));

        txtGrade3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade3KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 50, -1));

        txtGrade8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade8KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 50, -1));

        txtGrade7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade7KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 50, -1));

        txtGrade6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade6KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 50, -1));

        txtGrade5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade5KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 50, -1));

        txtGrade11.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade11KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 50, -1));

        txtGrade10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade10KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 50, -1));

        txtGrade9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade9KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 50, -1));

        jLabel18.setForeground(new java.awt.Color(255, 255, 0));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("E");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 89, -1));

        txtGrade12.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtGrade12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGrade12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGrade12KeyTyped(evt);
            }
        });
        jPanel3.add(txtGrade12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 50, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("MIN");
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 50, -1));

        txtSUBJECT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
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
        txtCODE.setToolTipText("e.g ENG");
        txtCODE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCODEMouseClicked(evt);
            }
        });
        txtCODE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCODEKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCODEKeyReleased(evt);
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

        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel17.setText("CODE");

        jLabel20.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel20.setText("S.N #");

        txtNO.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCODE, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCODE, txtNO, txtSUBJECT});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(5, 5, 5)
                        .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(txtSUBJECT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCODE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCODE, txtNO, txtSUBJECT});

        setSize(new java.awt.Dimension(605, 546));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        AddSubject();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tableSubjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSubjectsMouseClicked
        int row = tableSubjects.getSelectedRow();
        int id = Integer.parseInt(tableSubjects.getModel().getValueAt(row, 0).toString());
        selectedSubject = subjectDAO.get(id);
        if (selectedSubject != null) {
            txtNO.setSelectedItem(selectedSubject.getNumber());
            txtCODE.setText(selectedSubject.getCode());
            txtSUBJECT.setText(selectedSubject.getName());
            txtGrade1.setText(selectedSubject.getGrade1());
            txtGrade2.setText(selectedSubject.getGrade2());
            txtGrade3.setText(selectedSubject.getGrade3());
            txtGrade4.setText(selectedSubject.getGrade4());
            txtGrade5.setText(selectedSubject.getGrade5());
            txtGrade6.setText(selectedSubject.getGrade6());
            txtGrade7.setText(selectedSubject.getGrade7());
            txtGrade8.setText(selectedSubject.getGrade8());
            txtGrade9.setText(selectedSubject.getGrade9());
            txtGrade10.setText(selectedSubject.getGrade10());
            txtGrade11.setText(selectedSubject.getGrade11());
            txtGrade12.setText(selectedSubject.getGrade12());
        }
    }//GEN-LAST:event_tableSubjectsMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int response = JOptionPane.showConfirmDialog(null, "The Subject will be deleted Permanently", "Delete", JOptionPane.YES_NO_OPTION);
        if (response == 0) {
            subjectDAO.delete(selectedSubject.getId());
            JOptionPane.showMessageDialog(null, "Subject deleted");
            updateUI();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void txtSUBJECTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSUBJECTMouseClicked
        
    }//GEN-LAST:event_txtSUBJECTMouseClicked

    private void txtCODEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCODEMouseClicked
        
    }//GEN-LAST:event_txtCODEMouseClicked

    private void txtCODEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCODEKeyTyped
        char c = evt.getKeyChar();
        int length = txtCODE.getText().length();
        if (length >= 3) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtCODEKeyTyped

    private void txtGrade1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade1KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade1.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade2.requestFocus();
                evt.consume();
            }
        }

    }//GEN-LAST:event_txtGrade1KeyTyped

    private void txtGrade2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade2KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade2.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade3.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade2KeyTyped

    private void txtGrade3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade3KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade3.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade4.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade3KeyTyped

    private void txtGrade4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade4KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade4.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade5.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade4KeyTyped

    private void txtGrade5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade5KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade5.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade6.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade5KeyTyped

    private void txtGrade6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade6KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade6.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade7.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade6KeyTyped

    private void txtGrade7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade7KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade7.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade8.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade7KeyTyped

    private void txtGrade8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade8KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade8.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade9.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade8KeyTyped

    private void txtGrade9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade9KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade9.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade10.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade9KeyTyped

    private void txtGrade10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade10KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade10.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade11.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade10KeyTyped

    private void txtGrade11KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade11KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade11.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                txtGrade12.requestFocus();
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtGrade11KeyTyped

    private void txtGrade12KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGrade12KeyTyped
        char c = evt.getKeyChar();
        int length = txtGrade12.getText().length();
        if (length >= 2) {
            if (!(c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSubjects;
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
    private javax.swing.JComboBox<String> txtNO;
    private javax.swing.JTextField txtSUBJECT;
    // End of variables declaration//GEN-END:variables
}
