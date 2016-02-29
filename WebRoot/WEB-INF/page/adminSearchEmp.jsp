<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Search Employee</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
	</head>
	<script type="text/javascript">
	   var contextPath="<%=request.getContextPath()%>";
	    function showpart()
	    {
	       var v=document.getElementById("search").value;
	       if(v=='e.g. 88842221 or Zhang San')
	       {
	          alert("Please fill text");
	       }else{
	       document.getElementById("form1").action =contextPath+"/ShowEmployeeAction!showPart?pageNow=1";
           document.getElementById("form1").submit();
           }           
	    }
	    function del()
	    {
	       var res=window.confirm("Really want to delete these employees?");
	       if(res==true){
	       document.getElementById("form2").action =contextPath+"/ShowEmployeeAction!delEmp";
           document.getElementById("form2").submit();
           }
           else
           {
              return ;
              }
	}
	function checksearch(){
	 if(document.getElementById("search").value=='e.g. 88842221 or Zhang San')
     {
         document.getElementById("search").value='';
         document.getElementById("search").style.color='#000';
     }     
    }
    
    function blursearch()
    {
       if(document.getElementById("search").value=='')
     {
         document.getElementById("search").value='e.g. 88842221 or Zhang San';
         document.getElementById("search").style.color='#999999';
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
											Search Employee
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
											<br />
											<form action="" method="post" name="form1" id="form1">												
												<input onblur="blursearch()" onfocus="javascript:checksearch();" type="text" name="search" id="search" value="e.g. 88842221 or Zhang San" style="width: 268px; height: 20px; color: #999999"/>
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
															Person ID
														</th>
														<th>
															Name
														</th>
														<th>
															Section
														</th>
														<th>
															Multi-role
														</th>
														<th>
															Email
														</th>
														<th>
															PassWord
														</th>
														<th>
															Mark
														</th>
													</tr>
													<s:iterator value="#request.emp" var="s" status="stuts">
														<tr
															<s:if test="#stuts.odd == true">style="background-color:#acf7f3"</s:if>>
															<td align="center">
																<input type="checkbox" id="dels" name="dels"
																	value="<s:property value='#s.EId'/>" />
															</td>
															<td align="center">
																<a
																	href="ShowEmployeeAction!modEmp?eid=<s:property value='#s.EId'/>"><font
																	color="blue"
																	style="font-style: italic; font-weight: bold;"><s:property
																			value="#s.EId" /> </font> </a>
															</td>
															<td align="center">		
																<s:if test="#s.Mark==4">
																  <a href="AdminAllocateProAction?eid=<s:property value='#s.EId'/>&ename=<s:property value='#s.EmpName'/>"><b><s:property value="#s.EmpName" /></b></a>
																</s:if>
																<s:else>
																	<s:property value="#s.EmpName" />
																</s:else>
															</td>
															<td align="center">
																<s:property value="#s.Section" />
															</td>
															<td align="center">
																<s:property value="#s.Asection"/>
															</td>
															<td align="center">
																<s:property value="#s.Email" />
															</td>
															<td align="center">
																<s:property value="#s.Pwd" />
															</td>
															<td align="center">														
																<s:if test="#s.Mark==0">
																  general employee
																</s:if>
																<s:elseif test="#s.Mark==1">
																TPM
																</s:elseif>
																<s:elseif test="#s.Mark==2">
																administrator 
																</s:elseif>	
																<s:elseif test="#s.Mark==4">
																guest
																</s:elseif>																	
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
															.println("<a href='ShowEmployeeAction!showAll?pageNow=1'>first</a>&nbsp;&nbsp;");
													if (pageNow > 1) {
														out.println("<a href='ShowEmployeeAction!showAll?pageNow="
																+ (pageNow - 1) + "'>front</a>&nbsp;&nbsp;");
													}
													for (int i = 1; i <= pageCount; i++) {
														if(pageNow==i)
														{
														   out.println("<a href='ShowEmployeeAction!showAll?pageNow="
																+ i + "'><font color='red'><b>[" + i + "]</b></font></a>&nbsp;&nbsp;");
														}
														else
														{
														   out.println("<a href='ShowEmployeeAction!showAll?pageNow="
																+ i + "'>[" + i + "]</a>&nbsp;&nbsp;");
														}
													}
													if (pageCount > pageNow) {
														out.println("<a href='ShowEmployeeAction!showAll?pageNow="
																+ (pageNow + 1) + "'>next</a>&nbsp;&nbsp;");
													}
													out.println("<a href='ShowEmployeeAction!showAll?pageNow="
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
				</tr><tr><td valign="top"><br></td></tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>