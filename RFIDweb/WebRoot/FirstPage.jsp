<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>

   <%@taglib prefix="s" uri="/struts-tags" %>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>quququ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function sub(){
			if(confirm("确认删除")){
				window.location.href='download.action';
			}
		}
	</script>
  </head>
  
  <body>
    !!!!<br>
  		<input type="button" id="receive" value="接收" onclick="window.location.href='FirstPage.jsp'" />
		<input type="button" id="send" value="发送" onclick="window.location.href='FirstPage.jsp'" />
	    <br><br><br>
	    <br><br>
	    <input type="button" id="browsetags" value="浏览药品信息" onclick="window.location.href='browse_tag.action'">
		 <input type="button" id="browsefactory" value="浏览制药厂信息" onclick="window.location.href='browse_factory.action'">
		 <input type="button" id="browsestore" value="浏览商店信息" onclick="window.location.href='browse_store.action'">
		  <input type="button" id="browsetransport" value="浏览运输公司信息" onclick="window.location.href='browse_transport.action'">
		
		<table border="1" align = "center">
    		<tr>
	    		<td >此处无银三百两</td>
	    		<td>隔壁王二不曾偷</td>
	    		<br>
	    		<tr>
		    		<td text="disk" >
					</td>
					<td text="disk" >
					</td>
				<tr>
				
   			</tr>
    	</table>
  </body>
</html>
