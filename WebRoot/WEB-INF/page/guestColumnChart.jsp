<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Show Chart</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
			//document.getElementById("proType").length = 1; //根据ID获取指定元素，并赋值
			xmlHttp.onreadystatechange = cityList; //指定onreadystatechange处理函数
			var y=document.getElementById("year").value;
			var url = "/EPD_Budget_Controlling_Tool/TpmChangeAction!createOption?tpmname="
					+ objVal+"&year="+y; //请求的URL地址
			xmlHttp.open("GET", url, true);
			xmlHttp.send(null);
		}
		
		function YearChange(objVal) {
			createXMLHttpRequest(); //创建XMLHttpRequest对象
			//document.getElementById("proType").length = 1; //根据ID获取指定元素，并赋值
			xmlHttp.onreadystatechange = cityList; //指定onreadystatechange处理函数
			var y=document.getElementById("tpmname").value;
			var url = "/EPD_Budget_Controlling_Tool/TpmChangeAction!createOption?tpmname="
					+ y+"&year="+objVal; //请求的URL地址
			xmlHttp.open("GET", url, true);
			xmlHttp.send(null);
		}
		function cityList() { //onreadystatechange的处理函数
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					parseXML(xmlHttp.responseXML); //解析服务器返回的XML数据
				}
			}
		}
	
		//解析xml信息，以添加地市
		/*function parseXML(xmlDoc) {
			var len = xmlDoc.getElementsByTagName("proType");
			//获取XML数据中所有的“city”元素对象集合
			var _citySel = document.getElementById("proType"); //根据ID获取页面中的select元素
			for ( var i = 0; i < len.length; i++) { //遍历XML数据并给select元素添加选项
				var opt = document.createElement("OPTION"); //创建option对象
				opt.text = xmlDoc.getElementsByTagName("proType")[i].firstChild.data;
				//指定新创建元素的text属性值
				opt.value = xmlDoc.getElementsByTagName("proType")[i].firstChild.data;
				//指定新创建元素的value属性值
				_citySel.add(opt); //为select元素添加option
			}
		}*/
		  function newSubmit()
		{
		     var str=document.getElementsByName("myproname");
		     var m=document.getElementById("year").value;
		     if(m=='/')
		     {
		         alert("Please select one year first !");
		         return false;
		     }
	         var objarray=str.length;
			 var chestr="";
	         for (i=0;i<objarray;i++)
			{
			  if(str[i].checked == true)
			  {
			   chestr+=str[i].value+",";
			  }
			}
			if(chestr=="")
			{
			  alert("Please select one project first!");
			  return false;
			}
			else
			{
			   document.form1.action =contextPath+"/TpmChartAction!createForGuest";
	           document.form1.submit();
	           return true;
			}
		}
		
		function makeothersChecked() {
			var x = document.getElementsByName("chartType");
			if (x[0].checked == true) {
				x[1].checked = true;
				x[2].checked = true;
				x[3].checked = true;
				var str=document.getElementsByName("myproname");
		         var objarray=str.length;
				 var chestr="";
		         for (i=0;i<objarray;i++)
				{
				  if(str[i].checked == true)
				  {
				   chestr+=str[i].value+",";
				  }
				}
				if(chestr!=""){
					document.form1.action = "/EPD_Budget_Controlling_Tool/TpmChartAction!createForGuest";
					document.form1.submit();
				}
			} else {
				x[1].checked = false;
				x[2].checked = false;
				x[3].checked = false;
			}
	
		}
	
		function check() {		
			var x = document.getElementsByName("chartType");
			if (!(x[1].checked == true && x[2].checked == true && x[3].checked == true)) {
				x[0].checked = false;
			} else {
				x[0].checked = true;
			}
			var str=document.getElementsByName("myproname");
		         var objarray=str.length;
				 var chestr="";
		         for (i=0;i<objarray;i++)
				{
				  if(str[i].checked == true)
				  {
				   chestr+=str[i].value+",";
				  }
				}
				if(chestr!=""){
					document.form1.action = "/EPD_Budget_Controlling_Tool/TpmChartAction!createForGuest";
					document.form1.submit();
				}
		}
		</script>
		<title>Administrator Chart</title>
	</head>
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
										<jsp:include page="guestSideBar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div id="display" style="width: 100%;">
										<div id="title">
											Show Chart
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
											<form action="" method="post" name="form1" id="form1">
												<table width="100%" border="1" bordercolor=green
													height="50%" cellspacing=0>
													<tr style="height: 5px">
														<td align="center">
															Year:&nbsp;&nbsp;&nbsp;
															<%
																Calendar calendar = Calendar.getInstance();
																int year = calendar.get(Calendar.YEAR);
																int selectYear = 0;
																if (session.getAttribute(ip + "guestchartYear") != null) {
																	selectYear = Integer.parseInt(session.getAttribute(
																			ip + "guestchartYear").toString().trim());
																}
																out
																		.println("<select name='year' id='year' onchange='YearChange(this.value);'>");
																for (int k = 1; k <= 5; k++) {
																	if (selectYear != (year - k + 1))
																		out.println("<option value='" + (year - k + 1) + "'>"
																				+ (year - k + 1) + "</option>");
																	else
																		out.println("<option selected value='" + (year - k + 1)
																				+ "'>" + (year - k + 1) + "</option>");
																}
																out.println("</select>");
															%>
														</td>
														<td width="35%" align="right">
															&nbsp;&nbsp;&nbsp;Options:&nbsp;&nbsp;&nbsp;
														</td>
														<td width="20%">
															<%
																String[] original = { "EPD", "AE", "RBEI" };
																if (request.getAttribute("chartType") == null
																		|| "".equals(request.getAttribute("chartType").toString())) {
																	out
																			.println("<div style='width:180px;height:10px;float:left'><input type='checkbox'  name='chartType' id='chartType' onChange='makeothersChecked()'/>"
																					+ "All"
																					+ "<input type='checkbox' name='chartType' id='chartType' value='EPD' onChange='check()'/>"
																					+ "EPD"
																					+ "<input type='checkbox'  name='chartType' id='chartType' value='AE' onChange='check()'/>"
																					+ "AE"
																					+ "<input type='checkbox'  name='chartType' id='chartType' value='RBEI' onChange='check()'/>"
																					+ "RBEI</div>");
																} else {
																	String chartType = request.getAttribute("chartType").toString();
																	String[] arrStrings = chartType.split(", ");
																	if (arrStrings.length == 4) {
																		out
																				.println("<div style='width:180px;height:10px;float:left'><input type='checkbox' name='chartType' id='chartType' value='All' onChange='makeothersChecked()' checked='true'/>"
																						+ "All"
																						+ "<input type='checkbox'  name='chartType' id='chartType' value='EPD' onChange='check()' checked='true'/>"
																						+ "EPD"
																						+ "<input type='checkbox'  name='chartType' id='chartType' value='AE' onChange='check()' checked='true'/>"
																						+ "AE"
																						+ "<input type='checkbox' name='chartType' id='chartType' value='RBEI' onChange='check()' checked='true'/>"
																						+ "RBEI</div>");
																	} else {
																		out
																				.println("<div style='width:180px;height:10px;float:left'><input type='checkbox' name='chartType' id='chartType' value='All' onChange='makeothersChecked()'/>All");
																		for (int i = 0; i < original.length; i++) {
																			int k = 0;
																			for (k = 0; k < arrStrings.length; k++) {
																				if (arrStrings[k].equals(original[i])) {
																					out
																							.println("<input type='checkbox' name='chartType' id='chartType' value='"
																									+ original[i]
																									+ "' onChange='check()' checked='true'/>"
																									+ original[i]);
																					break;
																				}
																			}
																			if (k == arrStrings.length) {
																				out
																						.println("<input type='checkbox' name='chartType' id='chartType' value='"
																								+ original[i]
																								+ "' onChange='check()'/>"
																								+ original[i]);
																			}
																		}
																		out.println("</div>");
																	}
																}
															%>
														</td>
													</tr>
													<tr>
														<td colspan="4" style="width: 95%">
															<div id="pro"
																style="width: 100%; height: 105px; overflow-y: scroll;">
																	<%
																	String ename = session.getAttribute(ip + "EmpName").toString();
																	String sql = "select distinct proName from tb_GuestPro where GTPM='"
																			+ ename + "'";
																	BasicDB basicDB = new BasicDB();
																	ArrayList<String> al = basicDB.SQuerydb(sql);
																	if (al.size() != 0 && al != null) {
																		String[] clist = (String[]) request.getAttribute("checkPro");
																		out
																				.println("<table cellspacing=0 border=1 corlor=green width='150%' height='100%'>");
																		int i = 0;
																		for (i = 0; i < al.size(); i++) {
																			String temp = al.get(i).toString().trim();
																			if (i % 5 == 0) {
																				out
																						.println("<tr style='height:3px'><td align='left' style='height:3px;'>");
																			} else if (i % 5 == 1) {
																				out.println("<td align='left' style='height:3px'>");
																			} else if (i % 5 == 2) {
																				out.println("<td align='left' style='height:3px'>");
																			} else if (i % 5 == 3) {
																				out.println("<td align='left' style='height:3px'>");
																			} else if ((i + 1) % 5 == 0) {
																				out.println("<td align='left' style='height:3px'>");
																			}
																			int k = 0;
																			if (clist != null) {
																				for (k = 0; k < clist.length; k++) {
																					if (clist[k].equals(temp)) {
																						out
																								.println("<input checked=true type='checkbox' name='myproname' id='myproname' value='"
																										+ temp
																										+ "'/>&nbsp;&nbsp;"
																										+ temp);
																						break;
																					}
																				}
																				if (k == clist.length) {
																					out
																							.println("<input type='checkbox' name='myproname' id='myproname' value='"
																									+ temp + "'/>&nbsp;&nbsp;" + temp);
																				}
																			} else {
																				out
																						.println("<input type='checkbox' name='myproname' id='myproname' value='"
																								+ temp + "'/>&nbsp;&nbsp;" + temp);
																			}
																			if (i % 5 == 0 || i % 5 == 1 || i % 5 == 2 || i % 5 == 3) {
																				out.println("</td>");
																			} else if (i % 5 == 4) {
																				out.println("</td></tr>");
																			}
																		}
																		if (i % 5 == 0) {
																			out.println("</table>");
																		} else {
																			out.println("</tr></table>");
																		}
																	} else {
																		out.println("<b>NULL</b>");
																	}
																%>
															</div>
														</td>
														<td style="width: 5%">
															<input value="Show Chart" type="submit"
																onClick="return newSubmit()"
																style="height: 105px; width: 162px" />
														</td>
													</tr>
												</table>
											</form>
											<%
												String[] fileNames = (String[]) request.getAttribute("filename");
												String totalName=(String)request.getAttribute("totalFileName");
												//String []pro=(String[])request.getAttribute("checkPro");
												if (fileNames != null) {
													int i = 0;
													for (i = 0; i < fileNames.length; i++) {
														String graphURL = request.getContextPath()
																+ "/chart/DisplayChart?filename=" + fileNames[i];
											%>
											<img src="<%=graphURL%>" usemap="#'<%=fileNames[i]%>'">
											<%
												}
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