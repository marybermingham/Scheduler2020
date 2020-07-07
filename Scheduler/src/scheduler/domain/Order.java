package scheduler.domain;

import java.util.Date;



public class Order {
	
	private String id;
	private Product product;
	private String customerName;
	private Date completionDate;
	
	public Order() {
		super();
	}


	public Order(Product product, String customerName, Date completionDate) {
		super();
		this.product = product;
		this.customerName = customerName;
		this.completionDate = completionDate;
	}

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Date getCompletionDate() {
		return completionDate;
	}


	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
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
				+ completionDate + "]";
	}

	
}





