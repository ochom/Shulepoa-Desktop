
package com.lysofts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;


public class ExportTableToCSV {
   public static boolean ExportTable(JTable TableToExport,String filename,String path){
       int len = filename.length();
        String ext = "";
        String file;
        if (len > 4) {
          ext = filename.substring(len - 4, len);
        }
        if (ext.equals(".csv")) {
          file = path + "\\" + filename;
        } else {
          file = path + "\\" + filename + ".csv";
        }
       try{
           TableModel model = TableToExport.getModel();
           FileWriter csv = new FileWriter(new File(file));
           for(int i=0;i<model.getColumnCount();i++){
               csv.write(model.getColumnName(i)+",");
           }
           csv.write("\n");
           for(int i=0;i<model.getRowCount();i++){
               for(int j=0;j<model.getColumnCount();j++){
                   if(!(model.getValueAt(i, 0).toString()==null || model.getValueAt(i, 0).toString().isEmpty())){
                       csv.write(String.valueOf(model.getValueAt(i, j))+",");
                   }                   
               }
               csv.write("\n");
           }
           csv.close();
           JOptionPane.showMessageDialog(null, "File saved succesfully", "acme", JOptionPane.INFORMATION_MESSAGE);
           return true;
       }catch(IOException e){
           System.out.println(e);
           return false;
       }       
   } 
}
