package bicyclestore.staff;

public class SalesAssistant extends Employee {
	
	private int managerID;

	public SalesAssistant(int staffID, String name, String phoneNum, String password, int managerID) {
		super(staffID, name, phoneNum, password);
		this.managerID = managerID;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	
	public void display(){
		System.out.println("Sales Assistant");
		super.display();
		System.out.println("His/Her Manager's ID is: "+ managerID);
	}
}