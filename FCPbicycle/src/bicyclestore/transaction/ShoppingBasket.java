package bicyclestore.transaction;

import java.util.ArrayList;

import bicyclestore.bikes.Bicycle;

public class ShoppingBasket {

	private ArrayList<Bicycle> shoppingList;
	private double totalCostValue;
	private double totalSaleValue;
	
	public ShoppingBasket() {
		shoppingList = new ArrayList<Bicycle>();
		totalCostValue = 0;
		totalSaleValue = 0;
	}
	
	public void add(Bicycle bicycle) {
		shoppingList.add(bicycle);
		totalCostValue += bicycle.getCostPrice();
		totalSaleValue += bicycle.getSalePrice();
	}
	
	public void remove(int index) {
		shoppingList.remove(index);
	}
	
	public void remove(Bicycle bicycle) {
		shoppingList.remove(bicycle);
	}
	
	public void removeAll() {
		shoppingList.clear();
	}
	
	public ArrayList<Bicycle> getShoppingList() {
		return shoppingList;
	}
	
	public double getTotalCostValue() {
		return totalCostValue;
	}
	
	public double getTotalSaleValue() {
		return totalSaleValue;
	}
	
	public int getQuantity() {
		return shoppingList.size();
	}

}
