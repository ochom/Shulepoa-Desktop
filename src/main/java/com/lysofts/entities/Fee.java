/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lysofts.entities;

import com.lysofts.annotations.Column;
import com.lysofts.annotations.Table;
import java.io.Serializable;

/**
 *
 * @author Rick
 */
@Table(name = "tbl_fee_register")
public class Fee implements Serializable {

    private String id;

    @Column(name = "adm")
    private String regNumber;

    @Column(name = "code")
    private String transCode;

    @Column(name = "academic_year")
    private String year;

    private String debit, credit;

    @Column(name = "mode_of_payment")
    private String mode;

    @Column(name = "amount_in_words")
    private String inWords;

    @Column(name = "receipt_no")
    private String receiptNumber;

    @Column(name = "register_date")
    private String created;

    @Column(name = "served_by")
    private String createdBy;

    public Fee() {

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

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getInWords() {
        return inWords;
    }

    public void setInWords(String inWords) {
        this.inWords = inWords;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
