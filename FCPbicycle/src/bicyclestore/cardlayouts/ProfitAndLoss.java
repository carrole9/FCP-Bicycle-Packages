package bicyclestore.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import bicyclestore.Database;
import bicyclestore.staff.Employee;

public class ProfitAndLoss {

	     
		  private ArrayList<PurchasingTransaction>purchasingTransactions;
		  private ArrayList<SalesTransaction>salesTransactions;
		  private ArrayList<Employee>wages;
		  private double totalSalesTransactionValue;
		  private double totalPurchasingTransactionValue;
		  private double totalIncome;
		  private double totalExpenditure;
		  private double overallBalance;
		  private boolean profitMaking;
		  private double employeeWages;
		  private double bills;
		  private Database data;
		
            public ProfitAndLoss(Database data) {
            this.data=data;
			this.salesTransactions = new ArrayList<SalesTransaction>(data.getSalesTransactions());
			this.purchasingTransactions = new ArrayList<PurchasingTransaction>(data.getPurchasingTransactions());
			this.wages = new ArrayList<Employee>(data.getEmployees());
			bills=500;
			 
			
		}
            
    public void updateAccounts(){
    	calculateWages();
    	calculateSalesTransactionValue();
    	calculatePurchasingTransactionValue();
    	calculateTotalIncome();
    	calculateTotalExpenditure();
    	profitOrLoss();
    }


	public boolean isProfitMaking() {
		return profitMaking;
	}


	public void setProfitMaking(boolean profitMaking) {
		this.profitMaking = profitMaking;
	}


	public double getSalesTransactionValue() {
		return totalSalesTransactionValue;
	}


	public double getEmployeeWages() {
		return employeeWages;
	}


	public void setEmployeeWages(double employeeWages) {
		this.employeeWages = employeeWages;
	}


	public void setSalesTransactionValue(double totalSalesTransactionValue) {
		this.totalSalesTransactionValue = totalSalesTransactionValue;
	}
	
	public double getPurchasingTransactionValue() {
		return totalPurchasingTransactionValue;
	}


	public void setPurchasingTransactionValue(double totalPurchasingTransactionValue) {
		this.totalPurchasingTransactionValue = totalPurchasingTransactionValue;
	}
	
	
	
	
	public double getBills() {
		return bills;
	}

	public void setBills(double bills) {
		this.bills = bills;
	}

	public double getOverallBalance() {
		return overallBalance;
	}


	public void setOverallBalance(double overallBalance) {
		this.overallBalance = overallBalance;
	}
	
	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public double getTotalExpenditure() {
		return totalExpenditure;
	}

	public void setTotalExpenditure(double totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}
	public void calculateTotalIncome(){
		double total =0;
		total = getSalesTransactionValue();
		setTotalIncome(total);
	}

	public void calculateTotalExpenditure(){
		double total =0;
		total = getEmployeeWages()+getPurchasingTransactionValue()+bills;
		setTotalExpenditure(total);
	}
	
	public void calculateWages(){
		double total=0;
		for(Employee wage:wages){
			total=total + (wage.getWages()*40);
		}setEmployeeWages(total);
	}


	public void calculateSalesTransactionValue(){
		double salestotalValue=0;
		for(SalesTransaction salesTransaction: salesTransactions){
			salestotalValue=salestotalValue + salesTransaction.getTransactionCost();
		
		}setSalesTransactionValue(salestotalValue);
	}
	
	
	public void calculatePurchasingTransactionValue(){
		double purchasingtotalValue=0;;
		for(PurchasingTransaction puchasingTransaction: purchasingTransactions){
			 purchasingtotalValue= purchasingtotalValue + puchasingTransaction.getTransactionCost();
		
		}setPurchasingTransactionValue( purchasingtotalValue);
	}
	
	public void profitOrLoss(){
		double result;
		result = totalIncome-totalExpenditure;
		if(result > 0){
			setProfitMaking(true);
		}
		else if(result < 0){
			setProfitMaking(false);
		}
		setOverallBalance(result);
	}
  
	public void display() {
		int balance= (int) Math.round(getOverallBalance());
		if(profitMaking==true){
			System.out.println("The company is making a profit");
			System.out.println("The companies profit is: €" + balance);
		}else if(profitMaking==false){
			System.out.println("The company is making a loss");
			System.out.println("The companies loss is: €" + balance);
			
		}
	}


}
