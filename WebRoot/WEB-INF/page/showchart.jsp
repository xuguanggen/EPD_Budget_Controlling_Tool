<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.xgg.epd.dbs.BasicDB"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="css/noline.css" rel="stylesheet" type="text/css" />
		<base href="<%=basePath%>">
		<title>SHOW CHART PAGE</title>
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">



	</head>

	<body>
		<center>
			<form action="AllChartAction" method="post">
				<select name="proType" id="proType">
					<option value="">
						--Plz select one project--
					</option>
					<%
						BasicDB basicDb = new BasicDB();
						ArrayList<String> arrayList = basicDb
								.SQuerydb("select distinct proName from tb_Summary");
						int i = 0;
						if (session.getAttribute("pro"
								+ session.getAttribute("EmpName").toString()) == null) {
							while (i < arrayList.size()) {
								out.println("<option value=" + arrayList.get(i) + ">"
										+ arrayList.get(i) + "</option>");
								i++;
							}
						} else {
							String selectedProName = session.getAttribute(
									"pro" + session.getAttribute("EmpName").toString())
									.toString().trim();
							while (i < arrayList.size()) {
								if (selectedProName.equals(arrayList.get(i).trim())) {
									out.println("<option selected value="
											+ arrayList.get(i) + ">" + arrayList.get(i)
											+ "</option>");
								} else {
									out.println("<option value=" + arrayList.get(i) + ">"
											+ arrayList.get(i) + "</option>");
								}
								i++;
							}
						}
					%>
				</select>
				
				<select name="detail" id="detail">
					<option value="">
						--Plz select one field
					</option>
					<%
					if(session.getAttribute("det"+session.getAttribute("EmpName").toString())==null)
					{
					   out.println("<option value='Total'>Total</option>"+"<option value='EPD'>EPD</option>"+
					   "<option value='AE'>AE</option>"+"<option value='RBEI'>RBEI</option>");
					}else
					{
					   String det=session.getAttribute("det"+session.getAttribute("EmpName").toString()).toString().trim();
					   if(det.equals("Total"))
					   { 
					      out.println("<option value='Total' selected>Total</option>"+"<option value='EPD'>EPD</option>"+
					   "<option value='AE'>AE</option>"+"<option value='RBEI'>RBEI</option>");
					   }else if(det.equals("EPD"))
					   {
					     out.println("<option value='Total'>Total</option>"+"<option value='EPD' selected>EPD</option>"+
					   "<option value='AE'>AE</option>"+"<option value='RBEI'>RBEI</option>");
					   }else  if(det.equals("AE"))
					   {
					    out.println("<option value='Total'>Total</option>"+"<option value='EPD'>EPD</option>"+
					   "<option value='AE' selected>AE</option>"+"<option value='RBEI'>RBEI</option>");
					   }else 
					   {
					     out.println("<option value='Total'>Total</option>"+"<option value='EPD'>EPD</option>"+
					   "<option value='AE'>AE</option>"+"<option value='RBEI' selected>RBEI</option>");
					   }
					}
					 %>
				</select>
				<input type="submit" value="ShowChart" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a
					href="/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/AdminFunction.jsp">BACK
					TO AdminFunction&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</form>
		</center>
	
		<%
			String fileName = (String) request.getAttribute("filename");
			if (fileName != null) {
				String graphURL = request.getContextPath()
						+ "/chart/DisplayChart?filename=" + fileName;
		%>
		<img src="<%=graphURL%>" width=1000 height=500
			usemap="#'<%=fileName%>'">
		<%
			}
		%>
	</body>
</html>
