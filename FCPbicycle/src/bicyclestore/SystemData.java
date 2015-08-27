package bicyclestore;

import java.util.Calendar;
import java.util.Date;

import bicyclestore.bikes.BMX;
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
		database.addSupplier(new Supplier(101, "ABC Bicycle Supplies", "Dublin", "BMX","01 1234567", "abc.bike.supplies@yahoo.com"));
		database.addSupplier(new Supplier(102, "Road Bike Wholesale", "Cork", "Road bikes","021 6543212", "road.bike.wholesales@gmail.com"));
		database.addSupplier(new Supplier(103, "The Mountain Bike Shack", "Wexfor", "Mountain bikes","053 6789101", "mountainbikes@hotmail.com"));
		database.addSupplier(new Supplier(104, "City Bike Warehouse", "Wicklow", "Hybrids","0404 123456", "citybikes@yahoo.com"));
	}
	
	private void createBicycles() {
		// create bmx bikes
		database.addBicycle(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", database, 300, 469.95));
		database.addBicycle(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", database, 180, 299.99));
		database.addBicycle(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300));
		
		// create Mountain bikes
		database.addBicycle(new MountainBike(27, "Kona Blast","Orange", 27, 27, "Aluminium", database, 550, 719.99));
		database.addBicycle(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", database, 400, 565.95));
		
		// create Road bikes
		database.addBicycle(new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649));
		
		// create Hybrid bikes
		database.addBicycle(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", database, 300, 479.95));
		
		// create Cruiser bikes
		database.addBicycle(new Cruiser("Red", " Schwinn Corvette", 26, 26, "Steel", database, 450, 749.99));
		
		// create Motorised bikes
		database.addBicycle(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", database, 480, 699.99));
	}
	
	private void createOrders() {
		
		
		// create shopping baskets to add to transactions
		ShoppingBasket basket1 = new ShoppingBasket();
		ShoppingBasket basket2 = new ShoppingBasket();
		ShoppingBasket basket3 = new ShoppingBasket();
		basket1.add(new MountainBike(27, "VooDoo Aizan 29er", "Black", 29, 29, "6061 Aluminium", database, 400, 565.95));
		basket1.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", database, 300, 479.95));
		basket1.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", database, 480, 699.99));
		basket2.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", database, 300, 479.95));
		basket2.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", database, 180, 299.99));
		basket3.add(new MotorisedBike("EBCO UCL30 Electric", "Black", 21, 26, "Aluminium", database, 480, 699.99));
		
		// create old orders
		
		// order from 7 days ago
		Date sevenDaysAgo = new Date(System.currentTimeMillis() - 7*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10001, database.getEmployee(10004),
				database.getSupplier(101), basket1.getTotalCostValue(), "Account", sevenDaysAgo,
				basket1, getDeliveryDate(sevenDaysAgo, 4)));
		
		// order from 6 days ago
		Date sixDaysAgo = new Date(System.currentTimeMillis() - 6*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10002, database.getEmployee(10002),
				database.getSupplier(102), basket2.getTotalCostValue(), "Account", sixDaysAgo, 
				basket2, getDeliveryDate(sixDaysAgo, 4)));
		
		// order from 4 days ago
		Date fourDaysAgo = new Date(System.currentTimeMillis() - 4*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10003, database.getEmployee(10004),
				database.getSupplier(103), basket3.getTotalCostValue(), "Account", fourDaysAgo, 
				basket3, getDeliveryDate(fourDaysAgo, 4)));
		
		// order from 3 days ago
		Date threeDaysAgo = new Date(System.currentTimeMillis() - 3*24*3600*1000);
		database.addPurhasingTransaction(new PurchasingTransaction(10004, database.getEmployee(10002),
				database.getSupplier(104), basket2.getTotalCostValue(), "Account", threeDaysAgo, 
				basket2, getDeliveryDate(threeDaysAgo, 4)));
		
		// create orders for current date
		database.addPurhasingTransaction(new PurchasingTransaction(10005, database.getEmployee(10002),
				database.getSupplier(102), basket1.getTotalCostValue(), "Account", new Date(),
				basket1, getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10006, database.getEmployee(10004),
				database.getSupplier(104), basket2.getTotalCostValue(), "Account", new Date(), 
				basket2,getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10007, database.getEmployee(10004),
				database.getSupplier(101), basket3.getTotalCostValue(), "Account", new Date(), 
				basket3, getDeliveryDate(new Date(), 4)));
		database.addPurhasingTransaction(new PurchasingTransaction(10008, database.getEmployee(10002),
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
		ShoppingBasket basket = new ShoppingBasket();
		basket.add(new Hybrid(24, "Carrera Subway 1", "Black", 22, 27, "Alloy", database, 300, 479.95));
		basket.add(new BMX("Blue", "Mongoose Scan R90", 21, 20, "Aluminium", database, 180, 299.99));
		
		Date yesterday = new Date(System.currentTimeMillis() - 24*60*60*1000);
		SalesTransaction firstsale= new SalesTransaction(111, database.getEmployee(10002),database.getCustomer("Tom Smith"),100.99, "Cash", yesterday, basket);
		database.addSalesTransaction(firstsale);
		
	}

}
