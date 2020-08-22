package scheduler.domain;

import java.time.LocalDate;
import java.util.List;

public class ScheduledOrder {
	private Integer id;
	private Order order;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Employee> employees;
    

	public ScheduledOrder() {
		super();
	}


	public ScheduledOrder(Order order, LocalDate startDate, LocalDate endDate, List<Employee> employees) {
		super();
		this.order = order;
		this.startDate = startDate;
		this.endDate = endDate;
		this.employees = employees;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "ScheduledOrder{" +
				"order=" + order +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", employees=" + employees +
				'}';
	}
}
