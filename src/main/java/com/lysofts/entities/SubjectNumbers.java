/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mspace-dev
 */
@Entity
@Table(name = "Basic_SubjectNumbers")
public class SubjectNumbers implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
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
