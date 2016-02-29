<%@ page language="java" import="java.util.*,com.xgg.epd.dbs.*"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.xgg.epd.beans.MonthlyControllingBean"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'AssociateFill.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="${path}/css/href.css" rel="stylesheet" type="text/css"/>
	</head>
	<script type="text/javascript">
    var contextPath="<%=request.getContextPath()%>";
   function Del()
   {
      if(confirm('really want to delete selected records?')){
	      document.Rec.action =contextPath+"/ManageRecordAction!del";
	      document.Rec.submit();
      }else
      {
        return false;
      }    
   }
   function Sub()
   {
      if(confirm("really want to upload these records?")){
      document.Rec.action =contextPath+"/ManageRecordAction!insert";
      document.Rec.submit();}
      else{
        return false;
      }
   }
   
   function checkworkingdays(){
	var pass=document.getElementById("Workingdays");
	document.getElementById("wdwaring").innerHTML="<font color='red'>Please input a number</font>";
}

function checkWorkingDays(workingdays){
	if(workingdays==''){
		document.getElementById("wdwaring").innerHTML="<font color='red'>must be filled!</font>";
	}else{
		var yd=/^[1-9]\d*$/;

		if(!yd.exec(workingdays)){
			document.getElementById("wdwaring").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please input a number</font>";
		}else{
			document.getElementById("wdwaring").innerHTML="<img src='images/right.gif'></img>";
		}
	}
}
   </script>
	<body>
		<center>
			<h1>
				Employee Fill The MonthlyControlling
			</h1>
		</center>
		<table width="100%">
			<tr>
				<td align="right">
					<%
						String mark = session.getAttribute("mark").toString();
						if ("1".equals(mark)) {
							out
									.println("<a href='/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/TpmFunction.jsp'>"
											+ "BACK TO TPMFunction"
											+ "&nbsp;&nbsp;&nbsp;&nbsp;</a>");
						} else if ("2".equals(mark)) {
							out
									.println("<a href='/EPD_Budget_Controlling_Tool/AdminNavigateTo?url=/WEB-INF/page/AdminFunction.jsp'>"
											+ "BACK TO AdminFunction"
											+ "&nbsp;&nbsp;&nbsp;&nbsp;</a>");
						}
					%>
				</td>
			</tr>
		</table>
		<center>
			<form action="FillAction" method="post">
				<table>
					<tr>
						<td align="right">
							Project Name:
						</td>
						<td>
							<select name="proName" id="proName"
								style="width: 200px; height: 20px;">
								<option>
									--Plz select one project
								</option>
								<%
									BasicDB basicDb = new BasicDB();
									ArrayList<String> arrayList = basicDb
											.SQuerydb("select  proName from tb_Project");
									int i = 0;
									while (i < arrayList.size()) {
										out.println("<option value=" + arrayList.get(i) + ">"
												+ arrayList.get(i) + "</option>");
										i++;
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td align="right">
							ServiceNr:
						</td>
						<td>
							<select name="Service" id="Service"
								style="width: 200px; height: 20px;">
								<option>
									--Plz select one ServiceNr
								</option>
								<%
									BasicDB basicdb = new BasicDB();
									String sec = session.getAttribute("Sec").toString();
									String sql = "select ServiceNr from tb_SerOfSec where Section=? or Section=? ";
									String[] paras = { "000 General", sec };
									ArrayList<Object[]> aList = basicdb.queryDB(sql, paras);
									int j = 0;
									for (j = 0; j < aList.size(); j++) {
										Object[] obj = aList.get(j);
										out.println("<option value='" + obj[0].toString() + "'>"
												+ obj[0].toString() + "</option>");
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td align="right">
							WorkingDays:
						</td>
						<td>
							<input type="text" name="Workingdays" id="Workingdays"
								style="width: 200px; height: 20px;"  onfocus="javascript:checkworkingdays();" onblur="javascript:checkWorkingDays(this.value)"/>
						</td><td id="wdwaring"></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td align="right">
							Remark:
						</td>
						<td>
							<textarea rows="4" cols="20" name="Remark" id="Remark"
								style="width: 200px; height: 100px;"></textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
				</table>
				<input type="submit" value="submit">
			</form>
			<%
				String empname = session.getAttribute("EmpName").toString();
				ArrayList<MonthlyControllingBean> al = (ArrayList<MonthlyControllingBean>) session
						.getAttribute(empname);
				if (al != null && al.size() != 0) {
					out
							.println("<form action='ManageRecordAction' method='post' name='Rec' id='Rec'>");
					out
							.println("<table border=1 bordercolor=green cellspacing=0>"
									+ "<tr><td align='center'>IsDelete</td><td align='center'>ID</td><td align='center'>Employee Name</td>"
									+ "<td align='center'>Month</td><td align='center'>Section</td><td align='center'>Project Name</td><td align='center'>Service Nr</td><td align='center'>WorkingDays</td><td align='center'>Remark</td></tr>");
					int start;
					int end;
					if(request.getAttribute("start")!=null&&request.getAttribute("end")!=null)
					{
					    start=Integer.parseInt(request.getAttribute("start").toString());
					    end=Integer.parseInt(request.getAttribute("end").toString());   
					}else
					{  
					    start=0;
					    if(al.size()>=5)
					    { 
					        end=5;
					    }
					    else{
					        end=al.size();
					    }
					}				
					for (int k = start; k <end; k++) {
						MonthlyControllingBean bean = al.get(k);
						if (k % 2 == 0) {
							out
									.println("<tr bgcolor='#eff8ff'><td align='center'><input type='checkbox' name='delid' id='delid' value='"
											+ k
											+ "'/></td><td align='center'><a href='/EPD_Budget_Controlling_Tool/ManageRecordAction!modify?id="
											+ k
											+ "'>"
											+ k
											+ "</a></td><td align='center'>"
											+ empname
											+ "</td>"
											+ "<td align='center'>"
											+ bean.getMonth()
											+ "</td><td align='center'>"
											+ bean.getSection()
											+ "</td><td align='center'>"
											+ bean.getProname()
											+ "</td><td align='center'>"
											+ bean.getService()
											+ "</td><td align='center'>"
											+ bean.getWorkingdays()
											+ "</td><td align='center'>"
											+ bean.getRemark() + "</td></tr>");
						} else {
							out
									.println("<tr bgcolor='#b4cff1'><td align='center'><input type='checkbox' name='delid' id='delid' value='"
											+ k
											+ "'/></td><td align='center'><a href='EPD_Budget_Controlling_Tool/ManageRecordAction!modify?id="
											+ k
											+ "'>"
											+ k
											+ "</a></td><td align='center'>"
											+ empname
											+ "</td>"
											+ "<td align='center'>"
											+ bean.getMonth()
											+ "</td><td align='center'>"
											+ bean.getSection()
											+ "</td><td align='center'>"
											+ bean.getProname()
											+ "</td><td align='center'>"
											+ bean.getService()
											+ "</td><td align='center'>"
											+ bean.getWorkingdays()
											+ "</td><td align='center'>"
											+ bean.getRemark() + "</td></tr>");
						}
					}
					out
							.println("</table><br/>");
					int count = al.size();
					int sum = count / 5;
					int pageCount;
					if (count % 5 == 0) {
						pageCount = sum;
					} else {
						pageCount = sum + 1;
					}
					if (sum > 0) {
						for (int s = 0; s < pageCount; s++) {
							out.println("<a href='/EPD_Budget_Controlling_Tool/SplitPageAction?page="+(s+1)+"'>["+(s+1)+"]</a>&nbsp;");
						}
					}
					out.println("current: "+(start/5+1)+"   total:"+pageCount);
					out.println("<br/><input type='submit' value='Submit' onClick='return Sub()'/>&nbsp;&nbsp;&nbsp<input type='submit' value='Delete' onClick='return Del()'/></form>");

				} else {
					out
							.println("<font color='red'>You have not fill any records!!!</font>");

				}
			%>
			<!--<table border="1" cellpadding="1"
				cellspacing="1">
				<s:iterator value="#session.list" var="aa"
					status="st">
					<tr>
					<td>
						<s:property value="#aa.empname" />
					</td>
					<td>
						<s:property value="#aa.proname" />
					</td>
					<td>
						<s:property value="#aa.month" />
					</td>
					<td>
						<s:property value="#aa.section" />
					</td>
					<td>
						<s:property value="#aa.service" />
					</td>
					<td>
						<s:property value="#aa.workingdays"/>
					</td>
					<td>
						<s:property value="#aa.remark" />
					</td>
				</tr>
				</s:iterator>
			</table>  -->
		</center>
	</body>
</html>
