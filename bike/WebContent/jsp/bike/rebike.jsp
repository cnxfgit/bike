<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>还车页面</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/BikeServlet" method="post">
	<input type="hidden" name="method" value="rebikebutton" />
		<table>
			<tr>
				<td>你正在租用的单车id为${map.get("bid")}</td>
				<td>你的使用时长为${time}</td>
			<tr>
		</table>
		<button type="submit">确认还车</button>
	</form>
</body>
</html>