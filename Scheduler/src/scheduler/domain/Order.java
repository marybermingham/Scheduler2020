package scheduler.domain;

import java.sql.Date;
import java.time.LocalDate;



public class Order {
	
	private Integer id;
	private Product product;
	private String customerName;
	private LocalDate requiredDate;
	
	public Order() {
		super();
	}


	public Order(Product product, String customerName, LocalDate requiredDate) {
		super();
		this.product = product;
		this.customerName = customerName;
		this.requiredDate = requiredDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public LocalDate getRequiredDate() {
		return requiredDate;
	}


	public void setRequiredDate(LocalDate requiredDate) {
		this.requiredDate = requiredDate;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", product=" + product + ", customerName=" + customerName + ", completionDate="
				+ requiredDate + "]";
	}


	public void setCompletionDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	
}





