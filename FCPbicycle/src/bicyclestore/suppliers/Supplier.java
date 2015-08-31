package bicyclestore.suppliers;

import java.util.ArrayList;

import bicyclestore.Database;
import bicyclestore.bikes.Bicycle;

public class Supplier {
	
	private int supplierID;
	private String name;
	private String address;
//	private String typeOfProduct;
//	private Bicycle typeOfProduct;
	private ArrayList<Bicycle> products;
	private String phoneNum;
	private String email;
	private Database database;

	public Supplier(int supplierID, String name, String address, String phoneNum, String email, ArrayList<Bicycle> products) {
		
		this.supplierID = supplierID;
		this.name = name;
		this.address = address;
		//this.typeOfProduct = new Bicycle(typeOfProduct, "Black", 21, 20, "Aluminium", database, 300, 469.95);
		this.phoneNum = phoneNum;
		this.email = email;
		this.products = products;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
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
	
	public void addProduct(Bicycle bicycle) {
		products.add(bicycle);
	}
	
	public ArrayList<Bicycle> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Bicycle> products) {
		this.products = products;
	}

	public void display(){
		System.out.println("Supplier ID: "+supplierID);
		System.out.println("Supplier Name: "+name);
		System.out.println("Supplier Address: "+address);
		//System.out.println("Supplier Product: "+typeOfProduct);
		System.out.println("Supplier Phone: "+phoneNum);
		System.out.println("Supplier Email: "+email);
	}
	
}
