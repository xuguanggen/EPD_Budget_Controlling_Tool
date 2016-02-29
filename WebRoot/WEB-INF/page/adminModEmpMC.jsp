<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.struts2.components.Else"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>
<%@page import="com.xgg.epd.beans.MonthlyControllingBean"%>
<%@page import="org.jfree.data.time.Month"%>
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
		<script type="text/javascript" language="javaScript">
		var xmlHttp = false; //全局变量，用于记录XMLHttpRequest对象
	function checkworkingdays() {
		document.getElementById("wdwaring").innerHTML = "<font color='red'>Please input a number</font>";
	}
	function checkWorkingDays(workingdays) {
		if (workingdays == '') {
			document.getElementById("wdwaring").innerHTML = "<img src='images/wrong.gif'></img><font color='red'>must be filled!</font>";
			document.getElementById("wdwaring").value='';
		} else {
		   workingdays=workingdays.replace(/\s/gi,'');
		   document.getElementById("workingdays").value=workingdays;
			var yd =  /^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/; 
			if (!yd.test(workingdays)) {
				document.getElementById("wdwaring").innerHTML = "<img src='images/wrong.gif'></img><font color='red'>Please input a number</font>";
			} else {
				document.getElementById("wdwaring").innerHTML = "<img src='images/right.gif'></img>";
			}
		}
	}
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
		document.getElementById("service").length = 1; //根据ID获取指定元素，并赋值
		xmlHttp.onreadystatechange = cityList; //指定onreadystatechange处理函数
		var url = "/EPD_Budget_Controlling_Tool/SectionChangeAction!createServices?section="
				+ objVal; //请求的URL地址
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
	function parseXML(xmlDoc) {
		var len = xmlDoc.getElementsByTagName("service");
		//获取XML数据中所有的“city”元素对象集合
		var _citySel = document.getElementById("service"); //根据ID获取页面中的select元素
		for ( var i = 0; i < len.length; i++) { //遍历XML数据并给select元素添加选项
			var opt = document.createElement("OPTION"); //创建option对象
			opt.text = xmlDoc.getElementsByTagName("service")[i].firstChild.data;
			//指定新创建元素的text属性值
			opt.value = xmlDoc.getElementsByTagName("service")[i].firstChild.data;
			//指定新创建元素的value属性值
			_citySel.add(opt); //为select元素添加option
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
											Update MonthlyControlling
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
											<center>
												<br />
												<form action="AdminUpdateMCAction" method="post" name="form1" id="form1">
													<table>
														<tr>
															<td width="160px" align="right">
																Employee Name:
															</td>
															<td>
																<input disabled="disabled"  type="text" name="empname" maxlength="20"
																	id="empname" readonly
																	style="width: 300px; height: 20px;"
																	value="<s:property value="#session.modMC.empname"/>" />
															</td>
														</tr>
														<tr>
															<td width="160px" align="right">
																Section:
															</td>
															<td width="300px" height="30" >
																<select name="section" id="section" onchange="proChange(this.value);"
																	style="width: 300px; height: 20px;">
																	<option>
																		--Please select one section
																	</option>
																	<%
																		String sec = ((MonthlyControllingBean) session
																				.getAttribute("modMC")).getSection().trim();
																		if("EPD".equals(sec))
																		{
																		   out.println("<option selected value='EPD'>EPD</option>");
																		}else
																		    out.println("<option  value='EPD'>EPD</option>");
																		for (int i = 1; i <= 6; i++) {
																			String tempSec = "EPD" + i;
																			if (tempSec.equals(sec)) {
																				out.println("<option selected value=" + tempSec + ">"
																						+ tempSec + "</option>");
																			} else {
																				out.println("<option value=" + tempSec + ">" + tempSec
																						+ "</option>");
																			}
																		}
																	%>
																</select>
															</td>
														</tr>
														<tr>
															<td width="160px" align="right">
																Month:
															</td>
															<td width="300px" height="30">
																<select name="month" id="month"
																	style="width: 300px; height: 20px;">
																	<option>
																		--Please select one section
																	</option>
																	<%
																		String month = ((MonthlyControllingBean) session
																				.getAttribute("modMC")).getMonth().trim();
																		for (int i = 1; i <= 12; i++) {
																			String tempSec = "" + i;
																			if (tempSec.equals(month)) {
																				out.println("<option selected value=" + tempSec + ">"
																						+ tempSec + "</option>");
																			} else {
																				out.println("<option value=" + tempSec + ">" + tempSec
																						+ "</option>");
																			}
																		}
																	%>
																</select>
															</td>
														</tr>
														<tr>
															<td width="160px" align="right">
																Project Name:
															</td>
															<td width="300px" height="30">
																<select name="proName" id="proName"
																	style="width: 300px; height: 20px;">
																	<option>
																		--Please select one project
																	</option>
																	<%
																		BasicDB basicDb = new BasicDB();
																		String sqlString = "select distinct proName from tb_Project where 1=?";
																		String[] paras1 = { "1" };
																		ArrayList<Object[]> alist = basicDb.queryDB(sqlString, paras1);
																		int i = 0;
																		String pro = ((MonthlyControllingBean) session
																				.getAttribute("modMC")).getProname().trim()
																				.replace(" ", "");
																		for (i = 0; i < alist.size(); i++) {
																			Object[] obj = alist.get(i);
																			String tempPro = obj[0].toString().trim().replace(" ", "");
																			if (tempPro.equals(pro)) {
																				out.println("<option selected value='" + obj[0].toString()
																						+ "'>" + obj[0].toString() + "</option>");
																			} else
																				out.println("<option value='" + obj[0].toString() + "'>"
																						+ obj[0].toString() + "</option>");
																		}
																	%>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																Service Nr:
															</td>
															<td>
																<select name="service" id="service"
																	style="width: 300px; height: 20px;">
																	<option>
																		--Please select one ServiceNr
																	</option>
																	<%
																	if(request.getAttribute("serviceList")!=null)
																	{
																	    List<String> list=(List<String>)request.getAttribute("serviceList");
																	    for(int j=0;j<list.size();j++)
																	    {
																	       out.println("<option value='"+list.get(j).toString().trim()+"'>"+list.get(j).toString().trim()+"</option>");
																	    }
																	}																										
																	 %>														
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																WorkingDays:
															</td>
															<td>
																<input type="text" name="workingdays" maxlength="20"
																	id="workingdays"
																	value="<s:property value='#session.modMC.workingdays'/>"
																	onfocus="javascript:checkworkingdays();"
																	onblur="javascript:checkWorkingDays(this.value)"
																	style="width: 300px; height: 20px;" />
															</td>
															<td id="wdwaring"></td>
														</tr>
														<tr>
															<td align="right">
																Remark:
															</td>
															<td>
																<%
																	String remark = ((MonthlyControllingBean) session
																			.getAttribute("modMC")).getRemark().trim();
																	out
																			.println("<textarea rows='4' cols='20' name='remark' id='remark' style='width:300px;height:80px;resize:none'>"
																					+ remark + "</textarea>");
																%>																
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="submit" value="Update"/>
															</td>
														</tr>
													</table>
												</form>
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