package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/schedules";
			String username = "root";
			String password = "";
			Class.forName(driver);

			Connection connection = DriverManager.getConnection(url,username,password);
			System.out.println("connected");
			return connection;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

}
