package customers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

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

import main.Database;

public class ViewCustomerCard extends JPanel implements ListSelectionListener {

	// auto generated serialVersionUID
	private static final long serialVersionUID = -896419160000515179L;
	
	
	private JList<String> customerList;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	private JPanel customerDetails;

	private Database database;
	
	// labels to store customer details
	private JLabel idLabel, nameLabel, addressLabel, phoneLabel, emailLabel, customerId,
					customerName, customerAddress, customerPhone, customerEmail;
	
	public ViewCustomerCard(Database database) {
		this.database = database;
		setUpCustomerList();
		createViewCustomerCard();
	}
	
	private void setUpCustomerList() {
		listModel = new DefaultListModel<String>();
		addCustomersFromDB();
		
		customerList = new JList<String>(listModel);
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerList.setSelectedIndex(0);
		customerList.addListSelectionListener(this);
		customerList.setVisibleRowCount(10);
		
		// set up scroll pane to contain list and wrap in titled border
		listScrollPane = new JScrollPane(customerList);
		TitledBorder scrollPaneBorder = BorderFactory.createTitledBorder("Select a customer");
		scrollPaneBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(scrollPaneBorder);
		
		customerList.setPreferredSize(new Dimension(200,200));
	}
	
	private void addCustomersFromDB() {
		for(Customer customer : database.getCustomers()) {
			listModel.addElement(customer.getName());
		}
	}

	private void createViewCustomerCard() {
		setLayout(new BorderLayout());
		setUpCustomerDetailsPane();
		setUpLabels();
		
		if(database.getCustomers().size() > 0)
			setCustomerDetailsContent();
		
		add(listScrollPane, BorderLayout.WEST);
		add(customerDetails, BorderLayout.CENTER);
	}
	
	private void setUpCustomerDetailsPane() {
		// set titled border on panel
		GridLayout detailsGrid = new GridLayout(5,2);
		detailsGrid.setVgap(5);
		detailsGrid.setHgap(5);
		customerDetails = new JPanel(detailsGrid);
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Customer Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		customerDetails.setBorder(detailsBorder);
	}
	
	private void setUpLabels() {
		idLabel = new JLabel("Customer ID:", JLabel.CENTER);
		nameLabel = new JLabel("Customer Name:", JLabel.CENTER);
		addressLabel = new JLabel("Customer Address:", JLabel.CENTER);
		phoneLabel = new JLabel("Customer phone number:", JLabel.CENTER);
		emailLabel = new JLabel("Customer email address:", JLabel.CENTER);
		
		idLabel.setOpaque(true);
		idLabel.setBackground(new Color(107,106,104));
		idLabel.setForeground(Color.WHITE);
		nameLabel.setBackground(new Color(107,106,104));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setOpaque(true);
		addressLabel.setBackground(new Color(107,106,104));
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setOpaque(true);
		phoneLabel.setBackground(new Color(107,106,104));
		phoneLabel.setForeground(Color.WHITE);
		phoneLabel.setOpaque(true);
		emailLabel.setBackground(new Color(107,106,104));
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setOpaque(true);
		
		customerId = new JLabel("", JLabel.CENTER);
		customerName = new JLabel("", JLabel.CENTER);
		customerAddress = new JLabel("", JLabel.CENTER);
		customerPhone = new JLabel("", JLabel.CENTER);
		customerEmail = new JLabel("", JLabel.CENTER);
		customerId.setBackground(Color.LIGHT_GRAY);
		customerId.setOpaque(true);
		customerName.setBackground(Color.LIGHT_GRAY);
		customerName.setOpaque(true);
		customerAddress.setBackground(Color.LIGHT_GRAY);
		customerAddress.setOpaque(true);
		customerPhone.setBackground(Color.LIGHT_GRAY);
		customerPhone.setOpaque(true);
		customerEmail.setBackground(Color.LIGHT_GRAY);
		customerEmail.setOpaque(true);
		
		// add labels to panel
		customerDetails.add(idLabel);
		customerDetails.add(customerId);
		customerDetails.add(nameLabel);
		customerDetails.add(customerName);
		customerDetails.add(addressLabel);
		customerDetails.add(customerAddress);
		customerDetails.add(phoneLabel);
		customerDetails.add(customerPhone);
		customerDetails.add(emailLabel);
		customerDetails.add(customerEmail);
	}
	
	private void setCustomerDetailsContent() {
		// get customer at selected index
		Customer customer = database.getCustomer(customerList.getSelectedValue());
		customerId.setText(customer.getId()+"");
		customerName.setText(customer.getName());
		customerAddress.setText(customer.getAddress());
		customerPhone.setText(customer.getPhoneNum());
		customerEmail.setText(customer.getEmail());
	}
	
	private void emptyCustomerDetailFields() {
		customerId.setText("");
		customerName.setText("");
		customerAddress.setText("");
		customerPhone.setText("");
		customerEmail.setText("");
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selection and display content
		if(!listModel.isEmpty()) {
			if(customerList.isSelectionEmpty())
				customerList.setSelectedIndex(0);
			setCustomerDetailsContent();	
		}
		// if list empty reset fields to blank
		else
			emptyCustomerDetailFields();
	}
	
	public void refresh(String newCustomerName) {
		listModel.addElement(newCustomerName);
	}
	
	public void customerDetailsEdited(String oldName, String newName) {
		listModel.setElementAt(newName, listModel.indexOf(oldName));
		customerList.setSelectedValue(newName, true);
		setCustomerDetailsContent();
	}
	
	public void customerDeleted(String customerName) {
		listModel.removeElement(customerName);
		if(customerName.toString().equals(customerName)) {
			customerList.setSelectedIndex(0);
		}
	}

}
