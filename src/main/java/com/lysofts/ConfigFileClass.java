package com.lysofts;

import com.lysofts.utils.ConnClass;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ConfigFileClass {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public File config = new File("C:/Acme/Exam System/Config.txt");
    public File db_update = new File("C:/Acme/Exam System/databaseupdate.sql");
    public File agreement = new File("C:/Acme/Exam System/Agreement.txt");
    String propsFile = "C:/Acme/Exam System/config.properties";
    Preferences prefsRoot = Preferences.systemRoot();
    Preferences myPrefs = prefsRoot.node("lysofts_exams_system");
    
    public ConfigFileClass() {
        
    }

    public void CreateFile(File file, String text) {
        try {
            if (!file.exists()) {
                FileWriter wr = new FileWriter(file);
                wr.write(text);// date installed    
                wr.close();
                file.setReadOnly();
            }
        } catch (IOException e) {
        }

    }

    public void DeleteFile(File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String ReadFile(File file) {
        String readString = "";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                String Line;
                while (((Line = br.readLine()) != null)) {
                    readString += Line;
                }
                br.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (FileNotFoundException e) {
        }
        return readString;
    }

    public boolean fileContainsDate() {
        try {
            String InstalledDate = ReadFile(config);

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

            dateFormat.parse(InstalledDate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String ReadDatabaseUpdateFile() {
        return ReadFile(db_update);
    }

    public void AgreeToTerms() {
        myPrefs.putBoolean("agreed_to_update",true);
        myPrefs.putInt("activation_state", 0);
    }


    public boolean AgreedToTerms() {
        return myPrefs.getBoolean("agreed_to_update",false);
    }

    public long TodayDateStamp() {
        return new Date().getTime();//todays date in milliseconds
    }

    public long TrialDateStamp() {
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusDays(35);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return date.getTime();//todays date + 35 Days
    }

    public void CreatePreferences() {
        try {
            if(prefsRoot.nodeExists("lysofts_exams_system")){       
                //System.out.println("Node already Exists");
                return;
            }
            myPrefs.putBoolean("new_installation", false);//change to true for new installation setups
            myPrefs.putInt("activation_state", 0);
            myPrefs.putLong("installation_date", TodayDateStamp());
            myPrefs.putLong("trial_deadline", 0);
            myPrefs.putBoolean("purchased", false);
            myPrefs.putBoolean("agreed_to_update", false);          
            System.out.println("Prefs created");
        } catch (BackingStoreException ex) {
            ConnClass.printError(ex);
        }
    }
    
    public boolean activeState(int state){
        System.out.println(myPrefs.getInt("activation_state", 0));
        return myPrefs.getInt("activation_state", 0)==state;
    }
    
    public void setTrialDeadline() {
        myPrefs.putLong("trial_deadline", TrialDateStamp()); 
        myPrefs.putInt("activation_state", 1);
    }
    
    public boolean isPeriodEnded() {
        System.out.println("Millis Difference: " + (myPrefs.getLong("trial_deadline", 0) - TodayDateStamp()));
        return (myPrefs.getLong("trial_deadline", 0) - TodayDateStamp())<0;
    }
    
    public void setTrialEnded() {
        myPrefs.putInt("activation_state", 2);
    }
    
    public boolean isNewInstallation() {
        return myPrefs.getBoolean("new_installation",true);
    }
    
    public void ActivateSystem(){
        myPrefs.putInt("activation_state", 3);    
    }

}
