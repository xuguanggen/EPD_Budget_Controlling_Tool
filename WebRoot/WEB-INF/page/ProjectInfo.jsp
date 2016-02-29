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
			content="13;url=/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/adminAddPro.jsp">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Project Info</title>
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
											Project Info
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
													the new project successfully!</font>
											</div>
											<div class="STYLE1" id="time"
												style="color: #f00; margin: 0% 40% 0% 20%">
											</div>
											<br />
											<div style="margin: 0% 30% 0% 20%">
												<table border=1 border="0" cellspacing="0"
													style="width: 50%; height: 70%">
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>Project Name：
														</td>
														<td width="300px" height="30">
															${pname }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>TPM：
														</td>
														<td width="300px" height="30">
														   ${tpm }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>IsBp:
														</td>
														<td width="300px" height="30">
															<%
																String bp=request.getAttribute("bp").toString().trim();
																if("Yes".equals(bp))
																{
																	out.println(bp);	  
																}else{
																	out.println(bp);
																}
															%>
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>Budget:
														</td>
														<td width="300px" height="30">
															${budget }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>EPD_Budget:
														</td>
														<td width="300px" height="30">
															${ebudget }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>AE_Budget:
														</td>
														<td width="300px" height="30">
															${abudget }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>RBEI_Budget:
														</td>
														<td width="300px" height="30">
															${rbudget }
														</td>
													</tr>													
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>Status:
														</td>
														<td width="300px" height="30">
															${status }
														</td>
													</tr>												
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>OEM:
														</td>
														<td width="300px" height="30">
															${oem }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>Customer:
														</td>
														<td width="300px" height="30">
															${customer }
														</td>
													</tr>
													<tr>
														<td width="100px" align="right">
															<span class="font_red"></span>Remark:
														</td>
														<td width="300px" height="30">
															${remark }
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