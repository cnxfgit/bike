package bike.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bike.user.dao.UserDao;
import bike.user.model.User;
import bike.user.service.exception.UserException;

public class UserService {
	private UserDao userDao = new UserDao();

	// 添加评论
	public void addComent(String loginname, String time, String ta) {
		try {
			userDao.addComent(loginname, time, ta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	// 查询评论
	public List<Map<String, Object>> comment() {
		try {
			List<Map<String, Object>> maplist = userDao.findcomment();
			return maplist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	// 修改密码
	public void updatePassword(String loginname, String newpass, String oldpass) throws UserException {
		// 1.校验密码
		try {
			boolean bool = userDao.findByuidpass(loginname, oldpass);
			if (!bool) {
				throw new UserException("原密码错误！");
			}
			// 2.修改密码
			userDao.updatePassword(loginname, newpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// 注册功能
	public void regist(User user) {
		// 向数据库插入
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// 用户名注册校验
	public boolean ajaxLoginname(String loginname) {
		try {
			return userDao.ajaxLoginname(loginname);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	// 用户登录功能
	public User login(User user) {
		try {
			return userDao.findBynamepass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
