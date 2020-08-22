package scheduler.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Schedule;
import scheduler.domain.Schedule;
import scheduler.domain.Product;
import scheduler.domain.ScheduledOrder;

public class BestScheduleService {

	
	private Connection connection;
	ScheduledOrderService scheduledOrderService;
	
	public BestScheduleService(Connection connection) {
		super();
		this.connection = connection;
		scheduledOrderService = new ScheduledOrderService(connection);
	}


	public Schedule saveBestSchedule(Schedule schedule, Integer orderListId) {

		String SQL_INSERT = "INSERT INTO BEST_SCHEDULE (ORDER_LIST_ID, SCORE) VALUES (?,?)";
		String GENERATED_COLUMNS[] = { "ID" };

		try (
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, GENERATED_COLUMNS)) {
			preparedStatement.setInt(1, orderListId);
			preparedStatement.setInt(2, schedule.getScore());

			int row = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				schedule.setId(id);
			}
			List<ScheduledOrder> savedScheduledOrders = new ArrayList<ScheduledOrder>();				
			for(ScheduledOrder scheduledOrder : schedule.getScheduledOrders()){
				ScheduledOrder savedScheduledOrder = scheduledOrderService.saveOrUpdate(scheduledOrder, schedule.getId());
				savedScheduledOrders.add(savedScheduledOrder);	
			}
			schedule.setScheduledOrders(savedScheduledOrders);

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}





	public void clearBestSchedule(Schedule schedule, Integer orderListId) {
		//return connection.;
		Statement stmt = null;

		String sql1 = "delete from BEST_SCHEDULE WHERE ORDER_LIST_ID = " + orderListId;
		String sql2 = "delete from SCHEDULED_ORDER WHERE ID IN (" + String.join(",", getScheduledOrderIdList(schedule)) + ")";

		try {
			stmt = connection.createStatement();
			stmt.execute(sql1);
			stmt.execute(sql2);
			for(ScheduledOrder scheduledOrder : schedule.getScheduledOrders()){
				String sql = "delete from SCHEDULED_ORDER_EMPLOYEE WHERE SCHEDULED_ORDER_ID = " + scheduledOrder.getId();
				stmt.execute(sql);
			}

		} catch (SQLException e ) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
	}

	private List<String> getScheduledOrderIdList(Schedule schedule){
		List<String> scheduledOrderIdList = new ArrayList<>();
		for(ScheduledOrder scheduledOrder : schedule.getScheduledOrders()){
			scheduledOrderIdList.add(scheduledOrder.getId().toString());
		}
		return scheduledOrderIdList;
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
