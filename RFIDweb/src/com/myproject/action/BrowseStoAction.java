package com.myproject.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myproject.Model.Store;
import com.myproject.Model.Tags;
import com.myproject.service.LabelManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BrowseStoAction extends ActionSupport implements ModelDriven<Tags>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Store> store  = new ArrayList<Store>() ;
	public BrowseStoAction() {
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
		JSONArray ja = lm.dopost("store",tagid);
		try {
			for(int i = 0;i<ja.length();i++){
				JSONObject js;
				js = ja.getJSONObject(i);
				Store sto = new Store();
				Tags tags= new Tags();
				tags.setTagsid(js.getDouble("tagid"));
				sto.setTags(tags);
				sto.setStorename(js.getString("storename"));
				sto.setStorestatus(js.getString("storestatus"));
				sto.setSoldtime(Timestamp.valueOf(js.getString("soldtime")));
				sto.setStocktime(Timestamp.valueOf(js.getString("stocktime")));
				store.add(sto);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getCause();
		}
		return "success";
	}
	public List<Store> getStore() {
		return store;
	}
	public void setStore(List<Store> store) {
		this.store = store;
	}
}
