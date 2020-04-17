package bike.AndroidServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.google.gson.Gson;

import bike.user.model.User;
import bike.user.service.UserService;
import bike.wallet.walletservce.WalletService;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/AndroidUserServlet")
public class AndroidUserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
    private WalletService walletService = new WalletService();
	
    // 安卓查询评论
    public void findComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	List<Map<String, Object>> maplist = userService.comment();
    	List<Comment> list = new ArrayList<Comment>();
    	for (Map<String, Object> map : maplist) {
			Comment comment = new Comment();
			String loginname = (String)map.get("loginname");
			Date time = (Date)map.get("time");;
			String time1 = time.toString();
			String comment1 = (String)map.get("comment");;
			
			comment.setLoginname(loginname);
			comment.setTime(time1);
			comment.setComment(comment1);
			list.add(comment);
		}
    	
    	Gson gson = new Gson();
    	String js = gson.toJson(list);
    	
    	PrintWriter writer = resp.getWriter();
		writer.print(js);
		writer.flush();
		writer.close();//注意刷新和关闭缓存
    }
    
	// 安卓查询余额
	public void findmoney(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("接收到客户端的访问：");
		String loginname = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
        System.out.println(loginname);
        
        int money = walletService.findbikestatus(loginname);
        String mon = Integer.toString(money) ;
        
        PrintWriter writer = resp.getWriter();
		writer.print(mon);
		writer.flush();
		writer.close();//注意刷新和关闭缓存
	}
	
	// 安卓登录的方法
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("接收到客户端的访问：");
		String loginname = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
        System.out.println(loginname);
        String loginpass = req.getParameter("loginpass");
        System.out.println(loginpass);
        
        User userForm = new User();
        userForm.setLoginname(loginname);
		userForm.setLoginpass(loginpass);
		// 使用service查询，得到user
		User user = userService.login(userForm);
		
		if (user == null) {
			PrintWriter writer = resp.getWriter();
			writer.write("用户名或密码错误");//这里可以向客户端返回相应的字符串,客户端可用String res=response.body().string();来接收该值
			writer.flush();
			writer.close();//注意刷新和关闭缓存
		} else {
			PrintWriter writer = resp.getWriter();
			writer.print(userForm);
			writer.flush();
			writer.close();//注意刷新和关闭缓存
		}
	}
}
