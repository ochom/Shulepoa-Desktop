/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import com.lysofts.annotations.Table;

/**
 *
 * @author Rick
 */
@Table(name = "Basic_SubjectNumbers")
public class SubjectNumbers {

    private String id;

    public SubjectNumbers() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
