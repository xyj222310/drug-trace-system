package com.myproject.Model;

import java.sql.Timestamp;

/**
 * Factory entity. @author MyEclipse Persistence Tools
 */

public class Factory implements java.io.Serializable {

	// Fields

	private int factoryid;
	private Tags tags;
	private String factoryname;
	private Timestamp outputtime;
	private String productLine1Status;
	private String productLine2Stauts;
	private String leaveFactoryStatus;

	// Constructors

	/** default constructor */
	public Factory() {
	}

	/** full constructor */
	public Factory(Tags tags, String factoryname, Timestamp outputtime,
			String productLine1Status, String productLine2Stauts,
			String leaveFactoryStatus) {
		this.tags = tags;
		this.factoryname = factoryname;
		this.outputtime = outputtime;
		this.productLine1Status = productLine1Status;
		this.productLine2Stauts = productLine2Stauts;
		this.leaveFactoryStatus = leaveFactoryStatus;
	}

	// Property accessors

	public int getFactoryid() {
		return this.factoryid;
	}

	public void setFactoryid(Integer factoryid) {
		this.factoryid = factoryid;
	}

	public Tags getTags() {
		return this.tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}

	public String getFactoryname() {
		return this.factoryname;
	}

	public void setFactoryname(String factoryname) {
		this.factoryname = factoryname;
	}

	public Timestamp getOutputtime() {
		return this.outputtime;
	}

	public void setOutputtime(Timestamp outputtime) {
		this.outputtime = outputtime;
	}

	public String getProductLine1Status() {
		return this.productLine1Status;
	}

	public void setProductLine1Status(String productLine1Status) {
		this.productLine1Status = productLine1Status;
	}

	public String getProductLine2Stauts() {
		return this.productLine2Stauts;
	}

	public void setProductLine2Stauts(String productLine2Stauts) {
		this.productLine2Stauts = productLine2Stauts;
	}

	public String getLeaveFactoryStatus() {
		return this.leaveFactoryStatus;
	}

	public void setLeaveFactoryStatus(String leaveFactoryStatus) {
		this.leaveFactoryStatus = leaveFactoryStatus;
	}

}