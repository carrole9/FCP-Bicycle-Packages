package bicyclestore.bikes;

import bicyclestore.Database;

public class MotorisedBike extends Bicycle {
	
	private boolean requiresLicence;

	public MotorisedBike(String model,String colour,int frameSize,int wheelSize,String frameComposition,
			double costPrice, double salePrice) {

		super(model,colour, frameSize, wheelSize, frameComposition, costPrice, salePrice);
	}

	public boolean isRequiresLicence() {
		return requiresLicence;
	}

	public void setRequiresLicence(boolean requiresLicence) {
		this.requiresLicence = requiresLicence;
	}

	public void Display(){
		
	}
}
