package com.myproject.action;

import com.myproject.Model.Tags;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class DetailsAction extends ActionSupport implements ModelDriven<Tags>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DetailsAction() {
		super();
		// TODO Auto-generated constructor stubs
		System.out.println("browses action£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡");
	}
	@Override
	public Tags getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		return "success";
	}
}
