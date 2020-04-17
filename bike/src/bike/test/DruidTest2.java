package bike.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DruidTest2 {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pStatement = null;
		try {
			conn = DruidTest1.getConnection();
			String sqlString = "insert into user(id,loginname,loginpass) value(3,?,?)";
			// ��ȡpstmt����
			pStatement = conn.prepareStatement(sqlString);
			// ���ʺŸ�ֵ
			pStatement.setString(1, "sd");
			pStatement.setString(2, "sd");
			
			// ִ�д���
			int count = pStatement.executeUpdate();
			System.out.println(count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DruidTest1.close(pStatement, conn);
		}
	}
}
