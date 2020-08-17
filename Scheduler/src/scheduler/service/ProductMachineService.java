package scheduler.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import scheduler.domain.EmployeeHoliday;
import scheduler.domain.ProductMachine;

public class ProductMachineService {

	
	private Connection connection;
	
	public ProductMachineService(Connection connection) {
		super();
		this.connection = connection;
	}
	
  


	public List<ProductMachine> getAllProductMachines() {
		
		List<ProductMachine> result = new ArrayList<>();
		//return connection.;
			    Statement stmt = null;
	    String query = "select * from ProductMachine";
	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	ProductMachine productMachine = new ProductMachine();
	        	productMachine.setProductId(rs.getString("PRODUCT_ID"));
	        	productMachine.setMachineId(rs.getString("MACHINE_ID"));
	      
	           result.add(productMachine);
	        }
	    } catch (SQLException e ) {
	      
	    } finally {
	       // if (stmt != null) { stmt.close(); }
	    }
	 
		return result;
	}
	
	public List<String> getAllMachineIds() {
		
		List<ProductMachine> productMachines = getAllProductMachines();
		Set<String> uniqueMachineIds = new HashSet<>();
		
		for (ProductMachine productMachine : productMachines) {
			uniqueMachineIds.add(productMachine.getMachineId());
		}
		 	
		return new ArrayList<>(uniqueMachineIds);
	}
	
	


	public Connection getConnection() {
		return connection;
	}



	public void setConnection(Connection connection) {
		this.connection = connection;
	}
		
}
