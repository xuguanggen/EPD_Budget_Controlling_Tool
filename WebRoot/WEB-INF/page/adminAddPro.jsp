<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.xgg.epd.utils.Common"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.xgg.epd.dbs.BasicDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link href="css/register.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Add Project</title>
	</head>
	<script type="text/javascript">
	   var contextPath="<%=request.getContextPath()%>";
	function $(id) {
		return id ? document.getElementById(id) : '';
	}
	function addPro()
	{
		if (confirm("Really want to add this project?")) {
			document.projectform.action = contextPath + "/AddProjectAction!addPro()";
			document.projectform.submit();
		} else {
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
											Add Project
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
												<form id="projectform" name="projectform" action=""
													method="post">
													<table>
														<tr>
															<td width="160px" align="right">
																<span class="font_red">*</span>Project Name：
															</td>
															<td width="300px" height="30">
																<input type="text" name="pname"
																	id="pname" onfocus="javascript:checknames();"
																	onblur="javascript:checkid(this.value);"
																	style="width: 268px; height: 20px;" />
															</td>
															<td id="passname"></td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Category:
															</td>
															<td>
																<select id="category" name="category"
																	style="width: 268px; height: 20px;">
																	<option value="">
																		--Please select one category--
																	</option>
																	<option value="none">
																		None
																	</option>
																	<%																
																  for(int i=0;i<Common.categoryType.length;i++)
																  {
																    out.println("<option value='"+Common.categoryType[i]+"'>"+Common.categoryType[i]+"</option>");
																  }
																   %>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>TPM:
															</td>
															<td>
																<select id="tpm" name="tpm"
																	style="width: 268px; height: 20px;">
																	<option value="">
																		--Please select the TPM--
																	</option>
																	<option value="none">
																		None
																	</option>
																	<option value="Admin">Admin</option>
																	<%
																  BasicDB basicDB=new BasicDB();
																  String sql="select EmpName from tb_Employee where mark=1 or mark=3";
																  ArrayList<String> alStrings=basicDB.SQuerydb(sql);
																  for(int i=0;i<alStrings.size();i++)
																  {
																    out.println("<option value='"+alStrings.get(i)+"'>"+alStrings.get(i)+"</option>");
																  }
																   %>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																IsBP:
															</td>
															<td>
																<select id="bp" name="bp"
																	style="width: 268px; height: 20px;">
																	<option value="1">
																		Yes
																	</option>
																	<option value="0">
																		No
																	</option>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Year:
															</td>
															<td>
																<input class="Wdate" name="year" id="year"
																	onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy'})" />
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>EPD_Budget:
															</td>
															<td>
																<input name="ebudget" id="ebudget"
																	style="width: 268px; height: 20px;">
															</td>
															<td>
																<span class="font_black">If EPD_Budget=0,please
																	fill </span><span class="font_red"><b>0</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>AE_Budget:
															</td>
															<td>
																<input name="abudget" id="abudget"
																	style="width: 268px; height: 20px;">
															</td>
															<td>
																<span class="font_black">If AE_Budget=0,please
																	fill </span><span class="font_red"><b>0</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>RBEI_Budget:
															</td>
															<td>
																<input name="rbudget" id="rbudget"
																	style="width: 268px; height: 20px;">
															</td>
															<td>
																<span class="font_black">If RBEI_Budget=0,please
																	fill </span><span class="font_red"><b>0</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Status:
															</td>
															<td>
																<select style="width: 268px; height: 20px;" id="status" name="status" >
																	<option value="">
																		--Please select one status--
																	</option>
																	<%
																	   for(int i=0;i<Common.status.length;i++)
																	   {
																	      String state=Common.status[i];
																	      out.println("<option value="+state+">"+state+"</option>");
																	   }
																	 %>
															  </select>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Tier1:
															</td>
															<td>
																<input type="text" name="customer" id="customer"
																	style="width: 268px; height: 20px;" />
															</td>
															<td>
																<span class="font_black">If have no Tier1,please fill </span><span class="font_red"><b>None</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>OEM:
															</td>
															<td>
																<input type="text" name="oem" id="oem"
																	style="width: 268px; height: 20px;" />
															</td>
															<td>
																<span class="font_black">If have no OEM,please fill </span><span class="font_red"><b>None</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																Remark:
															</td>
															<td>
																<textarea rows="2" cols="20" name="remark" id="remark"
																	style="width: 268px; height: 60px; resize: none;"></textarea>
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="submit" value="Add" onClick="return addPro();" />
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
			</table>
		</div>
		<!-- end container -->
	</body>
</html>