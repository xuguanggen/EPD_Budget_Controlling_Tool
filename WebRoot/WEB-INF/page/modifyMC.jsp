<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page
	import="com.xgg.epd.beans.MonthlyControllingBean,com.xgg.epd.dbs.BasicDB"%>
<%@page import="org.apache.struts2.ServletActionContext"%>

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

		<title>My JSP 'modifyMC.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>
	
    <script type="text/javascript">
    function show()
    {
       var selectIndex = document.getElementById("proName").selectedIndex;
       var selectText = document.getElementById("proName").options[selectIndex].text;
       alert(selectText);
    }
    </script>
	<%
		MonthlyControllingBean bean = (MonthlyControllingBean) request
				.getAttribute("modbean");
		String idString = session.getAttribute("modID").toString();
		System.out.println("JSP to modify infomation:" + idString + "  "
				+ bean.getEmpname() + " " + bean.getProname() + " "
				+ bean.getSection() + " " + bean.getService() + "  "
				+ bean.getRemark() + "  " + bean.getWorkingdays());
	%>
	<body>
		<center>
			<h1>
				Modify MonthlyControlling Record
			</h1>
			<form action="UpdateMcAction" method="post">
				<table>
					<tr>
						<td align="right">
							Project Name:
						</td>
						<td>
							<select name="proName" id="proName"
								style="width: 200px; height: 20px;">
								<option>
									--Please select one project
								</option>
								<%
									BasicDB basicDb = new BasicDB();
									String sql1 = "select distinct ProName from tb_Project where 1=? ";
									String[] paras1 = { "1" };
									ArrayList<Object[]> arrayList = basicDb.queryDB(sql1, paras1);
									int i = 0;
									for (i = 0; i < arrayList.size(); i++) {
										Object[] obj = arrayList.get(i);
										String pro = obj[0].toString().trim();
										if ((pro).equals(bean.getProname().trim())) {
											out.println("<option selected=true value="
													+ obj[0].toString() + ">" + obj[0].toString()
													+ "</option>");
										} else {
											out.println("<option value=" + obj[0].toString() + ">"
													+ obj[0].toString() + "</option>");
										}
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
									--Please select one ServiceNr
								</option>
								<%
									BasicDB basicdb = new BasicDB();
									String sec = session.getAttribute("Sec").toString();
									String sql = "select ServiceNr from tb_SerOfSec where Section=? or Section=? ";
									String[] paras = { "General", sec };
									ArrayList<Object[]> aList = basicdb.queryDB(sql, paras);
									for (int k = 0; k < aList.size(); k++) {
										Object[] obj = aList.get(k);
										String ser = obj[0].toString().trim();
										if (ser.equals(bean.getService().trim())) {
											out.println("<option selected=true value="
													+ obj[0].toString() + ">" + obj[0].toString()
													+ "</option>");
										} else {
											out.println("<option value=" + obj[0].toString() + ">"
													+ obj[0].toString() + "</option>");
										}
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
								value="<%=bean.getWorkingdays()%>"
								style="width: 200px; height: 20px;" />
						</td>
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
								style="width: 200px; height: 100px;"><%=bean.getRemark()%></textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
				</table>
				<input type="submit" value="submit" onClick="show();">
			</form>
		</center>
	</body>
</html>
