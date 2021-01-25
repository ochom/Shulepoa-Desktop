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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private int id;
    
    private String Username;
    private String password;

    public int getId() {
        return id;
    }

    public User() {
    }    

    public User(String Username, String password) {
        this.Username = Username;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
