<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟景点自行车主页</title>

<link rel="stylesheet" type="text/css"
	href="../css/index.css">
</head>
 <body background="../images/蓝天白云.jpg">

<table class="table" align="center">
	<tr class="trTop">
		<td colspan="2" class="tdTop">
			<iframe frameborder="0" src="<c:url value='/jsp/top.jsp'/>" name="top"></iframe>
		</td>
	</tr>
	<tr>
		<td class="tdLeft" rowspan="2">
			<iframe frameborder="0" src="<c:url value='/jsp/left.jsp'/>" name="left"></iframe>
		</td>
	</tr>
	<tr>
		<td class="body" style="border-top-width: 0px;">
			<iframe frameborder="0" src="<c:url value='/jsp/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>
</body>
</html>