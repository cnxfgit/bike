package bike.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bike.user.dao.UserDao;
import bike.user.model.User;
import bike.user.service.exception.UserException;

public class UserService {
	private UserDao userDao = new UserDao();

	// �������
	public void addComent(String loginname, String time, String ta) {
		try {
			userDao.addComent(loginname, time, ta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	// ��ѯ����
	public List<Map<String, Object>> comment() {
		try {
			List<Map<String, Object>> maplist = userDao.findcomment();
			return maplist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
	}
	
	// �޸�����
	public void updatePassword(String loginname, String newpass, String oldpass) throws UserException {
		// 1.У������
		try {
			boolean bool = userDao.findByuidpass(loginname, oldpass);
			if (!bool) {
				throw new UserException("ԭ�������");
			}
			// 2.�޸�����
			userDao.updatePassword(loginname, newpass);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ע�Ṧ��
	public void regist(User user) {
		// �����ݿ����
		try {
			userDao.add(user);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// �û���ע��У��
	public boolean ajaxLoginname(String loginname) {
		try {
			return userDao.ajaxLoginname(loginname);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
	}

	// �û���¼����
	public User login(User user) {
		try {
			return userDao.findBynamepass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
