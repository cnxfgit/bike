<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>充值页面</title>
</head>
<body>
	<table>
		<form action="${pageContext.request.contextPath}/WalletServelt" method="post">
			<input type="hidden" name="method" value="rechargebutton" />
			<table>
				<tr>
					<td>你现在的的余额为${money}</td>
				<tr>
				<tr>
					<td><p>请输入充值金额：</p></td>				
				</tr>
				<tr>
					<td><input type="number" name="recharge" min="0" max="9999" required="required" /><td>
				</tr>
			</table>
			<button type="submit">确认充值</button>
	</table>
</body>
</html>