package bicyclestore;

import java.util.ArrayList;
import java.util.Arrays;

import bicyclestore.bikes.Bicycle;
import bicyclestore.customers.Customer;
import bicyclestore.staff.Employee;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.SalesTransaction;
import bicyclestore.suppliers.Supplier;

public class Database {
	
	private ArrayList<Bicycle> bicycles;
	private ArrayList<Customer> customers;
	private ArrayList<Employee> employees;
	private ArrayList<Supplier> suppliers;
	private ArrayList<PurchasingTransaction> purchasingTransactions;
	private ArrayList<SalesTransaction> salesTransactions;

	public Database() {
		bicycles = new ArrayList<Bicycle>();
		customers = new ArrayList<Customer>();
		employees = new ArrayList<Employee>();
		suppliers = new ArrayList<Supplier>();
		purchasingTransactions = new ArrayList<PurchasingTransaction>();
		salesTransactions = new ArrayList<SalesTransaction>();
	}
	
	public boolean addCustomer(Customer customer) {
		return customers.add(customer);
	}
	
	public Customer getCustomer(int id) {
		for(Customer customer : customers) {
			if(customer.getId() == id)
				return customer;
		}
		// if customer not found return null and output message to cmd line
		System.out.println("Customer not found");
		return null;
	}
	
	public Customer getCustomer(String name) {
		for(Customer customer : customers) {
			if(customer.getName().equals(name))
				return customer;
		}
		// if customer not found return null and output message to cmd line
		System.out.println("Customer not found");
		return null;
	}
	
	public boolean removeCustomer(Customer customer) {
		return customers.remove(customer);
	}
	
	public boolean addBicycle(Bicycle bicycle) {
		return bicycles.add(bicycle);
	}
	
	public Bicycle getBicycle(String model) {
		for(Bicycle bicycle : bicycles) {
			if(bicycle.getModel().equals(model))
				return bicycle;
		}
		// if customer not found return null and output message to cmd line
		System.out.println("Bicycle: "+model+" not found");
		return null;
	}
	
	public boolean removeBicycle(Bicycle bicycle) {
		return bicycles.remove(bicycle);
	}
	
	public boolean addEmployee(Employee employee) {
		return employees.add(employee);
	}
	
	public Employee getEmployee(int id) {
		for(Employee employee : employees) {
			if(employee.getStaffID() == id)
				return employee;
		}
		// if customer not found return null and output message to cmd line
		System.out.println("Employee not found");
		return null;
	}
	
	public boolean isValidEmployee(int staffId, char[] password) {
		for(Employee employee : employees) {
			if(employee.getStaffID() == staffId) {
				if(Arrays.equals(password, employee.getPassword().toCharArray()))
					return true;
			}
		}
		return false;
	}
	
	public boolean removeEmployee(Employee employee) {
		return employees.remove(employee);
	}
	
	public boolean addSupplier(Supplier supplier) {
		return suppliers.add(supplier);
	}
	
	public Supplier getSupplier(int supplierID) {
		for(Supplier supplier : suppliers) {
			if(supplier.getSupplierID() == supplierID)
				return supplier;
		}
		// if supplier not found return null and output message to cmd line
		System.out.println("Supplier not found");
		return null;
	}
	public Supplier getSupplier(String name) {
		for(Supplier supplier : suppliers) {
			if(supplier.getName().equals(name))
				return supplier;
		}
		// if supplier not found return null and output message to cmd line
		System.out.println("Supplier not found");
		return null;
	}


	public boolean removeSupplier(Supplier supplier) {
		return suppliers.remove(supplier);
	}
	
	public void addPurhasingTransaction(PurchasingTransaction transaction) {
		purchasingTransactions.add(transaction);
	}
	
	public PurchasingTransaction getPurchasingTransaction(int transactionId) {
		for(PurchasingTransaction transaction : purchasingTransactions) {
			if(transaction.getTransactionID() == transactionId)
				return transaction;
		}
		// if supplier not found return null and output message to cmd line
		System.out.println("Purchasing transaction not found");
		return null;
	}
	
	public boolean removePurchasingTransaction(PurchasingTransaction transaction) {
		return purchasingTransactions.remove(transaction);
	}
	
	public void addSalesTransaction(SalesTransaction transaction) {
		salesTransactions.add(transaction);
	}
	
	public SalesTransaction getSalesTransaction(int transactionId) {
		for(SalesTransaction transaction : salesTransactions) {
			if(transaction.getTransactionID() == transactionId)
				return transaction;
		}
		// if supplier not found return null and output message to cmd line
		System.out.println("Sales transaction not found");
		return null;
	}
	
	public boolean removeSalesTransaction(SalesTransaction transaction) {
		return salesTransactions.remove(transaction);
	}
	
	public ArrayList<Bicycle> getBicycles() {
		return bicycles;
	}

	public void setBicycles(ArrayList<Bicycle> bicycles) {
		this.bicycles = bicycles;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public ArrayList<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
	}

	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(ArrayList<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public ArrayList<PurchasingTransaction> getPurchasingTransactions() {
		return purchasingTransactions;
	}

	public void setPurchasingTransactions(ArrayList<PurchasingTransaction> purchasingTransactions) {
		this.purchasingTransactions = purchasingTransactions;
	}

	public ArrayList<SalesTransaction> getSalesTransactions() {
		return salesTransactions;
	}

	public void setSalesTransactions(ArrayList<SalesTransaction> salesTransactions) {
		this.salesTransactions = salesTransactions; 
	}
	
}
