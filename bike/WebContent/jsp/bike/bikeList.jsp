<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>租车页面</title>
</head>
<body>
	<div class="">
		<p>单车状态1可租用0已被租用2损坏3正在维修</p>
		<table class="">
			<thead>
				<tr>
					<th>Bid</th>
					<th>单车昵称</th>
					<th>单车状态</th>
					<th>所在站点</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${maplist}" var="bike" varStatus="s">
					<tr>
						<td>${maplist.get(s.index).get("bid")}</td>
						<td>${maplist.get(s.index).get("bikename")}</td>
						<td>${maplist.get(s.index).get("status")}</td>
						<td>${maplist.get(s.index).get("zhan")}</td>
						<td><a
							href="${pageContext.request.contextPath}/BikeServlet?method=borrow&bid=${maplist.get(s.index).get("bid")}">租借</a></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>
</body>
</html>