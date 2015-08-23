package bicyclestore.cardlayouts.customercardlayouts;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.customers.Customer;

public class AddCustomerCard extends JPanel {
	
	// auto generated serialVersionUID
	private static final long serialVersionUID = 3323602430099342409L;

	private Database database;
	private CustomersCardLayout cardLayout;
	
	private JTextField txtId, txtName, txtAddress, txtPhone, txtEmail;
	private JButton btnAddCustomer;
	
	private JButton btnEditId;
	private Box idPane;
	private int idCounter = 5;

	public AddCustomerCard(Database database, CustomersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		initComponents();
		createAddCustomerCard();
	}
	
	private void initComponents() {
		setUpIdPane();
		
		txtName = new JTextField(10);
		txtAddress = new JTextField(10);
		txtPhone = new JTextField(10);
		txtEmail = new JTextField(10);
		
		btnAddCustomer = new JButton("Add Customer");
		btnAddCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddCustomer.addActionListener(new ButtonActionListener());
	}
	
	private void setUpIdPane() {
		idPane = Box.createHorizontalBox();
		
		// set ID field to auto-increment value, grey out colour and set editable to false
		txtId = new JTextField(idCounter+"",10);
		txtId.setBackground(Color.LIGHT_GRAY);
		txtId.setEditable(false);
		
		btnEditId = new JButton("Edit ID");
		
		idPane.add(txtId);
		idPane.add(btnEditId);
		
		btnEditId.addActionListener(new ButtonActionListener());
	}
	
	private void createAddCustomerCard() {
		TitledBorder addCustomerBorder = BorderFactory.createTitledBorder("Enter Customer Details");
		addCustomerBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(addCustomerBorder);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(createCustomerDetailsForm());
		add(btnAddCustomer);
	}
	
	private JPanel createCustomerDetailsForm() {
		JPanel customerDetailsGrid = new JPanel();
		GridLayout gridLayout = new GridLayout(5,2);
		gridLayout.setVgap(10);
		customerDetailsGrid.setBorder(new EmptyBorder(20,20,20,20));
		customerDetailsGrid.setLayout(gridLayout);
		customerDetailsGrid.setMaximumSize(new Dimension(400, 400));
		customerDetailsGrid.add(new JLabel("Customers id"));
		customerDetailsGrid.add(idPane);
		customerDetailsGrid.add(new JLabel("Customers name"));
		customerDetailsGrid.add(txtName);
		customerDetailsGrid.add(new JLabel("Customers address"));
		customerDetailsGrid.add(txtAddress);
		customerDetailsGrid.add(new JLabel("Customers phone number"));
		customerDetailsGrid.add(txtPhone);
		customerDetailsGrid.add(new JLabel("Customers email"));
		customerDetailsGrid.add(txtEmail);
		
		return customerDetailsGrid;
	}
	
	// check if all fields have been filled and attempt to add customer to database
	private void checkFieldsComplete() {
		// get customer details from text fields
		String strId = txtId.getText();
		String strName = txtName.getText();
		String strAddress = txtAddress.getText();
		String strPhone = txtPhone.getText();
		String strEmail = txtEmail.getText();
		
		// check if any text fields have been left blank and output error message if true
		if(strId.length() == 0 || strName.length() == 0 || strAddress.length() == 0 
				|| strPhone.length() == 0 || strEmail.length() == 0) {
			JOptionPane.showMessageDialog(null, "You must enter all customer details before submitting", 
					"Incomplete content", JOptionPane.ERROR_MESSAGE);
		}
		// attempt to add customer if all fields are complete
		else
			addCustomer(strId, strName, strAddress, strPhone, strEmail);
	}
	
	private void addCustomer(String strId, String name, String address, String phoneNum, String email) {
		// attempt to parse customer id integer from string
		int customerId = 0;
		boolean gotId = false;
		try{
			customerId = Integer.parseInt(strId);
			gotId = true;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "The customer id field must not contain any non numeric characters",
					"Invalid content", JOptionPane.ERROR_MESSAGE);
		}
		if(gotId) {
			// If the customer was added with auto-increment id, increment id for next customer 
			if(customerId == idCounter)
				idCounter++;
			database.addCustomer(new Customer(customerId, name, address, phoneNum, email));
			confirmCustomerAdded(strId, name, address, phoneNum, email);
		}
	}
	
	private void confirmCustomerAdded(String strId, String name, String address, String phone, String email) {
		// display information message and reset all text fields to blank
		JOptionPane.showMessageDialog(null, "New customer added,\nID: "+strId+", Name: "+name+", Address: "+address+
				"\n Phone number: "+phone+", Email: "+email,
				"Success", JOptionPane.INFORMATION_MESSAGE);
		txtId.setText(idCounter+"");
		txtName.setText("");
		txtAddress.setText("");
		txtPhone.setText("");
		txtEmail.setText("");
		
		cardLayout.newCustomerAdded(name);
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			// if the user pressed the add customer button, check if fields are all filled and attempt to add customer
			if(event.getSource() == btnAddCustomer) {
				checkFieldsComplete();
			}
			// if the user pressed the edit id button display dialogue allowing them to edit id field
			if(event.getSource() == btnEditId) {
				String custIdStr = JOptionPane.showInputDialog(null, "Enter customer ID",
						"Edit Customer ID", JOptionPane.INFORMATION_MESSAGE);
				if(custIdStr != null) {
					try{
						txtId.setText(Integer.parseInt(custIdStr) + "");
					}catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Customer ID must contain a number",
							"Invalid Input", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		}
		
	}

}