package bicyclestore.cardlayouts.suppliercardlayouts;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class EditSupplierCard extends JPanel implements ListSelectionListener {

	// auto generated serialVersionUID
	private static final long serialVersionUID = -3945567056710817814L;
	
	private Database database;
	private SuppliersCardLayout cardLayout;
	
	private ArrayList<Bicycle> catalog;
	
	private JList<String> supplierList;
	private DefaultListModel<String> listModel;
	private DefaultTableModel productsTableModel;
	private JScrollPane listScrollPane, tableScrollPane;
	private JPanel editSupplierPane;
	private JTable productDetailsTable;
	private Box buttonPane, supplierDetailsBox;
	
	private JLabel lblSupplierID, lblSupplierName, lblSupplierAddress, lblSupplierPhoneNum, lblSupplierEmail;
	private JTextField txtSupplierID, txtSupplierName, txtSupplierAddress, txtSupplierPhoneNum, txtSupplierEmail, txtNumGears, txtModel, txtColour, txtFrameSize, txtWheelSize, 
	txtFrameComposition, txtCostPrice, txtSalePrice;
	
	private JComboBox<String> combo;
	
	private JButton btnAddProduct, btnSaveChanges, btnDeleteSupplier;
	
	public EditSupplierCard(Database database, SuppliersCardLayout cardLayout) {
		this.database = database;
		this.cardLayout = cardLayout;
		setUpSupplierList();
		createEditSupplierCard();
	}

	private void setUpSupplierList() {
		listModel = new DefaultListModel<String>();
		addSuppliersFromDB();
		
		supplierList = new JList<String>(listModel);
		supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		supplierList.setSelectedIndex(0);
		supplierList.addListSelectionListener(this);
		supplierList.setVisibleRowCount(12);
		
		// set up scroll pane to contain list and wrap in titled border
		listScrollPane = new JScrollPane(supplierList);
		TitledBorder scrollPaneBorder = BorderFactory.createTitledBorder("Select a supplier");
		scrollPaneBorder.setTitleJustification(TitledBorder.CENTER);
		listScrollPane.setBorder(scrollPaneBorder);
		
		supplierList.setPreferredSize(new Dimension(200,200));
	}
	
	private void addSuppliersFromDB() {
		for(Supplier supplier : database.getSuppliers()) {
			listModel.addElement(supplier.getName());
		}
	}
	
	private void createEditSupplierCard() {
		setLayout(new BorderLayout());
		
		setUpTextFields();
		setUpEditSupplierPane();
		setUpButtonPane();
		catalog = new ArrayList<Bicycle>();
		
		if(database.getSuppliers().size() > 0)
			fillInTextFields();
		
		add(listScrollPane, BorderLayout.WEST);
		add(supplierDetailsBox, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	private void setUpEditSupplierPane() {
		supplierDetailsBox = Box.createVerticalBox();
		
		GridLayout editPaneGrid = new GridLayout(6,2);
		editPaneGrid.setHgap(5);
		editPaneGrid.setVgap(5);
		editSupplierPane = new JPanel(editPaneGrid);
		
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Edit Supplier Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		editSupplierPane.setBorder(detailsBorder);
		
		editSupplierPane.add(lblSupplierID);
		editSupplierPane.add(txtSupplierID);
		editSupplierPane.add(lblSupplierName);
		editSupplierPane.add(txtSupplierName);
		editSupplierPane.add(lblSupplierAddress);
		editSupplierPane.add(txtSupplierAddress);
		editSupplierPane.add(lblSupplierPhoneNum);
		editSupplierPane.add(txtSupplierPhoneNum);
		editSupplierPane.add(lblSupplierEmail);
		editSupplierPane.add(txtSupplierEmail);
		
		createProductDetailsTable();
		
		supplierDetailsBox.add(editSupplierPane);
		supplierDetailsBox.add(tableScrollPane);
	}
	
	private void setUpTextFields() {
		lblSupplierID = new JLabel("Supplier ID: ");
		txtSupplierID = new JTextField(10);
		
		lblSupplierName = new JLabel("Supplier Name: ");
		txtSupplierName = new JTextField(10);
		
		lblSupplierAddress = new JLabel("Supplier Address: ");
		txtSupplierAddress = new JTextField(10);
		
		lblSupplierPhoneNum = new JLabel("Supplier Phone: ");
		txtSupplierPhoneNum = new JTextField(10);
		
		lblSupplierEmail = new JLabel("Supplier Email:");
		txtSupplierEmail = new JTextField(10);
				
		}
	
	private void setUpButtonPane() {
		buttonPane = Box.createHorizontalBox();
		
		btnSaveChanges = new JButton("Save Changes");
		btnAddProduct = new JButton("Add Product");
		btnDeleteSupplier = new JButton("Delete Supplier");
		
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(btnAddProduct);
	    buttonPane.add(Box.createHorizontalStrut(10));
	    buttonPane.add(btnSaveChanges);
	    buttonPane.add(Box.createHorizontalStrut(10));
	    buttonPane.add(btnDeleteSupplier);
	    buttonPane.add(Box.createHorizontalGlue());
	    
	    buttonPane.setBorder(new EmptyBorder(20,20,20,20));
	    
		// call button listener class for action
		btnSaveChanges.addActionListener(new ButtonClickListener());
		btnAddProduct.addActionListener(new ButtonClickListener());
		btnDeleteSupplier.addActionListener(new ButtonClickListener());
	}
	
	private void fillInTextFields() {
		Supplier supplier = database.getSupplier(supplierList.getSelectedValue());
		txtSupplierID.setText(supplier.getSupplierID()+"");
		txtSupplierName.setText(supplier.getName());
		txtSupplierAddress.setText(supplier.getAddress());
		txtSupplierPhoneNum.setText(supplier.getPhoneNum());
		txtSupplierEmail.setText(supplier.getEmail());
		
		addProductDetailsContent(supplier);
	}
	
	private void createProductDetailsTable() {
		// set up scroll pane and border for products table
		tableScrollPane = new JScrollPane();
		TitledBorder border = BorderFactory.createTitledBorder("Products");
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
	
	private void addProductDetailsContent(Supplier supplier) {
		// remove any suppliers already on table
		for(int i = productsTableModel.getRowCount()-1; i >= 0; i--) {
			productsTableModel.removeRow(i);
		}
		// add all product details from supplier
		for(Bicycle bike : supplier.getProducts()) {
			Object[] row = {splitCamelCase(bike.getClass().getSimpleName()), bike.getModel(), bike.getCostPrice()};
			productsTableModel.addRow(row);
		}
	}
	
	// separate words contained in camelCase. E.g class name "RoadBike" will become "Road Bike"
		private String splitCamelCase(String s) {
			return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
					"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		}
	
	private void emptyTextFields() {
		txtSupplierID.setText("");
		txtSupplierName.setText("");
		txtSupplierAddress.setText("");
		txtSupplierPhoneNum.setText("");
		txtSupplierEmail.setText("");
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selected supplier and fill in content
		if(!listModel.isEmpty()) {
			if(supplierList.isSelectionEmpty())
				supplierList.setSelectedIndex(0);
			fillInTextFields();	
		}
		// if list empty reset fields to blank
		else
			emptyTextFields();
	}
	
	public void refresh(String newSupplierName) {
		listModel.addElement(newSupplierName);
	}
	
	private void saveSupplierDetails() {
		// get supplier details from text fields
		String supID = txtSupplierID.getText();
		String supName = txtSupplierName.getText();
		String supAddress = txtSupplierAddress.getText();
		String supPhoneNum = txtSupplierPhoneNum.getText();
		String supEmail = txtSupplierEmail.getText();

		// check if any text fields have been left blank and output error message if true
		if(supID.length() == 0 || supName.length() == 0 || supAddress.length() == 0
				|| supPhoneNum.length() == 0 || supEmail.length() == 0) {
			JOptionPane.showMessageDialog(null, "You must enter all supplier details before submitting", 
					"Incomplete content", JOptionPane.ERROR_MESSAGE);
		}
		else {
			// attempt to parse an integer from supplier id
			int supplierID = 0;
			boolean gotId = false;
			try{
				supplierID = Integer.parseInt(supID);
				gotId = true;
			}catch(NumberFormatException e) {
				// output error dialogue if non numeric characters entered for supplier id
				JOptionPane.showMessageDialog(null, "Supplier id can not contain any non numeric characters",
						"Incorrect format", JOptionPane.ERROR_MESSAGE);
			}
			if(gotId) {
				// prompt user to confirm they want to save changes
				int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to save the changes",
						"Warning, this will overwrite supplier records", JOptionPane.YES_NO_OPTION);
				
				if(value == JOptionPane.YES_OPTION) {
					Supplier supplier = database.getSupplier(supplierList.getSelectedValue());
					supplier.setSupplierID(supplierID);
					supplier.setName(supName);
					supplier.setAddress(supAddress);
					supplier.setPhoneNum(supPhoneNum);
					supplier.setEmail(supEmail);
					
					// update supplier name in list and refresh content on other cards
					cardLayout.supplierEdited(supplierList.getSelectedValue(), supName);
					listModel.setElementAt(supName, supplierList.getSelectedIndex());
					
				}
			}
		}
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
	        		catalog.add(bike);
	        		Object[] row = {bike.getClass().getSimpleName(), bike.getModel(), bike.getCostPrice()};
	        		productsTableModel.addRow(row);
	        		int tempId = Integer.parseInt(txtSupplierID.getText());
	        		Supplier tempSupplier = database.getSupplier(tempId);
	        		tempSupplier.getProducts().add(bike);
	        	}
	        	else
	        		System.out.println("Bike is null");
        	}
			
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
		panel.add(new JLabel("Frame Composition:"));
		panel.add(txtFrameComposition);
		panel.add(new JLabel("Cost Price:"));
		panel.add(txtCostPrice);
		panel.add(new JLabel("RRP:"));
		panel.add(txtSalePrice);
		panel.add(new JLabel("Number of gears:"));
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
						costPrice, salePrice);
				break;
			case "Road Bike":
				bike = new RoadBike(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), costPrice, salePrice);
				break;
			case "Mountain Bike":
				bike = new MountainBike(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), costPrice, salePrice);
				break;
			case "Hybrid":
				bike = new Hybrid(numGears, txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), costPrice, salePrice);
				break;
			case "Cruiser":
				bike = new Cruiser(txtColour.getText(), txtModel.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), costPrice, salePrice);
				break;
			case "Motorised Bike":
				bike = new MotorisedBike(txtModel.getText(), txtColour.getText(), frameSize, wheelSize,
						txtFrameComposition.getText(), costPrice, salePrice);
				break;
			default:
				bike = null;
		}

		return bike;
	}
	private void deleteSelectedSupplier() {
		int value = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected supplier?",
				"Warning, supplier will be removed from system", JOptionPane.YES_NO_OPTION);
		
		if(value == JOptionPane.YES_OPTION) {
			// if the supplier selected yes, delete the supplier
			String supplierName = supplierList.getSelectedValue();
			database.removeSupplier(database.getSupplier(supplierName));
			listModel.removeElement(supplierName);
			cardLayout.supplierDeleted(supplierName);
		}
	}
	
	private class ButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnSaveChanges) {
				if(!listModel.isEmpty())
					saveSupplierDetails();
			}
			if(e.getSource() == btnDeleteSupplier) {
				if(!listModel.isEmpty())
					deleteSelectedSupplier();
			}
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
