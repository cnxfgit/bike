package bike.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest1 {
	private static DataSource dSource;

	static {
		try {
			Properties properties = new Properties();
			properties.load(DruidTest1.class.getClassLoader().getResourceAsStream("druid.properties"));

			dSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static Connection getConnection() throws SQLException {
		return dSource.getConnection();
	}

	public static void close(ResultSet rs, Statement stmt, Connection connection) {
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}
	
	public static DataSource getDataSource(){
        return  dSource;
    }
}
