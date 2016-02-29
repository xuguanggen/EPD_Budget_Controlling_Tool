<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList,com.xgg.epd.beans.*;"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Success Submit</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
	<%
   String ip2=request.getRemoteAddr().toString();
   String empname = session.getAttribute(ip2+"EmpName").toString();
   session.setAttribute(empname,null);
   if(session.getAttribute(ip2+"EmpName")==null)
   {
   out.println("<script type='text/javascript'>alert('Hello,please Login first!');window.document.location.href='index.jsp';</script>");
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
										<jsp:include page="tpmSidebar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div id="display" style="width: 100%;">
										<div id="title">
										     Records
										<div id="employee">
												<s:property value="#session.time" />
												<%String ip=request.getRemoteAddr().toString();
												out.println(session.getAttribute(ip+"EmpName"));
												 %>
												!
												<img src="images/logout.png" />
												<font size=1><a href="LoginOutAction"
													style="color: black;"
													onClick="return window.confirm('Really want to exit the system?')";>Log
														out</a> </font>
											</div>			
										</div>
										<div id="tpmtable">
												<div style="margin: 0% 30% 0 0"
												style="width:100%;height:48px">
												<img alt="" src="images/gou.png">
												<font size=3 face="Comic Sans MS">Congratulations,these
													records were saved in the server successfully!</font>
											</div>		
											<hr width=100% size=3 color=#5151A2
												style="filter: progid : DXImageTransform.Microsoft.Shadow ( color #5151A2, direction : 145, strength : 15 )">									
												<center>
												<div style="margin:0% 1% 0% -60%"><font color=green size=4><b>MonthlyControlling Records List:</b></font></div>
											<%
												ArrayList<MonthlyControllingBean> alArrayList = (ArrayList<MonthlyControllingBean>) session
														.getAttribute(ip+"show");
												out.println("<table cellspacing=0 border=1 bordercolor=green>");
												out
														.println("<tr><th>Year</th><th>Month</th><th>EmpName</th><th>Section</th><th>ProName</th><th>ServiceNr</th><th>WorkingDays</th><th>Remark</th></tr>");												
												for (int i = alArrayList.size()-1; i>=0; i--) {
													MonthlyControllingBean mcb = alArrayList.get(i);
													if (i % 2 == 0)
														out.println("<tr style='background-color:#8DEEEE'>");
													else
														out.println("<tr>");
													out.println("<td height=10px align='center'>" + mcb.getYear()
															+ "</td><td align='center'>" + mcb.getMonth()
															+ "</td><td align='center'>" + mcb.getEmpname()
															+ "</td><td align='center'>" + mcb.getSection()
															+ "</td><td align='center'>" + mcb.getProname()
															+ "</td><td align='center'>" + mcb.getService()
															+ "</td><td align='center'>" + mcb.getWorkingdays()
															+ "</td><td align='center'>" + mcb.getRemark()
															+ "</td></tr>");
												}
												out.println("</table>");
											 int pageCount=Integer.parseInt(session.getAttribute(ip+"showCount").toString().trim());
											 if(pageCount!=1)
											 {
											    for(int j=0;j<pageCount;j++)
											    {
											       out.println("<a href='ManageRecordAction!ShowResultByPage?showNow="+(j+1)+"'>["+(j+1)+"]&nbsp;</a>");
											    }
											 }
											 session.setAttribute(empname,null);
											%>
										</center>
										<!-- end comTable -->
									</div>
									<!-- end display -->
								</td>
							</tr>
						</table>
					</td>
				</tr>

			</table>
			<div style="margin:24% 0 0 0px"><jsp:include page="footer.jsp"></jsp:include></div>	
		</div>
		<!-- end container -->
	</body>
</html>