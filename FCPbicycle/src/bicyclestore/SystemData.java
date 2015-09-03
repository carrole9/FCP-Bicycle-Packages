package bicyclestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;
import bicyclestore.customers.Customer;
import bicyclestore.staff.Manager;
import bicyclestore.staff.SalesAssistant;
import bicyclestore.suppliers.Supplier;
import bicyclestore.transaction.PurchasingTransaction;
import bicyclestore.transaction.SalesTransaction;
import bicyclestore.transaction.ShoppingBasket;


public class SystemData {
	
	private Database database;

	public SystemData(Database database) {
		this.database = database;
	}
	
	public void fillDatabase() {
		createEmployeeAccounts();
		createCustomerAccounts();
		createSupplierAccounts();
		createBicycles();
		createOrders();
		createSalesTransaction();
	}

	// hard code some employee accounts into database
	private void createEmployeeAccounts() {
		database.addEmployee(new SalesAssistant(10001, "Fred Flintstone", "0861234567", "password",8.65, 200004));
		database.addEmployee(new Manager(10002, "Homer Simpson", "0869876543", "password",15.99, "Dublin", "IT"));
		database.addEmployee(new SalesAssistant(10003, "Peter Griffin", "0875678987", "password",8.65, 10004));
		database.addEmployee(new Manager(10004, "barack obama", "0851239876", "password",15.99,"Cork", "Sales"));
		database.addEmployee(new SalesAssistant(10006, "Bill Gates", "0835432198", "password",8.65, 10004));
	}

	// hard code some customers accounts to the database
	private void createCustomerAccounts() {
		database.addCustomer(new Customer(1, "Tom Smith", "Bray", "0861234567","tomsmith@gmail.com"));
		database.addCustomer(new Customer(2, "John Doe", "Ringsend", "0877654321", "johndoe@gmail.com"));
		database.addCustomer(new Customer(3, "Patrick Dunne", "Sandyford", "0836543219", "paddy.dunne@yahoo.com"));
		database.addCustomer(new Customer(4, "Freddy Mercury", "Dublin", "0897654321", "freddy.mercury@hotmail.com"));
	}

