<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Set Calendar</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
		function check_jan()
		{
			var v=document.getElementById("jan").value;
			var re =/^\+?[1-9][0-9]*$/;
			if(!re.test(v))
			{
			   document.getElementById("w_jan").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_jan").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_feb()
		{
		   var v=document.getElementById("feb").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_feb").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_feb").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_mar()
		{
		   var v=document.getElementById("mar").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_mar").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_mar").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_apr()
		{
		   var v=document.getElementById("apr").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_apr").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_apr").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_may()
		{
		   var v=document.getElementById("may").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_may").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_may").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_jun()
		{
		   var v=document.getElementById("jun").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_jun").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_jun").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_jul()
		{
		   var v=document.getElementById("jul").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_jul").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_jul").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_aug()
		{
		   var v=document.getElementById("aug").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_aug").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_aug").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_sep()
		{
		   var v=document.getElementById("sep").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_sep").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_sep").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_oct()
		{
		   var v=document.getElementById("oct").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_oct").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_oct").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_nov()
		{
		   var v=document.getElementById("nov").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_nov").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_nov").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function check_dec()
		{
		   var v=document.getElementById("dec").value;
		   var re =/^\+?[1-9][0-9]*$/;
		   if(!re.test(v))
			{
			   document.getElementById("w_dec").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please enter a positive integer</font>";
			} 
			else 
			{
				document.getElementById("w_dec").innerHTML="<img src='images/right.gif'></img>";
			} 
		}
		function Sub() {
		if (confirm("Really want to upload these records?")) {
			document.calendar.action = contextPath + "/CalendarAction";
			document.calendar.submit();
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
											Set Calendar
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
												<form action="" method="post" id="calendar" name="calendar">
												<div style="margin:2% 60% 0% 0%">Year:<input class="Wdate" name="year" id="year"
																	onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy'})" /></div>
													<br/>
													<table border="0">
														<tr>
															<td align="right">
																<font style="FONT-WEIGHT: bolder;">Month</font>
															</td>
															<td align="left">
																<font style="FONT-WEIGHT: bolder;">WorkingDays</font>
															</td>
															<td></td>
															<td align="right">
																<font style="FONT-WEIGHT: bolder;">Month</font>
															</td>
															<td align="left">
																<font style="FONT-WEIGHT: bolder;">WorkingDays</font>
															</td>
														</tr>
														<tr>
															<td align="right" style="width: 100px">
																Jan:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_jan()" type="text"
																	id="jan" name="jan" style="width: 50px" />
																days
															</td>
															<td id="w_jan" style="width:180px"></td>
															<td align="right" style="width: 100px">
																Jul:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_jul()" type="text" id="jul" name="jul"
																	style="width: 50px" />
																days
															</td>
															<td id="w_jul" style="width:180px"></td>
														</tr>
														<tr>
															<td align="right" style="width: 100px">
																Feb:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_feb()" type="text" id="feb" name="feb"
																	style="width: 50px" />
																days
															</td>
															<td id="w_feb" style="width:180px"></td>
															<td align="right" style="width: 100px">
																Aug:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_aug()" type="text" id="aug" name="aug"
																	style="width: 50px" />
																days
															</td>
															<td id="w_aug" style="width:180px"></td>
														</tr>
														<tr>
															<td align="right">
																Mar:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_mar()" type="text" id="mar" name="mar"
																	style="width: 50px" />
																days
															</td>
															<td id="w_mar" style="width:180px"></td>
															<td align="right">
																Sep:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_sep()" type="text" id="sep" name="sep"
																	style="width: 50px" />
																days
															</td>
															<td id="w_sep" style="width:180px"></td>
														</tr>
														<tr>
															<td align="right" style="width: 100px">
																Apr:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_apr()" type="text" id="apr" name="apr"
																	style="width: 50px" />
																days
															</td>
															<td id="w_apr" style="width:180px"></td>
															<td align="right" style="width: 100px">
																Oct:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_oct()" type="text" id="oct" name="oct"
																	style="width: 50px" />
																days
															</td>
															<td id="w_oct" style="width:180px"></td>
														</tr>
														<tr>
															<td align="right" style="width: 100px">
																May:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_may()" type="text" id="may" name="may"
																	style="width: 50px" />
																days
															</td>
															<td id="w_may" style="width:180px"></td>
															<td align="right" style="width: 100px">
																Nov:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_nov()" type="text" id="nov" name="nov"
																	style="width: 50px" />
																days
															</td>
															<td id="w_nov" style="width:180px"></td>
														</tr>
														<tr>
															<td align="right" style="width: 100px">
																Jun:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_jun()" type="text" id="jun" name="jun"
																	style="width: 50px" />
																days
															</td>
															<td id="w_jun" style="width:180px"></td>
															<td align="right" style="width: 100px">
																Dec:
															</td>
															<td align="left" style="width: 100px">
																<input onblur="javascript:check_dec()" type="text" id="dec" name="dec"
																	style="width: 50px" />
																days
															</td>
															<td id="w_dec" style="width:180px"></td>
														</tr>
														<tr>
															<td colspan="5" align="center">
																<input type="submit" value="Confirm Set" onClick="return Sub()"
																	style="width: 100px" />
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