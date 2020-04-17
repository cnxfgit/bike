<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>comment页面</title>

<link rel="stylesheet" type="text/css"
	href="http://localhost:8080/bike/css/user/comment.css" />
</head>
<body>

	<div class="div1">

		<c:forEach items="${maplist}" var="comment" varStatus="s">

			<div class="conmment_details">
				<span class="comment_name">${maplist.get(s.index).get("loginname")}
				</span>     <span>${maplist.get(s.index).get("time")}</span>
				<div class="comment_content">${maplist.get(s.index).get("comment")}</div>
				<div class="del"></div>
			</div>
			<hr>
		</c:forEach>
	</div>

	<div class="div2">
		<form action="${pageContext.request.contextPath}/UserServlet">
			<input type="hidden" name="method" value="addComment" />
			<p class="s">请输入你的评论</p>
			<div class="">
				<textarea placeholder="请输入内容" class="ta" name="ta"></textarea>
			</div>
			<div class="">
				<button type="submit" class="btn">立即提交</button>
				<button type="reset" class="btn">重置</button>
			</div>
		</form>
	</div>

</body>
</html>