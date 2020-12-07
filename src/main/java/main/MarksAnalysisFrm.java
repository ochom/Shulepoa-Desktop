
package main;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class MarksAnalysisFrm extends javax.swing.JFrame {
    Connection Conn = ConnClass.connectDB();
    PreparedStatement pst = null;
    ResultSet rs = null;
    String sql = null,Form="Select Form", classnumber = "1",Year="",Term="",Exam="";
    ArrayList ClassesList = new ArrayList();
    
    private Timer t;
    private ActionListener al;
    
    
    public MarksAnalysisFrm() {
        initComponents();
        new ConnClass().setFrameIcon(this);
        
        GetAcedmicYears();
        runprogressBar();
    }
    
    private void runprogressBar(){
        al = (ActionEvent e) -> {
            if ((AnalysisProg.getValue() < 100) || (AnalysisProg1.getValue() < 100)){
                AnalysisProg.setValue(AnalysisProg.getValue() + 1);
                AnalysisProg1.setValue(AnalysisProg1.getValue() + 1);
            }else if (AnalysisProg.getValue() == 100){
                t.stop();
                AnalysisProg.setValue(0);
                AnalysisProg1.setValue(0);
                LowerFormsProgressDlg.dispose();
                UpperFormsProgressDlg.dispose();
                JOptionPane.showMessageDialog(null, "Analysis completed succesfully", "acme", 1);
            }
        };
        t = new Timer(25, al);
    }
    private void GetSpecificClassesInThisForm(String classnumber){
        if(!ClassesList.isEmpty()){
            ClassesList.clear();
        }
        try{
            pst = Conn.prepareStatement("SELECT * FROM students_exams where substr(SE_StudentClass,1,1)='"+classnumber+"' group by SE_StudentClass");
            rs = pst.executeQuery();
            while(rs.next()){
                String classname = rs.getString("SE_StudentClass");
                ClassesList.add(classname);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void GetAcedmicYears(){
       cmbYear.removeAllItems();
       cmbYear.addItem("SELECT");
        try{
            pst = Conn.prepareStatement("SELECT * FROM students_exams group by Year ORDER BY Year DESC");
            rs = pst.executeQuery();
            while(rs.next()){
                String classname = rs.getString("Year");
                cmbYear.addItem(classname);
            }
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    private void getExamMarksForExport(){
    if (Form.equalsIgnoreCase("Select Form")) {
      JOptionPane.showMessageDialog(null, "Select a Form from the dropdown", "Error", 1);
    } else if (Year.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Enter the Year of examination", "Error", 1);
    } else if (Term.equalsIgnoreCase("SELECT")) {
      JOptionPane.showMessageDialog(null, "Select a Term from the dropdown", "Error", 1);
    } else if (Exam.equalsIgnoreCase("SELECT")) {
      JOptionPane.showMessageDialog(null, "Select a the Examination from the dropdown", "Error", 1);
    } else {
      try
      {
        DefaultTableModel model = new DefaultTableModel(new String[] { "Phone Number", "Text" }, 0);
        if (Exam.equalsIgnoreCase("Exam 1"))
        {
          sql = ("SELECT ifnull(students_exams.S1CODE,'') AS S1C, ifnull(students_exams.S1E1Marks,'') AS S1M,ifnull(students_exams.S1E1Grade,'') AS S1G,ifnull(students_exams.S2CODE,'') AS S2C, ifnull(students_exams.S2E1Marks,'') AS S2M,ifnull(students_exams.S2E1Grade,'') AS S2G,ifnull(students_exams.S3CODE,'') AS S3C, ifnull(students_exams.S3E1Marks,'') AS S3M,ifnull(students_exams.S3E1Grade,'') AS S3G,ifnull(students_exams.S4CODE,'') AS S4C, ifnull(students_exams.S4E1Marks,'') AS S4M,ifnull(students_exams.S4E1Grade,'') AS S4G,ifnull(students_exams.S5CODE,'') AS S5C, ifnull(students_exams.S5E1Marks,'') AS S5M,ifnull(students_exams.S5E1Grade,'') AS S5G,ifnull(students_exams.S6CODE,'') AS S6C, ifnull(students_exams.S6E1Marks,'') AS S6M,ifnull(students_exams.S6E1Grade,'') AS S6G,ifnull(students_exams.S7CODE,'') AS S7C, ifnull(students_exams.S7E1Marks,'') AS S7M,ifnull(students_exams.S7E1Grade,'') AS S7G,ifnull(students_exams.S8CODE,'') AS S8C, ifnull(students_exams.S8E1Marks,'') AS S8M,ifnull(students_exams.S8E1Grade,'') AS S8G,ifnull(students_exams.S9CODE,'') AS S9C, ifnull(students_exams.S9E1Marks,'') AS S9M,ifnull(students_exams.S9E1Grade,'') AS S9G,ifnull(students_exams.S10CODE,'') AS S10C, ifnull(students_exams.S10E1Marks,'') AS S10M,ifnull(students_exams.S10E1Grade,'') AS S10G,ifnull(students_exams.S11CODE,'') AS S11C, ifnull(students_exams.S11E1Marks,'') AS S11M,ifnull(students_exams.S11E1Grade,'') AS S11G,ifnull(students_exams.S12CODE,'') AS S12C, ifnull(students_exams.S12E1Marks,'') AS S12M,ifnull(students_exams.S12E1Grade,'') AS S12G,ifnull(students_exams.S13CODE,'') AS S13C, ifnull(students_exams.S13E1Marks,'') AS S13M,ifnull(students_exams.S13E1Grade,'') AS S13G,ifnull(students_exams.S14CODE,'') AS S14C, ifnull(students_exams.S14E1Marks,'') AS S14M,ifnull(students_exams.S14E1Grade,'') AS S14G,students_exams.Exam1TotalMarks AS TOTALMARKS,students_exams.Exam1MeanGrade AS GRADE, students_exams.Exam1OverallPosition AS Position,student_details.*, tblschool.* FROM tblschool inner join(students_exams inner join student_details on students_exams.SE_Student_id=student_details.student_id) WHERE substr(SE_StudentClass,1,1)='" + classnumber + "'  AND YEAR='" + Year + "' AND Term='" + Term + "' ORDER BY TOTALMARKS DESC");
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          String Result = null;
          while (rs.next())
          {
            Result = rs.getString("S1C") + " " + rs.getString("S1G") + " " + rs.getString("S2C") + " " + rs.getString("S2G") + " " + rs.getString("S3C") + " " + rs.getString("S3G") + " " + rs.getString("S4C") + " " + rs.getString("S4G") + " " + rs.getString("S5C") + " " + rs.getString("S5G") + " " + rs.getString("S6C") + " " + rs.getString("S6G") + " " + rs.getString("S7C") + " " + rs.getString("S7G") + " " + rs.getString("S8C") + " " + rs.getString("S8G") + " " + rs.getString("S9C") + " " + rs.getString("S9G") + " " + rs.getString("S10C") + " " + rs.getString("S10G") + " " + rs.getString("S11C") + " " + rs.getString("S11G") + " " + rs.getString("S12C") + " " + rs.getString("S12G") + " " + rs.getString("S13C") + " " + rs.getString("S13G") + " " + rs.getString("S14C") + " " + rs.getString("S14G") + " TOTAL: " + rs.getString("TOTALMARKS") + "" + rs.getString("GRADE") + " POS:" + rs.getString("Position");
            String ParentPhone = rs.getString("Phone1");
            String StudentDetails = rs.getString("Student_name") + " " + rs.getString("student_id");
            String Schoolname = rs.getString("School_name");
            
            String Text = (Schoolname + " CAT1 RESULTS " + StudentDetails + " " + Result).replaceAll("  ", "");
            
            model.addRow(new Object[] { ParentPhone, Text });
          }
        }
        else if (Exam.equalsIgnoreCase("Exam 2"))
        {
          sql = ("SELECT ifnull(students_exams.S1CODE,'') AS S1C, ifnull(students_exams.S1E2Marks,'') AS S1M,ifnull(students_exams.S1E2Grade,'') AS S1G,ifnull(students_exams.S2CODE,'') AS S2C, ifnull(students_exams.S2E2Marks,'') AS S2M,ifnull(students_exams.S2E2Grade,'') AS S2G,ifnull(students_exams.S3CODE,'') AS S3C, ifnull(students_exams.S3E2Marks,'') AS S3M,ifnull(students_exams.S3E2Grade,'') AS S3G,ifnull(students_exams.S4CODE,'') AS S4C, ifnull(students_exams.S4E2Marks,'') AS S4M,ifnull(students_exams.S4E2Grade,'') AS S4G,ifnull(students_exams.S5CODE,'') AS S5C, ifnull(students_exams.S5E2Marks,'') AS S5M,ifnull(students_exams.S5E2Grade,'') AS S5G,ifnull(students_exams.S6CODE,'') AS S6C, ifnull(students_exams.S6E2Marks,'') AS S6M,ifnull(students_exams.S6E2Grade,'') AS S6G,ifnull(students_exams.S7CODE,'') AS S7C, ifnull(students_exams.S7E2Marks,'') AS S7M,ifnull(students_exams.S7E2Grade,'') AS S7G,ifnull(students_exams.S8CODE,'') AS S8C, ifnull(students_exams.S8E2Marks,'') AS S8M,ifnull(students_exams.S8E2Grade,'') AS S8G,ifnull(students_exams.S9CODE,'') AS S9C, ifnull(students_exams.S9E2Marks,'') AS S9M,ifnull(students_exams.S9E2Grade,'') AS S9G,ifnull(students_exams.S10CODE,'') AS S10C, ifnull(students_exams.S10E2Marks,'') AS S10M,ifnull(students_exams.S10E2Grade,'') AS S10G,ifnull(students_exams.S11CODE,'') AS S11C, ifnull(students_exams.S11E2Marks,'') AS S11M,ifnull(students_exams.S11E2Grade,'') AS S11G,ifnull(students_exams.S12CODE,'') AS S12C, ifnull(students_exams.S12E2Marks,'') AS S12M,ifnull(students_exams.S12E2Grade,'') AS S12G,ifnull(students_exams.S13CODE,'') AS S13C, ifnull(students_exams.S13E2Marks,'') AS S13M,ifnull(students_exams.S13E2Grade,'') AS S13G,ifnull(students_exams.S14CODE,'') AS S14C, ifnull(students_exams.S14E2Marks,'') AS S14M,ifnull(students_exams.S14E2Grade,'') AS S14G,students_exams.Exam2TotalMarks AS TOTALMARKS,students_exams.Exam2MeanGrade AS GRADE, students_exams.Exam2OverallPosition AS Position,student_details.*, tblschool.* FROM tblschool inner join(students_exams inner join student_details on students_exams.SE_Student_id=student_details.student_id) WHERE substr(SE_StudentClass,1,1)='" + classnumber + "'  AND YEAR='" + Year + "' AND Term='" + Term + "' ORDER BY TOTALMARKS DESC");
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          String Result = null;
          while (rs.next())
          {
            Result = rs.getString("S1C") + " " + rs.getString("S1G") + " " + rs.getString("S2C") + " " + rs.getString("S2G") + " " + rs.getString("S3C") + " " + rs.getString("S3G") + " " + rs.getString("S4C") + " " + rs.getString("S4G") + " " + rs.getString("S5C") + " " + rs.getString("S5G") + " " + rs.getString("S6C") + " " + rs.getString("S6G") + " " + rs.getString("S7C") + " " + rs.getString("S7G") + " " + rs.getString("S8C") + " " + rs.getString("S8G") + " " + rs.getString("S9C") + " " + rs.getString("S9G") + " " + rs.getString("S10C") + " " + rs.getString("S10G") + " " + rs.getString("S11C") + " " + rs.getString("S11G") + " " + rs.getString("S12C") + " " + rs.getString("S12G") + " " + rs.getString("S13C") + " " + rs.getString("S13G") + " " + rs.getString("S14C") + " " + rs.getString("S14G") + " TOTAL: " + rs.getString("TOTALMARKS") + "" + rs.getString("GRADE") + " POS:" + rs.getString("Position");
            String ParentPhone = rs.getString("Phone1");
            String StudentDetails = rs.getString("Student_name") + " " + rs.getString("student_id");
            String Schoolname = rs.getString("School_name");
            
            String Text = (Schoolname + " CAT2 RESULTS " + StudentDetails + " " + Result).replaceAll("  ", "");
            
            model.addRow(new Object[] { ParentPhone, Text });
          }
        }
        else if (Exam.equalsIgnoreCase("Exam 3"))
        {
          sql = ("SELECT ifnull(students_exams.S1CODE,'') AS S1C, ifnull(students_exams.S1E3Marks,'') AS S1M,ifnull(students_exams.S1E3Grade,'') AS S1G,ifnull(students_exams.S2CODE,'') AS S2C, ifnull(students_exams.S2E3Marks,'') AS S2M,ifnull(students_exams.S2E3Grade,'') AS S2G,ifnull(students_exams.S3CODE,'') AS S3C, ifnull(students_exams.S3E3Marks,'') AS S3M,ifnull(students_exams.S3E3Grade,'') AS S3G,ifnull(students_exams.S4CODE,'') AS S4C, ifnull(students_exams.S4E3Marks,'') AS S4M,ifnull(students_exams.S4E3Grade,'') AS S4G,ifnull(students_exams.S5CODE,'') AS S5C, ifnull(students_exams.S5E3Marks,'') AS S5M,ifnull(students_exams.S5E3Grade,'') AS S5G,ifnull(students_exams.S6CODE,'') AS S6C, ifnull(students_exams.S6E3Marks,'') AS S6M,ifnull(students_exams.S6E3Grade,'') AS S6G,ifnull(students_exams.S7CODE,'') AS S7C, ifnull(students_exams.S7E3Marks,'') AS S7M,ifnull(students_exams.S7E3Grade,'') AS S7G,ifnull(students_exams.S8CODE,'') AS S8C, ifnull(students_exams.S8E3Marks,'') AS S8M,ifnull(students_exams.S8E3Grade,'') AS S8G,ifnull(students_exams.S9CODE,'') AS S9C, ifnull(students_exams.S9E3Marks,'') AS S9M,ifnull(students_exams.S9E3Grade,'') AS S9G,ifnull(students_exams.S10CODE,'') AS S10C, ifnull(students_exams.S10E3Marks,'') AS S10M,ifnull(students_exams.S10E3Grade,'') AS S10G,ifnull(students_exams.S11CODE,'') AS S11C, ifnull(students_exams.S11E3Marks,'') AS S11M,ifnull(students_exams.S11E3Grade,'') AS S11G,ifnull(students_exams.S12CODE,'') AS S12C, ifnull(students_exams.S12E3Marks,'') AS S12M,ifnull(students_exams.S12E3Grade,'') AS S12G,ifnull(students_exams.S13CODE,'') AS S13C, ifnull(students_exams.S13E3Marks,'') AS S13M,ifnull(students_exams.S13E3Grade,'') AS S13G,ifnull(students_exams.S14CODE,'') AS S14C, ifnull(students_exams.S14E3Marks,'') AS S14M,ifnull(students_exams.S14E3Grade,'') AS S14G,students_exams.Exam3TotalMarks AS TOTALMARKS,students_exams.Exam3MeanGrade AS GRADE, students_exams.Exam3OverallPosition AS Position,student_details.*, tblschool.* FROM tblschool inner join(students_exams inner join student_details on students_exams.SE_Student_id=student_details.student_id) WHERE substr(SE_StudentClass,1,1)='" + classnumber + "' AND YEAR='" + Year + "' AND Term='" + Term + "' ORDER BY TOTALMARKS DESC");
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          String Result = null;
          while (rs.next())
          {
            Result = rs.getString("S1C") + " " + rs.getString("S1G") + " " + rs.getString("S2C") + " " + rs.getString("S2G") + " " + rs.getString("S3C") + " " + rs.getString("S3G") + " " + rs.getString("S4C") + " " + rs.getString("S4G") + " " + rs.getString("S5C") + " " + rs.getString("S5G") + " " + rs.getString("S6C") + " " + rs.getString("S6G") + " " + rs.getString("S7C") + " " + rs.getString("S7G") + " " + rs.getString("S8C") + " " + rs.getString("S8G") + " " + rs.getString("S9C") + " " + rs.getString("S9G") + " " + rs.getString("S10C") + " " + rs.getString("S10G") + " " + rs.getString("S11C") + " " + rs.getString("S11G") + " " + rs.getString("S12C") + " " + rs.getString("S12G") + " " + rs.getString("S13C") + " " + rs.getString("S13G") + " " + rs.getString("S14C") + " " + rs.getString("S14G") + " TOTAL: " + rs.getString("TOTALMARKS") + "" + rs.getString("GRADE") + " POS:" + rs.getString("Position");
            String ParentPhone = rs.getString("Phone1");
            String StudentDetails = rs.getString("Student_name") + " " + rs.getString("student_id");
            String Schoolname = rs.getString("School_name");
            
            String Text = (Schoolname + " CAT3 RESULTS " + StudentDetails + " " + Result).replaceAll("  ", "");
            
            model.addRow(new Object[] { ParentPhone, Text });
          }
        }
        tableStudentMarks.setModel(model);
        tableStudentMarks.getColumn("Phone Number").setPreferredWidth(100);
        tableStudentMarks.getColumn("Text").setPreferredWidth(250);
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
    private void getEndTermMarksForExport(){
      if (Form.equalsIgnoreCase("Select Form")) {
        JOptionPane.showMessageDialog(null, "Select a Form from the dropdown", "Error", 1);
      } else if (Year.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Enter the Year of examination", "Error", 1);
      } else if (Term.equalsIgnoreCase("SELECT")) {
        JOptionPane.showMessageDialog(null, "Select a Term from the dropdown", "Error", 1);
      } else {
        try
        {
          DefaultTableModel model = new DefaultTableModel(new String[] { "Phone Number", "Text" }, 0);
          sql = ("SELECT ifnull(students_exams.S1CODE,'') AS S1C, ifnull(students_exams.S1AVGmarks,'') AS S1M,ifnull(students_exams.S1AVGGrade,'') AS S1G,ifnull(students_exams.S2CODE,'') AS S2C, ifnull(students_exams.S2AVGmarks,'') AS S2M,ifnull(students_exams.S2AVGGrade,'') AS S2G,ifnull(students_exams.S3CODE,'') AS S3C, ifnull(students_exams.S3AVGmarks,'') AS S3M,ifnull(students_exams.S3AVGGrade,'') AS S3G,ifnull(students_exams.S4CODE,'') AS S4C, ifnull(students_exams.S4AVGmarks,'') AS S4M,ifnull(students_exams.S4AVGGrade,'') AS S4G,ifnull(students_exams.S5CODE,'') AS S5C, ifnull(students_exams.S5AVGmarks,'') AS S5M,ifnull(students_exams.S5AVGGrade,'') AS S5G,ifnull(students_exams.S6CODE,'') AS S6C, ifnull(students_exams.S6AVGmarks,'') AS S6M,ifnull(students_exams.S6AVGGrade,'') AS S6G,ifnull(students_exams.S7CODE,'') AS S7C, ifnull(students_exams.S7AVGmarks,'') AS S7M,ifnull(students_exams.S7AVGGrade,'') AS S7G,ifnull(students_exams.S8CODE,'') AS S8C, ifnull(students_exams.S8AVGmarks,'') AS S8M,ifnull(students_exams.S8AVGGrade,'') AS S8G,ifnull(students_exams.S9CODE,'') AS S9C, ifnull(students_exams.S9AVGmarks,'') AS S9M,ifnull(students_exams.S9AVGGrade,'') AS S9G,ifnull(students_exams.S10CODE,'') AS S10C, ifnull(students_exams.S10AVGmarks,'') AS S10M,ifnull(students_exams.S10AVGGrade,'') AS S10G,ifnull(students_exams.S11CODE,'') AS S11C, ifnull(students_exams.S11AVGmarks,'') AS S11M,ifnull(students_exams.S11AVGGrade,'') AS S11G,ifnull(students_exams.S12CODE,'') AS S12C, ifnull(students_exams.S12AVGmarks,'') AS S12M,ifnull(students_exams.S12AVGGrade,'') AS S12G,ifnull(students_exams.S13CODE,'') AS S13C, ifnull(students_exams.S13AVGmarks,'') AS S13M,ifnull(students_exams.S13AVGGrade,'') AS S13G,ifnull(students_exams.S14CODE,'') AS S14C, ifnull(students_exams.S14AVGmarks,'') AS S14M,ifnull(students_exams.S14AVGGrade,'') AS S14G,students_exams.TotalMarks AS TOTALMARKS,students_exams.MeanGrade AS GRADE, students_exams.OverallPosition AS Position,student_details.*, tblschool.* FROM tblschool inner join(students_exams inner join student_details on students_exams.SE_Student_id=student_details.student_id) WHERE substr(SE_StudentClass,1,1)='" + classnumber + "'  AND YEAR='" + Year + "' AND Term='" + Term + "' ORDER BY TOTALMARKS DESC");
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          String Result = null;
          while (rs.next())
          {
            Result = rs.getString("S1C") + " " + rs.getString("S1G") + " " + rs.getString("S2C") + " " + rs.getString("S2G") + " " + rs.getString("S3C") + " " + rs.getString("S3G") + " " + rs.getString("S4C") + " " + rs.getString("S4G") + " " + rs.getString("S5C") + " " + rs.getString("S5G") + " " + rs.getString("S6C") + " " + rs.getString("S6G") + " " + rs.getString("S7C") + " " + rs.getString("S7G") + " " + rs.getString("S8C") + " " + rs.getString("S8G") + " " + rs.getString("S9C") + " " + rs.getString("S9G") + " " + rs.getString("S10C") + " " + rs.getString("S10G") + " " + rs.getString("S11C") + " " + rs.getString("S11G") + " " + rs.getString("S12C") + " " + rs.getString("S12G") + " " + rs.getString("S13C") + " " + rs.getString("S13G") + " " + rs.getString("S14C") + " " + rs.getString("S14G") + " TOTAL: " + rs.getString("TOTALMARKS") + "" + rs.getString("GRADE") + " POS:" + rs.getString("Position");
            String ParentPhone = rs.getString("Phone1");
            String StudentDetails = rs.getString("Student_name") + " " + rs.getString("student_id");
            String Schoolname = rs.getString("School_name");
            
            String Text = (Schoolname + " END TERM RESULTS " + StudentDetails + " " + Result).replaceAll("  ", "");

            model.addRow(new Object[] { ParentPhone, Text });
          }
          tableStudentMarks.setModel(model);
          tableStudentMarks.getColumn("Phone Number").setPreferredWidth(100);
          tableStudentMarks.getColumn("Text").setPreferredWidth(250);
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
    }
    private void UpdateTableFromStudentsExamsTable(){
      DefaultTableModel model = (DefaultTableModel)tableStudentMarks.getModel();
      model.setRowCount(0);
      try
      {
        sql = "SELECT students_exams.*,student_details.student_name FROM students_exams INNER JOIN student_details ON student_details.student_id=students_exams.SE_Student_id WHERE(substr(students_exams.SE_StudentClass,1,1)=? AND students_exams.Year=? AND students_exams.Term=?) ORDER BY students_exams.SE_Student_id ASC ";

        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        rs = pst.executeQuery();
        while (rs.next())
        {
          String ADMNO = rs.getString("SE_Student_id");
          String NAME = rs.getString("student_name");
          String Class = rs.getString("SE_StudentClass");
          model.addRow(new Object[] {ADMNO, NAME,Class});
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    
    private void PopulateSubjectMeanMarks() {
        try{
            lblProgress.setText("Calculating subject mean marks");
            lblProgress2.setText("Calculating subject mean marks");
          if (Exam.equalsIgnoreCase("EXAM 1")){
            sql = "UPDATE students_exams SET S1AVGMarks=S1E1Marks,S2AVGMarks=S2E1Marks,S3AVGMarks=S3E1Marks,S4AVGMarks=S4E1Marks,S5AVGMarks=S5E1Marks,S6AVGMarks=S6E1Marks,S7AVGMarks=S7E1Marks,S8AVGMarks=S8E1Marks,S9AVGMarks=S9E1Marks,S10AVGMarks=S10E1Marks,S11AVGMarks=S11E1Marks,S12AVGMarks=S12E1Marks,S13AVGMarks=S13E1Marks,S14AVGMarks=S14E1Marks WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          }else if (Exam.equalsIgnoreCase("EXAM 2")){
            sql = "UPDATE students_exams SET S1AVGMarks=(CASE WHEN ((S1E1Marks+S1E2Marks)+0>0) THEN rtrim(rtrim(Round(((S1E1Marks+S1E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S2AVGMarks=(CASE WHEN ((S2E1Marks+S2E2Marks)+0>0) THEN rtrim(rtrim(Round(((S2E1Marks+S2E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S3AVGMarks=(CASE WHEN ((S3E1Marks+S3E2Marks)+0>0) THEN rtrim(rtrim(Round(((S3E1Marks+S3E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S4AVGMarks=(CASE WHEN ((S4E1Marks+S4E2Marks)+0>0) THEN rtrim(rtrim(Round(((S4E1Marks+S4E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S5AVGMarks=(CASE WHEN ((S5E1Marks+S5E2Marks)+0>0) THEN rtrim(rtrim(Round(((S5E1Marks+S5E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S6AVGMarks=(CASE WHEN ((S6E1Marks+S6E2Marks)+0>0) THEN rtrim(rtrim(Round(((S6E1Marks+S6E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S7AVGMarks=(CASE WHEN ((S7E1Marks+S7E2Marks)+0>0) THEN rtrim(rtrim(Round(((S7E1Marks+S7E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S8AVGMarks=(CASE WHEN ((S8E1Marks+S8E2Marks)+0>0) THEN rtrim(rtrim(Round(((S8E1Marks+S8E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S9AVGMarks=(CASE WHEN ((S9E1Marks+S9E2Marks)+0>0) THEN rtrim(rtrim(Round(((S9E1Marks+S9E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S10AVGMarks=(CASE WHEN ((S10E1Marks+S10E2Marks)+0>0) THEN rtrim(rtrim(Round(((S10E1Marks+S10E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S11AVGMarks=(CASE WHEN ((S11E1Marks+S11E2Marks)+0>0) THEN rtrim(rtrim(Round(((S11E1Marks+S11E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S12AVGMarks=(CASE WHEN ((S12E1Marks+S12E2Marks)+0>0) THEN rtrim(rtrim(Round(((S12E1Marks+S12E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S13AVGMarks=(CASE WHEN ((S13E1Marks+S13E2Marks)+0>0) THEN rtrim(rtrim(Round(((S13E1Marks+S13E2Marks)/ 2.0)),'0'),'.') ELSE '' END),S14AVGMarks=(CASE WHEN ((S14E1Marks+S14E2Marks)+0>0) THEN rtrim(rtrim(Round(((S14E1Marks+S14E2Marks)/ 2.0)),'0'),'.') ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          }else if (Exam.equalsIgnoreCase("EXAM 3")){
            sql = "UPDATE students_exams SET S1AVGMarks=(CASE WHEN ((S1E1Marks+S1E2Marks+S1E3Marks)+0>0) THEN rtrim(rtrim(Round(((S1E1Marks+S1E2Marks+S1E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S2AVGMarks=(CASE WHEN ((S2E1Marks+S2E2Marks+S2E3Marks)+0>0) THEN rtrim(rtrim(Round(((S2E1Marks+S2E2Marks+S2E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S3AVGMarks=(CASE WHEN ((S3E1Marks+S3E2Marks+S3E3Marks)+0>0) THEN rtrim(rtrim(Round(((S3E1Marks+S3E2Marks+S3E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S4AVGMarks=(CASE WHEN ((S4E1Marks+S4E2Marks+S4E3Marks)+0>0) THEN rtrim(rtrim(Round(((S4E1Marks+S4E2Marks+S4E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S5AVGMarks=(CASE WHEN ((S5E1Marks+S5E2Marks+S5E3Marks)+0>0) THEN rtrim(rtrim(Round(((S5E1Marks+S5E2Marks+S5E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S6AVGMarks=(CASE WHEN ((S6E1Marks+S6E2Marks+S6E3Marks)+0>0) THEN rtrim(rtrim(Round(((S6E1Marks+S6E2Marks+S6E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S7AVGMarks=(CASE WHEN ((S7E1Marks+S7E2Marks+S7E3Marks)+0>0) THEN rtrim(rtrim(Round(((S7E1Marks+S7E2Marks+S7E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S8AVGMarks=(CASE WHEN ((S8E1Marks+S8E2Marks+S8E3Marks)+0>0) THEN rtrim(rtrim(Round(((S8E1Marks+S8E2Marks+S8E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S9AVGMarks=(CASE WHEN ((S9E1Marks+S9E2Marks+S9E3Marks)+0>0) THEN rtrim(rtrim(Round(((S9E1Marks+S9E2Marks+S9E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S10AVGMarks=(CASE WHEN ((S10E1Marks+S10E2Marks+S10E3Marks)+0>0) THEN rtrim(rtrim(Round(((S10E1Marks+S10E2Marks+S10E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S11AVGMarks=(CASE WHEN ((S11E1Marks+S11E2Marks+S11E3Marks)+0>0) THEN rtrim(rtrim(Round(((S11E1Marks+S11E2Marks+S11E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S12AVGMarks=(CASE WHEN ((S12E1Marks+S12E2Marks+S12E3Marks)+0>0) THEN rtrim(rtrim(Round(((S12E1Marks+S12E2Marks+S12E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S13AVGMarks=(CASE WHEN ((S13E1Marks+S13E2Marks+S13E3Marks)+0>0) THEN rtrim(rtrim(Round(((S13E1Marks+S13E2Marks+S13E3Marks)/ 3.0)),'0'),'.') ELSE '' END),S14AVGMarks=(CASE WHEN ((S14E1Marks+S14E2Marks+S14E3Marks)+0>0) THEN rtrim(rtrim(Round(((S14E1Marks+S14E2Marks+S14E3Marks)/ 3.0)),'0'),'.') ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
            }
          pst = Conn.prepareStatement(sql);
          pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
          pst.executeUpdate();
        }catch (SQLException e){
          e.printStackTrace();
        }
    }
    private void PopulateSubjectMeanPoints(){
        try{
            lblProgress.setText("Calculating subject mean points");
            lblProgress2.setText("Calculating subject mean points");
          if (Exam.equalsIgnoreCase("Exam 1")) {
            sql = "UPDATE students_exams SET S1AVGPoints=(S1E1Points),S2AVGPoints=(S2E1Points),S3AVGPoints=(S3E1Points),S4AVGPoints=(S4E1Points),S5AVGPoints=(S5E1Points),S6AVGPoints=(S6E1Points),S7AVGPoints=(S7E1Points),S8AVGPoints=(S8E1Points),S9AVGPoints=(S9E1Points),S10AVGPoints=(S10E1Points),S11AVGPoints=(S11E1Points),S12AVGPoints=(S12E1Points),S13AVGPoints=(S13E1Points),S14AVGPoints=(S14E1Points) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          } else if (Exam.equalsIgnoreCase("Exam 2")) {
            sql = "UPDATE students_exams SET S1AVGPoints=(CASE WHEN ((S1E1Points+S1E2Points)+0>0) THEN rtrim(rtrim(Round(((S1E1Points+S1E2Points)/ 2.0)),'0'),'.') ELSE '' END),S2AVGPoints=(CASE WHEN ((S2E1Points+S2E2Points)+0>0) THEN rtrim(rtrim(Round(((S2E1Points+S2E2Points)/ 2.0)),'0'),'.') ELSE '' END),S3AVGPoints=(CASE WHEN ((S3E1Points+S3E2Points)+0>0) THEN rtrim(rtrim(Round(((S3E1Points+S3E2Points)/ 2.0)),'0'),'.') ELSE '' END),S4AVGPoints=(CASE WHEN ((S4E1Points+S4E2Points)+0>0) THEN rtrim(rtrim(Round(((S4E1Points+S4E2Points)/ 2.0)),'0'),'.') ELSE '' END),S5AVGPoints=(CASE WHEN ((S5E1Points+S5E2Points)+0>0) THEN rtrim(rtrim(Round(((S5E1Points+S5E2Points)/ 2.0)),'0'),'.') ELSE '' END),S6AVGPoints=(CASE WHEN ((S6E1Points+S6E2Points)+0>0) THEN rtrim(rtrim(Round(((S6E1Points+S6E2Points)/ 2.0)),'0'),'.') ELSE '' END),S7AVGPoints=(CASE WHEN ((S7E1Points+S7E2Points)+0>0) THEN rtrim(rtrim(Round(((S7E1Points+S7E2Points)/ 2.0)),'0'),'.') ELSE '' END),S8AVGPoints=(CASE WHEN ((S8E1Points+S8E2Points)+0>0) THEN rtrim(rtrim(Round(((S8E1Points+S8E2Points)/ 2.0)),'0'),'.') ELSE '' END),S9AVGPoints=(CASE WHEN ((S9E1Points+S9E2Points)+0>0) THEN rtrim(rtrim(Round(((S9E1Points+S9E2Points)/ 2.0)),'0'),'.') ELSE '' END),S10AVGPoints=(CASE WHEN ((S10E1Points+S10E2Points)+0>0) THEN rtrim(rtrim(Round(((S10E1Points+S10E2Points)/ 2.0)),'0'),'.') ELSE '' END),S11AVGPoints=(CASE WHEN ((S11E1Points+S11E2Points)+0>0) THEN rtrim(rtrim(Round(((S11E1Points+S11E2Points)/ 2.0)),'0'),'.') ELSE '' END),S12AVGPoints=(CASE WHEN ((S12E1Points+S12E2Points)+0>0) THEN rtrim(rtrim(Round(((S12E1Points+S12E2Points)/ 2.0)),'0'),'.') ELSE '' END),S13AVGPoints=(CASE WHEN ((S13E1Points+S13E2Points)+0>0) THEN rtrim(rtrim(Round(((S13E1Points+S13E2Points)/ 2.0)),'0'),'.') ELSE '' END),S14AVGPoints=(CASE WHEN ((S14E1Points+S14E2Points)+0>0) THEN rtrim(rtrim(Round(((S14E1Points+S14E2Points)/ 2.0)),'0'),'.') ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          } else if (Exam.equalsIgnoreCase("Exam 3")) {
            sql = "UPDATE students_exams SET S1AVGPoints=(CASE WHEN ((S1E1Points+S1E2Points+S1E3Points)+0>0) THEN rtrim(rtrim(Round(((S1E1Points+S1E2Points+S1E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S2AVGPoints=(CASE WHEN ((S2E1Points+S2E2Points+S2E3Points)+0>0) THEN rtrim(rtrim(Round(((S2E1Points+S2E2Points+S2E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S3AVGPoints=(CASE WHEN ((S3E1Points+S3E2Points+S3E3Points)+0>0) THEN rtrim(rtrim(Round(((S3E1Points+S3E2Points+S3E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S4AVGPoints=(CASE WHEN ((S4E1Points+S4E2Points+S4E3Points)+0>0) THEN rtrim(rtrim(Round(((S4E1Points+S4E2Points+S4E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S5AVGPoints=(CASE WHEN ((S5E1Points+S5E2Points+S5E3Points)+0>0) THEN rtrim(rtrim(Round(((S5E1Points+S5E2Points+S5E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S6AVGPoints=(CASE WHEN ((S6E1Points+S6E2Points+S6E3Points)+0>0) THEN rtrim(rtrim(Round(((S6E1Points+S6E2Points+S6E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S7AVGPoints=(CASE WHEN ((S7E1Points+S7E2Points+S7E3Points)+0>0) THEN rtrim(rtrim(Round(((S7E1Points+S7E2Points+S7E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S8AVGPoints=(CASE WHEN ((S8E1Points+S8E2Points+S8E3Points)+0>0) THEN rtrim(rtrim(Round(((S8E1Points+S8E2Points+S8E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S9AVGPoints=(CASE WHEN ((S9E1Points+S9E2Points+S9E3Points)+0>0) THEN rtrim(rtrim(Round(((S9E1Points+S9E2Points+S9E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S10AVGPoints=(CASE WHEN ((S10E1Points+S10E2Points+S10E3Points)+0>0) THEN rtrim(rtrim(Round(((S10E1Points+S10E2Points+S10E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S11AVGPoints=(CASE WHEN ((S11E1Points+S11E2Points+S11E3Points)+0>0) THEN rtrim(rtrim(Round(((S11E1Points+S11E2Points+S11E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S12AVGPoints=(CASE WHEN ((S12E1Points+S12E2Points+S12E3Points)+0>0) THEN rtrim(rtrim(Round(((S12E1Points+S12E2Points+S12E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S13AVGPoints=(CASE WHEN ((S13E1Points+S13E2Points+S13E3Points)+0>0) THEN rtrim(rtrim(Round(((S13E1Points+S13E2Points+S13E3Points+1)/ 3.0)),'0'),'.') ELSE '' END),S14AVGPoints=(CASE WHEN ((S14E1Points+S14E2Points+S14E3Points)+0>0) THEN rtrim(rtrim(Round(((S14E1Points+S14E2Points+S14E3Points+1)/ 3.0)),'0'),'.') ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          }
          pst = Conn.prepareStatement(sql);
          pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
          pst.executeUpdate();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
    }
    private void PopulateSubjectMeanGrade(){
        try{
            lblProgress.setText("Calculating subject mean grades");
            lblProgress2.setText("Calculating subject means grades");
            sql = "UPDATE students_exams SET S1AVGGrade =( CASE WHEN S1AVGPoints='12' THEN 'A' WHEN S1AVGPoints='11' THEN 'A-' WHEN S1AVGPoints='10' THEN 'B+' WHEN S1AVGPoints='9' THEN 'B' WHEN S1AVGPoints='8' THEN 'B-' WHEN S1AVGPoints='7' THEN 'C+' WHEN S1AVGPoints='6' THEN 'C' WHEN S1AVGPoints='5' THEN 'C-' WHEN S1AVGPoints='4' THEN 'D+' WHEN S1AVGPoints='3' THEN 'D' WHEN S1AVGPoints='2' THEN 'D-' WHEN S1AVGPoints='1' THEN 'E' ELSE '' END),S2AVGGrade=(CASE WHEN S2AVGPoints='12' THEN 'A' WHEN S2AVGPoints='11' THEN 'A-' WHEN S2AVGPoints='10' THEN 'B+' WHEN S2AVGPoints='9' THEN 'B' WHEN S2AVGPoints='8' THEN 'B-' WHEN S2AVGPoints='7' THEN 'C+' WHEN S2AVGPoints='6' THEN 'C' WHEN S2AVGPoints='5' THEN 'C-' WHEN S2AVGPoints='4' THEN 'D+' WHEN S2AVGPoints='3' THEN 'D' WHEN S2AVGPoints='2' THEN 'D-' WHEN S2AVGPoints='1' THEN 'E' ELSE '' END),S3AVGGrade=(CASE WHEN S3AVGPoints='12' THEN 'A' WHEN S3AVGPoints='11' THEN 'A-' WHEN S3AVGPoints='10' THEN 'B+' WHEN S3AVGPoints='9' THEN 'B' WHEN S3AVGPoints='8' THEN 'B-' WHEN S3AVGPoints='7' THEN 'C+' WHEN S3AVGPoints='6' THEN 'C' WHEN S3AVGPoints='5' THEN 'C-' WHEN S3AVGPoints='4' THEN 'D+' WHEN S3AVGPoints='3' THEN 'D' WHEN S3AVGPoints='2' THEN 'D-' WHEN S3AVGPoints='1' THEN 'E' ELSE '' END),S4AVGGrade=(CASE WHEN S4AVGPoints='12' THEN 'A' WHEN S4AVGPoints='11' THEN 'A-' WHEN S4AVGPoints='10' THEN 'B+' WHEN S4AVGPoints='9' THEN 'B' WHEN S4AVGPoints='8' THEN 'B-' WHEN S4AVGPoints='7' THEN 'C+' WHEN S4AVGPoints='6' THEN 'C' WHEN S4AVGPoints='5' THEN 'C-' WHEN S4AVGPoints='4' THEN 'D+' WHEN S4AVGPoints='3' THEN 'D' WHEN S4AVGPoints='2' THEN 'D-' WHEN S4AVGPoints='1' THEN 'E' ELSE '' END),S5AVGGrade=(CASE WHEN S5AVGPoints='12' THEN 'A' WHEN S5AVGPoints='11' THEN 'A-' WHEN S5AVGPoints='10' THEN 'B+' WHEN S5AVGPoints='9' THEN 'B' WHEN S5AVGPoints='8' THEN 'B-' WHEN S5AVGPoints='7' THEN 'C+' WHEN S5AVGPoints='6' THEN 'C' WHEN S5AVGPoints='5' THEN 'C-' WHEN S5AVGPoints='4' THEN 'D+' WHEN S5AVGPoints='3' THEN 'D' WHEN S5AVGPoints='2' THEN 'D-' WHEN S5AVGPoints='1' THEN 'E' ELSE '' END),S6AVGGrade=(CASE WHEN S6AVGPoints='12' THEN 'A' WHEN S6AVGPoints='11' THEN 'A-' WHEN S6AVGPoints='10' THEN 'B+' WHEN S6AVGPoints='9' THEN 'B' WHEN S6AVGPoints='8' THEN 'B-' WHEN S6AVGPoints='7' THEN 'C+' WHEN S6AVGPoints='6' THEN 'C' WHEN S6AVGPoints='5' THEN 'C-' WHEN S6AVGPoints='4' THEN 'D+' WHEN S6AVGPoints='3' THEN 'D' WHEN S6AVGPoints='2' THEN 'D-' WHEN S6AVGPoints='1' THEN 'E' ELSE '' END),S7AVGGrade=(CASE WHEN S7AVGPoints='12' THEN 'A' WHEN S7AVGPoints='11' THEN 'A-' WHEN S7AVGPoints='10' THEN 'B+' WHEN S7AVGPoints='9' THEN 'B' WHEN S7AVGPoints='8' THEN 'B-' WHEN S7AVGPoints='7' THEN 'C+' WHEN S7AVGPoints='6' THEN 'C' WHEN S7AVGPoints='5' THEN 'C-' WHEN S7AVGPoints='4' THEN 'D+' WHEN S7AVGPoints='3' THEN 'D' WHEN S7AVGPoints='2' THEN 'D-' WHEN S7AVGPoints='1' THEN 'E' ELSE '' END),S8AVGGrade=(CASE WHEN S8AVGPoints='12' THEN 'A' WHEN S8AVGPoints='11' THEN 'A-' WHEN S8AVGPoints='10' THEN 'B+' WHEN S8AVGPoints='9' THEN 'B' WHEN S8AVGPoints='8' THEN 'B-' WHEN S8AVGPoints='7' THEN 'C+' WHEN S8AVGPoints='6' THEN 'C' WHEN S8AVGPoints='5' THEN 'C-' WHEN S8AVGPoints='4' THEN 'D+' WHEN S8AVGPoints='3' THEN 'D' WHEN S8AVGPoints='2' THEN 'D-' WHEN S8AVGPoints='1' THEN 'E' ELSE '' END),S9AVGGrade=(CASE WHEN S9AVGPoints='12' THEN 'A' WHEN S9AVGPoints='11' THEN 'A-' WHEN S9AVGPoints='10' THEN 'B+' WHEN S9AVGPoints='9' THEN 'B' WHEN S9AVGPoints='8' THEN 'B-' WHEN S9AVGPoints='7' THEN 'C+' WHEN S9AVGPoints='6' THEN 'C' WHEN S9AVGPoints='5' THEN 'C-' WHEN S9AVGPoints='4' THEN 'D+' WHEN S9AVGPoints='3' THEN 'D' WHEN S9AVGPoints='2' THEN 'D-' WHEN S9AVGPoints='1' THEN 'E' ELSE '' END),S10AVGGrade=(CASE WHEN S10AVGPoints='12' THEN 'A' WHEN S10AVGPoints='11' THEN 'A-' WHEN S10AVGPoints='10' THEN 'B+' WHEN S10AVGPoints='9' THEN 'B' WHEN S10AVGPoints='8' THEN 'B-' WHEN S10AVGPoints='7' THEN 'C+' WHEN S10AVGPoints='6' THEN 'C' WHEN S10AVGPoints='5' THEN 'C-' WHEN S10AVGPoints='4' THEN 'D+' WHEN S10AVGPoints='3' THEN 'D' WHEN S10AVGPoints='2' THEN 'D-' WHEN S10AVGPoints='1' THEN 'E' ELSE '' END),S11AVGGrade=(CASE WHEN S11AVGPoints='12' THEN 'A' WHEN S11AVGPoints='11' THEN 'A-' WHEN S11AVGPoints='10' THEN 'B+' WHEN S11AVGPoints='9' THEN 'B' WHEN S11AVGPoints='8' THEN 'B-' WHEN S11AVGPoints='7' THEN 'C+' WHEN S11AVGPoints='6' THEN 'C' WHEN S11AVGPoints='5' THEN 'C-' WHEN S11AVGPoints='4' THEN 'D+' WHEN S11AVGPoints='3' THEN 'D' WHEN S11AVGPoints='2' THEN 'D-' WHEN S11AVGPoints='1' THEN 'E' ELSE '' END),S12AVGGrade=(CASE WHEN S12AVGPoints='12' THEN 'A' WHEN S12AVGPoints='11' THEN 'A-' WHEN S12AVGPoints='10' THEN 'B+' WHEN S12AVGPoints='9' THEN 'B' WHEN S12AVGPoints='8' THEN 'B-' WHEN S12AVGPoints='7' THEN 'C+' WHEN S12AVGPoints='6' THEN 'C' WHEN S12AVGPoints='5' THEN 'C-' WHEN S12AVGPoints='4' THEN 'D+' WHEN S12AVGPoints='3' THEN 'D' WHEN S12AVGPoints='2' THEN 'D-' WHEN S12AVGPoints='1' THEN 'E' ELSE '' END),S13AVGGrade=(CASE WHEN S13AVGPoints='12' THEN 'A' WHEN S13AVGPoints='11' THEN 'A-' WHEN S13AVGPoints='10' THEN 'B+' WHEN S13AVGPoints='9' THEN 'B' WHEN S13AVGPoints='8' THEN 'B-' WHEN S13AVGPoints='7' THEN 'C+' WHEN S13AVGPoints='6' THEN 'C' WHEN S13AVGPoints='5' THEN 'C-' WHEN S13AVGPoints='4' THEN 'D+' WHEN S13AVGPoints='3' THEN 'D' WHEN S13AVGPoints='2' THEN 'D-' WHEN S13AVGPoints='1' THEN 'E' ELSE '' END),S14AVGGrade=(CASE WHEN S14AVGPoints='12' THEN 'A' WHEN S14AVGPoints='11' THEN 'A-' WHEN S14AVGPoints='10' THEN 'B+' WHEN S14AVGPoints='9' THEN 'B' WHEN S14AVGPoints='8' THEN 'B-' WHEN S14AVGPoints='7' THEN 'C+' WHEN S14AVGPoints='6' THEN 'C' WHEN S14AVGPoints='5' THEN 'C-' WHEN S14AVGPoints='4' THEN 'D+' WHEN S14AVGPoints='3' THEN 'D' WHEN S14AVGPoints='2' THEN 'D-' WHEN S14AVGPoints='1' THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
            pst = Conn.prepareStatement(sql);
            pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
            pst.executeUpdate();
        }catch (SQLException e){
          e.printStackTrace();
        }
    }
    private void PopulateSubjectRemarks(){
        try{
            lblProgress.setText("Remarking subjects perfomance");
            lblProgress2.setText("Remarking subjects perfomance");
          sql = "UPDATE students_exams SET S1Remarks=(CASE WHEN S1AVGPoints+0>11 THEN 'Excellent' WHEN S1AVGPoints+0>10 THEN 'Very Good' WHEN S1AVGPoints+0>9 THEN 'Good' WHEN S1AVGPoints+0>8 THEN 'Good' WHEN S1AVGPoints+0>7 THEN 'Average' WHEN S1AVGPoints+0>6 THEN 'Average' WHEN S1AVGPoints+0>5 THEN 'Average' WHEN S1AVGPoints+0>4 THEN 'Put more effort' WHEN S1AVGPoints+0>3 THEN 'Put more effort' WHEN S1AVGPoints+0>2 THEN 'Put more effort' WHEN S1AVGPoints+0>1 THEN 'Put more effort' WHEN S1AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S2Remarks=(CASE WHEN S2AVGPoints+0>11 THEN 'Excellent' WHEN S2AVGPoints+0>10 THEN 'Very Good' WHEN S2AVGPoints+0>9 THEN 'Good' WHEN S2AVGPoints+0>8 THEN 'Good' WHEN S2AVGPoints+0>7 THEN 'Average' WHEN S2AVGPoints+0>6 THEN 'Average' WHEN S2AVGPoints+0>5 THEN 'Average' WHEN S2AVGPoints+0>4 THEN 'Put more effort' WHEN S2AVGPoints+0>3 THEN 'Put more effort' WHEN S2AVGPoints+0>2 THEN 'Put more effort' WHEN S2AVGPoints+0>1 THEN 'Put more effort' WHEN S2AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S3Remarks=(CASE WHEN S3AVGPoints+0>11 THEN 'Excellent' WHEN S3AVGPoints+0>10 THEN 'Very Good' WHEN S3AVGPoints+0>9 THEN 'Good' WHEN S3AVGPoints+0>8 THEN 'Good' WHEN S3AVGPoints+0>7 THEN 'Average' WHEN S3AVGPoints+0>6 THEN 'Average' WHEN S3AVGPoints+0>5 THEN 'Average' WHEN S3AVGPoints+0>4 THEN 'Put more effort' WHEN S3AVGPoints+0>3 THEN 'Put more effort' WHEN S3AVGPoints+0>2 THEN 'Put more effort' WHEN S3AVGPoints+0>1 THEN 'Put more effort' WHEN S3AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S4Remarks=(CASE WHEN S4AVGPoints+0>11 THEN 'Excellent' WHEN S4AVGPoints+0>10 THEN 'Very Good' WHEN S4AVGPoints+0>9 THEN 'Good' WHEN S4AVGPoints+0>8 THEN 'Good' WHEN S4AVGPoints+0>7 THEN 'Average' WHEN S4AVGPoints+0>6 THEN 'Average' WHEN S4AVGPoints+0>5 THEN 'Average' WHEN S4AVGPoints+0>4 THEN 'Put more effort' WHEN S4AVGPoints+0>3 THEN 'Put more effort' WHEN S4AVGPoints+0>2 THEN 'Put more effort' WHEN S4AVGPoints+0>1 THEN 'Put more effort' WHEN S4AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S5Remarks=(CASE WHEN S5AVGPoints+0>11 THEN 'Excellent' WHEN S5AVGPoints+0>10 THEN 'Very Good' WHEN S5AVGPoints+0>9 THEN 'Good' WHEN S5AVGPoints+0>8 THEN 'Good' WHEN S5AVGPoints+0>7 THEN 'Average' WHEN S5AVGPoints+0>6 THEN 'Average' WHEN S5AVGPoints+0>5 THEN 'Average' WHEN S5AVGPoints+0>4 THEN 'Put more effort' WHEN S5AVGPoints+0>3 THEN 'Put more effort' WHEN S5AVGPoints+0>2 THEN 'Put more effort' WHEN S5AVGPoints+0>1 THEN 'Put more effort' WHEN S5AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S6Remarks=(CASE WHEN S6AVGPoints+0>11 THEN 'Excellent' WHEN S6AVGPoints+0>10 THEN 'Very Good' WHEN S6AVGPoints+0>9 THEN 'Good' WHEN S6AVGPoints+0>8 THEN 'Good' WHEN S6AVGPoints+0>7 THEN 'Average' WHEN S6AVGPoints+0>6 THEN 'Average' WHEN S6AVGPoints+0>5 THEN 'Average' WHEN S6AVGPoints+0>4 THEN 'Put more effort' WHEN S6AVGPoints+0>3 THEN 'Put more effort' WHEN S6AVGPoints+0>2 THEN 'Put more effort' WHEN S6AVGPoints+0>1 THEN 'Put more effort' WHEN S6AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S7Remarks=(CASE WHEN S7AVGPoints+0>11 THEN 'Excellent' WHEN S7AVGPoints+0>10 THEN 'Very Good' WHEN S7AVGPoints+0>9 THEN 'Good' WHEN S7AVGPoints+0>8 THEN 'Good' WHEN S7AVGPoints+0>7 THEN 'Average' WHEN S7AVGPoints+0>6 THEN 'Average' WHEN S7AVGPoints+0>5 THEN 'Average' WHEN S7AVGPoints+0>4 THEN 'Put more effort' WHEN S7AVGPoints+0>3 THEN 'Put more effort' WHEN S7AVGPoints+0>2 THEN 'Put more effort' WHEN S7AVGPoints+0>1 THEN 'Put more effort' WHEN S7AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S8Remarks=(CASE WHEN S8AVGPoints+0>11 THEN 'Excellent' WHEN S8AVGPoints+0>10 THEN 'Very Good' WHEN S8AVGPoints+0>9 THEN 'Good' WHEN S8AVGPoints+0>8 THEN 'Good' WHEN S8AVGPoints+0>7 THEN 'Average' WHEN S8AVGPoints+0>6 THEN 'Average' WHEN S8AVGPoints+0>5 THEN 'Average' WHEN S8AVGPoints+0>4 THEN 'Put more effort' WHEN S8AVGPoints+0>3 THEN 'Put more effort' WHEN S8AVGPoints+0>2 THEN 'Put more effort' WHEN S8AVGPoints+0>1 THEN 'Put more effort' WHEN S8AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S9Remarks=(CASE WHEN S9AVGPoints+0>11 THEN 'Excellent' WHEN S9AVGPoints+0>10 THEN 'Very Good' WHEN S9AVGPoints+0>9 THEN 'Good' WHEN S9AVGPoints+0>8 THEN 'Good' WHEN S9AVGPoints+0>7 THEN 'Average' WHEN S9AVGPoints+0>6 THEN 'Average' WHEN S9AVGPoints+0>5 THEN 'Average' WHEN S9AVGPoints+0>4 THEN 'Put more effort' WHEN S9AVGPoints+0>3 THEN 'Put more effort' WHEN S9AVGPoints+0>2 THEN 'Put more effort' WHEN S9AVGPoints+0>1 THEN 'Put more effort' WHEN S9AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S10Remarks=(CASE WHEN S10AVGPoints+0>11 THEN 'Excellent' WHEN S10AVGPoints+0>10 THEN 'Very Good' WHEN S10AVGPoints+0>9 THEN 'Good' WHEN S10AVGPoints+0>8 THEN 'Good' WHEN S10AVGPoints+0>7 THEN 'Average' WHEN S10AVGPoints+0>6 THEN 'Average' WHEN S10AVGPoints+0>5 THEN 'Average' WHEN S10AVGPoints+0>4 THEN 'Put more effort' WHEN S10AVGPoints+0>3 THEN 'Put more effort' WHEN S10AVGPoints+0>2 THEN 'Put more effort' WHEN S10AVGPoints+0>1 THEN 'Put more effort' WHEN S10AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S11Remarks=(CASE WHEN S11AVGPoints+0>11 THEN 'Excellent' WHEN S11AVGPoints+0>10 THEN 'Very Good' WHEN S11AVGPoints+0>9 THEN 'Good' WHEN S11AVGPoints+0>8 THEN 'Good' WHEN S11AVGPoints+0>7 THEN 'Average' WHEN S11AVGPoints+0>6 THEN 'Average' WHEN S11AVGPoints+0>5 THEN 'Average' WHEN S11AVGPoints+0>4 THEN 'Put more effort' WHEN S11AVGPoints+0>3 THEN 'Put more effort' WHEN S11AVGPoints+0>2 THEN 'Put more effort' WHEN S11AVGPoints+0>1 THEN 'Put more effort' WHEN S11AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S12Remarks=(CASE WHEN S12AVGPoints+0>11 THEN 'Excellent' WHEN S12AVGPoints+0>10 THEN 'Very Good' WHEN S12AVGPoints+0>9 THEN 'Good' WHEN S12AVGPoints+0>8 THEN 'Good' WHEN S12AVGPoints+0>7 THEN 'Average' WHEN S12AVGPoints+0>6 THEN 'Average' WHEN S12AVGPoints+0>5 THEN 'Average' WHEN S12AVGPoints+0>4 THEN 'Put more effort' WHEN S12AVGPoints+0>3 THEN 'Put more effort' WHEN S12AVGPoints+0>2 THEN 'Put more effort' WHEN S12AVGPoints+0>1 THEN 'Put more effort' WHEN S12AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S13Remarks=(CASE WHEN S13AVGPoints+0>11 THEN 'Excellent' WHEN S13AVGPoints+0>10 THEN 'Very Good' WHEN S13AVGPoints+0>9 THEN 'Good' WHEN S13AVGPoints+0>8 THEN 'Good' WHEN S13AVGPoints+0>7 THEN 'Average' WHEN S13AVGPoints+0>6 THEN 'Average' WHEN S13AVGPoints+0>5 THEN 'Average' WHEN S13AVGPoints+0>4 THEN 'Put more effort' WHEN S13AVGPoints+0>3 THEN 'Put more effort' WHEN S13AVGPoints+0>2 THEN 'Put more effort' WHEN S13AVGPoints+0>1 THEN 'Put more effort' WHEN S13AVGPoints+0>0 THEN 'Put more effort' ELSE '' END),S14Remarks=(CASE WHEN S14AVGPoints+0>11 THEN 'Excellent' WHEN S14AVGPoints+0>10 THEN 'Very Good' WHEN S14AVGPoints+0>9 THEN 'Good' WHEN S14AVGPoints+0>8 THEN 'Good' WHEN S14AVGPoints+0>7 THEN 'Average' WHEN S14AVGPoints+0>6 THEN 'Average' WHEN S14AVGPoints+0>5 THEN 'Average' WHEN S14AVGPoints+0>4 THEN 'Put more effort' WHEN S14AVGPoints+0>3 THEN 'Put more effort' WHEN S14AVGPoints+0>2 THEN 'Put more effort' WHEN S14AVGPoints+0>1 THEN 'Put more effort' WHEN S14AVGPoints+0>0 THEN 'Put more effort' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
          pst = Conn.prepareStatement(sql);
          pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
          pst.executeUpdate();
        }catch (SQLException e){
          e.printStackTrace();
        }
    }
    private void PopulateStudentSubjectPositions(){
        for(int i=0;i<ClassesList.size();i++){
            Form = ClassesList.get(i).toString();
            lblProgress.setText("Calculating Subject positions for Form :"+ Form);
            lblProgress2.setText("Calculating Subject positions for Form :"+ Form);
            try{
                String s1p,s2p,s3p,s4p,s5p,s6p,s7p,s8p,s9p,s10p,s11p,s12p,s13p,s14p;
                s1p = "(CASE WHEN (S1AVGMarks =' ' OR S1AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S1AVGMarks+0 > students_exams.S1AVGMarks+0 AND (students_exams.S1AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s2p = "(CASE WHEN (S2AVGMarks =' ' OR S2AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S2AVGMarks+0 > students_exams.S2AVGMarks+0 AND (students_exams.S2AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s3p = "(CASE WHEN (S3AVGMarks =' ' OR S3AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S3AVGMarks+0 > students_exams.S3AVGMarks+0 AND (students_exams.S3AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s4p = "(CASE WHEN (S4AVGMarks =' ' OR S4AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S4AVGMarks+0 > students_exams.S4AVGMarks+0 AND (students_exams.S4AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s5p = "(CASE WHEN (S5AVGMarks =' ' OR S5AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S5AVGMarks+0 > students_exams.S5AVGMarks+0 AND (students_exams.S5AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s6p = "(CASE WHEN (S6AVGMarks =' ' OR S6AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S6AVGMarks+0 > students_exams.S6AVGMarks+0 AND (students_exams.S6AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s7p = "(CASE WHEN (S7AVGMarks =' ' OR S7AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S7AVGMarks+0 > students_exams.S7AVGMarks+0 AND (students_exams.S7AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s8p = "(CASE WHEN (S8AVGMarks =' ' OR S8AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S8AVGMarks+0 > students_exams.S8AVGMarks+0 AND (students_exams.S8AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s9p = "(CASE WHEN (S9AVGMarks =' ' OR S9AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S9AVGMarks+0 > students_exams.S9AVGMarks+0 AND (students_exams.S9AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s10p = "(CASE WHEN (S10AVGMarks =' ' OR S10AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S10AVGMarks+0 > students_exams.S10AVGMarks+0 AND (students_exams.S10AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s11p = "(CASE WHEN (S11AVGMarks =' ' OR S11AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S11AVGMarks+0 > students_exams.S11AVGMarks+0 AND (students_exams.S11AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s12p = "(CASE WHEN (S12AVGMarks =' ' OR S12AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S12AVGMarks+0 > students_exams.S12AVGMarks+0 AND (students_exams.S12AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s13p = "(CASE WHEN (S13AVGMarks =' ' OR S13AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S13AVGMarks+0 > students_exams.S13AVGMarks+0 AND (students_exams.S13AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                s14p = "(CASE WHEN (S14AVGMarks =' ' OR S14AVGMarks ='') THEN ''  ELSE (1+(SELECT COUNT(*) FROM students_exams as t2 WHERE (t2.S14AVGMarks+0 > students_exams.S14AVGMarks+0 AND (students_exams.S14AVGMarks !='') AND t2.SE_StudentClass='" + Form + "' AND t2.Year='" + Year + "' AND t2.Term='" + Term + "'))) END)";
                sql = ("UPDATE students_exams SET S1AVGPosition="+s1p+",S2AVGPosition="+s2p+",S3AVGPosition="+s3p+",S4AVGPosition="+s4p+",S5AVGPosition="+s5p+",S6AVGPosition="+s6p+",S7AVGPosition="+s7p+",S8AVGPosition="+s8p+",S9AVGPosition="+s9p+",S10AVGPosition="+s10p+",S11AVGPosition="+s11p+",S12AVGPosition="+s12p+",S13AVGPosition="+s13p+",S14AVGPosition="+s14p+" WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')");
                pst = Conn.prepareStatement(sql);
                pst.executeUpdate();
            }catch (SQLException e){
              e.printStackTrace();
            }
        }
    }
    
    private void LowerFormSumtheExamTotalMarks(){
      try{
          lblProgress2.setText("Calculating Exam Total Marks");
        if (Exam.equalsIgnoreCase("Exam 1")) {
          sql = "UPDATE students_exams SET Exam1TotalMarks=(IFNULL(S1E1Marks,0)+IFNULL(S2E1Marks,0)+IFNULL(S3E1Marks,0)+IFNULL(S4E1Marks,0)+IFNULL(S5E1Marks,0)+IFNULL(S6E1Marks,0)+IFNULL(S7E1Marks,0)+IFNULL(S8E1Marks,0)+IFNULL(S9E1Marks,0)+IFNULL(S10E1Marks,0)+IFNULL(S11E1Marks,0)+IFNULL(S12E1Marks,0)+IFNULL(S13E1Marks,0)+IFNULL(S14E1Marks,0)),TotalOutOf='1100' WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
          sql = "UPDATE students_exams SET Exam2TotalMarks=(IFNULL(S1E2Marks,0)+IFNULL(S2E2Marks,0)+IFNULL(S3E2Marks,0)+IFNULL(S4E2Marks,0)+IFNULL(S5E2Marks,0)+IFNULL(S6E2Marks,0)+IFNULL(S7E2Marks,0)+IFNULL(S8E2Marks,0)+IFNULL(S9E2Marks,0)+IFNULL(S10E2Marks,0)+IFNULL(S11E2Marks,0)+IFNULL(S12E2Marks,0)+IFNULL(S13E2Marks,0)+IFNULL(S14E2Marks,0)) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
          sql = "UPDATE students_exams SET Exam3TotalMarks=(IFNULL(S1E3Marks,0)+IFNULL(S2E3Marks,0)+IFNULL(S3E3Marks,0)+IFNULL(S4E3Marks,0)+IFNULL(S5E3Marks,0)+IFNULL(S6E3Marks,0)+IFNULL(S7E3Marks,0)+IFNULL(S8E3Marks,0)+IFNULL(S9E3Marks,0)+IFNULL(S10E3Marks,0)+IFNULL(S11E3Marks,0)+IFNULL(S12E3Marks,0)+IFNULL(S13E3Marks,0)+IFNULL(S14E3Marks,0)) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    private void LowerFormSumtheExamMeanPoints(){
      try{
          lblProgress.setText("Calculating Exam Mean Points");
        if (Exam.equalsIgnoreCase("Exam 1")) {
          sql = "UPDATE students_exams SET Exam1MeanPoints=Round(((Exam1TotalMarks) * 12.00/1046),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
          sql = "UPDATE students_exams SET Exam2MeanPoints=Round(((Exam2TotalMarks) * 12.00/1046),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
          sql = "UPDATE students_exams SET Exam3MeanPoints=Round(((Exam3TotalMarks) * 12.00/1046),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();     
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void LowerFormpopulateExamMeanGrade(){
      try{
          lblProgress.setText("Calculating Exam Mean Grade");
        if (Exam.equalsIgnoreCase("Exam 1")) {
          sql = "UPDATE students_exams SET Exam1MeanGrade=(CASE WHEN (Exam1MeanPoints+0) >11 THEN 'A' WHEN ((Exam1MeanPoints+0)+0) >10 THEN 'A-' WHEN (Exam1MeanPoints+0) >9 THEN 'B+' WHEN (Exam1MeanPoints+0) >8 THEN 'B' WHEN (Exam1MeanPoints+0) >7 THEN 'B-' WHEN (Exam1MeanPoints+0) >6 THEN 'C+' WHEN (Exam1MeanPoints+0) >5 THEN 'C' WHEN (Exam1MeanPoints+0) >4 THEN 'C-' WHEN (Exam1MeanPoints+0) >3 THEN 'D+' WHEN (Exam1MeanPoints+0) >2 THEN 'D' WHEN (Exam1MeanPoints+0) >1 THEN 'D-' WHEN (Exam1MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
          sql = "UPDATE students_exams SET Exam2MeanGrade=(CASE WHEN (Exam2MeanPoints+0) >11 THEN 'A' WHEN ((Exam2MeanPoints+0)+0) >10 THEN 'A-' WHEN (Exam2MeanPoints+0) >9 THEN 'B+' WHEN (Exam2MeanPoints+0) >8 THEN 'B' WHEN (Exam2MeanPoints+0) >7 THEN 'B-' WHEN (Exam2MeanPoints+0) >6 THEN 'C+' WHEN (Exam2MeanPoints+0) >5 THEN 'C' WHEN (Exam2MeanPoints+0) >4 THEN 'C-' WHEN (Exam2MeanPoints+0) >3 THEN 'D+' WHEN (Exam2MeanPoints+0) >2 THEN 'D' WHEN (Exam2MeanPoints+0) >1 THEN 'D-' WHEN (Exam2MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
          sql = "UPDATE students_exams SET Exam3MeanGrade=(CASE WHEN (Exam3MeanPoints+0) >11 THEN 'A' WHEN ((Exam3MeanPoints+0)+0) >10 THEN 'A-' WHEN (Exam3MeanPoints+0) >9 THEN 'B+' WHEN (Exam3MeanPoints+0) >8 THEN 'B' WHEN (Exam3MeanPoints+0) >7 THEN 'B-' WHEN (Exam3MeanPoints+0) >6 THEN 'C+' WHEN (Exam3MeanPoints+0) >5 THEN 'C' WHEN (Exam3MeanPoints+0) >4 THEN 'C-' WHEN (Exam3MeanPoints+0) >3 THEN 'D+' WHEN (Exam3MeanPoints+0) >2 THEN 'D' WHEN (Exam3MeanPoints+0) >1 THEN 'D-' WHEN (Exam3MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        }
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
        
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    
    private void LowerFormSumtheEndTermTotalMarks(){
      try{
        lblProgress.setText("Calculating End Term Total Points");
          sql = "UPDATE students_exams SET TotalMarks=(IFNULL(S1AVGMarks,0)+IFNULL(S2AVGMarks,0)+IFNULL(S3AVGMarks,0)+IFNULL(S4AVGMarks,0)+IFNULL(S5AVGMarks,0)+IFNULL(S6AVGMarks,0)+IFNULL(S7AVGMarks,0)+IFNULL(S8AVGMarks,0)+IFNULL(S9AVGMarks,0)+IFNULL(S10AVGMarks,0)+IFNULL(S11AVGMarks,0)+IFNULL(S12AVGMarks,0)+IFNULL(S13AVGMarks,0)+IFNULL(S14AVGMarks,0)),TotalOutOf='1100' WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
               
          pst = Conn.prepareStatement(sql);
          pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
          pst.executeUpdate(); 
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void LowerFormSumtheEndTermMeanPoints(){
      try{
          lblProgress.setText("Calculating End Term Mean Points");
        if (Exam.equalsIgnoreCase("Exam 1")){
          sql = "UPDATE students_exams SET MeanPoints=(Exam1MeanPoints) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }else if (Exam.equalsIgnoreCase("Exam 2")){
          sql = "UPDATE students_exams SET MeanPoints=round(((IFNULL(Exam1MeanPoints,0)+IFNULL(Exam2MeanPoints,0))/2.0),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }else if (Exam.equalsIgnoreCase("Exam 3")){
          sql = "UPDATE students_exams SET MeanPoints=round(((IFNULL(Exam1MeanPoints,0)+IFNULL(Exam2MeanPoints,0)+IFNULL(Exam3MeanPoints,0))/3.0),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }
            pst = Conn.prepareStatement(sql);
            pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
            pst.executeUpdate();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void LowerFormEndTermMeanGrade(){
      try{
        lblProgress.setText("End Term Mean Grade");
        sql = "UPDATE students_exams SET MeanGrade=(CASE WHEN (MeanPoints+0)>11 THEN 'A' WHEN (MeanPoints+0)>10 THEN 'A-' WHEN (MeanPoints+0)>9 THEN 'B+' WHEN (MeanPoints+0)>8 THEN 'B' WHEN (MeanPoints+0)>7 THEN 'B-' WHEN (MeanPoints+0)>6 THEN 'C+' WHEN (MeanPoints+0)>5 THEN 'C' WHEN (MeanPoints+0)>4 THEN 'C-' WHEN (MeanPoints+0)>3 THEN 'D+' WHEN (MeanPoints+0)>2 THEN 'D' WHEN (MeanPoints+0)>1 THEN 'D-' WHEN (MeanPoints+0)>0 THEN 'E' ELSE 'X' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    
    private void UpperFormSumtheExam1TotalPoints(){
    try{
      int Rows = tableStudentMarks.getRowCount();
      for (int row = 0; row < Rows; row++){
        String StudentID = tableStudentMarks.getValueAt(row, 0).toString();
        lblProgress2.setText("Calculating Exam Total Points For: "+ tableStudentMarks.getValueAt(row, 1).toString()+ "ADM  :"+tableStudentMarks.getValueAt(row, 0).toString());
        sql = "SELECT (CASE WHEN S1E1Points+0>0 THEN S1E1Points ELSE '' END) as S1,(CASE WHEN S2E1Points+0>0 THEN S2E1Points ELSE '' END) as S2,(CASE WHEN S3E1Points+0>0 THEN S3E1Points ELSE '' END) as S3,(CASE WHEN S4E1Points+0>0 THEN S4E1Points ELSE '' END) as S4,(CASE WHEN S5E1Points+0>0 THEN S5E1Points ELSE '' END) as S5,(CASE WHEN S6E1Points+0>0 THEN S6E1Points ELSE '' END) as S6,(CASE WHEN S7E1Points+0>0 THEN S7E1Points ELSE '' END) as S7,(CASE WHEN S8E1Points+0>0 THEN S8E1Points ELSE '' END) as S8,(CASE WHEN S9E1Points+0>0 THEN S9E1Points ELSE '' END) as S9,(CASE WHEN S10E1Points+0>0 THEN S10E1Points ELSE '' END) as S10,(CASE WHEN S11E1Points+0>0 THEN S11E1Points ELSE '' END) as S11,(CASE WHEN S12E1Points+0>0 THEN S12E1Points ELSE '' END) as S12,(CASE WHEN S13E1Points+0>0 THEN S13E1Points ELSE '' END) as S13,(CASE WHEN S14E1Points+0>0 THEN S14E1Points ELSE '' END) as S14 FROM students_exams WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')";
        pst = Conn.prepareStatement(sql);
        rs = pst.executeQuery();
        if (rs.next()){
          int GroupA = 0;
          int FirstOfGroupBPicked = 0;
          int GroupBRemainder = 0;
          int FirstOfGroupCPicked = 0;
          int GroupCRemainders = 0;
          int GroupD = 0;
          int GroupCRemaindersToGroupD = 0;
          int GroupBRemainderToGroupCToGroupD = 0;
          
          String S1P = rs.getString("S1");String S2P = rs.getString("S2");String S3P = rs.getString("S3");
          String S4P = rs.getString("S4");String S5P = rs.getString("S5");String S6P = rs.getString("S6");
          String S7P = rs.getString("S7");String S8P = rs.getString("S8");String S9P = rs.getString("S9");
          String S10P = rs.getString("S10");String S11P = rs.getString("S11");String S12P = rs.getString("S12");
          String S13P = rs.getString("S13");String S14P = rs.getString("S14");
          
          int S1 = 0;
          if (!S1P.equalsIgnoreCase("")) {
            S1 = Integer.parseInt(S1P);
          }
          int S2 = 0;
          if (!S2P.equalsIgnoreCase("")) {
            S2 = Integer.parseInt(S2P);
          }
          int S3 = 0;
          if (!S3P.equalsIgnoreCase("")) {
            S3 = Integer.parseInt(S3P);
          }
          int S4 = 0;
          if (!S4P.equalsIgnoreCase("")) {
            S4 = Integer.parseInt(S4P);
          }
          int S5 = 0;
          if (!S5P.equalsIgnoreCase("")) {
            S5 = Integer.parseInt(S5P);
          }
          int S6 = 0;
          if (!S6P.equalsIgnoreCase("")) {
            S6 = Integer.parseInt(S6P);
          }
          int S7 = 0;
          if (!S7P.isEmpty()) {
            S7 = Integer.parseInt(S7P);
          }
          int S8 = 0;
          if (!S8P.equalsIgnoreCase("")) {
            S8 = Integer.parseInt(S8P);
          }
          int S9 = 0;
          if (!S9P.equalsIgnoreCase("")) {
            S9 = Integer.parseInt(S9P);
          }
          int S10 = 0;
          if (!S10P.equalsIgnoreCase("")) {
            S10 = Integer.parseInt(S10P);
          }
          int S11 = 0;
          if (!S11P.equalsIgnoreCase("")) {
            S11 = Integer.parseInt(S11P);
          }
          int S12 = 0;
          if (!S12P.equalsIgnoreCase("")) {
            S12 = Integer.parseInt(S12P);
          }
          int S13 = 0;
          if (!S13P.equalsIgnoreCase("")) {
            S13 = Integer.parseInt(S13P);
          }
          int S14 = 0;
          if (!S14P.equalsIgnoreCase("")) {
            S14 = Integer.parseInt(S14P);
          }
          GroupA = S1 + S2 + S3;
          GroupD = S10 + S11 + S12 + S13 + S14;
          if ((S4 <= S5) && (S4 <= S6))
          {
            FirstOfGroupBPicked = S5 + S6;
            GroupBRemainder = S4;
          }
          else if ((S5 <= S4) && (S5 <= S6))
          {
            FirstOfGroupBPicked = S4 + S6;
            GroupBRemainder = S5;
          }
          else if ((S6 <= S4) && (S6 <= S5))
          {
            FirstOfGroupBPicked = S4 + S5;
            GroupBRemainder = S6;
          }
          if ((S7 >= S8) && (S7 >= S9))
          {
            FirstOfGroupCPicked = S7;
            GroupCRemainders = S8 + S9;
          }
          else if ((S8 >= S7) && (S8 >= S9))
          {
            FirstOfGroupCPicked = S8;
            GroupCRemainders = S7 + S9;
          }
          else if ((S9 >= S7) && (S9 >= S8))
          {
            FirstOfGroupCPicked = S9;
            GroupCRemainders = S7 + S8;
          }
          if (GroupCRemainders >= GroupD) {
            GroupCRemaindersToGroupD = GroupCRemainders;
          } else if (GroupD >= GroupCRemainders) {
            GroupCRemaindersToGroupD = GroupD;
          }
          if ((GroupBRemainder <= FirstOfGroupCPicked) && (GroupBRemainder <= GroupCRemaindersToGroupD)) {
            GroupBRemainderToGroupCToGroupD = FirstOfGroupCPicked + GroupCRemaindersToGroupD;
          } else if ((FirstOfGroupCPicked <= GroupBRemainder) && (FirstOfGroupCPicked <= GroupCRemaindersToGroupD)) {
            GroupBRemainderToGroupCToGroupD = GroupBRemainder + GroupCRemaindersToGroupD;
          } else if ((GroupCRemaindersToGroupD <= GroupBRemainder) && (GroupCRemaindersToGroupD <= FirstOfGroupCPicked)) {
            GroupBRemainderToGroupCToGroupD = GroupBRemainder + FirstOfGroupCPicked;
          }
          int Points = GroupA + FirstOfGroupBPicked + GroupBRemainderToGroupCToGroupD;
          sql = ("UPDATE students_exams SET Exam1TotalMarks='" + Points + "',TotalOutOf='84' WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')");
          pst = Conn.prepareStatement(sql);
          pst.executeUpdate();
        }
      }
    }
    catch (HeadlessException|NumberFormatException|SQLException e)
    {
      e.printStackTrace();
    }
  }
    private void UpperFormSumtheExam2TotalPoints(){
      try{
        int Rows = tableStudentMarks.getRowCount();
        for (int row = 0; row < Rows; row++){
          String StudentID = tableStudentMarks.getValueAt(row, 0).toString();
          lblProgress2.setText("Calculating Exam Total Points For: "+ tableStudentMarks.getValueAt(row, 1).toString()+ "ADM  :"+tableStudentMarks.getValueAt(row, 0).toString());
          sql = "SELECT (CASE WHEN S1E2Points+0>0 THEN S1E2Points ELSE '' END) as S1,(CASE WHEN S2E2Points+0>0 THEN S2E2Points ELSE '' END) as S2,(CASE WHEN S3E2Points+0>0 THEN S3E2Points ELSE '' END) as S3,(CASE WHEN S4E2Points+0>0 THEN S4E2Points ELSE '' END) as S4,(CASE WHEN S5E2Points+0>0 THEN S5E2Points ELSE '' END) as S5,(CASE WHEN S6E2Points+0>0 THEN S6E2Points ELSE '' END) as S6,(CASE WHEN S7E2Points+0>0 THEN S7E2Points ELSE '' END) as S7,(CASE WHEN S8E2Points+0>0 THEN S8E2Points ELSE '' END) as S8,(CASE WHEN S9E2Points+0>0 THEN S9E2Points ELSE '' END) as S9,(CASE WHEN S10E2Points+0>0 THEN S10E2Points ELSE '' END) as S10,(CASE WHEN S11E2Points+0>0 THEN S11E2Points ELSE '' END) as S11,(CASE WHEN S12E2Points+0>0 THEN S12E2Points ELSE '' END) as S12,(CASE WHEN S13E2Points+0>0 THEN S13E2Points ELSE '' END) as S13,(CASE WHEN S14E2Points+0>0 THEN S14E2Points ELSE '' END) as S14 FROM students_exams WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')";
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          if (rs.next()){
            int GroupA = 0;
            int FirstOfGroupBPicked = 0;
            int GroupBRemainder = 0;
            int FirstOfGroupCPicked = 0;
            int GroupCRemainders = 0;
            int GroupD = 0;
            int GroupCRemaindersToGroupD = 0;
            int GroupBRemainderToGroupCToGroupD = 0;

            String S1P = rs.getString("S1");String S2P = rs.getString("S2");String S3P = rs.getString("S3");
            String S4P = rs.getString("S4");String S5P = rs.getString("S5");String S6P = rs.getString("S6");
            String S7P = rs.getString("S7");String S8P = rs.getString("S8");String S9P = rs.getString("S9");
            String S10P = rs.getString("S10");String S11P = rs.getString("S11");String S12P = rs.getString("S12");
            String S13P = rs.getString("S13");String S14P = rs.getString("S14");
            
            int S1 = 0;
            if (!S1P.equalsIgnoreCase("")) {
              S1 = Integer.parseInt(S1P);
            }
            int S2 = 0;
            if (!S2P.equalsIgnoreCase("")) {
              S2 = Integer.parseInt(S2P);
            }
            int S3 = 0;
            if (!S3P.equalsIgnoreCase("")) {
              S3 = Integer.parseInt(S3P);
            }
            int S4 = 0;
            if (!S4P.equalsIgnoreCase("")) {
              S4 = Integer.parseInt(S4P);
            }
            int S5 = 0;
            if (!S5P.equalsIgnoreCase("")) {
              S5 = Integer.parseInt(S5P);
            }
            int S6 = 0;
            if (!S6P.equalsIgnoreCase("")) {
              S6 = Integer.parseInt(S6P);
            }
            int S7 = 0;
            if (!S7P.isEmpty()) {
              S7 = Integer.parseInt(S7P);
            }
            int S8 = 0;
            if (!S8P.equalsIgnoreCase("")) {
              S8 = Integer.parseInt(S8P);
            }
            int S9 = 0;
            if (!S9P.equalsIgnoreCase("")) {
              S9 = Integer.parseInt(S9P);
            }
            int S10 = 0;
            if (!S10P.equalsIgnoreCase("")) {
              S10 = Integer.parseInt(S10P);
            }
            int S11 = 0;
            if (!S11P.equalsIgnoreCase("")) {
              S11 = Integer.parseInt(S11P);
            }
            int S12 = 0;
            if (!S12P.equalsIgnoreCase("")) {
              S12 = Integer.parseInt(S12P);
            }
            int S13 = 0;
            if (!S13P.equalsIgnoreCase("")) {
              S13 = Integer.parseInt(S13P);
            }
            int S14 = 0;
            if (!S14P.equalsIgnoreCase("")) {
              S14 = Integer.parseInt(S14P);
            }
            GroupA = S1 + S2 + S3;
            GroupD = S10 + S11 + S12 + S13 + S14;
            if ((S4 <= S5) && (S4 <= S6))
            {
              FirstOfGroupBPicked = S5 + S6;
              GroupBRemainder = S4;
            }
            else if ((S5 <= S4) && (S5 <= S6))
            {
              FirstOfGroupBPicked = S4 + S6;
              GroupBRemainder = S5;
            }
            else if ((S6 <= S4) && (S6 <= S5))
            {
              FirstOfGroupBPicked = S4 + S5;
              GroupBRemainder = S6;
            }
            if ((S7 >= S8) && (S7 >= S9))
            {
              FirstOfGroupCPicked = S7;
              GroupCRemainders = S8 + S9;
            }
            else if ((S8 >= S7) && (S8 >= S9))
            {
              FirstOfGroupCPicked = S8;
              GroupCRemainders = S7 + S9;
            }
            else if ((S9 >= S7) && (S9 >= S8))
            {
              FirstOfGroupCPicked = S9;
              GroupCRemainders = S7 + S8;
            }
            if (GroupCRemainders >= GroupD) {
              GroupCRemaindersToGroupD = GroupCRemainders;
            } else if (GroupD >= GroupCRemainders) {
              GroupCRemaindersToGroupD = GroupD;
            }
            if ((GroupBRemainder <= FirstOfGroupCPicked) && (GroupBRemainder <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = FirstOfGroupCPicked + GroupCRemaindersToGroupD;
            } else if ((FirstOfGroupCPicked <= GroupBRemainder) && (FirstOfGroupCPicked <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + GroupCRemaindersToGroupD;
            } else if ((GroupCRemaindersToGroupD <= GroupBRemainder) && (GroupCRemaindersToGroupD <= FirstOfGroupCPicked)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + FirstOfGroupCPicked;
            }
            int Points = GroupA + FirstOfGroupBPicked + GroupBRemainderToGroupCToGroupD;
            sql = ("UPDATE students_exams SET Exam2TotalMarks='" + Points + "' WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')");
            pst = Conn.prepareStatement(sql);
            pst.executeUpdate();
          }
        }
      }
      catch (HeadlessException|NumberFormatException|SQLException e)
      {
        e.printStackTrace();
      }
    }
    private void UpperFormSumtheExam3TotalPoints(){
      try{
        int Rows = tableStudentMarks.getRowCount();
        for (int row = 0; row < Rows; row++){
          String StudentID = tableStudentMarks.getValueAt(row, 0).toString();
          lblProgress2.setText("Calculating Exam Total Points For: "+ tableStudentMarks.getValueAt(row, 1).toString()+ "ADM  :"+tableStudentMarks.getValueAt(row, 0).toString());
          sql = "SELECT (CASE WHEN S1E3Points+0>0 THEN S1E3Points ELSE '' END) as S1,(CASE WHEN S2E3Points+0>0 THEN S2E3Points ELSE '' END) as S2,(CASE WHEN S3E3Points+0>0 THEN S3E3Points ELSE '' END) as S3,(CASE WHEN S4E3Points+0>0 THEN S4E3Points ELSE '' END) as S4,(CASE WHEN S5E3Points+0>0 THEN S5E3Points ELSE '' END) as S5,(CASE WHEN S6E3Points+0>0 THEN S6E3Points ELSE '' END) as S6,(CASE WHEN S7E3Points+0>0 THEN S7E3Points ELSE '' END) as S7,(CASE WHEN S8E3Points+0>0 THEN S8E3Points ELSE '' END) as S8,(CASE WHEN S9E3Points+0>0 THEN S9E3Points ELSE '' END) as S9,(CASE WHEN S10E3Points+0>0 THEN S10E3Points ELSE '' END) as S10,(CASE WHEN S11E3Points+0>0 THEN S11E3Points ELSE '' END) as S11,(CASE WHEN S12E3Points+0>0 THEN S12E3Points ELSE '' END) as S12,(CASE WHEN S13E3Points+0>0 THEN S13E3Points ELSE '' END) as S13,(CASE WHEN S14E3Points+0>0 THEN S14E3Points ELSE '' END) as S14 FROM students_exams WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')";
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          if (rs.next()){
            int GroupA = 0;
            int FirstOfGroupBPicked = 0;
            int GroupBRemainder = 0;
            int FirstOfGroupCPicked = 0;
            int GroupCRemainders = 0;
            int GroupD = 0;
            int GroupCRemaindersToGroupD = 0;
            int GroupBRemainderToGroupCToGroupD = 0;

            String S1P = rs.getString("S1");String S2P = rs.getString("S2");String S3P = rs.getString("S3");
            String S4P = rs.getString("S4");String S5P = rs.getString("S5");String S6P = rs.getString("S6");
            String S7P = rs.getString("S7");String S8P = rs.getString("S8");String S9P = rs.getString("S9");
            String S10P = rs.getString("S10");String S11P = rs.getString("S11");String S12P = rs.getString("S12");
            String S13P = rs.getString("S13");String S14P = rs.getString("S14");
            
            int S1 = 0;
            if (!S1P.equalsIgnoreCase("")) {
              S1 = Integer.parseInt(S1P);
            }
            int S2 = 0;
            if (!S2P.equalsIgnoreCase("")) {
              S2 = Integer.parseInt(S2P);
            }
            int S3 = 0;
            if (!S3P.equalsIgnoreCase("")) {
              S3 = Integer.parseInt(S3P);
            }
            int S4 = 0;
            if (!S4P.equalsIgnoreCase("")) {
              S4 = Integer.parseInt(S4P);
            }
            int S5 = 0;
            if (!S5P.equalsIgnoreCase("")) {
              S5 = Integer.parseInt(S5P);
            }
            int S6 = 0;
            if (!S6P.equalsIgnoreCase("")) {
              S6 = Integer.parseInt(S6P);
            }
            int S7 = 0;
            if (!S7P.isEmpty()) {
              S7 = Integer.parseInt(S7P);
            }
            int S8 = 0;
            if (!S8P.equalsIgnoreCase("")) {
              S8 = Integer.parseInt(S8P);
            }
            int S9 = 0;
            if (!S9P.equalsIgnoreCase("")) {
              S9 = Integer.parseInt(S9P);
            }
            int S10 = 0;
            if (!S10P.equalsIgnoreCase("")) {
              S10 = Integer.parseInt(S10P);
            }
            int S11 = 0;
            if (!S11P.equalsIgnoreCase("")) {
              S11 = Integer.parseInt(S11P);
            }
            int S12 = 0;
            if (!S12P.equalsIgnoreCase("")) {
              S12 = Integer.parseInt(S12P);
            }
            int S13 = 0;
            if (!S13P.equalsIgnoreCase("")) {
              S13 = Integer.parseInt(S13P);
            }
            int S14 = 0;
            if (!S14P.equalsIgnoreCase("")) {
              S14 = Integer.parseInt(S14P);
            }
            GroupA = S1 + S2 + S3;
            GroupD = S10 + S11 + S12 + S13 + S14;
            if ((S4 <= S5) && (S4 <= S6))
            {
              FirstOfGroupBPicked = S5 + S6;
              GroupBRemainder = S4;
            }
            else if ((S5 <= S4) && (S5 <= S6))
            {
              FirstOfGroupBPicked = S4 + S6;
              GroupBRemainder = S5;
            }
            else if ((S6 <= S4) && (S6 <= S5))
            {
              FirstOfGroupBPicked = S4 + S5;
              GroupBRemainder = S6;
            }
            if ((S7 >= S8) && (S7 >= S9))
            {
              FirstOfGroupCPicked = S7;
              GroupCRemainders = S8 + S9;
            }
            else if ((S8 >= S7) && (S8 >= S9))
            {
              FirstOfGroupCPicked = S8;
              GroupCRemainders = S7 + S9;
            }
            else if ((S9 >= S7) && (S9 >= S8))
            {
              FirstOfGroupCPicked = S9;
              GroupCRemainders = S7 + S8;
            }
            if (GroupCRemainders >= GroupD) {
              GroupCRemaindersToGroupD = GroupCRemainders;
            } else if (GroupD >= GroupCRemainders) {
              GroupCRemaindersToGroupD = GroupD;
            }
            if ((GroupBRemainder <= FirstOfGroupCPicked) && (GroupBRemainder <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = FirstOfGroupCPicked + GroupCRemaindersToGroupD;
            } else if ((FirstOfGroupCPicked <= GroupBRemainder) && (FirstOfGroupCPicked <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + GroupCRemaindersToGroupD;
            } else if ((GroupCRemaindersToGroupD <= GroupBRemainder) && (GroupCRemaindersToGroupD <= FirstOfGroupCPicked)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + FirstOfGroupCPicked;
            }
            int Points = GroupA + FirstOfGroupBPicked + GroupBRemainderToGroupCToGroupD;
            sql = ("UPDATE students_exams SET Exam3TotalMarks='" + Points + "' WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')");
            pst = Conn.prepareStatement(sql);
            pst.executeUpdate();
          }
        }
      }
      catch (HeadlessException|NumberFormatException|SQLException e)
      {
        e.printStackTrace();
      }
    }
    private void UpperFormSumtheExamMeanPoints(){
      try{ 
          lblProgress2.setText("Calculating Exam Mean Points");
        if (Exam.equalsIgnoreCase("Exam 1")) {
          sql = "UPDATE students_exams SET Exam1MeanPoints=(Round((Exam1TotalMarks/7.0),2)) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
          sql = "UPDATE students_exams SET Exam2MeanPoints=(Round((Exam2TotalMarks/7.0),2)) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
          sql = "UPDATE students_exams SET Exam3MeanPoints=(Round((Exam3TotalMarks/7.0),2)) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        }
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
      }
      catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void UpperFormExamMeanGrade(){
      try{
          lblProgress2.setText("Calculating Exam Mean Grade");
        if (Exam.equalsIgnoreCase("Exam 1")) {
          sql = "UPDATE students_exams SET Exam1MeanGrade=(CASE WHEN (Exam1MeanPoints+0) >11.43 THEN 'A' WHEN ((Exam1MeanPoints+0)+0) >10.43 THEN 'A-' WHEN (Exam1MeanPoints+0) >9.43 THEN 'B+' WHEN (Exam1MeanPoints+0) >8.43 THEN 'B' WHEN (Exam1MeanPoints+0) >7.43 THEN 'B-' WHEN (Exam1MeanPoints+0) >6.43 THEN 'C+' WHEN (Exam1MeanPoints+0) >5.43 THEN 'C' WHEN (Exam1MeanPoints+0) >4.43 THEN 'C-' WHEN (Exam1MeanPoints+0) >3.43 THEN 'D+' WHEN (Exam1MeanPoints+0) >2.43 THEN 'D' WHEN (Exam1MeanPoints+0) >1.43 THEN 'D-' WHEN (Exam1MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        } else if (Exam.equalsIgnoreCase("Exam 2")) {
          sql = "UPDATE students_exams SET Exam2MeanGrade=(CASE WHEN (Exam2MeanPoints+0) >11.43 THEN 'A' WHEN ((Exam2MeanPoints+0)+0) >10.43 THEN 'A-' WHEN (Exam2MeanPoints+0) >9.43 THEN 'B+' WHEN (Exam2MeanPoints+0) >8.43 THEN 'B' WHEN (Exam2MeanPoints+0) >7.43 THEN 'B-' WHEN (Exam2MeanPoints+0) >6.43 THEN 'C+' WHEN (Exam2MeanPoints+0) >5.43 THEN 'C' WHEN (Exam2MeanPoints+0) >4.43 THEN 'C-' WHEN (Exam2MeanPoints+0) >3.43 THEN 'D+' WHEN (Exam2MeanPoints+0) >2.43 THEN 'D' WHEN (Exam2MeanPoints+0) >1.43 THEN 'D-' WHEN (Exam2MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        } else if (Exam.equalsIgnoreCase("Exam 3")) {
          sql = "UPDATE students_exams SET Exam3MeanGrade=(CASE WHEN (Exam3MeanPoints+0) >11.43 THEN 'A' WHEN ((Exam3MeanPoints+0)+0) >10.43 THEN 'A-' WHEN (Exam3MeanPoints+0) >9.43 THEN 'B+' WHEN (Exam3MeanPoints+0) >8.43 THEN 'B' WHEN (Exam3MeanPoints+0) >7.43 THEN 'B-' WHEN (Exam3MeanPoints+0) >6.43 THEN 'C+' WHEN (Exam3MeanPoints+0) >5.43 THEN 'C' WHEN (Exam3MeanPoints+0) >4.43 THEN 'C-' WHEN (Exam3MeanPoints+0) >3.43 THEN 'D+' WHEN (Exam3MeanPoints+0) >2.43 THEN 'D' WHEN (Exam3MeanPoints+0) >1.43 THEN 'D-' WHEN (Exam3MeanPoints+0) >0 THEN 'E' ELSE '' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        }
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();

      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    
    private void UpperFormSumtheEndTermTotalMarks(){
      try{
        int Rows = tableStudentMarks.getRowCount();
        for (int row = 0; row < Rows; row++){
          String StudentID = tableStudentMarks.getValueAt(row, 0).toString();
          lblProgress2.setText("Calculating Exam Total Points For: "+ tableStudentMarks.getValueAt(row, 1).toString()+ "ADM  :"+tableStudentMarks.getValueAt(row, 0).toString());
          sql = "SELECT (CASE WHEN S1AVGPoints+0>0 THEN S1AVGPoints ELSE '' END) as S1,(CASE WHEN S2AVGPoints+0>0 THEN S2AVGPoints ELSE '' END) as S2,(CASE WHEN S3AVGPoints+0>0 THEN S3AVGPoints ELSE '' END) as S3,(CASE WHEN S4AVGPoints+0>0 THEN S4AVGPoints ELSE '' END) as S4,(CASE WHEN S5AVGPoints+0>0 THEN S5AVGPoints ELSE '' END) as S5,(CASE WHEN S6AVGPoints+0>0 THEN S6AVGPoints ELSE '' END) as S6,(CASE WHEN S7AVGPoints+0>0 THEN S7AVGPoints ELSE '' END) as S7,(CASE WHEN S8AVGPoints+0>0 THEN S8AVGPoints ELSE '' END) as S8,(CASE WHEN S9AVGPoints+0>0 THEN S9AVGPoints ELSE '' END) as S9,(CASE WHEN S10AVGPoints+0>0 THEN S10AVGPoints ELSE '' END) as S10,(CASE WHEN S11AVGPoints+0>0 THEN S11AVGPoints ELSE '' END) as S11,(CASE WHEN S12AVGPoints+0>0 THEN S12AVGPoints ELSE '' END) as S12,(CASE WHEN S13AVGPoints+0>0 THEN S13AVGPoints ELSE '' END) as S13,(CASE WHEN S14AVGPoints+0>0 THEN S14AVGPoints ELSE '' END) as S14 FROM students_exams WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')";
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          if (rs.next()){
            int GroupA = 0;
            int FirstOfGroupBPicked = 0;
            int GroupBRemainder = 0;
            int FirstOfGroupCPicked = 0;
            int GroupCRemainders = 0;
            int GroupD = 0;
            int GroupCRemaindersToGroupD = 0;
            int GroupBRemainderToGroupCToGroupD = 0;

            String S1P = rs.getString("S1");String S2P = rs.getString("S2");String S3P = rs.getString("S3");
            String S4P = rs.getString("S4");String S5P = rs.getString("S5");String S6P = rs.getString("S6");
            String S7P = rs.getString("S7");String S8P = rs.getString("S8");String S9P = rs.getString("S9");
            String S10P = rs.getString("S10");String S11P = rs.getString("S11");String S12P = rs.getString("S12");
            String S13P = rs.getString("S13");String S14P = rs.getString("S14");
            
            int S1 = 0;
            if (!S1P.equalsIgnoreCase("")) {
              S1 = Integer.parseInt(S1P);
            }
            int S2 = 0;
            if (!S2P.equalsIgnoreCase("")) {
              S2 = Integer.parseInt(S2P);
            }
            int S3 = 0;
            if (!S3P.equalsIgnoreCase("")) {
              S3 = Integer.parseInt(S3P);
            }
            int S4 = 0;
            if (!S4P.equalsIgnoreCase("")) {
              S4 = Integer.parseInt(S4P);
            }
            int S5 = 0;
            if (!S5P.equalsIgnoreCase("")) {
              S5 = Integer.parseInt(S5P);
            }
            int S6 = 0;
            if (!S6P.equalsIgnoreCase("")) {
              S6 = Integer.parseInt(S6P);
            }
            int S7 = 0;
            if (!S7P.isEmpty()) {
              S7 = Integer.parseInt(S7P);
            }
            int S8 = 0;
            if (!S8P.equalsIgnoreCase("")) {
              S8 = Integer.parseInt(S8P);
            }
            int S9 = 0;
            if (!S9P.equalsIgnoreCase("")) {
              S9 = Integer.parseInt(S9P);
            }
            int S10 = 0;
            if (!S10P.equalsIgnoreCase("")) {
              S10 = Integer.parseInt(S10P);
            }
            int S11 = 0;
            if (!S11P.equalsIgnoreCase("")) {
              S11 = Integer.parseInt(S11P);
            }
            int S12 = 0;
            if (!S12P.equalsIgnoreCase("")) {
              S12 = Integer.parseInt(S12P);
            }
            int S13 = 0;
            if (!S13P.equalsIgnoreCase("")) {
              S13 = Integer.parseInt(S13P);
            }
            int S14 = 0;
            if (!S14P.equalsIgnoreCase("")) {
              S14 = Integer.parseInt(S14P);
            }
            GroupA = S1 + S2 + S3;
            GroupD = S10 + S11 + S12 + S13 + S14;
            if ((S4 <= S5) && (S4 <= S6))
            {
              FirstOfGroupBPicked = S5 + S6;
              GroupBRemainder = S4;
            }
            else if ((S5 <= S4) && (S5 <= S6))
            {
              FirstOfGroupBPicked = S4 + S6;
              GroupBRemainder = S5;
            }
            else if ((S6 <= S4) && (S6 <= S5))
            {
              FirstOfGroupBPicked = S4 + S5;
              GroupBRemainder = S6;
            }
            if ((S7 >= S8) && (S7 >= S9))
            {
              FirstOfGroupCPicked = S7;
              GroupCRemainders = S8 + S9;
            }
            else if ((S8 >= S7) && (S8 >= S9))
            {
              FirstOfGroupCPicked = S8;
              GroupCRemainders = S7 + S9;
            }
            else if ((S9 >= S7) && (S9 >= S8))
            {
              FirstOfGroupCPicked = S9;
              GroupCRemainders = S7 + S8;
            }
            if (GroupCRemainders >= GroupD) {
              GroupCRemaindersToGroupD = GroupCRemainders;
            } else if (GroupD >= GroupCRemainders) {
              GroupCRemaindersToGroupD = GroupD;
            }
            if ((GroupBRemainder <= FirstOfGroupCPicked) && (GroupBRemainder <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = FirstOfGroupCPicked + GroupCRemaindersToGroupD;
            } else if ((FirstOfGroupCPicked <= GroupBRemainder) && (FirstOfGroupCPicked <= GroupCRemaindersToGroupD)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + GroupCRemaindersToGroupD;
            } else if ((GroupCRemaindersToGroupD <= GroupBRemainder) && (GroupCRemaindersToGroupD <= FirstOfGroupCPicked)) {
              GroupBRemainderToGroupCToGroupD = GroupBRemainder + FirstOfGroupCPicked;
            }
            int Points = GroupA + FirstOfGroupBPicked + GroupBRemainderToGroupCToGroupD;
            sql = ("UPDATE students_exams SET TotalMarks='" + Points + "',TotalOutOf='84' WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')");
            pst = Conn.prepareStatement(sql);
            pst.executeUpdate();
          }
        }
      }
      catch (HeadlessException|NumberFormatException|SQLException e){
        e.printStackTrace();
      }
    }
    private void UpperFormSumtheEndTermMeanPoints(){
      try{
        lblProgress2.setText("Calculating End Term Mean Points");
        sql = "UPDATE students_exams SET MeanPoints=Round((TotalMarks / 7.0),2) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND TERM=?)";
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void UpperFormEndTermMeanGrade(){
      try{
        lblProgress2.setText("Calculating End Term Mean Grades");
        sql = "UPDATE students_exams SET MeanGrade=(CASE WHEN (MeanPoints+0)>11.43 THEN 'A' WHEN (MeanPoints+0)>10.43 THEN 'A-' WHEN (MeanPoints+0)>9.43 THEN 'B+' WHEN (MeanPoints+0)>8.43 THEN 'B' WHEN (MeanPoints+0)>7.43 THEN 'B-' WHEN (MeanPoints+0)>6.43 THEN 'C+' WHEN (MeanPoints+0)>5.43 THEN 'C' WHEN (MeanPoints+0)>4.43 THEN 'C-' WHEN (MeanPoints+0)>3.43 THEN 'D+' WHEN (MeanPoints+0)>2.43 THEN 'D' WHEN (MeanPoints+0)>1.43 THEN 'D-' WHEN (MeanPoints+0)>0 THEN 'E' ELSE 'X' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
        }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }

    private void populateExamPosition(){    
        try{
          if (Exam.equalsIgnoreCase("Exam 1")){
            for(int i=0;i<ClassesList.size();i++){
                Form = ClassesList.get(i).toString();
                lblProgress.setText("Populating Exam Position for :" + Form);
                lblProgress2.setText("Populating Exam Position for :" + Form);
                String Exam1StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam1TotalMarks + 0 > Students_exams.Exam1TotalMarks + 0 AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String Exam1OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam1TotalMarks + 0  > Students_exams.Exam1TotalMarks + 0 AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String query = "UPDATE students_exams SET Exam1StreamPosition=" + Exam1StreamPosition + ",Exam1OverallPosition=" + Exam1OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                pst = Conn.prepareStatement(query);
                pst.executeUpdate();
            }            
          }else if (Exam.equalsIgnoreCase("Exam 2")){
            for(int i=0;i<ClassesList.size();i++){
                Form = ClassesList.get(i).toString();
                lblProgress.setText("Populating Exam Position for :" + Form);
                lblProgress2.setText("Populating Exam Position for :" + Form);
                String Exam2StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam2TotalMarks + 0 > Students_exams.Exam2TotalMarks + 0 AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String Exam2OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam2TotalMarks + 0  > Students_exams.Exam2TotalMarks + 0 AND substr(SE_StudentClass,1,1)='" + classnumber+ "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String query = "UPDATE students_exams SET Exam2StreamPosition=" + Exam2StreamPosition + ",Exam2OverallPosition=" + Exam2OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                pst = Conn.prepareStatement(query);
                pst.executeUpdate();
            }
        } else if (Exam.equalsIgnoreCase("Exam 3")){
            for(int i=0;i<ClassesList.size();i++){
                Form = ClassesList.get(i).toString();
                lblProgress.setText("Populating Exam Position for :" + Form);
                lblProgress2.setText("Populating Exam Position for :" + Form);
                String Exam3StreamPosition = "(1+(SELECT COUNT(*) FROM students_exams As t2 WHERE  (t2.Exam3TotalMarks + 0 > Students_exams.Exam3TotalMarks + 0 AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String Exam3OverallPosition = "(1+(SELECT COUNT(*) FROM students_exams  As t2 WHERE (t2.Exam3TotalMarks + 0  > Students_exams.Exam3TotalMarks + 0 AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String query = "UPDATE students_exams SET Exam3StreamPosition=" + Exam3StreamPosition + ",Exam3OverallPosition=" + Exam3OverallPosition + " WHERE  (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                pst = Conn.prepareStatement(query);
                pst.executeUpdate();
            }  
        }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    private void EndTermPosition(){
        try{
            for(int i=0;i<ClassesList.size();i++){
                Form = ClassesList.get(i).toString();
                lblProgress.setText("Populating end term position for :" + Form);
                lblProgress2.setText("Populating end term position for :" + Form);
                String StreamPosition = "(1+(SELECT Count(*) FROM students_exams AS t2 WHERE (t2.TotalMarks + 0 > students_exams.TotalMarks + 0 AND SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String OverallPosition = "(1+(SELECT Count(*) FROM students_exams AS t2 WHERE (t2.TotalMarks + 0 >students_exams.TotalMarks + 0 AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')))";
                String StreamOutOf = "(SELECT Count(*) FROM students_exams WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "'))";
                String OverallOutOf = "(SELECT Count(*) FROM students_exams WHERE (substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "'))";

                String query = "UPDATE students_exams SET StreamPosition=" + StreamPosition + ",StreamOutOf=" + StreamOutOf + ",OverallPosition=" + OverallPosition + ",OverallOutOf=" + OverallOutOf + " WHERE (SE_StudentClass='" + Form + "' AND Year='" + Year + "' AND Term='" + Term + "')";
                pst = Conn.prepareStatement(query);
                pst.executeUpdate();
            }
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    private void populateEndTermRemarks(){
      try{
        lblProgress.setText("Remarking Students perfomance...");
        lblProgress2.setText("Remarking Students perfomance...");
        sql = "UPDATE students_exams SET OverallRemarks=(CASE WHEN (MeanPoints+0)>9.5 THEN 'This is a wonderful performance. Keep working on your strengths to be able to maintain it or achieve even better results' WHEN (MeanPoints+0)>7.5 THEN 'This is work well done, continue working on your weak subjects. You have the potential and can achieve better results' WHEN (MeanPoints+0)>5.5 THEN 'This is an average performance. Consult your teachers on the various subjects. You can do better than this, work harder next term.' ELSE 'Your performance is below average. Better performance is expected of you. Work hard to improve your grades.' END) WHERE (substr(SE_StudentClass,1,1)=? AND Year=? AND Term=?)";
        pst = Conn.prepareStatement(sql);
        pst.setString(1, classnumber);pst.setString(2, Year);pst.setString(3, Term);
        pst.executeUpdate();
      }catch (SQLException e){
        e.printStackTrace();
      }
    }
    
    
    private void SaveMarksToTermlyPerfomanceOnStudentsTable(){
      try{
        for (int row = 0; row < tableStudentMarks.getRowCount(); row++){
          String StudentID = tableStudentMarks.getValueAt(row, 0).toString();
          lblProgress.setText("Updating Termly perfomance for: "+ tableStudentMarks.getValueAt(row, 1).toString() +" ADM: "+ tableStudentMarks.getValueAt(row, 0).toString() );
          lblProgress2.setText("Updating Termly perfomance for: "+ tableStudentMarks.getValueAt(row, 1).toString() +" ADM: "+ tableStudentMarks.getValueAt(row, 0).toString() );
          sql = ("SELECT MeanPoints,MeanGrade,OverallPosition,OverallOutOf FROM students_exams WHERE (SE_Student_id='" + StudentID + "' AND substr(SE_StudentClass,1,1)='" + classnumber + "' AND Year='" + Year + "' AND Term='" + Term + "')");
          pst = Conn.prepareStatement(sql);
          rs = pst.executeQuery();
          if (rs.next())
          {
            String Points = rs.getString("MeanPoints");
            String Grade = rs.getString("MeanGrade");
            String Position = rs.getString("OverallPosition");
            String Overall = rs.getString("OverallOutOf");
            if ((classnumber.equalsIgnoreCase("1")) && (Term.equalsIgnoreCase("Term 1")))
            {
              sql = ("UPDATE student_details SET F1T1M='" + Points + "',F1T1MG = '"+Grade+"', F1T1OP='"+Overall+"', F1T1P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("1")) && (Term.equalsIgnoreCase("Term 2")))
            {
              sql = ("UPDATE student_details SET F1T2M='" + Points + "', F1T2MG = '"+Grade+"', F1T2OP='"+Overall+"', F1T2P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("1")) && (Term.equalsIgnoreCase("Term 3")))
            {
              sql = ("UPDATE student_details SET F1T3M='" + Points + "',F1T3MG = '"+Grade+"', F1T3OP='"+Overall+"', F1T3P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("2")) && (Term.equalsIgnoreCase("Term 1")))
            {
              sql = ("UPDATE student_details SET F2T1M='" + Points + "',F2T1MG = '"+Grade+"', F2T1OP='"+Overall+"', F2T1P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("2")) && (Term.equalsIgnoreCase("Term 2")))
            {
              sql = ("UPDATE student_details SET F2T2M='" + Points + "', F2T2MG = '"+Grade+"', F2T2OP='"+Overall+"', F2T2P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("2")) && (Term.equalsIgnoreCase("Term 3")))
            {
              sql = ("UPDATE student_details SET F2T3M='" + Points + "',F2T3MG = '"+Grade+"', F2T3OP='"+Overall+"', F2T3P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("3")) && (Term.equalsIgnoreCase("Term 1")))
            {
              sql = ("UPDATE student_details SET F3T1M='" + Points + "',F3T1MG = '"+Grade+"', F3T1OP='"+Overall+"', F3T1P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("3")) && (Term.equalsIgnoreCase("Term 2")))
            {
              sql = ("UPDATE student_details SET F3T2M='" + Points + "',F3T2MG = '"+Grade+"', F3T2OP='"+Overall+"', F3T2P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("3")) && (Term.equalsIgnoreCase("Term 3")))
            {
              sql = ("UPDATE student_details SET F3T3M='" + Points + "',F3T3MG = '"+Grade+"', F3T3OP='"+Overall+"', F3T3P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("4")) && (Term.equalsIgnoreCase("Term 1")))
            {
              sql = ("UPDATE student_details SET F4T1M='" + Points + "', F4T1MG = '"+Grade+"', F4T1OP='"+Overall+"', F4T1P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("4")) && (Term.equalsIgnoreCase("Term 2")))
            {
              sql = ("UPDATE student_details SET F4T2M='" + Points + "', F4T2MG = '"+Grade+"', F4T2OP='"+Overall+"', F4T2P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
            else if ((classnumber.equalsIgnoreCase("4")) && (Term.equalsIgnoreCase("Term 3")))
            {
              sql = ("UPDATE student_details SET F4T3M='" + Points + "', F4T3MG = '"+Grade+"', F4T3OP='"+Overall+"', F4T3P='" + Position + "' WHERE student_id='" + StudentID + "'");
              pst = Conn.prepareStatement(sql);
              pst.executeUpdate();
            }
          }
        }
        lblProgress.setText("Refreshing...");
        lblProgress2.setText("Refreshing...");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }

    
    private void AnalyzeLowerForms(){
      SwingWorker<Void, Integer> worker = new SwingWorker(){
        @Override
        protected Void doInBackground()
          throws Exception{
          AnalysisModeDlg.dispose();
          AnalysisProg.setIndeterminate(true);
          
          PopulateSubjectMeanMarks();
          PopulateSubjectMeanPoints();
          PopulateSubjectMeanGrade();
          PopulateStudentSubjectPositions();
          PopulateSubjectRemarks();

          LowerFormSumtheExamTotalMarks();
          LowerFormSumtheExamMeanPoints();
          LowerFormpopulateExamMeanGrade();

          LowerFormSumtheEndTermTotalMarks();
          LowerFormSumtheEndTermMeanPoints();
          LowerFormEndTermMeanGrade();

          populateExamPosition();

          EndTermPosition();
          populateEndTermRemarks();
          SaveMarksToTermlyPerfomanceOnStudentsTable();
          return null;
        }
        
        @Override
        protected void done(){
          AnalysisProg.setIndeterminate(false);
          AnalysisProg.setValue(0);
          t.start();
        }
      };
      worker.execute();
    }
    private void AnalyzeUpperForms(){
      SwingWorker<Void, Integer> worker = new SwingWorker(){
        @Override
        protected Void doInBackground()throws Exception{
          AnalysisModeDlg.dispose();
          AnalysisProg1.setIndeterminate(true);
          
          PopulateSubjectMeanMarks();
          PopulateSubjectMeanPoints();
          PopulateSubjectMeanGrade();
          PopulateStudentSubjectPositions();
          PopulateSubjectRemarks();
          if (Exam.equalsIgnoreCase("Exam 1")) {
            UpperFormSumtheExam1TotalPoints();
          } else if (Exam.equalsIgnoreCase("Exam 2")) {
            UpperFormSumtheExam2TotalPoints();
          } else if (Exam.equalsIgnoreCase("Exam 3")) {
            UpperFormSumtheExam3TotalPoints();
          }
          UpperFormSumtheExamMeanPoints();
          UpperFormExamMeanGrade();

          UpperFormSumtheEndTermTotalMarks();
          UpperFormSumtheEndTermMeanPoints();
          UpperFormEndTermMeanGrade();

          populateExamPosition();

          EndTermPosition();
          populateEndTermRemarks();
          SaveMarksToTermlyPerfomanceOnStudentsTable();
          return null;
        }

        @Override
        protected void done(){
          AnalysisProg1.setIndeterminate(false);
          AnalysisProg1.setValue(0);
          t.start();
        }
      };
      worker.execute();
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LowerFormsProgressDlg = new javax.swing.JDialog();
        AnalysisProg = new javax.swing.JProgressBar();
        lblProgress = new javax.swing.JLabel();
        UpperFormsProgressDlg = new javax.swing.JDialog();
        AnalysisProg1 = new javax.swing.JProgressBar();
        lblProgress2 = new javax.swing.JLabel();
        AnalysisModeDlg = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnExamResult = new javax.swing.JButton();
        btnAnalyze = new javax.swing.JButton();
        comboForm = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboExam = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        btnTermResult = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableStudentMarks = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableStudentMarks1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboTerm = new javax.swing.JComboBox<>();
        btnExportToExcel = new javax.swing.JButton();
        cmbYear = new javax.swing.JComboBox<>();

        LowerFormsProgressDlg.setModal(true);
        LowerFormsProgressDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                LowerFormsProgressDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblProgress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProgress.setForeground(new java.awt.Color(0, 153, 0));
        lblProgress.setText("Analysing Exams please wait . . .");

        javax.swing.GroupLayout LowerFormsProgressDlgLayout = new javax.swing.GroupLayout(LowerFormsProgressDlg.getContentPane());
        LowerFormsProgressDlg.getContentPane().setLayout(LowerFormsProgressDlgLayout);
        LowerFormsProgressDlgLayout.setHorizontalGroup(
            LowerFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LowerFormsProgressDlgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LowerFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AnalysisProg, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addComponent(lblProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        LowerFormsProgressDlgLayout.setVerticalGroup(
            LowerFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LowerFormsProgressDlgLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(lblProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnalysisProg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        UpperFormsProgressDlg.setModal(true);
        UpperFormsProgressDlg.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                UpperFormsProgressDlgWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        lblProgress2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblProgress2.setForeground(new java.awt.Color(0, 153, 0));
        lblProgress2.setText("Analysing Exams please wait . . .");

        javax.swing.GroupLayout UpperFormsProgressDlgLayout = new javax.swing.GroupLayout(UpperFormsProgressDlg.getContentPane());
        UpperFormsProgressDlg.getContentPane().setLayout(UpperFormsProgressDlgLayout);
        UpperFormsProgressDlgLayout.setHorizontalGroup(
            UpperFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpperFormsProgressDlgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UpperFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AnalysisProg1, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addComponent(lblProgress2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        UpperFormsProgressDlgLayout.setVerticalGroup(
            UpperFormsProgressDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UpperFormsProgressDlgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProgress2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AnalysisProg1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        AnalysisModeDlg.setModal(true);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("11 Subjects Analysis");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("7 Subjects Analysis");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 204, 0));

        jLabel3.setFont(new java.awt.Font("Old English Text MT", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Set the Analisys Type for this Class");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout AnalysisModeDlgLayout = new javax.swing.GroupLayout(AnalysisModeDlg.getContentPane());
        AnalysisModeDlg.getContentPane().setLayout(AnalysisModeDlgLayout);
        AnalysisModeDlgLayout.setHorizontalGroup(
            AnalysisModeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AnalysisModeDlgLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnalysisModeDlgLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AnalysisModeDlgLayout.setVerticalGroup(
            AnalysisModeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnalysisModeDlgLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AnalysisModeDlgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnExamResult.setText("Exam Result");
        btnExamResult.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnExamResult.setContentAreaFilled(false);
        btnExamResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExamResultActionPerformed(evt);
            }
        });

        btnAnalyze.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAnalyze.setText("Analyze");
        btnAnalyze.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnAnalyze.setContentAreaFilled(false);
        btnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyzeActionPerformed(evt);
            }
        });

        comboForm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Form", "Form 1", "Form 2", "Form 3", "Form 4" }));
        comboForm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFormPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("FORM");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("EXAMINATION");

        comboExam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboExam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "EXAM 1", "EXAM 2", "EXAM 3" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("TERM");

        btnTermResult.setText("Endterm Result");
        btnTermResult.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnTermResult.setContentAreaFilled(false);
        btnTermResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTermResultActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("YEAR");

        tableStudentMarks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADM NO", "NAME", "CLASS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStudentMarks.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableStudentMarks);
        if (tableStudentMarks.getColumnModel().getColumnCount() > 0) {
            tableStudentMarks.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableStudentMarks.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        tableStudentMarks1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ADM NO", "NAME", "CLASS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableStudentMarks1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableStudentMarks1);
        if (tableStudentMarks1.getColumnModel().getColumnCount() > 0) {
            tableStudentMarks1.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableStudentMarks1.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jPanel1.setBackground(new java.awt.Color(0, 204, 0));

        jLabel1.setFont(new java.awt.Font("Old English Text MT", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Marks Analysis");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        comboTerm.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        comboTerm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT", "TERM 1", "TERM 2", "TERM 3" }));
        comboTerm.setToolTipText("");
        comboTerm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboTermPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        btnExportToExcel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExportToExcel.setText("Export to SMS File");
        btnExportToExcel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 0), 2, true));
        btnExportToExcel.setContentAreaFilled(false);
        btnExportToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportToExcelActionPerformed(evt);
            }
        });

        cmbYear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECT" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnExamResult, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTermResult, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExportToExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 47, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExportToExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnExamResult, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnTermResult, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAnalyze, btnExportToExcel});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(590, 509));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalyzeActionPerformed
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
        Exam = comboExam.getSelectedItem().toString();
        if (Form.equalsIgnoreCase("Select Form"))
        {
          JOptionPane.showMessageDialog(null, "Select a Form from the dropdown", "Error", 1);
        }else if (Year.equalsIgnoreCase("SELECT")){
          JOptionPane.showMessageDialog(null, "Select the Year of examination", "Error", 1);
        }else if (Term.equalsIgnoreCase("SELECT")){
          JOptionPane.showMessageDialog(null, "Select a Term from the dropdown", "Error", 1);
        }else if (Exam.equalsIgnoreCase("SELECT")){
          JOptionPane.showMessageDialog(null, "Select a the Examination from the dropdown", "Error", 1);
        }else{
          getToolkit().beep();
          int res = JOptionPane.showConfirmDialog(null, "Analysing " + Exam + " will affect the End Term Perfomance. Do you want to proceed?", "acme", 0);
          if (res == 0){   
              switch(Form){
                case "Form 1":
                    classnumber = "1";
                    break;
                case "Form 2":
                    classnumber = "2";
                    break;
                case "Form 3":
                    classnumber = "3";
                    break;
                case "Form 4":
                    classnumber = "4";
                    break;
            }
            GetSpecificClassesInThisForm(classnumber); 
            AnalysisModeDlg.pack();
            AnalysisModeDlg.setLocationRelativeTo(null);
            AnalysisModeDlg.setVisible(true);
          }
        }
    }//GEN-LAST:event_btnAnalyzeActionPerformed

    private void LowerFormsProgressDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_LowerFormsProgressDlgWindowGainedFocus
         AnalyzeLowerForms();
    }//GEN-LAST:event_LowerFormsProgressDlgWindowGainedFocus

    private void btnExportToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToExcelActionPerformed
        JFileChooser fc = new JFileChooser();
        int option = fc.showSaveDialog(this);
        if (option == 0)
        {
          String filename = fc.getSelectedFile().getName();
          String path = fc.getSelectedFile().getParentFile().getPath();
          ExportTableToCSV.ExportTable(tableStudentMarks,filename,path);
        }
    }//GEN-LAST:event_btnExportToExcelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Conn.close();
       }catch(SQLException e){
           System.out.println(e);
       }
        this.dispose();
        new AdminPanelFrm().setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnExamResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExamResultActionPerformed
        getExamMarksForExport();
    }//GEN-LAST:event_btnExamResultActionPerformed

    private void btnTermResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTermResultActionPerformed
        getEndTermMarksForExport();
    }//GEN-LAST:event_btnTermResultActionPerformed

    private void UpperFormsProgressDlgWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_UpperFormsProgressDlgWindowGainedFocus
        AnalyzeUpperForms();
    }//GEN-LAST:event_UpperFormsProgressDlgWindowGainedFocus

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AnalysisModeDlg.dispose();
        AnalysisProg.setIndeterminate(true);
        LowerFormsProgressDlg.pack();
        LowerFormsProgressDlg.setLocationRelativeTo(null);
        LowerFormsProgressDlg.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        AnalysisModeDlg.dispose();
        AnalysisProg.setIndeterminate(true);
        UpperFormsProgressDlg.pack();
        UpperFormsProgressDlg.setLocationRelativeTo(null);
        UpperFormsProgressDlg.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void comboTermPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboTermPopupMenuWillBecomeInvisible
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
         switch(Form){
            case "Form 1":
                classnumber = "1";
                break;
            case "Form 2":
                classnumber = "2";
                break;
            case "Form 3":
                classnumber = "3";
                break;
            case "Form 4":
                classnumber = "4";
                break;
        }
        UpdateTableFromStudentsExamsTable();
    }//GEN-LAST:event_comboTermPopupMenuWillBecomeInvisible

    private void comboFormPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboFormPopupMenuWillBecomeInvisible
        Form = comboForm.getSelectedItem().toString();
        Year = cmbYear.getSelectedItem().toString();
        Term = comboTerm.getSelectedItem().toString();
         switch(Form){
            case "Form 1":
                classnumber = "1";
                break;
            case "Form 2":
                classnumber = "2";
                break;
            case "Form 3":
                classnumber = "3";
                break;
            case "Form 4":
                classnumber = "4";
                break;
            default:
                break;
        }
        UpdateTableFromStudentsExamsTable();
    }//GEN-LAST:event_comboFormPopupMenuWillBecomeInvisible

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarksAnalysisFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MarksAnalysisFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AnalysisModeDlg;
    private javax.swing.JProgressBar AnalysisProg;
    private javax.swing.JProgressBar AnalysisProg1;
    private javax.swing.JDialog LowerFormsProgressDlg;
    private javax.swing.JDialog UpperFormsProgressDlg;
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JButton btnExamResult;
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnTermResult;
    private javax.swing.JComboBox<String> cmbYear;
    private javax.swing.JComboBox<String> comboExam;
    private javax.swing.JComboBox<String> comboForm;
    private javax.swing.JComboBox<String> comboTerm;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblProgress;
    private javax.swing.JLabel lblProgress2;
    private javax.swing.JTable tableStudentMarks;
    private javax.swing.JTable tableStudentMarks1;
    // End of variables declaration//GEN-END:variables
}
