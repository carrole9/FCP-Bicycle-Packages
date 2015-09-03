package bicyclestore.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.customers.Customer;
import bicyclestore.staff.Employee;
import bicyclestore.staff.SalesAssistant;
import bicyclestore.suppliers.Supplier;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.SalesTransaction;
import bicyclestore.transaction.ShoppingBasket;

public class DatabaseTest {

	@Test
	public void testAddCustomer() {
		Database database = new Database();
		database.addCustomer(new Customer(1, "John", "Wicklow", "0861234566", "john@gmail.com"));
		
		assertEquals("John", database.getCustomer(1).getName());
	}

	@Test
	public void testGetCustomer() {
		Database database = new Database();
		database.addCustomer(new Customer(1, "John", "Wicklow", "0861234567", "john@gmail.com"));
		
		Customer customer = database.getCustomer(1);
		
		assertEquals(1, customer.getId());
		assertEquals("Wicklow", customer.getAddress());
	}
	
	@Test
	public void testRemoveCustomer() {
		Database database = new Database();
		database.addCustomer(new Customer(1, "John", "Wicklow", "0861234567", "john@gmail.com"));
		
		database.removeCustomer(database.getCustomer("John"));
		
		assertNull(database.getCustomer("John"));
	}
	
	@Test
	public void testAddBicycle() {
		Database database = new Database();
		Bicycle bike = new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", 170, 300);
		database.addBicycle(bike);
		
		assertNotNull(database.getBicycle("Giant GFR"));
	}
	
	@Test
	public void testRemoveBicycle() {
		Database database = new Database();
		Bicycle bike = new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", 170, 300);
		database.addBicycle(bike);
		
		assertNotNull(database.getBicycle("Giant GFR")); // test that it has been added first
		
		database.removeBicycle(bike);
		assertNull(database.getBicycle("Giant GFR")); // then test that it has been removed
	}
	
	@Test
	public void testAddEmployee() {
		Database database = new Database();
		Employee employee = new SalesAssistant(10001, "Fred Flintstone", "0861234567", "password",8.65, 200004);
		database.addEmployee(employee);
		
		assertNotNull(employee);
		
	}
	
	@Test
	public void testRemoveEmployee() {
		Database database = new Database();
		Employee employee = new SalesAssistant(10001, "Fred Flintstone", "0861234567", "password",8.65, 200004);
		database.addEmployee(employee);
		
		assertNotNull(employee);
		
		System.out.println(database.removeEmployee(employee));
		assertNull(database.getEmployee(10001));
	}
	
	@Test
	public void testAddSupplier() {
		Database database = new Database();
		Supplier supplier = new Supplier(101, "ABC Bicycle Supplies", "Dublin","01 1234567", 
				"abc.bike.supplies@yahoo.com", new ArrayList<Bicycle>());
		
		database.addSupplier(supplier);
		
		assertNotNull(database.getSupplier(101));
	}
	
	@Test
	public void testRemoveSupplier() {
		Database database = new Database();
		Supplier supplier = new Supplier(101, "ABC Bicycle Supplies", "Dublin","01 1234567", 
				"abc.bike.supplies@yahoo.com", new ArrayList<Bicycle>());
		
		database.addSupplier(supplier);
		
		assertNotNull(database.getSupplier(101));
		
		database.removeSupplier(supplier);
		assertNull(database.getSupplier(101));
	}
	
	@Test
	public void testAddPurchasingTransaction() {
		Database database = new Database();
		
		database.addPurhasingTransaction(new PurchasingTransaction(10001, database.getEmployee(10002),
				database.getSupplier(102), 444.44, "Account", new Date(), new ShoppingBasket(),
				new Date()));
		
		assertNotNull(database.getPurchasingTransaction(10001));
	}

	@Test
	public void testRemovePurchasingTransaction() {
		Database database = new Database();

		database.addPurhasingTransaction(new PurchasingTransaction(10001, database.getEmployee(10002),
				database.getSupplier(102), 444.44, "Account", new Date(), new ShoppingBasket(), new Date()));

		assertNotNull(database.getPurchasingTransaction(10001));
		
		database.removePurchasingTransaction(database.getPurchasingTransaction(10001));
		assertNull(database.getPurchasingTransaction(10001));
		
	}
	
	@Test
	public void testAddSalesTransaction() {
		Database database = new Database();
		
		SalesTransaction firstsale= new SalesTransaction(111, database.getEmployee(10002),
				database.getCustomer("Tom Smith"),100.99, "Cash", new Date(), new ShoppingBasket());
		database.addSalesTransaction(firstsale);
		
		assertNotNull(database.getSalesTransaction(111));
	}
	
	@Test
	public void testRemoveSalesTransaction() {
		Database database = new Database();

		SalesTransaction firstsale = new SalesTransaction(111, database.getEmployee(10002),
				database.getCustomer("Tom Smith"), 100.99, "Cash", new Date(), new ShoppingBasket());
		database.addSalesTransaction(firstsale);

		assertNotNull(database.getSalesTransaction(111));
		
		database.removeSalesTransaction(firstsale);
		assertNull(database.getSalesTransaction(111));
	}

}
