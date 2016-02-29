<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.xgg.epd.dbs.BasicDB"%>
<%@ page import="org.jfree.data.general.DefaultPieDataset"%> 
<%@ page import="org.jfree.chart.*"%> 
<%@ page import="org.jfree.chart.plot.*"%> 
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%> 
<%@ page import="org.jfree.chart.urls.StandardPieURLGenerator"%> 
<%@ page import="org.jfree.chart.entity.StandardEntityCollection"%> 
<%@ page import="java.io.*"%> 
<%@ page import="java.awt.*"%>
<%@ page import="java.text.*"%>
<%@ page import="org.jfree.chart.urls.StandardPieURLGenerator"%> 
<%@ page import="org.jfree.chart.labels.StandardPieSectionLabelGenerator"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>Pie Chart</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" language="javaScript">
		var contextPath="<%=request.getContextPath()%>";
		function newSubmit()
		{
			 var s=document.getElementById("section").value;
			 if(s=='')
			 {
			    alert("Please select one section first !");
			    return false;
			 }
		     var sm=document.getElementById("smonth").value;
		     var em=document.getElementById("emonth").value;
		     if(sm=='/')
		     {
		         alert("Please select one start month !");
		         return false;
		     }
		     if(sm=='')
		     {
		         alert("Please select one start month !");
		         return false;
		     }
		     if(em=='/')
		     {
		        alert("Please select one end month !");
		        return false;
		     }
		     if(em=='')
		     {
		        alert("Please select one end month !");
		        return false;
		     }
		     var copy_sm;
		     var copy_em;
		     copy_sm=sm+"-11";
		     copy_em=em+"-11";
		     var sarr=copy_sm.split("-");
		     var earr=copy_em.split("-");
		     var starttime=new Date(sarr[0],sarr[1],sarr[2]);    
		     var starttimes=starttime.getTime();  
		     
		     var lktime=new Date(earr[0],earr[1],earr[2]);    
		     var lktimes=lktime.getTime(); 
		     if(starttimes>lktimes)    
             {   
             	  alert("Warning:Start month<End Month !"); 
                  return false;   
             }  
             if(s!='')
             {
             	window.location.href="/EPD_Budget_Controlling_Tool/PieChartAction!CreateSectionPie?smonth="+sm+"&emonth="+em+"&section="+s;
             }
		}
		</script>
		<title>Pie Chart</title>
	</head>
	<body style="overflow-y: scroll">
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
											Pie Chart(Section)
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
											<form action="" method="post" name="form1" id="form1">
												<table width="100%" border="1" bordercolor=green
													height="50%" cellspacing=0>
													<tr style="height: 5px">
														<td align="center" width="30%">
															Start Month:
															<input class="Wdate" name="smonth" id="smonth"
																onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})"
																value=<%String empnameString = session.getAttribute(ip + "EmpName")
					.toString();
			if (session.getAttribute("spieDept" + empnameString) != null) {
				out.println(session.getAttribute("spieDept" + empnameString)
						.toString().trim());
			}%> />
														</td>
														<td width="30%" align="center">
															End Month:
															<input class="Wdate" name="emonth" id="emonth"
																onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})"
																value=<%if (session.getAttribute("epieDept" + empnameString) != null) {
																			out.println(session.getAttribute("epieDept" + empnameString)
						.toString().trim());
			}%> />
														</td>
														<td width="30%" align="center">
															&nbsp;&nbsp;Section:&nbsp;&nbsp;
															<select name="section" id="section" style="width: 150px;">
																<option value="">
																	--Select one Section--
																</option>
																<%
																	String selectSec = "";
																	if (session.getAttribute(ip + "PieDept") != null) {
																		selectSec = session.getAttribute(ip + "PieDept").toString()
																				.trim();
																		for (int s = 1; s <= 6; s++) {
																			String temp = "EPD" + s;
																			if (selectSec.equals(temp)) {
																				out.println("<option selected value='" + temp + "'>"
																						+ temp + "</option>");
																			} else
																				out.println("<option value='" + temp + "'>" + temp
																						+ "</option>");
																		}
																	} else {
																		for (int s = 1; s <= 6; s++) {
																			String temp = "EPD" + s;
																			out.println("<option value='" + temp + "'>" + temp
																					+ "</option>");
																		}
																	}
																%>
															</select>
														</td>
														<td width="10%">														
															<input type="button" onClick="return newSubmit()"
																value="PieChart" />
														</td>
													</tr>
												</table>
											</form>
											<%
												DefaultPieDataset dataset = (DefaultPieDataset) request.getAttribute("sdataset");
												String fileNames="";												
												String section="";
												if(dataset!=null)
												{
												    section=session.getAttribute(ip + "PieDept").toString().trim();;
											        JFreeChart chart=null;
											        String pieName = section+"-ServiceNr";
											        chart =   ChartFactory.createPieChart(pieName, dataset, true, true,false);												   
													PiePlot pieplot = (PiePlot) chart.getPlot();
													pieplot.setURLGenerator(new StandardPieURLGenerator("SectionChartAction","lion"));		
													pieplot.setBackgroundPaint(new Color(238, 224, 229));
													pieplot.setLabelFont(new Font("sans-serif", 0, 11));
													pieplot.setNoDataMessage("No Data");
													pieplot.setCircular(true);
													pieplot.setLabelGap(0.06D);
													pieplot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE); 
													pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
															"{0}={1} ({2})", NumberFormat.getNumberInstance(),
															new DecimalFormat("0.00%")));
													StandardEntityCollection sec = new StandardEntityCollection(); 
													ChartRenderingInfo info = new ChartRenderingInfo(sec); 
													PrintWriter w = new PrintWriter(out);													
													fileNames = ServletUtilities.saveChartAsPNG(chart, 910, 800,
															info, session);
													ChartUtilities.writeImageMap(w, "map0", info, false);
													String graphURL = request.getContextPath() + "/chart/DisplayChart?filename=" + fileNames;
													%>
													<img src="<%= graphURL %>" width=1000 height=900 border=0 usemap="<%="#map0"%>" >
													<% 
												}
											%>
											
											<%
												 dataset = (DefaultPieDataset) request.getAttribute("NoTPMfilename");
												 fileNames="";
												if(dataset!=null)
												{
											        JFreeChart chart=null;
												    String pieName = section+"-Admin";
													PiePlot pieplot = new PiePlot(dataset);
													pieplot.setURLGenerator(new StandardPieURLGenerator("SectionChartAction!createAdminColumnChart?section="+section,"lion"));		
													pieplot.setBackgroundPaint(new Color(238, 224, 229));
													pieplot.setLabelFont(new Font("sans-serif", 0, 11));
													pieplot.setNoDataMessage("No Data");
													pieplot.setCircular(true);
													pieplot.setLabelGap(0.06D);
													pieplot.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE); 
													pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
															"{0}={1} ({2})", NumberFormat.getNumberInstance(),
															new DecimalFormat("0.00%")));
													StandardEntityCollection sec = new StandardEntityCollection(); 
													ChartRenderingInfo info = new ChartRenderingInfo(sec); 
													PrintWriter w = new PrintWriter(out);
													chart =  new JFreeChart(pieName,new Font("sans-serif", 0, 20), pieplot, true); 			
													fileNames = ServletUtilities.saveChartAsPNG(chart, 910, 800,
															info, session);
													ChartUtilities.writeImageMap(w, "map1", info, false);
													String graphURL = request.getContextPath() + "/chart/DisplayChart?filename=" + fileNames;
													%>
													<img src="<%= graphURL %>" width=1000 height=900 border=0 usemap="<%="#map1"%>" >
													<% 
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
						<!--<jsp:include page="footer.jsp"></jsp:include>-->
					</td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>