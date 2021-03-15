package com.lysofts;

import com.lysofts.dao.SchoolDAO;
import com.lysofts.entities.School;
import com.lysofts.utils.ConnClass;
import javax.swing.JOptionPane;

public class ActivationFrm extends javax.swing.JFrame {

    public ActivationFrm() {
        initComponents();
        ConnClass.setFrameIcon(this);
    }
    
    private void Activate() {        
        School school = SchoolDAO.get();
        String keyExtract = (school.getName().toUpperCase().replaceAll("\\.", "").replaceAll("\\'", "")).replaceAll(" ", "");
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

            
            school.setActivated("1");
            if (key_new_system && SchoolDAO.update(school)) {
                JOptionPane.showMessageDialog(null, "Full License Activated succesfull", "Activation", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new AdminPanelFrm().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Activation Key Entered for the Updated System ", "Activation", JOptionPane.INFORMATION_MESSAGE);
            }
        }
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
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("License has expired:: Activate");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        jTextArea1.setText(">>  Your System must be a genuine Copy\n>>  Call or Text +254708113456 for payment Information\n>>  Make your Payment as Instructed\n>>  Activate the system using the Activation Key Sent to You");
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Procedure", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Cambria Math", 1, 12), new java.awt.Color(0, 204, 51))); // NOI18N
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
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

        setSize(new java.awt.Dimension(602, 392));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActivationFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField key1TXT;
    private javax.swing.JTextField key2TXT;
    private javax.swing.JTextField key3TXT;
    private javax.swing.JTextField key4TXT;
    // End of variables declaration//GEN-END:variables
}
