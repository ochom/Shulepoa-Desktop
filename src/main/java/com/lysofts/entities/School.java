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
@Table(name = "tblschool")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "School_id")
    int id;

    @Column(name = "School_name")
    String name;

    @Column(name = "School_posta")
    String postalAddress;

    @Column(name = "School_logo")
    String logo;

    @Column(name = "School_Motto")
    String motto;

    @Column(name = "School_Email")
    String email;

    @Column(name = "School_Contact")
    String contact;

    @Column(name = "Principal_name")
    String principal;

    @Column(name = "Principal_Signature")
    String signature;

    @Column(name = "ClosingDate")
    String closingDate;

    @Column(name = "OpeningDate")
    String openingDate;

    public School() {
    }

    public School(String name, String postalAddress, String logo, String motto, String email, String contact, String principal, String signature, String closingDate, String openingDate) {
        this.name = name;
        this.postalAddress = postalAddress;
        this.logo = logo;
        this.motto = motto;
        this.email = email;
        this.contact = contact;
        this.principal = principal;
        this.signature = signature;
        this.closingDate = closingDate;
        this.openingDate = openingDate;
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

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }
}
