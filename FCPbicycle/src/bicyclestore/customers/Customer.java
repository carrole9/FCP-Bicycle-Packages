package bicyclestore.customers;

public class Customer {

	private int id;
	private String name;
	private String address;
	private String phoneNum;
	private String email;
	
	public Customer(int id, String name, String address, String phoneNum, String email) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNum = phoneNum;
		this.email = email;
	}

	public void display() {
		System.out.println("Customer id: "+id+", name: "+name+", address: "+address);
	}
	
	public int getId() {
		return id;
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

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}