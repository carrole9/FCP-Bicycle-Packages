package bicyclestore.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;

public class StockControl {
	private ArrayList<Bicycle>stockcontrol= new ArrayList<Bicycle>();
	private int bmx;
	private int motorizedBike;
	private int mountainBike;
	private int roadBike;
	private BMX bmxx;
	private MotorisedBike motor;
	private MountainBike mountain;
	private RoadBike road;
	private double noOfbikesSold;
	private double predictions;
	
	
	public StockControl(Database data) {
		Collections.copy(stockcontrol,data.getBicycles());
		this.bmx=bmx;
		this.motorizedBike=motorizedBike;
		this.mountainBike=mountainBike;
		this.roadBike=roadBike;
	}
	public StockControl(){
		
	}
	
	public ArrayList<Bicycle> getStockcontrol() {
		return stockcontrol;
	}

	public double getNoOfbikesSold() {
		return noOfbikesSold;
	}
	public void setNoOfbikesSold(double noOfbikesSold) {
		this.noOfbikesSold = noOfbikesSold;
	}
	public void setStockcontrol(ArrayList<Bicycle> stockcontrol) {
		this.stockcontrol = stockcontrol;
	}

	public int getBmx() {
		return bmx;
	}

	public void setBmx(int bmx) {
		this.bmx = bmx;
	}

	public int getMotorizedBike() {
		return motorizedBike;
	}

	public void setMotorizedBike(int motorizedBike) {
		this.motorizedBike = motorizedBike;
	}

	public int getMountainBike() {
		return mountainBike;
	}

	public void setMountainBike(int mountainBike) {
		this.mountainBike = mountainBike;
	}

	public int getRoadBike() {
		return roadBike;
	}

	public double getPredictions() {
		return predictions;
	}
	public void setPredictions(double predictions) {
		this.predictions = predictions;
	}
	public void setRoadBike(int roadBike) {
		this.roadBike = roadBike;
	}

	public void calculateStock(Bicycle bike){
		for(Bicycle calculatebike:stockcontrol){
		if(bmxx.isHasStuntPegs()==true){
			setBmx((getBmx())+1);}
		
	
		else if(motor.isRequiresLicence()==true){
			setMotorizedBike(getMotorizedBike()+1);
	}
		else if(mountain.getNoOfGears()<0 &&mountain.isHasSuspension()==true){
			setMountainBike(getMountainBike()+1);
		}
		
		else if(road.getNumberOfGears()<0){
			setRoadBike(getRoadBike()+1);
		}
	}
	}
	public void predictions(Database data){
		ArrayList<SalesTransaction>temp= new ArrayList<SalesTransaction>();
		Collections.copy(temp,data.getSalesTransactions());
		Date now = new Date();
		Date lastWeek = new Date(System.currentTimeMillis() - 7*24*60*60*1000);
		for(Transaction transaction:temp){
			if(transaction.getTransactionDate().before(now)&&transaction.getTransactionDate().after(lastWeek)){
				setNoOfbikesSold(noOfbikesSold+1);
			}	
		}
		 setPredictions(getNoOfbikesSold()*1.5);
		
	
	}
	
	}
