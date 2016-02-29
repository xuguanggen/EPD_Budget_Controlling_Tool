<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
		  function upload()
		  {
		   window.location.href = "<%=path%>/AdminNavigateTo?url=/WEB-INF/page/upload.jsp"; 
		  }
		  function showchart()
		  {
		     window.location.href = "<%=path%>/AdminNavigateTo?url=/WEB-INF/page/showchart.jsp"; 
		  }
		   function fill()
		  {
		     window.location.href = "<%=path%>/AdminNavigateTo?url=/WEB-INF/page/AssociateFill.jsp"; 
		  }
		  function addemp()
		  {
		     window.location.href = "<%=path%>/AdminNavigateTo?url=/WEB-INF/page/AddEmp.jsp"; 
		  }
		  function addpro()
		  {
		     window.location.href="<%=path%>/AdminNavigateTo?url=/WEB-INF/page/AddPro.jsp";
		  }
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'AdminFunction.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="${path}/css/href.css" rel="stylesheet" type="text/css"/>
	</head>

	<body>
		<br />
		<br />
		<br />
		<center>
			<h1>
				Admin Function Menu
			</h1>
			<input type="button" value="Upload" style="width: 100px;"
				onclick="upload();" />
			<br>
			<br />
			<input type="button" value="Show Chart" style="width: 100px;"
				onclick="showchart();" />
			<br />
			<br />
			<input type="button" value="Fill MC" style="width: 100px;"
				onclick="fill();" />
			<br />
			<br />
			<input type="button" value="Add Emp" style="width: 100px;"
				onclick="addemp();" />
			<br />
			<br />
				<input type="button" value="Add Pro" style="width: 100px;"
				onclick="addpro();" />
			<br />
			<br />
		</center>




	</body>
</html>
