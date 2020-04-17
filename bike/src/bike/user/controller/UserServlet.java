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
		Date date = new Date();//���ϵͳʱ��. 
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		try {
			userService.addComent(user.getLoginname(), nowTime, string);
			List<Map<String, Object>> maplist = userService.comment();
			
			req.setAttribute("maplist", maplist);
			return "f:/jsp/user/comment.jsp";
		} catch (Exception e) {
			req.setAttribute("msg", "���ʧ��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
	}

	public String comment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Map<String, Object>> maplist = userService.comment();

		req.setAttribute("maplist", maplist);
		return "f:/jsp/user/comment.jsp";
	}

	// �޸�����ķ���
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1.��װ����
		// 2.��session�л�ȡuid
		// 3.ʹ��uid�������������������service�еķ���
		// 4.����ɹ���Ϣ��request��
		// 5.ת����msg.jsp

		User formUser = new User();

		formUser.setLoginpass(req.getParameter("oldloginpass"));
		formUser.setLoginname(req.getParameter("newloginpass"));
		formUser.setReloginpass(req.getParameter("reloginpass"));
		User user = (User) req.getSession().getAttribute("sessionUser");

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		try {
			userService.updatePassword(user.getLoginname(), formUser.getLoginname(), formUser.getLoginpass());
			req.setAttribute("code", "success");
			req.setAttribute("msg", "�޸ĳɹ�");
			req.getSession().invalidate();
			return "f:/jsp/msg.jsp";
		} catch (Exception e) {
			req.setAttribute("msg", "�޸�ʧ��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

	}

	// ��¼����
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User userForm = new User();
		// ��װ��Ϣ��userForm
		String str = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
		userForm.setLoginname(str);
		userForm.setLoginpass(req.getParameter("loginpass"));
		userForm.setVerifyCode(req.getParameter("verifyCode"));

		// У��,���У��ʧ�ܣ�����У����Ϣ��ʾ��ע��ҳ��
		Map<String, String> errors = validateLogin(userForm, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", errors);
			return "f:/jsp/msg.jsp";
		}

		// 3.ʹ��service��ѯ���õ�user
		User user = userService.login(userForm);
		// 4.�鿴�û��Ƿ���ڣ���������ڱ��������Ϣ������״̬�����ؾ���Ϣlogin.jsp��
		if (user == null) {
			req.setAttribute("msg", "�û������������");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		} else {
			// �����û���session
			req.getSession().setAttribute("sessionUser", user);
			// ��ȡ�û������浽cookie��
			String loginname = user.getLoginname();
			loginname = URLEncoder.encode(loginname, "utf-8");
			Cookie cookie = new Cookie("loginname", loginname);
			cookie.setMaxAge(60 * 60 * 24 * 10);// ����10��
			resp.addCookie(cookie);
			return "r:/jsp/index.jsp";// �ض�����ҳ
		}
	}

	// ��¼У��
	// �Ա������ݽ������У��
	private Map<String, String> validateLogin(User userFrom, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		// У���¼��
		String loginname = userFrom.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "�û�������Ϊ��!");
		} else if (loginname.length() < 3 || loginname.length() > 16) {
			errors.put("loginname", "�û���������3~16λ֮��!");
		}

		// У���¼����
		String loginpass = userFrom.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "���벻��Ϊ��!");
		} else if (loginpass.length() < 3 || loginpass.length() > 16) {
			errors.put("loginpass", "���볤����3~16λ֮��!");
		}

		// У����֤��
		String verifyCode = userFrom.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "��֤�벻��Ϊ��!");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "��֤�����!");
		}

		return errors;
	}

	// �˳���¼�ķ���
	public String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/jsp/user/userLogin.jsp";
	}

	// ajax�û����Ƿ�ע��
	public String ajaxLoginname(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ��ȡ����
		String loginname = req.getParameter("loginname");
		// �õ�У����
		boolean b = userService.ajaxLoginname(loginname);
		// ���͸��ͻ���
		resp.getWriter().print(b);
		return null;
	}

	// ajax��֤���Ƿ���ȷ
	public String ajaxVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ��ȡ�����У�������
		String verifyCode = req.getParameter("verifyCode");
		// ��ȡ��ʵУ����
		String vCode = (String) req.getSession().getAttribute("vCode");
		// ���к��Դ�Сд�Ƚϣ��õ�У����
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		// ���ؽ��
		resp.getWriter().print(b);
		return null;
	}

	// ע�᷽��
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User userForm = new User();

		String str = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
		userForm.setLoginname(str);
		userForm.setLoginpass(req.getParameter("loginpass"));
		userForm.setReloginpass(req.getParameter("reloginpass"));
		userForm.setVerifyCode(req.getParameter("verifyCode"));

		// У��,���У��ʧ�ܣ�����У����Ϣ��ʾ��ע��ҳ��
		Map<String, String> errors = validateRegist(userForm, req.getSession());
		if (errors.size() > 0) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", errors);
			return "f:/jsp/msg.jsp";
		}

		// ����userService����û�
		userService.regist(userForm);

		req.setAttribute("code", "success");
		req.setAttribute("msg", "ע��ɹ������¼");
		return "f:/jsp/msg.jsp";
	}

	// ע��У��
	// �Ա������ݽ������У��
	private Map<String, String> validateRegist(User userFrom, HttpSession session) {
		Map<String, String> errors = new HashMap<String, String>();
		// У���¼��
		String loginname = userFrom.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "�û�������Ϊ��!");
		} else if (loginname.length() < 3 || loginname.length() > 16) {
			errors.put("loginname", "�û���������3~16λ֮��!");
		} else if (!userService.ajaxLoginname(loginname)) {
			errors.put("loginname", "�û�����ע��!");
		}

		// У���¼����
		String loginpass = userFrom.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "���벻��Ϊ��!");
		} else if (loginpass.length() < 3 || loginpass.length() > 16) {
			errors.put("loginpass", "���볤����3~16λ֮��!");
		}
		// У��ȷ�ϵ�¼����
		String reloginpass = userFrom.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "ȷ�����벻��Ϊ��!");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "�������벻һ��!");
		}

		// У����֤��
		String verifyCode = userFrom.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "��֤�벻��Ϊ��!");
		} else if (!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "��֤�����!");
		}

		return errors;
	}

}
