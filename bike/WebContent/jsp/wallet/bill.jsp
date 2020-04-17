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
		<table class="">
			<thead>
				<tr>
					<th>所借单车</th>
					<th>租车时间</th>
					<th>还车时间</th>
					<th>总时长</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach items="${maplist}" var="bike" varStatus="s">
					<tr>
						<td>${maplist.get(s.index).get("bid")}</td>
						<td width="240px">${maplist.get(s.index).get("btime")}</td>
						<td width="240px">${maplist.get(s.index).get("rtime")}</td>
						<td>${maplist.get(s.index).get("time")}</td>
						<td><c:choose>
								<c:when test="${maplist.get(s.index).ispay == 0}">
									<a
										href="${pageContext.request.contextPath}/WalletServelt?method=billbutton">清算</a>
								</c:when>
								<c:otherwise>
									<p>已清</p>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
	</div>
</body>
</html>