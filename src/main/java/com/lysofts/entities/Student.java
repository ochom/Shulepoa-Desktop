/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author mspace-dev
 */
@Entity
public class Student implements Serializable {   
    
    @Id
    int id;
    String regNo;
    String name;
    String classroom;
    String kcpeMarks;
    String kcpeGrade;
    String passport;
    String doa;
    String dob;
    String Sex;
    String House;
    String kinName;
    String kinPhone;
    String kinRelationship;
    String perfomanceHistory;

    public Student() {
    }

    public Student(String regNo, String name, String classroom, String kcpeMarks, String kcpeGrade, String passport, String doa, String dob, String Sex, String House, String kinName, String kinPhone, String kinRelationship, String perfomanceHistory) {
        this.regNo = regNo;
        this.name = name;
        this.classroom = classroom;
        this.kcpeMarks = kcpeMarks;
        this.kcpeGrade = kcpeGrade;
        this.passport = passport;
        this.doa = doa;
        this.dob = dob;
        this.Sex = Sex;
        this.House = House;
        this.kinName = kinName;
        this.kinPhone = kinPhone;
        this.kinRelationship = kinRelationship;
        this.perfomanceHistory = perfomanceHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getKcpeMarks() {
        return kcpeMarks;
    }

    public void setKcpeMarks(String kcpeMarks) {
        this.kcpeMarks = kcpeMarks;
    }

    public String getKcpeGrade() {
        return kcpeGrade;
    }

    public void setKcpeGrade(String kcpeGrade) {
        this.kcpeGrade = kcpeGrade;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String House) {
        this.House = House;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinPhone() {
        return kinPhone;
    }

    public void setKinPhone(String kinPhone) {
        this.kinPhone = kinPhone;
    }

    public String getKinRelationship() {
        return kinRelationship;
    }

    public void setKinRelationship(String kinRelationship) {
        this.kinRelationship = kinRelationship;
    }

    public String getPerfomanceHistory() {
        return perfomanceHistory;
    }

    public void setPerfomanceHistory(String perfomanceHistory) {
        this.perfomanceHistory = perfomanceHistory;
    }
}
