<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.xgg.epd.beans.MonthlyControllingBean"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<title>Import/Export Excel</title>
		<script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>

	<script type="text/javascript">
	function confirmDownLoad()
	{
	    var t=window.confirm("Really want to export the records?");
	    if(t==true)
	    {  
	       var month=document.getElementById("d").value;	 
	   	   document.getElementById("e").submit();
	   	   return true;
	    }
	    else
	    {
	      return false;
	    }
	}	
	function exportPros()
	{
	   var t=window.confirm("Really want to export all projects?");
	   if(t==true)
	   {
	       window.location.href="WriteIntoExcelAction!ExportProjects";
	       return true;
	   }else
	   {
	      return false;
	   }
	}
	</script>
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
											Import / Export Excel
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
											<div style="margin: 0% 0% 0% 6%" align="left">
												<font
													style="font-family: 'Times New Roman'; font-size: 20px;">Import:</font>
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid:DXImageTransform.Microsoft.Shadow(color #5151A2, direction :145, strength :   15 )">
											<center>
												<form action="uploadAction" method="post" id="uploadform"
													name="uploadform" enctype="multipart/form-data">
													<table border="0">
														<tr>
															<td width="160px" align="right">
																Upload Type:
															</td>
															<td>
																<select name="uploadtype" id="uploadtype"
																	onchange=checktype(this.value)
																	style="width: 200px; height: 20px;">
																	<option value="">
																		--Please select upload file type
																	</option>
																	<option value="AE">
																		AE
																	</option>
																	<option value="RBEI">
																		RBEI
																	</option>
																	<option value="MaterialExpense">
																		Material Expense
																	</option>
																</select>
															</td>
															<td id="typeerror"></td>
														</tr>
														<tr>
															<td align="right">
																Month:
															</td>
															<td>
																<input class="Wdate" name="d" id="d"
																	onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})" />
															</td>
														</tr>
														<tr>
															<td align="right">
																Upload File:
															</td>
															<td>
																<input type="file" name="myFile" id="myFile"
																	onblur="check()">
															</td>
															<td id="fileerror"></td>
														</tr>
														<tr>
															<td align=right>
															</td>
															<td align="left">
																<input type="submit" value="upload" />
																<input type="reset" value="Reset" />
															</td>
														</tr>
													</table>
												</form>
											</center>
											<div style="margin: 2% 0% 0% 6%" align="left">
												<font
													style="font-family: 'Times New Roman'; font-size: 20px;">Export
													MonthlyControlling:</font>
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid :   DXImageTransform.Microsoft.Shadow (   color #5151A2, direction :   145, strength :   15 )">
											<center>
												<form action="WriteIntoExcelAction!CreateExcel"
													method="post" id="e" name="e">
													<table border="0">
														<tr>
															<td align="center">
																Month:
															</td>
															<td align="left">
																<div align="center">
																</div>
																<div style="margin: 0% 0% 0% -10%" align="left">
																	<input class="Wdate" name="d" id="d"
																		onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})">
																</div>
															</td>
														</tr>
														<tr>
															<td align="right"></td>
															<td align="left">
																<div style="margin: 0% 0% 0% -10%" align="left">
																	<input type="submit" value="DownLoad"
																		onclick="return confirmDownLoad();" />
																</div>
															</td>
														</tr>
													</table>
												</form>
											</center>
											<div style="margin: 2% 0% 0% 6%" align="left">
												<font
													style="font-family: 'Times New Roman'; font-size: 20px;">Export
													Projects:</font>
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid :   DXImageTransform.Microsoft.Shadow (   color #5151A2, direction :   145, strength :   15 )">
											<input type="button" value="Export Projects" style="margin: 0% 0% 0% -60%" onClick="return exportPros()"/>
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