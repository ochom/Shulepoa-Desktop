/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author mspace-dev
 */
@Entity
@Table(name = "tblteachers")
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "T_name")
    private String name;

    @Column(name = "T_code")
    private String staffNumber;

    @Column(name = "T_gender")
    private String gender;

    @Column(name = "T_phone", nullable = true)
    private String phone;

    @Column(name = "T_initials")
    private String initials;
    
    @Transient
    private List<TeacherSubject> subjects;

    public Teacher() {
    }

    public Teacher(String name, String staffNumber, String gender, String phone, String initials) {
        this.name = name;
        this.staffNumber = staffNumber;
        this.gender = gender;
        this.phone = phone;
        this.initials = initials;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
