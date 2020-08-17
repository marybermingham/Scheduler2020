package scheduler.domain;

import java.util.List;

public class Schedule
{
	
	private List<ScheduledOrder> scheduledOrders;
	
	

	public Schedule(List<ScheduledOrder> scheduledOrders) {
		super();
		this.scheduledOrders = scheduledOrders;
	}

	public List<ScheduledOrder> getSheduledOrders() {
		return scheduledOrders;
	}

	public void setSheduledOrders(List<ScheduledOrder> sheduledOrders) {
		this.scheduledOrders = sheduledOrders;
	}
	
	

	
	
	
}


