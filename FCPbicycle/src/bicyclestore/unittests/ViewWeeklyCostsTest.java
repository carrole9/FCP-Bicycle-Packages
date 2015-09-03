package bicyclestore.unittests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;
import bicyclestore.cardlayouts.ordercardlayouts.ViewWeeklyCosts;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.ShoppingBasket;

public class ViewWeeklyCostsTest {
	
	private static final double DELTA = 1e-15; // value for double test precision

	@Test
	public void testGetWeeklyAverage() {
		Database db = createAndFillDB();
		ViewWeeklyCosts viewCosts = new ViewWeeklyCosts(db, new JButton());
		
		assertEquals((double)13437/2, viewCosts.getWeeklyAverage(), DELTA);
	}

	@Test
	public void testGetLastSixWeeksAverage() {
		Database db = createAndFillDB();
		ViewWeeklyCosts viewCosts = new ViewWeeklyCosts(db, new JButton());
		
		assertEquals((double)13437/6, viewCosts.getLastSixWeeksAverage(), DELTA);
	}

	@Test
	public void testCalculateWeeklyCosts() {
		Database db = createAndFillDB();
		ViewWeeklyCosts viewCosts = new ViewWeeklyCosts(db, new JButton());
		
		double[] weeklyCosts = viewCosts.calculateWeeklyCosts();
		Double[] testCase = { 0.0, 0.0, 3779.0, 700.0, 4479.0, 4479.0};
		Double[] objectWeeklyCosts = new Double[weeklyCosts.length];
		for(int i = 0; i < weeklyCosts.length; i++) {
			objectWeeklyCosts[i] = weeklyCosts[i];
		}
		assertArrayEquals(testCase, objectWeeklyCosts);
	}

	private Database createAndFillDB() {
		Database db = new Database();

		// create baskets to pass to orders
		ShoppingBasket basket1 = new ShoppingBasket();
		ShoppingBasket basket2 = new ShoppingBasket();
		basket1.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket2.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket2.add(new RoadBike(22, "Lapierre Aircode 300", "Black", 58, 700, "AIRCODE CARBON", 2949, 1850));
		basket2.add(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));

		// create two current orders
		db.addPurhasingTransaction(new PurchasingTransaction(10014, db.getEmployee(10004), db.getSupplier(101),
				basket1.getTotalCostValue(), "Account", new Date(), basket1, new Date()));
		db.addPurhasingTransaction(new PurchasingTransaction(10015, db.getEmployee(10002), db.getSupplier(103),
				basket2.getTotalCostValue(), "Account", new Date(), basket2, new Date()));
				// new orders total cost = 4479

		// create three old orders
		Calendar lastWeek = Calendar.getInstance();
		lastWeek.add(Calendar.WEEK_OF_YEAR, -1);
		db.addPurhasingTransaction(new PurchasingTransaction(10001, db.getEmployee(10002), db.getSupplier(102),
				basket1.getTotalCostValue(), "Account", lastWeek.getTime(), basket1, new Date()));

		db.addPurhasingTransaction(new PurchasingTransaction(10002, db.getEmployee(10004), db.getSupplier(101),
				basket2.getTotalCostValue(), "Account", lastWeek.getTime(), basket2, new Date()));

		Calendar twoWeeksAgo = Calendar.getInstance();
		twoWeeksAgo.add(Calendar.WEEK_OF_YEAR, -2);
		db.addPurhasingTransaction(new PurchasingTransaction(10003, db.getEmployee(10004), db.getSupplier(103),
				basket1.getTotalCostValue(), "Account", twoWeeksAgo.getTime(), basket1, new Date()));
		
		
		Calendar threeWeeksAgo = Calendar.getInstance();
		threeWeeksAgo.add(Calendar.WEEK_OF_YEAR, -3);
		db.addPurhasingTransaction(new PurchasingTransaction(10004, db.getEmployee(10004), db.getSupplier(103),
				basket2.getTotalCostValue(), "Account", threeWeeksAgo.getTime(), basket2, new Date()));
		
		// old orders total cost = 5179

		// total orders = 9658

		return db;
	}

}
