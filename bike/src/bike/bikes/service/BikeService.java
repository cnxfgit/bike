package bike.bikes.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bike.bikes.dao.BikeDao;

public class BikeService {
	private BikeDao bikeDao = new BikeDao();
	
	public boolean findbyispay(String loginname) {
		try {
			return bikeDao.findispay(loginname);
		} catch (SQLException e) {
			
			throw new RuntimeException();
		}
	}
	
	public boolean findbybid(String bid) {
		try {
			return bikeDao.findBybid(bid);
		} catch (SQLException e) {
			
			throw new RuntimeException();
		}
	}
	
	public List<Map<String, Object>> findBike() {
		try {
			List<Map<String, Object>> maplist = bikeDao.findBike();
			return maplist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}
	
	public List<Map<String, Object>> findABike() {
		try {
			List<Map<String, Object>> maplist = bikeDao.findABike();
			return maplist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		}
		
	}
	
	public int findbid(String loginname) {
		try {
			int bidString = bikeDao.findbid(loginname);
			return bidString;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void insertTime(String time,String loginname,int bid) {
		try {
			bikeDao.insertTime(loginname, time,bid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void changeBid(String bid,String loginname) {
		try {
			bikeDao.insertBid(bid,loginname);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void changeBS(int bid,int status) {
		try {
			bikeDao.changeBS(bid, status);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int findbikestatus(int bid) {
		try {
			return bikeDao.findbikestatus(bid);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public Map<String, Object> findrecord(String loginname) {
		try {
			Map<String, Object> map =bikeDao.findrecord(loginname);
			return map;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void insertRTime(int time,String rtime,String loginname) {
		try {
			bikeDao.insertRTime(time, rtime,loginname);;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}	
