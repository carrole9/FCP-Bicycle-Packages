package bicyclestore.staff;

public class SalesAssistant extends Employee {
	
	private int managerID;

	public SalesAssistant(int staffID, String name, String phoneNum, String password, double wages, int managerID) {
		super(staffID, name, phoneNum, password, wages);
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
