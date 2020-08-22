package scheduler.domain;

import java.time.LocalDate;

public class EmployeeHoliday {
		
		private Integer id;
		private Employee employee;
		private LocalDate date;


		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public EmployeeHoliday() {
			super();
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


         
