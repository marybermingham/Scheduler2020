package scheduler.domain;


import java.util.List;

public class Schedule {
	
	private Schedule schedule;
    private List<Order> scheduledOrders;
    
    
    
	public Schedule() {
		super();
	}

	public Schedule(Schedule schedule,  List<Order> scheduledOrders) {
		super();
		this.schedule = schedule;
		this.List<Order> = List<order>
		
		
	
	}

	public Schedule getSchedule() {
		return schedule;
	}



	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}



	public List<Order> getScheduledOrders() {
		return scheduledOrders;
	}



	public void setScheduledOrders(List<Order> scheduledOrders) {
		this.scheduledOrders = scheduledOrders;
	}



	
}
