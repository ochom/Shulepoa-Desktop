/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mspace-dev
 */
@Entity
@Table(name="student_details")
public class Student implements Serializable {   
    
    @Id            
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    
    @Column(name = "Student_ID")
    String regNumber;
    
    @Column(name = "Student_name")
    String name;
    
    @Column(name = "Student_Class")
    String classroom;
    
    @Column(name = "kcpe_marks")
    String kcpeMarks;
    
    @Column(name = "kcpe_grade")
    String kcpeGrade;
    
    @Column(name = "Picture")
    String passport;
    
    @Column(name = "DOA")
    String doa;
    
    @Column(name = "DOB")
    String dob;
    
    @Column(name = "Gender")
    String sex;
    
    @Column(name = "House")
    String house;
    
    @Column(name = "Father")
    String kinName;
    
    @Column(name = "Phone1")
    String kinPhone;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
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
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
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
    
}
