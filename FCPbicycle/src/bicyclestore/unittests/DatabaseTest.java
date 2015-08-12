package bicyclestore.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.customers.Customer;

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

}
