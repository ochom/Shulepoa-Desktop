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
 * @author Rick
 */
@Table(name = "student_details")
public class Student {

    private String id;

    @Column(name = "Student_ID")
    private String regNumber;

    @Column(name = "Student_name")
    private String name;

    @Column(name = "Student_Class")
    private String classroom;

    @Column(name = "kcpe_marks")
    private String kcpeMarks;

    @Column(name = "kcpe_grade")
    private String kcpeGrade;

    @Column(name = "Picture")
    private String passport;

    @Column(name = "DOA")
    private String doa;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Gender")
    private String sex;

    @Column(name = "House")
    private String house;

    @Column(name = "Father")
    private String kinName;

    @Column(name = "Phone1")
    private String kinPhone;

    @Column(name = "FeeRequired")
    private String feeReuired;

    @Column(name = "FeePaid")
    private String feePaid;

    @Column(name = "FeeBalance")
    private String feeBalance;

    @Column(name = "SubjectEntries")
    private String subjectEntry;

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFeeReuired() {
        return feeReuired;
    }

    public void setFeeReuired(String feeReuired) {
        this.feeReuired = feeReuired;
    }

    public String getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(String feePaid) {
        this.feePaid = feePaid;
    }

    public String getFeeBalance() {
        return feeBalance;
    }

    public void setFeeBalance(String feeBalance) {
        this.feeBalance = feeBalance;
    }

    public String getSubjectEntry() {
        return subjectEntry;
    }

    public void setSubjectEntry(String subjectEntry) {
        this.subjectEntry = subjectEntry;
    }
}
