package bicyclestore.cardlayouts.suppliercardlayouts;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bicyclestore.Database;
import bicyclestore.suppliers.Supplier;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;


public class AddSupplierCard extends JPanel {
	
	// auto generated serialVersionUID
	private static final long serialVersionUID = -6426213638245316291L;
	
	private Database database;
	private SuppliersCardLayout cardLayout;
	
	private JTextField txtID, txtName, txtAddress, txtPhoneNum, txtEmail;
	private JButton btnAddSupplier;
	
	private JLabel lblSelectProductType, lblSelectProductModel;	
	private JComboBox<String> productTypeList, productModelList;
	
	private String[] productTypes = {"* Please Select Product Type *", "BMX", "Mountain Bike", "Road Bike", "Hybrid", "Cruiser", "Motorised Bike"};
	private String[] productModels; // product models will be empty until use selects product type
	
	//private JButton btnEditId;
	private Box idPane;
	private int idCounter = 105;

	public AddSupplierCard(Database database, SuppliersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		initComponents();
		createAddSupplierCard();
	}
	
	private void initComponents() {
		
		setUpIdPane();
		
		txtName = new JTextField(10);
		txtAddress = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		txtEmail = new JTextField(10);
		
		lblSelectProductType = new JLabel("Supplier Product Type");
		lblSelectProductModel = new JLabel("Select Model");
		
		productTypeList = new JComboBox<String>(productTypes);
		productTypeList.addActionListener(new ComboBoxListener());
		productModelList = new JComboBox<String>();
		
		btnAddSupplier = new JButton("Add Supplier");
		btnAddSupplier.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAddSupplier.addActionListener(new ButtonActionListener());
	}
	
	private void setUpIdPane() {
		idPane = Box.createHorizontalBox();
		
		txtID = new JTextField(idCounter+"",10);
		txtID.setEditable(false);
		
		idPane.add(txtID);
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
		GridLayout gridLayout = new GridLayout(8,2);
		gridLayout.setVgap(10);
		supplierDetailsGrid.setBorder(new EmptyBorder(20,20,20,20));
		supplierDetailsGrid.setLayout(gridLayout);
		supplierDetailsGrid.setMaximumSize(new Dimension(400, 400));
		supplierDetailsGrid.add(new JLabel("Supplier ID:"));
		supplierDetailsGrid.add(idPane);
		supplierDetailsGrid.add(lblSelectProductType);
		supplierDetailsGrid.add(productTypeList);
		supplierDetailsGrid.add(lblSelectProductModel);
		supplierDetailsGrid.add(productModelList);
		supplierDetailsGrid.add(new JLabel("Supplier Name:"));
		supplierDetailsGrid.add(txtName);
		supplierDetailsGrid.add(new JLabel("Supplier Address:"));
		supplierDetailsGrid.add(txtAddress);
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
			if(supplierID == idCounter)
				idCounter++;
			
			database.addSupplier(new Supplier(supplierID, name, address, typeOfProduct, phoneNum, email));
			confirmSupplierAdded(supID, name, address, typeOfProduct, phoneNum, email);
		}
	}
		
	
	
	private void confirmSupplierAdded(String supID, String name, String address, String typeOfProduct, String phoneNum, String email) {
		// display information message and reset all text fields to blank
		JOptionPane.showMessageDialog(null, "New supplier added,\nID: "+supID+", Name: "+name+", Address: "+address+", Product: "
		+typeOfProduct+", Phone: "+phoneNum+", Email: "+email,"Success", JOptionPane.INFORMATION_MESSAGE);
		
		txtID.setText(idCounter+"");
		txtName.setText("");
		txtAddress.setText("");
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
			String supTypeOfProduct = (String) productTypeList.getSelectedItem();
			String supPhoneNum = txtPhoneNum.getText();
			String supEmail = txtEmail.getText();
			
			// check if any text fields have been left blank and output error message if true
			if(supId.length() == 0 || supName.length() == 0 || supAddress.length() == 0 
					|| supPhoneNum.length() == 0 || supEmail.length() == 0) {
				JOptionPane.showMessageDialog(null, "You must enter all supplier details before submitting", 
						"Incomplete content", JOptionPane.ERROR_MESSAGE);
			}
			// attempt to add supplier if all fields are complete
			else
				addSupplier(supId, supName, supAddress, supTypeOfProduct, supPhoneNum, supEmail);
		}
	}
		private class ComboBoxListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String productType = (String)productTypeList.getSelectedItem();
				ArrayList<String> products = new ArrayList<String>();
				
					ArrayList<Bicycle> bicycles = database.getBicycles();
					if(productType.equals("BMX")) {
						for(int i = 0; i < bicycles.size(); i++){
					     Bicycle temp = bicycles.get(i);
							if(temp instanceof BMX   ){
							products.add(temp.getModel());						
							}
						}
					}
					if(productType.equals("Cruiser")) {					
						for(int i = 0; i < bicycles.size(); i++){
						     Bicycle temp = bicycles.get(i);
								if(temp instanceof Cruiser   ){
								products.add(temp.getModel());						
							}
						}
					}
					if(productType.equals("Hybrid")) {
						for(int i = 0; i < bicycles.size(); i++){
						     Bicycle temp = bicycles.get(i);
								if(temp instanceof Hybrid   ){
								products.add(temp.getModel());						
							}
						}
					}
					if(productType.equals("Motorised Bike")) {
						for(int i = 0; i < bicycles.size(); i++){
						     Bicycle temp = bicycles.get(i);
								if(temp instanceof MotorisedBike   ){
								products.add(temp.getModel());						
							}
						}
					}
					if(productType.equals("Mountain Bike")) {
						for(int i = 0; i < bicycles.size(); i++){
						     Bicycle temp = bicycles.get(i);
								if(temp instanceof MountainBike   ){
								products.add(temp.getModel());						
							}
						}
					}
					if(productType.equals("Road Bike")) {
						for(int i = 0; i < bicycles.size(); i++){
						     Bicycle temp = bicycles.get(i);
								if(temp instanceof RoadBike   ){
								products.add(temp.getModel());						
							}
						}
					}
					
			
				// convert ArrayList to array and update combo box to contain selected models 
				productModels = products.toArray(new String[products.size()]);
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(productModels);
				productModelList.setModel(model);
				}				
					
		}		
		
}
