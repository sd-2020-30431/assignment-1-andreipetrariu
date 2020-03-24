package data_access;

import java.sql.*;
import javax.swing.JOptionPane;
public class DBConnector {
	private static DBConnector dbconn=new DBConnector();
	
	private DBConnector() {
            try{
                    Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e) {
			System.out.println("Connection error! "+e.getMessage());
		}
	}
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wasteless_db?autoReconnect=true&useSSL=false","root","andrei18");
	        //JOptionPane.showMessageDialog(null,"Connection established!");
	        return conn;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Connection failed!(DBConnector)");
		}
		return null;
	}
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to close connection!");
		}
	}
	public static void close(Statement statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to close connection!");
		}
	}
	public static void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Failed to close connection!");
		}
	}

}