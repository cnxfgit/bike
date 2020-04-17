<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>用户登录界面</title>

<link rel="stylesheet" type="text/css"
	href="../../css/user/userLogin.css" />
<script type="text/javascript"
	src="../../jquery/jquery-1.5.1.js"></script>
<script src="../../js/user/userLogin.js" type="text/javascript"></script>
</head>
<body>
	<div class="main">
		<div class="top">
			<div class="header">
				<a class="logo" href="../../jsp/index.jsp"><img id="bird" class="bird"
					src="../../images/菜鸟.jpeg"></a>
			</div>
			<h1>菜鸟景点自行车登录页面</h1>
		</div>
		<div class="tableDiv">
			<form action="${pageContext.request.contextPath}/UserServlet"
				method="post">
				<input type="hidden" name="method" value="login" />
				<table id="table">
					<tr>
						<td>用户名:</td>
						<td><input class="input" type="text" name="loginname" id="loginname"/></td>
						<td>
							<label class="errorClass" id="loginnameError">${errors.loginname}</label>
						</td>
					</tr>
					<tr>
						<td>密码:</td>
						<td><input class="input" type="password" name="loginpass" id="loginpass"/></td>
						<td>
							<label class="errorClass" id="loginpassError">${errors.loginpass}</label>
						</td>
					</tr>
					<tr>
						<td>验证码:</td>
						<td><input class="input" type="text" name="verifyCode" id="verifyCode"/></td>
						<td>
							<label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div id="divVf">
								<img id="img"
									src="${pageContext.request.contextPath}/VerifyCodeServlet" />
							</div>
						</td>
						<td><label><a href="javascript:hyz()">换一张!</a></label></td>
					</tr>
					<tr>
						<td></td>
						<td align="center"><input type="submit" value="确认登录">
						</td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>