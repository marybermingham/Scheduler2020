package scheduler.domain;

import java.util.List;

public class ScheduleOrderEmployee {
	
	private Order order;
	private Schedule schedule;
	private List<Employee> employees;
	 
	
	
	
	public ScheduleOrderEmployee() {
		super();
	}




	public Order getOrder() {
		return order;
	}




	public void setOrder(Order order) {
		this.order = order;
	}




	public Schedule getSchedule() {
		return schedule;
	}




	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}




	public List<Employee> getEmployees() {
		return employees;
	}




	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}








	








	}