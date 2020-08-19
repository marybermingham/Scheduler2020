package scheduler.domain;


import java.util.List;

public class Schedule {
	
	private String Id;
    private List<Order> scheduledOrders;
    
    
    
	public Schedule() {
		super();
	}

	
	public String getId() {
		return Id;
	}


	public void setId(String id) {
		Id = id;
	}


	public List<Order> getScheduledOrders() {
		return scheduledOrders;
	}


	public void setScheduledOrders(List<Order> scheduledOrders) {
		this.scheduledOrders = scheduledOrders;
	}

	
}
