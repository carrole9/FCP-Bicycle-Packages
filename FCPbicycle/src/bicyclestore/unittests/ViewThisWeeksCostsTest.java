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
import bicyclestore.cardlayouts.ordercardlayouts.ViewThisWeeksCosts;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.ShoppingBasket;

public class ViewThisWeeksCostsTest {
	
	private static final double DELTA = 1e-15; // value for double test precision

	@Test
	public void testGetThisWeeksOrdersCost() {
		Database db = createAndFillDB();
		ViewThisWeeksCosts viewCosts = new ViewThisWeeksCosts(db, new JButton());
		
		assertEquals(4479, viewCosts.getThisWeeksOrdersCost(), DELTA);
	}

	@Test
	public void testGetNoOrdersThisWeek() {
		Database db = createAndFillDB();
		ViewThisWeeksCosts viewCosts = new ViewThisWeeksCosts(db, new JButton());
		
		assertEquals(2, viewCosts.getNoOrdersThisWeek());
	}

	@Test
	public void testGetPercentEarlierCost() {
		Database db = createAndFillDB();
		ViewThisWeeksCosts viewCosts = new ViewThisWeeksCosts(db, new JButton());
		
		assertEquals(46.376061296334644854007040795196, viewCosts.getPercentCostThisWeek(), DELTA);
	}

	@Test
	public void testGetPercentCostThisWeek() {
		Database db = createAndFillDB();
		ViewThisWeeksCosts viewCosts = new ViewThisWeeksCosts(db, new JButton());
		
		assertEquals(53.623938703665355145992959204804, viewCosts.getPercentEarlierCost(), DELTA);
	}
	
	private Database createAndFillDB() {
		Database db = new Database();
		
		// create baskets to pass to orders
		ShoppingBasket basket1 = new ShoppingBasket();
		ShoppingBasket basket2= new ShoppingBasket();
		basket1.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket2.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket2.add(new RoadBike(22, "Lapierre Aircode 300", "Black", 58, 700, "AIRCODE CARBON", 2949, 1850));
		basket2.add(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));
		
		// create two current orders
		db.addPurhasingTransaction(new PurchasingTransaction(10014, db.getEmployee(10004),
				db.getSupplier(101), basket1.getTotalCostValue(), "Account", new Date(), 
				basket1, new Date()));
		db.addPurhasingTransaction(new PurchasingTransaction(10015, db.getEmployee(10002),
				db.getSupplier(103), basket2.getTotalCostValue(), "Account", new Date(),
				basket2, new Date()));
		// new orders total cost = 4479

		// create three old orders
		// order from 60 days ago
		Calendar sixtyDaysAgo = Calendar.getInstance();
		sixtyDaysAgo.add(Calendar.DAY_OF_YEAR, -60);
		db.addPurhasingTransaction(new PurchasingTransaction(10001, db.getEmployee(10002),
				db.getSupplier(102), basket1.getTotalCostValue(), "Account", sixtyDaysAgo.getTime(), basket1,
				new Date()));

		// order from 45 days ago
		Calendar fortyFiveDaysAgo = Calendar.getInstance();
		fortyFiveDaysAgo.add(Calendar.DAY_OF_YEAR, -45);
		db.addPurhasingTransaction(new PurchasingTransaction(10002, db.getEmployee(10004),
				db.getSupplier(101), basket2.getTotalCostValue(), "Account", fortyFiveDaysAgo.getTime(), basket2,
				new Date()));

		// order from 30 days ago
		Calendar thirtyDaysAgo = Calendar.getInstance();
		thirtyDaysAgo.add(Calendar.DAY_OF_YEAR, -30);
		db.addPurhasingTransaction(new PurchasingTransaction(10003, db.getEmployee(10004),
				db.getSupplier(103), basket1.getTotalCostValue(), "Account", thirtyDaysAgo.getTime(), basket1,
				new Date()));
		// old orders total cost = 5179
		
		// total orders = 9658
		
		return db;
	}

}
