package com.myproject.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myproject.Model.Factory;
import com.myproject.Model.Tags;
import com.myproject.service.LabelManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class BrowseFacAction extends ActionSupport implements ModelDriven<Tags>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Factory> factory = new ArrayList<Factory>();
	public BrowseFacAction() {
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
		JSONArray ja = lm.dopost("factory",tagid);
		try {
			for(int i = 0;i<ja.length();i++){
				JSONObject js;
				js = ja.getJSONObject(i);
				Factory fac = new Factory();
				Tags tags= new Tags();
				tags.setTagsid(js.getDouble("tagid"));
				fac.setTags(tags);
				fac.setFactoryname(js.getString("factoryname"));
				fac.setLeaveFactoryStatus(js.getString("leavestatus"));
				
				fac.setOutputtime(Timestamp.valueOf(js.getString("outputtime")));

				fac.setProductLine1Status(js.getString("line1status"));
				fac.setProductLine2Stauts(js.getString("line2status"));
				factory.add(fac);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}
	
//	public JSONArray gettable(String table){
//		LabelManager lm = new LabelManager();
//		int tagid = 0;
//		JSONArray ja = lm.dopost(table,tagid);
//		try {
//			switch (table) {
//			case "tag":
//				for(int i = 0;i<ja.length();i++){
//					JSONObject js;
//					js = ja.getJSONObject(i);
//					Tags t =new Tags();
//					t.setTagsid(js.getInt("tagid"));
//					t.setMedicinename(js.getString("medicine"));
//					tags.add(t);	
//				}
//				break;
//			case "fac":
//				for(int i = 0;i<ja.length();i++){
//					JSONObject js;
//					js = ja.getJSONObject(i);
//					Factory fac = new Factory(); 
//					fac.setTags(tags.get(i));
//					fac.setFactoryname(js.getString("factoryname"));
//					fac.setLeaveFactoryStatus(js.getString("leavestatus"));
//					
//					fac.setOutputtime(Timestamp.valueOf(
//							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(js.getString("outputtime"))));
//
//					fac.setProductLine1Status(js.getString("line1status"));
//					fac.setProductLine2Stauts(js.getString("line21status"));
//					factory.add(fac);
//				}
//				break;
//			case "sto":
//				for(int i = 0;i<ja.length();i++){
//					JSONObject js;
//					js = ja.getJSONObject(i);
//					Store sto = new Store(); 
//					sto.setTags(tags.get(i));
//					sto.setStorename(js.getString("storename"));
//					sto.setStorestatus(js.getString("storestatus"));
//					sto.setSoldtime(Timestamp.valueOf(
//							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(js.getString("soldtime"))));
//					sto.setStocktime(Timestamp.valueOf(
//							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(js.getString("stocktime"))));
//					store.add(sto);
//				}
//				break;
//			case "trans":
//				for(int i = 0;i<ja.length();i++){
//					JSONObject js;
//					js = ja.getJSONObject(i);
//					Transport trans = new Transport(); 
//					trans.setTags(tags.get(i));
//					trans.setTansportname(js.getString("transportname"));
//					trans.setTransportstatus(js.getString("transportstatus"));
//					trans.setStarttime(Timestamp.valueOf(
//							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(js.getString("starttime"))));
//					trans.setEndtime(Timestamp.valueOf(
//							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//							.format(js.getString("endtime"))));
//					transport.add(trans);
//				}
//				break;
//	
//			default:
//				break;
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//	public List<Tags> getTags() {
//		return tags;
//	}
//	public void setTags(List<Tags> tags) {
//		this.tags = tags;
//	}
	public List<Factory> getFactory() {
		return factory;
	}
	public void setFactory(List<Factory> factory) {
		this.factory = factory;
	}
}
