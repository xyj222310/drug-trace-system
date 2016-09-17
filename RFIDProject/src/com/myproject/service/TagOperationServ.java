package com.myproject.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
/*
 * (non-Javadoc)
 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 * doPost方法，实现的是服务器接收请求和 相应请求。
 */
/**
 * Servlet implementation class Tag
 */
@WebServlet("/Tag")
public class TagOperationServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TagOperationServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
//			JSONObject jso = new JSONObject();
			LabelManager lm = new LabelManager();
			String table = request.getParameter("table");
			Double tagid = Double.valueOf(request.getParameter("tagid"));
//			if(0 == tagid){
			JSONArray jArray = lm.sendToWeb(table,tagid);
				 response.setCharacterEncoding("gb2312");
//			}else{
//				jso = lm.sendToAPP(table, tagid);
//				response.setCharacterEncoding("gb2312");
			if(0==tagid){
				response.getWriter().write(jArray.toString());
			}
			else{
				response.getWriter().write(jArray.getJSONObject(0).toString());
			}
//			}
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

}
