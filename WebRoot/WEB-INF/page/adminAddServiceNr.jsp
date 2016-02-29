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
		<title>Add ServiceNr</title>
	</head>
	<script type="text/javascript" language="javaScript">
		function sectionChange()
		{
		   var s=document.getElementById("section").value;
		   window.location.href="SectionChangeAction!showServiceNr?section="+s;
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
											Add ServiceNr
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
														out</a></font>
											</div>			
										</div>
										<div id="comtable">
											<center>
												<form action="AddServiceNrAction" method="post">
													<table>
														<tr>
															<td align="right">
																Section:
															</td>
															<td>
																<select name="section" id="section"
																	style="width: 250x; height: 20px;" onChange="sectionChange()">
																	<option value="">
																		--Please select one Section
																	</option>
																	<%
																	if(request.getAttribute("section")==null)
																	{
																	    for(int i=1;i<=6;i++)
																	    {
																	       String s="EPD"+i;
																	       out.println("<option value='"+s+"'>"+s+"</option>"); 
																	    }
																	}else
																	{
																	    String selectedSection=request.getAttribute("section").toString().trim();
																	 	for(int i=1;i<=6;i++)
																	 	{
																	 	   String s="EPD"+i;
																	 	   if(s.equals(selectedSection))
																	 	   {
																	 	      out.println("<option selected value='"+s+"'>"+s+"</option>");
																	 	   }else
																	 	   {
																	 	      out.println("<option value='"+s+"'>"+s+"</option>");
																	 	   }
																	 	}   
																	}																	
																	 %>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																ServiceNr:
															</td>
															<td>
																<input type="text" name="service" id="service"
																	style="width: 200px; height: 20px;" />
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="submit" value="Add" onClick="return window.confirm('Confirm to add this ServiceNr?');" />
															</td>
														</tr>
													</table>
												</form>
												<%
												if(request.getAttribute("serviceList")!=null)
												{
												   out.println("<table border=1 bordercolor=green cellspacing=0 style='width:80%'>");
												   out.println("<tr><th>Delete</th><th>Section</th><th>ServiceNr</th></tr>");
												   ArrayList<String> alist=(ArrayList<String>)request.getAttribute("serviceList");
												   String sec=request.getAttribute("section").toString().trim();
												   for(int i=0;i<alist.size();i++)
												   {
												      out.println("<tr>");
												      out.println("<td align=center><a href='AddServiceNrAction!deleteServicr?section="+sec+"&service="+alist.get(i)+"'><img src='images/wrong.gif'/></a>");
												      out.println("</td>");
												      out.println("<td align=center>"+sec);
												      out.println("</td>");
												      out.println("<td align=left>"+alist.get(i));
												      out.println("</td>");
												      out.println("</tr>");
												   }
												   out.println("</table>");
												}												
												 %>
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