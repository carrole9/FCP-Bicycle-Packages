package bicyclestore.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;

public class StockControl {
	//private ArrayList<Bicycle>stockcontrol= new ArrayList<Bicycle>();
	private int bmx;
	private int motorizedBike;
	private int mountainBike;
	private int roadBike;
	private int cruiserBike;
	private int hybridBike;
	private BMX bmxx;
	private MotorisedBike motor;
	private MountainBike mountain;
	private RoadBike road;
	private Cruiser cruiser;
	private Hybrid hyrid;
	private int noOfbikesSold;
	private int predictions;
	private Database data;
	
	
	public StockControl(Database data) {
		//Collections.copy(stockcontrol,data.getBicycles());
		this.bmx=bmx;
		this.motorizedBike=motorizedBike;
		this.mountainBike=mountainBike;
		this.roadBike=roadBike;
		this.cruiserBike=cruiserBike;
		this.hybridBike=hybridBike;
		this.data=data;
	}
	public StockControl(){
		
	}

	public int getNoOfbikesSold() {
		return noOfbikesSold;
	}
	public void setNoOfbikesSold(int noOfbikesSold) {
		this.noOfbikesSold = noOfbikesSold;
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

	public int getPredictions() {
		return predictions;
	}
	public void setPredictions(int predictions) {
		this.predictions = predictions;
	}
	public void setRoadBike(int roadBike) {
		this.roadBike = roadBike;
	}

	public int getCruiserBike() {
		return cruiserBike;
	}
	public void setCruiserBike(int cruiserBike) {
		this.cruiserBike = cruiserBike;
	}
	public int getHybridBike() {
		return hybridBike;
	}
	public void setHybridBike(int hybridBike) {
		this.hybridBike = hybridBike;
	}
	public void calculateStock(Bicycle bike, Database data){
		for(Bicycle calculatebike:data.getBicycles()){
		if(calculatebike instanceof BMX){
			setBmx((getBmx())+1);}
		
	
		else if(calculatebike instanceof MotorisedBike){
			setMotorizedBike(getMotorizedBike()+1);
	}
		else if(calculatebike instanceof MountainBike){
			setMountainBike(getMountainBike()+1);
		}
		
		else if(calculatebike instanceof RoadBike){
			setRoadBike(getRoadBike()+1);
		}
		
		else if(calculatebike instanceof Cruiser){
			setCruiserBike(getCruiserBike()+1);
		}
		
		else if(calculatebike instanceof Hybrid){
			setHybridBike(getHybridBike()+1);
		}
	}
	}
	public void predictions(Database data){

		Date now = new Date();
		Date lastWeek = new Date(System.currentTimeMillis() - 7*24*60*60*1000);
		for(Transaction transaction:data.getSalesTransactions()){
			//if(now.after(transaction.getTransactionDate())){
			boolean wasDateLastWeek = ((transaction.getTransactionDate()).after(lastWeek) && ((transaction.getTransactionDate().before(now))));
				if(wasDateLastWeek==true){
					setNoOfbikesSold(getNoOfbikesSold()+1);
				//&&transaction.getTransactionDate().after(lastWeek)
			}	
		}
		 setPredictions(getNoOfbikesSold()+2);
		 //System.out.print(getNoOfbikesSold());
		
	
	}
	
	}
