package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class TestDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
    String host = "192.168.1.129:8888";
    String dbname = "test_db";
    String url = "jdbc:mariadb://" + host + "/" + dbname  // + "?useServerPrepStmts=true"
//    String url = "jdbc:mariadb://" + host + "/" + dbname + "?rewriteBatchedStatements=true"
//    		+ "&maxPoolSize=10&pool"
//    		+ "&sessionVariables=sql_mode=ORACLE"
//    		+ "&autocommit=false"
    		;
    
    String username = "maxscale";
    String password = "maxscale";
    
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
		System.out.println("connect closed");
	}

//	public ArrayList<testbean> selectDB() 
	public boolean selectDB() {
		connect() ;
	
		System.out.println("\n"+url+"\n");
		
		String sql = "SELECT @@server_id, @@sql_mode, @@autocommit FROM dual";
		
//		ArrayList<testbean> list = new ArrayList<>();
		
		testbean bean = null;

		try {
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery(sql);
			
			while (rs.next()) {
				bean = new testbean();
				bean.setServer_id(rs.getString("@@server_id"));
				bean.setAutocommit(rs.getString("@@autocommit"));
				bean.setSqlmode(rs.getString("@@sql_mode"));
//				bean.setC1(rs.getString("c1"));
//				bean.setC2(rs.getInt("c2"));
				
//				list.add(bean);
				
			}
			
			
			System.out.println(String.format("%s\t%s\t%s", "SERVER_ID", "AUTOCOMMIT", "SQL_MODE"));
			System.out.println(String.format("%s\t\t%s\t\t%s", bean.getServer_id(), bean.getAutocommit(), bean.getSqlmode()));
			
//			disconnect();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
//		return list;
		return true;
	}
	
	
	public boolean insertDB(String testbean1, int testbean2) throws SQLException {
		connect();
		String sql = "insert into test1.t1 (c1, c2)" + "values (?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);   //auto-commit false인 경우 executeUpdate 후 commit, catch에 rollback 필요

			pstmt.setString(1, testbean1);
			pstmt.setInt(2, testbean2);
				
			pstmt.executeUpdate();
			conn.commit();

			System.out.println("data inserted");
			
		} catch (SQLException e) {
			conn.rollback();
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

	public String rand_str() {

	    Random random = new Random();
		
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 3;

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
		return generatedString;
	}

	
}
