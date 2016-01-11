<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <%@taglib prefix="s" uri="/struts-tags" %>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    This is my JSP page. <br>
    !!!!<br>
     
	
	    <br><br><br>
	    <h1 style="color: gray" align="center">Ò©µêÏêÏ¸:</h1>
	    
	    
	<table border="1" align = "center">
    	<tr>
    		<td style="color: gray">StoreName</td>
    		<td style="color: gray">tagsid</td>
    		<td style="color: gray">stocktime</td>
    		<td style="color: gray">soldtime</td>
    		<td style="color: gray">storestatus</td>
    		<br>
   			<s:iterator value="store">
	    		<tr>
					<td text="2" >
						<s:property value="storename"/>
					</td>
					<td text="2" >
						<s:property value="tags.getTagsid()"/>
					</td>
					<td text="2" >
						<s:property value="stocktime"/>
					</td>
					<td text="2" >
						<s:property value="soldtime"/>
					</td>
					<td text="2" >
						<s:property value="storestatus"/>
					</td>
				<tr>
	    	</s:iterator>
   		</tr>
    </table>
    <br>
  </body>
</html>
