package bicyclestore.cardlayouts.customercardlayouts;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;

public class CustomersCardLayout implements ItemListener {
	
	private Database database;
	
	private JPanel cardLayoutPane, comboBoxPane, cards;
	private static final String ADD_CUSTOMER = "Add Customer";
	private static final String VIEW_CUSTOMER = "View Customer";
	private static final String EDIT_DELETE_CUSTOMER = "Edit/Delete Customer";
	private String[] comboBoxItems = {ADD_CUSTOMER, VIEW_CUSTOMER, EDIT_DELETE_CUSTOMER};
	
	// Card classes
	private ViewCustomerCard viewCard;
	private EditCustomerCard editCard;

	public CustomersCardLayout(Database database) {
		this.database = database;
		createCardLayoutPane();
	}
	
	private void createCardLayoutPane() {
		// create panels which make up card layouts
		cardLayoutPane = new JPanel(new BorderLayout());
		createComboBoxPane();
		cards = new JPanel(new CardLayout());
		
		viewCard = new ViewCustomerCard(database);
		editCard = new EditCustomerCard(database, this);
		
		// create cards to make up card layout
		JPanel card1 = new AddCustomerCard(database, this);
		JPanel card2 = viewCard;
		JPanel card3 = editCard;
		
		cards.add(card1, ADD_CUSTOMER);
		cards.add(card2, VIEW_CUSTOMER);
		cards.add(card3, EDIT_DELETE_CUSTOMER);
		
		cardLayoutPane.add(comboBoxPane, BorderLayout.NORTH);
		cardLayoutPane.add(cards, BorderLayout.CENTER);
	}
	
	public void newCustomerAdded(String newCustomerName) {
		// refresh customer lists
		viewCard.refresh(newCustomerName);
		editCard.refresh(newCustomerName);
	}
	
	public void customerEdited(String oldName, String newName) {
		viewCard.customerDetailsEdited(oldName, newName);
	}
	
	public void customerDeleted(String customerName) {
		viewCard.customerDeleted(customerName);
	}
	
	private void createComboBoxPane() {
		// set up titled border for combo box pane
		TitledBorder comboBoxBorder = BorderFactory.createTitledBorder("Select Customer option");
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
	
	public JPanel getCardLayoutPane() {
		return cardLayoutPane;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)e.getItem());
	}
}