	private void createSupplierAccounts() {
		// create catalogs of products for suppliers
		ArrayList<Bicycle> catalog1 = new ArrayList<Bicycle>();
		ArrayList<Bicycle> catalog2 = new ArrayList<Bicycle>();
		ArrayList<Bicycle> catalog3 = new ArrayList<Bicycle>();
		ArrayList<Bicycle> catalog4 = new ArrayList<Bicycle>();
		catalog1.add(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", 300, 469.95));
		catalog1.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", 180, 299.99));
		catalog1.add(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", 170, 300));
		catalog1.add(new BMX("Blue", "Social Storm", 21, 20, "CrMo", 400, 630));
		catalog1.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		catalog1.add(new MotorisedBike("A2B Hybrid 24", "White", 26, 26, "Aluminium", 2200, 3049));
		catalog1.add(new MotorisedBike("Neo Street Unisex", "Black", 26, 26, "Alloy", 1500, 2249));
		catalog1.add(new MotorisedBike("BH Evo City", "White", 28, 28, "Aluminium", 1800, 2650));
		catalog1.add(new Cruiser("Red", " Schwinn Corvette", 26, 26, "Steel", 450, 749.99));
		catalog1.add(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel",120, 200));
		catalog1.add(new Cruiser("Green", "Firmstrong Bella", 26, 26, "Steel", 180, 300));
		catalog1.add(new Cruiser("Green", "DiamondBack Drifter", 26, 26, "Steel", 100, 180));
		
		catalog2.add(new RoadBike(18, "Boardman Road Comp", "Grey", 24, 23, "Alloy", 480, 649));
		catalog2.add(new RoadBike(11, "Cannondale CAAD10", "Black", 25, 22, "SmartFormed 6069 Alloy", 1350, 1845));
		catalog2.add(new RoadBike(20, "Giant Anyroad 1", "Black", 25, 22, "ALUXX-Grade Aluminum", 1249, 800));
		catalog2.add(new RoadBike(22, "Lapierre Aircode 300", "Black", 21, 25, "AIRCODE CARBON", 2949, 1850));
		
		catalog3.add(new MountainBike(27, "Kona Blast","Orange", 27, 27, "Aluminium",550, 719.99));
		catalog3.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		catalog3.add(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));
		catalog3.add(new MountainBike(30, "Cube LTD Race", "Blue", 20, 29, "Aluminium Lite", 750, 1149));
		catalog3.add(new MountainBike(20, "Boardman Pro Carbon", "Carbon", 21, 27, "Super light carbon", 1100, 1649));
		
		catalog4.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		catalog4.add(new Hybrid(18, "Lapierre Sit and Go", "White", 24, 26, "ALLOY 6061", 280, 449));
		catalog4.add(new Hybrid(21, "Giant Escape 3", "Black/Blue", 23, 27, "ALUXX-Grade Aluminum",200, 375));
		catalog4.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		
		database.addSupplier(new Supplier(101, "ABC Bicycle Supplies", "Dublin","01 1234567", "abc.bike.supplies@yahoo.com", catalog1));
		database.addSupplier(new Supplier(102, "Road Bike Wholesale", "Cork","021 6543212", "road.bike.wholesales@gmail.com", catalog2));
		database.addSupplier(new Supplier(103, "The Mountain Bike Shack", "Wexfor","053 6789101", "mountainbikes@hotmail.com", catalog3));
		database.addSupplier(new Supplier(104, "City Bike Warehouse", "Wicklow","0404 123456", "citybikes@yahoo.com", catalog4));
	}
	
	private void createBicycles() {
		// create bmx bikes
		database.addBicycle(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", 300, 469.95));
		database.addBicycle(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", 180, 299.99));
		database.addBicycle(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", 170, 300));
		database.addBicycle(new BMX("Blue", "Social Storm", 21, 20, "CrMo", 400, 630));
		
		// create Mountain bikes
		database.addBicycle(new MountainBike(27, "Kona Blast","Orange", 27, 27, "Aluminium", 550, 719.99));
		database.addBicycle(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		database.addBicycle(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));
		database.addBicycle(new MountainBike(30, "Cube LTD Race", "Blue", 20, 29, "Aluminium Lite", 750, 1149));
		database.addBicycle(new MountainBike(20, "Boardman Pro Carbon", "Carbon", 21, 27, "Super light carbon", 1100, 1649));
		
		// create Road bikes
		database.addBicycle(new RoadBike(18, "Boardman Road Comp", "Grey", 24, 29, "Alloy", 480, 649));
		database.addBicycle(new RoadBike(11, "Cannondale CAAD10", "Black", 24, 23, "SmartFormed 6069 Alloy", 1350, 1845));
		database.addBicycle(new RoadBike(20, "Giant Anyroad 1", "Black", 20, 21, "ALUXX-Grade Aluminum", 1249, 800));
		database.addBicycle(new RoadBike(22, "Lapierre Aircode 300", "Black", 20, 24, "AIRCODE CARBON", 2949, 1850));
		database.addBicycle(new RoadBike(21, "Raleigh", "White", 54, 700, "Steel", 500, 999.99));
		
		// create Hybrid bikes
		database.addBicycle(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		database.addBicycle(new Hybrid(18, "Lapierre Sit and Go", "White", 24, 26, "ALLOY 6061", 280, 449));
		database.addBicycle(new Hybrid(21, "Giant Escape 3", "Black/Blue", 22, 27, "ALUXX-Grade Aluminum", 200, 375));
		database.addBicycle(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		
		// create Cruiser bikes
		database.addBicycle(new Cruiser("Red", " Schwinn Corvette", 26, 26, "Steel", 450, 749.99));
		database.addBicycle(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel", 120, 200));
		database.addBicycle(new Cruiser("Green", "Firmstrong Bella", 26, 26, "Steel", 180, 300));
		database.addBicycle(new Cruiser("Green", "DiamondBack Drifter", 26, 26, "Steel", 100, 180));
		
		// create Motorised bikes
		database.addBicycle(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		database.addBicycle(new MotorisedBike("A2B Hybrid 24", "White", 26, 26, "Aluminium",  2200, 3049));
		database.addBicycle(new MotorisedBike("Neo Street Unisex", "Black", 26, 26, "Alloy",  1500, 2249));
		database.addBicycle(new MotorisedBike("BH Evo City", "White", 22, 25, "Aluminium", 1800, 2650));
	}
	
	private void createOrders() {
		
		
		// create shopping baskets to add to transactions
		ShoppingBasket basket1 = new ShoppingBasket();
		ShoppingBasket basket2 = new ShoppingBasket();
		ShoppingBasket basket3 = new ShoppingBasket();
		ShoppingBasket basket4 = new ShoppingBasket();
		basket1.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", 400, 565.95));
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket1.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket1.add(new RoadBike(22, "Lapierre Aircode 300", "Black", 21, 26, "AIRCODE CARBON", 2949, 1850));
		basket1.add(new MountainBike(24, "Cannondale Tango 7", "Black", 29, 27, "Optimized 6061 alloy", 350, 640));
		basket1.add(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel", 120, 200));
		basket2.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket2.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", 180, 299.99));
		basket3.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket3.add(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel", 120, 200));
		basket4.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket4.add(new MountainBike(30, "Cube LTD Race", "Blue", 20, 29, "Aluminium Lite", 750, 1149));
		basket4.add(new MotorisedBike("BH Evo City", "White", 21, 27, "Aluminium", 1800, 2650));
		basket4.add(new Cruiser("Green", "Firmstrong Bella", 26, 26, "Steel", 180, 300));
		basket4.add(new BMX("Blue", "Social Storm", 21, 20, "CrMo", 400, 630));
		
		// create old orders

		// order from 60 days ago
		Calendar sixtyDaysAgo = Calendar.getInstance();
		sixtyDaysAgo.add(Calendar.DAY_OF_YEAR, -60);
		database.addPurhasingTransaction(new PurchasingTransaction(10001, database.getEmployee(10002),
				database.getSupplier(102), basket1.getTotalCostValue(), "Account", sixtyDaysAgo.getTime(), basket1,
				getDeliveryDate(sixtyDaysAgo.getTime(), 4)));

		// order from 45 days ago
		Calendar fortyFiveDaysAgo = Calendar.getInstance();
		fortyFiveDaysAgo.add(Calendar.DAY_OF_YEAR, -45);
		database.addPurhasingTransaction(new PurchasingTransaction(10002, database.getEmployee(10004),
				database.getSupplier(101), basket3.getTotalCostValue(), "Account", fortyFiveDaysAgo.getTime(), basket3,
				getDeliveryDate(fortyFiveDaysAgo.getTime(), 4)));

		// order from 30 days ago
		Calendar thirtyDaysAgo = Calendar.getInstance();
		thirtyDaysAgo.add(Calendar.DAY_OF_YEAR, -30);
		database.addPurhasingTransaction(new PurchasingTransaction(10003, database.getEmployee(10004),
				database.getSupplier(103), basket4.getTotalCostValue(), "Account", thirtyDaysAgo.getTime(), basket4,
				getDeliveryDate(thirtyDaysAgo.getTime(), 4)));

		// order from 25 days ago
		Calendar twentyFiveDaysAgo = Calendar.getInstance();
		twentyFiveDaysAgo.add(Calendar.DAY_OF_YEAR, -25);
		database.addPurhasingTransaction(new PurchasingTransaction(10004, database.getEmployee(10002),
				database.getSupplier(101), basket2.getTotalCostValue(), "Account", twentyFiveDaysAgo.getTime(), basket2,
				getDeliveryDate(twentyFiveDaysAgo.getTime(), 4)));

		// order from 20 days ago
		Date twentyDaysAgo = new Date(System.currentTimeMillis() - 20 * 24 * 3600 * 1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10005, database.getEmployee(10002),
				database.getSupplier(102), basket3.getTotalCostValue(), "Account", twentyDaysAgo, basket3,
				getDeliveryDate(twentyDaysAgo, 4)));

		// order from 15 days ago
		Date fifteenDaysAgo = new Date(System.currentTimeMillis() - 15 * 24 * 3600 * 1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10006, database.getEmployee(10002),
				database.getSupplier(104), basket4.getTotalCostValue(), "Account", fifteenDaysAgo, basket4,
				getDeliveryDate(fifteenDaysAgo, 4)));

		// order from 10 days ago
		Date tenDaysAgo = new Date(System.currentTimeMillis() - 10*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10007, database.getEmployee(10004),
				database.getSupplier(104), basket1.getTotalCostValue(), "Account", tenDaysAgo,
				basket1, getDeliveryDate(tenDaysAgo, 4)));
		
		// order from 7 days ago
		Date sevenDaysAgo = new Date(System.currentTimeMillis() - 7*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10008, database.getEmployee(10004),
				database.getSupplier(101), basket3.getTotalCostValue(), "Account", sevenDaysAgo,
				basket3, getDeliveryDate(sevenDaysAgo, 4)));
		
		// order from 6 days ago
		Date sixDaysAgo = new Date(System.currentTimeMillis() - 6*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10009, database.getEmployee(10002),
				database.getSupplier(102), basket2.getTotalCostValue(), "Account", sixDaysAgo, 
				basket2, getDeliveryDate(sixDaysAgo, 4)));
		
		// order from 4 days ago
		Date fourDaysAgo = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10010, database.getEmployee(10004),
				database.getSupplier(103), basket3.getTotalCostValue(), "Account", fourDaysAgo, 
				basket3, getDeliveryDate(fourDaysAgo, 4)));
		
		// order from 3 days ago
		Date threeDaysAgo = new Date(System.currentTimeMillis() - 3*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10011, database.getEmployee(10002),
				database.getSupplier(104), basket2.getTotalCostValue(), "Account", threeDaysAgo, 
				basket2, getDeliveryDate(threeDaysAgo, 4)));
		
		// create orders for current date
		database.addPurhasingTransaction(new PurchasingTransaction(10012, database.getEmployee(10002),
				database.getSupplier(102), basket1.getTotalCostValue(), "Account", new Date(),
				basket1, getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10013, database.getEmployee(10004),
				database.getSupplier(104), basket2.getTotalCostValue(), "Account", new Date(), 
				basket2,getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10014, database.getEmployee(10004),
				database.getSupplier(101), basket3.getTotalCostValue(), "Account", new Date(), 
				basket3, getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10015, database.getEmployee(10002),
				database.getSupplier(103), basket2.getTotalCostValue(), "Account", new Date(),
				basket2, getDeliveryDate(new Date(), 4)));
	}
	
	private Date getDeliveryDate(Date orderDate, int deliveryDays) {
		Calendar c = Calendar.getInstance();
		c.setTime(orderDate);
		// add number of delivery days and set time to 14:00:00
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)+deliveryDays);
		c.set(Calendar.HOUR_OF_DAY, 14);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	private void createSalesTransaction() {
		ShoppingBasket basket1 = new ShoppingBasket();
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket1.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", 180, 299.99));
		
		ShoppingBasket basket2 = new ShoppingBasket();
		basket2.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket2.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", 180, 299.99));
		
		ShoppingBasket basket3 = new ShoppingBasket();
		basket3.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", 480, 699.99));
		basket3.add(new Cruiser("Blue", " Kent Bay Breeze", 15, 26, "Steel", 120, 200));
		
		ShoppingBasket basket4 = new ShoppingBasket();
		basket4.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", 300, 479.95));
		basket4.add(new MountainBike(30, "Cube LTD Race", "Blue", 20, 29, "Aluminium Lite", 750, 1149));
		basket4.add(new MotorisedBike("BH Evo City", "White", 28, 700, "Aluminium", 1800, 2650));
		basket4.add(new Cruiser("Green", "Firmstrong Bella", 26, 26, "Steel", 180, 300));
		basket4.add(new BMX("Blue", "Social Storm", 21, 20, "CrMo", 400, 630));
		
		Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
		SalesTransaction firstsale= new SalesTransaction(111, database.getEmployee(10002),database.getCustomer("Tom Smith"),100.99, "Cash", yesterday, basket1);
		database.addSalesTransaction(firstsale);
		
		Date threeDaysAgo = new Date(System.currentTimeMillis() - 3*24*60*60*1000);
		SalesTransaction secondSale= new SalesTransaction(112, database.getEmployee(10003),database.getCustomer("John Doe"), basket2.getTotalSaleValue(), "Cash", threeDaysAgo, basket2);
		database.addSalesTransaction(secondSale);
		
		Calendar oneWeekAgo = Calendar.getInstance();
		oneWeekAgo.set(Calendar.WEEK_OF_YEAR, oneWeekAgo.get(Calendar.WEEK_OF_YEAR)-1);
		database.addSalesTransaction(new SalesTransaction(113, database.getEmployee(10001),
				database.getCustomer("Patrick Dunne"), basket3.getTotalSaleValue(), "Cash", oneWeekAgo.getTime(), basket3));
		
		Calendar twoWeeksAgo = Calendar.getInstance();
		oneWeekAgo.set(Calendar.WEEK_OF_YEAR, twoWeeksAgo.get(Calendar.WEEK_OF_YEAR)-2);
		database.addSalesTransaction(new SalesTransaction(114, database.getEmployee(10001),
				database.getCustomer("Freddy Mercury"), basket4.getTotalSaleValue(), "Cash", twoWeeksAgo.getTime(), basket4));
		
		Calendar threeWeeksAgo = Calendar.getInstance();
		oneWeekAgo.set(Calendar.WEEK_OF_YEAR, threeWeeksAgo.get(Calendar.WEEK_OF_YEAR)-3);
		database.addSalesTransaction(new SalesTransaction(114, database.getEmployee(10001),
				database.getCustomer("Tom Smith"), basket2.getTotalSaleValue(), "Cash", threeWeeksAgo.getTime(), basket2));
		
	}

}
