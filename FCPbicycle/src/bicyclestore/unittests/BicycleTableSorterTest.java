package bicyclestore.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.Utilities;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;

public class BicycleTableSorterTest {

	@Test
	public void testAddBicyclesFromDB() {
		Database database = new Database();
		database.addBicycle(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", 300, 469.95));
		database.addBicycle(new Cruiser("Black", "Low Cruiser", 21, 20, "Alloy", 255, 369.95));
		database.addBicycle(new BMX("Red", "Wethepeople Elite", 22, 20, "Aluminium", 340, 569.95));
		
		ArrayList<Bicycle> newArrayList;
		newArrayList = database.getBicycles();
		
		assertEquals("Black",newArrayList.get(0).getColour());
		assertEquals("Low Cruiser",newArrayList.get(1).getModel());
		assertEquals(20,newArrayList.get(2).getWheelSize());
		
		
	}

	@Test
	public void testViewBicyclesTable() {
		Database database = new Database();
		database.addBicycle(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", 300, 469.95));
		
		for (Bicycle bicycle : database.getBicycles()) {
			
			
			Object [] row = {Utilities.splitCamelCase(bicycle.getClass().getSimpleName()), bicycle.getModel()
					,bicycle.getColour(),bicycle.getFrameSize() + "",bicycle.getWheelSize() + "", bicycle.getFrameComposition()
					,bicycle.getCostPrice(),bicycle.getSalePrice()};

			assertEquals("Black",database.getBicycles().get(0).getColour());
		
			

	
	
		}
	}

	

}
