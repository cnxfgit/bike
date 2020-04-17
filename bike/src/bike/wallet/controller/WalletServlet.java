package bike.wallet.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bike.bikes.service.BikeService;
import bike.user.model.User;
import bike.wallet.walletservce.WalletService;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/WalletServelt")
public class WalletServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private WalletService walletService = new WalletService();
	private BikeService bikeService = new BikeService();
	
	// ��ֵ��ť
	public String rechargebutton(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");

		int money = walletService.findbikestatus(user.getLoginname());
		
		String remoney = req.getParameter("recharge");
		int i = Integer.parseInt(remoney);
		money = money+i;
		walletService.recharge(user.getLoginname(), money);
		
		req.setAttribute("msg", "��ֵ�ɹ�");
		req.setAttribute("code", "success");
		return "f:/jsp/msg.jsp";
	}
	
	// ��ֵҳ��
	public String recharge(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		int money = walletService.findbikestatus(user.getLoginname());

		req.setAttribute("money", money);
		
		return "f:/jsp/wallet/recharge.jsp";
	}
	
	// �˵���ť
	public String billbutton(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		List<Map<String, Object>> maplist =  walletService.findrecord(user.getLoginname());
		
		int i = (int)maplist.get(0).get("ispay");
		if (i==1) {
			req.setAttribute("msg", "��û����Ҫ������˵���");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		int time = (int)maplist.get(0).get("time");
		int price = balance(time);
		
		int money = walletService.findbikestatus(user.getLoginname());
		if (money<price) {
			req.setAttribute("msg", "������㣡");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		int fmoney = money - price;
		walletService.setmoney(user.getLoginname(), fmoney);
		walletService.changeispay(user.getLoginname(), 1);
		
		req.setAttribute("msg", "����ɹ�");
		req.setAttribute("code", "success");
		return "f:/jsp/msg.jsp";
	}

	// ��ʾ�˵�
	public String showbill(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid != 0) {
			req.setAttribute("msg", "�������⳵���뻹�����ٲ鿴��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		List<Map<String, Object>> maplist =  walletService.findrecord(user.getLoginname());
		
		for (Map<String, Object> map : maplist) {
			int i = (int)map.get("time");
			String string = date(i);
			map.put("time", string); 
			
		}
		
		req.setAttribute("maplist", maplist);
		return "f:/jsp/wallet/bill.jsp";
	}

	// ��ʾ���
	public String showMoney(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = (User) req.getSession().getAttribute("sessionUser");

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		int money = walletService.findbikestatus(user.getLoginname());

		req.setAttribute("money", money);
		return "f:/jsp/wallet/money.jsp";
	}
	
	public static String date(int i) {
		int nd = 24 * 60 * 60;
	    int nh = 60 * 60;
	    int nm = 60;
	    int day = i / nd;
	    // ��������Сʱ
	    int hour = i % nd / nh;
	    // �������ٷ���
	    int min = i % nd % nh / nm;
	    
	    // ����������//������
	    int sec = i % nd % nh % nm;
		return day + "��" + hour + "Сʱ" + min + "����" + sec + "��";
	}
	
	public static int balance(int time) {
		int nd = 24 * 60 * 60;
	    int nh = 60 * 60;
	    int nm = 60;
		
		if (time<=(15*nm)) {
			return 1;
		}
		if (time<=(nh)) {
			return 2;
		}
		if (nh<time&&time<=(2*nh)) {
			return 3;
		}
		if (time>(2*nh)) {
			return (time/nh)*1+2;
		}

		return 0;
	}
	
	
}
