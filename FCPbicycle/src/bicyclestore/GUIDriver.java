package bicyclestore;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	
	private JPanel mainPanel;
	private JTabbedPane tabbedPane;
	private JButton logoutBtn;
	
	public GUIDriver(Employee employee, Database database) {
		this.employee = employee;
		this.database = database;
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		
		JPanel backgroundPanel = new BackgroundPanel();
		backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.X_AXIS));
		
		mainPanel = new GUIPanel();
		backgroundPanel.add(Box.createHorizontalGlue());
		backgroundPanel.add(mainPanel, BorderLayout.CENTER);
		backgroundPanel.add(Box.createHorizontalGlue());
		
		setContentPane(backgroundPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800,600));
        setVisible(true);
	}
	
	private void setUpTabbedPane() {
		//tabbedPane.setSize(800, 600);
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
	
	@SuppressWarnings("serial")
	private class BackgroundPanel extends JPanel {
	    Image bg = new ImageIcon("src/images/bike_shop_background.png").getImage();
	    @Override
	    public void paintComponent(Graphics g) {
	        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	    }
	}
	
	@SuppressWarnings("serial")
	class GUIPanel extends JPanel {
	    GUIPanel() {
	    	setUpTabbedPane();
	    	
	    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setOpaque(false);
			add(Box.createVerticalGlue());
			add(tabbedPane);
		
			// add log out button and event handler
			logoutBtn = new JButton("Log Out");
			logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			add(logoutBtn);
			add(Box.createVerticalGlue());
			logoutBtn.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent arg0) {
					dispose();
					new LoginGUI();
				}
			});
			pack();
	    }
	}
	
//	*------ uncomment main method if you would like to avoid logging in during testing    -------*
	
//	public static void main(String[] args) {
//		Database d = new Database();
//		SystemData data = new SystemData(d);
//		data.fillDatabase();
//		Employee e = d.getEmployee(10002);
//		new GUIDriver(e, d);
//	}
}
