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
			req.setAttribute("msg", "你没有登录！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean b = bikeService.findbybid(nowbid);
		if (!b) {
			req.setAttribute("msg", "车辆号码错误！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid != 0) {
			req.setAttribute("msg", "你正在租借的车未归还");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean ispay = bikeService.findbyispay(user.getLoginname());
		if (ispay) {
			req.setAttribute("msg", "上个账单未清算");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int status = bikeService.findbikestatus(tbid);
		if (status != 1) {
			req.setAttribute("msg", "单车的状态为不可租借");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		// 将单车状态变成0已租用
		bikeService.changeBS(tbid, 0);

		bikeService.changeBid(nowbid, user.getLoginname());

		Date date = new Date();// 获得系统时间.
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

		bikeService.insertTime(nowTime, user.getLoginname(), tbid);

		req.setAttribute("msg", "租借成功");
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
			req.setAttribute("msg", "你没有登录！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid != 0) {
			req.setAttribute("msg", "你正在租借的车未归还");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		boolean ispay = bikeService.findbyispay(user.getLoginname());
		if (ispay) {
			req.setAttribute("msg", "上个账单未清算");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		int status = bikeService.findbikestatus(tbid);
		if (status != 1) {
			req.setAttribute("msg", "单车的状态为不可租借");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}

		// 将单车状态变成0已租用
		bikeService.changeBS(tbid, 0);

		bikeService.changeBid(nowbid, user.getLoginname());

		Date date = new Date();// 获得系统时间.
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
			req.setAttribute("msg", "你没有登录！");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		int userBid = bikeService.findbid(user.getLoginname());
		if (userBid == 0) {
			req.setAttribute("msg", "你没有租车");
			req.setAttribute("code", "error");
			return "f:/jsp/msg.jsp";
		}
		
		Map<String, Object> map =bikeService.findrecord(user.getLoginname());
		req.setAttribute("map", map);
		Object oldtime = map.get("btime");
		Date olddate = (Date) oldtime;
		Date date = new Date();// 获得系统时间.		
		
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
		Date date = new Date();// 获得系统时间.		
		
		int time = (int)getSecond(date, olddate);
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		bikeService.insertRTime(time, nowTime,user.getLoginname());
		
		req.setAttribute("msg", "还车成功,请尽快结算账单");
		req.setAttribute("code", "success");
		return "f:/jsp/msg.jsp";
	}
	
	public static long getSecond(Date newDate, Date oldDate) {
		
		long diff = newDate.getTime() - oldDate.getTime();
		long sec = diff / 1000;
		return sec;
	}
	
	// 计算时间差的方法
	public static String getDatePoor(Date newDate, Date oldDate) {
		 
	    long nd = 24 * 60 * 60 * 1000;
	    long nh = 60 * 60 * 1000;
	    long nm = 60 * 1000;
	    long ns = 1000;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = newDate.getTime() - oldDate.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    
	    // 计算差多少秒//输出结果
	    long sec = diff % nd % nh % nm / ns;
	    return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
	}
}
