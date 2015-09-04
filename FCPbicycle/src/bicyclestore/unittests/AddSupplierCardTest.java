package bicyclestore.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bicyclestore.Database;
import bicyclestore.bikes.Bicycle;
import bicyclestore.suppliers.Supplier;
import bicyclestore.cardlayouts.suppliercardlayouts.AddSupplierCard;
import bicyclestore.cardlayouts.suppliercardlayouts.SuppliersCardLayout;

public class AddSupplierCardTest {

	@Test
	public void testAddSupplier() {
		Database database = new Database();
		SuppliersCardLayout cardLayout = new SuppliersCardLayout(database);
		AddSupplierCard addSupplierCard = new AddSupplierCard(database, cardLayout);
		ArrayList <Bicycle> catalog = new ArrayList<Bicycle>();
		
		int supplierID = 12345;
		addSupplierCard.addSupplier(12345+"", "Bike Wholesale shop", "Dublin", "0861234567", 
				"bikewholesale@yahoo.com", catalog);
		
		assertEquals("Bike Wholesale shop", database.getSupplier(supplierID).getName());
		assertEquals("Dublin", database.getSupplier(supplierID).getAddress());
		assertEquals("0861234567", database.getSupplier(supplierID).getPhoneNum());
		assertEquals("bikewholesale@yahoo.com", database.getSupplier(supplierID).getEmail());
		assertEquals(catalog, database.getSupplier(supplierID).getProducts());
		
		// attempt to add a Supplier with non numeric supplier id
				addSupplierCard.addSupplier("supplier1", "Ronald Mcdonald", "Louth", "087955884", "r.mcdonald.hotmail.com", catalog);
				Supplier rMc = database.getSupplier("Ronald Mcdonald");
				assertNull(rMc);
	
	
	}

}

