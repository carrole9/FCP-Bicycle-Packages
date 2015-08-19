package bicyclestore.cardlayouts.ordercardlayouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bicyclestore.Database;
import bicyclestore.transaction.PurchasingTransaction;

@SuppressWarnings("serial")
public class ViewOldOrdersCard extends JPanel implements ListSelectionListener {

	private Database database;
	
	private JList<String> orderList;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	private JPanel orderDetailsPane;
	
	private JLabel idLabel, employeeLabel, supplierLabel, costLabel, paymentMethodLabel, dateLabel,
						lblTransactionID, lblEmployee, lblSupplier, lblCost, lblPaymentMethod, lblDate;
	
	public ViewOldOrdersCard(Database database) {
		this.database = database;
		setUpOrderList();
		createViewOrderCard();
	}
	
	private void setUpOrderList() {
		listModel = new DefaultListModel<String>();
		addOrdersFromDB();
		
		orderList = new JList<String>(listModel);
		orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderList.setSelectedIndex(0);
		orderList.addListSelectionListener(this);
		orderList.setVisibleRowCount(10);
		
		// set up scroll pane to contain list and wrap in titled border
		listScrollPane = new JScrollPane(orderList);
		TitledBorder scrollPaneBorder = BorderFactory.createTitledBorder("Select an order");
		scrollPaneBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(scrollPaneBorder);
		
		orderList.setPreferredSize(new Dimension(200,200));
	}

	private void addOrdersFromDB() {
		Calendar now = Calendar.getInstance();
		Calendar yesterday = Calendar.getInstance();
		yesterday.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR)-1);
		for(PurchasingTransaction order : database.getPurchasingTransactions()) {
			// if order is older than yesterday add to list
			Calendar orderDate = Calendar.getInstance();
			orderDate.setTime(order.getTransactionDate());
			
			if(orderDate.before(yesterday))
				listModel.addElement(order.getTransactionID()+"");
		}
	}
	
	private void createViewOrderCard() {
		setLayout(new BorderLayout());
		setUpOrderDetailsPane();
		setUpLabels();
		
		if(database.getCustomers().size() > 0)
			setOrderDetailsContent();
		
		add(listScrollPane, BorderLayout.WEST);
		add(orderDetailsPane, BorderLayout.CENTER);
	}
	
	private void setUpOrderDetailsPane() {
		// set titled border on panel
		GridLayout detailsGrid = new GridLayout(6,2);
		detailsGrid.setVgap(5);
		detailsGrid.setHgap(5);
		orderDetailsPane = new JPanel(detailsGrid);
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Order Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		orderDetailsPane.setBorder(detailsBorder);
	}
	
	private void setUpLabels() {
		idLabel = new JLabel("Transaction ID:", JLabel.CENTER);
		employeeLabel = new JLabel("Employee Name:", JLabel.CENTER);
		supplierLabel = new JLabel("Supplier Name:", JLabel.CENTER);
		costLabel = new JLabel("Cost of transaction:", JLabel.CENTER);
		paymentMethodLabel = new JLabel("Payment method:", JLabel.CENTER);
		dateLabel = new JLabel("Date:", JLabel.CENTER);
		
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
		paymentMethodLabel.setBackground(new Color(107,106,104));
		paymentMethodLabel.setForeground(Color.WHITE);
		paymentMethodLabel.setOpaque(true);
		dateLabel.setBackground(new Color(107,106,104));
		dateLabel.setForeground(Color.WHITE);
		dateLabel.setOpaque(true);
		
		lblTransactionID = new JLabel("", JLabel.CENTER);
		lblEmployee = new JLabel("", JLabel.CENTER);
		lblSupplier = new JLabel("", JLabel.CENTER);
		lblCost = new JLabel("", JLabel.CENTER);
		lblPaymentMethod = new JLabel("", JLabel.CENTER);
		lblDate = new JLabel("", JLabel.CENTER);
		lblTransactionID.setBackground(Color.LIGHT_GRAY);
		lblTransactionID.setOpaque(true);
		lblEmployee.setBackground(Color.LIGHT_GRAY);
		lblEmployee.setOpaque(true);
		lblSupplier.setBackground(Color.LIGHT_GRAY);
		lblSupplier.setOpaque(true);
		lblCost.setBackground(Color.LIGHT_GRAY);
		lblCost.setOpaque(true);
		lblPaymentMethod.setBackground(Color.LIGHT_GRAY);
		lblPaymentMethod.setOpaque(true);
		lblDate.setBackground(Color.LIGHT_GRAY);
		lblDate.setOpaque(true);
		// add labels to panel
		orderDetailsPane.add(idLabel);
		orderDetailsPane.add(lblTransactionID);
		orderDetailsPane.add(employeeLabel);
		orderDetailsPane.add(lblEmployee);
		orderDetailsPane.add(supplierLabel);
		orderDetailsPane.add(lblSupplier);
		orderDetailsPane.add(costLabel);
		orderDetailsPane.add(lblCost);
		orderDetailsPane.add(paymentMethodLabel);
		orderDetailsPane.add(lblPaymentMethod);
		orderDetailsPane.add(dateLabel);
		orderDetailsPane.add(lblDate);
	}
	
	private void setOrderDetailsContent() {
		// get customer at selected index
		int transactionId = Integer.parseInt(orderList.getSelectedValue());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		PurchasingTransaction order = database.getPurchasingTransaction(transactionId);
		lblTransactionID.setText(order.getTransactionID()+"");
		lblEmployee.setText(order.getEmployee().getName());
		lblSupplier.setText(order.getSupplier().getName());
		lblCost.setText(order.getTransactionCost()+"");
		lblPaymentMethod.setText(order.getPaymentMethod());
		lblDate.setText(sdf.format(order.getTransactionDate()));
	}
	
	private void emptyOrderDetailFields() {
		lblTransactionID.setText("");
		lblEmployee.setText("");
		lblSupplier.setText("");
		lblCost.setText("");
		lblPaymentMethod.setText("");
		lblDate.setText("");
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selection and display content
		if(!listModel.isEmpty()) {
			if(orderList.isSelectionEmpty())
				orderList.setSelectedIndex(0);
			setOrderDetailsContent();	
		}
		// if list empty reset fields to blank
		else
			emptyOrderDetailFields();
	}
	
	public void refresh(int newTransactionID) {
		listModel.addElement(newTransactionID+"");
	}
	
	public void customerDetailsEdited(int oldID, int newID) {
		listModel.setElementAt(oldID+"", listModel.indexOf(newID));
		orderList.setSelectedValue(newID, true);
		setOrderDetailsContent();
	}
	
	public void customerDeleted(int transactionID) {
		listModel.removeElement(transactionID);
		int currentId = Integer.parseInt(lblTransactionID.getText());
		if(currentId == transactionID) {
			orderList.setSelectedIndex(0);
		}
	}

}
