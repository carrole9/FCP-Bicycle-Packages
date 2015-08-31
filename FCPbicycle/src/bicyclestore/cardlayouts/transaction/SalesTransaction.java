package bicyclestore.transaction;

import java.util.Date;

import bicyclestore.bikes.Bicycle;
import bicyclestore.customers.Customer;
import bicyclestore.staff.Employee;

public class SalesTransaction extends Transaction {
      private Customer customer;
	
	
	public SalesTransaction (int transactionID, Employee employee,Customer customer,double transactionCost,
			String paymentMethod,Date transactionDate, ShoppingBasket shoppingList) {
		super(transactionID,employee,transactionCost,paymentMethod,transactionDate, shoppingList);
		this.customer=customer;

		}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public void display(){
		System.out.println("Transactions ID: "+getTransactionID());
		System.out.println("Customers ID: "+ customer.getId());
		//System.out.println("Staff ID: "+getEmployee().getStaffID());
		System.out.println("Transaction Cost: €"+ getTransactionCost());
		System.out.println("Payment Method: "+getPaymentMethod());
		System.out.println("Transaction Date: "+ getTransactionDate());
		

}
}
