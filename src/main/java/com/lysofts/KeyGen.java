/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts;

import java.util.Scanner;

/**
 *
 * @author mspace-dev
 */
public class KeyGen {
    public static void main(String[] args) {
        System.out.println("Enter SChool Name:");
        Scanner sc = new Scanner(System.in);
        String schoolName = sc.nextLine();
        generateKey(schoolName);
    }

    private static void generateKey(String schoolName) {
        schoolName = schoolName.toUpperCase().replaceAll("\\.", "").replaceAll("\\'", "").replaceAll(" ", "");
        String sk1 = "";
        String sk2 = "";
        String sk3 = "";
        String sk4 = "";
        String sk5 = "";
        String sk6 = "";
        String sk7 = "";
        String sk8 = "";

        if (!schoolName.isEmpty()) {
            sk1 = schoolName.substring(1, 2);// 2nd char
            sk2 = schoolName.substring(3, 4);
            sk3 = schoolName.substring(2, 3);
            sk4 = schoolName.substring(5, 6);
            sk5 = schoolName.substring(0, 1);
            sk6 = schoolName.substring(7, 8);
            sk7 = schoolName.substring(3, 4);
            sk8 = schoolName.substring(4, 5);
        }

        String newKey = "L" + sk2 + "A" + sk3 + "-" + sk5 + "POH-" + sk4 + sk6 + "8R-" + sk7 + sk8 + sk1 + "S";
        System.out.println("New Key:" + newKey);

        String updateKey = "K" + sk2 + "LC-" + sk3 + sk5 + "AC-5" + sk4 + sk6 + "P-" + sk7 + sk8 + sk1 + "J";
        System.out.println("Update Key:" + updateKey);

    }
}
