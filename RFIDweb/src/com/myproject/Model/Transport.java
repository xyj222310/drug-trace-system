package com.myproject.Model;

import java.sql.Timestamp;

/**
 * Transport entity. @author MyEclipse Persistence Tools
 */

public class Transport implements java.io.Serializable {

	// Fields

	private Integer transportid;
	private Tags tags;
	private String tansportname;
	private Timestamp starttime;
	private Timestamp endtime;
	private String transportstatus;

	// Constructors

	/** default constructor */
	public Transport() {
	}

	/** full constructor */
	public Transport(Tags tags, String tansportname, Timestamp starttime,
			Timestamp endtime, String transportstatus) {
		this.tags = tags;
		this.tansportname = tansportname;
		this.starttime = starttime;
		this.endtime = endtime;
		this.transportstatus = transportstatus;
	}

	// Property accessors

	public Integer getTransportid() {
		return this.transportid;
	}

	public void setTransportid(Integer transportid) {
		this.transportid = transportid;
	}

	public Tags getTags() {
		return this.tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public String getTansportname() {
		return this.tansportname;
	}

	public void setTansportname(String tansportname) {
		this.tansportname = tansportname;
	}

	public Timestamp getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getTransportstatus() {
		return this.transportstatus;
	}

	public void setTransportstatus(String transportstatus) {
		this.transportstatus = transportstatus;
	}

}