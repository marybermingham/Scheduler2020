package scheduler.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Employee;
import scheduler.domain.ScheduledOrder;
import scheduler.domain.Schedule;
import scheduler.domain.Product;

public class ScheduledOrderService {

	
	private Connection connection;
	private  ScheduleOrderEmployeeService scheduleOrderEmployeeService;

	public ScheduledOrderService(Connection connection) {
		super();
		this.connection = connection;
		scheduleOrderEmployeeService = new ScheduleOrderEmployeeService(connection);
	}


	public ScheduledOrder saveOrUpdate(ScheduledOrder scheduledOrder, Integer scheduleId) {

		String SQL_INSERT = "INSERT INTO ORDER (SCHEDULE_ID, ORDER_ID, START_DATE, END_DATE) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE ORDER_LIST_ID=?, CUSTOMER_NAME=?, PRODUCT_ID=?, REQUIRED_DATE=?";
		String GENERATED_COLUMNS[] = { "ID" };

		try (
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, GENERATED_COLUMNS)) {
			preparedStatement.setInt(1, scheduleId);
			preparedStatement.setInt(2, scheduledOrder.getOrder().getId());
			preparedStatement.setDate(3, java.sql.Date.valueOf(scheduledOrder.getStartDate()));
			preparedStatement.setDate(4, java.sql.Date.valueOf(scheduledOrder.getEndDate()));

			int row = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				scheduledOrder.setId(id);
			}
			for (Employee employee : scheduledOrder.getEmployees()){
				scheduleOrderEmployeeService.save(scheduledOrder.getId(), employee.getId());
			}


		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduledOrder;
	}


//	public List<ScheduledOrder> getAllScheduledOrders() {
//		//return connection.;
//		
//		List<Schedule> result = new ArrayList<>();
//		Statement stmt = null;
//	    String query = "select * from ScheduledOrder";
//	    try {
//	        stmt = connection.createStatement();
//	        ResultSet rs = stmt.executeQuery(query);
//	        while (rs.next()) {
//	        	ScheduledOrder scheduledOrder = new ScheduledOrder();   		        	
//		         result.add(scheduledOrders);
//		        }
//		    } catch (SQLException e ) {
//		      
//		    } 
//	    return result;
//	}
	
	
	 
	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
