package bike.user.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bike.user.model.User;
import bike.user.service.UserService;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	
	public String addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String string = req.getParameter("ta");
		User user = (User) req.getSession().getAttribute("sessionUser");
		Date date = new Date();//获得系统时间. 
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		if (user == null) {
			req.setAttribute("msg", "你没有登录！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		try {
			userService.addComent(user.getLoginname(), nowTime, string);
			List<Map<String, Object>> maplist = userService.comment();
			
			req.setAttribute("maplist", maplist);
			return "f:/jsp/user/comment.jsp";
		} catch (Exception e) {
			req.setAttribute("msg", "添加失败");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
	}

	public String comment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, Object>> maplist = userService.comment();

		req.setAttribute("maplist", maplist);
		return "f:/jsp/user/comment.jsp";
	}

	// 修改密码的方法
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.封装表单到
		// 2.从session中获取uid
		// 3.使用uid和新密码旧密码来调用service中的方法
		// 4.保存成功信息到request中
		// 5.转发到msg.jsp

		User formUser = new User();

		formUser.setLoginpass(req.getParameter("oldloginpass"));
		formUser.setLoginname(req.getParameter("newloginpass"));
		formUser.setReloginpass(req.getParameter("reloginpass"));
		User user = (User) req.getSession().getAttribute("sessionUser");

		if (user == null) {
			req.setAttribute("msg", "你没有登录！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		try {
			userService.updatePassword(user.getLoginname(), formUser.getLoginname(), formUser.getLoginpass());
			req.setAttribute("code", "success");
			req.setAttribute("msg", "修改成功");
			req.getSession().invalidate();
			return "f:/jsp/msg.jsp";
		} catch (Exception e) {
			req.setAttribute("msg", "修改失败");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

	}

	// 登录方法
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User userForm = new User();
		// 封装信息到userForm
		String str = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
		userForm.setLoginname(str);
		userForm.setLoginpass(req.getParameter("loginpass"));
		userForm.setVerifyCode(req.getParameter("verifyCode"));

		// 校验,如果校验失败，保存校验信息显示在注册页面
		Map<String, String> errors = validateLogin(userForm, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", errors);
			return "f:/jsp/msg.jsp";
		}

		// 3.使用service查询，得到user
		User user = userService.login(userForm);
		// 4.查看用户是否存在，如果不存在保存错误信息，保存状态（传回旧信息login.jsp）
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		} else {
			// 保存用户到session
			req.getSession().setAttribute("sessionUser", user);
			// 获取用户名保存到cookie中
			String loginname = user.getLoginname();
			loginname = URLEncoder.encode(loginname, "utf-8");
			Cookie cookie = new Cookie("loginname", loginname);
			cookie.setMaxAge(60 * 60 * 24 * 10);// 保存10天
			resp.addCookie(cookie);
			return "r:/jsp/index.jsp";// 重定向到主页
		}
	}

	// 登录校验
	// 对表单的数据进行逐个校验
	private Map<String, String> validateLogin(User userFrom, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		// 校验登录名
		String loginname = userFrom.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空!");
		} else if (loginname.length() < 3 || loginname.length() > 16) {
			errors.put("loginname", "用户名长度在3~16位之间!");
		}

		// 校验登录密码
		String loginpass = userFrom.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空!");
		} else if (loginpass.length() < 3 || loginpass.length() > 16) {
			errors.put("loginpass", "密码长度在3~16位之间!");
		}

		// 校验验证码
		String verifyCode = userFrom.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空!");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误!");
		}

		return errors;
	}

	// 退出登录的方法
	public String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/jsp/user/userLogin.jsp";
	}

	// ajax用户名是否注册
	public String ajaxLoginname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取参数
		String loginname = req.getParameter("loginname");
		// 得到校验结果
		boolean b = userService.ajaxLoginname(loginname);
		// 发送给客户端
		resp.getWriter().print(b);
		return null;
	}

	// ajax验证码是否正确
	public String ajaxVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 获取输入框校验码参数
		String verifyCode = req.getParameter("verifyCode");
		// 获取真实校验码
		String vCode = (String) req.getSession().getAttribute("vCode");
		// 进行忽略大小写比较，得到校验结果
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		// 返回结果
		resp.getWriter().print(b);
		return null;
	}

	// 注册方法
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User userForm = new User();

		String str = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
		userForm.setLoginname(str);
		userForm.setLoginpass(req.getParameter("loginpass"));
		userForm.setReloginpass(req.getParameter("reloginpass"));
		userForm.setVerifyCode(req.getParameter("verifyCode"));

		// 校验,如果校验失败，保存校验信息显示在注册页面
		Map<String, String> errors = validateRegist(userForm, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", errors);
			return "f:/jsp/msg.jsp";
		}

		// 调用userService添加用户
		userService.regist(userForm);

		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请登录");
		return "f:/jsp/msg.jsp";
	}

	// 注册校验
	// 对表单的数据进行逐个校验
	private Map<String, String> validateRegist(User userFrom, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		// 校验登录名
		String loginname = userFrom.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空!");
		} else if (loginname.length() < 3 || loginname.length() > 16) {
			errors.put("loginname", "用户名长度在3~16位之间!");
		} else if (!userService.ajaxLoginname(loginname)) {
			errors.put("loginname", "用户名被注册!");
		}

		// 校验登录密码
		String loginpass = userFrom.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空!");
		} else if (loginpass.length() < 3 || loginpass.length() > 16) {
			errors.put("loginpass", "密码长度在3~16位之间!");
		}
		// 校验确认登录密码
		String reloginpass = userFrom.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空!");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次密码不一致!");
		}

		// 校验验证码
		String verifyCode = userFrom.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空!");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误!");
		}

		return errors;
	}

}
