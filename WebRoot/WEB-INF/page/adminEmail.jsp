<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList"%>
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
		<title>Email</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript" language="javaScript">
	function confirmsubmit()
	{
		var month=document.getElementById("d").value;
		if(month=='')
		{
		  return false;
		}else 
		   return true;
	}	
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
	   var t=window.confirm("Really send emails to them?");
	   if(t==true)
	   	  {
	   	     var x=document.getElementsByName("emp");
	   	     var mail="Mailto:";
	   	     for(var i=0;i<x.length-1;i++)
	   	     {
	   	        if(x[i].checked==true)
	   	            mail=mail+x[i].value+";"
	   	     }
	   	     mail=mail+x[i].value+"?CC=Jieying.Jiang@cn.bosch.com;John.Li2@cn.bosch.com;Honglun.Li@cn.bosch.com;George.Geng@cn.bosch.com;Xuehua.Ni@cn.bosch.com;Hongwei.Wang@cn.bosch.com;Munir.Ganbour2@cn.bosch.com;Tobias.Frick@cn.bosch.com&Subject=Monthly Controlling Last reminder&Body=Dear all,%0A%20%20%20Please check the working days of your input in monthly controlling since it doesn’t match with the working days of current month. Try to finish filling before deadline. Thanks so much for your kind support.%0AB.R.%0AKatie.";
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
											Email
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
											<div style="margin: 2% 0% 0% 10%" align="left">
												<form action="EmailAction" method="post">
													Month:
													<input class="Wdate" name="d" id="d"
														onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" />
													&nbsp;&nbsp;
													<input type="submit" value="show"
														onClick="return confirmsubmit()"/ >
												</form>
												<%
													String time = (String) request.getAttribute("query_month");
													if (time != null)
														out
																.println("<div style='margin:-2% 5% 0% 40%;color:#F00' align='left'>Query Month:<font size=4><B>"
																		+ time
																		+ "</B></font>&nbsp;&nbsp;WorkingDays:<font size=4><B>"
																		+ request.getAttribute("workDays")
																		+ "</B></font></div>");
												%>
											</div>
											<%
												out
														.println("<br/><hr width=80% size=3 color=#5151A2 style='filter: progid :   DXImageTransform.Microsoft.Shadow (   color #5151A2, direction :   145, strength :   15 )'>");

												ArrayList<String> empList = (ArrayList<String>) request
														.getAttribute("warnEmplist");
												ArrayList<String> emailList = (ArrayList<String>) request
														.getAttribute("warnEmailList");
												ArrayList<String> workList = (ArrayList<String>) request
														.getAttribute("workList");
												if (empList != null && emailList.size() != 0) {
													out
															.println("<div style='margin:0% 0% 0% -75%'><input type='checkbox' name='selAll' id='selAll' checked onClick='makeotherscheck()'/>&nbsp;&nbsp;<b>All</b></div>");
													out
															.println("<div style='margin:0% 0% 0% 10%;width:80%' align='center'><table cellspacing=0 border=1 corlor='#458B00' width='80%'>");
													int i = 0;
													for (i = 0; i < emailList.size(); i++) {
														String name = empList.get(i);
														String email = emailList.get(i);
														String workdays = workList.get(i);
														if (i % 6 == 0) {
															out
																	.println("<tr style='height:3px'><td align='left' style='height:3px;'>");
														} else if (i % 6 == 1) {
															out.println("<td align='left' style='height:3px'>");
														} else if (i % 6 == 2) {
															out.println("<td align='left' style='height:3px'>");
														} else if (i % 6 == 3) {
															out.println("<td align='left' style='height:3px'>");
														} else if (i % 6 == 4) {
															out.println("<td align='left' style='height:3px'>");
														} else if ((i + 1) % 6 == 0) {
															out.println("<td align='left' style='height:3px'>");
														}
														out
																.println("<input type='checkbox' name='emp' id='emp' onClick='checkOP()' checked value='"
																		+ email
																		+ "'/>&nbsp;&nbsp;"
																		+ name
																		+ ":&nbsp;&nbsp;<font color='red'>"
																		+ workdays + "</font>");
														if (i % 6 == 0 || i % 6 == 1 || i % 6 == 2 || i % 6 == 3
																|| i % 6 == 4) {
															out.println("</td>");
														} else if (i % 6 == 5) {
															out.println("</td></tr>");
														}
													}
													if (i % 6 == 0) {
														out.println("</table></div>");
													} else {
														out.println("</tr></table></div>");
													}
													out
															.println("<input type='submit' value='EMAIL' onClick='sendEmail()'/>");
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
				<tr>
					<td><jsp:include page="footer.jsp"></jsp:include></td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>