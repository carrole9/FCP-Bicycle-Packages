package main;



	import java.awt.BorderLayout;
	import java.awt.Container;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JTabbedPane;

import customers.CustomersCardLayout;
import staff.Employee;
import transaction.OrdersCardLayout;

	public class GUIDriver extends JFrame{
		
		// auto generated serialVersionUID to stop warning from extending JFrame
		private static final long serialVersionUID = -6709981858396174046L;
		
		private Employee employee;
		private Database database;
		
		private JTabbedPane tabbedPane;
		
		public GUIDriver(Employee employee, Database database) {
			this.employee = employee;
			this.database = database;
			createAndShowGUI();
		}
		
		private void createAndShowGUI() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(800, 500);
			setLocationRelativeTo(null);
			setLayout(new BorderLayout());
			
			setUpTabbedPane();
			
			Container cp = getContentPane();
			cp.add(tabbedPane, BorderLayout.CENTER);
			
			setVisible(true);
		}
		
		private void setUpTabbedPane() {
			tabbedPane = new JTabbedPane();
			
			// set tabs which will be displayed
			JPanel customerTab = new JPanel();
			JPanel supplierTab = new JPanel();
			JPanel productTab = new JPanel();
			JPanel ordersTab = new JPanel();
			
			// add customer card layout to customer tab
			CustomersCardLayout customersLayout = new CustomersCardLayout(database);
			customerTab.add(customersLayout.getCardLayoutPane());
			
			// add orders card layout to orders tab
			OrdersCardLayout ordersLayout = new OrdersCardLayout(database, employee);
			ordersTab.add(ordersLayout);
			
			// add tabs to tabbed pane
			tabbedPane.add("Customers", customerTab);
			tabbedPane.add("Suppliers", supplierTab);
			tabbedPane.add("Products", productTab);
			tabbedPane.add("Orders", ordersTab);
		}
		
//		*------ uncomment main method if you would like to avoid logging in during testing    -------*
		
//		public static void main(String[] args) {
//			Employee e = new SalesAssistant(10001, "Fred Flintstone", "0861234567", "password", 200004);
//			Database d = new Database();
//			d.addEmployee(e);
//			new GUIDriver(e, d);
//		}
	}
