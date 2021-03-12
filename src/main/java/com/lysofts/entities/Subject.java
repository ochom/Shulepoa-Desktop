/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import com.lysofts.annotations.Column;
import com.lysofts.annotations.Table;


/**
 *
 * @author mspace-dev
 */
@Table(name = "subjects")
public class Subject {    
    
    @Column(name ="Subject_id")
    private String id;

    @Column(name = "S_NO")
    private String number;

    @Column(name = "Subject_Code")
    private String code;

    @Column(name = "Subject_name")
    private String name;

    @Column(name = "Subject_group")
    private String group="";


    @Column(name = "Grade1")
    private String grade1;

    @Column(name = "Grade2")
    private String grade2;


    @Column(name = "Grade3")
    private String grade3;


    @Column(name = "Grade4")
    private String grade4;


    @Column(name = "Grade5")
    private String grade5;


    @Column(name = "Grade6")
    private String grade6;


    @Column(name = "Grade7")
    private String grade7;


    @Column(name = "Grade8")
    private String grade8;


    @Column(name = "Grade9")
    private String grade9;


    @Column(name = "Grade10")
    private String grade10;


    @Column(name = "Grade11")
    private String grade11;


    @Column(name = "Grade12")
    private String grade12;

    public Subject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = "";
    }

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public String getGrade3() {
        return grade3;
    }

    public void setGrade3(String grade3) {
        this.grade3 = grade3;
    }

    public String getGrade4() {
        return grade4;
    }

    public void setGrade4(String grade4) {
        this.grade4 = grade4;
    }

    public String getGrade5() {
        return grade5;
    }

    public void setGrade5(String grade5) {
        this.grade5 = grade5;
    }

    public String getGrade6() {
        return grade6;
    }

    public void setGrade6(String grade6) {
        this.grade6 = grade6;
    }

    public String getGrade7() {
        return grade7;
    }

    public void setGrade7(String grade7) {
        this.grade7 = grade7;
    }

    public String getGrade8() {
        return grade8;
    }

    public void setGrade8(String grade8) {
        this.grade8 = grade8;
    }

    public String getGrade9() {
        return grade9;
    }

    public void setGrade9(String grade9) {
        this.grade9 = grade9;
    }

    public String getGrade10() {
        return grade10;
    }

    public void setGrade10(String grade10) {
        this.grade10 = grade10;
    }

    public String getGrade11() {
        return grade11;
    }

    public void setGrade11(String grade11) {
        this.grade11 = grade11;
    }

    public String getGrade12() {
        return grade12;
    }

    public void setGrade12(String grade12) {
        this.grade12 = grade12;
    }
    
    
}
