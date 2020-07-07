package scheduler.domain;

public class Employee {
	
	private String id;
	private String employeeName;
	private String address;
	private String phoneNo;
	private String email; 
	
	
	
	public Employee() {
		super();
	}



	public Employee(String id, String employeeName, String address, String phoneNo, String email) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.address = address;
		this.phoneNo = phoneNo;
		this.email = email;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getEmployeeName() {
		return employeeName;
	}



	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPhoneNo() {
		return phoneNo;
	}



	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

}
	

	