package scheduler.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Order;
import scheduler.domain.Product;

public class OrderService {

	
	private Connection connection;
	private ProductService productService;
	
	public OrderService(Connection connection) {
		super();
		this.connection = connection;
		this.productService = new ProductService(connection);
	}
	
	
	public void saveAll(List<Order> orders) {
		
	}


	public List<Order> getAllByOrderListId(Integer orderListId) {
		//return connection.;
		
		List<Order> result = new ArrayList<>();
		Statement stmt = null;
	    String query = "select * from order_detail where order_list_id = " + orderListId;
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	Order order = new Order();
	        	order.setId(rs.getInt("ID"));
	        	String productId = rs.getString("PRODUCT_ID");
	        	Product product = productService.getByCode(productId);
	        	order.setProduct(product);
	        	order.setCustomerName(rs.getString("CUSTOMER_NAME"));
	        	order.setCompletionDate(rs.getDate("REQUIRED_DATE"));
		         result.add(order);
		        }
		    } catch (SQLException e ) {
		      
		    } 
	    return result;
	}

	public Order saveOrUpdate(Order order, Integer orderListId) {

		String SQL_INSERT = "INSERT INTO ORDER_DETAIL (ORDER_LIST_ID, PRODUCT_ID, CUSTOMER_NAME, REQUIRED_DATE) VALUES (?,?,?,?)";
		String GENERATED_COLUMNS[] = { "ID" };

		try (
			 PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT, GENERATED_COLUMNS)) {
			preparedStatement.setInt(1, orderListId);
			preparedStatement.setLong(2, order.getProduct().getId());
			preparedStatement.setString(3, order.getCustomerName());
			preparedStatement.setDate(4, java.sql.Date.valueOf(order.getRequiredDate()));

			int row = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if (rs.next()) {
				int id = rs.getInt(1);
				order.setId(id);
			}


		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}

	 
	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
