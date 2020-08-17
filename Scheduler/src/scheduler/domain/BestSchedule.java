package scheduler.domain;



public class BestSchedule {


	private String id;
	private int score;
	
	
	public BestSchedule() {
		super();
	}


	public BestSchedule(String id, int score) {
		super();
		this.id = id;
		this.score = score;
	}

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getScore() {
		// loop through each employee start on the first day of the first order of the schedule. looping through each day 
		//to see wheither that day is contained in the list of  unavailable day (not) for that Employee if true add this 
		//day to a list called employeescore. Find the number of available days for this employee and multiply it by 10. 
		//do this for all employees 
		//loop through machines and do the same thing. 
		//score = new Score(employee, machine);
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
	
	}