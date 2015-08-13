package bicyclestore.cardlayouts.suppliercardlayouts;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.suppliers.Supplier;

public class AddSupplierCard extends JPanel {
	
	// auto generated serialVersionUID
	private static final long serialVersionUID = -6426213638245316291L;
	
	private Database database;
	private SuppliersCardLayout cardLayout;
	
	private JTextField txtID, txtName, txtAddress, txtTypeOfProduct, txtPhoneNum, txtEmail;
	private JButton btnAddSupplier;

	public AddSupplierCard(Database database, SuppliersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		initComponents();
		createAddSupplierCard();
	}
	
	private void initComponents() {
		txtID = new JTextField(10);
		txtName = new JTextField(10);
		txtAddress = new JTextField(10);
		txtTypeOfProduct = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		txtEmail = new JTextField(10);
		
		btnAddSupplier = new JButton("Add Supplier");
		btnAddSupplier.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddSupplier.addActionListener(new ButtonActionListener());
	}
	
	private void createAddSupplierCard() {
		TitledBorder addSupplierBorder = BorderFactory.createTitledBorder("Enter New Supplier Details");
		addSupplierBorder.setTitleJustification(TitledBorder.CENTER);
		setBorder(addSupplierBorder);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(createSupplierDetailsForm());
		add(btnAddSupplier);
	}
	
	private JPanel createSupplierDetailsForm() {
		JPanel supplierDetailsGrid = new JPanel();
		GridLayout gridLayout = new GridLayout(6,2);
		gridLayout.setVgap(10);
		supplierDetailsGrid.setBorder(new EmptyBorder(20,20,20,20));
		supplierDetailsGrid.setLayout(gridLayout);
		supplierDetailsGrid.setMaximumSize(new Dimension(400, 400));
		supplierDetailsGrid.add(new JLabel("Supplier ID:"));
		supplierDetailsGrid.add(txtID);
		supplierDetailsGrid.add(new JLabel("Supplier Name:"));
		supplierDetailsGrid.add(txtName);
		supplierDetailsGrid.add(new JLabel("Supplier Address:"));
		supplierDetailsGrid.add(txtAddress);
		supplierDetailsGrid.add(new JLabel("Supplier Product:"));
		supplierDetailsGrid.add(txtTypeOfProduct);
		supplierDetailsGrid.add(new JLabel("Supplier Phone:"));
		supplierDetailsGrid.add(txtPhoneNum);
		supplierDetailsGrid.add(new JLabel("Supplier Email:"));
		supplierDetailsGrid.add(txtEmail);
		
		return supplierDetailsGrid;
	}
	
	private void addSupplier(String supID, String name, String address, String typeOfProduct, String phoneNum, String email) {
		// attempt to parse supplier id integer from string
		int supplierID = 0;
		boolean gotID = false;
		
		try{
			supplierID = Integer.parseInt(supID);
			gotID = true;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "The supplier id field must not contain any non numeric characters",
					"Invalid content", JOptionPane.ERROR_MESSAGE);
		}
		if(gotID) {
			database.addSupplier(new Supplier(supplierID, name, address, typeOfProduct, phoneNum, email));
			confirmSupplierAdded(supID, name, address, typeOfProduct, phoneNum, email);
		}
	}
		
	
	
	private void confirmSupplierAdded(String supID, String name, String address, String typeOfProduct, String phoneNum, String email) {
		// display information message and reset all text fields to blank
		JOptionPane.showMessageDialog(null, "New supplier added,\nID: "+supID+", Name: "+name+", Address: "+address+", Product: "
		+typeOfProduct+", Phone: "+phoneNum+", Email: "+email,"Success", JOptionPane.INFORMATION_MESSAGE);
		
		txtID.setText("");
		txtName.setText("");
		txtAddress.setText("");
		txtTypeOfProduct.setText("");
		txtPhoneNum.setText("");
		txtEmail.setText("");
		
		
		cardLayout.newSupplierAdded(name);
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// get customer details from text fields
			String supId = txtID.getText();
			String supName = txtName.getText();
			String supAddress = txtAddress.getText();
			String supTypeOfProduct = txtTypeOfProduct.getText();
			String supPhoneNum = txtPhoneNum.getText();
			String supEmail = txtEmail.getText();
			
			// check if any text fields have been left blank and output error message if true
			if(supId.length() == 0 || supName.length() == 0 || supAddress.length() == 0 
					|| supTypeOfProduct.length() == 0 || supPhoneNum.length() == 0 || supEmail.length() == 0) {
				JOptionPane.showMessageDialog(null, "You must enter all supplier details before submitting", 
						"Incomplete content", JOptionPane.ERROR_MESSAGE);
			}
			// attempt to add supplier if all fields are complete
			else
				addSupplier(supId, supName, supAddress, supTypeOfProduct, supPhoneNum, supEmail);
		}
		
	}

}