/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import com.lysofts.annotations.Column;
import com.lysofts.annotations.Table;
import com.lysofts.annotations.Transient;
import java.util.List;

/**
 *
 * @author mspace-dev
 */
@Table(name = "tblteachers")
public class Teacher{
    
    private String id;

    @Column(name = "T_name")
    private String name;

    @Column(name = "T_code")
    private String staffNumber;

    @Column(name = "T_gender")
    private String gender;

    @Column(name = "T_phone")
    private String phone;

    @Column(name = "T_initials")
    private String initials;
    
    @Transient
    private List<TeacherSubject> subjects;

    public Teacher() {
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public List<TeacherSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TeacherSubject> subjects) {
        this.subjects = subjects;
    }
}
