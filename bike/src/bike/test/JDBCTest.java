package bike.test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JDBCTest {
	public static void main(String[] args) {
		
		Statement statement = null;
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String sql = "insert into user value(0,'666','666')";
			
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bike","root","");
			
			statement = (Statement) connection.createStatement();
			
			int count = statement.executeUpdate(sql);
			
			if (count == 1) {
				System.out.println("³É¹¦");
			}else {
				System.out.println("Ê§°Ü");
			}
			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
