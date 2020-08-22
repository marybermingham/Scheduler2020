package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import scheduler.domain.Product;

public class ProductService {

	private Connection connection;
	
	public ProductService(Connection connection) {
		super();
		this.connection = connection;
	}


	public List<Product> getAll() {

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
	
	
	public Product getByCode(String code) {
		

		//return connection.;
	    Statement stmt = null;
	    Product product = null;
	    String query = "select * from Product where code = '" + code + "'";
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
	    	product.setId(rs.getInt("ID"));
			product.setCode(rs.getString("CODE"));
	    	product.setName(rs.getString("NAME"));
	    	product.setNoEmployeesRequired(rs.getInt("NO_EMP_REQUIRED"));
	    	product.setNoProcessDays(rs.getInt("NO_PROCESS_DAYS"));
	    	product.setMachineCode(rs.getString("MACHINE_ID"));
	    	  	
		} catch (SQLException e ) {
		      
	    }
		return product;
	}

	public List<String> getAllMachineCodes() {

		List<Product> products = getAll();
		Set<String> uniqueMachineCodes = new HashSet<>();

		for (Product product: products) {
			uniqueMachineCodes.add(product.getMachineCode());
		}

		return new ArrayList<>(uniqueMachineCodes);
	}


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
