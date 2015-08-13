package bicyclestore.cardlayouts.suppliercardlayouts;


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

import bicyclestore.Database;
import bicyclestore.suppliers.Supplier;

public class EditSupplierCard extends JPanel implements ListSelectionListener {

	// auto generated serialVersionUID
	private static final long serialVersionUID = -3945567056710817814L;
	
	private Database database;
	private SuppliersCardLayout cardLayout;
	
	private JList<String> supplierList;
	private DefaultListModel<String> listModel;
	private JScrollPane listScrollPane;
	private JPanel editSupplierPane;
	private Box buttonPane;
	
	private JLabel lblSupplierID, lblSupplierName, lblSupplierAddress, lblSupplierTypeOfProduct, lblSupplierPhoneNum, lblSupplierEmail;
	private JTextField txtSupplierID, txtSupplierName, txtSupplierAddress, txtSupplierTypeOfProduct, txtSupplierPhoneNum, txtSupplierEmail;
	
	private JButton btnSaveChanges, btnDeleteSupplier;
	
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
		
		if(database.getSuppliers().size() > 0)
			fillInTextFields();
		
		add(listScrollPane, BorderLayout.WEST);
		add(editSupplierPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	private void setUpEditSupplierPane() {
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
		editSupplierPane.add(lblSupplierTypeOfProduct);
		editSupplierPane.add(txtSupplierTypeOfProduct);
		editSupplierPane.add(lblSupplierPhoneNum);
		editSupplierPane.add(txtSupplierPhoneNum);
		editSupplierPane.add(lblSupplierEmail);
		editSupplierPane.add(txtSupplierEmail);
	}
	
	private void setUpTextFields() {
		lblSupplierID = new JLabel("Supplier ID: ");
		txtSupplierID = new JTextField(10);
		
		lblSupplierName = new JLabel("Supplier Name: ");
		txtSupplierName = new JTextField(10);
		
		lblSupplierAddress = new JLabel("Supplier Address: ");
		txtSupplierAddress = new JTextField(10);
		
		lblSupplierTypeOfProduct  = new JLabel("Supplier Product:");
		txtSupplierTypeOfProduct = new JTextField(10);
		
		lblSupplierPhoneNum = new JLabel("Supplier Phone: ");
		txtSupplierPhoneNum = new JTextField(10);
		
		lblSupplierEmail = new JLabel("Supplier Email:");
		txtSupplierEmail = new JTextField(10);
				
		}
	
	private void setUpButtonPane() {
		buttonPane = Box.createHorizontalBox();
		
		btnSaveChanges = new JButton("Save Changes");
		btnDeleteSupplier = new JButton("Delete Supplier");
		
		buttonPane.add(Box.createHorizontalGlue());
	    buttonPane.add(btnSaveChanges);
	    buttonPane.add(Box.createHorizontalStrut(10));
	    buttonPane.add(btnDeleteSupplier);
	    buttonPane.add(Box.createHorizontalGlue());
	    
	    buttonPane.setBorder(new EmptyBorder(20,20,20,20));
	    
		// call button listener class for action
		btnSaveChanges.addActionListener(new ButtonClickListener());
		btnDeleteSupplier.addActionListener(new ButtonClickListener());
		
		
	}
	
	private void fillInTextFields() {
		Supplier supplier = database.getSupplier(supplierList.getSelectedValue());
		txtSupplierID.setText(supplier.getSupplierID()+"");
		txtSupplierName.setText(supplier.getName());
		txtSupplierAddress.setText(supplier.getAddress());
		txtSupplierTypeOfProduct.setText(supplier.getTypeOfProduct());
		txtSupplierPhoneNum.setText(supplier.getPhoneNum());
		txtSupplierEmail.setText(supplier.getEmail());
	}
	
	private void emptyTextFields() {
		txtSupplierID.setText("");
		txtSupplierName.setText("");
		txtSupplierAddress.setText("");
		txtSupplierTypeOfProduct.setText("");
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
		String supTypeOfProduct = txtSupplierTypeOfProduct.getText();
		String supPhoneNum = txtSupplierPhoneNum.getText();
		String supEmail = txtSupplierEmail.getText();

		// check if any text fields have been left blank and output error message if true
		if(supID.length() == 0 || supName.length() == 0 || supAddress.length() == 0
				|| supTypeOfProduct.length() == 0 ||supPhoneNum.length() == 0 || supEmail.length() == 0) {
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
					supplier.setTypeOfProduct(supTypeOfProduct);
					supplier.setPhoneNum(supPhoneNum);
					supplier.setEmail(supEmail);
					
					// update supplier name in list and refresh content on other cards
					cardLayout.supplierEdited(supplierList.getSelectedValue(), supName);
					listModel.setElementAt(supName, supplierList.getSelectedIndex());
				}
			}
		}
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
		}
	}
}