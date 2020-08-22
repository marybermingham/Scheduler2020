package scheduler.domain;


import java.util.List;

public class Schedule {
	private Integer id;
    private List<ScheduledOrder> scheduledOrders;
    private int score;

	public Schedule() {
		super();
	}

	public Schedule(List<ScheduledOrder> scheduledOrders) {
		this.scheduledOrders = scheduledOrders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<ScheduledOrder> getScheduledOrders() {
		return scheduledOrders;
	}

	public void setScheduledOrders(List<ScheduledOrder> scheduledOrders) {
		this.scheduledOrders = scheduledOrders;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Schedule{" +
				"scheduledOrders=" + scheduledOrders +
				", score=" + score +
				'}';
	}
}
