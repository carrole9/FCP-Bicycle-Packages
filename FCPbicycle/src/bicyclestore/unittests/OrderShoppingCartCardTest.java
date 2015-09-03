package bicyclestore.unittests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.MountainBike;
import bicyclestore.cardlayouts.ordercardlayouts.OrderFromSupplierCard;
import bicyclestore.cardlayouts.ordercardlayouts.OrderShoppingCartCard;
import bicyclestore.cardlayouts.ordercardlayouts.OrdersCardLayout;
import bicyclestore.cardlayouts.profitandlosscardlayout.ProfitAndLossCard;
import bicyclestore.staff.Employee;
import bicyclestore.staff.Manager;
import bicyclestore.suppliers.Supplier;
import bicyclestore.transaction.ShoppingBasket;

public class OrderShoppingCartCardTest {
	
	@Test
	public void testAddToCart() {
		// add a bicycle to the shopping basket
		OrderShoppingCartCard shoppingCartCard = createShoppingCartCard();
		Bicycle bike = new MountainBike(21, "Giant STP3", "Black", 18, 26, "Alloy", 280, 500);
		Supplier supplier = new Supplier(12345, "Bike Wholesale shop", "Dublin", "0861234567", 
				"bikewholesale@yahoo.com", new ArrayList<Bicycle>());
		shoppingCartCard.addToCart("Mountain Bike", bike, 3, supplier);
		
		ShoppingBasket basket = shoppingCartCard.getShoppingBasket();
		
		assertNotNull(basket);
		assertEquals(3, basket.getQuantity());
		assertEquals("Giant STP3", basket.getShoppingList().get(0).getModel());
	}

	@Test
	public void testGetDeliveryDate() {

		OrderShoppingCartCard shoppingCartCard = createShoppingCartCard();
		
		// create a delivery date 4 days from now. delivery time is 14:00:00
		Date deliveryDate = shoppingCartCard.getDeliveryDate(new Date(), 4); 
		
		// create date 4 days from now
		Calendar fourDaysFromNow = Calendar.getInstance();
		fourDaysFromNow.set(Calendar.DAY_OF_YEAR, fourDaysFromNow.get(Calendar.DAY_OF_YEAR)+4);
		fourDaysFromNow.set(Calendar.HOUR_OF_DAY, 14);
		fourDaysFromNow.set(Calendar.MINUTE, 0);
		fourDaysFromNow.set(Calendar.SECOND, 0);
		
		assertEquals(deliveryDate, fourDaysFromNow.getTime());
		
	}

	@Test
	public void testGetSupplier() {
		
		OrderShoppingCartCard shoppingCartCard = createShoppingCartCard();
		
		// add to cart method will store supplier in shopping cart card
		Bicycle bike = new MountainBike(21, "Giant STP3", "Black", 18, 26, "Alloy", 280, 500);
		Supplier supplier = new Supplier(12345, "Bike Wholesale shop", "Dublin", "0861234567", 
				"bikewholesale@yahoo.com", new ArrayList<Bicycle>());
		shoppingCartCard.addToCart("Mountain Bike", bike, 3, supplier);
		
		// get supplier from class
		Supplier newSupplier = shoppingCartCard.getSupplier();
		
		// verify new supplier
		assertNotNull(newSupplier);
		assertEquals("Bike Wholesale shop", newSupplier.getName());
		
	}

	@Test
	public void testIsShoppingCartEmpty() {
		OrderShoppingCartCard shoppingCartCard = createShoppingCartCard();
		
		assertEquals(true, shoppingCartCard.isShoppingCartEmpty());
		
		// add to cart and test again
		Bicycle bike = new MountainBike(21, "Giant STP3", "Black", 18, 26, "Alloy", 280, 500);
		Supplier supplier = new Supplier(12345, "Bike Wholesale shop", "Dublin", "0861234567", 
				"bikewholesale@yahoo.com", new ArrayList<Bicycle>());
		shoppingCartCard.addToCart("Mountain Bike", bike, 3, supplier);
		
		assertEquals(false, shoppingCartCard.isShoppingCartEmpty());
	}
	
	private OrderShoppingCartCard createShoppingCartCard() {
		
		Database database = new Database();
		Employee employee = new Manager(12345, "George Best", "0877654321", "password", 35, "IT","HR");
		ProfitAndLossCard profitAndLoss = new ProfitAndLossCard(database);
		OrdersCardLayout cardLayout = new OrdersCardLayout(database, employee, profitAndLoss);
		
		return new OrderShoppingCartCard(database, employee, cardLayout, new JButton(),
				new OrderFromSupplierCard(database, employee, cardLayout, profitAndLoss), profitAndLoss );
	}

}
