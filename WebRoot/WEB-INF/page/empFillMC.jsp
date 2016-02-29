<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*,com.xgg.epd.dbs.*" pageEncoding="utf-8"%>
<%@ page import="com.xgg.epd.beans.*"%>
<%@page import="com.xgg.epd.actions.FillAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
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
	<script type="text/javascript">
  var xmlhttp;
  $(document).ready(
		function() {
			if(window.XMLHttpRequest)
			{
			  xmlhttp=new XMLHttpRequest();
			}else
			{
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=init;
			xmlhttp.open("GET","/EPD_Budget_Controlling_Tool/InitProAction",true);
			xmlhttp.send();
		});
		
   function init()
   { 
      if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				//alert(xmlhttp.responseText); 
				var str=xmlhttp.responseText;
				var strs=new Array();
				strs=str.split(",");				
			$(".ddcombo").ddcombo( {
				minChars :0,
				options : strs
			});
			}
		}
   } 
  </script>
	<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
	function Del() {
		var str = document.getElementsByName("delid");
		var objarray = str.length;
		var chestr = "";
		for (i = 0; i < objarray; i++) {
			if (str[i].checked == true) {
				chestr += str[i].value + ",";
			}
		}
		if (chestr == "") {
			alert("Please select one record first!");
			return false;
		} else {
			if (confirm('Really want to delete selected records?')) {
				document.Rec.action = contextPath + "/ManageRecordAction!del";
				document.Rec.submit();			  
			} else {
				return false;
			}
		}
	}
	function Sub() {
		if (confirm("Really want to upload these records?")) {
			document.Rec.action = contextPath + "/ManageRecordAction!insert";
			document.Rec.submit();
		} else {
			return false;
		}
	}

	function checkworkingdays() {
		document.getElementById("wdwaring").innerHTML = "<font color='red'>Please input a number</font>";
	}

	function checkWorkingDays(workingdays) {
		if (workingdays == '') {
			document.getElementById("Workingdays").value =1;
		} else {
			var yd = /^\d*(\.(5|0))?$/;
			if (!yd.exec(workingdays)) {
				document.getElementById("Workingdays").value=1;
			} 
		}
	}
	function plus()
	{
	    var days=parseFloat(getObject("Workingdays").value);
	    var d=parseFloat(0.5); 
	    getObject("Workingdays").value=days+d;
	}
	function min()
	{
	    var days=parseFloat(getObject("Workingdays").value);
	    var d=parseFloat(0.5);
	    if(days>=0.5)
	    {
	         getObject("Workingdays").value=days-d;  
	    }
	    
	}
	function getObject(id)
	{
	   return document.getElementById(id);
	}
	var resubmit=false; 
	function checkCFSubmit()
	{
	      if(resubmit)   
        {   
          return   false;   
        }   
        else   
        {   
          resubmit=true;   
          return true;  
          } 
	}	
	var canNav = false;
	function canNavigate() {
		return canNav;
	} 
	function reduce_onclick(mid)
	{
	   var name='s'+mid;
	   var before=parseFloat(getObject(name).value);
	   var d=parseFloat(0.5);
	   if(before>=0.5)
	   {
	      getObject(name).value=before-d;
	      var sd= parseFloat(document.getElementById("myTable").rows[2].cells[1].innerHTML)-d;
	      var rd= parseFloat(document.getElementById("myTable").rows[3].cells[1].innerHTML)+d;
	      document.getElementById("myTable").rows[2].cells[1].innerHTML=sd;	    
	      document.getElementById("myTable").rows[3].cells[1].innerHTML=rd;   
	       var xmlHttp;  
			if(window.ActiveXObject)  
			{  
			    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
			}  
			else if(window.XMLHttpRequest)  
			{  
			   xmlHttp=new XMLHttpRequest();  
			} 
			var datastr="id="+mid;
			var url="ManageRecordAction!ReduceAgain";
			xmlHttp.open("POST",url,false);  
			xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
			xmlHttp.send(datastr);    
	   }	
	}
	function add_onclick(mid)
	{
	   var name='s'+mid;
	   var before=parseFloat(getObject(name).value);
	   var d=parseFloat(0.5);		  
      getObject(name).value=before+d;
      var sd= parseFloat(document.getElementById("myTable").rows[2].cells[1].innerHTML)+d;
      var rd= parseFloat(document.getElementById("myTable").rows[3].cells[1].innerHTML)-d;
      document.getElementById("myTable").rows[2].cells[1].innerHTML=sd;	    
      document.getElementById("myTable").rows[3].cells[1].innerHTML=rd;  
        var xmlHttp;  
			if(window.ActiveXObject)  
			{  
			    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
			}  
			else if(window.XMLHttpRequest)  
			{  
			   xmlHttp=new XMLHttpRequest();  
			} 
			var datastr="id="+mid;
			var url="ManageRecordAction!AddAgain";
			xmlHttp.open("POST",url,false);  
			xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
			xmlHttp.send(datastr);  
	}
	function changeDays(wid,val)
	{
	   if (val == '') {
			document.getElementById(wid).value =1;
		} else {
			var yd = /^\d*(\.(5|0))?$/;
			if (!yd.exec(val)) {
				document.getElementById(wid).value=1;
			} else
			{
			    var d=parseFloat(val);
			   var sd= parseFloat(document.getElementById("myTable").rows[2].cells[1].innerHTML)+d;
               var rd= parseFloat(document.getElementById("myTable").rows[3].cells[1].innerHTML)-d;
               document.getElementById("myTable").rows[2].cells[1].innerHTML=sd;	    
               document.getElementById("myTable").rows[3].cells[1].innerHTML=rd;
				var xmlHttp;  
				if(window.ActiveXObject)  
				{  
				    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
				}  
				else if(window.XMLHttpRequest)  
				{  
				   xmlHttp=new XMLHttpRequest();  
				} 
				var datastr="id="+wid+"&days="+val;
				var url="ManageRecordAction!ChangeDays";
				xmlHttp.open("POST",url,false);  
				xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
				xmlHttp.send(datastr);  
			}
		}
	}
	function SetDays(objId,objVal)
	{
	   var d=parseFloat(objVal);
	   var sd=parseFloat(document.getElementById("myTable").rows[2].cells[1].innerHTML)-d;
	   var rd= parseFloat(document.getElementById("myTable").rows[3].cells[1].innerHTML)+d;
	   document.getElementById("myTable").rows[2].cells[1].innerHTML=sd;	  
	   document.getElementById("myTable").rows[3].cells[1].innerHTML=rd;  
	}
	function checkService()
	{
	   var ser=document.getElementById("Service").value;
	   var p=document.getElementById("proName").value;
	   if(p=='')
	   {
	      alert("Please select one Project first !");
	      return false;
	   }
	   if(ser=='')
	   {
	      alert("Please select one ServiceNr !");
	      return false;
	   }else
	   {
	      document.getElementById("form1").action="FillAction";
	      
	   }
	}
	function copyLastMonth()
	{
	   if (confirm('Really want to copy last records?')) {
				window.location.href="CopyLastMonthAction";
				return true;
			} else {
				return false;
		}
	}
	function changeRemark(objId,objVal)
	{
	   //创建xmlHttp对象  
			var xmlHttp;  
			if(window.ActiveXObject)  
			{  
			    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
			}  
			else if(window.XMLHttpRequest)  
			{  
			   xmlHttp=new XMLHttpRequest();  
			} 
			var datastr="id="+objId+"&remark="+objVal;
			var url="ChangeLastMonthAction!changeRemark";
			xmlHttp.open("POST",url,false);  
			xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
			xmlHttp.send(datastr);  
	}
	
	function changeService(objId,objVal)
	{
	   var xmlHttp;  
			if(window.ActiveXObject)  
			{  
			    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
			}  
			else if(window.XMLHttpRequest)  
			{  
			   xmlHttp=new XMLHttpRequest();  
			} 
			var datastr="id="+objId+"&service="+objVal;
			var url="ChangeLastMonthAction!changeService";
			xmlHttp.open("POST",url,false);  
			xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
			xmlHttp.send(datastr);  
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
										<jsp:include page="employeeSideBar.jsp"></jsp:include>
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
											<div>
												<table border="1" cellspacing=0 id="myTable"
													style="background: #e5e5e5; width: 20%; right: 10%; bottom: 60%; position: absolute">
													<tr>
														<td align="right">
															Working Days:
														</td>
														<td width="59%" id="wd" align="center">
															<%
																BasicDB basicDB1 = new BasicDB();
																Calendar a = Calendar.getInstance();
																int year = a.get(Calendar.YEAR);
																int month = a.get(Calendar.MONTH) + 1;
																String fillmonth = (a.get(Calendar.MONTH) + 1) + "";
																int date = a.get(Calendar.DATE);
																if (month == 1 && date <= DeadlineBean.getEndDate()) {
																	year = year - 1;
																	fillmonth = "12";
																}
																if (date <= DeadlineBean.getEndDate() && month != 1)
																	fillmonth = (a.get(Calendar.MONTH)) + "";
																String[] paras1 = { year + "", fillmonth };
																String sql1 = "select Days from tb_Calendar where Year=? and Month=?";
																ArrayList<Object[]> alist = basicDB1.queryDB(sql1, paras1);
																Object[] obj1 = alist.get(0);
																int wDays = Integer.parseInt(obj1[0].toString().trim());
																out.println(obj1[0].toString().trim());
															%>
														</td>
													</tr>
													<tr>
														<td align="right">
															Submitted Days:
														</td>
														<td width="58%" id="sd" align="center">
															<%
																String eString = session.getAttribute(ip2 + "EmpName").toString();
																String sql2 = "select totalDays from tb_PersonControlling where Year=? and Month=? and EmpName=?";
																String[] paras2 = { year + "", fillmonth, eString };
																ArrayList<Object[]> aList2 = new ArrayList<Object[]>();
																aList2 = basicDB1.queryDB(sql2, paras2);
																float subDays = 0;
																if (aList2 == null || aList2.size() == 0) {
																	subDays = 0;
																	out.println("<font color='red' size=4>0</font>");
																} else {
																	Object[] objects = aList2.get(0);
																	subDays = Float.parseFloat(objects[0].toString().trim());
																	if (subDays < wDays)
																		out.println("<font color='red' size=4>"
																				+ objects[0].toString().trim() + "</font>");
																	else
																		out.println("<font color='green' size=4>"
																				+ objects[0].toString().trim() + "</font>");
																}
															%>
														</td>
													</tr>
													<tr>
														<td align="right">
															Sum Days:
														</td>
														<td width="58%" id="td" align="center">
															<%
																ArrayList<MonthlyControllingBean> alArrayList = (ArrayList<MonthlyControllingBean>) session
																		.getAttribute(eString);
																float currentDays = 0;
																if (alArrayList == null || alArrayList.size() == 0) {
																	out.println(0);
																	currentDays = 0;
																} else {
																	out.println(session.getAttribute(ip2 + "total"));
																	currentDays = Float.parseFloat(session.getAttribute(
																			ip2 + "total").toString().trim());
																}
															%>
														</td>
													</tr>
													<tr>
														<td align="right">
															Remaining Days:
														</td>
														<td width="58%" id="rd" align="center">
															<%
																float rd = wDays - subDays - currentDays;
																out.println(rd);
															%>
														</td>
													</tr>
												</table>
											</div>
											<form action="" method="post" name="form1" id="form1">
												<center>
													<table>
														<tr>
															<td width="160px" align="right">
																Project Name:
															</td>
															<td width="300px" height="30">
																<div id="proname" class="ddcombo"></div>
																<input type="hidden" name="proName" id="proName"/>
															</td>
														</tr>
														<tr>
															<td align="right">
																Service Nr:
															</td>
															<td>
																<select name="Service" id="Service"
																	style="width: 300px; height: 30px;">
																	<option value=''>
																		--Please select one ServiceNr
																	</option>
																	<%
																		String empname2 = session.getAttribute(ip2 + "EmpName").toString()
																				.trim();
																		BasicDB basicdb = new BasicDB();
																		String sec = session.getAttribute(ip2 + "Sec").toString();
																		String asec = session.getAttribute(ip2 + "asec").toString();
																		String sql = "select ServiceNr from tb_SerOfSec where Section=? or Section=? or Section=?";
																		String[] paras = { "000 General", sec, asec };
																		ArrayList<Object[]> aList = basicdb.queryDB(sql, paras);
																		int j = 0;
																		out.println("<option value="
																				+ aList.get(aList.size() - 1)[0].toString().trim() + ">"
																				+ aList.get(aList.size() - 1)[0].toString().trim()
																				+ "</option>");
																		for (j = 0; j < aList.size() - 1; j++) {
																			Object[] obj = aList.get(j);
																			out.println("<option value='" + obj[0].toString().trim() + "'>"
																					+ obj[0].toString().trim() + "</option>");
																		}
																	%>
																</select>
															</td>
														</tr>
														<tr style="height: 5px">
															<td align="right">
																WorkingDays:
															</td>
															<td>
																<input type="text" id="Workingdays" name="Workingdays"
																	onblur="javascript:checkWorkingDays(this.value)"
																	maxlength="8"
																	style="width: 40px; height: 30px; border: 1px solid #D3D3D3; position: relative; left: 0px; top: 14px;"
																	value="1" />
																<div style="position: relative; left: 47px; top: -20px;">
																	<a disabled=true href=""
																		onclick="return canNavigate();"><img
																			src="images/increase.jpg" onClick="plus()" /> </a>
																</div>
																<div style="position: relative; left: 47px; top: -14px;">
																	<a disabled=true href=""
																		onclick="return canNavigate();"><img
																			src="images/decrease.jpg" onClick="min()" /> </a>
																</div>
															</td>
															<td></td>
														</tr>
														<tr>
															<td align="right">
																Remark:
															</td>
															<td>
																<textarea rows="4" cols="20" name="Remark" id="Remark"
																	style="width: 300px; height: 100px; resize: none;"></textarea>
															</td>
														</tr>
														<tr>
															<td>
															</td>
															<td>
																<input type="submit" value="Add"
																	onClick='return checkService()' />
															</td>
														</tr>
													</table>
												</center>
											</form>
											<%
												String empname = session.getAttribute(ip2 + "EmpName").toString();
												ArrayList<MonthlyControllingBean> al = (ArrayList<MonthlyControllingBean>) session
														.getAttribute(empname);
												if (al != null && al.size() != 0) {
													out.println("<div style='position:relative;left:-40%;top:0%;'><input type='button' value='Copy Last Month'  onClick='return copyLastMonth()'/></div>");
													out
															.println("<form action='' method='post' name='Rec' id='Rec'>");
													out
															.println("<table border=1 bordercolor=green cellspacing=0>"
																	+ "<tr><td width='20' align='center'>Delete</td><td align='center'>ID</td><td align='center' width='15'>EName</td>"
																	+ "<td align='center' width='10'>Month</td><td width='10' align='center'>Section</td><td>PName</td><td align='center'>ServiceNr</td><td align='center' width='13'>WorkingDays</td><td align='center'>Remark</td></tr>");
													int start;
													int end;
													if (request.getAttribute("start") != null
															&& request.getAttribute("end") != null) {
														start = Integer.parseInt(request.getAttribute("start")
																.toString());
														end = Integer.parseInt(request.getAttribute("end")
																.toString());
													} else {
														start = 0;
														if (al.size() >= FillAction.pageSize) {
															end = FillAction.pageSize;
														} else {
															end = al.size();
														}
													}
													for (int k = start; k < end; k++) {
														MonthlyControllingBean bean = al.get(k);
														if (k % 2 == 0) {
															out
																	.println("<tr bgcolor='#eff8ff' style='height:20px;'>");
														} else {
															out
																	.println("<tr bgcolor='#b4cff1' style='height:20px;'>");
														}
														out
																.println("<td align='center' width='20'><input type='checkbox' name='delid' id='delid' value='"
																		+ k
																		+ "'/></td><td align='center'><a href='/EPD_Budget_Controlling_Tool/ManageRecordAction!modify?id="
																		+ k
																		+ "'>"
																		+ k
																		+ "</a></td><td align='center'>"
																		+ empname
																		+ "</td>"
																		+ "<td align='center'>"
																		+ bean.getYear()
																		+ "-"
																		+ bean.getMonth()
																		+ "</td><td align='center'>"
																		+ bean.getSection()
																		+ "</td><td align='center'>"
																		+ bean.getProname()
																		+ "</td><td align='center' width='20%'><select onchange='changeService(this.id,this.value)'    id='n"
																		+ k
																		+ "' name='n"
																		+ k
																		+ "' style='width:200px; height: 30px;'>");
														String firstService = aList.get(aList.size() - 1)[0]
																.toString().trim();
														if (firstService.equals(bean.getService().trim())) {
															out.println("<option value='" + firstService
																	+ "' selected>" + firstService + "</option>");
														} else {
															out.println("<option value='" + firstService + "'>"
																	+ firstService + "</option>");
														}
														int m = 0;
														String tempSer = "";
														// System.out.println("Fill Ser:"+bean.getService());
														for (m = 0; m < aList.size() - 1; m++) {
															tempSer = aList.get(m)[0].toString().trim();
															if (tempSer.equals(bean.getService().trim())) {
																out.println("<option value='" + tempSer
																		+ "' selected>" + tempSer + "</option>");
															} else {
																out.println("<option value='" + tempSer + "'>"
																		+ tempSer + "</option>");
															}
														}
														out
																.println("</select></td><td style='width:110px'><input type='button' class='btn' style='width:30px;height:30px;background:url(images/reduce.png)' id='"
																		+ k
																		+ "' onClick='return reduce_onclick(this.id)'/><input type='text' onfocus='SetDays(this.id,this.value);'  onblur='changeDays(this.id,this.value)' style='width:40px;height:28px;text-align:center' value='"
																		+ bean.getWorkingdays()
																		+ "' id='s"
																		+ k
																		+ "'/><input type='button' style='width:30px;height:30px;background:url(images/add.png)'id='"
																		+ k
																		+ "' onClick='return add_onclick(this.id)'/></td>");
														if (k % 2 == 0) {
															out
																	.println("<td align='center' width='25%'><input onblur='changeRemark(this.id,this.value)' style='height:105%;width:95%;background-color:#eff8ff;' type='text' id='r"
																			+ k
																			+ "' name='r"
																			+ k
																			+ "' value='"
																			+ bean.getRemark() + "'/></td></tr>");
														} else {
															out
																	.println("<td align='center' width='25%'><input onblur='changeRemark(this.id,this.value)' style='height:105%;width:95%;background-color:#b4cff1;' type='text' id='r"
																			+ k
																			+ "' name='r"
																			+ k
																			+ "' value='"
																			+ bean.getRemark() + "'/></td></tr>");
														}
													}

													int count = al.size();
													int sum = count / (FillAction.pageSize);
													int pageCount;
													if (count % (FillAction.pageSize) == 0) {
														pageCount = sum;
													} else {
														pageCount = sum + 1;
													}
													out
															.println("</table><table><tr style='height:5px'><td colspan=2><font color=black style='font-style: italic; font-weight: bold;'>Total WorkingDays: <font color=red style='font-style: italic; font-weight: bold;'>"
																	+ session.getAttribute(ip2 + "total")
																			.toString() + "</td><td>");

													if (sum > 0) {
														for (int s = 0; s < pageCount; s++) {
															out
																	.println("<a href='/EPD_Budget_Controlling_Tool/SplitPageAction?page="
																			+ (s + 1)
																			+ "'>["
																			+ (s + 1)
																			+ "]</a>&nbsp;");
														}
													}

													out
															.println("current: <font color=red style='font-style: italic; font-weight: bold;'>"
																	+ (start / (FillAction.pageSize) + 1)
																	+ "</font>   total: <font color=red style='font-style: italic; font-weight: bold;'> "
																	+ pageCount);

													out
															.println("</td></tr></table><input type='submit' value='Submit' onClick='return Sub()'/>&nbsp;&nbsp;&nbsp<input type='submit' value='Delete' onClick='return Del()'/></form>");

												} else {
													out
															.println("<div style='position:relative;left:-10%;top:0%;'><font color='red'><B>You have not fill any records!!!</B></font></div>");
													out.println("<div style='position:relative;left:-40%;top:0%;'><input type='button' value='Copy Last Month' onClick='return copyLastMonth()'/></div>");
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