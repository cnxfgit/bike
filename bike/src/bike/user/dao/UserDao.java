package bike.user.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bike.user.model.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qRunner = new TxQueryRunner();

	// 添加评论
	public void addComent(String loginname, String time, String ta) throws SQLException {
		String sql = "insert into comment(loginname,time,comment) value(?,?,?)";
		Object[] params = { loginname, time, ta };
		qRunner.update(sql, params);
	}

	// 查询整个评论表
	public List<Map<String, Object>> findcomment() throws SQLException {
		String sql = "select * from comment ORDER BY time DESC";
		List<Map<String, Object>> maplist = qRunner.query(sql, new MapListHandler());
		return maplist;
	}

	// 按loginname和password修改密码
	public void updatePassword(String loginname, String password) throws SQLException {
		String sql = "update user set loginpass=? where loginname=?";
		qRunner.update(sql, password, loginname);
	}

	// 按loginname和password查询
	public boolean findByuidpass(String loginname, String password) throws SQLException {
		String sql = "select count(*) from user where loginname=? and loginpass=?";
		Number number = (Number) qRunner.query(sql, new ScalarHandler(), loginname, password);
		return number.intValue() > 0;
	}

	// 添加用户
	public void add(User user) throws SQLException {
		String sql = "insert into user(loginname, loginpass) value(?, ?)";
		Object[] params = { user.getLoginname(), user.getLoginpass() };
		qRunner.update(sql, params);
	}

	// 检验用户是否注册
	public boolean ajaxLoginname(String loginname) throws SQLException {
		String sqlString = "select count(1) from user where loginname=?";
		Number number = (Number) qRunner.query(sqlString, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}

	// 按用户名和密码查询
	public User findBynamepass(String loginname, String loginpass) throws SQLException {
		String sql = "select * from user where loginname=? and loginpass=?";
		return qRunner.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}

}
