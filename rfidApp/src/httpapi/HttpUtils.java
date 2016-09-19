package httpapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import myclass.Factory;
import myclass.Store;
import myclass.TransPort;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpUtils {
	
	String url,Table;
	public HttpUtils(String table,double tagId)
	{
		this.Table=table;
		if(table.equals("tag"))
		{
			url="http://192.168.191.1:8080/RFIDserver/tag?table=tag&tagid="+String.valueOf(tagId);
		}
		
		if(table.equals("factory"))
		{
			url="http://192.168.191.1:8080/RFIDserver/tag?table=factory&tagid="+String.valueOf(tagId);
		}
		if(table.equals("store"))
		{
			url="http://192.168.191.1:8080/RFIDserver/tag?table=store&tagid="+String.valueOf(tagId);
		}
		if(table.equals("transport"))
		{
			url="http://192.168.191.1:8080/RFIDserver/tag?table=transport&tagid="+String.valueOf(tagId);
		}
	
	}
	public String getinputResult() throws IOException
	{
		String result="";
		HttpURLConnection httpConn=null;
		
		BufferedReader in=null;
		try {
            URL u=new URL(url);
            httpConn=(HttpURLConnection)u.openConnection();
            //读取响应
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"gb2312"));//将http链接中产生的输入流中的文字转化为GB2312格式。免得显示在屏幕上是乱码
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);//将转化的结果赋值给一个String，就是Http请求得到的json数据对象。
                }
                result= content.toString();
                Log.e("inputstream",result);
            }else{
                try {
					throw new Exception("请求出现了问题!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } 
		catch (IOException e)
		{
            e.printStackTrace();
        }finally{
            in.close();
            httpConn.disconnect();
        }
		Log.e("httpresult",result);
		return result;
		}
		
	

	public String getResult(String a)
	{
		String result="";
		if(Table.equals("tag"))
		{
			result=getTag(a);
		}
		if(Table.equals("factory"))
		{
			result=getFactory(a);
		}
		if(Table.equals("store"))
		{
			result=getStore(a);
		}
		if(Table.equals("transport"))
		{
			result=getTransport(a);
		}
		return result;
	}
	 public String getTransport(String a) {
		// TODO Auto-generated method stub		 
		String b="";
		 Log.e("what is a!!!!!!!!!", a);
		if(a!=null)
		{
			 if(a.equals(""))
			 {
				 b="查无此结果";
				 return b;
			 }
			try{
				JSONObject js = new JSONObject(a);
				TransPort t=new TransPort();
				t.setTagId(js.getLong("tagid")+"");
				t.setTransportName(js.getString("transportname"));
				t.setStartTime(js.getString("starttime"));
				t.setEndTime(js.getString("endtime"));
				t.setTransportStatus(js.getString("transportstatus"));
				b=t.getTransportResult();
			}catch(Exception e)
			{
				b="no_data found";
				e.printStackTrace();
				
			}
		}
		return b;
	}
	public String getStore(String a) {
		// TODO Auto-generated method stub
		 String result="";
		 
		 
		 Log.e("what is a!!!!!!!!!", a);
		 if(a!=null)
		 {
			 
			 
			 if(a.equals(""))
			 {
				 result="查无此结果";
				 return result;
			 }
			 
			 try{
				 
					JSONObject js = new JSONObject(a);
					Store s=new Store();
					s.setTagId(js.getLong("tagid")+"");
					s.setStoreName(js.getString("storename"));
					s.setStockTime(js.getString("stocktime"));
					s.setSoldTime(js.getString("soldtime"));
					s.setStoreStatus(js.getString("storestatus"));
					result=s.getStoreResult();
					
				 
			 }catch(Exception e){

					result="no_data found";
					e.printStackTrace();
			 }
		 }
				 
		return result;
	}
	public String getTag(String strResult) { 
	
		 String result="";		 
		 Log.e("tag", strResult);
		 Log.e("what is a!!!!!!!!!", strResult);
		 if(strResult!=null){
			 if(strResult.equals(""))
			 {
				 result="查无此结果";
				 return result;
			 }
	        try {
	            JSONObject js = new JSONObject(strResult);
	            result="TAGID:"+js.getLong("tagid")+"\n"
	            +"medicineName:"+js.getString("medicine");	                    
	        } catch (JSONException e) { 

				result="no_data found";
	            e.printStackTrace(); 
	        } 
		 }
	        return result;
	    } 
	   
	 public String getFactory(String a)
	 {
		 String result="";
		 if(a!=null)
		 {
			 if(a.equals(""))
			 {
				 result="查无此结果";
				 return result;
			 }
			 try{
				 JSONObject js=new JSONObject(a);
				 Factory f=new Factory();
				 f.setTagid(js.getLong("tagid")+"");
				 f.setFactoryName(js.getString("factoryname"));
				 f.setLeave_status(js.getString("leavestatus"));
				 f.setOutputTime(js.getString("outputtime"));
				 f.setProduct_line1_status(js.getString("line1status"));
				 f.setProduct_line2_status(js.getString("line2status"));
				 result=f.getFactoryResult();
						 
			 }catch(Exception e)
			 {

					result="no_data found";
				 e.printStackTrace();
				 }
		 }
		 
		 return result;
	 }
	 

}
