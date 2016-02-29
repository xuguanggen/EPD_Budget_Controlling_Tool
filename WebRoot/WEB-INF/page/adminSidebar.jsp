<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<style type="text/css">
.STYLE1 {
	font-size: 13px;
	font-weight: bold;
	color: white;
}

span {
	font-size: 12px;
}

body {
	font-family: Arial;
}

td {
	height: 22px;
}
</style>
		<script type="text/javascript">
	function showsubmenu(sid) {
		initsubmenu(sid);
	}
	function initsubmenu(sid) {
		for ( var i = 1; i <= 3; i++) {
			if (i == sid) {
				whichEl = eval("submenu" + sid);
				if (whichEl.style.display == "none") {
					eval("submenu" + sid + ".style.display=\"\";");
				} else {
					eval("submenu" + i + ".style.display=\"none\";");
				}
			} else {
				eval("submenu" + i + ".style.display=\"none\";");
			}
		}
	}
</script>
	</head>
	<body onload="initsubmenu();">
		<table width="100%">
			<tr>
				<td></td>
			</tr>
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td class="STYLE1">
								BC System
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="border: 1px #cccccc solid;">
					<table>
						<tr>
							<td>
								<span><font color="#000079">Version 1.1</font></span>
							</td>
						</tr>
						<tr>
							<td>
								<span><font color="#000079">(04/01/2015)</font></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td class="STYLE1">
								MySelf
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="border: 1px #cccccc solid;">
					<table width="100%">
						<tr>
							<td width="10">
								<div>
									<img src="images/epd/arr01.gif">
								</div>
							</td>
							<td>
								<span><a
									href="AdminNavigateTo?url=/WEB-INF/page/adminFillMC.jsp"
								 onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">MonthlyControlling</a>
								</span>
							</td>
						</tr>
						<tr>
							<td width="10">
								<div>
									<img src="images/epd/arr01.gif">
								</div>
							</td>
							<td>
								<span><a
									href="AdminNavigateTo?url=/WEB-INF/page/adminHistory.jsp"
								 onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">History Records</a>
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td class="STYLE1">
								Employee
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="border: 1px #cccccc solid;">
					<table width="100%">
						<tr>
							<td onclick="showsubmenu(1)">
								<div style="cursor: hand">
									<img src="images/epd/arr01.gif">
									<span style="color:#000079">Manage</span>
								</div>
								<div id="submenu1">
									<table width="88%" style="text-align: left;" align="right">
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
													href="AdminNavigateTo?url=/WEB-INF/page/adminAddEmp.jsp">Add</a>
												</span>
											</td>
										</tr>
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'" href="ShowEmployeeAction!showAll?pageNow=1">Search</a>
												</span>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td class="STYLE1">
								Project
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="border: 1px #cccccc solid;">
					<table width="100%">
						<tr>
							<td onclick="showsubmenu(2)">
								<div style="cursor: hand">
									<img src="images/epd/arr01.gif">
									<span style="color:#000079">Manage</span>
								</div>
								<div id="submenu2">
									<table width="88%" style="text-align: left;" align="right">
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
													href="AdminNavigateTo?url=/WEB-INF/page/adminAddPro.jsp">Add</a>
												</span>
											</td>
										</tr>
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'" href="showProjectAction!showAll?pageNow=1">Search</a>
												</span>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif">
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminAddServiceNr.jsp"
										>Add ServiceNr</a>
									</span>
								</div>
							</td>
						</tr>					
					</table>
				</td>
			</tr>
		</table>
		<table width="100%">
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td class="STYLE1">
								Administration
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="border: 1px #cccccc solid;">
					<table width="100%">
					<tr>
							<td onclick="showsubmenu(3)">
								<div style="cursor: hand">
									<img src="images/epd/arr01.gif">
									<span style="color:#000079">Chart</span>
								</div>
								<div id="submenu3">
									<table width="88%" style="text-align: left;" align="right">
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
													href="AdminNavigateTo?url=/WEB-INF/page/adminChart.jsp">
														TPM</a> </span>
											</td>
										</tr>
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
													href="AdminNavigateTo?url=/WEB-INF/page/adminPieChart.jsp">
														PieChart (Project)</a> </span>
											</td>
										</tr>
										<tr>
											<td width="10">
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
													href="AdminNavigateTo?url=/WEB-INF/page/adminSectionPieChart.jsp">
														PieChart (Section)</a> </span>
											</td>
										</tr>
										<tr>
											<td>
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'" href="AdminNavigateTo?url=/WEB-INF/page/BaseTopTen.jsp">Top10 Budget</a> </span>
											</td>
										</tr>
										<tr>
											<td>
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'" href="AdminNavigateTo?url=/WEB-INF/page/CostTopTen.jsp">Top10 Cost</a> </span>
											</td>
										</tr>
										<tr>
											<td>
												<div>
													<img src="images/epd/dot01.gif">
												</div>
											</td>
											<td>
												<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'" href="AdminNavigateTo?url=/WEB-INF/page/PercentTopTen.jsp">Top10 Exceed</a> </span>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif">
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminUpload.jsp"
										>Import / Export</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif">
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminEmail.jsp"
										>Email</a>
									</span>
								</div>
							</td>
						</tr>				
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif">
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminRate.jsp"
										>Set</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif">
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminCorrect.jsp"
										>Correct</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div>
									<img src="images/epd/arr01.gif" />
									<span><a onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color='#000079';this.style.fontWeight='normal'"
										href="AdminNavigateTo?url=/WEB-INF/page/adminSetCalendar.jsp"
										 >Bosch Calendar</a>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>		
	</body>