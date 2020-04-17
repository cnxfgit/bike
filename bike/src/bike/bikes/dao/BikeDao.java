package bike.bikes.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;

public class BikeDao {
	private QueryRunner qRunner = new TxQueryRunner();
	
	public boolean findBybid(String bid) throws SQLException {
		String sql = "select count(*) from bike where bid=?";
		Number number = (Number)qRunner.query(sql, new ScalarHandler(), bid);
		return number.intValue()>0;
	}
	
	public List<Map<String, Object>> findBike() throws SQLException {
		String sql = "select * from bike";
		List<Map<String, Object>> maplist = qRunner.query(sql, new MapListHandler());
		return maplist;
	}
	
	public List<Map<String, Object>> findABike() throws SQLException {
		String sql = "select * from bike where status=1";
		List<Map<String, Object>> maplist = qRunner.query(sql, new MapListHandler());
		return maplist;
	}
	
	public int findbid(String loginname) throws SQLException {
		String sql= "select * from user where loginname='"+loginname+"'";
		Map<String, Object> map = qRunner.query(sql,new MapHandler());
		return (int) map.get("bid");
	}
	
	public void insertTime(String loginname,String time,int bid) throws SQLException {
		String sql = "insert into record(loginname,btime,bid) value(?,?,?)";
		Object[] objects = {loginname,time,bid};
		qRunner.update(sql,objects);
	}
	
	public void insertBid(String bid,String loginname) throws SQLException {
		String sql = "update user set bid=? where loginname='"+loginname+"'";
		Object[] objects = {bid};
		qRunner.update(sql,objects);
	}
	
	public void changeBS(int bid,int status) throws SQLException {
		String sql = "update bike set status=? where bid="+bid+"";
		Object[] objects = {status};
		qRunner.update(sql,objects);
	}
	
	public int findbikestatus(int bid) throws SQLException {
		String sql= "select * from bike where bid="+bid+"";
		Map<String, Object> map = qRunner.query(sql,new MapHandler());
		return (int)map.get("status");
	}
	
	public Map<String, Object> findrecord(String loginname) throws SQLException {
		String sql= "select * from record where loginname='"+loginname+"' and rtime is null";
		Map<String, Object> map = qRunner.query(sql,new MapHandler());
		return map;
	}
	
	public void insertRTime(int time,String rtime,String loginname) throws SQLException {
		String sql = "update record set time=?,rtime=? where loginname=? and ispay=0";
		Object[] objects = {time,rtime,loginname};
		qRunner.update(sql,objects);
	}
	
	public boolean findispay(String loginname) throws SQLException {
		String sql = "select count(*) from record where loginname=? and ispay=0";
		Number number = (Number)qRunner.query(sql, new ScalarHandler(), loginname);
		return number.intValue()>0;	
	}
	
}
