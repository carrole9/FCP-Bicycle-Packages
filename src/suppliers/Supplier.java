package suppliers;
public class Supplier {
	
	private int supplierID;
	private String name;
	private String address;
	private String typeOfProduct;
	private String phoneNum;
	private String email;

	public Supplier(int supplierID, String name, String address, String typeOfProduct, String phoneNum, String email) {
		
		this.supplierID = supplierID;
		this.name = name;
		this.address = address;
		this.typeOfProduct = typeOfProduct;
		this.phoneNum = phoneNum;
		this.email = email;
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

	public String getTypeOfProduct() {
		return typeOfProduct;
	}

	public void setTypeOfProduct(String typeOfProduct) {
		this.typeOfProduct = typeOfProduct;
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


	public void display(){
		System.out.println("Supplier ID: "+supplierID);
		System.out.println("Supplier Name: "+name);
		System.out.println("Supplier Address: "+address);
		System.out.println("Supplier Product: "+typeOfProduct);
		System.out.println("Supplier Phone: "+phoneNum);
		System.out.println("Supplier Email: "+email);
	}
	
}

