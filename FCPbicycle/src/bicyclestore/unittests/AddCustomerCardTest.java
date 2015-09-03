package bicyclestore.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.cardlayouts.customercardlayouts.AddCustomerCard;
import bicyclestore.cardlayouts.customercardlayouts.CustomersCardLayout;
import bicyclestore.customers.Customer;

public class AddCustomerCardTest {

	@Test
	public void testAddCustomer() {
		Database database = new Database();
		CustomersCardLayout cardLayout = new CustomersCardLayout(database);
		AddCustomerCard addCustomerCard = new AddCustomerCard(database, cardLayout);
		
		
		// add a customer and use getter methods to test
		int transactionId = 12345;
		addCustomerCard.addCustomer(12345+"", "Tom Smith", "Aungier st, Dublin", "0861234567", "tom.smith@gmail.com");
		
		assertEquals("Tom Smith", database.getCustomer(transactionId).getName());
		assertEquals("Aungier st, Dublin", database.getCustomer(transactionId).getAddress());
		assertEquals("0861234567", database.getCustomer(transactionId).getPhoneNum());
		assertEquals("tom.smith@gmail.com", database.getCustomer(transactionId).getEmail());
		
		// attempt to add a customer with non numeric transaction id
		addCustomerCard.addCustomer("abcde", "John Doe", "Bray", "0877654321", "john.doe.hotmail.com");
		Customer johnDoe = database.getCustomer("John Doe");
		assertNull(johnDoe);
	}

}
