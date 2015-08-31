package bicyclestore.cardlayouts.suppliercardlayouts;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import bicyclestore.Database;
import bicyclestore.bikes.BMX;
import bicyclestore.bikes.Bicycle;
import bicyclestore.bikes.Cruiser;
import bicyclestore.bikes.Hybrid;
import bicyclestore.bikes.MotorisedBike;
import bicyclestore.bikes.MountainBike;
import bicyclestore.bikes.RoadBike;
import bicyclestore.suppliers.Supplier;


public class AddSupplierCard extends JPanel {
	
	// auto generated serialVersionUID
	private static final long serialVersionUID = -6426213638245316291L;
	
	private Database database;
	private SuppliersCardLayout cardLayout;
	
	private ArrayList<Bicycle> catelog;
					
	private JTextField txtID, txtName, txtAddress, txtPhoneNum, txtEmail;
	private JButton btnAddSupplier, btnAddProduct;
	private Box buttonPane;
	
	private DefaultTableModel productsTableModel;
	private JTable productDetailsTable;
	private JScrollPane tableScrollPane;
	
	private String[] productTypes = {"* Please Select Product Type *", "BMX", "Mountain Bike", "Road Bike", "Hybrid", "Cruiser", "Motorised Bike"};
	private String[] productModels; // product models will be empty until use selects product type
	
	//private JButton btnEditId;
	private Box idPane;
	private int idCounter = 105;
	
	private JComboBox<String> combo;
	private JTextField txtNumGears, txtModel, txtColour, txtFrameSize, txtWheelSize, 
							txtFrameComposition, txtCostPrice, txtSalePrice;
	
	public AddSupplierCard(Database database, SuppliersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		initComponents();
		createAddSupplierCard();
	}
	
	private void initComponents() {
		
		setUpIdPane();
		catelog = new ArrayList<Bicycle>();
		txtName = new JTextField(10);
		txtAddress = new JTextField(10);
		txtPhoneNum = new JTextField(10);
		txtEmail = new JTextField(10);
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
		createProductDetailsTable();
		add(tableScrollPane);
		setUpButtonPane();
		add(buttonPane);
	}
	
	private JPanel createSupplierDetailsForm() {
		JPanel supplierDetailsGrid = new JPanel();
		GridLayout gridLayout = new GridLayout(6,2);
		gridLayout.setVgap(10);
		supplierDetailsGrid.setBorder(new EmptyBorder(10,10,10,10));
		supplierDetailsGrid.setLayout(gridLayout);
		supplierDetailsGrid.setMaximumSize(new Dimension(400, 400));
		supplierDetailsGrid.add(new JLabel("Supplier ID:"));
		supplierDetailsGrid.add(idPane);
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
	
	private void createProductDetailsTable() {
		// set up scroll pane and border for products table
		tableScrollPane = new JScrollPane();
		TitledBorder border = BorderFactory.createTitledBorder("Products supplier sells");
		border.setTitleJustification(TitledBorder.CENTER);
		tableScrollPane.setBorder(border);
		
		Object[] colNames = {"Product Type","Model","Cost"};
		productsTableModel = new DefaultTableModel();
		
		// set up table and make sure cells can not be edited by user
		productDetailsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		productsTableModel.setColumnIdentifiers(colNames);
		
		productDetailsTable.setModel(productsTableModel);
		productDetailsTable.setFillsViewportHeight(true);
		productDetailsTable.setPreferredScrollableViewportSize(new Dimension(600,100));
		
		tableScrollPane.setViewportView(productDetailsTable);
	}
	
	private void setUpButtonPane() {
		buttonPane = Box.createHorizontalBox();
		
		btnAddProduct = new JButton("Add Product");
		btnAddSupplier = new JButton("Add Supplier");
		
		buttonPane.add(Box.createHorizontalGlue());
	    buttonPane.add(btnAddProduct);
	    buttonPane.add(Box.createHorizontalStrut(10));
	    buttonPane.add(btnAddSupplier);
	    buttonPane.add(Box.createHorizontalGlue());
	    
	    buttonPane.setBorder(new EmptyBorder(20,20,20,20));
	    
		// call button listener class for action
		btnAddProduct.addActionListener(new ButtonActionListener());
		btnAddSupplier.addActionListener(new ButtonActionListener());
	}
	
	private void displayAddProductForm() {
        JPanel panel = setUpDialogPanel();
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter bicycle details",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
        	// check if user has entered all required fields
        	if(txtModel.getText().length() == 0 || txtColour.getText().length() == 0 
        			|| txtFrameSize.getText().length() == 0 || txtWheelSize.getText().length() == 0 
        			|| txtFrameComposition.getText().length() == 0 || txtCostPrice.getText().length() == 0
        			|| txtSalePrice.getText().length() == 0 || combo.getSelectedIndex() == 0) {
        		
        		JOptionPane.showMessageDialog(null, "You must enter all product details before submitting",
						"Incomplete content", JOptionPane.ERROR_MESSAGE);
        	}
        	else {
        		Bicycle bike = createBicycle();
	        	if(!(bike == null)) {
	        		catelog.add(bike);
	        		Object[] row = {bike.getClass().getSimpleName(), bike.getModel(), bike.getCostPrice()};
	        		productsTableModel.addRow(row);
	        	}
	        	else
	        		System.out.println("Bike is null");
        	}
			
        } else {
            System.out.println("Cancelled");
        }
    }
	
