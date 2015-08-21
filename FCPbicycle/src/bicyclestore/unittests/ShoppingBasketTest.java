package bicyclestore.unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.RoadBike;
import bicyclestore.transaction.ShoppingBasket;

public class ShoppingBasketTest {
	
	private Database database = new Database();
	
	private static final double DELTA = 1e-15; // value for double test precision

	@Test
	public void testRemoveInt() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.add(new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649));
		basket.add(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300));
		
		assertEquals(2, basket.getQuantity());
		basket.remove(0);
		assertEquals(1,basket.getQuantity());
	}

	@Test
	public void testRemoveBicycle() {
		ShoppingBasket basket = new ShoppingBasket();
		Bicycle bike1 = new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649);
		Bicycle bike2 = new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300);
		basket.add(bike1);
		basket.add(bike2);
		
		assertEquals(2, basket.getQuantity());
		basket.remove(bike2);
		assertEquals(1,basket.getQuantity());
	}

	@Test
	public void testRemoveAll() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.add(new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649));
		basket.add(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300));
		
		assertEquals(2, basket.getQuantity());
		basket.removeAll();
		assertEquals(0,basket.getQuantity());
	}

	@Test
	public void testGetTotalCostValue() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.add(new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649));
		basket.add(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300));
		
		assertEquals(650, basket.getTotalCostValue(), DELTA);
		
		basket.remove(0);
		assertEquals(170, basket.getTotalCostValue(), DELTA);
		
		basket.removeAll();
	}

	@Test
	public void testGetTotalSaleValue() {
		ShoppingBasket basket = new ShoppingBasket();
		basket.add(new RoadBike(18, "Boardman Road Comp", "Grey", 53, 700, "Alloy", database, 480, 649));
		basket.add(new BMX("Black", "Giant GFR", 20, 20, "High Tensile Steel", database, 170, 300));
		
		assertEquals(949, basket.getTotalSaleValue(), DELTA);
		
		basket.remove(1);
		assertEquals(649, basket.getTotalSaleValue(), DELTA);
		
		basket.removeAll();
		assertEquals(0, basket.getTotalSaleValue(), DELTA);
	}

}
