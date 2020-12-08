
package com.lysofts;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


public class CompareDates {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static String CurrrentDate = getTodaysDate();
    
    public static int getDateDiff(String InstalledDate){
        InstalledDate = InstalledDate.replaceAll("A", "0");
        InstalledDate = InstalledDate.replaceAll("B", "1");
        InstalledDate = InstalledDate.replaceAll("C", "2");
        InstalledDate = InstalledDate.replaceAll("D", "3");
        InstalledDate = InstalledDate.replaceAll("E", "4");
        InstalledDate = InstalledDate.replaceAll("F", "5");
        InstalledDate = InstalledDate.replaceAll("G", "6");
        InstalledDate = InstalledDate.replaceAll("H", "7");
        InstalledDate = InstalledDate.replaceAll("I", "8");
        InstalledDate = InstalledDate.replaceAll("J", "9");
        InstalledDate = InstalledDate.replaceAll("K", "/");
        InstalledDate = InstalledDate.replaceAll("L", "/");
        int dateDiff;
        try{
            Date D1  = dateFormat.parse(CurrrentDate);
            Date D2  = dateFormat.parse(InstalledDate);
            long datediff  = (D1.getTime() - D2.getTime());
            dateDiff = Integer.parseInt(String.format("%d", TimeUnit.MILLISECONDS.toDays(datediff)));
            //System.out.println(dateDiff);
            return dateDiff;
        }catch(ParseException e){        
            System.out.println(e);
            return 0;
        }  
    }
    public static String getTodaysDate(){
        String CurrentDate;
            LocalDate ld = LocalDate.now();
            int day = ld.getDayOfMonth();
            int month = ld.getMonthValue();
            int year = ld.getYear();       
            CurrentDate = (day+"/"+month+"/"+year);
        return CurrentDate;
    }
     
     public static String SetInstallationDate(){       
        String installDate = CurrrentDate;
         try{
            installDate = installDate.replaceAll("0", "A");
            installDate = installDate.replaceAll("1", "B");
            installDate = installDate.replaceAll("2", "C");
            installDate = installDate.replaceAll("3", "D");
            installDate = installDate.replaceAll("4", "E");
            installDate = installDate.replaceAll("5", "F");
            installDate = installDate.replaceAll("6", "G");
            installDate = installDate.replaceAll("7", "H");
            installDate = installDate.replaceAll("8", "I");
            installDate = installDate.replaceAll("9", "J");
            installDate= installDate.replaceFirst("/", "K");
            installDate = installDate.replaceAll("/", "L");
           return installDate;
        }catch(Exception e){        
            System.out.println(e);
            return null;
        }
     }
}
