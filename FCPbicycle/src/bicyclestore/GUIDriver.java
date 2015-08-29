package bicyclestore;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import bicyclestore.cardlayouts.bicyclelayout.BicycleTableSorter;
import bicyclestore.cardlayouts.customercardlayouts.CustomersCardLayout;
import bicyclestore.cardlayouts.invoicecardlayouts.InvoiceCardLayout;
import bicyclestore.cardlayouts.ordercardlayouts.OrdersCardLayout;
import bicyclestore.cardlayouts.profitandlosscardlayout.ProfitAndLossCard;
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
	
	private JFrame frame;
	private JPanel mainPanel;
	private JTabbedPane tabbedPane;
	private JButton logoutBtn;
	
	private JMenuBar menuBar;
	private JMenu fileMenu, viewMenu;
	private JMenuItem logOutMenuItem, exitMenuItem, shrinkWindowMenuItem, fullScreenMenuItem;
	
	public GUIDriver(Employee employee, Database database) {
		this.employee = employee;
		this.database = database;
		frame = this;
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		
		JPanel backgroundPanel = new BackgroundPanel();
		backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.X_AXIS));
		
		mainPanel = new GUIPanel();
		backgroundPanel.add(Box.createHorizontalGlue());
		backgroundPanel.add(mainPanel, BorderLayout.CENTER);
		backgroundPanel.add(Box.createHorizontalGlue());
		
		createMenu();
		
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
		
		// add Invoice card layout to invoice tab
		InvoiceCardLayout invoiceCard = new InvoiceCardLayout(database, employee);
		invoicingTab.add(invoiceCard);
		
		// add Bicycle card layout to products tab
		BicycleTableSorter bicycleTableSorter = new BicycleTableSorter();
		productTab.add(bicycleTableSorter.getBicycleLayoutPane());
		
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
		
		// add invoicing card layout to invoicing tab
		InvoiceCardLayout invoiceCard = new InvoiceCardLayout(database, employee);
		invoicingTab.add(invoiceCard);
		
		// add profit and loss card layout to profit and loss tab
		ProfitAndLossCard profitAndLossCard = new ProfitAndLossCard(database);
		profitAndLossTab.add(profitAndLossCard.getStockControlCardLayout());
		
		// add Bicycle card layout to products tab
		BicycleTableSorter bicycleTableSorter = new BicycleTableSorter();
		productTab.add(bicycleTableSorter.getBicycleLayoutPane());
		
		tabbedPane.add("Customers", customerTab);
		tabbedPane.add("Products", productTab);
		tabbedPane.add("Invoicing", invoicingTab);
		tabbedPane.add("Suppliers", supplierTab);
		tabbedPane.add("Ordering", orderingTab);
		tabbedPane.add("Profit And Loss", profitAndLossTab);
		tabbedPane.add("Stock Control", stockTab);
	}
	
	private void createMenu() {
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		
		setFileMenuItems();
		setViewMenuItems();
		
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void setFileMenuItems() {
		// create log out menu item
		logOutMenuItem = new JMenuItem("Log out");
		logOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		logOutMenuItem.setMnemonic(KeyEvent.VK_L);
		logOutMenuItem.addActionListener(new MenuItemListener());
		
		exitMenuItem = new JMenuItem("Exit program");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_X, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		exitMenuItem.setMnemonic(KeyEvent.VK_X);
		
		exitMenuItem.addActionListener(new MenuItemListener());
		
		fileMenu.add(logOutMenuItem);
		fileMenu.add(exitMenuItem);
	}
	
	private void setViewMenuItems() {
		fullScreenMenuItem = new JMenuItem("Full Screen");
		fullScreenMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		fullScreenMenuItem.setMnemonic(KeyEvent.VK_F);
		fullScreenMenuItem.addActionListener(new MenuItemListener());
		
		shrinkWindowMenuItem = new JMenuItem("Shrink Window");
		shrinkWindowMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		shrinkWindowMenuItem.setMnemonic(KeyEvent.VK_S);
		shrinkWindowMenuItem.addActionListener(new MenuItemListener());
		
		viewMenu.add(shrinkWindowMenuItem);
		viewMenu.add(fullScreenMenuItem);
	}
	
	@SuppressWarnings("serial")
	private class BackgroundPanel extends JPanel {
	    Image bg = new ImageIcon(getClass().getResource("/images/bike_shop_background.png")).getImage();
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
					new LoginGUI(database);
				}
			});
			pack();
	    }
	}
	
	private class MenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == exitMenuItem) {
				System.exit(0);
			}
			
			if(e.getSource() == logOutMenuItem) {
				dispose();
				new LoginGUI(database);
			}
			
			if(e.getSource() == shrinkWindowMenuItem) {
				frame.setSize(800,500);;
				frame.setLocationRelativeTo(null);
			}
			
			if(e.getSource() == fullScreenMenuItem) {
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
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
