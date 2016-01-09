package com.myproject.serverAcess.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.myproject.serverAcess.ServerAccess;
public class ServerAccessImpl implements ServerAccess{
	public ServerAccessImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String dopost(String table) throws Exception{
		HttpURLConnection httpConn = null;
		String path = "http://localhost:8080/RFIDweb/tag?table="+table+"&tagid=0";
		BufferedReader in=null;
		try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            //读取响应
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
                throw new Exception("请求出现了问题!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            in.close();
            httpConn.disconnect();
        }
		return null;
	}
}
