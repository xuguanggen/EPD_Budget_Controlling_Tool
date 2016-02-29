<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.xgg.epd.beans.DeadlineBean"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.xgg.epd.actions.DeadLineAction"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Set</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">
	    function setRate()
	    {
	        var y=document.getElementById("year").value;
	        if(y=='')
	        {
	           alert("Please select one year first !");
	           return false;
	        }else
	        {
	           var t=window.confirm("Really want to set this value?");
	           if(t==true)
	           {
	           	  document.rateform.action="SetRateAction";
	              document.rateform.submit();
	           }
	           
	        }		        
	    }
	    
		    
	function checkRate(ratevalue) {
		if (ratevalue == '') {
			alert("Please input the rate value !");
		} else {
			var yd =/^[0-9].*$/;
			if (!yd.exec(ratevalue)) {
				alert("Error rate value !");
			} 
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
			
			if(request.getAttribute("process")!=null)
			{
			    String yj=request.getAttribute("process").toString().trim();
			    out
						.println("<script type='text/javascript'>alert('Submit Success ! ');</script>");
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
											Set
											<div id="employee">
												<s:property value="#session.time" />
												<%
													String ip = request.getRemoteAddr().toString();
													out.println(session.getAttribute(ip + "EmpName"));
												%>
												!
												<img src="images/logout.png" />
												<font size=1><a href="LoginOutAction" style="";>Log
														out</a> </font>
											</div>
										</div>
										<div id="comtable">
											<div style="margin: 0% 0% 0% 6%" align="left">
												<font
													style="font-family: 'Times New Roman'; font-size: 20px;">Rate:</font>
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid : DXImageTransform.Microsoft.Shadow (color #5151A2, direction :               145, strength :               15 )">
											<center>
												<form action="" method="post" id="rateform"
													name="rateform" enctype="multipart/form-data">
													<table border="0">
														<tr>
															<td align="right">
																Year:
															</td>
															<td>
																<input class="Wdate" name="year" id="year"
																	onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy'})" />
															</td>
														</tr>
														<tr>
															<td align="right">
																Rate:
															</td>
															<td>
																<input type="text" name="rate" id="rate" onblur="checkRate(this.value)"
																	style="width: 100px" />
																€/hour
															</td>
														</tr>
														<tr>
															<td colspan="2">
																<input type="submit" value="Set"
																	style="margin-left: 20%; margin-bottom: 10px;" onClick="return setRate()"/>
															</td>
														</tr>
													</table>
												</form>
											</center>
											<div style="margin: 2% 0% 0% 6%" align="left">
												<font
													style="font-family: 'Times New Roman'; font-size: 20px;">Deadline:</font>
											</div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid :   DXImageTransform.Microsoft.Shadow (   color #5151A2, direction :               145, strength :               15 )">
											<center>
												<div style="margin: 2% 65% 0% 10%">
													<form action="DeadLineAction" method="post">
														<table cellspacing=0 border=1 color='#458B00'>
															<tr>
																<td colspan=2 align="center">
																	<%
																		String[] months = { "January", "February", "March", "April", "May", "June",
																				"July", "August", "September", "October", "November", "December" };
																		Calendar c = Calendar.getInstance();
																		int currentmonth = c.get(Calendar.MONTH) + 1;
																		out.println("<font size=5 color='#458B00'>"+months[currentmonth - 1]+"</font>");
																	%>
																</td>
															</tr>
															<tr>
																<td align="right">
																	<font size=4 color='#458B00'>Current Deadline:</font>
																</td>
																<td align="center">
																	<%
																		out.println("<font size=2 color='#458B00'>");
																		Calendar calendar = Calendar.getInstance();
																		int year = calendar.get(Calendar.YEAR);
																		int month = calendar.get(Calendar.MONTH) + 1;
																		int today = calendar.get(Calendar.DATE);
																		if (month == 12) {
																			out.println(year + "-12-" + DeadlineBean.startDate + "~"
																					+ (year + 1) + "-1-" + DeadlineBean.endDate);
																		} else {
																			out.println(year + "-" + month + "-" + DeadlineBean.startDate
																					+ "&nbsp;~&nbsp;" + year + "-" + (month + 1) + "-"
																					+ DeadlineBean.endDate);
																		}
																	%>
																</td>
															</tr>
															<tr>
																<td align="right">
																	<font size=4>Start Date:</font>
																</td>
																<td>
																	<input class="Wdate" name="stime" id="stime"
																		onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
																</td>
															</tr>
															<tr>
																<td align="right">
																	<font size=4>End Date:</font>
																</td>
																<td>
																	<input class="Wdate" name="etime" id="etime"
																		onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd'})" />
																</td>
															</tr>
															<tr>
																<td colspan="2" align="center">
																	<input type="submit" value="Confirm Modify" />
																</td>
															</tr>
														</table>
													</form>
												</div>
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