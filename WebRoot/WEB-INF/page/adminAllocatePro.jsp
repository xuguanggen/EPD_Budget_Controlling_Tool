<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList,java.util.Map,java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Allocate Project</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript" language="javaScript">
	var y=document.getElementsByName("selAll");
	function checkOP()
	{
	   var x=document.getElementsByName("emp");
	   var count=x.length;
	   var selectCount=0;
	   for(var i=0;i<count;i++)
	   {
	     if(x[i].checked == true)
		 {
          selectCount++;
          } 
	   }
	   if(selectCount==count)
	   {
	      y[0].checked=true;
	   }else
	   {
	      y[0].checked=false;
	   }
	}
	function  makeotherscheck()
	{
	  	var x=document.getElementsByName("emp");
	  	if(y[0].checked==true)
	  	{
	  	  for(var i=0;i<x.length;i++)
	  	  {
	  	      x[i].checked=true;
	  	  }
	  	}else
	  	{
	  	   for(var i=0;i<x.length;i++)
	  	   {
	  	      x[i].checked=false;
	  	   }
	  	}
		
	}
	function sendEmail()
	{
	   var t=window.confirm("Really  allocate this projects to this guest?");
	   if(t==true)
	   	  {
	   	     var x=document.getElementsByName("emp");
	   	     var mail="AdminAllocateProAction!allocatePro?pro=";
	   	     var pro="";
	   	     for(var i=0;i<x.length-1;i++)
	   	     {
	   	        if(x[i].checked==true)
	   	            pro=pro+x[i].value+",";	   	            
	   	     }
	   	     pro=pro.substring(0,pro.length-1) 
	   	     mail=mail+pro;
	   	     window.location.href=mail;
	   	     return true;
	   	  }
	   	else
	   	{  
	   	   return false;
	   	}
	}
	</script>
	<body>
		<%
			String ip2 = request.getRemoteAddr().toString();
			if (session.getAttribute(ip2 + "EmpName") == null) {
				out
						.println("<script type='text/javascript'>alert('Hello,please Login first!');window.document.location.href='index.jsp';</script>");
				return;
			}
		%>
		<div id="container">
			<table width="100%" height="100%" border="0">
				<tr>
					<td>
						<div id="head"><jsp:include page="adminHead.jsp"></jsp:include></div>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td width="15%" valign="top">
									<div>
										<jsp:include page="adminSidebar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div id="display" style="width: 100%;">
										<div id="title">
											Allocate Project
											<div id="employee">
												<s:property value="#session.time" />
												<%
													String ip = request.getRemoteAddr().toString();
													out.println(session.getAttribute(ip + "EmpName"));
												%>
												!
												<img src="images/logout.png" />
												<font size=1><a href="LoginOutAction"
													style="color: black;"
													onClick="return window.confirm('Really want to exit the system?')";>Log
														out</a> </font>
											</div>
										</div>
										<div id="comtable">											
											<%											
											  List<Map.Entry<String, String>> list=(List<Map.Entry<String, String>>)request.getAttribute("proList");
											  String ename=request.getAttribute("ename").toString().trim();
											  if (list != null && list.size() != 0) {
													out
															.println("<div style='margin:0% 0% 0% -75%'><input type='checkbox' name='selAll' id='selAll' checked onClick='makeotherscheck()'/>&nbsp;&nbsp;<b>"+ename+"</b></div>");
													out
															.println("<div style='margin:0% 0% 0% 0%;width:100%' align='center'><table cellspacing=0 border=1 corlor='#458B00' width='80%'>");
													int i = 0;
													for (i = 0; i < list.size(); i++) {
														String []str=list.get(i).toString().split("=");
														String tname = str[1];
														String pname = str[0];
														
														if (i % 5== 0) {
															out
																	.println("<tr style='height:3px'><td align='left' style='height:3px;'>");
														} else if (i % 5 == 1) {
															out.println("<td align='left' style='height:3px'>");
														} else if (i % 5== 2) {
															out.println("<td align='left' style='height:3px'>");
														} else if (i % 5 == 3) {
															out.println("<td align='left' style='height:3px'>");
														} else if ((i + 1) % 5 == 0) {
															out.println("<td align='left' style='height:3px'>");
														}
														out
																.println("<input type='checkbox' name='emp' id='emp' onClick='checkOP()' checked value='"
																		+ pname
																		+ "'/>"
																		+ pname
																		+ "-<font color=blue>"
																		+ tname + "</b>");
														if (i % 5== 0 || i % 5 == 1 || i % 5 == 2 || i %5== 3
																) {
															out.println("</td>");
														} else if (i % 5 == 4) {
															out.println("</td></tr>");
														}
													}
													if (i % 5== 0) {
														out.println("</table></div>");
													} else {
														out.println("</tr></table></div>");
													}
													out
															.println("<input type='submit' value='Allocate' onClick='sendEmail()'/>");
												}
											%>
										</div>
										<!-- end comTable -->
									</div>
									<!-- end display -->
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>