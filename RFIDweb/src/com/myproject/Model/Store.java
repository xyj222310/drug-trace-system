package com.myproject.Model;

import java.sql.Timestamp;


/**
 * Store entity. @author MyEclipse Persistence Tools
 */

public class Store  implements java.io.Serializable {


    // Fields    

     private Integer storeid;
     private Tags tags;
     private String storename;
     private Timestamp stocktime;
     private Timestamp soldtime;
     private String storestatus;


    // Constructors

    /** default constructor */
    public Store() {
    }

    
    /** full constructor */
    public Store(Tags tags, String storename, Timestamp stocktime, Timestamp soldtime, String storestatus) {
        this.tags = tags;
        this.storename = storename;
        this.stocktime = stocktime;
        this.soldtime = soldtime;
        this.storestatus = storestatus;
    }

   
    // Property accessors

    public Integer getStoreid() {
        return this.storeid;
    }
    
    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public Tags getTags() {
        return this.tags;
    }
    
    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public String getStorename() {
        return this.storename;
    }
    
    public void setStorename(String storename) {
        this.storename = storename;
    }

    public Timestamp getStocktime() {
        return this.stocktime;
    }
    
    public void setStocktime(Timestamp stocktime) {
        this.stocktime = stocktime;
    }

    public Timestamp getSoldtime() {
        return this.soldtime;
    }
    
    public void setSoldtime(Timestamp soldtime) {
        this.soldtime = soldtime;
    }

    public String getStorestatus() {
        return this.storestatus;
    }
    
    public void setStorestatus(String storestatus) {
        this.storestatus = storestatus;
    }
   








}