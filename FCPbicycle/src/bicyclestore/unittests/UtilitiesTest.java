package bicyclestore.unittests;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.Utilities;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.ShoppingBasket;

public class UtilitiesTest {
	
	private static final double DELTA = 1e-15; // value for double test precision

	@Test
	public void testSplitCamelCase() {
		String camelCaseStr = "RoadBikeShop";
		assertEquals("Road Bike Shop", Utilities.splitCamelCase(camelCaseStr));
		
		camelCaseStr = "SheSellsSeaShellsByTheSeaShore";
		assertEquals("She Sells Sea Shells By The Sea Shore", Utilities.splitCamelCase(camelCaseStr));
	}

	@Test
	public void testIsDateInCurrentWeek() {
		Calendar now = Calendar.getInstance();

		assertEquals(true, Utilities.isDateInCurrentWeek(now.getTime()));
		
		now.set(Calendar.WEEK_OF_YEAR, now.get(Calendar.WEEK_OF_YEAR)-2); // subtract 2 weeks
		assertEquals(false, Utilities.isDateInCurrentWeek(now.getTime()));
	}

	@Test
	public void testGetTotalCostOfOrders() {
		Database database = new Database();
		
		ShoppingBasket basket1 = new ShoppingBasket();
		basket1.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket1.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket1.add(new RoadBike(22, "Lapierre Aircode 300", "Black", 58, 700, "AIRCODE CARBON", 2949, 1850));
		basket1.add(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));
		basket1.add(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel", 120, 200));
		
		database.addPurhasingTransaction(new PurchasingTransaction(10012, database.getEmployee(10002),
				database.getSupplier(102), basket1.getTotalCostValue(), "Account", new Date(),
				basket1, new Date()));
		
		assertEquals(4599, Utilities.getTotalCostOfOrders(database), DELTA);
	}

}
