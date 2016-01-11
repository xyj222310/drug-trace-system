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
	    <h1 style="color: gray" align="center">ÖÆÒ©³§ÏêÏ¸:</h1>
	    
	<table border="1" align = "center">
    	<tr>
    		<td style="color: gray">FactoryName</td>
    		<td style="color: gray">tagsid</td>
    		<td style="color: gray">productline1status</td>
    		<td style="color: gray">productline2status</td>
    		<td style="color: gray">leavefromherestatus</td>
    		<td style="color: gray">OutPutTime</td>
    		<br>
   			<s:iterator value="factory">
	    		<tr>
					<td text="2" >
						<s:property value="factoryname"/>
					</td>
					<td text="2" >
						<s:property value="tags.getTagsid()"/>
					</td>
					<td text="2" >
						<s:property value="productLine1Status"/>
					</td>
					<td text="2" >
						<s:property value="productLine2Stauts"/>
					</td>
					<td text="2" >
						<s:property value="leaveFactoryStatus"/>
					</td>
					<td text="2" >
						<s:property value="outputtime"/>
					</td>
				<tr>
	    	</s:iterator>
   		</tr>
    </table>
    
    
    <br>
  </body>
</html>
