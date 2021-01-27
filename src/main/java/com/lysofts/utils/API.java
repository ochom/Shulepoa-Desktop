/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.utils;

import static com.lysofts.utils.ConnClass.API_END;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 *
 * @author mspace-dev
 */
public class API {

    public boolean register(String schoolName, String username, String email, String phone, String password) {
        boolean success = false;
        try{            
            String json = "{\"schoolName\":\""+schoolName+"\",\"username\":\""+username+"\",\"email\":\""+email+"\",\"phone\":\""+phone+"\",\"password\":\""+password+"\"}";
            System.out.println(json);
            byte[] out = json.getBytes();
            int length = out.length;
            
            URL url = new URL(API_END+"register/");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            
            try(InputStream is = http.getInputStream()) {            
                Scanner s = new Scanner(is).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                //System.out.println(result);
            }
            
            return http.getResponseCode()==201;            
        }catch(IOException ex){
            ConnClass.printError(ex);
        }
        return success;
    }

    public boolean login(String username, String password) {
         boolean success = false;
        try{String json = "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
            System.out.println(json);
            byte[] out = json.getBytes();
            int length = out.length;
            
            URL url = new URL(API_END+"login/");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            try(InputStream is = http.getInputStream()) {            
                Scanner s = new Scanner(is).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                //System.out.println(result);
            }
            
            return http.getResponseCode()==200;           
        }catch(IOException ex){
            ConnClass.printError(ex);
        }
        return success;
    }
    
}
