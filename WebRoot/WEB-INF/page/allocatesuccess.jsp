<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<meta http-equiv="refresh" content="5;url=ShowEmployeeAction!showAll?pageNow=1"> 
		<link rel="shortcut icon" href="images/epd/favicon.png" />
		<title>Allocate Success</title>
		<link href="css/body.css" style="text/css" rel="stylesheet">
	</head>
	<script language="javascript">
	var times = 6;
	clock();
	function clock() {
		window.setTimeout('clock()', 1000);
		times = times - 1;
		time.innerHTML = times;
	}
</script>
	<style type="text/css">
.STYLE1 {
	color: black
}
</style>
	<body>
		<h1>Congratulations, allocated successfully......</h1>
	</body>
</html>