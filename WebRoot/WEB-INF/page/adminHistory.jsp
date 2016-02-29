<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*,com.xgg.epd.dbs.*" pageEncoding="utf-8"%>
<%@ page import="com.xgg.epd.beans.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>My MonthlyControlling DataBase</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery/jquery-1.2.6.min.js"></script>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css"
			href="js/jquery/ddcombo/jquery.ddcombo.css" />
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/jquery.ready.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/jquery.flydom-3.1.1.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.bgiframe.min.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.dimensions.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.ajaxQueue.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/thickbox-compressed.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/jquery.ddcombo.js"></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">
		function show()
		{
		   var time=document.getElementById("d").value;
		   if(time=='')
		   {
		     return ;
		   }
		    else
		    {
		    window.location.href="ManageRecordAction!showRecords?time="+time;
		    }  
		}
   </script>
	<body style="overflow-y: scroll">
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
											History &nbsp;Records
											<div id="employee">
												<s:property value="#session.time" />
												<%
													out.println(session.getAttribute(ip2 + "EmpName"));
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
											<div style="margin: 1% 0% 0% 10%" align="left">
												Month:
												<input class="Wdate" name="d" id="d"
													onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" />
												<input type="button" value="show" onClick="show()" />
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid : DXImageTransform.Microsoft.Shadow ( color #5151A2, direction : 145, strength : 15 )">

											<%
												if (session.getAttribute(ip2 + "time") != null) {
													out
															.println("<div style='margin:0% 0% 0% -72%'><font size=4 color=green>"
																	+ session.getAttribute(ip2 + "time").toString()
																			.trim() + "</font></div>");
												}
												out.println("<center>");
												ArrayList<Object[]> alist = (ArrayList<Object[]>) session
														.getAttribute(ip2 + "history");
												if (!(alist == null || alist.size() == 0)) {
													out.println("<table cellspacing=0 border=1 bordercolor=green>");
													out
															.println("<tr><th>Year</th><th>Month</th><th>EmpName</th><th>Section</th><th>ProName</th><th>ServiceNr</th><th>WorkingDays</th><th>Remark</th></tr>");
													for (int i = 0; i < alist.size(); i++) {
														Object[] obj = alist.get(i);
														String year = obj[8].toString().trim();
														String month = obj[3].toString().trim();
														String empName = obj[1].toString().trim();
														String section = obj[2].toString().trim();
														String pName = obj[4].toString().trim();
														String service = obj[5].toString().trim();
														String wd = obj[7].toString().trim();
														String remark = obj[6].toString().trim();
														if (i % 2 == 0)
															out.println("<tr style='background-color:#8DEEEE'>");
														else
															out.println("<tr>");
														out.println("<td height=10px align='center'>" + year
																+ "</td><td align='center'>" + month
																+ "</td><td align='center'>" + empName
																+ "</td><td align='center'>" + section
																+ "</td><td align='center'>" + pName
																+ "</td><td align='center'>" + service
																+ "</td><td align='center'>" + wd
																+ "</td><td align='center'>" + remark
																+ "</td></tr>");
													}
													out.println("</table>");
													int pageCount = Integer.parseInt(session.getAttribute(
															ip2 + "pageCount").toString().trim());
													if (pageCount != 1) {
														for (int j = 0; j < pageCount; j++) {
															out
																	.println("<a href='ManageRecordAction!showByPage?pageNow="
																			+ (j + 1)
																			+ "'>["
																			+ (j + 1)
																			+ "]</a>&nbsp;");
														}
													}
												} else {
													out
															.println("<div style='margin:1% 0% 0% -45%'><font color=red size=4>No any MonthlyControlling Records in this Month!</font></div>");
												}
												out.println("</center>");
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