package bicyclestore.cardlayouts.suppliercardlayouts;

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

public class SuppliersCardLayout implements ItemListener {
	
	private Database database;
	
	private JPanel cardLayoutPane, comboBoxPane, cards;
	private static final String ADD_SUPPLIER = "Add Supplier";
	private static final String VIEW_SUPPLIER = "View Supplier";
	private static final String EDIT_DELETE_SUPPLIER = "Edit/Delete Supplier";
	private String[] comboBoxItems = {ADD_SUPPLIER, VIEW_SUPPLIER, EDIT_DELETE_SUPPLIER};
	
	// Card classes
		private ViewSupplierCard viewCard;
		private EditSupplierCard editCard;

	public SuppliersCardLayout(Database database) {
		this.database = database;
		createCardLayoutPane();
		
	}
	
	private void createCardLayoutPane() {
		// create panels which make up card layouts
		cardLayoutPane = new JPanel(new BorderLayout());
		createComboBoxPane();
		cards = new JPanel(new CardLayout());
		
		viewCard = new ViewSupplierCard(database);
		editCard = new EditSupplierCard(database, this);
		
		// create cards to make up card layout
		JPanel card1 = new AddSupplierCard(database, this);
		JPanel card2 = viewCard;
		JPanel card3 = editCard;
				
		cards.add(card1, ADD_SUPPLIER);
		cards.add(card2, VIEW_SUPPLIER);
		cards.add(card3, EDIT_DELETE_SUPPLIER);
				
		cardLayoutPane.add(comboBoxPane, BorderLayout.NORTH);
		cardLayoutPane.add(cards, BorderLayout.CENTER);
	}
	
	public void newSupplierAdded(String newSupplierName) {
		// refresh customer lists
		viewCard.refresh(newSupplierName);
		editCard.refresh(newSupplierName);
	}
	
	public void supplierEdited(String oldName, String newName) {
		viewCard.supplierDetailsEdited(oldName, newName);
	}
	
	public void supplierDeleted(String supplierName) {
		viewCard.supplierDeleted(supplierName);
	}
	
	private void createComboBoxPane() {
		// set up titled border for combo box pane
		TitledBorder comboBoxBorder = BorderFactory.createTitledBorder("Select option");
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