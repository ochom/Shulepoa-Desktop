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

@Table(name = "tblclasses")
public class Classroom {
    
    private String id;

    @Column(name = "Class_name")
    private String name;

    @Column(name = "Class_Teacher")
    private String classTeacher;

    @Column(name = "ClassTeacher_Signature")
    private String signature;

    public Classroom() {
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

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
