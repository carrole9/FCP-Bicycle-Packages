package bicyclestore.unittests;


import static org.junit.Assert.*;

import java.util.ArrayList;

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
		
		assertEquals(3, basket.getQuantity());
	}

	@Test
	public void testCreatePurchasingTransaction() {
		
	}

	@Test
	public void testGetDeliveryDate() {
		
	}

	@Test
	public void testGetSupplier() {
		
	}

	@Test
	public void testIsShoppingCartEmpty() {
		
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
