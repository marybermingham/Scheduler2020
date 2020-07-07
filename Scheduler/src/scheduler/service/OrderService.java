package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Order;
import scheduler.domain.Product;

public class OrderService {

	
	private Connection connection;
	private ProductService productService;
	
	public OrderService(Connection connection, ProductService productService) {
		super();
		this.connection = connection;
		this.productService = productService;
	}
	
	
	public void saveAll(List<Order> orders) {
		
	}


	public List<Order> getAllOrders() {
		//return connection.;
		
		List<Order> result = new ArrayList<>();
		Statement stmt = null;
	    String query = "select * from Order";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	Order order = new Order();
	        	order.setId(rs.getString("ID"));
	        	String productId = rs.getString("PRODUCT_ID");
	        	Product product = productService.getProductById(productId);
	        	order.setProduct(product);
	        	order.setCustomerName(rs.getString("CUSTOMER_NAME"));
	        	order.setCompletionDate(rs.getDate("COMPLETION_DATE"));     		        	
		         result.add(order);
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
