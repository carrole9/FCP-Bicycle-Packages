package bicyclestore.cardlayouts.suppliercardlayouts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import bicyclestore.Database;
import bicyclestore.bikes.Bicycle;
import bicyclestore.suppliers.Supplier;

public class ViewSupplierCard extends JPanel implements ListSelectionListener {

	// auto generated serialVersionUID
	private static final long serialVersionUID = -84760905264596971L;
	
	private JList<String> supplierList;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	private JPanel supplierDetails;
	
	private JTable productDetailsTable;
	private Box supplierDetailsBox;
	private DefaultTableModel productsTableModel;
	private JScrollPane tableScrollPane;

	private Database database;
	
	// labels to store supplier details
	private JLabel idLabel, nameLabel, addressLabel, phoneNumLabel, emailLabel, supplierID,
						supplierName, supplierAddress, supplierPhoneNum, supplierEmail;
	
	public ViewSupplierCard(Database database) {
		this.database = database;
		setUpSupplierList();
		createViewSupplierCard();
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

	private void createViewSupplierCard() {
		setLayout(new BorderLayout());
		setUpSupplierDetailsPane();
		setUpLabels();
		
		if(database.getSuppliers().size() > 0)
			setSupplierDetailsContent();
		
		add(listScrollPane, BorderLayout.WEST);
		add(supplierDetailsBox, BorderLayout.CENTER);
	}
	
	private void setUpSupplierDetailsPane() {
		supplierDetailsBox = Box.createVerticalBox();
		
		// set titled border on panel
		GridLayout detailsGrid = new GridLayout(6,2);
		detailsGrid.setVgap(5);
		detailsGrid.setHgap(5);
		supplierDetails = new JPanel(detailsGrid);
		TitledBorder detailsBorder = BorderFactory.createTitledBorder("Supplier Details");
		detailsBorder.setTitleJustification(TitledBorder.CENTER);
		supplierDetails.setBorder(detailsBorder);
		
		createProductDetailsTable();
		
		supplierDetailsBox.add(supplierDetails);
		supplierDetailsBox.add(tableScrollPane);
		
		
	}
	
	private void setUpLabels() {
		idLabel = new JLabel("Supplier ID:", JLabel.CENTER);
		nameLabel = new JLabel("Supplier Name:", JLabel.CENTER);
		addressLabel = new JLabel("Supplier Address:", JLabel.CENTER);
		phoneNumLabel = new JLabel("Supplier Phone:", JLabel.CENTER);
		emailLabel = new JLabel("Supplier Email:", JLabel.CENTER);
		
		idLabel.setOpaque(true);
		idLabel.setBackground(new Color(107,106,104));
		idLabel.setForeground(Color.WHITE);
		
		nameLabel.setBackground(new Color(107,106,104));
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setOpaque(true);
		
		addressLabel.setBackground(new Color(107,106,104));
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setOpaque(true);
		
		phoneNumLabel.setBackground(new Color(107,106,104));
		phoneNumLabel.setForeground(Color.WHITE);
		phoneNumLabel.setOpaque(true);
		
		emailLabel.setBackground(new Color(107,106,104));
		emailLabel.setForeground(Color.WHITE);
		emailLabel.setOpaque(true);
		
		supplierID = new JLabel("", JLabel.CENTER);
		supplierName = new JLabel("", JLabel.CENTER);
		supplierAddress = new JLabel("", JLabel.CENTER);
		supplierPhoneNum = new JLabel("", JLabel.CENTER);
		supplierEmail = new JLabel("", JLabel.CENTER);
		
		supplierID.setBackground(Color.LIGHT_GRAY);
		supplierID.setOpaque(true);
		
		supplierName.setBackground(Color.LIGHT_GRAY);
		supplierName.setOpaque(true);
		
		supplierAddress.setBackground(Color.LIGHT_GRAY);
		supplierAddress.setOpaque(true);
		
		supplierPhoneNum.setBackground(Color.LIGHT_GRAY);
		supplierPhoneNum.setOpaque(true);
		
		supplierEmail.setBackground(Color.LIGHT_GRAY);
		supplierEmail.setOpaque(true);
		
		// add labels to panel
		supplierDetails.add(idLabel);
		supplierDetails.add(supplierID);
		supplierDetails.add(nameLabel);
		supplierDetails.add(supplierName);
		supplierDetails.add(addressLabel);
		supplierDetails.add(supplierAddress);
		supplierDetails.add(phoneNumLabel);
		supplierDetails.add(supplierPhoneNum);
		supplierDetails.add(emailLabel);
		supplierDetails.add(supplierEmail);
	}
	
	private void setSupplierDetailsContent() {
		// get supplier at selected index
		Supplier supplier = database.getSupplier(supplierList.getSelectedValue());
		supplierID.setText(supplier.getSupplierID()+"");
		supplierName.setText(supplier.getName());
		supplierAddress.setText(supplier.getAddress());
		supplierPhoneNum.setText(supplier.getPhoneNum());
		supplierEmail.setText(supplier.getEmail());
		
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
	
	private void emptySupplierDetailFields() {
		supplierID.setText("");
		supplierName.setText("");
		supplierAddress.setText("");
		supplierPhoneNum.setText("");
		supplierEmail.setText("");
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// if list still contains entries reset selection and display content
		if(!listModel.isEmpty()) {
			if(supplierList.isSelectionEmpty())
				supplierList.setSelectedIndex(0);
			setSupplierDetailsContent();	
		}
		// if list empty reset fields to blank
		else
			emptySupplierDetailFields();
	}
	
	public void refresh(String newSupplierName) {
		listModel.addElement(newSupplierName);
	}
	
	public void supplierDetailsEdited(String oldName, String newName) {
		listModel.setElementAt(newName, listModel.indexOf(oldName));
		supplierList.setSelectedValue(newName, true);
		setSupplierDetailsContent();
	}
	
	public void supplierDeleted(String supplierName) {
		listModel.removeElement(supplierName);
		if(supplierName.toString().equals(supplierName)) {
			supplierList.setSelectedIndex(0);
		}
	}

}
