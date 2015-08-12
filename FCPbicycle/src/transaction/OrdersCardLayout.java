package transaction;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import main.Database;
import staff.Employee;
import suppliers.OrderFromSupplierCard;

@SuppressWarnings("serial")
public class OrdersCardLayout extends JPanel implements ItemListener {
	
	private Database database;
	private Employee employee;
	
	private JPanel  comboBoxPane, cards;
	private static final String ORDER_SUPPLIER = "Order from Supplier";
	private static final String VIEW_ORDERS = "View Orders";
	private static final String VIEW_OLD_ORDERS = "View Old Orders";
	private static final String VIEW_DELIVERY_DATES = "View Delivery Dates";
	private static final String VIEW_COSTS = "View Costs";
	private String[] comboBoxItems = {ORDER_SUPPLIER, VIEW_ORDERS, VIEW_OLD_ORDERS, VIEW_DELIVERY_DATES, VIEW_COSTS};
	
	// Card classes
	private OrderFromSupplierCard orderSupplierCard;

	public OrdersCardLayout(Database database, Employee employee) {
		this.database = database;
		this.employee = employee;
		createCardLayoutPane();
	}
	
	private void createCardLayoutPane() {
		// create panels which make up card layouts
		this.setLayout(new BorderLayout());
		createComboBoxPane();
		cards = new JPanel(new CardLayout());
		
		orderSupplierCard = new OrderFromSupplierCard(database, employee);
		//viewCard = new ViewCustomerCard(database);
		//editCard = new EditCustomerCard(database, this);
		
		// create cards to make up card layout
		JPanel card1 = orderSupplierCard;
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();
		JPanel card5 = new JPanel();
		
		cards.add(card1, ORDER_SUPPLIER);
		cards.add(card2, VIEW_ORDERS);
		cards.add(card3, VIEW_OLD_ORDERS);
		cards.add(card4, VIEW_DELIVERY_DATES);
		cards.add(card5, VIEW_COSTS);
		
		this.add(comboBoxPane, BorderLayout.NORTH);
		this.add(cards, BorderLayout.CENTER);
	}
	
	private void createComboBoxPane() {
		// set up titled border for combo box pane
		TitledBorder comboBoxBorder = BorderFactory.createTitledBorder("Select Order option");
		comboBoxBorder.setTitleJustification(TitledBorder.CENTER);
		comboBoxPane = new JPanel();
		comboBoxPane.setPreferredSize(new Dimension(750, 65));
		comboBoxPane.setBorder(comboBoxBorder);
		
		// add items and item listener to combo box
        JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}

}