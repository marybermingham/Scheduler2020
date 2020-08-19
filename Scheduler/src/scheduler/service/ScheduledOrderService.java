package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Schedule;
import scheduler.domain.Product;

public class ScheduledOrderService {

	
	private Connection connection;
	
	public ScheduledOrderService(Connection connection) {
		super();
		this.connection = connection;
	}
	
	



	public List<Order> getAllOrders() {
		//return connection.;
		
		List<Schedule> result = new ArrayList<>();
		Statement stmt = null;
	    String query = "select * from Order";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	ScheduledOrder scheduledOrder = new ScheduledOrder();   		        	
		         result.add(scheduledOrders);
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
