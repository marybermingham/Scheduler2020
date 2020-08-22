package scheduler.domain;

public class Employee {
	
	private Integer id;
	private String cardId;
	private String name;
	private String address;
	private String phoneNo;
	private String email; 

	public Employee() {
		super();
	}

	public Employee(String cardId, String name, String address, String phoneNo, String email) {
		super();
		this.cardId = cardId;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
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
	

	