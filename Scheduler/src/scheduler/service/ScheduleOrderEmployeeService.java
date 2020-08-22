package scheduler.service;

import scheduler.domain.ScheduledOrder;

import java.sql.*;

public class ScheduleOrderEmployeeService {

	
	private Connection connection;
	
	public ScheduleOrderEmployeeService(Connection connection) {
		super();
		this.connection = connection;
	}


	public void save(Integer scheduledOrderId, Integer employeeId) {

		String SQL_INSERT = "INSERT INTO ORDER (SCHEDULED_ORDER_ID, EMPLOYEE_ID) VALUES (?,?)";

		try (
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)) {
			preparedStatement.setInt(1, scheduledOrderId);
			preparedStatement.setInt(2, employeeId);

			int row = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearRecordsForScheduledOrder(ScheduledOrder scheduledOrder) {
		//return connection.;
		Statement stmt = null;

		String sql = "delete from SCHEDULED_ORDER_EMPLOYEE from Order where SCHEDULED_ORDER_ID = " + scheduledOrder.getId();
		try {
			stmt = connection.createStatement();
			stmt.executeQuery(sql);

		} catch (SQLException e ) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}
	}


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
