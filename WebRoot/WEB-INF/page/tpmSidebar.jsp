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
	height: 25px;
}
</style>
		<script type="text/javascript">
	function showsubmenu(sid) {
		initsubmenu(sid);
	}
	function initsubmenu(sid) {
		for ( var i = 1; i <= 5; i++) {
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
	<body>
		<table width="100%">
		<tr><td style="height:20px"></td></tr>
			<tr bgcolor="#295D89">
				<td>
					<table width="100%">
						<tr>
							<td width="88%" class="STYLE1">
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
									href="AdminNavigateTo?url=/WEB-INF/page/tpmFillMC.jsp"
									onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">MonthlyControlling</a> </span>
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
									href="AdminNavigateTo?url=/WEB-INF/page/tpmHistory.jsp"
									onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">History Records</a> </span>
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
								TPM
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
									href="AdminNavigateTo?url=/WEB-INF/page/tpmChart.jsp"
									onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">Column Chart</a> </span>
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
									href="AdminNavigateTo?url=/WEB-INF/page/tpmPieChart.jsp"
									onMouseOver="this.style.color='#FF0000';this.style.fontWeight='bold'" onMouseOut="this.style.color = '#000079';this.style.fontWeight='normal'">Pie Chart</a> </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>