	private JPanel setUpDialogPanel() {
		
		String[] items = {"Select product type", "BMX", "Road Bike", "Mountain Bike", "Hybrid", "Cruiser", "Motorised Bike"};
        combo = new JComboBox<String>(items);
        combo.addActionListener(new ComboBoxListener());
        
        txtModel = new JTextField(10);
        txtColour = new JTextField(10);
        txtFrameSize = new JTextField(10);
        txtWheelSize = new JTextField(10);
        txtFrameComposition = new JTextField(10);
        txtCostPrice = new JTextField(10);
        txtSalePrice = new JTextField(10);
        txtNumGears = new JTextField(10);
		JPanel panel = new JPanel(new GridLayout(9, 2));

		panel.add(new JLabel("Product Type:"));
		panel.add(combo);
		panel.add(new JLabel("Model:"));
		panel.add(txtModel);
		panel.add(new JLabel("Colour:"));
		panel.add(txtColour);
		panel.add(new JLabel("Frame size:"));
		panel.add(txtFrameSize);
		panel.add(new JLabel("Wheel size:"));
		panel.add(txtWheelSize);
		panel.add(new JLabel("FrameComposition:"));
		panel.add(txtFrameComposition);
		panel.add(new JLabel("CostPrice:"));
		panel.add(txtCostPrice);
		panel.add(new JLabel("RRP:"));
		panel.add(txtSalePrice);
		panel.add(new JLabel("Number of gears"));
		panel.add(txtNumGears);
		
		return panel;
	}
	
	private Bicycle createBicycle() {
		int frameSize = -1;
		int wheelSize = -1;
		double costPrice = -1;
		double salePrice = -1;
		int numGears = -1;
		try {
			frameSize = Integer.parseInt(txtFrameSize.getText());
			wheelSize = Integer.parseInt(txtWheelSize.getText());
			costPrice = Double.parseDouble(txtCostPrice.getText());
			salePrice = Double.parseDouble(txtSalePrice.getText());
			if (txtNumGears.getText().length() > 0)
				numGears = Integer.parseInt(txtNumGears.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Bicycle bike;
		switch(combo.getSelectedItem().toString()) {
			case "BMX":
				bike = new BMX(txtColour.getText(), txtModel.getText(), frameSize, wheelSize, txtFrameComposition.getText(),
						database, costPrice, salePrice);
				break;
			case "Road Bike":
				bike = new RoadBike(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), database, costPrice, salePrice);
				break;
			case "Mountain Bike":
				bike = new MountainBike(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), database, costPrice, salePrice);
				break;
			case "Hybrid":
				bike = new Hybrid(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), database, costPrice, salePrice);
				break;
			case "Cruiser":
				bike = new Cruiser(txtColour.getText(), txtModel.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), database, costPrice, salePrice);
				break;
			case "Motorised Bike":
				bike = new MotorisedBike(txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), database, costPrice, salePrice);
				break;
			default:
				bike = null;
		}

		return bike;
	}
	
	private void addSupplier(String supID, String name, String address, String phoneNum, String email, ArrayList<Bicycle> catelog) {
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
			
			database.addSupplier(new Supplier(supplierID, name, address, phoneNum, email, catelog));
			confirmSupplierAdded(supID, name, address, phoneNum, email);
		}
	}
		
	private void confirmSupplierAdded(String supID, String name, String address, String phoneNum, String email) {
		// display information message and reset all text fields to blank
		JOptionPane.showMessageDialog(null, "New supplier added,\nID: "+supID+", Name: "+name+", Address: "+address+","
				+ " Phone: "+phoneNum+", Email: "+email,"Success", JOptionPane.INFORMATION_MESSAGE);
		
		txtID.setText(idCounter+"");
		txtName.setText("");
		txtAddress.setText("");
		txtPhoneNum.setText("");
		txtEmail.setText("");
		
		
		cardLayout.newSupplierAdded(name);
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// if user pressed the add supplier button check fields are complete and attempt to add supplier
			if (e.getSource() == btnAddSupplier) {
				// get customer details from text fields
				String supId = txtID.getText();
				String supName = txtName.getText();
				String supAddress = txtAddress.getText();
				String supPhoneNum = txtPhoneNum.getText();
				String supEmail = txtEmail.getText();

				// check if any text fields have been left blank and output
				// error message if true
				if (supId.length() == 0 || supName.length() == 0 || supAddress.length() == 0
						|| supPhoneNum.length() == 0 || supEmail.length() == 0) {
					JOptionPane.showMessageDialog(null, "You must enter all supplier details before submitting",
							"Incomplete content", JOptionPane.ERROR_MESSAGE);
				}

				// attempt to add supplier if all fields are complete
				else {
					addSupplier(supId, supName, supAddress, supPhoneNum, supEmail, catelog);
				}
			}
			
			// if user pressed the add product button display a dialogue to allow new bicycle entry
			if(e.getSource() == btnAddProduct) {
				displayAddProductForm();
			}
			
		}
	}	
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// grey out number of gears field if unavailable for selected product type
			if(combo.getSelectedItem().equals("BMX") || combo.getSelectedItem().equals("Cruiser") 
					|| combo.getSelectedItem().equals("Motorised Bike")) {
				txtNumGears.setText("");
				txtNumGears.setEditable(false);
				txtNumGears.setOpaque(true);
				txtNumGears.setBackground(Color.GRAY);
			}
			else {
				txtNumGears.setEditable(true);
				txtNumGears.setBackground(Color.WHITE);
			}
		}
		
	}
}
