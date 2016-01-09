package com.myproject.action;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.Model.Tags;
import com.myproject.service.LabelManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BrowseAction extends ActionSupport implements ModelDriven<Tags>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Tags> tags ;
	public BrowseAction() {
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
		String table = "tag";
		JSONArray ja = lm.dopost(table);
		try {
			for(int i = 0;i<ja.length();i++){
				JSONObject js;
				js = ja.getJSONObject(i);
				Tags t =new Tags();
				t.setTagsid(js.getInt("tagid"));
				t.setMedicinename(js.getString("medicine"));
				tags.add(t);
//				tags.get(i).setMedicinename();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		switch (key) {
//		case value:
//			
//			break;
//
//		default:
//			break;
//		}
		return "success";
	}
	public List<Tags> getTags() {
		return tags;
	}
	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
}
