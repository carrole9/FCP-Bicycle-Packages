package bicyclestore.bikes;

import bicyclestore.Database;

public class Bicycle {

	private String model;
	private String colour;
	private int frameSize;
	private int wheelSize;
	private String frameComposition;
	boolean inStock = false;
	Database database;
	
	private double costPrice;
	private double salePrice;
	
	public Bicycle(String model,String colour,int frameSize,int wheelSize,String frameComposition,Database database,
			double costPrice, double salePrice) {
		
		this.model = model;
		this.colour = colour;
		this.frameSize = frameSize;
		this.wheelSize = wheelSize;
		this.frameComposition = frameComposition;
		this.database = database;
		
		this.setCostPrice(costPrice);
		this.setSalePrice(salePrice);
	}
	

	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(int frameSize) {
		this.frameSize = frameSize;
	}

	public int getWheelSize() {
		return wheelSize;
	}

	public void setWheelSize(int wheelSize) {
		this.wheelSize = wheelSize;
	}

	public String getFrameComposition() {
		return frameComposition;
	}

	public void setFrameComposition(String frameComposition) {
		this.frameComposition = frameComposition;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

		public void addBicycle(){
			
			
		}


		public double getCostPrice() {
			return costPrice;
		}


		public void setCostPrice(double costPrice) {
			this.costPrice = costPrice;
		}


		public double getSalePrice() {
			return salePrice;
		}


		public void setSalePrice(double salePrice) {
			this.salePrice = salePrice;
		}
	

}
