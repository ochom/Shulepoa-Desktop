package main;

import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ActivationFrm extends javax.swing.JFrame {

    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = null, system_level;

    public ActivationFrm(String system_level) {
        initComponents();
        this.system_level = system_level;
    }

    public void UpdateLabel(String text) {
        jLabel1.setText(text);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        key4TXT = new javax.swing.JTextField();
        key1TXT = new javax.swing.JTextField();
        key2TXT = new javax.swing.JTextField();
        key3TXT = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 0), 5));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        key4TXT.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        key4TXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        key4TXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                key4TXTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                key4TXTKeyTyped(evt);
            }
        });
        jPanel2.add(key4TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 90, 30));

        key1TXT.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        key1TXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        key1TXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                key1TXTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                key1TXTKeyTyped(evt);
            }
        });
        jPanel2.add(key1TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        key2TXT.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        key2TXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        key2TXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                key2TXTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                key2TXTKeyTyped(evt);
            }
        });
        jPanel2.add(key2TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 90, 30));

        key3TXT.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        key3TXT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        key3TXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                key3TXTKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                key3TXTKeyTyped(evt);
            }
        });
        jPanel2.add(key3TXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 90, 30));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("---");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 30, 30));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("---");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 30, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("---");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 30, 30));

        jPanel3.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe Print", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Activate the system");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("Skip");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 204, 51));
        jButton2.setText("Activate >>");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 51), 3, true));
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("Activation Key is sent to you via Email or Phone. !!");
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reminder", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Calibri Light", 1, 13)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(">>  Your System must be a genuine Copy\n>>  Call or Text +254797969142 for payment Information\n>>  Make your Payment as Instructed\n>>  Activate the system using the Activation Key Sent to You");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Procedure", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria Math", 1, 12), new java.awt.Color(0, 204, 51))); // NOI18N
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(592, 362));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        new LoginFrm().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Activate();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void key1TXTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key1TXTKeyTyped
        int count = key1TXT.getText().length();
        if (count >= 4) {
            evt.consume();
            String c = String.valueOf(evt.getKeyChar());
            key2TXT.setText(c);
            key2TXT.requestFocus();
        }
    }//GEN-LAST:event_key1TXTKeyTyped

    private void key2TXTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key2TXTKeyTyped
        int count = key2TXT.getText().length();
        if (count >= 4) {
            evt.consume();
            String c = String.valueOf(evt.getKeyChar());
            key3TXT.setText(c);
            key3TXT.requestFocus();
        }
    }//GEN-LAST:event_key2TXTKeyTyped

    private void key3TXTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key3TXTKeyTyped
        int count = key3TXT.getText().length();
        if (count >= 4) {
            evt.consume();
            String c = String.valueOf(evt.getKeyChar());
            key4TXT.setText(c);
            key4TXT.requestFocus();
        }
    }//GEN-LAST:event_key3TXTKeyTyped

    private void key4TXTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key4TXTKeyTyped
        int count = key4TXT.getText().length();
        if (count >= 4) {
            evt.consume();
        }
    }//GEN-LAST:event_key4TXTKeyTyped

    private void key1TXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key1TXTKeyReleased
        key1TXT.setText(key1TXT.getText().toUpperCase());
    }//GEN-LAST:event_key1TXTKeyReleased

    private void key2TXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key2TXTKeyReleased
        key2TXT.setText(key2TXT.getText().toUpperCase());
    }//GEN-LAST:event_key2TXTKeyReleased

    private void key3TXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key3TXTKeyReleased
        key3TXT.setText(key3TXT.getText().toUpperCase());
    }//GEN-LAST:event_key3TXTKeyReleased

    private void key4TXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_key4TXTKeyReleased
        key4TXT.setText(key4TXT.getText().toUpperCase());
    }//GEN-LAST:event_key4TXTKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing
    private String getSchoolInfo(String SchoolName) {
        try {
            sql = "select * from tblSchool";
            pst = Conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                SchoolName = rs.getString("School_name");
                return SchoolName;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return SchoolName;
    }

    private void Activate() {
        String keyExtract = (getSchoolInfo("").toUpperCase().replaceAll("\\.", "").replaceAll("\\'", "")).replaceAll(" ", "");
        String sk1 = "";
        String sk2 = "";
        String sk3 = "";
        String sk4 = "";
        String sk5 = "";
        String sk6 = "";
        String sk7 = "";
        String sk8 = "";

        if (!keyExtract.isEmpty()) {
            sk1 = keyExtract.substring(1, 2);//2nd char
            sk2 = keyExtract.substring(3, 4);
            sk3 = keyExtract.substring(2, 3);
            sk4 = keyExtract.substring(5, 6);
            sk5 = keyExtract.substring(0, 1);
            sk6 = keyExtract.substring(7, 8);
            sk7 = keyExtract.substring(3, 4);
            sk8 = keyExtract.substring(4, 5);

        }
        //Reminder Key school code Order = 3,1,4,2
        String MyKey = key1TXT.getText() + key2TXT.getText() + key3TXT.getText() + key4TXT.getText();//Entered by the user
        int len = MyKey.length();
        if (len < 16) {
            JOptionPane.showMessageDialog(null, "This key is Incomplete. Ensure that each area is filled", "Error", 0);
        } else {
            boolean key_new_system = (MyKey.contains(sk1)
                    && MyKey.contains(sk2)
                    && MyKey.contains(sk3 + sk5)
                    && MyKey.contains(sk4)
                    && MyKey.contains(sk6)
                    && MyKey.contains(sk7 + sk8)
                    && MyKey.contains("L")
                    && MyKey.contains("OH")
                    && MyKey.contains("A")
                    && MyKey.contains("8R")
                    && MyKey.contains("P")
                    && MyKey.contains("S"));
            boolean key_update_system = (MyKey.contains(sk1)
                    && MyKey.contains(sk2)
                    && MyKey.contains(sk3 + sk5)
                    && MyKey.contains(sk4)
                    && MyKey.contains(sk6)
                    && MyKey.contains(sk7 + sk8)
                    && MyKey.contains("K")
                    && MyKey.contains("LC")
                    && MyKey.contains("A")
                    && MyKey.contains("C5")
                    && MyKey.contains("P")
                    && MyKey.contains("J"));
            if (system_level.equalsIgnoreCase("new")) {
                if (key_new_system) {
                    new ConfigFileClass().ActivateSystem();
                    JOptionPane.showMessageDialog(null, "Full License Activated succesfull", "Activation", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new LoginFrm().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Activation Key Entered for the Updated System ", "Activation", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (key_update_system) {
                    new ConfigFileClass().ActivateSystem();
                    JOptionPane.showMessageDialog(null, "Full Update License Activated succesfull", "Activation", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new LoginFrm().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Activation Key Entered for the Updated System ", "Activation", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ActivationFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActivationFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActivationFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActivationFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActivationFrm("new").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField key1TXT;
    private javax.swing.JTextField key2TXT;
    private javax.swing.JTextField key3TXT;
    private javax.swing.JTextField key4TXT;
    // End of variables declaration//GEN-END:variables
}
