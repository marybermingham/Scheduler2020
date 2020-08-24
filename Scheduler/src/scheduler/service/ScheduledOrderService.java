package scheduler.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import scheduler.domain.Employee;
import scheduler.domain.ScheduledOrder;

public class ScheduledOrderService {

	
	private Connection connection;
	private  ScheduleOrderEmployeeService scheduleOrderEmployeeService;

	public ScheduledOrderService(Connection connection) {
		super();
		this.connection = connection;
		scheduleOrderEmployeeService = new ScheduleOrderEmployeeService(connection);
	}


	public ScheduledOrder saveOrUpdate(ScheduledOrder scheduledOrder, Integer scheduleId) {

		String SQL_INSERT = "INSERT INTO SCHEDULED_ORDER (SCHEDULE_ID, ORDER_ID, START_DATE, END_DATE) VALUES (?,?,?,?)";
		String GENERATED_COLUMNS[] = { "ID" };

		try (
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, GENERATED_COLUMNS)) {
			preparedStatement.setInt(1, scheduleId);
			preparedStatement.setInt(2, scheduledOrder.getOrder().getId());
			preparedStatement.setDate(3, java.sql.Date.valueOf(scheduledOrder.getStartDate()));
			preparedStatement.setDate(4, java.sql.Date.valueOf(scheduledOrder.getEndDate()));

			preparedStatement.executeUpdate();
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
