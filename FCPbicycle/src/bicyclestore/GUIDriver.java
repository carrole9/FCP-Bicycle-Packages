package bicyclestore;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import bicyclestore.LoginGUI;
import bicyclestore.cardlayouts.customercardlayouts.CustomersCardLayout;
import bicyclestore.cardlayouts.ordercardlayouts.OrdersCardLayout;
import bicyclestore.cardlayouts.stockcontrol.StockControlCard;
import bicyclestore.cardlayouts.suppliercardlayouts.SuppliersCardLayout;
import bicyclestore.staff.Employee;
import bicyclestore.staff.Manager;
import bicyclestore.staff.SalesAssistant;

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
		//setSize(800, 500);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // set full screen
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		setUpTabbedPane();
		
		Container cp = getContentPane();
		cp.add(tabbedPane, BorderLayout.CENTER);
		
		setVisible(true);
		
		logoutBtn = new JButton("Log Out");
		add(logoutBtn, BorderLayout.SOUTH);
		logoutBtn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new LoginGUI();
			}
		});
		
	}
	
	private void setUpTabbedPane() {
		// find out if employee is a sales assistant or manager
		if(employee instanceof SalesAssistant)
			setUpSalesAssistantTabs();
		else if(employee instanceof Manager)
			setUpManagerTabs();
	}
	
	private void setUpSalesAssistantTabs() {
		tabbedPane = new JTabbedPane();
		
		JPanel customerTab = new JPanel();
		JPanel productTab  = new JPanel();
		JPanel invoicingTab = new JPanel();
		
		// add customer card layout to customer tab
		CustomersCardLayout customersLayout = new CustomersCardLayout(database);
		customerTab.add(customersLayout.getCardLayoutPane());
		
		tabbedPane.add("Customers", customerTab);
		tabbedPane.add("Products", productTab);
		tabbedPane.add("Invoicing", invoicingTab);
	}
	
	private void setUpManagerTabs() {
		tabbedPane = new JTabbedPane();
		
		JPanel customerTab = new JPanel();
		JPanel productTab  = new JPanel();
		JPanel invoicingTab = new JPanel();
		JPanel supplierTab = new JPanel();
		JPanel orderingTab = new JPanel();
		JPanel profitAndLossTab = new JPanel();
		JPanel stockTab = new JPanel();
		
		// add customer card layout to customer tab
		CustomersCardLayout customersLayout = new CustomersCardLayout(database);
		customerTab.add(customersLayout.getCardLayoutPane());
		
		// add orders card layout to orders tab 
		OrdersCardLayout ordersLayout = new OrdersCardLayout(database, employee); 
		orderingTab.add(ordersLayout); 
		
		// add stock control layout to stock control tab
		StockControlCard stockControlCard = new StockControlCard();
		stockTab.add(stockControlCard.getStockControlCardLayout());
		
		// add supplier card layout to supplier control tab
		SuppliersCardLayout suppliersLayout = new SuppliersCardLayout(database);
		supplierTab.add(suppliersLayout.getCardLayoutPane());
		
		tabbedPane.add("Customers", customerTab);
		tabbedPane.add("Products", productTab);
		tabbedPane.add("Invoicing", invoicingTab);
		tabbedPane.add("Suppliers", supplierTab);
		tabbedPane.add("Ordering", orderingTab);
		tabbedPane.add("Profit And Loss", profitAndLossTab);
		tabbedPane.add("Stock Control", stockTab);
	}
	
//	*------ uncomment main method if you would like to avoid logging in during testing    -------*
	
//	public static void main(String[] args) {
//		Employee e = new SalesAssistant(10001, "Fred Flintstone", "0861234567", "password", 200004);
//		Database d = new Database();
//		d.addEmployee(e);
//		new GUIDriver(e, d);
//	}
}
