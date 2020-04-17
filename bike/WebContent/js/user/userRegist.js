$(function() {
	// 得到所有的错误信息，循环遍历他们。用一个方法来表示是否显示他们
	$(".errorClass").each(function() {
		showError($(this));// 遍历每个元素，用每个元素来调用showError
	})

	// 输入框得到光标隐藏错误信息
	$(".inputClass").focus(function() {
		var lableId = $(this).attr("id") + "Error";// 通过输入框找到对应的lable
		$("#" + lableId).text("");// 把lable内容清空
		showError($("#" + lableId));// 隐藏没有信息的lable
	});

	// 输入框失去光标显示隐藏信息
	$(".inputClass").blur(function() {
		var id = $(this).attr("id");// 获取当前输入框的id
		var funName = id + "()";// 方法名为loginname()
		eval(funName);// 调用名为id的函数
	});

});

// 登录名校验方法
function loginname() {
	var id = "loginname";
	var value = $("#" + id).val();// 获取输入框内容
	// 1.非空校验
	if (!value) {
		$("#" + id + "Error").text("用户名不能为空!");
		showError($("#" + id + "Error"));
		return false;
	}
	// 2.长度校验
	if (value.length < 3 || value.length > 16) {
		$("#" + id + "Error").text("用户名必须在3~16位之间!");
		showError($("#" + id + "Error"));
		return false;
	}
	// 3.是否注册校验
	$.ajax({
		url:"/bike/UserServlet",
		data:{method:"ajaxLoginname", loginname:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果不是则不会等服务器返回，就会向下运行		
		cache:false,
		success:function(result){
			if (!result) {//如果校验失败
				$("#" + id + "Error").text("用户名已被注册!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});
	return true;
}

// 登录密码校验方法
function loginpass() {
	var id = "loginpass";
	var value = $("#" + id).val();// 获取输入框内容
	// 1.非空校验
	if (!value) {
		$("#" + id + "Error").text("密码不能为空!");
		showError($("#" + id + "Error"));
		return false;
	}
	// 2.长度校验
	if (value.length < 3 || value.length > 16) {
		$("#" + id + "Error").text("密码必须在3~16位之间!");
		showError($("#" + id + "Error"));
		return false;
	}

	return true;
}

// 确认密码校验方法
function reloginpass() {
	var id = "reloginpass";
	var value = $("#" + id).val();// 获取输入框内容
	// 1.非空校验
	if (!value) {
		$("#" + id + "Error").text("确认密码不能为空!");
		showError($("#" + id + "Error"));
		return false;
	}
	// 2.是否跟第一次输入密码相同
	if (value != $("#loginpass").val()) {
		$("#" + id + "Error").text("两次密码输入不一致!");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}

// 验证码校验方法
function verifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val();// 获取输入框内容
	// 1.非空校验
	if (!value) {
		$("#" + id + "Error").text("验证码不能为空!");
		showError($("#" + id + "Error"));
		return false;
	}
	
	//2.是否校验正确
	$.ajax({
		url:"/bike/UserServlet",
		data:{method:"ajaxVerifyCode", verifyCode:value},//给服务器的参数
		type:"POST",
		dataType:"json",
		async:false,//是否异步请求，如果不是则不会等服务器返回，就会向下运行		
		cache:false,
		success:function(result){
			if (!result) {//如果校验失败
				$("#" + id + "Error").text("验证码错误!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});

	return true;
}

// 判断当前元素是否存在内容，存在则显示不存在则不显示
function showError(ele) {
	var text = ele.text();// 获取元素内容
	if (!text) {// 判断有无内容
		ele.css("display", "none");// 没有,即隐藏
	} else {// 有
		ele.css("display", "");
	}
}

function hyz() {
	$("#img").attr("src", "/bike/VerifyCodeServlet?a=" + new Date().getTime());
}