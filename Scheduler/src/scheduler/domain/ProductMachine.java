package scheduler.domain;

import java.sql.Connection;

public class ProductMachine {
	
	private String productId;
	private String machineId;
	
	
	
	public ProductMachine() {
		super();
	}



	public ProductMachine(String productId, String machineId) {
		super();
		this.productId = productId;
		this.machineId = machineId;
	}



	public ProductMachine(Connection connection) {
		// TODO Auto-generated constructor stub
	}



	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getMachineId() {
		return machineId;
	}



	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	

	
	
	
	
	
}




