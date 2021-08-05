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
@Table(name = "tblstudents_subjects")
public class StudentSubject {

    @Column(name = "SS_Student_id")
    private String studentId;

    @Column(name = "SS_Subject_code")
    private String subjectCode;

    @Column(name = "SS_Subject_name")
    private String subjectName;

    public StudentSubject() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
}
