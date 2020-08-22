package scheduler.domain;



public class Product {
	
		private Integer id;
		private String code;
		private String name;
		private int noProcessDays;
		private String machineCode;
		private int noEmployeesRequired;
		
		public Product() {
			super();
		}


		public Product(String code, String name, int noProcessDay, String machineCode) {
			super();
			this.code = code;
			this.name = name;
			this.noProcessDays = noProcessDay;
			this.machineCode = machineCode;
		}


		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public int getNoEmployeesRequired() {
			return noEmployeesRequired;
		}


		public void setNoEmployeesRequired(int noEmployeesRequired) {
			this.noEmployeesRequired = noEmployeesRequired;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public int getNoProcessDays() {
			return noProcessDays;
		}


		public void setNoProcessDays(int noProcessDay) {
			this.noProcessDays = noProcessDay;
		}


		public String getMachineCode() {
			return machineCode;
		}


		public void setMachineCode(String machineCode) {
			this.machineCode = machineCode;
		}


		@Override
		public String toString() {
			return "Product [code=" + code + ", name=" + name + ", noProcessDays=" + noProcessDays
					+ ", machineId=" + machineCode + ", noEmployeesRequired=" + noEmployeesRequired + "]";
		}
		
		
		
	}




