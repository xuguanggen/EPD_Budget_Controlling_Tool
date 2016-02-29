<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="org.apache.struts2.components.Else"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Search Project</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
	</head>
	<script type="text/javascript">
	   var contextPath="<%=request.getContextPath()%>";
	    function showpart()
	    {
	        var v=document.getElementById("search").value;
	       if(v=='e.g. Name,TPM,OEM,Customer')
	       {
	          alert("Please fill text");
	       }else{
	       document.getElementById("form1").action =contextPath+"/showProjectAction!showPart?pageNow=1";
           document.getElementById("form1").submit();
           }                
	    }
	    function del()
	    {
	       var res=window.confirm("Really want to delete these projects?");
	       if(res==true){
	       document.getElementById("form2").action =contextPath+"/showProjectAction!delPro";
           document.getElementById("form2").submit();}
           else
           {
              return ;
              }
	}
	function checksearch(){
	 if(document.getElementById("search").value=='e.g. Name,TPM,OEM,Tier1')
     {
         document.getElementById("search").value='';
         document.getElementById("search").style.color='#000';
     }     
    }
    
    function blursearch()
    {
       if(document.getElementById("search").value=='')
     {
         document.getElementById("search").value='e.g. Name,TPM,OEM,Tier1';
         document.getElementById("search").style.color='#999999';
     }     
    }
</script>
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
									<div id="display" style="width: 100%;">
										<div id="title">
											Search Project
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
											<br />
											<form action="" method="post" name="form1" id="form1">
												<input onblur="blursearch()"
													onfocus="javascript:checksearch();" type="text"
													name="search" id="search"
													value="<s:property value='#session.search'/>"
													style="width: 300px; height: 20px; color: #999999" />
												<input type="submit" name="submit" value="Search"
													onClick="showpart()" />
											</form>
											<br />
											<s:if test="null!=#request.res&&!#request.res.isEmpty()">
												<font style="font-weight: bold; font-style: italic;"
													color="red"><s:property value="#request.res"></s:property>
												</font>
											</s:if>
											<form action="" method="post" name="form2" id="form2">
												<table border="1" bordercolor=green cellspacing=0>
													<tr>
														<th>
															IsDelete
														</th>
														<th>
															ID
														</th>
														<th>
															Name
														</th>
														<th>
															Category
														</th>
														<th>
															TPM
														</th>
														<th>
															EPD_Budget
														</th>
														<th>
															AE_Budget
														</th>
														<th>
															RBEI_Budget
														</th>
														<th>
															Status
														</th>
														<th>
															OEM
														</th>
														<th>
															Tier1
														</th>
														<th>
															Remark
														</th>
													</tr>
													<s:iterator value="#request.pro" var="s" status="stuts">
														<tr
															<s:if test="#stuts.odd == true">style="background-color:#acf7f3"</s:if>>
															<td align="center">
																<input type="checkbox" id="dels" name="dels"
																	value="<s:property value='#s.proId'/>" />
															</td>
															<td align="center">
																<a
																	href="showProjectAction!modPro?pid=<s:property value='#s.proId'/>"><font
																	color="blue"
																	style="font-style: italic; font-weight: bold;"><s:property
																			value="#s.proId" /> </font> </a>
															</td>
															<td align="center">
																<s:property value="#s.proName" />
															</td>
															<td align="center">
																<s:property value="#s.category" />
															</td>
															<td align="center">
																<s:property value="#s.TPM" />
															</td>
															<td align="center">
																<a
																	href="ShowBudgetAction?proName=<s:property value="#s.proName" />">
																	<s:property value="#s.ebudget" /> </a>
															</td>
															<td align="center">
																<s:property value="#s.abudget" />
															</td>
															<td align="center">
																<s:property value="#s.rbudget" />
															</td>
															<td align="center">
																<s:property value="#s.status" />
															</td>
															<td align="center">
																<s:property value="#s.oem" />
															</td>
															<td align="center">
																<s:property value="#s.customer" />
															</td>
															<td align="center">
																<s:property value="#s.remark" />
															</td>
														</tr>
													</s:iterator>
												</table>
												<input type="submit" value="Delete" onClick="del()" />
											</form>
											<br />
											<%
												if (request.getAttribute("pageNow") != null
														&& request.getAttribute("pagecount") != null) {
													int pageNow = Integer.parseInt(request.getAttribute("pageNow")
															.toString());
													int pageCount = Integer.parseInt(request.getAttribute(
															"pagecount").toString());
													out
															.println("<a href='showProjectAction!showPart?pageNow=1'>first</a>&nbsp;&nbsp;");
													if (pageNow > 1) {
														out.println("<a href='showProjectAction!showPart?pageNow="
																+ (pageNow - 1) + "'>front</a>&nbsp;&nbsp;");
													}
													for (int i = 1; i <= pageCount; i++) {
														if (i == pageNow)
															out
																	.println("<a href='showProjectAction!showPart?pageNow="
																			+ i
																			+ "'><font color='red'><b>["
																			+ i
																			+ "]</b></font></a>&nbsp;&nbsp;");
														else
															out
																	.println("<a href='showProjectAction!showPart?pageNow="
																			+ i + "'>[" + i + "]</a>&nbsp;&nbsp;");
													}
													if (pageCount > pageNow) {
														out.println("<a href='showProjectAction!showPart?pageNow="
																+ (pageNow + 1) + "'>next</a>&nbsp;&nbsp;");
													}
													out.println("<a href='showProjectAction!showPart?pageNow="
															+ pageCount + "'>end</a>");
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
				<tr>
					<td>
						<jsp:include page="footer.jsp"></jsp:include></td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>