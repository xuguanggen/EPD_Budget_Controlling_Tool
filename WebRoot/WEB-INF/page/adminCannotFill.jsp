<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*,com.xgg.epd.dbs.*" pageEncoding="utf-8"%>
<%@ page import="com.xgg.epd.beans.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
			<meta http-equiv="refresh" content="8;url=/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/adminHome.jsp""> 
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Fill My MonthlyControlling</title>
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
	</head>
	
   	<script language="javascript">
	var times = 8;
	clock();
	function clock() {
		window.setTimeout('clock()', 1000);
		times = times - 1;
		time.innerHTML = times;
	}
</script>
	<style type="text/css">
.STYLE1 {
	color: black
}
</style>
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
											Fill &nbsp;MonthlyControlling
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
										     <font size=4 color="#FF3030">Sorry,&nbsp; you can not fill MonthlyControlling today!</font>
											 <div class="STYLE1" id= "time" style="color:#f00">7</div> seconds automatically jump</div>
										</div>
										<!-- end comTable -->
									<!-- end display -->
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div style="margin: 0% 0 0 0px"><jsp:include
					page="footer.jsp"></jsp:include></div>
		</div>
		<!-- end container -->
	</body>
</html>