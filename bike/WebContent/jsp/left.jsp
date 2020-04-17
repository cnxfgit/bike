<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>left菜单</title>

<base target="body" />

<script type="text/javascript"
	src="../jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="../menu/mymenu.js"></script>
<link rel="stylesheet" href="../menu/mymenu.css"
	type="text/css" media="all">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/left.css">

<script type="text/javascript">
	var bar = new Q6MenuBar("bar", "菜鸟景点自行车租赁");
	$(function() {
		bar.colorStyle = 4;// 指定配色样式
		bar.config.imgDir = "${pageContext.request.contextPath}/menu/img/";// 图片路径
		bar.config.radioButton = false;// 是否支持打开多个菜单

		bar.add("开始租车", "查看所有车辆", "${pageContext.request.contextPath}/BikeServlet?method=showBikeList", "body");
		bar.add("开始租车", "查看可用车辆", "${pageContext.request.contextPath}/BikeServlet?method=showABike", "body");
		bar.add("开始租车", "确认租车", "${pageContext.request.contextPath}/BikeServlet?method=reborrow", "body");
		bar.add("开始租车", "确认还车", "${pageContext.request.contextPath}/BikeServlet?method=rebike", "body");
		bar.add("开始租车", "车辆评论", "${pageContext.request.contextPath}/UserServlet?method=comment", "body");

		bar.add("景点地图", "查看附近的站点", "/bike/html/map.html", "body");
		bar.add("景点地图", "查看当前可用车辆", "/bike/jsp/userLogin.jsp", "body");
		bar.add("景点地图", "寻找最近的车辆(?)", "/bike/jsp/userLogin.jsp", "body");
		
		bar.add("钱包", "余额", "${pageContext.request.contextPath}/WalletServelt?method=showMoney", "body");
		bar.add("钱包", "充值", "${pageContext.request.contextPath}/WalletServelt?method=recharge", "body");
		bar.add("钱包", "账单详情", "${pageContext.request.contextPath}/WalletServelt?method=showbill", "body");
		bar.add("钱包", "优惠券", "", "body");
		
		bar.add("行程", "查看历史行程", "/bike/jsp/userLogin.jsp", "body");

		$("#menu").html(bar.toString());
	});
</script>
</head>
<body>
	<div id="menu"></div>
</body>
</html>