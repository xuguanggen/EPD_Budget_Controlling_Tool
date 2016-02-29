<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Reset PassWord</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
	</head>
	<script type="text/javascript">
	var xmlhttp;
	function checkold() {
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		var oldpwd = document.form1.oldpwd.value;
		xmlhttp.onreadystatechange = clback;
		xmlhttp.open("post",
				"/EPD_Budget_Controlling_Tool/CheckOldPwdAction?oldpwd="
						+ oldpwd, true);
		xmlhttp.send();
	}
	function clback() {
		if (xmlhttp.readyState == 4) {
			var text = xmlhttp.responseText;
			if ("false" == text) {
				document.getElementById("errinfo").innerHTML = "<img src='images/wrong.gif'></img><font color='red'>ERROR</font>";
			} else {
				document.getElementById("errinfo").innerHTML = "<img src='images/right.gif'></img>";
			}
		}
	}
	
	function chkpwd(obj){

	var t=obj.value;
	var id=getResult(t);
	
	//定义对应的消息提示
	var msg=new Array(4);;
	msg[0]="password is too short";
	msg[1]="password strength is bad ";
	msg[2]="password strength is good";
	msg[3]="password strength is high";

	var sty=new Array(4);
	sty[0]=-45;
	sty[1]=-30;;
	sty[2]=-15;
	sty[3]=0;

	var col = new Array(4);
	col[0] = "gray";
	col[1] = "#50AEDD";
	col[2] = "#FF8213";
	col[3] = "green";

	//设置显示效果
	var sWidth=300;
	var sHeight=15;
	var Bobj=document.getElementById("chkResult");
	Bobj.style.fontSize="12px";
	Bobj.style.color=col[id];
	Bobj.style.width=sWidth + "px";;
	Bobj.style.height=sHeight + "px";
	Bobj.style.lineHeight=sHeight + "px";
/*	Bobj.style.background="url no-repeat left " + sty[id] + "px";*/
	Bobj.innerHTML="examination hints：" + msg[id];
}
function getResult(s){
	if(s.length < 4){
		return 0;
	}
	var ls = 0;
	if (s.match(/[a-z]/ig)){
		ls++;
	}
	if(s.match(/[0-9]/ig)){
		ls++;
	}
	if(s.match(/(.[^a-z0-9])/ig)){
		ls++;
	}
	if(s.length < 6 && ls > 0){
		ls--;
	}
	return ls;
}
function checkpasswords(){
	document.getElementById("passwname").innerHTML="Please input 6-20 English letter of alphabets, number or sign and classify big small letter";
}

function checkpasswordss(pwd){
	if(pwd==null||pwd==''){
		document.getElementById("passwname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
	}else if(pwd.length<6){
		document.getElementById("passwname").innerHTML="Password is at least six";
	}else{
		document.getElementById("passwname").innerHTML="<img src='images/right.gif'></img>";
	}
}

function checkpasswords2(){
	document.getElementById("passname2").innerHTML="Please input the password again";
}

function checkpassword2(pwd2){
	if(pwd2==null||pwd2==''){
		document.getElementById("passname2").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
	}else if(pwd2.length<6){
		document.getElementById("passname2").innerHTML="<font color='red'>Password inconformity please re- input</font>";
	}else{
		var pwd=document.getElementById("pwd").value;
		
		if(pwd!=pwd2){
			document.getElementById("passname2").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Password inconformity please re- input</font>";
		}else{
			document.getElementById("passname2").innerHTML="<img src='images/right.gif'></img>";
		}
	}
}

</script>
	<body>
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
											Reset PassWord
											<s:iterator var="i" begin="1" end="45" step="1">
												&nbsp;
											</s:iterator>
											<s:property value="#session.time" />
											<s:property value="#session.EmpName" />
											!&nbsp;&nbsp;
											<img src="images/logout.png"/><font size=1><a href="LoginOutAction" style="color: black;"
									onClick="return window.confirm('Really want to exit the system?')";>Log out</a> </font>
										</div>
										<div id="comtable">
											<center>										
												<form action="ResetPwdAction" name="form1" id="form1">
													<table>
														<tr>
															<td align="right" width="200px">
																Old PassWord:
															</td>
															<td width="300px" height="30">
																<input type="password" id="oldpwd" name="oldpwd"
																	size="30" onblur="checkold()" style="width: 268px; height: 20px;"/>
															</td>
															<td id="errinfo">																
															</td>
														</tr>
														<tr>
															<td align="right" width="200px">
																New PassWord:
															</td>
															<td width="300px" height="30">
																<input type="password" name="password" maxlength="20"
																	id="pwd" onfocus="javascript:checkpasswords();"
																	onblur="javascript:checkpasswordss(this.value);"
																	onkeyup="chkpwd(this)"
																	style="width: 268px; height: 20px;" />
															</td>
															<td id="passwname">																
															</td>
														</tr>
														<tr>
														<td id="chkResult" colspan="3" align="center"></td>
														</tr>
														<tr>
															<td align="right" width="200px">
																Confirm PassWord:
															</td>
															<td width="300px" height="30">
																<input type="password" name="confirm_password"
																	maxlength="20" id="pwd2"
																	onfocus="javascript:checkpasswords2();"
																	onblur="javascript:checkpassword2(this.value);"
																	style="width: 268px; height: 20px;" />
															</td>
															<td id="passname2">																
															</td>
														</tr>
														<tr>
															<td colspan="3" align="center">
																<input type="submit" value="Reset">
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