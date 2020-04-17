package bike.wallet.walletdao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class WalletDao {
	private QueryRunner qRunner = new TxQueryRunner();
	
	public int findmoney(String loginname) throws SQLException {
		String sql= "select * from user where loginname='"+loginname+"'";
		Map<String, Object> map = qRunner.query(sql,new MapHandler());
		return (int)map.get("money");
	}
	
	public void setmoney(String loginname, int fmoney) throws SQLException {
		String sql= "update user set money=? where loginname='"+loginname+"'";
		Object[] object = {fmoney};
		qRunner.update(sql, object);
	}
	
	public void changeispay(String loginname, int ispay) throws SQLException {
		String sql= "update record set ispay=? where loginname='"+loginname+"' and ispay=0";
		Object[] object = {ispay};
		qRunner.update(sql, object);
	}
	
	public List<Map<String, Object>> findrecord(String loginname) throws SQLException {
		String sql = "select * from record where loginname='"+loginname+"' order by rtime desc";
		List<Map<String, Object>> maplist = qRunner.query(sql, new MapListHandler());
		return maplist;
	}
	
	public void recharge(String loginname,int money) throws SQLException {
		String sql = "update user set money=? where loginname='"+loginname+"'";
		Object[] objects = {money};
		qRunner.update(sql, objects);
	}
}
