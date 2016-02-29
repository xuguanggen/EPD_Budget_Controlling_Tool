<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.xgg.epd.dbs.BasicDB"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">		
function checknames(){
	if(document.getElementById("empid").value=='e.g.88842221')
     {
         document.getElementById("empid").value='';
         document.getElementById("empid").style.color='#000';
     }     
	document.getElementById("passname").innerHTML="Please input 8 numbers";
}


function checkid(user){
	if(user==''){
		document.getElementById("passname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		document.getElementById("empid").value='e.g.88842221';
		document.getElementById("empid").style.color='#999999';
	}else{
		var result=user.match(/^\d{8}$/);		
		if(result==null){
		   document.getElementById("passname").innerHTML="<img src='images/wrong.gif'></img>Please input 8 numbers";
		}else{
			document.getElementById("passname").innerHTML="<img src='images/right.gif'></img>";
		}
	}
}
function checkmails(){

	if(document.getElementById("mail").value='e.g.honglun.li@cn.bosch.com')
	{
	   document.getElementById("mail").value='';
	   document.getElementById("mail").style.color='#000';
	}
	document.getElementById("mailname").innerHTML="Please input in common use mailbox";
}

function checkmail(){
	var mail=document.getElementById("mail");
	if(mail.value==''||mail.value==null){
	    document.getElementById("mail").value='e.g.honglun.li@cn.bosch.com';
	    document.getElementById("mail").style.color='#999999';
		document.getElementById("mailname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
	}else{
		var qc="-";		
		var mailvalue=mail.value;
		mailvalue=mailvalue.replace(new RegExp(qc), "");
		var m=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
		if(!m.exec(mailvalue)){
			document.getElementById("mailname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please input a right email</font>";
		}else{
			document.getElementById("mailname").innerHTML="<img src='images/right.gif'></img>";
		}
	}
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

function checkuname(){
     if(document.getElementById("username").value=='e.g.Li Honglun')
     {
         document.getElementById("username").value='';
         document.getElementById("username").style.color='#000';
     }     
	document.getElementById("unames").innerHTML="Please input English";
}

function checkuser(user){
	if(user==''){
	    document.getElementById("username").value='e.g.Li Honglun';
	    document.getElementById("username").style.color='#999999';
		document.getElementById("unames").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
	}else{
		user=user.replace(/\s+/g,"")
		var yd=/^[A-Za-z\u4E00-\u9fa5]*$/;
		if(!yd.exec(user)){
			document.getElementById("unames").innerHTML="<img src='images/wrong.gif'></img>Please input English";
		}else{
			document.getElementById("unames").innerHTML="<img src='images/right.gif'></img>";
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

//定义检测函数,返回0/1/2/3分别代表无效/差/一般/强
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

function check(){

	var name=document.getElementById("passport").value;
	var mail=document.getElementById("mail").value;
	var pwd=document.getElementById("pwd").value;
	var pwd2=document.getElementById("pwd2").value;
	var captcha=document.getElementById("captcha").value;
	var username=document.getElementById("username").value;
	var m=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;
	var patrn=/^[a-zA-Z][a-zA-Z0-9]*$/;
	var user=/^[A-Za-z\u4E00-\u9fa5]*$/;
	
	if(name==''){
		document.getElementById("passname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		return false;
	}else if(name.length<4){
		document.getElementById("passname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>登录名太短了，至少4位</font>";
		return false;
	}else if(!patrn.exec(name)){
		document.getElementById("passname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>以字母开头，4-20位字母或数字</font>";
		return false;
	}else if(mail==''){
		document.getElementById("mailname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		return false;
	}else if(!m.exec(mail)){
		document.getElementById("mailname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>请输入正确的邮箱地址</font>";
		return false;
	}else if(pwd==''){
		document.getElementById("passwname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		return false;
	}else if(pwd.length<6){
		document.getElementById("passwname").innerHTML="<img src='images/wrong.gif'></img><font color='red'>请输入6-20位英文字母、数字或符号，区分大小写</font>";
		return false;
	}else if(pwd2==''){
		document.getElementById("passname2").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		return false;
	}else if(pwd2.length<6){
		document.getElementById("passname2").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Password inconformity please re- input</font>";
		return false;
	}else if(pwd!=pwd2){
		document.getElementById("passname2").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Password inconformity please re- input</font>";
		return false;
	}else if(username==''){
		document.getElementById("unames").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Must to be filled</font>";
		return false;
	}else if(!user.exec(username)){
		document.getElementById("unames").innerHTML="<img src='images/wrong.gif'></img><font color='red'>Please input English</font>";
		return false;
	}else{
		return true;
	}
	
}
</script>
<html>
	<head>
		<link href="css/body.css" style="text/css" rel="stylesheet">
		<link href="css/register.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Add Employee</title>
	</head>
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
									<div style="width: 100%;height:65%">
										<div id="title">
											Add Employee
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
												<form id="register" name="user_register_form"
													action="RegisterAction!addEmp" method="post"
													onsubmit="return check();">
													<table>
														<tr>
															<td width="160px" align="right">
																<span class="font_red">*</span>Employee ID：
															</td>
															<td width="300px" height="30">
																<input type="text" name="empid" maxlength="20"
																	value="e.g.88842221" id="empid"
																	onfocus="javascript:checknames();"
																	onblur="javascript:checkid(this.value);"
																	style="width: 268px; height: 20px; color: #999999" />
															</td>
															<td id="passname"></td>
														</tr>

														<tr>
															<td align="right">
																<span class="font_red">*</span>Employee Name:
															</td>
															<td>
																<input type="text" name="empname" id="username"
																	value="e.g.Li Honglun"
																	onfocus="javascript:checkuname();"
																	onblur="javascript:checkuser(this.value);"
																	style="width: 268px; height: 20px; color: #999999" />
															</td>
															<td id="unames"></td>
														</tr>

														<tr>
															<td align="right">
																<span class="font_red">*</span>Section:
															</td>
															<td>
																<select name="section" id="section"
																	style="width: 268px; height: 20px;">
																	<option>
																		--Please select one section
																	</option>
																	<option value="EPD">
																		EPD
																	</option>
																	<option value="EPD1">
																		EPD1
																	</option>
																	<option value="EPD2">
																		EPD2
																	</option>
																	<option value="EPD3">
																		EPD3
																	</option>
																	<option value="EPD4">
																		EPD4
																	</option>
																	<option value="EPD5">
																		EPD5
																	</option>
																	<option value="EPD6">
																		EPD6
																	</option>
																	<option value="Guest">
																		Guest
																	</option>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																Multi-role:
															</td>
															<td>
																<select name="Asection" id="Asection"
																	style="width: 268px; height: 20px;">
																	<option value=''>
																		--Please select one section
																	</option>
																	<option value="EPD">
																		EPD
																	</option>
																	<option value="EPD1">
																		EPD1
																	</option>
																	<option value="EPD2">
																		EPD2
																	</option>
																	<option value="EPD3">
																		EPD3
																	</option>
																	<option value="EPD4">
																		EPD4
																	</option>
																	<option value="EPD5">
																		EPD5
																	</option>
																	<option value="EPD6">
																		EPD6
																	</option>
																</select>
															</td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Email:
															</td>
															<td>
																<input type="text" name="email" maxlength="50"
																	value="e.g.honglun.li@cn.bosch.com" id="mail"
																	onfocus="checkmails();" onblur="checkmail();"
																	style="width: 268px; height: 20px; color: #999999" />
															</td>
															<td id="mailname"></td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>PassWord:
															</td>
															<td>
																<input type="password" name="password" maxlength="20"
																	id="pwd" onfocus="javascript:checkpasswords();"
																	onblur="javascript:checkpasswordss(this.value);"
																	onkeyup="chkpwd(this)"
																	style="width: 268px; height: 20px;" />
															</td>
															<td id="passwname"></td>
														</tr>
														<tr style="height: 8px">
															<td></td>
															<td id="chkResult" colspan="2"></td>
														</tr>
														<tr>
															<td align="right">
																<span class="font_red">*</span>Confirm PassWord:
															</td>
															<td>
																<input type="password" name="confirm_password"
																	maxlength="20" id="pwd2"
																	onfocus="javascript:checkpasswords2();"
																	onblur="javascript:checkpassword2(this.value);"
																	style="width: 268px; height: 20px;" />
															</td>
															<td id="passname2"></td>
														</tr>
														<tr>
															<td align="right">
																Mark:
															</td>
															<td>
																<select name="mark" id="mark"
																	style="width: 268px; height: 20px;">
																	<option>
																		--Please select the Mark
																	</option>
																	<option value="3">
																	  Admin&TPM																
																	</option>
																	<option value="2">
																		Admin
																	</option>
																	<option value="1">
																		TPM
																	</option>
																	<option value="0">
																		General employee
																	</option>
																	<option value="4">
																		Guest
																	</option>
																</select>
															</td>
														</tr>
														<tr>
															<td></td>
															<td>
																<input type="image" alt="Submit"
																	src="images/register.jpg" id="register_0"
																	value="Submit" border="none" />
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