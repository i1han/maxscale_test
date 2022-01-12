package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
    String host = "192.168.99.200:8888";
    String dbname = "test1";
    String url = "jdbc:mariadb://" + host + "/" + dbname;
    String username = "root";
    String password = "root";
	    
	void connect() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("DB connected");
		} catch (Exception e) {
			System.out.println("DB connect failed");
			e.printStackTrace();
		} 
	}
	
	void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<testbean> selectDB() {
		connect();
		
		String sql = "SELECT * FROM t1";
		
		ArrayList<testbean> list = new ArrayList<>();
		
		testbean bean = null;

		try {
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery(sql);
			
			while (rs.next()) {
				bean = new testbean();
				bean.set_C1(rs.getString("c1"));
				bean.set_C2(rs.getInt("c2"));

				list.add(bean);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} 
		return list;
	}
	
	
	public boolean insertDB(String testbean1, int testbean2) {
		connect();
		String sql = "insert into test1.t1 (c1, c2)" + "values (?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			//pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, testbean1);
			pstmt.setInt(2, testbean2);
				
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}	
	
	public boolean setDB(String testbean) {
		connect();
		String sql = "set session sql_mode='?'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			//pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, testbean);
				
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
	
}
