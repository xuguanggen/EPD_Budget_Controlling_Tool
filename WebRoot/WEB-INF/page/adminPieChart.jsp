<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>
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

		<title>Show Chart</title>
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
		 var xmlHttp = false; //全局变量，用于记录XMLHttpRequest对象
		function createXMLHttpRequest() {
			if (window.ActiveXObject) { //Internet Explorer时，创建XMLHttpRequest对象的方法
				try {
					xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					try {
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
						//旧版本的Internet Explorer，创建XMLHttpRequest对象
					} catch (e) {
						window.alert("创建XMLHttpRequest对象错误" + e);
					}
				}
			} else if (window.XMLHttpRequest) { //mozilla时，创建XMLHttpRequest对象的方法
				xmlHttp = new XMLHttpRequest();
			}
			if (!(xmlHttp)) { //未成功创建XMLHttpRequest对象
				window.alert("创建XMLHttpRequest对象异常！");
			}
		}
	   function proChange(objVal) {
			createXMLHttpRequest(); //创建XMLHttpRequest对象
			//document.getElementById("proType").length = 1; //根据ID获取指定元素，并赋值
			xmlHttp.onreadystatechange = cityList; //指定onreadystatechange处理函数	
			var url = "/EPD_Budget_Controlling_Tool/TpmChangeAction!projectInitByTPM?tpmname="
					+ objVal; //请求的URL地址
			xmlHttp.open("GET", url, true);
			xmlHttp.send(null);
		}
		function cityList() { //onreadystatechange的处理函数
			if (xmlHttp.readyState == 4) {
				if (xmlHttp.status == 200) {
					parseXML(xmlHttp.responseXML); //解析服务器返回的XML数据
				}
			}
		}
		
		function parseXML(xmlDoc)
		{	
			var len=xmlDoc.getElementsByTagName("proType");
		    var exeDiv=document.getElementById("pro");
		    var v="<table cellspacing=0 border=1 corlor=green width='150%' height='100%'>"
		    for(var i=0;i<len.length;i++)
		    {
				if(i%5==0)
				{
				  v=v+ "<tr style='height:3px'><td align='left' style='height:3px;'><input type='checkbox' name='pros' id='pros' value='"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"'>"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"</td>";
				}
				if(i%5==1){
			  	  v= v+"<td align='left' style='height:3px'><input type='checkbox' name='pros' id='pros' value='"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"'>"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"</td>";
				}
				if(i%5==2){
				  v= v+"<td align='left' style='height:3px'><input type='checkbox' name='pros' id='pros' value='"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"'>"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"</td>";
				}
				if(i%5==3)
				{
				   v=v+ "<td align='left' style='height:3px'><input type='checkbox' name='pros' id='pros' value='"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"'>"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"</td>";
				}
				if((i+1)%5==0)
				{
				   v=v+ "<td align='left' style='height:3px'><input type='checkbox' name='pros' id='pros' value='"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"'>"+xmlDoc.getElementsByTagName("proType")[i].firstChild.data+"</td></tr>";
				}				 
		   }
		   if(len.length%5==0)
		   {
		       v=v+"</table>";
		   }else
		   {  
		       v=v+"</tr></table>";
		   }
		   exeDiv.innerHTML=v;
		}
		function newSubmit()
		{
			 var t=document 
		     var str=document.getElementsByName("pros");
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
		     sm=sm+"-11";
		     em=em+"-11";
		     var sarr=sm.split("-");
		     var earr=em.split("-");
		     var starttime=new Date(sarr[0],sarr[1],sarr[2]);    
		     var starttimes=starttime.getTime();  
		     
		     var lktime=new Date(earr[0],earr[1],earr[2]);    
		     var lktimes=lktime.getTime(); 
		     if(starttimes>lktimes)    
             {   
             	  alert("Warning:Start month<End Month !"); 
                  return false;   
             }     
	         var objarray=str.length;
			 var chestr="";
	         for (i=0;i<objarray;i++)
			{
			  if(str[i].checked == true)
			  {
			   chestr+=str[i].value+",";
			  }
			}
			if(chestr=="")
			{
			  alert("Please select one project first!");
			  return false;
			}
			else
			{
			   document.form1.action =contextPath+"/PieChartAction";
	           document.form1.submit();
	           return true;
			}
		}
		

	
		function check() {		
			var x = document.getElementsByName("chartType");
			if (!(x[1].checked == true && x[2].checked == true && x[3].checked == true)) {
				x[0].checked = false;
			} else {
				x[0].checked = true;
			}
			var str=document.getElementsByName("pros");
		         var objarray=str.length;
				 var chestr="";
		         for (i=0;i<objarray;i++)
				{
				  if(str[i].checked == true)
				  {
				   chestr+=str[i].value+",";
				  }
				}
				if(chestr!=""){
					document.form1.action = "/EPD_Budget_Controlling_Tool/AllChartAction!createPieChart";
					document.form1.submit(); 
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
											Pie Chart(Project)
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
														<td align="center" width="25%">
															Start Month:
															<input class="Wdate" name="smonth" id="smonth"
																onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})"
																value=<%String empnameString = session.getAttribute(ip + "EmpName")
					.toString();
			if (session.getAttribute("spie" + empnameString) != null) {
				out.println(session.getAttribute("spie" + empnameString)
						.toString().trim());
			}%> />
														</td>
														<td width="25%" align="center">
															End Month:
															<input class="Wdate" name="emonth" id="emonth"
																onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM'})"
																value=<%if (session.getAttribute("epie" + empnameString) != null) {
																	out.println(session.getAttribute("epie" + empnameString)
																			.toString().trim());
			}%> />
														</td>
														<td width="25%">
															TPM:
														</td>
														<td width="25%" align="left">
															<select name="tpmname" id="tpmname"
																onchange="proChange(this.value);" style="width: 150px;">
																<option value="">
																	--TPM Name--
																</option>
																<%
																	String tpmname = "";
																	if (session.getAttribute("tpmnamePie" + empnameString) != null) {
																		tpmname = session.getAttribute("tpmnamePie" + empnameString)
																				.toString().trim();
																	}
																	BasicDB basicdb = new BasicDB();
																	String sql1 = "select distinct tb_Employee.EmpName,tb_Employee.EId from tb_Employee,tb_Summary where tb_Employee.EId=tb_Summary.TPM and 1=?";
																	String[] paras1 = { "1" };
																	ArrayList<Object[]> alist = basicdb.queryDB(sql1, paras1);
																	int j = 0;
																	while (j < alist.size()) {
																		Object[] obj = alist.get(j);
																		if (tpmname.equals(obj[1].toString().trim())) {
																			out.println("<option selected value='" + obj[1].toString()
																					+ "'>" + obj[0].toString() + "</option>");
																		} else {
																			out.println("<option value='" + obj[1].toString() + "'>"
																					+ obj[0].toString() + "</option>");
																		}
																		j++;
																	}
																%>
															</select>
														</td>
													</tr>
													<tr>
														<td colspan="3" style="width: 95%">
															<div id="pro"
																style="width: 100%; height: 105px; overflow-y: scroll;">
																<%
																	if (request.getAttribute("projectList") != null) {
																		ArrayList<String> al = (ArrayList<String>) request
																				.getAttribute("projectList");
																		String[] clist = (String[]) request.getAttribute("check");
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
																			for (k = 0; k < clist.length; k++) {
																				if (clist[k].equals(temp)) {
																					out
																							.println("<input checked=true type='checkbox' name='pros' id='pros' value='"
																									+ temp + "'/>&nbsp;&nbsp;" + temp);
																					break;
																				}
																			}
																			if (k == clist.length) {
																				out
																						.println("<input type='checkbox' name='pros' id='pros' value='"
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
															<input value="Pie Chart" type="submit"
																onClick="return newSubmit()"
																style="height: 105px; width: 162px" />
														</td>
													</tr>
												</table>
											</form>
											<%
												String []checkPros=(String[]) request.getAttribute("check");
												if(checkPros!=null&&checkPros.length!=0){
												    DefaultPieDataset[] dataset=(DefaultPieDataset[])request.getAttribute("dataset");
												    String []fileNames=new String[checkPros.length];
													for(j=0;j<checkPros.length;j++)
													{
													    JFreeChart chart=null;
													    String pieName = checkPros[j];
														PiePlot pieplot = new PiePlot(dataset[j]);
														pieplot.setURLGenerator(new StandardPieURLGenerator("ServiceNrChartAction?pro="+checkPros[j].toString().trim(),"lion"));		
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
														fileNames[j] = ServletUtilities.saveChartAsPNG(chart, 910, 800,
																info, session);
														ChartUtilities.writeImageMap(w, "map"+j, info, false);
														String graphURL = request.getContextPath() + "/chart/DisplayChart?filename=" + fileNames[j]; 
													%>
													<img src="<%= graphURL %>" width=1000 height=900 border=0 usemap="<%="#map"+j%>" >
													<%
													}
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
					<td><!--<jsp:include page="footer.jsp"></jsp:include>-->
					</td>
				</tr>
			</table>
		</div>
		<!-- end container -->
	</body>
</html>