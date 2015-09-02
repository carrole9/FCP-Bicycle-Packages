package bicyclestore.bikes;

import bicyclestore.Database;

public class Cruiser extends Bicycle {

	private boolean hasTassles;
	
	public Cruiser(String colour,String model,int frameSize,int wheelSize,String frameComposition,
			double costPrice, double salePrice) {
		
		super(model,colour, frameSize, wheelSize, frameComposition, costPrice, salePrice);

	}

	public boolean isHasTassles() {
		return hasTassles;
	}

	public void setHasTassles(boolean hasTassles) {
		this.hasTassles = hasTassles;
	}
	public void Display(){
		
	}

}
