package com.myproject.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myproject.Model.Tags;
import com.myproject.Model.Transport;
import com.myproject.service.LabelManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BrowseTransAction extends ActionSupport implements ModelDriven<Tags>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Transport> transport  =  new ArrayList<Transport>();
	public BrowseTransAction() {
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
	public String execute()  {
		// TODO Auto-generated method stub
		LabelManager lm = new LabelManager();
		Double tagid = (double) 0;
		JSONArray ja = lm.dopost("transport",tagid);
		try {
			for(int i = 0;i<ja.length();i++){
				JSONObject js;
				js = ja.getJSONObject(i);
				Transport trans = new Transport(); 
				Tags tags= new Tags();
				tags.setTagsid(js.getDouble("tagid"));
				trans.setTags(tags);
				trans.setTansportname(js.getString("transportname"));
				trans.setTransportstatus(js.getString("transportstatus"));
				trans.setStarttime(Timestamp.valueOf(js.getString("starttime")));
				trans.setEndtime(Timestamp.valueOf(js.getString("endtime")));
				transport.add(trans);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "success";
	}
	
	public List<Transport> getTransport() {
		return transport;
	}
	public void setTransport(List<Transport> transport) {
		this.transport = transport;
	}
}
