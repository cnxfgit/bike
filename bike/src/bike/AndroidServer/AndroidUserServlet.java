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
	
    // ��׿��ѯ����
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
		writer.close();//ע��ˢ�º͹رջ���
    }
    
	// ��׿��ѯ���
	public void findmoney(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("���յ��ͻ��˵ķ��ʣ�");
		String loginname = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
        System.out.println(loginname);
        
        int money = walletService.findbikestatus(loginname);
        String mon = Integer.toString(money) ;
        
        PrintWriter writer = resp.getWriter();
		writer.print(mon);
		writer.flush();
		writer.close();//ע��ˢ�º͹رջ���
	}
	
	// ��׿��¼�ķ���
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("���յ��ͻ��˵ķ��ʣ�");
		String loginname = new String(req.getParameter("loginname").getBytes("iso-8859-1"), "utf-8");
        System.out.println(loginname);
        String loginpass = req.getParameter("loginpass");
        System.out.println(loginpass);
        
        User userForm = new User();
        userForm.setLoginname(loginname);
		userForm.setLoginpass(loginpass);
		// ʹ��service��ѯ���õ�user
		User user = userService.login(userForm);
		
		if (user == null) {
			PrintWriter writer = resp.getWriter();
			writer.write("�û������������");//���������ͻ��˷�����Ӧ���ַ���,�ͻ��˿���String res=response.body().string();�����ո�ֵ
			writer.flush();
			writer.close();//ע��ˢ�º͹رջ���
		} else {
			PrintWriter writer = resp.getWriter();
			writer.print(userForm);
			writer.flush();
			writer.close();//ע��ˢ�º͹رջ���
		}
	}
}
