package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import scheduler.domain.Product;

public class ProductService {

	private Connection connection;
	
	public ProductService(Connection connection) {
		super();
		this.connection = connection;
	}


	public List<Product> getAllProducts() {
		

			
			List<Product> result = new ArrayList<>();
			//return connection.;
				    Statement stmt = null;
		    String query = "select * from Product";
		    try {
		        stmt = connection.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	Product product = getProductFromResult(rs);
			        result.add(product);
			        
			        }
			    } catch (SQLException e ) {
			      
			    } finally {
			       // if (stmt != null) { stmt.close(); }
			    }
			 
				return result;
			}
	
	
	public Product getProductById(String id) {
		

		//return connection.;
	    Statement stmt = null;
	    Product product = null;
	    String query = "select * from Product where id = '" + id + "'";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	product = getProductFromResult(rs);
		    }
	        }catch (SQLException e ) {
		      
		    } finally {
		       // if (stmt != null) { stmt.close(); }
		    }	 
			return product;
		}
	
	private Product getProductFromResult (ResultSet rs) {
		Product product = new Product();
		try {
		
	    	product.setId(rs.getString("ID"));
	    	product.setProductName(rs.getString("PRODUCT_NAME"));
	    	product.setNoEmployeesRequired(rs.getInt("NO_EMP_REQUIRED"));
	    	product.setNoProcessDays(rs.getInt("NO_PROCESS_DAYS"));
	    	product.setMachineId(rs.getString("MACHINE_ID"));
	    	  	
		} catch (SQLException e ) {
		      
	    }
		return product;
	}


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
