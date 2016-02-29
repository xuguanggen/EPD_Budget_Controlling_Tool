<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.struts2.components.Else"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.xgg.epd.beans.MonthlyControllingBean"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Administrator Set Rate</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" language="javaScript">
		 var contextPath="<%=request.getContextPath()%>";
	var xmlHttp = false; //全局变量，用于记录XMLHttpRequest对象
	function createXMLHttpRequest() {
		if (window.ActiveXObject) { //Internet Explorer时，创建XMLHttpRequest对象的方法
			try {
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
					//旧版本的Internet Explorer，创建XMLHttpRequest对象
				} catch (e) {
					window.alert("创建XMLHttpRequest对象错误" + e);
				}
			}
		} else if (window.XMLHttpRequest) { //mozilla时，创建XMLHttpRequest对象的方法
			xmlHttp = new XMLHttpRequest();
		}
		if (!(xmlHttp)) { //未成功创建XMLHttpRequest对象
			window.alert("创建XMLHttpRequest对象异常！");
		}
	}

	//下拉列表项改变时的操作
	function proChange(objVal) {
		createXMLHttpRequest(); //创建XMLHttpRequest对象
		document.getElementById("empname").length = 1; //根据ID获取指定元素，并赋值
		xmlHttp.onreadystatechange = cityList; //指定onreadystatechange处理函数
		var url = "/EPD_Budget_Controlling_Tool/SectionChangeAction!createOption?section="
				+ objVal; //请求的URL地址
		xmlHttp.open("GET", url, true);
		xmlHttp.send(null);
	}
	function change(objVal) {
		//alert(objVal);
		//createXMLHttpRequest();
		//xmlHttp.onreadystatechange = cityList;
		//var empname = document.getElementById("empname").value;
		//var url = "/EPD_Budget_Controlling_Tool/showMCAction?empname="
		//	+ empname + "&month=" + objVal;
		//xmlHttp.open("GET", url, true);
		//xmlHttp.send(null);

		document.getElementById("form1").submit();
	}

	function ChangeSubmit(objVal) {
		var month = document.getElementById("month").value;
		var r = /^\+?[1-9][0-9]*$/;
		if (r.test(month)) {
			document.getElementById("form1").submit();
		}
	}
	function cityList() { //onreadystatechange的处理函数
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				parseXML(xmlHttp.responseXML); //解析服务器返回的XML数据
			}
		}
	}

	//解析xml信息，以添加地市
	function parseXML(xmlDoc) {	
		var len = xmlDoc.getElementsByTagName("empname");
		//获取XML数据中所有的“city”元素对象集合
		var _citySel = document.getElementById("empname"); //根据ID获取页面中的select元素
		for ( var i = 0; i < len.length; i++) { //遍历XML数据并给select元素添加选项
			var opt = document.createElement("OPTION"); //创建option对象
			opt.text = xmlDoc.getElementsByTagName("empname")[i].firstChild.data;
			//指定新创建元素的text属性值
			opt.value = xmlDoc.getElementsByTagName("empname")[i].firstChild.data;
			//指定新创建元素的value属性值
			_citySel.add(opt); //为select元素添加option
		}
	}
	function warning()
	{
	     var res=window.confirm("Really want to delete these records?");
	       if(res==true){
		      
		       var vsection=document.form1.section.options[document.form1.section.selectedIndex].value;
		      
		       var vempname=document.form1.empname.options[document.form1.empname.selectedIndex].value;
		      
		       var vmonth=document.form1.month.options[document.form1.month.selectedIndex].value;
		       document.getElementById("form2").action =contextPath+"/showMCAction!delRecord?section="+vsection+"&month="+vmonth+"&empname="+vempname;
	           document.getElementById("form2").submit();
           }
           else
           {
              return ;
           }
	}
