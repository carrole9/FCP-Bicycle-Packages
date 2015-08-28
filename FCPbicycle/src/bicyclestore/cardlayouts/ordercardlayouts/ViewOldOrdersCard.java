package bicyclestore.cardlayouts.ordercardlayouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
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
public class ViewOldOrdersCard extends JPanel implements ListSelectionListener {

	private Database database;
	
	private JTable orderListTable;
	private DefaultTableModel orderListModel;
	private JScrollPane listScrollPane;
	
	private JPanel orderDetailsPane;
	private Box orderDetailsBox;
	private JTable productDetailsTable;
	private DefaultTableModel tableModel;
	private JScrollPane tableScrollPane;
	
	private JLabel idLabel, employeeLabel, supplierLabel, costLabel, dateLabel, deliveryDateLabel,
						lblTransactionID, lblEmployee, lblSupplier, lblCost, lblDate, lblDeliveryDate;
	
	public ViewOldOrdersCard(Database database) {
		this.database = database;
		setUpOrderListTable();
		createViewOrderCard();
	}
	
	private void setUpOrderListTable() {
		Object[] colNames = {"Transaction ID", "Date"};
		orderListModel = new DefaultTableModel();
		orderListModel.setColumnIdentifiers(colNames);
		orderListTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		orderListTable.setModel(orderListModel);
		orderListTable.setFillsViewportHeight(true);
		orderListTable.setPreferredScrollableViewportSize(new Dimension(200,200));
		
		// set up scroll pane to contain list and wrap in titled border
		listScrollPane = new JScrollPane(orderListTable);
		TitledBorder scrollPaneBorder = BorderFactory.createTitledBorder("Select an order");
		scrollPaneBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(scrollPaneBorder);
		
		addOrdersFromDB();
		orderListTable.setRowSelectionInterval(0, 0);
		orderListTable.getSelectionModel().addListSelectionListener(this);
	}
	
