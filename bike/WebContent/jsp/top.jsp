<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>top网页头</title>

<link rel="stylesheet" type="text/css"
	href="../css/top.css">
</head>
<body>
	<h2 style="text-align: center;">菜鸟景点自行车租赁系统</h2>
	<div style="font-size: 10pt; line-height: 10px;">

		<%-- 根据用户是否登录，显示不同的链接 --%>
		<c:choose>
			<c:when test="${empty sessionScope.sessionUser }">
				<a href="<c:url value='/jsp/user/userLogin.jsp'/>" target="_parent">菜鸟会员登录</a> |&nbsp; 
		  <a href="<c:url value='/jsp/user/userRegist.jsp'/>" target="_parent">注册菜鸟会员</a>
			</c:when>
			<c:otherwise>
		      <p>菜鸟会员：</p>${sessionScope.sessionUser.loginname}&nbsp;&nbsp;|&nbsp;&nbsp;

		  <a href="<c:url value='/jsp/user/psd.html'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>