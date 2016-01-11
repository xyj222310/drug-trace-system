package com.myproject.Model;

import java.util.HashSet;
import java.util.Set;


/**
 * Tags entity. @author MyEclipse Persistence Tools
 */

public class Tags  implements java.io.Serializable {


    // Fields    

     private Double tagsid;
     private String medicinename;
     private Set transports = new HashSet(0);
     private Set factories = new HashSet(0);
     private Set stores = new HashSet(0);


    // Constructors

    /** default constructor */
    public Tags() {
    }

    
    /** full constructor */
    public Tags(String medicinename, Set transports, Set factories, Set stores) {
        this.medicinename = medicinename;
        this.transports = transports;
        this.factories = factories;
        this.stores = stores;
    }

   
    // Property accessors

    public Double getTagsid() {
        return this.tagsid;
    }
    
    public void setTagsid(Double tagsid) {
        this.tagsid = tagsid;
    }

    public String getMedicinename() {
        return this.medicinename;
    }
    
    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public Set getTransports() {
        return this.transports;
    }
    
    public void setTransports(Set transports) {
        this.transports = transports;
    }

    public Set getFactories() {
        return this.factories;
    }
    
    public void setFactories(Set factories) {
        this.factories = factories;
    }

    public Set getStores() {
        return this.stores;
    }
    
    public void setStores(Set stores) {
        this.stores = stores;
    }
   








}