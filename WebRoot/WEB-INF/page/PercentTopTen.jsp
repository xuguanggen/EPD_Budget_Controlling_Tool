<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Administrator - Chart</title>
	</head>
	<script type="text/javascript">
	   var contextPath="<%=request.getContextPath()%>";
	   function show()
	   {
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
		if(chestr == "")
		{
		  alert("Please select one project first!");
		  return;
		}
		else
		{
		   document.form1.action =contextPath+"/AllChartAction!createPercentTopTen";
           document.form1.submit();
		}      
	   }
	    function ChangePercentTopTen(val)
	   {
	      window.location.href="TopTenAction!PercentTopTen?year="+val;  
	   }
	</script>
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
										<jsp:include page="adminSidebar.jsp"></jsp:include>
									</div>
								</td>
								<td valign="top" width="85%">
									<div  id="display" style="width: 100%">
										<div id="title">
											Top 10 Exceed
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
											<form action="" method="post" name="form1" id="form1">
												<table width="100%" border="1" bordercolor=green
													height="50%" cellspacing=0>
													<tr style="height: 5px">
														<td align="left">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Year:
															<%
																Calendar calendar = Calendar.getInstance();
																int year = calendar.get(Calendar.YEAR);
																int selectYear = 0;
																if (session.getAttribute(ip + "percenttenYear") != null) {
																	selectYear = Integer.parseInt(session.getAttribute(
																			ip + "percenttenYear").toString().trim());
																}
																out
																		.println("<select name='year' id='year' onChange='ChangePercentTopTen(this.value)'>");
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
													</tr>
													<tr>
														<td colspan="5" style="width: 95%">
															<div id="pro"
																style="width: 100%; height: 105px; overflow-y: scroll;">
																<%
																	ArrayList<String> al = (ArrayList<String>) session
																			.getAttribute(ip+"percenttenPro");
																	String[] clist = (String[]) request.getAttribute("checkPro");
																	if (al != null) {
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
																	}
																%>
															</div>
														</td>
														<td style="width: 5%">
															<input value="Show Chart" type="submit"
																onClick="return show()"
																style="height: 105px; width: 162px" />
														</td>
													</tr>
												</table>
											</form>
											<%
												String[] fileNames = (String[]) request.getAttribute("filename");
												if (fileNames != null) {
													int j = 0;
													for (j = 0; j < fileNames.length; j++) {
														String graphURL = request.getContextPath()
																+ "/chart/DisplayChart?filename=" + fileNames[j];
											%>
											<img src="<%=graphURL%>" usemap="#'<%=fileNames[j]%>'">
											<%
												}
											}
											%>
										</div>
									</div>
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