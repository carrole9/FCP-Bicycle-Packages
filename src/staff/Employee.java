package staff;

public class Employee {
	
	private int staffID;
	private String name, phoneNum, password;

	public Employee(int staffID, String name, String phoneNum, String password) {
		
		this.staffID = staffID;
		this.name = name;
		this.phoneNum = phoneNum;
		this.password = password;
	}


	public int getStaffID() {
		return staffID;
	}


	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public void display(){
		System.out.println(name+ "'s staff I.D is: "+ staffID + ", the contact number is "+ phoneNum+ ".");
	}
}