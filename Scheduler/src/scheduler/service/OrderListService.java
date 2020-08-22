package scheduler.service;

import scheduler.domain.Order;
import scheduler.domain.OrderList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderListService {

	private Connection connection;
	private OrderService orderService;

	public OrderListService(Connection connection) {
		super();
		this.connection = connection;
		this.orderService = new OrderService(connection);
	}
	

	public List<OrderList> getAll() {
		//return connection.;
		List<OrderList> result = new ArrayList<>();
	    return result;
	}

	public OrderList save(OrderList orderList) {

		String SQL_INSERT = "INSERT INTO ORDER_LIST (BEST_SCHEDULE_ID) VALUES (?)";
		String GENERATED_COLUMNS[] = { "ID" };

		try (
			PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, GENERATED_COLUMNS)) {
			if(orderList.getBestSchedule() != null) {
				preparedStatement.setInt(1, orderList.getBestSchedule().getId());
			}else {
				preparedStatement.setNull(1, Types.INTEGER);
			}
			

			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				orderList.setId(id);
			}

		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Order> savedOrders = new ArrayList<>();
		for(Order order : orderList.getOrders()){
			savedOrders.add(orderService.saveOrUpdate(order, orderList.getId()));
		}
		orderList.setOrders(savedOrders);

		return orderList;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
