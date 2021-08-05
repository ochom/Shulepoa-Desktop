package com.lysofts;

import com.lysofts.dao.FeeDAO;
import com.lysofts.dao.StudentDAO;
import com.lysofts.dao.ClassroomDAO;
import com.lysofts.dao.ExamDAO;
import com.lysofts.entities.Fee;
import com.lysofts.entities.Student;
import com.lysofts.utils.ConnClass;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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

	Connection conn = ConnClass.connectDB();
	List<Student> students = new ArrayList<>();
	Student selectedStudent = null;

	InputStream reportFile = null;

	DefaultTableModel model = null;
	String today;

	public FeePanelFrm() {
		initComponents();
		ConnClass.setFrameIcon(this);
		updateUI();

		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		today = day + "/" + month + "/" + year;

	}

	private void updateUI() {
		Thread t = new Thread(() -> {
			comboForm.removeAllItems();
			comboForm.addItem("Select class");
			ClassroomDAO.get().forEach(classroom -> {
				comboForm.addItem(classroom.getName());
			});

			comboYear.removeAllItems();
			comboYear1.removeAllItems();
			comboYear2.removeAllItems();

			ExamDAO.getYears().forEach(exam -> {
				comboYear.addItem(exam.getYear());
				comboYear1.addItem(exam.getYear());
				comboYear2.addItem(exam.getYear());
			});
		});
		t.start();
	}

	private void printReceipt(Fee fee) {
		try {
			dlgCollectFee.setVisible(false);
			reportFile = getClass().getClassLoader().getResourceAsStream("reports/fee/StudentPaymentReceipt.jrxml");
			HashMap<String, Object> param = new HashMap<>();
			param.put("student_id", selectedStudent.getRegNumber());
			param.put("academic_year", fee.getYear());
			JasperDesign jd = JRXmlLoader.load(reportFile);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);
			JRViewer jv = new JRViewer(jp);

			JFrame jf = new JFrame("Student Payment Receipt");
			jf.getContentPane().add(jv);
			jf.setIconImage(Toolkit.getDefaultToolkit()
					.getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
			jf.setType(Type.NORMAL);
			jf.validate();
			jf.setSize(new Dimension(900, 650));
			jf.setLocationRelativeTo(this);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setVisible(true);
		} catch (HeadlessException | JRException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		dlgCollectFee = new javax.swing.JDialog();
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
		jButton4 = new javax.swing.JButton();
		dlgChooseAction = new javax.swing.JDialog();
		jButton2 = new javax.swing.JButton();
		jButton7 = new javax.swing.JButton();
		lblChoiceName = new javax.swing.JLabel();
		jButton3 = new javax.swing.JButton();
		dlgAdjustFee = new javax.swing.JDialog();
		jPanel10 = new javax.swing.JPanel();
		jPanel11 = new javax.swing.JPanel();
		lblClass1 = new javax.swing.JLabel();
		lblName1 = new javax.swing.JLabel();
		lblAdm1 = new javax.swing.JLabel();
		jPanel12 = new javax.swing.JPanel();
		jLabel42 = new javax.swing.JLabel();
		txtCollectionAmount1 = new javax.swing.JTextField();
		jLabel44 = new javax.swing.JLabel();
		txtDescription = new javax.swing.JTextField();
		jLabel48 = new javax.swing.JLabel();
		comboYear1 = new javax.swing.JComboBox<>();
		jButton8 = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		Table_Students = new javax.swing.JTable();
		txtSearch = new javax.swing.JTextField();
		jPanel7 = new javax.swing.JPanel();
		jButton6 = new javax.swing.JButton();
		comboForm = new javax.swing.JComboBox<>();
		comboYear2 = new javax.swing.JComboBox<>();

		dlgCollectFee.setTitle("Fee Collection");
		dlgCollectFee.setModal(true);
		dlgCollectFee.setResizable(false);
		dlgCollectFee.setType(java.awt.Window.Type.UTILITY);

		jPanel6.setBackground(new java.awt.Color(255, 255, 255));
		jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

		jPanel4.setBackground(new java.awt.Color(255, 255, 255));
		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));

		lblClass.setText("Class");

		lblName.setText("Name");

		lblAdm.setText("Adm No");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAdm, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblClass, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(63, Short.MAX_VALUE)));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel4Layout.createSequentialGroup()
								.addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lblAdm, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lblClass, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel5.setBackground(new java.awt.Color(255, 255, 255));
		jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Payment Details"));

		jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel35.setText(" Amount (Ksh.)");

		txtCollectionAmount
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
		txtCollectionAmount.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCollectionAmountKeyTyped(evt);
			}
		});

		jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel37.setText("Mode");

		comboModeofPayment
				.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Cheque", "Mobile-Money" }));
		comboModeofPayment
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

		jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel39.setText("Transaction ID");

		txtTransactionID
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

		jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel36.setText("Date");

		txtDateofPayment
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

		jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel41.setText("Rec. No.");

		txtReceiptNumber.setBackground(new java.awt.Color(212, 255, 253));
		txtReceiptNumber
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));
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
		jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(txtTransactionID))
								.addGroup(jPanel5Layout
										.createSequentialGroup()
										.addGroup(jPanel5Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 119,
														Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(jPanel5Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(comboModeofPayment, 0, 326, Short.MAX_VALUE)
												.addComponent(txtCollectionAmount)))
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												txtDateofPayment, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addGroup(jPanel5Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 123,
														Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(jPanel5Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(txtReceiptNumber)
												.addComponent(comboYear, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(txtServedBy))))
						.addContainerGap(8, Short.MAX_VALUE)));

		jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { jLabel35, jLabel36, jLabel37, jLabel38, jLabel39, jLabel40, jLabel41 });

		jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { comboModeofPayment,
				comboYear, txtCollectionAmount, txtDateofPayment, txtReceiptNumber, txtServedBy, txtTransactionID });

		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel5Layout.createSequentialGroup()
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel5Layout.createSequentialGroup().addGap(4, 4, 4).addComponent(
										txtCollectionAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 14,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel5Layout.createSequentialGroup().addGap(2, 2, 2)
										.addComponent(comboModeofPayment))
								.addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtTransactionID, javax.swing.GroupLayout.DEFAULT_SIZE, 28,
										Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(txtDateofPayment, javax.swing.GroupLayout.DEFAULT_SIZE, 32,
										Short.MAX_VALUE)
								.addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtReceiptNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 32,
										Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel5Layout.createSequentialGroup().addGap(2, 2, 2).addComponent(comboYear))
								.addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtServedBy, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { comboModeofPayment, comboYear, jLabel35, jLabel36, jLabel37, jLabel38,
						jLabel39, jLabel40, jLabel41, txtCollectionAmount, txtDateofPayment, txtReceiptNumber,
						txtServedBy, txtTransactionID });

		jButton4.setBackground(new java.awt.Color(255, 255, 255));
		jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check_16x16.png"))); // NOI18N
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

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addGap(48, 48, 48).addGroup(jPanel6Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										jPanel6Layout.createSequentialGroup()
												.addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGap(342, 342, 342))
								.addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(55, Short.MAX_VALUE)));

		jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { jPanel4, jPanel5 });

		jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jButton4,
								javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(32, 32, 32)));

		javax.swing.GroupLayout dlgCollectFeeLayout = new javax.swing.GroupLayout(dlgCollectFee.getContentPane());
		dlgCollectFee.getContentPane().setLayout(dlgCollectFeeLayout);
		dlgCollectFeeLayout.setHorizontalGroup(
				dlgCollectFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel6,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		dlgCollectFeeLayout.setVerticalGroup(
				dlgCollectFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel6,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		dlgChooseAction.setModal(true);
		dlgChooseAction.setType(java.awt.Window.Type.UTILITY);

		jButton2.setText("Record Payment");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton7.setText("Set Requirement");
		jButton7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton7ActionPerformed(evt);
			}
		});

		lblChoiceName.setText("jLabel2");

		jButton3.setText("Fee Statement");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout dlgChooseActionLayout = new javax.swing.GroupLayout(dlgChooseAction.getContentPane());
		dlgChooseAction.getContentPane().setLayout(dlgChooseActionLayout);
		dlgChooseActionLayout.setHorizontalGroup(dlgChooseActionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(dlgChooseActionLayout.createSequentialGroup().addGap(21, 21, 21)
						.addGroup(dlgChooseActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lblChoiceName, javax.swing.GroupLayout.PREFERRED_SIZE, 442,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(dlgChooseActionLayout.createSequentialGroup()
										.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 193,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 175,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 167,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		dlgChooseActionLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { jButton2, jButton3, jButton7 });

		dlgChooseActionLayout.setVerticalGroup(dlgChooseActionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlgChooseActionLayout.createSequentialGroup()
						.addGap(22, 22, 22).addComponent(lblChoiceName).addGap(18, 18, 18)
						.addGroup(dlgChooseActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton3))
						.addGap(14, 14, 14)));

		dlgChooseActionLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { jButton2, jButton3, jButton7 });

		dlgAdjustFee.setTitle("Fee Collection");
		dlgAdjustFee.setModal(true);
		dlgAdjustFee.setResizable(false);
		dlgAdjustFee.setType(java.awt.Window.Type.UTILITY);

		jPanel10.setBackground(new java.awt.Color(255, 255, 255));
		jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204)));

		jPanel11.setBackground(new java.awt.Color(255, 255, 255));
		jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Details"));

		lblClass1.setText("Class");

		lblName1.setText("Name");

		lblAdm1.setText("Adm No");

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel11Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lblName1, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAdm1, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblClass1, javax.swing.GroupLayout.PREFERRED_SIZE, 406,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(57, Short.MAX_VALUE)));
		jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel11Layout.createSequentialGroup()
								.addComponent(lblName1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lblAdm1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lblClass1, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel12.setBackground(new java.awt.Color(255, 255, 255));
		jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Fee Adjustment"));

		jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel42.setText(" Amount (Ksh.)");

		txtCollectionAmount1
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
		txtCollectionAmount1.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				txtCollectionAmount1KeyTyped(evt);
			}
		});

		jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel44.setText("Description");

		txtDescription.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

		jLabel48.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel48.setText("Academic Year");

		comboYear1
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

		javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
		jPanel12.setLayout(jPanel12Layout);
		jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel12Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel12Layout.createSequentialGroup()
										.addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 322,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel12Layout.createSequentialGroup()
										.addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 119,
												Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(txtCollectionAmount1)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel12Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(comboYear1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap()));

		jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { comboYear1, txtCollectionAmount1, txtDescription });

		jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { jLabel42, jLabel44, jLabel48 });

		jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel12Layout.createSequentialGroup()
						.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel12Layout.createSequentialGroup().addGap(4, 4, 4).addComponent(
										txtCollectionAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(18, 18, 18)
						.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 28,
										Short.MAX_VALUE))
						.addGap(18, 18, 18)
						.addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(
										jPanel12Layout.createSequentialGroup().addGap(2, 2, 2).addComponent(comboYear1))
								.addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 26,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] { comboYear1, jLabel42,
				jLabel44, jLabel48, txtCollectionAmount1, txtDescription });

		jButton8.setBackground(new java.awt.Color(255, 255, 255));
		jButton8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Check_16x16.png"))); // NOI18N
		jButton8.setText("Save");
		jButton8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
		jButton8.setContentAreaFilled(false);
		jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		jButton8.setOpaque(true);
		jButton8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton8ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
		jPanel10.setLayout(jPanel10Layout);
		jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap().addGroup(jPanel10Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel10Layout.createSequentialGroup()
										.addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGap(342, 342, 342))
								.addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] { jPanel11, jPanel12 });

		jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap()
						.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout dlgAdjustFeeLayout = new javax.swing.GroupLayout(dlgAdjustFee.getContentPane());
		dlgAdjustFee.getContentPane().setLayout(dlgAdjustFeeLayout);
		dlgAdjustFeeLayout.setHorizontalGroup(
				dlgAdjustFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel10,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		dlgAdjustFeeLayout.setVerticalGroup(
				dlgAdjustFeeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel10,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

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

		Table_Students.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Adm", "Name", "Class" }) {
			boolean[] canEdit = new boolean[] { false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Table_Students.setFillsViewportHeight(true);
		Table_Students.setGridColor(new java.awt.Color(204, 204, 204));
		Table_Students.setRowHeight(25);
		Table_Students.setSelectionBackground(new java.awt.Color(204, 204, 204));
		Table_Students.setSelectionForeground(new java.awt.Color(0, 0, 204));
		Table_Students.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				Table_StudentsMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(Table_Students);
		if (Table_Students.getColumnModel().getColumnCount() > 0) {
			Table_Students.getColumnModel().getColumn(1).setPreferredWidth(200);
		}

		txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		txtSearch.setForeground(new java.awt.Color(102, 102, 102));
		txtSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));
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
		jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Print_24x24.png"))); // NOI18N
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
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel7Layout.createSequentialGroup().addGap(6, 6, 6).addComponent(jButton6,
								javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 164,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(comboYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 164,
								javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(0, 14, Short.MAX_VALUE)));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel7Layout.createSequentialGroup().addContainerGap()
								.addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(28, 28, 28)
								.addComponent(comboYear2, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(txtSearch)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(jPanel1Layout.createSequentialGroup()
												.addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408,
														Short.MAX_VALUE)))
								.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
		this.dispose();
		new AdminPanelFrm().setVisible(true);
	}// GEN-LAST:event_formWindowClosing

	private void formWindowDeactivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowDeactivated
		// TODO add your handling code here:
	}// GEN-LAST:event_formWindowDeactivated

	private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_txtSearchMouseClicked
		txtSearch.setText("");
	}// GEN-LAST:event_txtSearchMouseClicked

	private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
		students = StudentDAO.get(txtSearch.getText(), "");
		model = (DefaultTableModel) Table_Students.getModel();
		model.setRowCount(0);
		students.forEach((student) -> {
			model.addRow(new Object[] { student.getRegNumber(), student.getName(), student.getClassroom() });
		});
	}// GEN-LAST:event_txtSearchKeyReleased

	private void Table_StudentsMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_Table_StudentsMouseClicked
		if (Table_Students.getModel().getRowCount() > 0) {
			selectedStudent = students.get(Table_Students.getSelectedRow());

			lblChoiceName.setText("Student Name: " + selectedStudent.getName());
			lblName.setText("Student Name: " + selectedStudent.getName());
			lblAdm.setText("Student Adm: " + selectedStudent.getRegNumber());
			lblClass.setText("Student Class: " + selectedStudent.getClassroom());

			lblName1.setText("Student Name: " + selectedStudent.getName());
			lblAdm1.setText("Student Adm: " + selectedStudent.getRegNumber());
			lblClass1.setText("Student Class: " + selectedStudent.getClassroom());

			dlgChooseAction.pack();
			dlgChooseAction.setLocationRelativeTo(this);
			dlgChooseAction.setVisible(true);
		}
	}// GEN-LAST:event_Table_StudentsMouseClicked

	private void txtCollectionAmountKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtCollectionAmountKeyTyped
		char c = evt.getKeyChar();
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
			getToolkit().beep();
			evt.consume();
			txtCollectionAmount.getToolTipText();
		}
	}// GEN-LAST:event_txtCollectionAmountKeyTyped

	private void txtReceiptNumberKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtReceiptNumberKeyTyped
		// TODO add your handling code here:
	}// GEN-LAST:event_txtReceiptNumberKeyTyped

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		Fee fee = new Fee();
		fee.setRegNumber(selectedStudent.getRegNumber());
		fee.setReceiptNumber(txtReceiptNumber.getText());
		fee.setCredit(txtCollectionAmount.getText());
		fee.setMode(comboModeofPayment.getSelectedItem().toString());
		fee.setTransCode(txtTransactionID.getText());
		fee.setYear(comboYear.getSelectedItem().toString());
		fee.setCreated(((JTextField) txtDateofPayment.getDateEditor().getUiComponent()).getText());
		fee.setCreatedBy(txtServedBy.getText());

		if (fee.getCredit().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Amount paid not Entered");
		} else if (fee.getCreated().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the date paid");
		} else if (fee.getReceiptNumber().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the Receipt Number of the paper receipt");
		} else if (fee.getCreatedBy().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter your name as the service person");
		} else {
			int res = JOptionPane.showConfirmDialog(null,
					"Do you wish to confirm that this payment has been received ?", "acme", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				if (FeeDAO.get(fee.getReceiptNumber()) != null) {
					JOptionPane.showMessageDialog(null, "This Receipt number has been used already", "Recorded", 1);
				} else {
					fee.setInWords(NumberToWordsClass.convert(Integer.parseInt(fee.getCredit())) + " only");
					if (FeeDAO.add(fee)) {
						System.out.println("Fee Register recorded");
						int feeReq = 0, feePaid = 0, feeBal;
						for (Fee f : FeeDAO.get()) {
							if (f.getRegNumber().equals(selectedStudent.getRegNumber())
									&& f.getYear().equals(fee.getYear())) {
								feeReq += f.getDebit() == null || f.getDebit().isEmpty() ? 0
										: Integer.parseInt(f.getDebit());
								feePaid += f.getCredit() == null || f.getCredit().isEmpty() ? 0
										: Integer.parseInt(f.getCredit());
							}
						}
						feeBal = feeReq - feePaid;
						selectedStudent.setFeeReuired(feeReq + "");
						selectedStudent.setFeePaid(feePaid + "");
						selectedStudent.setFeeBalance(feeBal + "");
						StudentDAO.update(selectedStudent);
						System.out.println("Exam System Fee Balance updated successfully");
						JOptionPane.showMessageDialog(null, "Payment recorded and the receipt is ready for printing",
								"Recorded", 1);
						printReceipt(fee);
					}
				}
			}
		}
	}// GEN-LAST:event_jButton4ActionPerformed

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
		String year = comboYear2.getSelectedItem().toString();
		String formName = comboForm.getSelectedItem().toString();
		if (formName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the class for which you want to print Register");
		} else if (year.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the academic year for which you want to print Register");
		} else {
			try {
				reportFile = getClass().getClassLoader().getResourceAsStream("reports/fee/FeeBalanceReport.jrxml");
				HashMap<String, Object> param = new HashMap<>();
				param.put("FormName", formName);
				param.put("academic_year", year);

				JasperDesign jd = JRXmlLoader.load(reportFile);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);
				JRViewer jv = new JRViewer(jp);

				JFrame jf = new JFrame("Student Fee Payment Register");
				jf.getContentPane().add(jv);
				jf.setIconImage(Toolkit.getDefaultToolkit()
						.getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
				jf.setType(Type.NORMAL);
				jf.validate();
				jf.setSize(new Dimension(900, 650));
				jf.setLocationRelativeTo(this);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setVisible(true);
			} catch (HeadlessException | JRException ex) {
				ex.printStackTrace();
			}
		}
	}// GEN-LAST:event_jButton6ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		dlgChooseAction.setVisible(false);
		dlgCollectFee.pack();
		dlgCollectFee.setLocationRelativeTo(this);
		dlgCollectFee.setVisible(true);
	}// GEN-LAST:event_jButton2ActionPerformed

	private void txtCollectionAmount1KeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtCollectionAmount1KeyTyped
		// TODO add your handling code here:
	}// GEN-LAST:event_txtCollectionAmount1KeyTyped

	private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
		Fee fee = new Fee();
		fee.setRegNumber(selectedStudent.getRegNumber());
		fee.setDebit(txtCollectionAmount1.getText());
		fee.setMode(txtDescription.getText());
		fee.setYear(comboYear1.getSelectedItem().toString());
		fee.setCreated(today);

		if (fee.getDebit().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter adjusted amount");
		} else {
			int res = JOptionPane.showConfirmDialog(null, "Do you wish to confirm that this fee adjustment?", "acme",
					JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				fee.setInWords(NumberToWordsClass.convert(Integer.parseInt(fee.getDebit())) + " only");
				if (FeeDAO.add(fee)) {
					System.out.println("Fee Register recorded");
					int feeReq = 0, feePaid = 0, feeBal;
					for (Fee f : FeeDAO.get()) {
						if (f.getRegNumber().equals(selectedStudent.getRegNumber())
								&& f.getYear().equals(fee.getYear())) {
							feeReq += f.getDebit() == null || f.getDebit().isEmpty() ? 0
									: Integer.parseInt(f.getDebit());
							feePaid += f.getCredit() == null || f.getCredit().isEmpty() ? 0
									: Integer.parseInt(f.getCredit());
						}
					}
					feeBal = feeReq - feePaid;
					selectedStudent.setFeeReuired(feeReq + "");
					selectedStudent.setFeePaid(feePaid + "");
					selectedStudent.setFeeBalance(feeBal + "");
					StudentDAO.update(selectedStudent);
					System.out.println("Exam System Fee Balance updated successfully");
					JOptionPane.showMessageDialog(null, "Fee adjustment recorded successfully", "Recorded", 1);
				}
			}
		}
	}// GEN-LAST:event_jButton8ActionPerformed

	private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton7ActionPerformed
		dlgChooseAction.setVisible(false);
		dlgAdjustFee.pack();
		dlgAdjustFee.setLocationRelativeTo(this);
		dlgAdjustFee.setVisible(true);
	}// GEN-LAST:event_jButton7ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		dlgChooseAction.setVisible(false);
		String year = comboYear2.getSelectedItem().toString();
		if (year.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter the academic year for which you want to print Register");
		} else {
			try {
				reportFile = getClass().getClassLoader().getResourceAsStream("reports/fee/FeeRegister.jrxml");
				HashMap<String, Object> param = new HashMap<>();
				param.put("student_id", selectedStudent.getRegNumber());
				param.put("academic_year", year);
				JasperDesign jd = JRXmlLoader.load(reportFile);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);
				JRViewer jv = new JRViewer(jp);

				JFrame jf = new JFrame("Student Fee Payment Register");
				jf.getContentPane().add(jv);
				jf.setIconImage(Toolkit.getDefaultToolkit()
						.getImage(getClass().getClassLoader().getResource("images/Print_16x16.png")));
				jf.setType(Type.NORMAL);
				jf.validate();
				jf.setSize(new Dimension(900, 650));
				jf.setLocationRelativeTo(this);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setVisible(true);
			} catch (HeadlessException | JRException e) {
				e.printStackTrace();
			}
		}
	}// GEN-LAST:event_jButton3ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FeePanelFrm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FeePanelFrm().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTable Table_Students;
	private javax.swing.JComboBox<String> comboForm;
	private javax.swing.JComboBox<String> comboModeofPayment;
	private javax.swing.JComboBox<String> comboYear;
	private javax.swing.JComboBox<String> comboYear1;
	private javax.swing.JComboBox<String> comboYear2;
	private javax.swing.JDialog dlgAdjustFee;
	private javax.swing.JDialog dlgChooseAction;
	private javax.swing.JDialog dlgCollectFee;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton6;
	private javax.swing.JButton jButton7;
	private javax.swing.JButton jButton8;
	private javax.swing.JLabel jLabel35;
	private javax.swing.JLabel jLabel36;
	private javax.swing.JLabel jLabel37;
	private javax.swing.JLabel jLabel38;
	private javax.swing.JLabel jLabel39;
	private javax.swing.JLabel jLabel40;
	private javax.swing.JLabel jLabel41;
	private javax.swing.JLabel jLabel42;
	private javax.swing.JLabel jLabel44;
	private javax.swing.JLabel jLabel48;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel10;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanel6;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblAdm;
	private javax.swing.JLabel lblAdm1;
	private javax.swing.JLabel lblChoiceName;
	private javax.swing.JLabel lblClass;
	private javax.swing.JLabel lblClass1;
	private javax.swing.JLabel lblName;
	private javax.swing.JLabel lblName1;
	private javax.swing.JTextField txtCollectionAmount;
	private javax.swing.JTextField txtCollectionAmount1;
	private com.toedter.calendar.JDateChooser txtDateofPayment;
	private javax.swing.JTextField txtDescription;
	private javax.swing.JTextField txtReceiptNumber;
	private javax.swing.JTextField txtSearch;
	private javax.swing.JTextField txtServedBy;
	private javax.swing.JTextField txtTransactionID;
	// End of variables declaration//GEN-END:variables
}
