<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link href="css/register.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="refresh"
			content="13;url=/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/adminAddEmp.jsp">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Employee Info</title>
	</head>
	<script language="javascript">
	var times = 13;
	clock();
	function clock() {
		window.setTimeout('clock()', 1000);
		times = times - 1;
		time.innerHTML = times+" seconds automatically jump";
	}
</script>
	<style type="text/css">
.STYLE1 {
	color: black
}
</style>
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
									<div style="width: 100%; height: 65%">
										<div id="title">
											Employee Info
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
											<div style="margin: 0% 30% 0% 0"
												style="width:100%;height:48px">
												<img alt="" src="images/gou.png">
												<font size=3 face="Comic Sans MS">Congratulations,add
													the new employee successfully!</font>
											</div>
											<div class="STYLE1" id="time" style="color: #f00;margin:0% 40% 0% 20%">
												13 	
											</div>
											<br/>
											<div style="margin:0% 30% 0% 20%">
											<table border=1 border="0" cellspacing="0" style="width:50%;height:70%">
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Employee ID：
													</td>
													<td width="300px" height="30">
														${empid }
													</td>
												</tr>
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Employee Name：
													</td>
													<td width="300px" height="30">
														${empname }
													</td>
												</tr>
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Section：
													</td>
													<td width="300px" height="30">
														${section }
													</td>
												</tr>
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Multi-role:
													</td>
													<td width="300px" height="30">
														${murole }
													</td>												
												</tr>
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Email：
													</td>
													<td width="300px" height="30">
														<a href=mailto:${email}>${email }</a>
													</td>
												</tr>
												<tr>
													<td width="200px" align="right">
														<span class="font_red"></span>Mark:
													</td>
													<td width="300px" height="30">
														<%
														String mark=request.getAttribute("mark").toString().trim();
														if("1".equals(mark))
														{
														   out.println("TPM");
														}else if("2".equals(mark))
														{
														   out.println("Administrator");
														}else if("0".equals(mark))
														{
														   out.println("General employee");
														}														
														 %>
													</td>
												</tr>
											</table>
											</div>
										</div>										
									</div>
									<!-- end comTable -->
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