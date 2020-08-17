package scheduler.domain;

import java.time.LocalDate;

public class EmployeeHoliday {
		
		private String id;
		private Employee employee;
		private LocalDate date;
		


		public EmployeeHoliday() {
			super();
		}


		public String getId() {
			return id;
		}



		public void setId(String id) {
			this.id = id;
		}


		public Employee getEmployee() {
			return employee;
		}


		public void setEmployee(Employee employee) {
			this.employee = employee;
		}


		public LocalDate getDate() {
			return date;
		}


		public void setDate(LocalDate date) {
			this.date = date;
		}


	
		
	}


         