	private void addOrdersFromDB() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			// if delivery date has passed display as old order
			if(now.after(order.getDeliveryDate())) {
				Object[] row = {order.getTransactionID(), sdf.format(order.getTransactionDate() ) };
				orderListModel.addRow(row);
			}
		}
	}
	
	private void createViewOrderCard() {
		setLayout(new BorderLayout());
		setUpOrderDetailsPane();
		setUpLabels();
		
		if(database.getPurchasingTransactions().size() > 0)
			setOrderDetailsContent();
		
		add(listScrollPane, BorderLayout.WEST);
		add(orderDetailsBox, BorderLayout.CENTER);
	}
	
	private void setUpOrderDetailsPane() {
		orderDetailsBox = Box.createVerticalBox();
		
		// set titled border on panel
		GridLayout detailsGrid = new GridLayout(6,2);
		detailsGrid.setVgap(5);
		detailsGrid.setHgap(5);
		orderDetailsPane = new JPanel(detailsGrid);
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Order Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		orderDetailsBox.setBorder(detailsBorder);
		
		createProductDetailsTable();
		
		orderDetailsBox.add(orderDetailsPane);
		orderDetailsBox.add(tableScrollPane);
		
	}
	
	private void setUpLabels() {
		idLabel = new JLabel("Transaction ID:", JLabel.CENTER);
		employeeLabel = new JLabel("Employee Name:", JLabel.CENTER);
		supplierLabel = new JLabel("Supplier Name:", JLabel.CENTER);
		costLabel = new JLabel("Cost of transaction:", JLabel.CENTER);
		dateLabel = new JLabel("Date:", JLabel.CENTER);
		deliveryDateLabel = new JLabel("Delivery Date:", JLabel.CENTER);
		
		idLabel.setOpaque(true);
		idLabel.setBackground(new Color(107,106,104));
		idLabel.setForeground(Color.WHITE);
		employeeLabel.setBackground(new Color(107,106,104));
		employeeLabel.setForeground(Color.WHITE);
		employeeLabel.setOpaque(true);
		supplierLabel.setBackground(new Color(107,106,104));
		supplierLabel.setForeground(Color.WHITE);
		supplierLabel.setOpaque(true);
		costLabel.setBackground(new Color(107,106,104));
		costLabel.setForeground(Color.WHITE);
		costLabel.setOpaque(true);
		dateLabel.setBackground(new Color(107,106,104));
		dateLabel.setForeground(Color.WHITE);
		dateLabel.setOpaque(true);
		deliveryDateLabel.setBackground(new Color(107,106,104));
		deliveryDateLabel.setForeground(Color.WHITE);
		deliveryDateLabel.setOpaque(true);
		
		lblTransactionID = new JLabel("", JLabel.CENTER);
		lblEmployee = new JLabel("", JLabel.CENTER);
		lblSupplier = new JLabel("", JLabel.CENTER);
		lblCost = new JLabel("", JLabel.CENTER);
		lblDate = new JLabel("", JLabel.CENTER);
		lblDeliveryDate = new JLabel("", JLabel.CENTER);
		lblTransactionID.setBackground(Color.LIGHT_GRAY);
		lblTransactionID.setOpaque(true);
		lblEmployee.setBackground(Color.LIGHT_GRAY);
		lblEmployee.setOpaque(true);
		lblSupplier.setBackground(Color.LIGHT_GRAY);
		lblSupplier.setOpaque(true);
		lblCost.setBackground(Color.LIGHT_GRAY);
		lblCost.setOpaque(true);
		lblDate.setBackground(Color.LIGHT_GRAY);
		lblDate.setOpaque(true);
		lblDeliveryDate.setBackground(Color.LIGHT_GRAY);
		lblDeliveryDate.setOpaque(true);
		// add labels to panel
		orderDetailsPane.add(idLabel);
		orderDetailsPane.add(lblTransactionID);
		orderDetailsPane.add(employeeLabel);
		orderDetailsPane.add(lblEmployee);
		orderDetailsPane.add(supplierLabel);
		orderDetailsPane.add(lblSupplier);
		orderDetailsPane.add(costLabel);
		orderDetailsPane.add(lblCost);
		orderDetailsPane.add(dateLabel);
		orderDetailsPane.add(lblDate);
		orderDetailsPane.add(deliveryDateLabel);
		orderDetailsPane.add(lblDeliveryDate);
	}
	
	private void setOrderDetailsContent() {
		int transactionId = (int)orderListTable.getValueAt(orderListTable.getSelectedRow(), 0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		PurchasingTransaction order = database.getPurchasingTransaction(transactionId);
		lblTransactionID.setText(order.getTransactionID()+"");
		lblEmployee.setText(order.getEmployee().getName());
		lblSupplier.setText(order.getSupplier().getName());
		lblCost.setText(order.getTransactionCost()+"");
		lblDate.setText(sdf.format(order.getTransactionDate()));
		lblDeliveryDate.setText(sdf.format(order.getDeliveryDate()));
	
		addProductDetailsContent(order);
	}
	
	private void createProductDetailsTable() {
		// set up scroll pane and border for products table
		tableScrollPane = new JScrollPane();
		TitledBorder border = BorderFactory.createTitledBorder("Products in order");
		border.setTitleJustification(TitledBorder.CENTER);
		tableScrollPane.setBorder(border);
		
		Object[] colNames = {"Product Type","Model","Cost"};
		tableModel = new DefaultTableModel();
		
		// set up table and make sure cells can not be edited by user
		productDetailsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.setColumnIdentifiers(colNames);
		
		productDetailsTable.setModel(tableModel);
		productDetailsTable.setFillsViewportHeight(true);
		productDetailsTable.setPreferredScrollableViewportSize(new Dimension(600,100));
		
		tableScrollPane.setViewportView(productDetailsTable);
	}
	
	private void addProductDetailsContent(PurchasingTransaction order) {
		// remove any products already on table
		for(int i = tableModel.getRowCount()-1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		// add all product details from order
		for(Bicycle bike : order.getShoppingList().getShoppingList()) {
			Object[] row = {bike.getClass().getSimpleName(), bike.getModel(), bike.getCostPrice()};
			tableModel.addRow(row);
		}
	}
	
	private void emptyOrderDetailFields() {
		lblTransactionID.setText("");
		lblEmployee.setText("");
		lblSupplier.setText("");
		lblCost.setText("");
		lblDate.setText("");
		lblDeliveryDate.setText("");
		
		// remove any products on table
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selection and display content
		if(orderListModel.getRowCount() > 0) {
			setOrderDetailsContent();	
		}
		// if list empty reset fields to blank
		else
			emptyOrderDetailFields();
	}
	
	public void refresh(int newTransactionID) {
		Date orderDate = database.getPurchasingTransaction(newTransactionID).getTransactionDate();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Object[] row = {newTransactionID, sdf.format(orderDate)};
		orderListModel.addRow(row);
	}
	
//	public void customerDetailsEdited(int oldID, int newID) {
//		listModel.setElementAt(oldID+"", listModel.indexOf(newID));
//		orderList.setSelectedValue(newID, true);
//		setOrderDetailsContent();
//	}
//	
//	public void customerDeleted(int transactionID) {
//		listModel.removeElement(transactionID);
//		int currentId = Integer.parseInt(lblTransactionID.getText());
//		if(currentId == transactionID) {
//			orderList.setSelectedIndex(0);
//		}
//	}

}
