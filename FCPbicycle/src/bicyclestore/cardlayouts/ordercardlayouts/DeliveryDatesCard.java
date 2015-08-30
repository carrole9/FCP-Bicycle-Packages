package bicyclestore.cardlayouts.ordercardlayouts;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import bicyclestore.Database;
import bicyclestore.bikes.Bicycle;
import bicyclestore.transaction.PurchasingTransaction;

@SuppressWarnings("serial")
public class DeliveryDatesCard extends JPanel implements ListSelectionListener {

	private Database database;
	
	private static final Object[] DELIVERY_TABLE_COL_NAMES = {"Transaction ID","Delivery date",
			"Order date","Supplier","Employee", "Cost of order"};
	private static final Object[] PRODUCT_TABLE_COL_NAMES = {"Product type", "Model", "Cost"};
	
	private JTable deliveryTable;
	private DefaultTableModel tableModel;
	private JScrollPane tableScrollPane; 
	
	private JTable productsTable;
	private DefaultTableModel productsTableModel;
	private JScrollPane productsTableScrollPane;
	
	public DeliveryDatesCard(Database database) {
		this.database = database;
		setUpDeliveryTable();
		setUpProductsTable();
		setUpPanel();
	}
	
	public void setUpPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(tableScrollPane);
		this.add(productsTableScrollPane);
		deliveryTable.getSelectionModel().addListSelectionListener(this);
	}
	
	private void setUpDeliveryTable() {
		// set up border for order table
		TitledBorder border = BorderFactory.createTitledBorder("Upcoming deliveries");
		border.setTitleJustification(TitledBorder.CENTER);
		
		deliveryTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // do not allow user to edit table fields
			}
		};
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(DELIVERY_TABLE_COL_NAMES);
		deliveryTable.setModel(tableModel);
		tableScrollPane = new JScrollPane();
		tableScrollPane.setBorder(border);
		tableScrollPane.setViewportView(deliveryTable);
		
		deliveryTable.setPreferredScrollableViewportSize(new Dimension(700, 100));
		deliveryTable.setFillsViewportHeight(true);
		
		addContentsFromDB();
		
		if(tableModel.getRowCount() > 0) {
			deliveryTable.setRowSelectionInterval(0, 0);
		}
		
	}
	
	// add purchasing transactions where orders have not yet been delivered
	private void addContentsFromDB() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			if(order.getDeliveryDate().after(new Date())) {
				Object[] row = {order.getTransactionID(), sdf.format(order.getDeliveryDate()),
						sdf.format(order.getTransactionDate()), order.getSupplier().getName(),
						order.getEmployee().getName(), order.getShoppingList().getTotalCostValue()};
				tableModel.addRow(row);
			}
		}
	}
	
	private void setUpProductsTable() {
		// set up border for products table
		TitledBorder border = BorderFactory.createTitledBorder("Products in selected order");
		border.setTitleJustification(TitledBorder.CENTER);
		
		productsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // do not allow user to edit table fields directly
			}
		};
		productsTable.setPreferredScrollableViewportSize(new Dimension(400,100));
		productsTable.setFillsViewportHeight(true);
		productsTableModel = new DefaultTableModel();
		productsTableModel.setColumnIdentifiers(PRODUCT_TABLE_COL_NAMES);
		productsTable.setModel(productsTableModel);
		productsTableScrollPane = new JScrollPane(productsTable);
		productsTableScrollPane.setBorder(border);
		
		if(tableModel.getRowCount() > 0)
			setProductsTableContent();
	}
	
	private void setProductsTableContent() {
		// remove any rows already on table
		for(int i = productsTableModel.getRowCount()-1; i >= 0; i--) {
			productsTableModel.removeRow(i);
		}
		
		// add new rows
		int transactionId = (int)deliveryTable.getValueAt(deliveryTable.getSelectedRow(), 0);
		PurchasingTransaction order = database.getPurchasingTransaction(transactionId);
		for(Bicycle bike : order.getShoppingList().getShoppingList()) {
			Object[] row = {splitCamelCase(bike.getClass().getSimpleName()), bike.getModel(), bike.getCostPrice()};
			productsTableModel.addRow(row);
		}
	}
	
	// separate words contained in camelCase. E.g class name "RoadBike" will become "Road Bike"
	private String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}
	
	// refresh table to add new orders to delivery dates table
	public void newOrderAdded(int newTransactionID) {
		PurchasingTransaction order = database.getPurchasingTransaction(newTransactionID);
		if(order.getDeliveryDate().after(new Date())) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
			Object[] row = {order.getTransactionID(), sdf.format(order.getDeliveryDate()),
					sdf.format(order.getTransactionDate()), order.getSupplier().getName(),
					order.getEmployee().getName(), order.getShoppingList().getTotalCostValue()};
			tableModel.addRow(row);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(tableModel.getRowCount() > 0) 
			setProductsTableContent();
	}

}
