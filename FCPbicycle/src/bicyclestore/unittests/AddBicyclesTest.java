package bicyclestore.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.cardlayouts.bicyclelayout.AddBicycles;
import bicyclestore.cardlayouts.bicyclelayout.BicycleCardLayout;

public class AddBicyclesTest {
	

	@Test
	public void testCheckFieldsComplete() {
		Database database = new Database();
		
		
		
	
		
		database.addBicycle(new BMX("Black", "Wethepeople Justice", 21, 20, "Aluminium", 300, 469.95));
		database.addBicycle(new Cruiser("Green", "Type 2", 22, 20, "Alloy", 200, 349.95));
		assertEquals("Black",database.getBicycles().get(0).getColour());
		assertEquals(22,database.getBicycles().get(1).getFrameSize());
	}

	@Test
	public void testClearTextFields() {
		fail("Not yet implemented");
	}

}
