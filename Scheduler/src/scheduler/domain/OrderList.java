package scheduler.domain;

import java.util.List;


public class OrderList {

	private Integer id;
	private List<Order> orders;
	private Schedule bestSchedule;

	public OrderList() {
		super();
	}

	public OrderList(List<Order> orders) {
		this.orders = orders;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Schedule getBestSchedule() {
		return bestSchedule;
	}

	public void setBestSchedule(Schedule bestSchedule) {
		this.bestSchedule = bestSchedule;
	}
}





