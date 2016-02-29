<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*,com.xgg.epd.dbs.*" pageEncoding="utf-8"%>
<%@ page import="com.xgg.epd.beans.MonthlyControllingBean"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Modify My MonthlyControlling</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
	</head>
	<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
	function checkworkingdays() {
		document.getElementById("wdwaring").innerHTML = "<font color='red'>Please input a number</font>";
	}

	function checkWorkingDays(workingdays) {
		if (workingdays == '') {
			document.getElementById("Workingdays").value =1;
		} else {
			var yd = /^[1-9]\d*$/;
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

    function show()
    {
       var selectIndex = document.getElementById("proName").selectedIndex;
       var selectText = document.getElementById("proName").options[selectIndex].text;
        var selectIndex1 = document.getElementById("Service").selectedIndex;
       var selectText1 = document.getElementById("Service").options[selectIndex1].text;
       alert("Update success!");
    }

	var canNav = false;
	function canNavigate() {
		return canNav;
	} 
</script>
	<%
		MonthlyControllingBean bean = (MonthlyControllingBean) request
				.getAttribute("modbean");
		String idString = session.getAttribute("modID").toString();
		System.out.println("JSP to modify infomation:===" + idString + "=="
				+ bean.getEmpname() + "===" + bean.getProname() + "==="
				+ bean.getSection() + "====" + bean.getService() + "===="
				+ bean.getRemark() + "==" + bean.getWorkingdays());
	%>
	<body style="overflow-y:scroll">
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
										<jsp:include page="tpmSidebar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div id="display" style="width: 100%;">
										<div id="title">
											Modify &nbsp;MonthlyControlling
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
											<form action="UpdateMcAction" method="post">
												<center>
													<table>
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
																		String sqlString = "select  proName from tb_Project where 1=?";
																		String[] paras1 = { "1" };
																		ArrayList<Object[]> alist = basicDb.queryDB(sqlString, paras1);
																		int i = 0;
																		for (i = 0; i < alist.size(); i++) {
																			Object[] obj = alist.get(i);
																			if(obj[0].toString().trim().equals(bean.getProname().trim()))
																			{
																			   out.println("<option selected value='"+obj[0].toString()+"'>"+obj[0].toString()+"</option>");
																			}else
																			{
																			  out.println("<option value='"+obj[0].toString()+"'>"+obj[0].toString()+"</option>");
																			}
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
																<select name="Service" id="Service"
																	style="width: 300px; height: 20px;">
																	<option>
																		--Please select one ServiceNr
																	</option>
																	<%
																		BasicDB basicdb = new BasicDB();
																		String sec = session.getAttribute(ip+"Sec").toString();
																		String sql = "select ServiceNr from tb_SerOfSec where Section=? or Section=? ";
																		String[] paras = { "000 General", sec };
																		ArrayList<Object[]> aList = basicdb.queryDB(sql, paras);
																		int j = 0;
																		for (j = 0; j < aList.size(); j++) {
																			Object[] obj = aList.get(j);
																			if(obj[0].toString().trim().equals(bean.getService().trim()))
																			{
																			out.println("<option selected value='" + obj[0].toString() + "'>"
																					+ obj[0].toString() + "</option>");
																			}else
																			{
																			 out.println("<option value='" + obj[0].toString() + "'>"
																					+ obj[0].toString() + "</option>");
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
															<input type="text" id="Workingdays"  name="Workingdays"
																	onblur="javascript:checkWorkingDays(this.value)"
																	maxlength="8"
																	style="width: 40px; height: 30px; border: 1px solid #D3D3D3; position: relative; left: 0px; top: 14px;"
																	value='<s:property value='#request.modbean.workingdays'/>' />
																<div style="position: relative; left: 47px; top: -20px;" >
																	<a disabled=true  href=""  onclick="return canNavigate();"><img src="images/increase.jpg" onClick="plus()"/></a>
																</div>
																<div style="position: relative; left: 47px; top: -14px;">
																	<a disabled=true  href=""  onclick="return canNavigate();"><img src="images/decrease.jpg" onClick="min()"/></a>														
																</div>																
															</td>
															<td id="wdwaring"></td>
														</tr>
														<tr>
															<td align="right">
																Remark:
															</td>
															<td>
																<textarea rows="4" cols="20" name="Remark" id="Remark"
																	style="width: 300px; height: 100px;"><s:property value="#request.modbean.remark"/></textarea>
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="submit" value="Update" onClick="show()"/>
															</td>
														</tr>
													</table>
												</center>
											</form>
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
			<div style="margin:24% 0 0 0px"><jsp:include page="footer.jsp"></jsp:include></div>	
		</div>
		<!-- end container -->
	</body>
</html>