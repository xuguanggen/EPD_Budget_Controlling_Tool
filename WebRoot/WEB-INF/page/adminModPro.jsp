<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.xgg.epd.beans.ProjectBean"%>
<%@page import="com.xgg.epd.utils.Common"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.xgg.epd.dbs.BasicDB,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link href="css/register.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Update Project Information</title>
	</head>
	<script type="text/javascript">
	   var contextPath="<%=request.getContextPath()%>";
	function $(id) {
		return id ? document.getElementById(id) : '';
	}
	function updatePro()
	{
		if (confirm("Really want to update this project?")) {
		    var p=${"pname"}.value;
			document.projectform.action = contextPath + "/AddProjectAction!modPro?pname="+p;
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
											Update ProjectInfo
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
																<input type="text" name="pname" maxlength="20" disabled="disabled"
																	id="pname" value="<s:property value='#request.modPro.proName'/>"
																	style="width: 268px; height: 20px;" readonly/>
															</td>
															<td id="passname"></td>
														</tr>
														<tr>
															<td align="right">
																Category:
															</td>
															<td>
																<select id="category" name="category"
																	style="width: 268px; height: 20px;">
																	<%
																	String category=((ProjectBean)request.getAttribute("modPro")).getCategory().toString().trim();
																	for(int k=0;k<Common.categoryType.length;k++)
																	{
																	   String tempCategory=Common.categoryType[k];
																	   if(category.equals(tempCategory))
																	   {
																	      out.println("<option selected value='"+tempCategory+"'>"+tempCategory+"</option>");
																	   }else
																	   {
																	      out.println("<option value='"+tempCategory+"'>"+tempCategory+"</option>");
																	   }
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
																  String tpm=((ProjectBean)request.getAttribute("modPro")).getTPM();
																   ArrayList<String> alStrings=new ArrayList<String>();
														    	   BasicDB basicDB=new BasicDB();
																 String sql="select EmpName from tb_Employee where mark=1 or mark=3";
																 alStrings=basicDB.SQuerydb(sql);
																  if("NULL".equals(tpm))
																  {
																     out.println("<option value='NULL' selected>NULL</option>");																	 
																     for(int i=0;i<alStrings.size();i++)
																     {
																	    out.println("<option value='"+alStrings.get(i).trim()+"'>"+alStrings.get(i).trim()+"</option>");
																     }  
																  }else 
																  {
																     out.println("<option value='NULL'>NULL</option>");
																     for(int i=0;i<alStrings.size();i++)
																     {
																        String temptpm=alStrings.get(i).trim();
																        if(temptpm.equals(tpm))
																        {
																           out.println("<option value='"+temptpm+"' selected>"+temptpm+"</option>");
																        }else
																        { 
																           out.println("<option value='"+temptpm+"'>"+temptpm+"</optipn>");
																        }
																     }
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
																	<%
																	String bp=((ProjectBean)request.getAttribute("modPro")).getBp();
																	System.out.println("bp="+bp);
																	if("1".equals(bp))
																	{
																	   out.println("<option value=1 selected>Yes</option>");
																	   out.println("<option value=0>No<option>");
																	}else
																	{
																	   out.println("<option value=1>Yes<option>");
																	   out.println("<option value=0 selected>No<option>");
																	}																	
																	 %>
																</select>
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
																	String state=((ProjectBean)request.getAttribute("modPro")).getStatus().toString().trim();
																	System.out.println("State="+state);
																	for(int i=0;i<Common.status.length;i++)
																	{
																	    String tempstate=Common.status[i];
																	    if(tempstate.equals(state))
																	    {
																	       out.println("<option value='"+tempstate+"' selected>"+tempstate+"</option>");
																	    }else
																	    {
																	       out.println("<option value='"+tempstate+"'>"+tempstate+"</option>");
																	    }
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
																<input type="text" name="customer" id="customer" value="<s:property value='#request.modPro.customer'/>"
																	style="width: 268px; height: 20px;" />
															</td>
															<td>
																<span class="font_black">If have no Tier1,please fill </span><span class="font_red"><b>NULL</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>OEM:
															</td>
															<td>
																<input type="text" name="oem" id="oem" value="<s:property value='#request.modPro.oem'/>"
																	style="width: 268px; height: 20px;" />
															</td>
															<td>
																<span class="font_black">If have no OEM,please fill </span><span class="font_red"><b>NULL</b></span>
															</td>
														</tr>
														<tr>
															<td align="right">
																Remark:
															</td>
															<td>
																<textarea rows="3" cols="20" name="remark" id="remark" text="<s:property value='#request.modPro.remark'/>"
																	style="width: 268px; height: 80px; resize: none;"></textarea>
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="submit" value="Update" onClick="return updatePro();" />
															</td>
														</tr>
													</table>
												</form>
											</center>
										</div>
									</div>
									<!-- end display -->
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<jsp:include page="footer.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>