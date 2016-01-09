package com.myproject.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

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
			JSONObject jso = new JSONObject();
			LabelManager lm = new LabelManager();
			String table = request.getParameter("table");
			int tagid = Integer.valueOf(request.getParameter("tagid"));
			if(0 == tagid){
				 JSONArray jArray = lm.sendToWeb(table);
				 response.setCharacterEncoding("gb2312");
				 response.getWriter().write(jArray.toString());
			}else{
				jso = lm.sendToAPP(table, tagid);
				response.setCharacterEncoding("gb2312");
				response.getWriter().write(jso.toString());
			}
			
		}catch(Exception e ){
			e.printStackTrace();
		}
	}

}

