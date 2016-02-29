<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*,com.xgg.epd.dbs.*" pageEncoding="utf-8"%>
<%@ page import="com.xgg.epd.beans.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Update Budget</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery/jquery-1.2.6.min.js"></script>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css"
			href="js/jquery/ddcombo/jquery.ddcombo.css" />
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/jquery.ready.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/jquery.flydom-3.1.1.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.bgiframe.min.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.dimensions.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/jquery.ajaxQueue.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/lib/autocomplete/thickbox-compressed.js"></script>
		<script type="text/javascript"
			src="js/jquery/ddcombo/jquery.ddcombo.js"></script>
		<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<script type="text/javascript">
	 function changeBudget(objId)
	 {
	   if(window.confirm("Really want to update "+objId +" budget ?"))
	   {
	        var t=document.getElementById('t'+objId).value;
		    var e=document.getElementById('e'+objId).value;
		    var a=document.getElementById('a'+objId).value;
		    var r=document.getElementById('r'+objId).value;
		    var p=document.getElementById("p"+objId).value;
		    var it=parseInt(t);
		    var ie=parseInt(e);
		    var ia=parseInt(a);
		    var ir=parseInt(r);
		    if((ie+ia+ir)!=it)
		    {
		       alert("Waring:"+ie+"+"+ia+"+"+ir+"!="+it);
		    }else 
		    {
			    var xmlHttp;  
				if(window.ActiveXObject)  
				{  
				    xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
				}  
				else if(window.XMLHttpRequest)  
				{  
				   xmlHttp=new XMLHttpRequest();  
				} 
				var datastr="proName="+p+"&year="+objId+"&budget="+it+"&ebudget="+ie+"&abudget="+ia+"&rbudget="+ir;
				var url="ShowBudgetAction!ChangeBudget";
				xmlHttp.open("POST",url,false);  
				xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");  
				xmlHttp.send(datastr);    
		    }
	   }else
	   {
	      return ;
	   }
	 }	
	 
	 function  AddBudget()
	 {
	    var y=document.getElementById("ayear").value;	   
	   if(window.confirm("Really want to add "+y+" budget?"))
	   {
	       var t=document.getElementById("budget").value;
	   	   var e=document.getElementById("ebudget").value;
	       var a=document.getElementById("abudget").value;
	       var r=document.getElementById("rbudget").value;
	        var p=document.getElementById("apro").value;
	        var it=parseInt(t);
		    var ie=parseInt(e);
		    var ia=parseInt(a);
		    var ir=parseInt(r);	      
	        if((ie+ia+ir)!=it)
		    {
		       alert("Waring:"+ie+"+"+ia+"+"+ir+"!="+it);
		    }else 
	             window.location.href="ShowBudgetAction!AddBudget?year="+y+"&budget="+it+"&ebudget="+ie+"&abudget="+ia+"&rbudget="+ir+"&proName="+p;
	   }else
	   {
	     return;
	   }
	 }
   </script>
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
											Update &nbsp;Budget
											<div id="employee">
												<s:property value="#session.time" />
												<%
													out.println(session.getAttribute(ip2 + "EmpName"));
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
											<div  style="position:relative;left:-30%;top:0%;" ><b><font color='green' size=5><s:property value="#request.proBudget"></s:property></font></b></div>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid :     DXImageTransform.Microsoft.Shadow (   color #5151A2, direction :                   145, strength :                   15 )">
											<center>
												<form action="" method="post" id="budgetform" name="budgetform"
													enctype="multipart/form-data">
													<%
													if(request.getAttribute("BudgetList")!=null)
													{
													    ArrayList<ProBudgetBean> proBudgetBeans=(ArrayList<ProBudgetBean>)request.getAttribute("BudgetList");
													    if(proBudgetBeans.size()>=0)
													    {
													       out.println("<table border=1 cellspacing=0 bordercolor=green><tr><th>Year</th><th>Total Budge&nbsp;(EUR)</th><th>EPD Budget&nbsp;(EUR)</th><th>AE Budget&nbsp;(EUR)</th><th>RBEI Budget&nbsp;(EUR)</th><th>UpDate</th></tr>");
													       for(int i=0;i<proBudgetBeans.size();i++)
													       {
													          ProBudgetBean pBean=proBudgetBeans.get(i);
													          String year=pBean.getYear();
													          out.println("<tr>");
													          int totalBudget=pBean.getEPD_Budget()+pBean.getAE_Budget()+pBean.getRBEI_Budget();
													          out.println("<td align='center' width='20%'><b><font color=red>"+year+"</font></b></td>");													          
													          out.println("<td align='center' width='16%'><input type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center' id='t"+year+"' name='t"+year+"' value="+totalBudget+"></td>");
													          out.println("<td align='center' width='16%'><input type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center' id='e"+year+"' name='e"+year+"' value="+pBean.getEPD_Budget()+"></td>");
													          out.println("<td align='center' width='16%'><input type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center' id='a"+year+"' name='a"+year+"' value="+pBean.getAE_Budget()+"></td>");
													          out.println("<td align='center' width='16%'><input type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center' id='r"+year+"' name='r"+year+"' value="+pBean.getRBEI_Budget()+"></td>");													         
													          out.println("<td align='center' width='16%'><input onClick='changeBudget(this.id)' type='button' id="+year+" name="+year+" value='update'/><input type='hidden' id='p"+year+"' name='p"+year+"' value='"+pBean.getProName().toString().trim()+"'/>");
													          out.println("</tr>");
													       }													         
													       out.println("</table>");
													    }
													}												
													 %>
												</form>
											<hr width=80% size=3 color=#5151A2
												style="filter: progid : DXImageTransform.Microsoft.Shadow(color#5151A2,direction:145, strength:15 )">												
											<div  style="position:relative;left:-35%;"><b><font color='green' size=5>Add New Budget</font></b></div>
											<table border=1 cellspacing=0 bordercolor=green>
											<tr><th>Year</th><th>Total Budge&nbsp;(EUR)</th><th>EPD Budget&nbsp;(EUR)</th><th>AE Budget&nbsp;(EUR)</th><th>RBEI Budget&nbsp;(EUR)</th><th>Add</th></tr>
											<tr><td align='center' width='20%'><input class="Wdate" name="ayear" id="ayear"
																	onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy'})" /></td>
											<td align='center' width='16%'><input id='budget' type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center'/></td>
											<td align='center' width='16%'><input id='ebudget'type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center'/></td>
											<td align='center' width='16%'><input id='abudget' type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center'/></td>
											<td align='center' width='16%'><input id='rbudget' type='text' style='border:2px solid grey;width:95%;height:110%;text-align:center'/></td>	
											<td align='center' width='16%'><input type="button" value="Add" onClick="AddBudget()"/><%
											out.println("<input type='hidden' id='apro' name='apro' value='"+request.getAttribute("proBudget").toString().trim()+"'/>");										
											 %></td>			
										</tr>									
											</table>
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