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
     
	
	    <br><br><br>
	    <h1 style="color: gray">药品药品:</h1>
	    <br><br><br>
	<table border="1" align = "center">
    	<tr>
    		<td style="color: gray">Medicine's ID</td>
    		<td style="color: gray">Medicine's Name</td>
    		<br>
   			<s:iterator value="tags">
	    		<tr>
	    			<a href="details.action">
			    		<td text="1" >
							<s:property value="tagsid"/>
						</td>
						<td text="2" >
							<s:property value="medicinename"/>
						</td>
					</a>
				<tr>
	    	</s:iterator>
   		</tr>
    </table>
    
    
  </body>
</html>
