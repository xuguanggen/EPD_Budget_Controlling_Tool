<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="org.apache.struts2.components.FieldError"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="css/login.css" style="text/css" rel="stylesheet">
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>EPD_CN_Budget_Controlling_Tool -Login</title>
	</head>
	<script type="text/javascript"> 
 function rset()
 {
    document.getElementById("perID").value='';
    document.getElementById("pwd").value='';
    document.getElementById("perID").focus();
 }
 function load()
 { 
    
 }
 </script>
	<%
   request.setCharacterEncoding("GBK");
   String ipString=request.getLocalAddr();
  // String perID=request.getParameter(ipString+"perID");
  // String pwd=request.getParameter(ipString+"pwd");
  String perID="";
  String pwd="";
   Cookie cookies[]=request.getCookies();
   if(cookies!=null)
   {
     for(int i=0;i<cookies.length;i++)
     {
        if(cookies[i].getName().equals(ipString+"perID"))
        {
          perID=cookies[i].getValue();
        }
     }
   }
   if(cookies!=null)
   {
      for(int i=0;i<cookies.length;i++)
      {
         if(cookies[i].getName().equals(ipString+"pwd"))
         {
            pwd=cookies[i].getValue();
         }
      }
   }   
   if(request.getAttribute("inf1")!=null)
   {
   		perID="";
   		pwd="";
   		out.println("<script type='text/javascript'>document.getElementById('perID').focus()</script>");
   }   
 %>


	<body onLoad="load()">
		<div id="login">
			<table class="logintable" align="center">
				<tr>
					<td class="logo">
						Welcome to EPD Budget Controlling Center
					</td>
					<td align="right">
						<img src="images/epd/B.bmp" style="height: 90px;">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="images/epd/title123.jpg"
							style="width: 100%; height: 120px;">
					</td>
				</tr>
				<tr height="8px">
					<td colspan="2"></td>
				</tr>
				<tr>
					<td valign="top">
						<form action="userloginAction.action" method="post"
							>
							<table class="user" align="left">
								<tr>
									<td class="alogin" colspan="3">
										Login
									</td>
								</tr>
								<tr>
									<td colspan="3">
										Please login here:
									</td>
								</tr>
								<tr>
									<td height="40" align="right" width="25%">
										User &nbsp;ID:
									</td>
									<td width="15%">
										<input type="text" size="20" name="perID" id="perID"
											value="<%if(perID!=null)out.println(perID); %>" maxlength="8" />
									</td>
									<td class="formFieldError">
										${FieldErrors.perID[0]}
										<s:if test="#request.inf1!=null">
											<s:property value="#request.inf1"></s:property>
										</s:if>
									</td>
								</tr>
								<tr>
									<td height="20" align="right" width="25%">
										Password:
									</td>
									<td width="15%">
										<input type="password" size="20" name="pwd" id="pwd"
											value="<%if(pwd!=null) out.println(pwd);%>" />
									</td>
									<td class="formFieldError">
										${FieldErrors.pwd[0]}
									</td>
								</tr>
								<tr>
									<td width="25%" height="45">
										&nbsp;
									</td>
									<td colspan=2>
										<input type="submit" value="Login" />
										&nbsp;
										<input type="button" value="Reset" onClick="rset()" />
									</td>
								</tr>
							</table>
						</form>
					</td>
					<td class="notice">
						<table width="100%" height="100%">
							<tr>
								<td>
									<br>
								</td>
							</tr>
							<tr>
								<td>
									<br>
									<a href=""></a>
								</td>
							</tr>
							<tr>
								<td>
									<br>
									<a href="#"></a>
								</td>
							</tr>
							<tr>
								<td>
									<br>
									<a href="#"></a>
								</td>
							</tr>
							<tr>
								<td>
									<br>
									<a href="#"></a>
								</td>
							</tr>
							<tr>
								<td>
									<br>
									<a href="#"></a>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><jsp:include page="../page/footer.jsp"></jsp:include></td>
				</tr>
			</table>
		</div>
	</body>
</html>