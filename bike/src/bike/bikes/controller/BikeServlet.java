package bike.bikes.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bike.bikes.service.BikeService;
import bike.user.model.User;
import cn.itcast.servlet.BaseServlet;

@WebServlet("/BikeServlet")
public class BikeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private BikeService bikeService = new BikeService();
	
	public String reborrowbutton(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("sessionUser");
		String nowbid = req.getParameter("bid");
		int tbid = Integer.parseInt(nowbid);

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean b = bikeService.findbybid(nowbid);
		if (!b) {
			req.setAttribute("msg", "�����������");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid != 0) {
			req.setAttribute("msg", "���������ĳ�δ�黹");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean ispay = bikeService.findbyispay(user.getLoginname());
		if (ispay) {
			req.setAttribute("msg", "�ϸ��˵�δ����");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int status = bikeService.findbikestatus(tbid);
		if (status != 1) {
			req.setAttribute("msg", "������״̬Ϊ�������");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		// ������״̬���0������
		bikeService.changeBS(tbid, 0);

		bikeService.changeBid(nowbid, user.getLoginname());

		Date date = new Date();// ���ϵͳʱ��.
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		bikeService.insertTime(nowTime, user.getLoginname(), tbid);

		req.setAttribute("msg", "���ɹ�");
		req.setAttribute("code", "success");
		return "f:/jsp/msg.jsp";
	}
	
	public String reborrow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "f:/jsp/bike/reborrow.jsp";
	}

	public String showBikeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		bikeService.findBike();
		List<Map<String, Object>> maplist = bikeService.findBike();

		req.setAttribute("maplist", maplist);
		return "f:/jsp/bike/bikeList.jsp";

	}

	public String showABike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		bikeService.findBike();
		List<Map<String, Object>> maplist = bikeService.findABike();

		req.setAttribute("maplist", maplist);
		return "f:/jsp/bike/bikeList.jsp";

	}

	public String borrow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		User user = (User) req.getSession().getAttribute("sessionUser");
		String nowbid = req.getParameter("bid");
		int tbid = Integer.parseInt(nowbid);

		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid != 0) {
			req.setAttribute("msg", "���������ĳ�δ�黹");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean ispay = bikeService.findbyispay(user.getLoginname());
		if (ispay) {
			req.setAttribute("msg", "�ϸ��˵�δ����");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int status = bikeService.findbikestatus(tbid);
		if (status != 1) {
			req.setAttribute("msg", "������״̬Ϊ�������");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		// ������״̬���0������
		bikeService.changeBS(tbid, 0);

		bikeService.changeBid(nowbid, user.getLoginname());

		Date date = new Date();// ���ϵͳʱ��.
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		bikeService.insertTime(nowTime, user.getLoginname(), tbid);

		bikeService.findBike();
		List<Map<String, Object>> maplist = bikeService.findBike();
		req.setAttribute("maplist", maplist);
		return "f:/jsp/bike/bikeList.jsp";
	}
	
	public String rebike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ParseException {

		User user = (User) req.getSession().getAttribute("sessionUser");
		if (user == null) {
			req.setAttribute("msg", "��û�е�¼��");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid == 0) {
			req.setAttribute("msg", "��û���⳵");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		Map<String, Object> map =bikeService.findrecord(user.getLoginname());
		req.setAttribute("map", map);
		Object oldtime = map.get("btime");
		Date olddate = (Date) oldtime;
		Date date = new Date();// ���ϵͳʱ��.		
		
		String time = getDatePoor(date, olddate);
		req.setAttribute("time", time);

		return "f:/jsp/bike/rebike.jsp";

	}
	
	public String rebikebutton(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		User user = (User) req.getSession().getAttribute("sessionUser");
		int userBid = bikeService.findbid(user.getLoginname());
		
		bikeService.changeBS(userBid, 1);

		bikeService.changeBid("0", user.getLoginname());
		
		Map<String, Object> map =bikeService.findrecord(user.getLoginname());
		req.setAttribute("map", map);
		Object oldtime = map.get("btime");
		Date olddate = (Date) oldtime;
		Date date = new Date();// ���ϵͳʱ��.		
		
		int time = (int)getSecond(date, olddate);
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		bikeService.insertRTime(time, nowTime,user.getLoginname());
		
		req.setAttribute("msg", "�����ɹ�,�뾡������˵�");
		req.setAttribute("code", "success");
		return "f:/jsp/msg.jsp";
	}
	
	public static long getSecond(Date newDate, Date oldDate) {
		
		long diff = newDate.getTime() - oldDate.getTime();
		long sec = diff / 1000;
		return sec;
	}
	
	// ����ʱ���ķ���
	public static String getDatePoor(Date newDate, Date oldDate) {
		 
	    long nd = 24 * 60 * 60 * 1000;
	    long nh = 60 * 60 * 1000;
	    long nm = 60 * 1000;
	    long ns = 1000;
	    // long ns = 1000;
	    // �������ʱ��ĺ���ʱ�����
	    long diff = newDate.getTime() - oldDate.getTime();
	    // ����������
	    long day = diff / nd;
	    // ��������Сʱ
	    long hour = diff % nd / nh;
	    // �������ٷ���
	    long min = diff % nd % nh / nm;
	    
	    // ����������//������
	    long sec = diff % nd % nh % nm / ns;
	    return day + "��" + hour + "Сʱ" + min + "����" + sec + "��";
	}
}
