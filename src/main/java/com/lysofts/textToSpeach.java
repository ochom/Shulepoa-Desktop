
package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.filechooser.FileSystemView;

public class textToSpeach {
 private static Connection Conn = ConnClass.connectDB();
 private static PreparedStatement pst  =null;
 private static ResultSet rs= null;
 private static ResultSetMetaData rsmd;
 
public static String TableToCSV(ResultSet rst,String backUpPath,String Filename){
    try{
         new File(backUpPath).mkdir();
         String Path  = backUpPath+"/"+Filename;
       /* new File("C:\\Users\\Acme\\Desktop\\Acme CSVs").mkdir();
        String Path  = "C:\\Users\\Acme\\Desktop\\Acme CSVs\\ExampleCSV.csv";*/
        FileWriter csv  =new FileWriter(new File(Path));
        rsmd = rst.getMetaData();
        int Cols = rsmd.getColumnCount();
            for(int i=1; i<Cols+1;i++){
                csv.write(rsmd.getColumnName(i)+";");
            }
          csv.write("\n"); 
        while(rst.next()){
            for(int i=1; i<Cols+1;i++){
                csv.write(String.valueOf(rs.getString(i))+";");
            }
         csv.write("\n");
        } 
       System.out.println("CSV File created at "+Path);
       csv.close();
    }catch(IOException | SQLException e){
        System.out.println(e);
    }
    return null;
 }
 public static String DataFecthedToResultSet(){
    try{
        String defaultDir =  FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Acme/Acme2018";
        String Filename = "Students.csv";
        pst  =Conn.prepareStatement("SELECT * FROM students_exams");
        rs = pst.executeQuery();
        TableToCSV(rs,defaultDir,Filename);
    }catch(SQLException e){
        System.out.println(e);
    }
     
    return null;
 }
    public static void main(String[] args) {
        DataFecthedToResultSet();
    }
}
