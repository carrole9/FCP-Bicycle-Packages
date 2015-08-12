package bicyclestore.transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import bicyclestore.staff.Employee;

public class Transaction {
	

	private int transactionID;
	private Employee employee;
	private double transactionCost;
	private String paymentMethod;
	private Date transactionDate;
	

	public Transaction(int transactionID, Employee employee,double transactionCost, String paymentMethod,Date transactionDate) {

		this.transactionID = transactionID;
		this.employee = employee;
		this.transactionCost=transactionCost;
		this.paymentMethod = paymentMethod;
		this.transactionDate=transactionDate;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		transactionDate = new Date();
				
	}

	public double getTransactionCost() {
		return transactionCost;
	}

	public void setTransactionCost(double transactionCost) {
		this.transactionCost = transactionCost;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public Date getRentalDate() {
		return transactionDate;
	}

	public void setRentalDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
		public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
