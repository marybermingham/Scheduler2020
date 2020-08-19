package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Schedule;
import scheduler.domain.Product;

public class ScheduleOrderEmployeeService {

	
	private Connection connection;
	
	public ScheduleOrderEmployeeService(Connection connection, ProductService productService) {
		super();
		this.connection = connection;
	}
	
	
	public void saveAll(List<Schedule> schedules) {
		
	}


	public List<Schedule> getAllSchedules() {
		//return connection.;
		
		List<Schedule> result = new ArrayList<>();
		Statement stmt = null;
	    String query = "select * from Schedule";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	Schedule schedule = new Schedule();   		        	
		         result.add(schedule);
		        }
		    } catch (SQLException e ) {
		      
		    } 
	    return result;
	}
	 
	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
