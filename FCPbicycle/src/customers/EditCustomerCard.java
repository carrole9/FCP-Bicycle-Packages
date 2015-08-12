package customers;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Database;

public class EditCustomerCard extends JPanel implements ListSelectionListener {

	// auto generated serialVersionUID
	private static final long serialVersionUID = 4182078751565104534L;

	private Database database;
	private CustomersCardLayout cardLayout;
	
	private JList<String> customerList;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	private JPanel editCustomerPane;
	private Box buttonPane;
	
	private JLabel lblCustomerId, lblCustomerName, lblCustomerAddress, lblCustomerPhone, lblCustomerEmail;
	private JTextField txtCustomerId, txtCustomerName, txtCustomerAddress, txtCustomerPhone, txtCustomerEmail;
	
	private JButton btnSaveChanges, btnDeleteCustomer;
	
	public EditCustomerCard(Database database, CustomersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		setUpCustomerList();
		createEditCustomerCard();
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
	
	private void createEditCustomerCard() {
		setLayout(new BorderLayout());
		
		setUpTextFields();
		setUpEditCustomerPane();
		setUpButtonPane();
		
		if(database.getCustomers().size() > 0)
			fillInTextFields();
		
		add(listScrollPane, BorderLayout.WEST);
		add(editCustomerPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	private void setUpEditCustomerPane() {
		GridLayout editPaneGrid = new GridLayout(5,2);
		editPaneGrid.setHgap(5);
		editPaneGrid.setVgap(5);
		editCustomerPane = new JPanel(editPaneGrid);
		
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Edit Customer Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		editCustomerPane.setBorder(detailsBorder);
		
		editCustomerPane.add(lblCustomerId);
		editCustomerPane.add(txtCustomerId);
		editCustomerPane.add(lblCustomerName);
		editCustomerPane.add(txtCustomerName);
		editCustomerPane.add(lblCustomerAddress);
		editCustomerPane.add(txtCustomerAddress);
		editCustomerPane.add(lblCustomerPhone);
		editCustomerPane.add(txtCustomerPhone);
		editCustomerPane.add(lblCustomerEmail);
		editCustomerPane.add(txtCustomerEmail);
	}
	
	private void setUpTextFields() {
		txtCustomerId = new JTextField(10);
		txtCustomerName = new JTextField(10);
		txtCustomerAddress = new JTextField(10);
		txtCustomerPhone = new JTextField(10);
		txtCustomerEmail = new JTextField(10);
		
		lblCustomerId = new JLabel("Customer ID: ");
		lblCustomerName = new JLabel("Customer Name: ");
		lblCustomerAddress = new JLabel("Customer Address: ");
		lblCustomerPhone = new JLabel("Customer Phone: ");
		lblCustomerEmail = new JLabel("Customer Email:");
	}
	
	private void setUpButtonPane() {
		buttonPane = Box.createHorizontalBox();
		
		btnSaveChanges = new JButton("Save Changes");
		btnDeleteCustomer = new JButton("Delete Customer");
		
		buttonPane.add(Box.createHorizontalGlue());
	    buttonPane.add(btnSaveChanges);
	    buttonPane.add(Box.createHorizontalStrut(10));
	    buttonPane.add(btnDeleteCustomer);
	    buttonPane.add(Box.createHorizontalGlue());
	    
	    buttonPane.setBorder(new EmptyBorder(20,20,20,20));
	    
		// call button listener class for action
		btnSaveChanges.addActionListener(new ButtonClickListener());
		btnDeleteCustomer.addActionListener(new ButtonClickListener());
		
		
	}
	
	private void fillInTextFields() {
		Customer customer = database.getCustomer(customerList.getSelectedValue());
		txtCustomerId.setText(customer.getId()+"");
		txtCustomerName.setText(customer.getName());
		txtCustomerAddress.setText(customer.getAddress());
		txtCustomerPhone.setText(customer.getPhoneNum());
		txtCustomerEmail.setText(customer.getEmail());
	}
	
	private void emptyTextFields() {
		txtCustomerId.setText("");
		txtCustomerName.setText("");
		txtCustomerAddress.setText("");
		txtCustomerPhone.setText("");
		txtCustomerEmail.setText("");
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selected customer and fill in content
		if(!listModel.isEmpty()) {
			if(customerList.isSelectionEmpty())
				customerList.setSelectedIndex(0);
			fillInTextFields();	
		}
		// if list empty reset fields to blank
		else
			emptyTextFields();
	}
	
	public void refresh(String newCustomerName) {
		listModel.addElement(newCustomerName);
	}
	
	private void saveCustomerDetails() {
		// get customer details from text fields
		String strId = txtCustomerId.getText();
		String strName = txtCustomerName.getText();
		String strAddress = txtCustomerAddress.getText();
		String strPhone = txtCustomerPhone.getText();
		String strEmail = txtCustomerEmail.getText();

		// check if any text fields have been left blank and output error message if true
		if(strId.length() == 0 || strName.length() == 0 || strAddress.length() == 0
				|| strPhone.length() == 0 || strEmail.length() == 0) {
			JOptionPane.showMessageDialog(null, "You must enter all customer details before submitting", 
					"Incomplete content", JOptionPane.ERROR_MESSAGE);
		}
		else {
			// attempt to parse an integer from customer id
			int customerId = 0;
			boolean gotId = false;
			try{
				customerId = Integer.parseInt(strId);
				gotId = true;
			}catch(NumberFormatException e) {
				// output error dialogue if non numeric characters entered for customer id
				JOptionPane.showMessageDialog(null, "Customer id can not contain any non numeric characters",
						"Incorrect format", JOptionPane.ERROR_MESSAGE);
			}
			if(gotId) {
				// prompt user to confirm they want to save changes
				int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to save the changes",
						"Warning, this will overwrite customer records", JOptionPane.YES_NO_OPTION);
				
				if(value == JOptionPane.YES_OPTION) {
					Customer customer = database.getCustomer(customerList.getSelectedValue());
					customer.setId(customerId);
					customer.setName(strName);
					customer.setAddress(strAddress);
					customer.setPhoneNum(strPhone);
					customer.setEmail(strEmail);
					
					// update customer name in list and refresh content on other cards
					cardLayout.customerEdited(customerList.getSelectedValue(), strName);
					listModel.setElementAt(strName, customerList.getSelectedIndex());
				}
			}
		}
	}
	
	private void deleteSelectedCustomer() {
		int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected customer?",
				"Warning, Customer will be removed from system", JOptionPane.YES_NO_OPTION);
		
		if(value == JOptionPane.YES_OPTION) {
			// if the customer selected yes, delete the customer
			String customerName = customerList.getSelectedValue();
			database.removeCustomer(database.getCustomer(customerName));
			listModel.removeElement(customerName);
			cardLayout.customerDeleted(customerName);
		}
	}
	
	private class ButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnSaveChanges) {
				if(!listModel.isEmpty())
					saveCustomerDetails();
			}
			if(e.getSource() == btnDeleteCustomer) {
				if(!listModel.isEmpty())
					deleteSelectedCustomer();
			}
		}
	}
}