</script>
	</head>
	<body>
	<%
   String ip2=request.getRemoteAddr().toString();
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
										<jsp:include page="adminSidebar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div id="display" style="width: 100%;">
										<div id="title">
											Correct
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
										<div id="comtable">
											<br />
											<center>
												<form action="showMCAction" name="form1" id="form1"
													method="post">
													Section:
													<select id="section" name="section"
														onchange="proChange(this.value);">
														<option value="">
															--Please select the section
														</option>
														<%
															if (request.getAttribute("ModSection") != null) {
																String section = request.getAttribute("ModSection").toString();
																if("EPD".equals(section))
																{
																   out.println("<option selected value='EPD'>EPD</option>");
																}
																else
																{
																   out.println("<option value='EPD'>EPD</option>");
																}
																for (int i = 1; i <= 6; i++) {
																	String temp_section = "EPD" + i;
																	if (section.equals(temp_section)) {
																		out.println("<option selected value='" + temp_section
																				+ "'>" + temp_section + "</option>");
																	} else {
																		out.println("<option value='" + temp_section + "'>"
																				+ temp_section + "</option>");
																	}
																}
															} else {
															    out.println("<option value='EPD'>EPD</option>");
																for (int i = 1; i <= 6; i++) {							
																	out.println("<option value='EPD" + i + "'>EPD" + i
																			+ "</option>");
																}
															}
														%>
													</select>
													&nbsp;&nbsp;&nbsp; Name:
													<select id="empname" name="empname"
														onchange="ChangeSubmit(this.value);">
														<option value="">
															--Please select the employee
														</option>
														<%
															if (session.getAttribute("empList") != null) {
																List<String> list = (List<String>) session
																		.getAttribute("empList");
																String emp = "";
																if (request.getAttribute("ModName") != null) {
																	emp = request.getAttribute("ModName").toString();
																}
																for (int i = 0; i < list.size(); i++)
																	if (emp.equals(list.get(i))) {
																		out.println("<option selected value='" + list.get(i)
																				+ "'>" + list.get(i) + "</option>");
																	} else {
																		out.println("<option value='" + list.get(i) + "'>"
																				+ list.get(i) + "</option>");
																	}
															}
														%>
													</select>
													Month:
													<select id="month" name="month"
														onchange="change(this.value);">
														<option value="">
															--Please select the month
														</option>
														<%
															if (request.getAttribute("ModMonth") == null) {
																for (int i = 1; i <= 12; i++) {
																	out.println("<option value='" + i + "'>" + i + "</option>");
																}
															} else {
																String month = request.getAttribute("ModMonth").toString();
																for (int i = 1; i <= 12; i++) {
																	String tmonth = i + "";
																	if (tmonth.equals(month)) {
																		out.println("<option value='" + tmonth + "' selected>"
																				+ tmonth + "</option>");
																	} else {
																		out.println("<option value='" + tmonth + "'>" + tmonth
																				+ "</option>");
																	}
																}
															}
														%>
													</select>
												</form>
												<br />
												<s:if test="#request.current[0]!= null">
													<form action="" method="post" name="form2" id="form2">
														<table border="1" bordercolor=green cellspacing=0>
															<tr>
																<th>
																	IsDelete
																</th>
																<th>
																	MID
																</th>
																<th>
																	EmpName
																</th>
																<th>
																	Section
																</th>
																<th>
																	Year
																</th>
																<th>
																	Month
																</th>
																<th>
																	ProName
																</th>
																<th>
																	ServiceNr
																</th>
																<th>
																	Remark
																</th>
																<th>
																	WorkingDays
																</th>
															</tr>
															<s:iterator value="#request.current" var="s"
																status="stuts">
																<tr
																	<s:if test="#stuts.odd == true">style="background-color:#acf7f3"</s:if>>
																	<td align="center">
																		<input type="checkbox" id="dels" name="dels"
																			value="<s:property value='#s.mid'/>" />
																	</td>
																	<td align="center">
																		<font style="color: blue"> <a
																			href="/EPD_Budget_Controlling_Tool/showMCAction!modRecord?mid=<s:property value='#s.mid'/>"><s:property
																					value="#s.mid" /> </a> </font>
																	</td>
																	<td align="center">
																		<s:property value="#s.empname" />
																	</td>
																	<td align="center">
																		<s:property value="#s.section" />
																	</td>
																	<td align="center">
																		<s:property value="#s.year" />
																	</td>
																	<td align="center">
																		<s:property value="#s.month" />
																	</td>
																	<td align="center">
																		<s:property value="#s.proname" />
																	</td>
																	<td align="center">
																		<s:property value="#s.service" />
																	</td>
																	<td align="center">
																		<s:property value="#s.remark" />
																	</td>
																	<td align="center">
																		<s:property value="#s.workingdays" />
																	</td>
																</tr>
															</s:iterator>
														</table>
														<%
															if (request.getAttribute("pageCount") != null) {
																	int pageCount = Integer.parseInt(request.getAttribute(
																			"pageCount").toString());
																	for (int i = 1; i <= pageCount; i++) {
																		String modname = request.getAttribute("ModName")
																				.toString().trim().replace(" ", "@");
																		out
																				.println("<a href=/EPD_Budget_Controlling_Tool/showMCAction!showPart?month="
																						+ request.getAttribute("ModMonth")
																								.toString()
																						+ "&empname="
																						+ modname
																						+ "&section="
																						+ request.getAttribute("ModSection")
																								.toString()
																						+ "&pageNow="
																						+ i
																						+ ">[" + i + "]</a>");
																	}
																}
														%>
														<br/>
														<input align="right" type="submit" value="Delete" onClick="warning();"/>
													</form>
												</s:if>
											</center>
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