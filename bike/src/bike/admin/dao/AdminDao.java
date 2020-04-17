package bike.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	
	QueryRunner qRunner = new TxQueryRunner();
	
	
	
	@Test
	public void test1() throws SQLException {
		String sql = "insert into admin(adminname,adminpass) value(?,?)";
		Object[] object = {"admin","admin"};
		
		qRunner.update(sql,object);
	}

}
