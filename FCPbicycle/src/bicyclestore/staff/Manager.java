package bicyclestore.staff;

import java.util.ArrayList;

import bicyclestore.suppliers.Supplier;

public class Manager extends Employee{
	
	
	private String division, speciality;
	

	public Manager(int staffID, String name, String phoneNum, String password,double wages, String division, String speciality) {
		
		super(staffID, name, phoneNum, password, wages);
		this.division = division;
		this.speciality = speciality;
	
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
	public void display(){
		System.out.println("Manager");
		super.display();
		System.out.println("Division: "+ division + " and Speciality is: "+ speciality);
		
	}
	
	public void addNewSupplier(int supplierID, String name, String address, String typeOfProduct, String phoneNum, String email){
		ArrayList <Supplier> suppliers = new ArrayList <Supplier>();
		Supplier supplier = new Supplier(supplierID, name, address, typeOfProduct, phoneNum, email);
		suppliers.add(supplier);
		 
	}
}
