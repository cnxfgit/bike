<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>确人租车页面</title>
</head>
<body>
	<table>
		<form action="${pageContext.request.contextPath}/BikeServlet" method="post">
			<input type="hidden" name="method" value="reborrowbutton" />
			<p>请输入要租用的单车号码</p>
			<input type="number" name="bid" min="0" max="9999" required="required" />
			<button type="submit">确认租车</button>
		</form>
	</table>
</body>
</html>