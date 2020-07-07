package scheduler.domain;



public class Product {
	
		private String id;
		private String productName;
		private int noProcessDays;
		private String machineId;
		private int noEmployeesRequired;
		
		public Product() {
			super();
		}


		public Product(String id, String productName, int noProcessDay, String machineId) {
			super();
			this.id = id;
			this.productName = productName;
			this.noProcessDays = noProcessDay;
			this.machineId = machineId;
		}



		
		public int getNoEmployeesRequired() {
			return noEmployeesRequired;
		}


		public void setNoEmployeesRequired(int noEmployeesRequired) {
			this.noEmployeesRequired = noEmployeesRequired;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getProductName() {
			return productName;
		}


		public void setProductName(String productName) {
			this.productName = productName;
		}


		public int getNoProcessDays() {
			return noProcessDays;
		}


		public void setNoProcessDays(int noProcessDay) {
			this.noProcessDays = noProcessDay;
		}


		public String getMachineId() {
			return machineId;
		}


		public void setMachineId(String machineId) {
			this.machineId = machineId;
		}


		@Override
		public String toString() {
			return "Product [id=" + id + ", productName=" + productName + ", noProcessDays=" + noProcessDays
					+ ", machineId=" + machineId + ", noEmployeesRequired=" + noEmployeesRequired + "]";
		}
		
		
		
	}




