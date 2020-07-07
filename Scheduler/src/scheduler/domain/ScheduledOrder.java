package scheduler.domain;

import java.util.Date;
import java.util.List;

public class ScheduledOrder {
	
	private Order order;
    private Date startDate;
    private Date endDate;
    private List<Employee> employees;
    
    
    
	public ScheduledOrder() {
		super();
	}


	public ScheduledOrder(Order order, Date startDate, Date endDate, List<Employee> employees) {
		super();
		this.order = order;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employees = employees;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public List<Employee> getEmployees() {
		return employees;
	}


	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
    
    
	
    
}
