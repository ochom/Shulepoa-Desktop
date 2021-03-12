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
 * 
 */

@Table(name = "tblTeachersToSubjects")
public class TeacherSubject {

    private String id;

    @Column(name = "Teacher_id")
    private String teacherId;

    @Column(name = "Subject_code")
    private String subjectCode;

    @Column(name = "Subject_name")
    private String subjectName;

    @Column(name = "Teacher_initials")
    private String teacherInitials;

    @Column(name = "Teaching_class")
    private String classroom;

    public TeacherSubject() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherInitials() {
        return teacherInitials;
    }

    public void setTeacherInitials(String teacherInitials) {
        this.teacherInitials = teacherInitials;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    
}
