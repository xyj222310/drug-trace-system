package com.myproject.action;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.myproject.Model.Tags;
import com.myproject.service.LabelManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BrowseTagAction extends ActionSupport implements ModelDriven<Tags>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Tags> tags = new ArrayList<Tags>();
	public BrowseTagAction() {
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
		LabelManager lm = new LabelManager();
//		BrowseAction ba = new BrowseAction();
//		List<Tags> t= ba.getTags();
		Double tagid = (double) 0;
		String table = "tag";
		JSONArray ja = lm.dopost(table,tagid);
		try {
			for(int i = 0;i<ja.length();i++){
				JSONObject js;
				js = ja.getJSONObject(i);
				Tags t =new Tags();
				t.setTagsid(js.getDouble("tagid"));
				t.setMedicinename(js.getString("medicine"));
				tags.add(t);
				System.out.print(t.getTagsid().longValue());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.getCause();
		}
		return "success";
	}
	public List<Tags> getTags() {
		return tags;
	}
	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
}
