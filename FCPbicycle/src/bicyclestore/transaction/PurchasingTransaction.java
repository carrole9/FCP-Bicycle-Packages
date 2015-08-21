package bicyclestore.transaction;

import java.util.Date;

import bicyclestore.bikes.Bicycle;
import bicyclestore.staff.Employee;
import bicyclestore.suppliers.Supplier;

public class PurchasingTransaction extends Transaction {
	
	private Supplier supplier;

	public PurchasingTransaction(int transactionID, Employee employee,Supplier supplier,double transactionCost,
			String paymentMethod,Date transactionDate, ShoppingBasket shoppingList) {
		super(transactionID,employee,transactionCost, paymentMethod,transactionDate, shoppingList);
		this.supplier=supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public void display(){
		System.out.println("Transactions ID: "+getTransactionID());
		System.out.println("Supplier ID: "+ supplier.getSupplierID());
		System.out.println("Staff ID: "+getEmployee().getStaffID());
		System.out.println("Transaction Cost: €"+ getTransactionCost());
		System.out.println("Payment Method: "+getPaymentMethod());
		System.out.println("Transaction Date: "+ getTransactionDate());

}

}
